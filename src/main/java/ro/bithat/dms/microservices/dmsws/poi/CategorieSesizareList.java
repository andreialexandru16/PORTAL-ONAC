package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de categorie sesizare
 */
@XmlRootElement
public class CategorieSesizareList extends BaseModel {
    private List<CategorieSesizare> categorieSesizareList;

    public List<CategorieSesizare> getCategorieSesizareList() {
        return categorieSesizareList;
    }

    public void setCategorieSesizareList(List<CategorieSesizare> categorieSesizareList) {
        this.categorieSesizareList = categorieSesizareList;
    }
}
