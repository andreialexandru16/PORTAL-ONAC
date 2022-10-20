package ro.bithat.dms.microservices.portal.ecitizen.cache.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CacheService {

	private static Logger logger = LoggerFactory.getLogger(CacheService.class);
	
	public static String[] cachesNames = new String[] {
			"infoTara", 
		    "infoLocalitate", 
		    "infoJudet", 
		    "tipDocumentList",
		    "dmswsPS4Service_tipDocumentList",
		    "listaTipuriDocumente", 
		    "detaliiServiciu", 
		    "acteNormativeByDocTypeId", 
		    "metadataByDocumentId", 
		    "lovListFiltered", 
		    "lovList", 
		    "documenteObligatoriiServiciu"
//    "docAttrLinkListByFileId", 
//    "attributeLinkListByFileId"
    }; 
    
	@CacheEvict(cacheNames = {
			"infoTara", 
		    "infoLocalitate", 
		    "infoJudet", 
		    "tipDocumentList",
		    "dmswsPS4Service_tipDocumentList",
		    "listaTipuriDocumente", 
		    "detaliiServiciu", 
		    "acteNormativeByDocTypeId", 
		    "metadataByDocumentId", 
		    "lovListFiltered", 
		    "lovList",
		    "documenteObligatoriiServiciu"})
	public void clearCaches() {
		logger.info("The cache was cleared for "+ Stream.of(cachesNames).collect(Collectors.joining(",")));
	}
}
