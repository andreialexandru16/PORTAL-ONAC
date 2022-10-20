package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Project extends BaseModel {
    private Integer id;
    private String nume;
    private String cod;
    private String numar;
    private String codProiect;
    private String imageLink;
    private Boolean zonaColaborativa;
    private String startDate;
    private String startDateVote;
    private Integer nrMesaje;
    private Integer nrMembri;
    private String creatLa;
    private Integer nrVoturiPro;
    private Integer nrVoturiContra;
    private String publicatLa;
    private String publicatDe;
    private String categoriePoi;
    private String base64Image;

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
    //pozitie
    private String info1;

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }
    public String getCategoriePoi() {
        return categoriePoi;
    }

    public void setCategoriePoi(String categoriePoi) {
        this.categoriePoi = categoriePoi;
    }

    public String getPublicatLa() {
        return publicatLa;
    }

    public void setPublicatLa(String publicatLa) {
        this.publicatLa = publicatLa;
    }

    public String getPublicatDe() {
        return publicatDe;
    }

    public void setPublicatDe(String publicatDe) {
        this.publicatDe = publicatDe;
    }
    public Integer getNrVoturiPro() {
        return nrVoturiPro;
    }

    public void setNrVoturiPro(Integer nrVoturiPro) {
        this.nrVoturiPro = nrVoturiPro;
    }

    public Integer getNrVoturiContra() {
        return nrVoturiContra;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public String getStartDateVote() {
        return startDateVote;
    }

    public void setStartDateVote(String startDateVote) {
        this.startDateVote = startDateVote;
    }

    public Integer getNrMembri() {
        return nrMembri;
    }

    public void setNrMembri(Integer nrMembri) {
        this.nrMembri = nrMembri;
    }

    public Integer getNrMesaje() {
        return nrMesaje;
    }

    public void setNrMesaje(Integer nrMesaje) {
        this.nrMesaje = nrMesaje;
    }
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public Boolean getZonaColaborativa() {
        return zonaColaborativa;
    }

    public void setZonaColaborativa(Boolean zonaColaborativa) {
        this.zonaColaborativa = zonaColaborativa;
    }
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getCodProiect() {
        return codProiect;
    }

    public void setCodProiect(String codProiect) {
        this.codProiect = codProiect;
    }
}
