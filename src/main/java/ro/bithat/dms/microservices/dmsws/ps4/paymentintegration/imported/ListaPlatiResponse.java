package ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ListaPlatiResponse extends StandardResponse {
    private List<PlataResponse> plati;

    public List<PlataResponse> getPlati() {
        return plati;
    }

    public void setPlati(List<PlataResponse> plati) {
        this.plati = plati;
    }
}
