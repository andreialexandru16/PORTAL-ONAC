package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza persoana din v_persoana
 */
@XmlRootElement
public class PersoanaFizicaJuridicaResponse extends BaseModel {

    private Integer idFisier;
    private Integer idDocument;
    private Integer idDocumentCI;
    private Integer idDocumentCUI;
    private Integer idDocumentImputernicire;
    private Integer id;
    private Integer idUtilizator;

    public Integer getIdDocumentCI() {
        return idDocumentCI;
    }

    public void setIdDocumentCI(Integer idDocumentCI) {
        this.idDocumentCI = idDocumentCI;
    }

    public Integer getIdDocumentCUI() {
        return idDocumentCUI;
    }

    public void setIdDocumentCUI(Integer idDocumentCUI) {
        this.idDocumentCUI = idDocumentCUI;
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

    public Integer getIdDocumentImputernicire() {
        return idDocumentImputernicire;
    }

    public void setIdDocumentImputernicire(Integer idDocumentImputernicire) {
        this.idDocumentImputernicire = idDocumentImputernicire;
    }
}
