package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza o  lista de poifile
 */
@XmlRootElement
public class PoiFileList extends BaseModel {
    private List<PoiFile> poiFileList;

    public List<PoiFile> getPoiFileList() {
        return poiFileList;
    }

    public void setPoiFileList(List<PoiFile> poiFileList) {
        this.poiFileList = poiFileList;
    }
}
