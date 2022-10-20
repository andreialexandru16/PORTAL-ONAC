package ro.bithat.dms.microservices.portal.ecitizen.website.services;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.*;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.CriteriiCautare;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.CriteriiCautareList;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RegistraturaCompleteList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RegistraturaService extends DmswsRestService {
//    public LocalitateList getInfoLocalitate(@PathVariable String token, @PathVariable String idTara) {
//        return get(LocalitateList.class, checkBaseModel(), getDmswsUrl()+"/nomenc/{token}/getInfoLocalitate/{idTara}",
//                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTara);
//    }
    public RegistraturaCompleteList getInregistrariList(String token,String idRegistru, Optional<Integer> indexStart,Optional<Integer> indexEnd) {
       String url =  getDmswsUrl()+"/registratura_portal/{token}/getInregistrariList/{idRegistru}";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        return get(RegistraturaCompleteList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idRegistru);
    }
    public BaseModel getInregistrariListCount(String token,String idRegistru) {
        String url =  getDmswsUrl()+"/registratura_portal/{token}/getInregistrariListCount/{idRegistru}";

        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idRegistru);
    }

    public RegistraturaCompleteList getInregistrariListCautareP(String token, String idRegistru, Optional<Integer> indexStart, Optional<Integer> indexEnd, Optional<String> an, Optional<String> luna, Optional<String> searchStr, List<String> criteriiCautareList) {
        String url =  getDmswsUrl()+"/registratura_portal/{token}/getInregistrariListCautareP/{idRegistru}";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        if(an.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&an="+an.get();
        }
        if(luna.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&luna="+luna.get();
        }
        if(searchStr.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&searchStr="+searchStr.get();
        }


        return post(criteriiCautareList,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                RegistraturaCompleteList.class, checkBaseModel(),
                url, token,idRegistru);
    }

    public RegistraturaCompleteList getInregistrariListFiltrare(String token, String idRegistru, Optional<Integer> indexStart, Optional<Integer> indexEnd, Optional<String> an, Optional<String> luna, Optional<String> searchStr, HashMap<String,String> criteriiCautareList) {
        String url =  getDmswsUrl()+"/registratura_portal/{token}/getInregistrariListFiltrare/{idRegistru}";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        if(an.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&an="+an.get();
        }
        if(luna.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&luna="+luna.get();
        }
        if(searchStr.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&searchStr="+searchStr.get();
        }


        return post(criteriiCautareList,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                RegistraturaCompleteList.class, checkBaseModel(),
                url, token,idRegistru);
    }

    public BaseModel getInregistrariListCautareCountP(String token, String idRegistru, Optional<String> an, Optional<String> luna, Optional<String> searchStr, List<String> criteriiCautareList) {
        String url =  getDmswsUrl()+"/registratura_portal/{token}/getInregistrariListCautareCountP/{idRegistru}";
        if(an.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&an="+an.get();
        }
        if(luna.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&luna="+luna.get();
        }
        if(searchStr.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&searchStr="+searchStr.get();
        }
         return post(criteriiCautareList,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idRegistru);
    }

    public CriteriiCautareList getCriteriiCautare(String token, Integer idRegistru) {
        String url =  getDmswsUrl()+"/registratura_portal/{token}/getCriteriiCautare/{idRegistru}";

        return get(CriteriiCautareList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idRegistru);
    }
}