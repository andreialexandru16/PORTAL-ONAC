package ro.bithat.dms.microservices.dmsws.colaboration;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Lisa de FileInfo.
 */
@XmlRootElement
public class InfoMesajeList extends BaseModel {
    private List<InfoMesaje> infoMesajeList;

    public List<InfoMesaje> getInfoMesajeList() {
        return infoMesajeList;
    }

    public void setInfoMesajeList(List<InfoMesaje> infoMesajeList) {
        this.infoMesajeList = infoMesajeList;
    }
}
