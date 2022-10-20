package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DirLinkFoldersAndFiles extends BaseModel {
    private DirLinkList3 dirLinkList3;
    private DirLink_BM dirLink;

    public DirLinkList3 getDirLinkList3() {
        return dirLinkList3;
    }

    public void setDirLinkList3(DirLinkList3 dirLinkList3) {
        this.dirLinkList3 = dirLinkList3;
    }

    public DirLink_BM getDirLink() {
        return dirLink;
    }

    public void setDirLink(DirLink_BM dirLink) {
        this.dirLink = dirLink;
    }
}
