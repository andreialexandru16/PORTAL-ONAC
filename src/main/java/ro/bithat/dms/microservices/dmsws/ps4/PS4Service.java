package ro.bithat.dms.microservices.dmsws.ps4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.file.*;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkListDynamic;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkListOfLists;
import ro.bithat.dms.microservices.dmsws.metadata.DmswsMetadataService;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.detaliiserviciu.ElectronicService;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.*;
import ro.bithat.dms.microservices.portal.ecitizen.rehabilitationreq.backend.DmswsRehabilitationService;
import ro.bithat.dms.security.SecurityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tip Document is used for clasa document!
 * Document is used for tip document!
 *
 * @author george
 */
@Service
public class PS4Service {

    @Autowired
    private DmswsPS4Service service;

    @Autowired
    private DmswsFileService fileService;

    @Autowired
    private DmswsMetadataService metadataService;

    @Autowired
    private DmswsRehabilitationService rehabilitationService;

    private String getToken() {
        return SecurityUtils.getToken();
    }

    public List<TipDocument> getAllDocumentTypes() {
        TipDocumentList result = service.getTipDocumentList(getToken());
        return result.getTipDocumentList();
    }
    public List<TipDocument> getAllDocumentTypesByToken(String token) {
        TipDocumentList result = service.getTipDocumentList(token);
        return result.getTipDocumentList();
    }
    public Optional<TipDocument> getDocumentType(Integer docTypeId) {
        List<TipDocument> result = SecurityUtils.getAllDocumentTypes();
        return result.stream().filter(dt -> dt.getId().equals(docTypeId)).findFirst();
    }
    public Optional<TipDocument> getDocumentTypeById(Integer docTypeId) {
        return Optional.ofNullable(service.getDocumentTypeById(docTypeId));
        //getDocuments(tipDocument).stream().filter(document -> document.getId().equals(documentId)).findAny();
    }
    public Optional<Document> getDocumentByIdAndClasa(Integer tipDocument, Integer documentId) {
        //return Optional.ofNullable(service.getDocumentById(documentId));
        return  getDocuments(tipDocument).stream().filter(document -> document.getId().equals(documentId)).findAny();
    }
    public Optional<Document> getDocumentById(Integer documentId) {
        return Optional.ofNullable(service.getDocumentById(documentId));
    }
    public Document getDocumentById2(Integer documentId) {
        return service.getDocumentById(documentId);
        //getDocuments(tipDocument).stream().filter(document -> document.getId().equals(documentId)).findAny();
    }

    public List<Document> getDocuments(Integer tipDocument) {
        Optional<TipDocument> tipDoc = SecurityUtils.getAllDocumentTypes(tipDocument).stream().filter(t -> t.getId().equals(tipDocument)).findFirst();
        tipDoc.get().setDocumentList(tipDoc.get().getDocumentList().stream().sorted(Comparator.comparing(Document::getPosition)).collect(Collectors.toList()));
        if (!tipDoc.isPresent()) {
            return Collections.emptyList();
        }
        return tipDoc.get().getDocumentList();
    }

    public List<Document> getDocuments(String filter) {
        List<Document> result = new ArrayList<Document>();
        SecurityUtils.getAllDocumentTypes().stream().forEach(td -> result.addAll(td.getDocumentList()));
        if (filter == null || filter.isEmpty()) {
            return result;
        }
        return result.stream().filter(d -> (d.getDenumire() != null && d.getDenumire().toLowerCase().contains(filter.toLowerCase())) ||
                (d.getDescriere() != null && d.getDescriere().toLowerCase().contains(filter.toLowerCase()))).collect(Collectors.toList());
    }

    public List<Document> getDocuments(Integer tipDocument, String filter, String tipServiciu, String tipPersoana) {
        //TODO add extra filter details
        return getDocuments(tipDocument).stream().
                filter(d -> filter == null || filter.isEmpty() ? true : d.getDenumire() != null && d.getDenumire().toLowerCase().contains(filter.toLowerCase()) ||
                        d.getDescriere() != null && d.getDescriere().toLowerCase().contains(filter.toLowerCase())).
                //filter(d -> tipServiciu==null||tipServiciu.isEmpty()?true:d.getDenumire().toLowerCase().contains(filter.toLowerCase())).
                        collect(Collectors.toList());
    }

    public boolean hasExternalLink(Document document) {
        return getExternalLink(document).isPresent();
    }

    public Optional<String> getExternalLink(Document document) {
        if (document.getJspPage() == null) {
            return null;
        }
        return Optional.ofNullable(document.getJspPage());
    }

    public ElectronicService getDetaliiServiciu(Long docTypeId) {
        return service.getDetaliiServiciu(getToken(), docTypeId);
    }

    public AttributeLinkList getDetaliiServicii(Long docTypeId) {
        return service.getDetaliiServicii(getToken(), docTypeId);
    }

    public List<ActNormativ> getActeNormativeByDocTypeId(Long docTypeId) {
        return service.getActeNormativeByDocTypeId(getToken(), docTypeId).getActNormativList();
    }

    public List<DocObligatoriuExtra> getDocumenteObligatoriiServiciu(Long docTypeId, Optional<Integer> parentFileId) {
        return service.getDocumenteObligatoriiServiciu(getToken(), docTypeId, parentFileId).getDocObligatoriuList().
                stream().map(d -> new DocObligatoriuExtra(d)).collect(Collectors.toList());
    }

    public List<DocObligatoriuExtra> getDocumenteLipsaServiciu(Long docTypeId, Optional<Integer> parentFileId) {
        return service.getDocumenteLipsaServiciu(getToken(), docTypeId, parentFileId).getDocObligatoriuList().
                stream().map(d -> new DocObligatoriuExtra(d)).collect(Collectors.toList());
    }

    public DocObligatoriuExtra uploadFileAndAttach(Long parentFileId, DocObligatoriuExtra docObligatoriuExtra, String filename, byte[] someByteArray) throws ServerErrorException, ServerWebInputException, IOException {
        DocObligatoriuExtra upload = uploadFile(docObligatoriuExtra, filename, someByteArray);
        attachFileToParent(getToken(), upload.getUploadedFileId().intValue(), parentFileId.intValue());
        return upload;
    }

    public List<DocAttrLink> getFileMetaData(Long fileId) {
        return Stream.of(fileService.getFileMetadata(getToken(), fileId + "").getDocAttrLink()).collect(Collectors.toList());
    }

    public Integer createDummyFileIncepereSolicitare(FileData fileData) {
        return fileService.createDummyFile(getToken(), fileData).getId();
    }
    public Integer createDummyFileIncepereSolicitareRegistratura(FileData fileData) {
        return fileService.createDummyFileSolicitare(getToken(), fileData).getId();
    }

    public String createDummyFileIncepereSolicitareRegistraturaStr(FileData fileData) {
        return fileService.createDummyFileSolicitare(getToken(), fileData).getDownloadLinkFile();
    }

    public Long replaceExistingFileAndSetMetadata(Long fileIdToBeReplaced, DocAttrLinkList metaData) throws ServerErrorException, ServerWebInputException, IOException {
        return replaceExistingFileAndSetMetadata(fileIdToBeReplaced, metaData, "dummy_filename", new byte[0]);
    }

    public Long replaceExistingFileAndSetMetadata(Long fileIdToBeReplaced, DocAttrLinkList docAttrLinkList, String filename, byte[] someByteArray) throws ServerErrorException, ServerWebInputException, IOException {
        CreateTipDocFileResponse resp = fileService.uploadToReplaceExistingFile(filename, fileIdToBeReplaced, filename, someByteArray);
        saveDocAttribute(new Integer(resp.getFileId()), docAttrLinkList);
        return new Long(resp.getFileId());
    }

    public void saveDocAttribute(Integer fileId, DocAttrLinkList docAttrLinkList) {
        fileService.setDocAttributesEvenIfMissing(getToken(), fileId, docAttrLinkList);
    }

    public void createProiectFromCerere(Integer fileId, FileOb fileOb) {
        fileService.createProiectFromCerere(getToken(), fileId, fileOb);
    }

    public DocObligatoriuExtra uploadFile(DocObligatoriuExtra docObligatoriuExtra, String filename, byte[] someByteArray) throws ServerErrorException, ServerWebInputException, IOException {
        CreateTipDocFileResponse result = fileService.uploadFisierTipDocId(getToken(), docObligatoriuExtra.getDocObligatoriu().getId_document(), SecurityUtils.getUserId(), filename, filename, someByteArray,Optional.empty());
        docObligatoriuExtra.setUploadedFileId(new Long(result.getFileId()));
        docObligatoriuExtra.setDownloadLinkForUploadedFile(result.getDownloadLink());
        docObligatoriuExtra.setUploadedFileName(filename);
        return docObligatoriuExtra;
    }

    public DocObligatoriuExtra uploadFileProcesare(DocObligatoriuExtra docObligatoriuExtra, String filename, byte[] someByteArray) throws ServerErrorException, ServerWebInputException, IOException {
        CreateTipDocFileResponse result = fileService.uploadFisierTipDocId(getToken(), docObligatoriuExtra.getDocObligatoriu().getId_document(), SecurityUtils.getUserId(), filename, filename, someByteArray,Optional.of("1"));
        docObligatoriuExtra.setUploadedFileId(new Long(result.getFileId()));
        docObligatoriuExtra.setDownloadLinkForUploadedFile(result.getDownloadLink());
        docObligatoriuExtra.setUploadedFileName(filename);
        return docObligatoriuExtra;
    }
    public void attachFileToParent(String token, Integer fileId, Integer parentFileId) {
        fileService.attachFile(getToken(), fileId, parentFileId);
    }
    public void attachFileToParentDocRaspuns(String token, Integer fileId, Integer parentFileId,Integer requestFileId) {
        fileService.attachFileDocRaspuns(getToken(), fileId, parentFileId,requestFileId);
    }

////////////////////////////metadata and LOV////////////////////////////////

    public AttributeLinkList getMetadataByDocumentId(Integer documentId) {
        return metadataService.getMetadataByDocumentId(getToken(), documentId);
    }

    public LovList getLovListFiltered(Integer lovId, String p_search) {
        return metadataService.getLovListFiltered(getToken(), lovId, p_search);
    }
    public LovList getLovListFilteredById(Integer lovId, String p_id) {
        return metadataService.getLovListFilteredById(getToken(), lovId, p_id);
    }
    public LovList getLovList(Integer lovId) {
        return metadataService.getLovList(getToken(), lovId);
    }

    public LovList getTari(){
        return metadataService.getTari(getToken());
    }

    public LovList getJudeteByIdTara(Integer idTara){
        return metadataService.getJudeteByIdTara(getToken(),idTara);
    }

    public LovList getLocalitatiByJudet(Integer idJudet){
        return metadataService.getLocalitatiByJudet(getToken(),idJudet);
    }

    public AttributeLinkList getAttributeLinkListByFileId(Integer fileId) {
        return metadataService.getAttributeLinkListByFileId(getToken(), fileId);
    }

    public DocAttrLinkList getDocAttrLinkListByFileId(Integer fileId) {
        return metadataService.getDocAttrLinkListByFileId(getToken(), fileId);
    }

    public BaseModel checkIfHasRole(String token, String roleCode) {
        //return true pe info daca are rol
        //return false pe info daca nu are rol
        return rehabilitationService.checkIfHasRole(token,roleCode);
    }


    public String exportXls(Integer idDocument) {

        return fileService.exportXls(getToken(),idDocument);
    }

    public AttributeLinkListOfLists importXls(byte[] fisier, Integer idDocument) {

        return fileService.importXls(getToken(),fisier,idDocument);
    }
    public CreateTipDocFileResponseXml importXml(byte[] fisier, Integer idDocument, String fileName) {

        return fileService.importXml(getToken(),fisier,idDocument,fileName);
    }
    public DocObligatoriiResp getCriteriiScrisoareLipsuri(long documentId, Optional<Integer> fileId) {
        return service.getCriteriiScrisoareLipsuri(getToken(), fileId,documentId,Optional.ofNullable("true"));
    }

    public DocObligatoriuExtra uploadFileAndAttachLaDocRaspuns(Integer requestFileId,Long parentFileId, DocObligatoriuExtra docObligatoriuExtra, String filename, byte[] someByteArray) throws ServerErrorException, ServerWebInputException, IOException {
        //1. Upload file
        DocObligatoriuExtra upload = uploadFile(docObligatoriuExtra, filename, someByteArray);
        //2. Attach la scrisoare/adresa
        attachFileToParentDocRaspuns(getToken(), upload.getUploadedFileId().intValue(), parentFileId.intValue(),requestFileId);
        return upload;
    }
    public LovList getLovListWithDependecies(AttributeLink attr, Map<AttributeLink, String> smartFormComponentsValues, AttributeLink attrComplex) {

        AttributeLinkList attributeLinkList= new AttributeLinkList();
        List<AttributeLink> listAttr= new ArrayList<>();
        for (AttributeLink attributeLink : smartFormComponentsValues.keySet()){
            attributeLink.setValue(smartFormComponentsValues.get(attributeLink));
           /* if(attr.getAttributeId().equals(attributeLink.getAttributeId())){
                attributeLink.setLabel("ATRIBUT_CURENT");
                if(attrComplex!=null){
                    attr.setIdDocumentSelectie(attrComplex.getAttributeId());
                }
            }*/
            listAttr.add(attributeLink);

        }
        /*if(listAttr==null || listAttr.size()==0){
            attr.setLabel("ATRIBUT_CURENT");
            if(attrComplex!=null){
                attr.setIdDocumentSelectie(attrComplex.getAttributeId());
            }
            listAttr.add(attr);
        }*/
        attributeLinkList.setAttributeLink(listAttr);
        LovList listValues=  metadataService.getLovListWithDependecies(getToken(), attr, attr.getLovId(),attributeLinkList);
        return listValues;

    }

    public LovList getLovListWithDependeciesDynamic(AttributeLink attr, Map<AttributeLink, String> smartFormComponentsValues, AttributeLink attrComplex, Integer offset, Integer limit, String query) {
        AttributeLinkListDynamic attributeLinkList = new AttributeLinkListDynamic();
        List<AttributeLink> listAttr = new ArrayList<>();

        for (AttributeLink attributeLink : smartFormComponentsValues.keySet()) {
            attributeLink.setValue(smartFormComponentsValues.get(attributeLink));
            listAttr.add(attributeLink);
        }

        attributeLinkList.setAttributeLink(listAttr);

        attributeLinkList.setOffset(offset);
        attributeLinkList.setLimit(limit);
        attributeLinkList.setQuery(query);

        LovList listValues=  metadataService.getLovListWithDependeciesDynamic(getToken(), attr, attr.getLovId(),attributeLinkList);
        return listValues;
    }

    public LovList getLovList(AttributeLink attr) {

        AttributeLinkList attributeLinkList= new AttributeLinkList();
        List<AttributeLink> listAttr= new ArrayList<>();
        attributeLinkList.setAttributeLink(listAttr);
        LovList listValues=  metadataService.getLovListWithDependecies(getToken(), attr, attr.getLovId(),attributeLinkList);
        return listValues;

    }

    public List<TipDocument> getAllDocumentTypesByTag(String tag) {
        TipDocumentList result = service.getTipDocumentListByTag(getToken(),tag);
        return result.getTipDocumentList();
    }

    public WsAndUserInfo getWsAndUserInfo(){
        UserToken userToken = SecurityUtils.getUserToken();
        WsAndUserInfo wsAndUserInfo = new WsAndUserInfo();
        wsAndUserInfo.setUserToken(userToken);

        if(service.getZuulproxyUploadUrl()!=null && !service.getZuulproxyUploadUrl().isEmpty()){
            wsAndUserInfo.setUploadWsUrl(service.getZuulproxyUploadUrl());
        }
        else{
            wsAndUserInfo.setUploadWsUrl(service.getDmswsUrl());
        }
        if(service.getZuulproxyUrl()!=null && !service.getZuulproxyUrl().isEmpty()){
            wsAndUserInfo.setWsUrl(service.getZuulproxyUrl());
        }
        else{
            wsAndUserInfo.setWsUrl(service.getDmswsUrl());
        }
        wsAndUserInfo.setPortalUrl(service.getPortalUrl());
        wsAndUserInfo.setTert(service.getTertDataByUserId(SecurityUtils.getToken()));
        wsAndUserInfo.setListaRoluri(SecurityUtils.getContCurentPortalE().getListaRoluri());

        return wsAndUserInfo;
    }


}
