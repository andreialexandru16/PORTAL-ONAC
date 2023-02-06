
package ro.bithat.dms.microservices.portal.ecitizen.website.models;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DrepturiTipDoc {
    private Integer id;
    private Integer idDocument;
    private Integer idUtilizator;
    private boolean editabil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public Integer getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Integer idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public boolean isEditabil() {
        return editabil;
    }

    public void setEditabil(boolean editabil) {
        this.editabil = editabil;
    }
}
