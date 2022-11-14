package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza persoana din v_persoana
 */
@XmlRootElement
public class UtilizatorAcOeResponse extends BaseModel {

    private Integer idFisier;
    private Integer idDocument;
    private Integer idMandat;
    private Integer id;
    private Integer idUtilizator;
    private Integer idCerere;
    private String emailInstitutie;

    public String getEmailInstitutie() {
        return emailInstitutie;
    }

    public void setEmailInstitutie(String emailInstitutie) {
        this.emailInstitutie = emailInstitutie;
    }

    public Integer getIdCerere() {
        return idCerere;
    }

    public void setIdCerere(Integer idCerere) {
        this.idCerere = idCerere;
    }

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

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

    public Integer getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Integer idUtilizator) {
        this.idUtilizator = idUtilizator;
    }
}
