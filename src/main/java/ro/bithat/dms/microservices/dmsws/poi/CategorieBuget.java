package ro.bithat.dms.microservices.dmsws.poi;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza categorie buget
 */
@XmlRootElement
public class CategorieBuget extends BaseModel {
    private Integer id;
    private Integer idUnitate;
    private String denumire;
    private String cod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(Integer idUnitate) {
        this.idUnitate = idUnitate;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}