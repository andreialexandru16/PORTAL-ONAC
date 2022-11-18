package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza persoana din v_persoana
 */
@XmlRootElement
public class UtilizatorContactResponse extends BaseModel {

    private Integer idMandat;
    private Integer id;

    public Integer getIdMandat() {
        return idMandat;
    }

    public void setIdMandat(Integer idMandat) {
        this.idMandat = idMandat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
