package ro.bithat.dms.microservices.dmsws.file;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Modeleaza attribute link.
 */
@XmlRootElement
public class DmsTert extends BaseModel {
    private Integer id;
    private String nume;
    private String prenume;
    private String ordinDeNumire;
    private String temeiLegal;
    private Integer idTipNotar;
    private Integer idTipOrdin;
    private String  data_ordin_str;
    private String  dataExpirareResedinta;
    private String data_juramant_str;
    private String data_suspendare_str;
    private String data_incetare_suspendare_str;
    private String prenumeMama;
    private String prenumeTata;
    private String mentiuni;
    private String dataDecizie;
    //Expertiza
    private String onorariuDefinitiv;
    private String tipActNumire;
    private String dataNumirii;
    // private String numarAct;
    private String dataDepunereRaport;
    private String dataInregistrareExpertiza;
    private String dataRealizareRaport;
    private String resedinta;
    private String sediu;
    private String observatii;
    private String actJustificativ;

    private String medieString;



    public String getMentiuni() {
        return mentiuni;
    }

    public void setMentiuni(String mentiuni) {
        this.mentiuni = mentiuni;
    }

    private Integer idCurteApel;
    private String curteApel;
    private Integer idLimba;
    private Integer idJudecatorie;
    private Integer principal;
    private String data_nastere_str;
    private String data_certificat_deces;
    private String numarCertificatDeces;
    private String judecatorie;
    private String data_inregistrare_birou;
    private String denumire_birou;
    private String localitate_birou;
    private String tipnotar;
    private String judet;
    private String cameraNotarilor;
    private DateTime data_nastere;
    private DateTime dataOrdin;




    private String loc_nastere;
    private String domiciliu;
    private String activitate_anterioara;
    private String sesiune;
    private Double medie;
    private Integer nr;

    private String sufix;
    private String rj;
    private String codCui;
    private String cnp;
    private String serie_ci;
    private String nr_ci;
    private String data_expirare_buletin_str;
    private String unitateLogistica;
    private Integer stare;

    private String codFiscal;
    private String fax;
    private String adresa;
    private String adresa2;
    private String contBancar;
    private String email;
    private String persoanaContact;
    private String telefon;
    private String ordinDenumire;
    private Integer idLocalitate;
    private Integer idJudet;
    private Integer idTara;

    private Integer numar_ordin;
    private Integer id_tip_notar;
    private String nr_ordin;
    private String tip_ordin;
    private Integer id_tip_ordin;
    private Integer id_camera_not;
    private Integer id_judecatorie;

    private Integer id_status_candidat;
    private Integer id_sesiune;
    private Integer id_specializare;
    private String status_candidat;
    private String specializare;

    private String strada;
    private String numar_strada;
    private String bloc_strada;
    private String scara_strada;
    private String etaj_strada;
    private String apartament_strada;
    private Integer id_sediu;

    private String strada2;
    private String stradaAsociere;
    private String numar_strada2;
    private String numarAsociere;
    private String bloc_strada2;
    private String blocAsociere;
    private String scara_strada2;
    private String scaraAsociere;
    private String etaj_strada2;
    private String etajAsociere;
    private String apartament_strada2;
    private String apartamentAsociere;
    private Integer idLocalitate2;
    private Integer idLocalitateAsociere;
    private Integer idJudet2;
    private Integer idJudetAsociere;
    private String denumire_birou2;
    private String denumireBirouAsociere;
    private String data_inregistrare_birou2;
    private String dataInregistrareBirouAsociere;
    private String telefon2;
    private String telefonAsociere;
    private String fax2;
    private String faxAsociere;
    private String stare_inactivitate;
    private String data_inactivare;
    private String note_inactivare;
    private String judet2;
    private String judetAsociere;
    private String localitate_birou2;
    private String localitateAsociere;
    private String judecatorieAsociere;
    private String cameraNotarilorAsociere;



    private Integer id_asociere;
    private String data_activare_str;
    private String data_incetare_str;

    private Integer numar_posturi;
    private String act_valabil_de_la_str;
    private String act_valabil_pana_la_str;
    private String studii;



    private String nume_expertiza;
    private String numar_expertiza;
    private Integer id_dosar;
    private Double suma;
    private String nume_reclamant;
    private String nume_parat;
    private String motiv;
    private String depunator;
    private String chitanta;
    private String numar_raport;
    private String termen_realizare_raport_str;
    private String data_depunerii_raportului_str;
    private String data_inregistrare_expertiza_str;
    private Integer numit;
    private String organJudiciar;
    private Integer institutie_id_numit;

    private String nr_registratura_unica;
    private String data_inrg_registratura_unica_str;
    private String data_pierderii_calitatii_str;
    private String numar_omj;
    private String nume_vechi;
    private Integer id_disponibilitate_exc;
    private Integer id_motiv;
    private Integer id_forma_organizare;

    private String disponibilitate;

    private Integer id_calitate;

    private Boolean persoanaFizica;


    private String serie_autorizatie;
    private String numar_autorizatie;
    private String data_autorizatie_str;
    private List<String> listSpecializariMulticriteriala = new ArrayList<>();
    private List<String> listSpecializariMulticriterialaActive = new ArrayList<>();
    private Integer id_temei_legal_numire;
    private Integer suspendat;
    private Integer radiat;
    private String limbiAutorizate;

    private String persoana_actiune;
    private String numarHotarare;
    private String data_hotarare_str;
    private Integer idSanctiune;
    private String sanctiune;
    private Integer asociat;

    private String data_radiere_str;

    private Integer idStatusSanctiune;
    private String statusSanctiune;

    private Integer rezultat;

    private String status;

    private Integer numarLegitimatie;


    private Integer numarTotal;
    private Integer numarSuspendati;
    private Integer numarRadiati;
    private Integer numarActivi;
    private Integer numarInregistrareDocJust;


    private String note;

    private Integer idFisier;

    private String campModificat;

    private String numarOrdin;
    private Integer numarRaport;
    private Integer idResponsabil;
    private Integer idRezultat;

    private String responsabil;
    private String rezultat_str;

    private String data_act_str;
    private Integer numarAct;
    private String sanctiuneDefinitiva;
    private Integer idSex;
    private String sex;
    private Integer idCetatenie;
    private String cetatenie;

    private Integer numarMapaProf;
    private Integer id_temei;
    private String numarCertificatConstatator;
    private String dataEmitereCertificatConstatator;
    private Integer idJudetCertificatConstatator;
    private String info;


    public Integer getId_temei() {
        return id_temei;
    }

    public void setId_temei(Integer id_temei) {
        this.id_temei = id_temei;
    }

    public Integer getNumarMapaProf() {
        return numarMapaProf;
    }

    public void setNumarMapaProf(Integer numarMapaProf) {
        this.numarMapaProf = numarMapaProf;
    }

    public String getData_act_str() {
        return data_act_str;
    }

    public void setData_act_str(String data_act_str) {
        this.data_act_str = data_act_str;
    }

    public Integer getNumarAct() {
        return numarAct;
    }

    public void setNumarAct(Integer numarAct) {
        this.numarAct = numarAct;
    }

    public String getSanctiuneDefinitiva() {
        return sanctiuneDefinitiva;
    }

    public void setSanctiuneDefinitiva(String sanctiuneDefinitiva) {
        this.sanctiuneDefinitiva = sanctiuneDefinitiva;
    }

    public String getOnorariuDefinitiv() {
        return onorariuDefinitiv;
    }

    public void setOnorariuDefinitiv(String onorariuDefinitiv) {
        this.onorariuDefinitiv = onorariuDefinitiv;
    }

    public String getTipActNumire() {
        return tipActNumire;
    }

    public void setTipActNumire(String tipActNumire) {
        this.tipActNumire = tipActNumire;
    }

    public String getDataNumirii() {
        return dataNumirii;
    }

    public void setDataNumirii(String dataNumirii) {
        this.dataNumirii = dataNumirii;
    }

    public String getNumarOrdin() {
        return numarOrdin;
    }

    public void setNumarOrdin(String numarOrdin) {
        this.numarOrdin = numarOrdin;
    }

    public String getMedieString() {
        return medieString;
    }

    public void setMedieString(String medieString) {
        this.medieString = medieString;
    }

    //    public String getNumarAct() {
//        return numarAct;
//    }

//    public void setNumarAct(String numarAct) {
//        this.numarAct = numarAct;
//    }

    public String getDataDepunereRaport() {
        return dataDepunereRaport;
    }

    public void setDataDepunereRaport(String dataDepunereRaport) {
        this.dataDepunereRaport = dataDepunereRaport;
    }

    public String getDataInregistrareExpertiza() {
        return dataInregistrareExpertiza;
    }

    public void setDataInregistrareExpertiza(String dataInregistrareExpertiza) {
        this.dataInregistrareExpertiza = dataInregistrareExpertiza;
    }

    public String getDataDecizie() {
        return dataDecizie;
    }

    public void setDataDecizie(String dataDecizie) {
        this.dataDecizie = dataDecizie;
    }

    public Boolean getPersoanaFizica() {
        return persoanaFizica;
    }

    public void setPersoanaFizica(Boolean persoanaFizica) {
        this.persoanaFizica = persoanaFizica;
    }

    public String getDataRealizareRaport() {
        return dataRealizareRaport;
    }

    public void setDataRealizareRaport(String dataRealizareRaport) {
        this.dataRealizareRaport = dataRealizareRaport;
    }

    public Integer getIdRezultat() {
        return idRezultat;
    }

    public void setIdRezultat(Integer idRezultat) {
        this.idRezultat = idRezultat;
    }

    public String getRezultat_str() {
        return rezultat_str;
    }

    public void setRezultat_str(String rezultat_str) {
        this.rezultat_str = rezultat_str;
    }

    public String getResponsabil() {
        return responsabil;
    }

    public void setResponsabil(String responsabil) {
        this.responsabil = responsabil;
    }


    public Integer getNumarRaport() {
        return numarRaport;
    }

    public void setNumarRaport(Integer numarRaport) {
        this.numarRaport = numarRaport;
    }

    public Integer getIdResponsabil() {
        return idResponsabil;
    }

    public void setIdResponsabil(Integer idResponsabil) {
        this.idResponsabil = idResponsabil;
    }



    public String getCampModificat() {
        return campModificat;
    }

    public void setCampModificat(String campModificat) {
        this.campModificat = campModificat;
    }

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getNumarInregistrareDocJust() {
        return numarInregistrareDocJust;
    }

    public void setNumarInregistrareDocJust(Integer numarInregistrareDocJust) {
        this.numarInregistrareDocJust = numarInregistrareDocJust;
    }

    public Integer getNumarActivi() {
        return numarActivi;
    }

    public void setNumarActivi(Integer numarActivi) {
        this.numarActivi = numarActivi;
    }

    public Integer getNumarTotal() {
        return numarTotal;
    }

    public void setNumarTotal(Integer numarTotal) {
        this.numarTotal = numarTotal;
    }

    public Integer getNumarSuspendati() {
        return numarSuspendati;
    }

    public void setNumarSuspendati(Integer numarSuspendati) {
        this.numarSuspendati = numarSuspendati;
    }

    public Integer getNumarRadiati() {
        return numarRadiati;
    }

    public void setNumarRadiati(Integer numarRadiati) {
        this.numarRadiati = numarRadiati;
    }

    public List<String> getListSpecializariMulticriterialaActive() {
        return listSpecializariMulticriterialaActive;
    }

    public void setListSpecializariMulticriterialaActive(List<String> listSpecializariMulticriterialaActive) {
        this.listSpecializariMulticriterialaActive = listSpecializariMulticriterialaActive;
    }

    public Integer getNumarLegitimatie() {
        return numarLegitimatie;
    }

    public void setNumarLegitimatie(Integer numarLegitimatie) {
        this.numarLegitimatie = numarLegitimatie;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRezultat() {
        return rezultat;
    }

    public void setRezultat(Integer rezultat) {
        this.rezultat = rezultat;
    }


    public Integer getIdSanctiune() {
        return idSanctiune;
    }

    public void setIdSanctiune(Integer idSanctiune) {
        this.idSanctiune = idSanctiune;
    }

    public String getStatusSanctiune() {
        return statusSanctiune;
    }

    public void setStatusSanctiune(String statusSanctiune) {
        this.statusSanctiune = statusSanctiune;
    }

    public Integer getIdStatusSanctiune() {
        return idStatusSanctiune;
    }

    public void setIdStatusSanctiune(Integer idStatusSanctiune) {
        this.idStatusSanctiune = idStatusSanctiune;
    }





    public String getData_radiere_str() {
        return data_radiere_str;
    }

    public void setData_radiere_str(String data_radiere_str) {
        this.data_radiere_str = data_radiere_str;
    }


    public String getDataInregistrareBirouAsociere() {
        return dataInregistrareBirouAsociere;
    }

    public void setDataInregistrareBirouAsociere(String dataInregistrareBirouAsociere) {
        this.dataInregistrareBirouAsociere = dataInregistrareBirouAsociere;
    }

    public String getJudecatorieAsociere() {
        return judecatorieAsociere;
    }

    public void setJudecatorieAsociere(String judecatorieAsociere) {
        this.judecatorieAsociere = judecatorieAsociere;
    }

    public String getCameraNotarilorAsociere() {
        return cameraNotarilorAsociere;
    }

    public void setCameraNotarilorAsociere(String cameraNotarilorAsociere) {
        this.cameraNotarilorAsociere = cameraNotarilorAsociere;
    }

    public String getStradaAsociere() {
        return stradaAsociere;
    }

    public void setStradaAsociere(String stradaAsociere) {
        this.stradaAsociere = stradaAsociere;
    }

    public String getNumarAsociere() {
        return numarAsociere;
    }

    public void setNumarAsociere(String numarAsociere) {
        this.numarAsociere = numarAsociere;
    }

    public String getBlocAsociere() {
        return blocAsociere;
    }

    public void setBlocAsociere(String blocAsociere) {
        this.blocAsociere = blocAsociere;
    }

    public String getScaraAsociere() {
        return scaraAsociere;
    }

    public void setScaraAsociere(String scaraAsociere) {
        this.scaraAsociere = scaraAsociere;
    }

    public String getEtajAsociere() {
        return etajAsociere;
    }

    public void setEtajAsociere(String etajAsociere) {
        this.etajAsociere = etajAsociere;
    }

    public String getApartamentAsociere() {
        return apartamentAsociere;
    }

    public void setApartamentAsociere(String apartamentAsociere) {
        this.apartamentAsociere = apartamentAsociere;
    }

    public Integer getIdLocalitateAsociere() {
        return idLocalitateAsociere;
    }

    public void setIdLocalitateAsociere(Integer idLocalitateAsociere) {
        this.idLocalitateAsociere = idLocalitateAsociere;
    }

    public Integer getIdJudetAsociere() {
        return idJudetAsociere;
    }

    public void setIdJudetAsociere(Integer idJudetAsociere) {
        this.idJudetAsociere = idJudetAsociere;
    }

    public String getDenumireBirouAsociere() {
        return denumireBirouAsociere;
    }

    public void setDenumireBirouAsociere(String denumireBirouAsociere) {
        this.denumireBirouAsociere = denumireBirouAsociere;
    }

    public String getTelefonAsociere() {
        return telefonAsociere;
    }

    public void setTelefonAsociere(String telefonAsociere) {
        this.telefonAsociere = telefonAsociere;
    }

    public String getFaxAsociere() {
        return faxAsociere;
    }

    public void setFaxAsociere(String faxAsociere) {
        this.faxAsociere = faxAsociere;
    }

    public String getJudetAsociere() {
        return judetAsociere;
    }

    public void setJudetAsociere(String judetAsociere) {
        this.judetAsociere = judetAsociere;
    }

    public String getLocalitateAsociere() {
        return localitateAsociere;
    }

    public void setLocalitateAsociere(String localitateAsociere) {
        this.localitateAsociere = localitateAsociere;
    }

    public Integer getAsociat() {
        return asociat;
    }

    public void setAsociat(Integer asociat) {
        this.asociat = asociat;
    }

    public String getSanctiune() {
        return sanctiune;
    }

    public void setSanctiune(String sanctiune) {
        this.sanctiune = sanctiune;
    }

    public String getData_hotarare_str() {
        return data_hotarare_str;
    }

    public void setData_hotarare_str(String data_hotarare_str) {
        this.data_hotarare_str = data_hotarare_str;
    }

    public String getNumarHotarare() {
        return numarHotarare;
    }

    public void setNumarHotarare(String numarHotarare) {
        this.numarHotarare = numarHotarare;
    }
    public String getPersoana_actiune() {
        return persoana_actiune;
    }

    public void setPersoana_actiune(String persoana_actiune) {
        this.persoana_actiune = persoana_actiune;
    }

    public Integer getSuspendat() {
        return suspendat;
    }

    public void setSuspendat(Integer suspendat) {
        this.suspendat = suspendat;
    }

    public Integer getRadiat() {
        return radiat;
    }

    public void setRadiat(Integer radiat) {
        this.radiat = radiat;
    }

    public Integer getId_temei_legal_numire() {
        return id_temei_legal_numire;
    }

    public void setId_temei_legal_numire(Integer id_temei_legal_numire) {
        this.id_temei_legal_numire = id_temei_legal_numire;
    }
    private Integer numar_dosar;
    private Integer numar_registru_special;
    private String numarDosar;

    public String getNumarDosar() {
        return numarDosar;
    }

    public void setNumarDosar(String numarDosar) {
        this.numarDosar = numarDosar;
    }

    public Integer getNumar_dosar() {
        return numar_dosar;
    }

    public void setNumar_dosar(Integer numar_dosar) {
        this.numar_dosar = numar_dosar;
    }

    public Integer getNumar_registru_special() {
        return numar_registru_special;
    }

    public void setNumar_registru_special(Integer numar_registru_special) {
        this.numar_registru_special = numar_registru_special;
    }

    public String getNume_vechi() {
        return nume_vechi;
    }

    public void setNume_vechi(String nume_vechi) {
        this.nume_vechi = nume_vechi;
    }

    public Integer getId_forma_organizare() {
        return id_forma_organizare;
    }

    public void setId_forma_organizare(Integer id_forma_organizare) {
        this.id_forma_organizare = id_forma_organizare;
    }

    public List<String> getListSpecializariMulticriteriala() {
        return listSpecializariMulticriteriala;
    }

    public void setListSpecializariMulticriteriala(List<String> listSpecializariMulticriteriala) {
        this.listSpecializariMulticriteriala = listSpecializariMulticriteriala;
    }

    public String getSerie_autorizatie() {
        return serie_autorizatie;
    }

    public void setSerie_autorizatie(String serie_autorizatie) {
        this.serie_autorizatie = serie_autorizatie;
    }

    public String getNumar_autorizatie() {
        return numar_autorizatie;
    }

    public void setNumar_autorizatie(String numar_autorizatie) {
        this.numar_autorizatie = numar_autorizatie;
    }

    public String getData_autorizatie_str() {
        return data_autorizatie_str;
    }

    public void setData_autorizatie_str(String data_autorizatie_str) {
        this.data_autorizatie_str = data_autorizatie_str;
    }

    public Integer getId_calitate() {
        return id_calitate;
    }

    public void setId_calitate(Integer id_calitate) {
        this.id_calitate = id_calitate;
    }

    public String getDisponibilitate() {
        return disponibilitate;
    }

    public void setDisponibilitate(String disponibilitate) {
        this.disponibilitate = disponibilitate;
    }

    public String getNr_registratura_unica() {
        return nr_registratura_unica;
    }

    public void setNr_registratura_unica(String nr_registratura_unica) {
        this.nr_registratura_unica = nr_registratura_unica;
    }

    public String getData_inrg_registratura_unica_str() {
        return data_inrg_registratura_unica_str;
    }

    public void setData_inrg_registratura_unica_str(String data_inrg_registratura_unica_str) {
        this.data_inrg_registratura_unica_str = data_inrg_registratura_unica_str;
    }

    public String getData_pierderii_calitatii_str() {
        return data_pierderii_calitatii_str;
    }

    public void setData_pierderii_calitatii_str(String data_pierderii_calitatii_str) {
        this.data_pierderii_calitatii_str = data_pierderii_calitatii_str;
    }

    public String getNumar_omj() {
        return numar_omj;
    }

    public void setNumar_omj(String numar_omj) {
        this.numar_omj = numar_omj;
    }

    public Integer getId_disponibilitate_exc() {
        return id_disponibilitate_exc;
    }

    public void setId_disponibilitate_exc(Integer id_disponibilitate_exc) {
        this.id_disponibilitate_exc = id_disponibilitate_exc;
    }

    public Integer getId_motiv() {
        return id_motiv;
    }

    public void setId_motiv(Integer id_motiv) {
        this.id_motiv = id_motiv;
    }

    public String getNume_expertiza() {
        return nume_expertiza;
    }

    public void setNume_expertiza(String nume_expertiza) {
        this.nume_expertiza = nume_expertiza;
    }

    public String getNumar_expertiza() {
        return numar_expertiza;
    }

    public void setNumar_expertiza(String numar_expertiza) {
        this.numar_expertiza = numar_expertiza;
    }

    public Integer getId_dosar() {
        return id_dosar;
    }

    public void setId_dosar(Integer id_dosar) {
        this.id_dosar = id_dosar;
    }

    public Double getSuma() {
        return suma;
    }

    public void setSuma(Double suma) {
        this.suma = suma;
    }

    public String getNume_reclamant() {
        return nume_reclamant;
    }

    public void setNume_reclamant(String nume_reclamant) {
        this.nume_reclamant = nume_reclamant;
    }

    public String getNume_parat() {
        return nume_parat;
    }

    public void setNume_parat(String nume_parat) {
        this.nume_parat = nume_parat;
    }

    public String getMotiv() {
        return motiv;
    }

    public void setMotiv(String motiv) {
        this.motiv = motiv;
    }

    public String getDepunator() {
        return depunator;
    }

    public void setDepunator(String depunator) {
        this.depunator = depunator;
    }

    public String getChitanta() {
        return chitanta;
    }

    public void setChitanta(String chitanta) {
        this.chitanta = chitanta;
    }

    public String getNumar_raport() {
        return numar_raport;
    }

    public void setNumar_raport(String numar_raport) {
        this.numar_raport = numar_raport;
    }

    public String getTermen_realizare_raport_str() {
        return termen_realizare_raport_str;
    }

    public void setTermen_realizare_raport_str(String termen_realizare_raport_str) {
        this.termen_realizare_raport_str = termen_realizare_raport_str;
    }

    public String getData_depunerii_raportului_str() {
        return data_depunerii_raportului_str;
    }

    public void setData_depunerii_raportului_str(String data_depunerii_raportului_str) {
        this.data_depunerii_raportului_str = data_depunerii_raportului_str;
    }

    public String getData_inregistrare_expertiza_str() {
        return data_inregistrare_expertiza_str;
    }

    public void setData_inregistrare_expertiza_str(String data_inregistrare_expertiza_str) {
        this.data_inregistrare_expertiza_str = data_inregistrare_expertiza_str;
    }

    public Integer getNumit() {
        return numit;
    }

    public void setNumit(Integer numit) {
        this.numit = numit;
    }

    public String getOrganJudiciar() {
        return organJudiciar;
    }

    public void setOrganJudiciar(String organJudiciar) {
        this.organJudiciar = organJudiciar;
    }

    public Integer getInstitutie_id_numit() {
        return institutie_id_numit;
    }

    public void setInstitutie_id_numit(Integer institutie_id_numit) {
        this.institutie_id_numit = institutie_id_numit;
    }

    public String getStudii() {
        return studii;
    }

    public void setStudii(String studii) {
        this.studii = studii;
    }

    public String getAct_valabil_de_la_str() {
        return act_valabil_de_la_str;
    }

    public void setAct_valabil_de_la_str(String act_valabil_de_la_str) {
        this.act_valabil_de_la_str = act_valabil_de_la_str;
    }

    public String getAct_valabil_pana_la_str() {
        return act_valabil_pana_la_str;
    }

    public void setAct_valabil_pana_la_str(String act_valabil_pana_la_str) {
        this.act_valabil_pana_la_str = act_valabil_pana_la_str;
    }

    public Integer getNumar_posturi() {
        return numar_posturi;
    }

    public void setNumar_posturi(Integer numar_posturi) {
        this.numar_posturi = numar_posturi;
    }




    public Integer getId_asociere() {
        return id_asociere;
    }

    public void setId_asociere(Integer id_asociere) {
        this.id_asociere = id_asociere;
    }

    public String getData_activare_str() {
        return data_activare_str;
    }

    public void setData_activare_str(String data_activare_str) {
        this.data_activare_str = data_activare_str;
    }

    public String getData_incetare_str() {
        return data_incetare_str;
    }

    public void setData_incetare_str(String data_incetare_str) {
        this.data_incetare_str = data_incetare_str;
    }

    public String getData_expirare_buletin_str() {
        return data_expirare_buletin_str;
    }

    public void setData_expirare_buletin_str(String data_expirare_buletin_str) {
        this.data_expirare_buletin_str = data_expirare_buletin_str;
    }

    public String getData_certificat_deces() {
        return data_certificat_deces;
    }

    public void setData_certificat_deces(String data_certificat_deces) {
        this.data_certificat_deces = data_certificat_deces;
    }

    public String getNumarCertificatDeces() {
        return numarCertificatDeces;
    }

    public void setNumarCertificatDeces(String numarCertificatDeces) {
        this.numarCertificatDeces = numarCertificatDeces;
    }

    public String getData_suspendare_str() {
        return data_suspendare_str;
    }

    public void setData_suspendare_str(String data_suspendare_str) {
        this.data_suspendare_str = data_suspendare_str;
    }

    public String getData_incetare_suspendare_str() {
        return data_incetare_suspendare_str;
    }

    public void setData_incetare_suspendare_str(String data_incetare_suspendare_str) {
        this.data_incetare_suspendare_str = data_incetare_suspendare_str;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNumar_strada() {
        return numar_strada;
    }

    public void setNumar_strada(String numar_strada) {
        this.numar_strada = numar_strada;
    }

    public String getBloc_strada() {
        return bloc_strada;
    }

    public void setBloc_strada(String bloc_strada) {
        this.bloc_strada = bloc_strada;
    }

    public String getScara_strada() {
        return scara_strada;
    }

    public void setScara_strada(String scara_strada) {
        this.scara_strada = scara_strada;
    }

    public String getEtaj_strada() {
        return etaj_strada;
    }

    public void setEtaj_strada(String etaj_strada) {
        this.etaj_strada = etaj_strada;
    }

    public String getApartament_strada() {
        return apartament_strada;
    }

    public void setApartament_strada(String apartament_strada) {
        this.apartament_strada = apartament_strada;
    }

    private List<String> listIdLimbiAutorizate = new ArrayList<>();
    private List<Integer> listIdTert = new ArrayList<>();

    public List<Integer> getListIdTert() {
        return listIdTert;
    }

    public void setListIdTert(List<Integer> listIdTert) {
        this.listIdTert = listIdTert;
    }

    public String getStatus_candidat() {
        return status_candidat;
    }

    public void setStatus_candidat(String status_candidat) {
        this.status_candidat = status_candidat;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public Integer getId_specializare() {
        return id_specializare;
    }

    public void setId_specializare(Integer id_specializare) {
        this.id_specializare = id_specializare;
    }

    public Integer getId_status_candidat() {
        return id_status_candidat;
    }

    public void setId_status_candidat(Integer id_status_candidat) {
        this.id_status_candidat = id_status_candidat;
    }

    public Integer getId_sesiune() {
        return id_sesiune;
    }

    public void setId_sesiune(Integer id_sesiune) {
        this.id_sesiune = id_sesiune;
    }

    public Integer getId_judecatorie() {
        return id_judecatorie;
    }

    public void setId_judecatorie(Integer id_judecatorie) {
        this.id_judecatorie = id_judecatorie;
    }

    public Integer getId_camera_not() {
        return id_camera_not;
    }

    public void setId_camera_not(Integer id_camera_not) {
        this.id_camera_not = id_camera_not;
    }

    public Integer getId_tip_ordin() {
        return id_tip_ordin;
    }

    public void setId_tip_ordin(Integer id_tip_ordin) {
        this.id_tip_ordin = id_tip_ordin;
    }

    public String getOrdinDenumire() {
        return ordinDenumire;
    }

    public void setOrdinDenumire(String ordinDenumire) {
        this.ordinDenumire = ordinDenumire;
    }

    public Integer getId_tip_notar() {
        return id_tip_notar;
    }

    public void setId_tip_notar(Integer id_tip_notar) {
        this.id_tip_notar = id_tip_notar;
    }

    public DateTime getDataOrdin() {
        return dataOrdin;
    }

    public void setDataOrdin(DateTime dataOrdin) {
        this.dataOrdin = dataOrdin;
    }

    public String getTip_ordin() {
        return tip_ordin;
    }

    public void setTip_ordin(String tip_ordin) {
        this.tip_ordin = tip_ordin;
    }

    public String getNr_ordin() {
        return nr_ordin;
    }

    public void setNr_ordin(String nr_ordin) {
        this.nr_ordin = nr_ordin;
    }

    public Integer getPrincipal() {
        return principal;
    }

    public void setPrincipal(Integer principal) {
        this.principal = principal;
    }

    public String getJudecatorie() {
        return judecatorie;
    }

    public void setJudecatorie(String judecatorie) {
        this.judecatorie = judecatorie;
    }

    public String getCameraNotarilor() {
        return cameraNotarilor;
    }

    public void setCameraNotarilor(String cameraNotarilor) {
        this.cameraNotarilor = cameraNotarilor;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getLocalitate_birou() {
        return localitate_birou;
    }

    public void setLocalitate_birou(String localitate_birou) {
        this.localitate_birou = localitate_birou;
    }

    public DateTime getData_nastere() {
        return data_nastere;
    }

    public void setData_nastere(DateTime data_nastere) {
        this.data_nastere = data_nastere;
    }

    public String getTipnotar() {
        return tipnotar;
    }

    public void setTipnotar(String tipnotar) {
        this.tipnotar = tipnotar;
    }

    public Integer getIdTipOrdin() {
        return idTipOrdin;
    }

    public void setIdTipOrdin(Integer idTipOrdin) {
        this.idTipOrdin = idTipOrdin;
    }

    public Integer getNumar_ordin() {
        return numar_ordin;
    }

    public void setNumar_ordin(Integer numar_ordin) {
        this.numar_ordin = numar_ordin;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getData_inregistrare_birou() {
        return data_inregistrare_birou;
    }

    public void setData_inregistrare_birou(String data_inregistrare_birou) {
        this.data_inregistrare_birou = data_inregistrare_birou;
    }

    public String getDenumire_birou() {
        return denumire_birou;
    }

    public void setDenumire_birou(String denumire_birou) {
        this.denumire_birou = denumire_birou;
    }
    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getOrdinDeNumire() {
        return ordinDeNumire;
    }

    public void setOrdinDeNumire(String ordinDeNumire) {
        this.ordinDeNumire = ordinDeNumire;
    }

    public String getTemeiLegal() {
        return temeiLegal;
    }

    public void setTemeiLegal(String temeiLegal) {
        this.temeiLegal = temeiLegal;
    }

    public Integer getIdTipNotar() {
        return idTipNotar;
    }

    public void setIdTipNotar(Integer idTipNotar) {
        this.idTipNotar = idTipNotar;
    }

    public String getData_ordin_str() {
        return data_ordin_str;
    }

    public void setData_ordin_str(String data_ordin_str) {
        this.data_ordin_str = data_ordin_str;
    }

    public String getData_juramant_str() {
        return data_juramant_str;
    }

    public void setData_juramant_str(String data_juramant_str) {
        this.data_juramant_str = data_juramant_str;
    }

    public Integer getIdCurteApel() {
        return idCurteApel;
    }

    public void setIdCurteApel(Integer idCurteApel) {
        this.idCurteApel = idCurteApel;
    }

    public Integer getIdJudecatorie() {
        return idJudecatorie;
    }

    public void setIdJudecatorie(Integer idJudecatorie) {
        this.idJudecatorie = idJudecatorie;
    }

    public String getData_nastere_str() {
        return data_nastere_str;
    }

    public void setData_nastere_str(String data_nastere_str) {
        this.data_nastere_str = data_nastere_str;
    }

    public String getLoc_nastere() {
        return loc_nastere;
    }

    public void setLoc_nastere(String loc_nastere) {
        this.loc_nastere = loc_nastere;
    }

    public String getDomiciliu() {
        return domiciliu;
    }

    public void setDomiciliu(String domiciliu) {
        this.domiciliu = domiciliu;
    }

    public String getActivitate_anterioara() {
        return activitate_anterioara;
    }

    public void setActivitate_anterioara(String activitate_anterioara) {
        this.activitate_anterioara = activitate_anterioara;
    }

    public String getSesiune() {
        return sesiune;
    }

    public void setSesiune(String sesiune) {
        this.sesiune = sesiune;
    }

    public Double getMedie() {
        return medie;
    }

    public void setMedie(Double medie) {
        this.medie = medie;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
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

    public String getSufix() {
        return sufix;
    }

    public void setSufix(String sufix) {
        this.sufix = sufix;
    }

    public String getRj() {
        return rj;
    }

    public void setRj(String rj) {
        this.rj = rj;
    }

    public String getCodCui() {
        return codCui;
    }

    public void setCodCui(String codCui) {
        this.codCui = codCui;
    }

    public Integer getStare() {
        return stare;
    }

    public void setStare(Integer stare) {
        this.stare = stare;
    }

    public String getUnitateLogistica() {
        return unitateLogistica;
    }

    public void setUnitateLogistica(String unitateLogistica) {
        this.unitateLogistica = unitateLogistica;
    }

    public String getCodFiscal() {
        return codFiscal;
    }

    public void setCodFiscal(String codFiscal) {
        this.codFiscal = codFiscal;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getAdresa2() {
        return adresa2;
    }

    public void setAdresa2(String adresa2) {
        this.adresa2 = adresa2;
    }

    public String getContBancar() {
        return contBancar;
    }

    public void setContBancar(String contBancar) {
        this.contBancar = contBancar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersoanaContact() {
        return persoanaContact;
    }

    public void setPersoanaContact(String persoanaContact) {
        this.persoanaContact = persoanaContact;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Integer getIdLocalitate() {
        return idLocalitate;
    }

    public void setIdLocalitate(Integer idLocalitate) {
        this.idLocalitate = idLocalitate;
    }

    public Integer getIdJudet() {
        return idJudet;
    }

    public void setIdJudet(Integer idJudet) {
        this.idJudet = idJudet;
    }

    public Integer getIdTara() {
        return idTara;
    }

    public void setIdTara(Integer idTara) {
        this.idTara = idTara;
    }

    public String getSerie_ci() {
        return serie_ci;
    }

    public void setSerie_ci(String serie_ci) {
        this.serie_ci = serie_ci;
    }

    public String getNr_ci() {
        return nr_ci;
    }

    public void setNr_ci(String nr_ci) {
        this.nr_ci = nr_ci;
    }

    public List<String> getListIdLimbiAutorizate() {
        return listIdLimbiAutorizate;
    }

    public void setListIdLimbiAutorizate(List<String> listIdLimbiAutorizate) {
        this.listIdLimbiAutorizate = listIdLimbiAutorizate;
    }

    public Integer getId_sediu() {
        return id_sediu;
    }

    public void setId_sediu(Integer id_sediu) {
        this.id_sediu = id_sediu;
    }

    public String getStrada2() {
        return strada2;
    }

    public void setStrada2(String strada2) {
        this.strada2 = strada2;
    }

    public String getNumar_strada2() {
        return numar_strada2;
    }

    public void setNumar_strada2(String numar_strada2) {
        this.numar_strada2 = numar_strada2;
    }

    public String getBloc_strada2() {
        return bloc_strada2;
    }

    public void setBloc_strada2(String bloc_strada2) {
        this.bloc_strada2 = bloc_strada2;
    }

    public String getScara_strada2() {
        return scara_strada2;
    }

    public void setScara_strada2(String scara_strada2) {
        this.scara_strada2 = scara_strada2;
    }

    public String getEtaj_strada2() {
        return etaj_strada2;
    }

    public void setEtaj_strada2(String etaj_strada2) {
        this.etaj_strada2 = etaj_strada2;
    }

    public String getApartament_strada2() {
        return apartament_strada2;
    }

    public void setApartament_strada2(String apartament_strada2) {
        this.apartament_strada2 = apartament_strada2;
    }

    public Integer getIdLocalitate2() {
        return idLocalitate2;
    }

    public void setIdLocalitate2(Integer idLocalitate2) {
        this.idLocalitate2 = idLocalitate2;
    }

    public Integer getIdJudet2() {
        return idJudet2;
    }

    public void setIdJudet2(Integer idJudet2) {
        this.idJudet2 = idJudet2;
    }

    public String getDenumire_birou2() {
        return denumire_birou2;
    }

    public void setDenumire_birou2(String denumire_birou2) {
        this.denumire_birou2 = denumire_birou2;
    }

    public String getData_inregistrare_birou2() {
        return data_inregistrare_birou2;
    }

    public void setData_inregistrare_birou2(String data_inregistrare_birou2) {
        this.data_inregistrare_birou2 = data_inregistrare_birou2;
    }

    public String getTelefon2() {
        return telefon2;
    }

    public void setTelefon2(String telefon2) {
        this.telefon2 = telefon2;
    }

    public String getFax2() {
        return fax2;
    }

    public void setFax2(String fax2) {
        this.fax2 = fax2;
    }

    public String getStare_inactivitate() {
        return stare_inactivitate;
    }

    public void setStare_inactivitate(String stare_inactivitate) {
        this.stare_inactivitate = stare_inactivitate;
    }

    public String getData_inactivare() {
        return data_inactivare;
    }

    public void setData_inactivare(String data_inactivare) {
        this.data_inactivare = data_inactivare;
    }

    public String getNote_inactivare() {
        return note_inactivare;
    }

    public void setNote_inactivare(String note_inactivare) {
        this.note_inactivare = note_inactivare;
    }

    public String getJudet2() {
        return judet2;
    }

    public void setJudet2(String judet2) {
        this.judet2 = judet2;
    }

    public String getLocalitate_birou2() {
        return localitate_birou2;
    }

    public void setLocalitate_birou2(String localitate_birou2) {
        this.localitate_birou2 = localitate_birou2;
    }

    public String getPrenumeMama() {
        return prenumeMama;
    }

    public void setPrenumeMama(String prenumeMama) {
        this.prenumeMama = prenumeMama;
    }

    public String getPrenumeTata() {
        return prenumeTata;
    }

    public void setPrenumeTata(String prenumeTata) {
        this.prenumeTata = prenumeTata;
    }

    public Integer getIdSex() {
        return idSex;
    }

    public void setIdSex(Integer idSex) {
        this.idSex = idSex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getIdCetatenie() {
        return idCetatenie;
    }

    public void setIdCetatenie(Integer idCetatenie) {
        this.idCetatenie = idCetatenie;
    }

    public String getCetatenie() {
        return cetatenie;
    }

    public void setCetatenie(String cetatenie) {
        this.cetatenie = cetatenie;
    }

    public String getLimbiAutorizate() {
        return limbiAutorizate;
    }

    public void setLimbiAutorizate(String limbiAutorizate) {
        this.limbiAutorizate = limbiAutorizate;
    }

    public Integer getIdLimba() {
        return idLimba;
    }

    public void setIdLimba(Integer idLimba) {
        this.idLimba = idLimba;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public String getCurteApel() {
        return curteApel;
    }

    public void setCurteApel(String curteApel) {
        this.curteApel = curteApel;
    }

    public String getResedinta() {
        return resedinta;
    }

    public void setResedinta(String resedinta) {
        this.resedinta = resedinta;
    }

    public String getSediu() {
        return sediu;
    }

    public void setSediu(String sediu) {
        this.sediu = sediu;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    public String getActJustificativ() {
        return actJustificativ;
    }

    public void setActJustificativ(String actJustificativ) {
        this.actJustificativ = actJustificativ;
    }

    public String getDataExpirareResedinta() {
        return dataExpirareResedinta;
    }

    public void setDataExpirareResedinta(String dataExpirareResedinta) {
        this.dataExpirareResedinta = dataExpirareResedinta;
    }

    public String getNumarCertificatConstatator() {
        return numarCertificatConstatator;
    }

    public void setNumarCertificatConstatator(String numarCertificatConstatator) {
        this.numarCertificatConstatator = numarCertificatConstatator;
    }

    public String getDataEmitereCertificatConstatator() {
        return dataEmitereCertificatConstatator;
    }

    public void setDataEmitereCertificatConstatator(String dataEmitereCertificatConstatator) {
        this.dataEmitereCertificatConstatator = dataEmitereCertificatConstatator;
    }

    public Integer getIdJudetCertificatConstatator() {
        return idJudetCertificatConstatator;
    }

    public void setIdJudetCertificatConstatator(Integer idJudetCertificatConstatator) {
        this.idJudetCertificatConstatator = idJudetCertificatConstatator;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }
}
