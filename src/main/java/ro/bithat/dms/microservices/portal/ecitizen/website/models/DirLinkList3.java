package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de doc attr link.
 */
@XmlRootElement
public class DirLinkList3 extends BaseModel {
    private List<DirLink3> dirLink3;

    public List<DirLink3> getDirLink3() {
        return dirLink3;
    }

    public void setDirLink3(List<DirLink3> dirLink3) {
        this.dirLink3 = dirLink3;
    }
}
