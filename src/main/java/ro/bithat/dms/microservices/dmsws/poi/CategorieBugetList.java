package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de categorii buget
 */
@XmlRootElement
public class CategorieBugetList extends BaseModel {
    private List<CategorieBuget> categorieBugetList;

    public List<CategorieBuget> getCategorieBugetList() {
        return categorieBugetList;
    }

    public void setCategorieBugetList(List<CategorieBuget> categorieBugetList) {
        this.categorieBugetList = categorieBugetList;
    }
}
