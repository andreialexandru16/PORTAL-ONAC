package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class DocObligatoriiResp extends BaseModel {
    private List<DocObligatoriu> docObligatoriuList;
    private String nrInregRaspuns;
    private String userToInfoEmail;
    private String infoFisierParinte;

    public String getInfoFisierParinte() {
        return infoFisierParinte;
    }

    public void setInfoFisierParinte(String infoFisierParinte) {
        this.infoFisierParinte = infoFisierParinte;
    }

    public String getUserToInfoEmail() {
        return userToInfoEmail;
    }

    public void setUserToInfoEmail(String userToInfoEmail) {
        this.userToInfoEmail = userToInfoEmail;
    }

    public String getNrInregRaspuns() {
        return nrInregRaspuns;
    }

    public void setNrInregRaspuns(String nrInregRaspuns) {
        this.nrInregRaspuns = nrInregRaspuns;
    }

    public List<DocObligatoriu> getDocObligatoriuList() {
        return docObligatoriuList;
    }

    public void setDocObligatoriuList(List<DocObligatoriu> docObligatoriuList) {
        this.docObligatoriuList = docObligatoriuList;
    }
}
