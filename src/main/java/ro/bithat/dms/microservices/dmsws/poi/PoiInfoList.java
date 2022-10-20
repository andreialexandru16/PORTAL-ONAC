package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza o lista de poi info
 */
@XmlRootElement
public class PoiInfoList extends BaseModel {
    private List<PoiInfo> poiInfoList;

    public List<PoiInfo> getPoiInfoList() {
        return poiInfoList;
    }

    public void setPoiInfoList(List<PoiInfo> poiInfoList) {
        this.poiInfoList = poiInfoList;
    }
}
