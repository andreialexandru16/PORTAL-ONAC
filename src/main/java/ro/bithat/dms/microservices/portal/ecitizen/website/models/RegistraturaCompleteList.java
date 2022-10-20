package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class RegistraturaCompleteList extends BaseModel {
    private List<RegistraturaComplete> registraturaCompleteList;

    public List<RegistraturaComplete> getRegistraturaCompleteList() {
        return registraturaCompleteList;
    }

    public void setRegistraturaCompleteList(List<RegistraturaComplete> registraturaCompleteList) {
        this.registraturaCompleteList = registraturaCompleteList;
    }
}
