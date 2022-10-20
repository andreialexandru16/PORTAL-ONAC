package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class PpnInfoRegistraturaResp extends BaseModel {
    private Integer version;
    private Integer nrInregistrare;
    private Date dataIntroducerii;
    private Date dataExterna;
    private String numarExtern;
    private Date dataModificarii;
    private String introdusDeUtilizator;
    private String comentariuNodeFluxElo;
    private PpnAdresant adresant;
    private String cuprinsAct;
    private String observatii;
    private Integer idObjElo;
    private Integer idFlowElo;
    private Integer termenRezolvare;
    private String tipActPrimarie;
    private Integer numarFile;
    private String istoricFlux;
    private String inLucruLa;
    private Integer id;
    private Boolean petitie;


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getNrInregistrare() {
        return nrInregistrare;
    }

    public void setNrInregistrare(Integer nrInregistrare) {
        this.nrInregistrare = nrInregistrare;
    }

    public Date getDataIntroducerii() {
        return dataIntroducerii;
    }

    public void setDataIntroducerii(Date dataIntroducerii) {
        this.dataIntroducerii = dataIntroducerii;
    }

    public Date getDataExterna() {
        return dataExterna;
    }

    public void setDataExterna(Date dataExterna) {
        this.dataExterna = dataExterna;
    }

    public String getNumarExtern() {
        return numarExtern;
    }

    public void setNumarExtern(String numarExtern) {
        this.numarExtern = numarExtern;
    }

    public Date getDataModificarii() {
        return dataModificarii;
    }

    public void setDataModificarii(Date dataModificarii) {
        this.dataModificarii = dataModificarii;
    }

    public String getIntrodusDeUtilizator() {
        return introdusDeUtilizator;
    }

    public void setIntrodusDeUtilizator(String introdusDeUtilizator) {
        this.introdusDeUtilizator = introdusDeUtilizator;
    }

    public String getComentariuNodeFluxElo() {
        return comentariuNodeFluxElo;
    }

    public void setComentariuNodeFluxElo(String comentariuNodeFluxElo) {
        this.comentariuNodeFluxElo = comentariuNodeFluxElo;
    }

    public PpnAdresant getAdresant() {
        return adresant;
    }

    public void setAdresant(PpnAdresant adresant) {
        this.adresant = adresant;
    }

    public String getCuprinsAct() {
        return cuprinsAct;
    }

    public void setCuprinsAct(String cuprinsAct) {
        this.cuprinsAct = cuprinsAct;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    public Integer getIdObjElo() {
        return idObjElo;
    }

    public void setIdObjElo(Integer idObjElo) {
        this.idObjElo = idObjElo;
    }

    public Integer getIdFlowElo() {
        return idFlowElo;
    }

    public void setIdFlowElo(Integer idFlowElo) {
        this.idFlowElo = idFlowElo;
    }

    public Integer getTermenRezolvare() {
        return termenRezolvare;
    }

    public void setTermenRezolvare(Integer termenRezolvare) {
        this.termenRezolvare = termenRezolvare;
    }

    public String getTipActPrimarie() {
        return tipActPrimarie;
    }

    public void setTipActPrimarie(String tipActPrimarie) {
        this.tipActPrimarie = tipActPrimarie;
    }

    public Integer getNumarFile() {
        return numarFile;
    }

    public void setNumarFile(Integer numarFile) {
        this.numarFile = numarFile;
    }

    public String getIstoricFlux() {
        return istoricFlux;
    }

    public void setIstoricFlux(String istoricFlux) {
        this.istoricFlux = istoricFlux;
    }

    public String getInLucruLa() {
        return inLucruLa;
    }

    public void setInLucruLa(String inLucruLa) {
        this.inLucruLa = inLucruLa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPetitie() {
        return petitie;
    }

    public void setPetitie(Boolean petitie) {
        this.petitie = petitie;
    }
}
