package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class InboxInfo extends BaseModel {
    private String inboxCount;
    private String inboxRedCount;
    private String myRequestsCount;
    private String corespPetitiiCount;
    private String corespControlCount;

    public String getInboxCount() {
        return inboxCount;
    }

    public void setInboxCount(String inboxCount) {
        this.inboxCount = inboxCount;
    }

    public String getInboxRedCount() {
        return inboxRedCount;
    }

    public void setInboxRedCount(String inboxRedCount) {
        this.inboxRedCount = inboxRedCount;
    }

    public String getMyRequestsCount() {
        return myRequestsCount;
    }

    public void setMyRequestsCount(String myRequestsCount) {
        this.myRequestsCount = myRequestsCount;
    }

    public String getCorespPetitiiCount() {
        return corespPetitiiCount;
    }

    public void setCorespPetitiiCount(String corespPetitiiCount) {
        this.corespPetitiiCount = corespPetitiiCount;
    }

    public String getCorespControlCount() {
        return corespControlCount;
    }

    public void setCorespControlCount(String corespControlCount) {
        this.corespControlCount = corespControlCount;
    }
}
