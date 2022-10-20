package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza attribute link.
 */
@XmlRootElement
public class MembriiEchipa extends BaseModel {
    private Integer id;
    private Integer uId;
    private Integer hrpId;
    private Integer idFunctie;
    private Integer idDepartament;
    private Integer idTipAct;
    private String nume;
    private String prenume;
    private String functie;
    private String adresa;
    private String adreseEmailAlternative;
    private String serieAct;
    private String numarAct;
    private String nrContract;
    private String departament;
    private String username;
    private String usernameLdap;
    private String cnp;
    private String nrPermisConducere;
    private String nrOre;
    private String marca;
    private String dataNastere;
    private String companie;
    private String contact;
    private String centruCost;
    private String holidayDaysLeft;
    private String holidayDaysLeftAnte;
    private String holidayDaysLeftAnte2;
    private String holidayDaysContract;
    private String email;
    private String dataIn;
    private String dataOut;
    private String creatLa;
    private String creatDe;
    private String modificatLa;
    private String modificatDe;
    private Integer idProiect;
    private Integer idUnitate;
    private String profilePicture;

    public String getHolidayDaysContract() {
        return holidayDaysContract;
    }

    public void setHolidayDaysContract(String holidayDaysContract) {
        this.holidayDaysContract = holidayDaysContract;
    }

    public Integer getIdTipAct() {
        return idTipAct;
    }

    public void setIdTipAct(Integer idTipAct) {
        this.idTipAct = idTipAct;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getAdreseEmailAlternative() {
        return adreseEmailAlternative;
    }

    public void setAdreseEmailAlternative(String adreseEmailAlternative) {
        this.adreseEmailAlternative = adreseEmailAlternative;
    }

    public String getSerieAct() {
        return serieAct;
    }

    public void setSerieAct(String serieAct) {
        this.serieAct = serieAct;
    }

    public String getNumarAct() {
        return numarAct;
    }

    public void setNumarAct(String numarAct) {
        this.numarAct = numarAct;
    }

    public String getNrContract() {
        return nrContract;
    }

    public void setNrContract(String nrContract) {
        this.nrContract = nrContract;
    }

    public String getHolidayDaysLeftAnte2() {
        return holidayDaysLeftAnte2;
    }

    public void setHolidayDaysLeftAnte2(String holidayDaysLeftAnte2) {
        this.holidayDaysLeftAnte2 = holidayDaysLeftAnte2;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDataIn() {
        return dataIn;
    }

    public void setDataIn(String dataIn) {
        this.dataIn = dataIn;
    }

    public String getDataOut() {
        return dataOut;
    }

    public void setDataOut(String dataOut) {
        this.dataOut = dataOut;
    }

    public String getNrOre() {
        return nrOre;
    }

    public void setNrOre(String nrOre) {
        this.nrOre = nrOre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getHrpId() {
        return hrpId;
    }

    public void setHrpId(Integer hrpId) {
        this.hrpId = hrpId;
    }

    public Integer getIdFunctie() {
        return idFunctie;
    }

    public void setIdFunctie(Integer idFunctie) {
        this.idFunctie = idFunctie;
    }

    public Integer getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(Integer idDepartament) {
        this.idDepartament = idDepartament;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameLdap() {
        return usernameLdap;
    }

    public void setUsernameLdap(String usernameLdap) {
        this.usernameLdap = usernameLdap;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNrPermisConducere() {
        return nrPermisConducere;
    }

    public void setNrPermisConducere(String nrPermisConducere) {
        this.nrPermisConducere = nrPermisConducere;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
    }

    public String getCentruCost() {
        return centruCost;
    }

    public void setCentruCost(String centruCost) {
        this.centruCost = centruCost;
    }

    public String getHolidayDaysLeft() {
        return holidayDaysLeft;
    }

    public void setHolidayDaysLeft(String holidayDaysLeft) {
        this.holidayDaysLeft = holidayDaysLeft;
    }

    public String getHolidayDaysLeftAnte() {
        return holidayDaysLeftAnte;
    }

    public void setHolidayDaysLeftAnte(String holidayDaysLeftAnte) {
        this.holidayDaysLeftAnte = holidayDaysLeftAnte;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public String getCreatDe() {
        return creatDe;
    }

    public void setCreatDe(String creatDe) {
        this.creatDe = creatDe;
    }

    public String getModificatLa() {
        return modificatLa;
    }

    public void setModificatLa(String modificatLa) {
        this.modificatLa = modificatLa;
    }

    public String getModificatDe() {
        return modificatDe;
    }

    public void setModificatDe(String modificatDe) {
        this.modificatDe = modificatDe;
    }

    public Integer getIdProiect() {
        return idProiect;
    }

    public void setIdProiect(Integer idProiect) {
        this.idProiect = idProiect;
    }

    public Integer getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(Integer idUnitate) {
        this.idUnitate = idUnitate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
