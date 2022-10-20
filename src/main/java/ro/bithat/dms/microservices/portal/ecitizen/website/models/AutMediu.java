package ro.bithat.dms.microservices.portal.ecitizen.website.models;


public class AutMediu {
    private Integer id;
    private Integer id_tert;
    private Integer nr_inreg_emitent;
    private String data_inreg_emitent;
    private String aut_emitent;
    private Integer aut_numar;
    private String aut_data_emiterii;
    private String aut_data_expirarii;
    private String judet;
    private String localitate_principala;
    private String localitate_apartinatoare;
    private String adresa;
    private String denumire;
    private String dec_emitent;
    private Integer dec_numar;
    private String dec_data_emiterii;
    private String perioada_de_valabilitate;
    private String dec_data_expirarii;
    private Integer id_fisier;
    private String downloadLink;
    private String nume_fisier;
    private boolean showDownloadBtn;
    private boolean showSaveBtn;
    private boolean showCheckMark;
    private boolean showDeleteBtn;
    private boolean showUploadBtn;

    public Integer getId_fisier() {
        return id_fisier;
    }

    public void setId_fisier(Integer id_fisier) {
        this.id_fisier = id_fisier;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getNume_fisier() {
        return nume_fisier;
    }

    public void setNume_fisier(String nume_fisier) {
        this.nume_fisier = nume_fisier;
    }

    public boolean isShowDownloadBtn() {
        return showDownloadBtn;
    }

    public void setShowDownloadBtn(boolean showDownloadBtn) {
        this.showDownloadBtn = showDownloadBtn;
    }

    public boolean isShowSaveBtn() {
        return showSaveBtn;
    }

    public void setShowSaveBtn(boolean showSaveBtn) {
        this.showSaveBtn = showSaveBtn;
    }

    public boolean isShowCheckMark() {
        return showCheckMark;
    }

    public void setShowCheckMark(boolean showCheckMark) {
        this.showCheckMark = showCheckMark;
    }

    public boolean isShowDeleteBtn() {
        return showDeleteBtn;
    }

    public void setShowDeleteBtn(boolean showDeleteBtn) {
        this.showDeleteBtn = showDeleteBtn;
    }

    public boolean isShowUploadBtn() {
        return showUploadBtn;
    }

    public void setShowUploadBtn(boolean showUploadBtn) {
        this.showUploadBtn = showUploadBtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_tert() {
        return id_tert;
    }

    public void setId_tert(Integer id_tert) {
        this.id_tert = id_tert;
    }

    public Integer getNr_inreg_emitent() {
        return nr_inreg_emitent;
    }

    public void setNr_inreg_emitent(Integer nr_inreg_emitent) {
        this.nr_inreg_emitent = nr_inreg_emitent;
    }

    public String getData_inreg_emitent() {
        return data_inreg_emitent;
    }

    public void setData_inreg_emitent(String data_inreg_emitent) {
        this.data_inreg_emitent = data_inreg_emitent;
    }

    public String getAut_emitent() {
        return aut_emitent;
    }

    public void setAut_emitent(String aut_emitent) {
        this.aut_emitent = aut_emitent;
    }

    public Integer getAut_numar() {
        return aut_numar;
    }

    public void setAut_numar(Integer aut_numar) {
        this.aut_numar = aut_numar;
    }

    public String getAut_data_emiterii() {
        return aut_data_emiterii;
    }

    public void setAut_data_emiterii(String aut_data_emiterii) {
        this.aut_data_emiterii = aut_data_emiterii;
    }

    public String getAut_data_expirarii() {
        return aut_data_expirarii;
    }

    public void setAut_data_expirarii(String aut_data_expirarii) {
        this.aut_data_expirarii = aut_data_expirarii;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getLocalitate_principala() {
        return localitate_principala;
    }

    public void setLocalitate_principala(String localitate_principala) {
        this.localitate_principala = localitate_principala;
    }

    public String getLocalitate_apartinatoare() {
        return localitate_apartinatoare;
    }

    public void setLocalitate_apartinatoare(String localitate_apartinatoare) {
        this.localitate_apartinatoare = localitate_apartinatoare;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDec_emitent() {
        return dec_emitent;
    }

    public void setDec_emitent(String dec_emitent) {
        this.dec_emitent = dec_emitent;
    }

    public Integer getDec_numar() {
        return dec_numar;
    }

    public void setDec_numar(Integer dec_numar) {
        this.dec_numar = dec_numar;
    }

    public String getDec_data_emiterii() {
        return dec_data_emiterii;
    }

    public void setDec_data_emiterii(String dec_data_emiterii) {
        this.dec_data_emiterii = dec_data_emiterii;
    }

    public String getPerioada_de_valabilitate() {
        return perioada_de_valabilitate;
    }

    public void setPerioada_de_valabilitate(String perioada_de_valabilitate) {
        this.perioada_de_valabilitate = perioada_de_valabilitate;
    }

    public String getDec_data_expirarii() {
        return dec_data_expirarii;
    }

    public void setDec_data_expirarii(String dec_data_expirarii) {
        this.dec_data_expirarii = dec_data_expirarii;
    }
}