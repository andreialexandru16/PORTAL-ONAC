package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

/**
 * Created by Bithat on 6/5/2020.
 */
public class StatusAdaugaEtapa extends BaseModel {
    private Integer id;
    private String status;
    private Integer pozitie;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPozitie() {
        return pozitie;
    }

    public void setPozitie(Integer pozitie) {
        this.pozitie = pozitie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
