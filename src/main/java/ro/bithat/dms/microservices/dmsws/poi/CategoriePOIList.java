package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de categorie poi
 */
@XmlRootElement
public class CategoriePOIList extends BaseModel {
    private List<CategoriePOI> categoriePOIList;

    public List<CategoriePOI> getCategoriePOIList() {
        return categoriePOIList;
    }

    public void setCategoriePOIList(List<CategoriePOI> categoriePOIList) {
        this.categoriePOIList = categoriePOIList;
    }
}
