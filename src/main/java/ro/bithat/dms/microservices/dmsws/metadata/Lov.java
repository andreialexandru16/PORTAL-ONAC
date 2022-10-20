package ro.bithat.dms.microservices.dmsws.metadata;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza un lov.
 */
@XmlRootElement
public class Lov {
    String id;
    String valoare;
    String id_parinte;
    String formulaCalcul;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValoare() {
        return valoare;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }

    public Lov(String id, String valoare) {
        this.id = id;
        this.valoare = valoare;
    }
    public Lov() {

    }

    public String getId_parinte() {
        return id_parinte;
    }

    public void setId_parinte(String id_parinte) {
        this.id_parinte = id_parinte;
    }

    public String getFormulaCalcul() {
        return formulaCalcul;
    }

    public void setFormulaCalcul(String formulaCalcul) {
        this.formulaCalcul = formulaCalcul;
    }
}
