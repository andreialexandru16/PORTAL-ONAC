package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Lisa de FileInfo.
 */
@XmlRootElement
public class ActNormativList extends BaseModel {
    private List<ActNormativ> actNormativList;

    public List<ActNormativ> getActNormativList() {
        return actNormativList;
    }

    public void setActNormativList(List<ActNormativ> actNormativList) {
        this.actNormativList = actNormativList;
    }
}
