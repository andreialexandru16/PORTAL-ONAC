package ro.bithat.dms.microservices.dmsws.email;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Lisa de email.
 */
@XmlRootElement
public class EmailList extends BaseModel {
    private List<Email> emailList;

    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }
}
