package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza attribute link.
 */
@XmlRootElement
public class Utilizator extends BaseModel{
    private String Id;
    private String Nume;
    private String Prenume;
    private String username;
    private String Parola;
    private String Email;
    private String NrOre;
    private String IdUnitate;
    private boolean Activ;
    private String SecurityCode;
    private String DataActivStart;
    private String DataActivEnd;
    private String CreatDe;
    private String CreatLa;
    private String ModificatDe;
    private String ModificatLa;
    private String PassSetDate;
    private String LoginFailRemains;
    private String Locked;
    private String ShowFirstLogin;
    private String label;
    private Integer  nrCrt;
    private String roluri;
    private Integer  idSubcont;

    public Integer getIdSubcont() {
        return idSubcont;
    }

    public void setIdSubcont(Integer idSubcont) {
        this.idSubcont = idSubcont;
    }

    public String getRoluri() {
        return roluri;
    }

    public void setRoluri(String roluri) {
        this.roluri = roluri;
    }
    public Integer getNrCrt() {
        return nrCrt;
    }

    public void setNrCrt(Integer nrCrt) {
        this.nrCrt = nrCrt;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public String getPrenume() {
        return Prenume;
    }

    public void setPrenume(String prenume) {
        Prenume = prenume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return Parola;
    }

    public void setParola(String parola) {
        Parola = parola;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNrOre() {
        return NrOre;
    }

    public void setNrOre(String nrOre) {
        NrOre = nrOre;
    }

    public String getIdUnitate() {
        return IdUnitate;
    }

    public void setIdUnitate(String idUnitate) {
        IdUnitate = idUnitate;
    }

    public boolean isActiv() {
        return Activ;
    }

    public void setActiv(boolean activ) {
        Activ = activ;
    }

    public String getSecurityCode() {
        return SecurityCode;
    }

    public void setSecurityCode(String securityCode) {
        SecurityCode = securityCode;
    }

    public String getDataActivStart() {
        return DataActivStart;
    }

    public void setDataActivStart(String dataActivStart) {
        DataActivStart = dataActivStart;
    }

    public String getDataActivEnd() {
        return DataActivEnd;
    }

    public void setDataActivEnd(String dataActivEnd) {
        DataActivEnd = dataActivEnd;
    }

    public String getCreatDe() {
        return CreatDe;
    }

    public void setCreatDe(String creatDe) {
        CreatDe = creatDe;
    }

    public String getCreatLa() {
        return CreatLa;
    }

    public void setCreatLa(String creatLa) {
        CreatLa = creatLa;
    }

    public String getModificatDe() {
        return ModificatDe;
    }

    public void setModificatDe(String modificatDe) {
        ModificatDe = modificatDe;
    }

    public String getModificatLa() {
        return ModificatLa;
    }

    public void setModificatLa(String modificatLa) {
        ModificatLa = modificatLa;
    }

    public String getPassSetDate() {
        return PassSetDate;
    }

    public void setPassSetDate(String passSetDate) {
        PassSetDate = passSetDate;
    }

    public String getLoginFailRemains() {
        return LoginFailRemains;
    }

    public void setLoginFailRemains(String loginFailRemains) {
        LoginFailRemains = loginFailRemains;
    }

    public String getLocked() {
        return Locked;
    }

    public void setLocked(String locked) {
        Locked = locked;
    }

    public String getShowFirstLogin() {
        return ShowFirstLogin;
    }

    public void setShowFirstLogin(String showFirstLogin) {
        ShowFirstLogin = showFirstLogin;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
