package ro.bithat.dms.microservices.dmsws.ps4.paymentintegration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported.ListaPlatiResponse;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported.PlataResponse;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported.SalveazaPlataRequest;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported.SalveazaPlataResponse;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;

@Service
public class DmswsBankingService extends DmswsRestService{

    public SalveazaPlataResponse salveazaPlata(String token, SalveazaPlataRequest spReq){
    	return post(spReq, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, SalveazaPlataResponse.class, checkStandardResponse(),
    			getDmswsUrl()+"/banking/{token}/plati/salveazaPlata", token);
    }
    public List<PlataResponse> getPaymentsListByUser() {
        return get(ListaPlatiResponse.class, checkStandardResponse(), getDmswsUrl()+"/banking/{token}/plati/getPlatiByIdUser",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, SecurityUtils.getToken()).getPlati();
    }
    public List<PlataResponse> getPaymentsListLimitedByUser(String nrRows) {
        return get(ListaPlatiResponse.class, checkStandardResponse(), getDmswsUrl()+"/banking/{token}/plati/getPlatiByIdUser/?nrRows={nrRows}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, SecurityUtils.getToken(),nrRows).getPlati();
    }
}
