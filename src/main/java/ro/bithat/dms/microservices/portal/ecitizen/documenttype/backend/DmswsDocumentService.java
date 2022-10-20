package ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocumentList;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.SpelParserUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ro.bithat.dms.security.SecurityUtils.getToken;

@Service
public class DmswsDocumentService extends DmswsRestService {

    @Autowired
    private DmswsPS4Service service;

    public CorespondentaList getCorespondenta(String token, String fileId) {
        String url = getDmswsUrl()+"/file/{token}/getCorespondentaList/"+fileId+'/';

        return get(CorespondentaList.class, checkBaseModel(), url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public CorespondentaList getNrCorespondentaList(String token, String fileId) {
        String url = getDmswsUrl()+"/file/{token}/getNrCorespondentaList/"+fileId+'/';

        return get(CorespondentaList.class, checkBaseModel(), url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public DocumentList getListaTipuriDocumente(String token,Optional<Integer> serviciuElectronic) {
        String url = getDmswsUrl()+"/document/{token}/getListaTipuriDocumente";
        if(serviciuElectronic!=null && serviciuElectronic.isPresent()){
            url+="?serviciuElectronic="+serviciuElectronic.get();
        }
        return get(DocumentList.class, checkBaseModel(), url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    //@Cacheable(value = "listaTipuriDocumente", key = "1")
    public DocumentList getListaTipuriDocumente(String token) {
        return get(DocumentList.class, checkBaseModel(), getDmswsUrl()+"/document/{token}/getListaTipuriDocumente?serviciuElectronic=1",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    //@Cacheable(value = "listaTipuriDocumente", key = "2")
    public List<Document> getListaTipuriDocumente() {
        DocumentList result = getListaTipuriDocumente(getToken());
        return result.getDocumentList();
    }

    public List<Document> getListaTipuriDocumenteFiltered(String filter) {
        List<Document> result = getListaTipuriDocumente();
        if (filter == null || filter.isEmpty()) {
            return result;
        }
        return result.stream().filter(d -> (d.getDenumire() != null && d.getDenumire().toLowerCase().contains(filter.trim().toLowerCase())) ||
                (d.getDescriere() != null && d.getDescriere().toLowerCase().contains(filter.toLowerCase()))).collect(Collectors.toList());
    }

    public List<DocObligatoriuExtra> getDocumenteObligatoriiServiciuDePlatit(Long docTypeId, Optional<Integer> parentFileId) {
        return service.getDocumenteObligatoriiServiciuDePlatit(SecurityUtils.getToken(), docTypeId, parentFileId).getDocObligatoriuList().
                stream().map(d->new DocObligatoriuExtra(d)).collect(Collectors.toList());
    }

    public List<DocObligatoriuExtra> getDocumenteObligatoriiServiciuDePlatit(Long docTypeId, Optional<Integer> parentFileId, Map<String, String> metaDataValues) {
        List<DocObligatoriuExtra> result = getDocumenteObligatoriiServiciuDePlatit(docTypeId, parentFileId);
        result.stream().
		        filter(doe-> NumberUtils.isCreatable(doe.getDocObligatoriu().getCost())).//spel is not a number
		        forEach(doe -> {
		        	Double cost = new Double(doe.getDocObligatoriu().getCost());
		        	doe.getDocObligatoriu().setCostD(cost);
		        });
        result.stream().
                filter(doe-> !NumberUtils.isCreatable(doe.getDocObligatoriu().getCost())).//spel is not a number
                forEach(doe -> {
                	Double cost = (Double)SpelParserUtil.parseSpel(metaDataValues, doe.getDocObligatoriu().getCost());
                	doe.getDocObligatoriu().setCost(String.format("%.2f", cost));
                	doe.getDocObligatoriu().setCostD(cost);
                });
        return result;
    }


}
