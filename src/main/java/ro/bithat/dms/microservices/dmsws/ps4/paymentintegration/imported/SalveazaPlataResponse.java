package ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalveazaPlataResponse extends StandardResponse {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
