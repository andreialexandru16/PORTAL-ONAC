package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class SucursalaList extends BaseModel {
    private List<Sucursala> sucursalaList;

    public List<Sucursala> getSucursalaList() {
        return sucursalaList;
    }

    public void setSucursalaList(List<Sucursala> sucursalaList) {
        this.sucursalaList = sucursalaList;
    }
}