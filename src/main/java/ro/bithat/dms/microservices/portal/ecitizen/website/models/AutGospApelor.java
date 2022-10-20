package ro.bithat.dms.microservices.portal.ecitizen.website.models;


public class AutGospApelor {
    private Integer id;
    private Integer id_tert;
    private Integer nr_inreg_emitent;
    private String data_inreg_emitent;
    private String emitent;
    private Integer numar;
    private String data_emiterii;
    private String data_expirarii;
    private String judet;
    private String localitate_principala;
    private String localitate_apartinatoare;
    private String adresa;
    private String obiectul_autorizatiei;
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

    public String getEmitent() {
        return emitent;
    }

    public void setEmitent(String emitent) {
        this.emitent = emitent;
    }

    public Integer getNumar() {
        return numar;
    }

    public void setNumar(Integer numar) {
        this.numar = numar;
    }

    public String getData_emiterii() {
        return data_emiterii;
    }

    public void setData_emiterii(String data_emiterii) {
        this.data_emiterii = data_emiterii;
    }

    public String getData_expirarii() {
        return data_expirarii;
    }

    public void setData_expirarii(String data_expirarii) {
        this.data_expirarii = data_expirarii;
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

    public String getObiectul_autorizatiei() {
        return obiectul_autorizatiei;
    }

    public void setObiectul_autorizatiei(String obiectul_autorizatiei) {
        this.obiectul_autorizatiei = obiectul_autorizatiei;
    }
}