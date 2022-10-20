package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de achizitii
 */
@XmlRootElement
public class RaportSocietatiList extends BaseModel {
    private List<RaportSocietati> acquisitionList;

    public List<RaportSocietati> getAcquisitionList() {
        return acquisitionList;
    }

    public void setAcquisitionList(List<RaportSocietati> acquisitionList) {
        this.acquisitionList = acquisitionList;
    }
}
