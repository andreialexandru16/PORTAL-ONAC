package ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class PlataResponse extends StandardResponse {
    private Integer id;
    private Date data_plata;
    private String data_plata_str;
    private Double suma;
    private String referinta_externa;
    private String hash;
    private String info1;
    private String info2;
    private String info3;
    private Integer id_fisier;
    private Integer plata_status;
    private String err_code;
    private String err_value;
    private String numeFisier;
    private String categorie;
    private String statusFlux;
    private Integer idClasaDocument;
    private Integer idDocument;

    public Integer getIdClasaDocument() {
        return idClasaDocument;
    }

    public void setIdClasaDocument(Integer idClasaDocument) {
        this.idClasaDocument = idClasaDocument;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }
    public String getNumeFisier() {
        return numeFisier;
    }

    public void setNumeFisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getStatusFlux() {
        return statusFlux;
    }

    public void setStatusFlux(String statusFlux) {
        this.statusFlux = statusFlux;
    }

    public String getData_plata_str() {
        return data_plata_str;
    }

    public void setData_plata_str(String data_plata_str) {
        this.data_plata_str = data_plata_str;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData_plata() {
        return data_plata;
    }

    public void setData_plata(Date data_plata) {
        this.data_plata = data_plata;
    }

    public Double getSuma() {
        return suma;
    }

    public void setSuma(Double suma) {
        this.suma = suma;
    }

    public String getReferinta_externa() {
        return referinta_externa;
    }

    public void setReferinta_externa(String referinta_externa) {
        this.referinta_externa = referinta_externa;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public Integer getId_fisier() {
        return id_fisier;
    }

    public void setId_fisier(Integer id_fisier) {
        this.id_fisier = id_fisier;
    }

    public Integer getPlata_status() {
        return plata_status;
    }

    public void setPlata_status(Integer plata_status) {
        this.plata_status = plata_status;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_value() {
        return err_value;
    }

    public void setErr_value(String err_value) {
        this.err_value = err_value;
    }
}
