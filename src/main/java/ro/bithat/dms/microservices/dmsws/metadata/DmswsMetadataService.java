package ro.bithat.dms.microservices.dmsws.metadata;
//Neata Georgiana # 23.06.2021 # ANRE # adaugare API de get valori LOV, inclusiv daca acestea depind de altele (=> au in formula ^COD_ATRIBUT)

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.file.Metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ro.bithat.dms.boot.BeanUtil.getBean;

@Service
public class DmswsMetadataService extends DmswsRestService{

	public Metadata getFileMetadata(String token, String fileId)  {
		return get(Metadata.class, checkBaseModel(), getDmswsUrl() + "/attribute/{token}/file_attr_list_by_id/{fileId}", token, fileId);
	}

	public BaseModel setFileMetadata(String token, Integer fileId, @RequestBody DocAttrLinkList docAttrLinkList) {
		return put(docAttrLinkList, BaseModel.class, checkBaseModel(), getDmswsUrl() + "/attribute/{token}/set_attr_list/{fileId}", token, fileId);
	}
    
    public BaseModel setFileMetadataEvenIfMissing(String token, Integer fileId, @RequestBody DocAttrLinkList docAttrLinkList) {
    	return put(docAttrLinkList, BaseModel.class, checkBaseModel(), getDmswsUrl() + "/attribute/{token}/add_set_attr_list/{fileId}", token, fileId);
	}

    //@Cacheable(value = "metadataByDocumentId", key = "#documentId")
    public AttributeLinkList getMetadataByDocumentId(String token, Integer documentId){
		AttributeLinkList attributeLinkList=  get(AttributeLinkList.class, checkBaseModel(), getDmswsUrl() + "/attribute/{token}/list_by_category/{categoryId}", token, documentId);
    	return attributeLinkList;
    }
    
    //should it be cached??
//    @Cacheable(value = "attributeLinkListByFileId", key = "#fileId", unless = "#result != null  && #result.attributeLink != null && #result.attributeLink.size()>0")
	public AttributeLinkList getAttributeLinkListByFileId(String token, Integer fileId){
		return get(AttributeLinkList.class, checkBaseModel(), getDmswsUrl() + "/attribute/{token}/full_file_attr_list_by_id/{fileId}", token, fileId);
	}

    //should it be cached??
//	@Cacheable(value = "docAttrLinkListByFileId", key = "#fileId", unless = "#result != null  && #result.docAttrLink != null && #result.docAttrLink.size()>0")
	public DocAttrLinkList getDocAttrLinkListByFileId(String token, Integer fileId){
		return get(DocAttrLinkList.class, checkBaseModel(), getDmswsUrl() + "/attribute/{token}/full_file_doc_attr_list_by_id/{fileId}", token, fileId);
	}

	//@Cacheable(value = "lovListFiltered", key = "#p_search + #lovId")
    public LovList getLovListFiltered(String token,  Integer lovId, String p_search){
    	return get(LovList.class, getDmswsUrl() + "/lov/{token}/values_by_id/{lovId}/{p_search}", token, lovId, p_search);
    }
	public LovList getLovListFilteredById(String token,  Integer lovId, String p_id){
		return get(LovList.class, getDmswsUrl() + "/lov/{token}/values_by_id_search/{lovId}/{p_id}", token, lovId, p_id);
	}
	//@Cacheable(value = "lovList", key = "#lovId")
    public LovList getLovList(String token,  Integer lovId){
    	return get(LovList.class, getDmswsUrl() + "/lov/{token}/values_by_id/{lovId}", token, lovId);
    }

	public LovList getTari(String token){
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getTari", token);
	}

	public LovList getJudeteByIdTara(String token, Integer idTara){
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getJudeteByIdTara/{idTara}", token,idTara);
	}

	public LovList getLocalitatiByJudet(String token, Integer idJudet){
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getLocalitatiByIdJudet/{idJudet}", token,idJudet);
	}

    public BaseModel getDataTypesList(String token){
		return get(BaseModel.class, getDmswsUrl() + "/attribute/{token}/getDataTypeList", token);
	}

	//Neata Georgiana # 23.06.2021 # ANRE # adaugare API de get valori LOV, inclusiv daca acestea depind de altele (=> au in formula ^COD_ATRIBUT)

	public LovList getLovListWithDependecies(String token, AttributeLink attr, Integer lovId, @RequestBody AttributeLinkList attributeLinkList) {


		return post(attributeLinkList, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, LovList.class, checkBaseModel(),
				getDmswsUrl() + "/lov/{token}/values_by_id_dependencies/{lovId}", token, lovId);
	}

	public LovList getLovListWithDependeciesDynamic(String token, AttributeLink attr, Integer lovId, @RequestBody AttributeLinkListDynamic attributeLinkList) {
		return post(attributeLinkList, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, LovList.class, checkBaseModel(),
				getDmswsUrl() + "/lov/{token}/values_by_id_dependencies_dynamic/{lovId}", token, lovId);
	}
}
