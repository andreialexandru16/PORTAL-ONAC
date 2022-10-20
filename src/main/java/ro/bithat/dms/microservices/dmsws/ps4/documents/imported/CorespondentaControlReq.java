package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CorespondentaControlReq {
    private Integer idUtilizator;

    public Integer getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Integer idUtilizator) {
        this.idUtilizator = idUtilizator;
    }
}
