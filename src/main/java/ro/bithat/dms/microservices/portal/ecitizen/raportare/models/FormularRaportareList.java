package ro.bithat.dms.microservices.portal.ecitizen.raportare.models;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class FormularRaportareList extends BaseModel {
    private List<FormularRaportare> formularRaportareList;
    private Integer isPerioadaInchisa;

    public Integer getIsPerioadaInchisa() {
        return isPerioadaInchisa;
    }

    public void setIsPerioadaInchisa(Integer isPerioadaInchisa) {
        this.isPerioadaInchisa = isPerioadaInchisa;
    }
    public List<FormularRaportare> getFormularRaportareList() {
        return formularRaportareList;
    }

    public void setFormularRaportareList(List<FormularRaportare> formularRaportareList) {
        this.formularRaportareList = formularRaportareList;
    }
}
