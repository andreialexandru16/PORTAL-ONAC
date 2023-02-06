package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.DrepturiTipDoc;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza persoana din v_persoana
 */
@XmlRootElement
public class PersoanaFizicaJuridica extends BaseModel{
    private Integer id;
    private String cnp;
    private String codCui;
    private String nume;
    private String prenume;
    private Integer idTipAct;
    private String tipAct;
    private String serieAct;
    private String nrAct;
    private String contact;
    private Integer idDepartament;
    private Integer idFunctie;
    private Integer idUtilizator;
    private Integer idUnitate;
    private Integer idTara;
    private String tara;
    private Integer idJudet;
    private String judet;
    private Integer idLocalitate;
    private String localitate;

    private String strada;
    private String nrStrada;
    private String bloc;
    private String scara;
    private String etaj;
    private String apartament;

    private String telefon;
    private String email;

    private String parola;
    private String confirmareParola;

    private String utilizator;
    private String persoana;
    private Integer status;
    private String numeComplet;
    private String usernameLDAP;
    private String departament;
    private String functie;
    private String estePersoanaFizica;
    private String rj;
    private String reprezentantLegal;
    private Integer idFisier;
    private Integer tipFormaOrganizare;
    private String token;
    private Integer idTert;
    private String username;

    private Integer idUnitateLogistica ;
    private String unitateLogistica ;
    private Integer nrOre ;
    private Integer normaLunara ;
    private Integer  hasTichete ;
    private String dataNastere ;
    private String nrPermisConducere;
    private String marca ;
    private String dataIn;
    private String dataOut;
    private Integer  idPersoana ;

    private String adresa;
    private String codPostal;
    private String numeSucursala;
    private String contBancar;
    private String fax;
    private String web;
    private String persoanaContact;
    private String domeniuActivitate;
    private String codCaen;
    private String banca;
    private String codIban;
    private String mama;
    private String tata;
    private String tipTert;
    private String numarAutorizatie;
    private String numeVechi;

    private Integer client  ;
    private Integer furnizor ;
    private Integer pfa ;
    private Integer platesteTva ;
    private String codFiscal;

    private String areParinte;
    private Integer idTertParinte;
    private String codCuiParinte;
    private String rjParinte;
    private Integer idTaraParinte;
    private String taraParinte;
    private Integer idJudetParinte;
    private String judetParinte;
    private Integer idLocalitateParinte;
    private String localitateParinte;
    private String adresaParinte;
    private String stradaParinte;
    private String nrStradaParinte;
    private String blocParinte;
    private String scaraParinte;
    private String etajParinte;
    private String apartamentParinte;
    private String telefonParinte;
    private String emailParinte;
    private String numeCompletParinte;
    private String estePersoanaFizicaParinte;
    private String reprezentantLegalParinte;
    private String codPostalParinte;

    private Integer idFiliala;
    private Integer idFilialaParinte;
    private Integer idJudetFiliala;
    private Integer idJudetFilialaParinte;
    private Integer idLocalitateFiliala;
    private Integer idLocalitateFilialaParinte;
    private String codRup;
    private String codRupParinte;
    private boolean domeniuEnergieElectrica;
    private boolean domeniuGazeNaturale;
    private boolean domeniuEnergieTermica;
    private List<DrepturiTipDoc> drepturiTipDoc;

    public Integer getIdFiliala() {
        return idFiliala;
    }

    public void setIdFiliala(Integer idFiliala) {
        this.idFiliala = idFiliala;
    }

    public Integer getIdFilialaParinte() {
        return idFilialaParinte;
    }

    public void setIdFilialaParinte(Integer idFilialaParinte) {
        this.idFilialaParinte = idFilialaParinte;
    }

    public Integer getIdJudetFiliala() {
        return idJudetFiliala;
    }

    public void setIdJudetFiliala(Integer idJudetFiliala) {
        this.idJudetFiliala = idJudetFiliala;
    }

    public Integer getIdJudetFilialaParinte() {
        return idJudetFilialaParinte;
    }

    public void setIdJudetFilialaParinte(Integer idJudetFilialaParinte) {
        this.idJudetFilialaParinte = idJudetFilialaParinte;
    }

    public Integer getIdLocalitateFiliala() {
        return idLocalitateFiliala;
    }

    public void setIdLocalitateFiliala(Integer idLocalitateFiliala) {
        this.idLocalitateFiliala = idLocalitateFiliala;
    }

    public Integer getIdLocalitateFilialaParinte() {
        return idLocalitateFilialaParinte;
    }

    public void setIdLocalitateFilialaParinte(Integer idLocalitateFilialaParinte) {
        this.idLocalitateFilialaParinte = idLocalitateFilialaParinte;
    }

    public String getCodRup() {
        return codRup;
    }

    public void setCodRup(String codRup) {
        this.codRup = codRup;
    }

    public String getCodRupParinte() {
        return codRupParinte;
    }

    public void setCodRupParinte(String codRupParinte) {
        this.codRupParinte = codRupParinte;
    }

    public String getCodPostalParinte() {
        return codPostalParinte;
    }

    public void setCodPostalParinte(String codPostalParinte) {
        this.codPostalParinte = codPostalParinte;
    }

    public String getCodFiscal() {
        return codFiscal;
    }

    public void setCodFiscal(String codFiscal) {
        this.codFiscal = codFiscal;
    }
    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(Integer furnizor) {
        this.furnizor = furnizor;
    }

    public Integer getPfa() {
        return pfa;
    }

    public void setPfa(Integer pfa) {
        this.pfa = pfa;
    }

    public Integer getPlatesteTva() {
        return platesteTva;
    }

    public void setPlatesteTva(Integer platesteTva) {
        this.platesteTva = platesteTva;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getNumeSucursala() {
        return numeSucursala;
    }

    public void setNumeSucursala(String numeSucursala) {
        this.numeSucursala = numeSucursala;
    }

    public String getContBancar() {
        return contBancar;
    }

    public void setContBancar(String contBancar) {
        this.contBancar = contBancar;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPersoanaContact() {
        return persoanaContact;
    }

    public void setPersoanaContact(String persoanaContact) {
        this.persoanaContact = persoanaContact;
    }

    public String getDomeniuActivitate() {
        return domeniuActivitate;
    }

    public void setDomeniuActivitate(String domeniuActivitate) {
        this.domeniuActivitate = domeniuActivitate;
    }

    public String getCodCaen() {
        return codCaen;
    }

    public void setCodCaen(String codCaen) {
        this.codCaen = codCaen;
    }

    public String getBanca() {
        return banca;
    }

    public void setBanca(String banca) {
        this.banca = banca;
    }

    public String getCodIban() {
        return codIban;
    }

    public void setCodIban(String codIban) {
        this.codIban = codIban;
    }

    public String getMama() {
        return mama;
    }

    public void setMama(String mama) {
        this.mama = mama;
    }

    public String getTata() {
        return tata;
    }

    public void setTata(String tata) {
        this.tata = tata;
    }

    public String getTipTert() {
        return tipTert;
    }

    public void setTipTert(String tipTert) {
        this.tipTert = tipTert;
    }

    public String getNumarAutorizatie() {
        return numarAutorizatie;
    }

    public void setNumarAutorizatie(String numarAutorizatie) {
        this.numarAutorizatie = numarAutorizatie;
    }

    public String getNumeVechi() {
        return numeVechi;
    }

    public void setNumeVechi(String numeVechi) {
        this.numeVechi = numeVechi;
    }

    public Integer getIdUnitateLogistica() {
        return idUnitateLogistica;
    }

    public void setIdUnitateLogistica(Integer idUnitateLogistica) {
        this.idUnitateLogistica = idUnitateLogistica;
    }

    public String getUnitateLogistica() {
        return unitateLogistica;
    }

    public void setUnitateLogistica(String unitateLogistica) {
        this.unitateLogistica = unitateLogistica;
    }

    public Integer getNrOre() {
        return nrOre;
    }

    public void setNrOre(Integer nrOre) {
        this.nrOre = nrOre;
    }

    public Integer getNormaLunara() {
        return normaLunara;
    }

    public void setNormaLunara(Integer normaLunara) {
        this.normaLunara = normaLunara;
    }

    public Integer getHasTichete() {
        return hasTichete;
    }

    public void setHasTichete(Integer hasTichete) {
        this.hasTichete = hasTichete;
    }

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
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

    public Integer getIdPersoana() {
        return idPersoana;
    }

    public void setIdPersoana(Integer idPersoana) {
        this.idPersoana = idPersoana;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getIdTert() {
        return idTert;
    }

    public void setIdTert(Integer idTert) {
        this.idTert = idTert;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public String getTipAct() {
        return tipAct;
    }

    public void setTipAct(String tipAct) {
        this.tipAct = tipAct;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getCodCui() {
        return codCui;
    }

    public void setCodCui(String codCui) {
        this.codCui = codCui;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getRj() {
        return rj;
    }

    public void setRj(String rj) {
        this.rj = rj;
    }

    public String getEstePersoanaFizica() {
        return estePersoanaFizica;
    }

    public void setEstePersoanaFizica(String estePersoanaFizica) {
        this.estePersoanaFizica = estePersoanaFizica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(Integer idDepartament) {
        this.idDepartament = idDepartament;
    }

    public Integer getIdFunctie() {
        return idFunctie;
    }

    public void setIdFunctie(Integer idFunctie) {
        this.idFunctie = idFunctie;
    }

    public Integer getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Integer idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public Integer getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(Integer idUnitate) {
        this.idUnitate = idUnitate;
    }

    public Integer getIdTipAct() {
        return idTipAct;
    }

    public void setIdTipAct(Integer idTipAct) {
        this.idTipAct = idTipAct;
    }

    public String getSerieAct() {
        return serieAct;
    }

    public void setSerieAct(String serieAct) {
        this.serieAct = serieAct;
    }

    public String getNrAct() {
        return nrAct;
    }

    public void setNrAct(String nrAct) {
        this.nrAct = nrAct;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(String utilizator) {
        this.utilizator = utilizator;
    }

    public String getPersoana() {
        return persoana;
    }

    public void setPersoana(String persoana) {
        this.persoana = persoana;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNumeComplet() {
        return numeComplet;
    }

    public void setNumeComplet(String numeComplet) {
        this.numeComplet = numeComplet;
    }

    public String getUsernameLDAP() {
        return usernameLDAP;
    }

    public void setUsernameLDAP(String usernameLDAP) {
        this.usernameLDAP = usernameLDAP;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public Integer getIdTara() {
        return idTara;
    }

    public void setIdTara(Integer idTara) {
        this.idTara = idTara;
    }

    public Integer getIdJudet() {
        return idJudet;
    }

    public void setIdJudet(Integer idJudet) {
        this.idJudet = idJudet;
    }

    public Integer getIdLocalitate() {
        return idLocalitate;
    }

    public void setIdLocalitate(Integer idLocalitate) {
        this.idLocalitate = idLocalitate;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNrStrada() {
        return nrStrada;
    }

    public void setNrStrada(String nrStrada) {
        this.nrStrada = nrStrada;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public String getScara() {
        return scara;
    }

    public void setScara(String scara) {
        this.scara = scara;
    }

    public String getEtaj() {
        return etaj;
    }

    public void setEtaj(String etaj) {
        this.etaj = etaj;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getConfirmareParola() {
        return confirmareParola;
    }

    public void setConfirmareParola(String confirmareParola) {
        this.confirmareParola = confirmareParola;
    }

    public Integer getTipFormaOrganizare() {
        return tipFormaOrganizare;
    }

    public void setTipFormaOrganizare(Integer tipFormaOrganizare) {
        this.tipFormaOrganizare = tipFormaOrganizare;
    }

    public String getReprezentantLegal() {
        return reprezentantLegal;
    }

    public void setReprezentantLegal(String reprezentantLegal) {
        this.reprezentantLegal = reprezentantLegal;
    }

    public Integer getIdTertParinte() {
        return idTertParinte;
    }

    public void setIdTertParinte(Integer idTertParinte) {
        this.idTertParinte = idTertParinte;
    }

    public String getCodCuiParinte() {
        return codCuiParinte;
    }

    public void setCodCuiParinte(String codCuiParinte) {
        this.codCuiParinte = codCuiParinte;
    }

    public String getRjParinte() {
        return rjParinte;
    }

    public void setRjParinte(String rjParinte) {
        this.rjParinte = rjParinte;
    }

    public Integer getIdTaraParinte() {
        return idTaraParinte;
    }

    public void setIdTaraParinte(Integer idTaraParinte) {
        this.idTaraParinte = idTaraParinte;
    }

    public String getTaraParinte() {
        return taraParinte;
    }

    public void setTaraParinte(String taraParinte) {
        this.taraParinte = taraParinte;
    }

    public Integer getIdJudetParinte() {
        return idJudetParinte;
    }

    public void setIdJudetParinte(Integer idJudetParinte) {
        this.idJudetParinte = idJudetParinte;
    }

    public String getJudetParinte() {
        return judetParinte;
    }

    public void setJudetParinte(String judetParinte) {
        this.judetParinte = judetParinte;
    }

    public Integer getIdLocalitateParinte() {
        return idLocalitateParinte;
    }

    public void setIdLocalitateParinte(Integer idLocalitateParinte) {
        this.idLocalitateParinte = idLocalitateParinte;
    }

    public String getLocalitateParinte() {
        return localitateParinte;
    }

    public void setLocalitateParinte(String localitateParinte) {
        this.localitateParinte = localitateParinte;
    }

    public String getStradaParinte() {
        return stradaParinte;
    }

    public void setStradaParinte(String stradaParinte) {
        this.stradaParinte = stradaParinte;
    }

    public String getNrStradaParinte() {
        return nrStradaParinte;
    }

    public void setNrStradaParinte(String nrStradaParinte) {
        this.nrStradaParinte = nrStradaParinte;
    }

    public String getBlocParinte() {
        return blocParinte;
    }

    public void setBlocParinte(String blocParinte) {
        this.blocParinte = blocParinte;
    }

    public String getScaraParinte() {
        return scaraParinte;
    }

    public void setScaraParinte(String scaraParinte) {
        this.scaraParinte = scaraParinte;
    }

    public String getEtajParinte() {
        return etajParinte;
    }

    public void setEtajParinte(String etajParinte) {
        this.etajParinte = etajParinte;
    }

    public String getApartamentParinte() {
        return apartamentParinte;
    }

    public void setApartamentParinte(String apartamentParinte) {
        this.apartamentParinte = apartamentParinte;
    }

    public String getTelefonParinte() {
        return telefonParinte;
    }

    public void setTelefonParinte(String telefonParinte) {
        this.telefonParinte = telefonParinte;
    }

    public String getEmailParinte() {
        return emailParinte;
    }

    public void setEmailParinte(String emailParinte) {
        this.emailParinte = emailParinte;
    }

    public String getNumeCompletParinte() {
        return numeCompletParinte;
    }

    public void setNumeCompletParinte(String numeCompletParinte) {
        this.numeCompletParinte = numeCompletParinte;
    }

    public String getEstePersoanaFizicaParinte() {
        return estePersoanaFizicaParinte;
    }

    public void setEstePersoanaFizicaParinte(String estePersoanaFizicaParinte) {
        this.estePersoanaFizicaParinte = estePersoanaFizicaParinte;
    }

    public String getReprezentantLegalParinte() {
        return reprezentantLegalParinte;
    }

    public void setReprezentantLegalParinte(String reprezentantLegalParinte) {
        this.reprezentantLegalParinte = reprezentantLegalParinte;
    }

    public String getAreParinte() {
        return areParinte;
    }

    public void setAreParinte(String areParinte) {
        this.areParinte = areParinte;
    }

    public String getAdresaParinte() {
        return adresaParinte;
    }

    public void setAdresaParinte(String adresaParinte) {
        this.adresaParinte = adresaParinte;
    }

    public boolean isDomeniuEnergieElectrica() {
        return domeniuEnergieElectrica;
    }

    public void setDomeniuEnergieElectrica(boolean domeniuEnergieElectrica) {
        this.domeniuEnergieElectrica = domeniuEnergieElectrica;
    }

    public boolean isDomeniuGazeNaturale() {
        return domeniuGazeNaturale;
    }

    public void setDomeniuGazeNaturale(boolean domeniuGazeNaturale) {
        this.domeniuGazeNaturale = domeniuGazeNaturale;
    }

    public boolean isDomeniuEnergieTermica() {
        return domeniuEnergieTermica;
    }

    public void setDomeniuEnergieTermica(boolean domeniuEnergieTermica) {
        this.domeniuEnergieTermica = domeniuEnergieTermica;
    }

    public List<DrepturiTipDoc> getDrepturiTipDoc() {
        return drepturiTipDoc;
    }

    public void setDrepturiTipDoc(List<DrepturiTipDoc> drepturiTipDoc) {
        this.drepturiTipDoc = drepturiTipDoc;
    }
}



