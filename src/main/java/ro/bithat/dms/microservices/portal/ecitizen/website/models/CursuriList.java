package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CursuriList extends BaseModel {
    private List<Cursuri> cursuriList;

    public List<Cursuri> getCursuriList() {
        return cursuriList;
    }

    public void setCursuriList(List<Cursuri> cursuriList) {
        this.cursuriList = cursuriList;
    }
}
