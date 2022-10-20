package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza un lov.
 */
@XmlRootElement
public class Fisier {
    private Integer id;
    private Integer id_director;
    private Integer id_unitate;
    private String nume;
    private String numeBaza;
    private String numeInainteDeBarcode;
    private String descriere;
    private String locatie_fizica;
    private Integer zile_alerta;
    private Integer page_count;
    private String barcode;
    private String barcode_type;
    private String path_fisa;
    private String extensie;
    private Integer versiune;
    private String blocat_de;
    private Integer id_document;
    private Integer id_gdpr_level;
    private String gdpr_level;
    private Integer id_document_referinta;
    private Integer id_role_doc_owner;
    private String role_doc_owner;
    private Integer id_role_doc_disp_owner;
    private String role_doc_disp_owner;
    private Integer id_nomenc_securitate;
    private String nomenc_securitate;
    private Integer id_workflow_status;
    private String workflow_status;
    private String document_referinta;
    private String document;
    private Boolean semnat;
    private String nr_inreg;
    private DateTime data_inreg;
    private String data_inreg_str;
    private DateTime data_arhivare;
    private String data_arhivare_str;
    private DateTime data_expirare;
    private String data_expirare_str;
    private DateTime data_disposal;
    private String data_disposal_str;
    private String status;
    private String creat_de;
    private DateTime creat_la;
    private String creat_la_str;
    private String modificat_de;
    private DateTime modificat_la;
    private String modificat_la_str;
    private String download_url;
    private String director_parinte;
    private String dimensiune;
    private Boolean original;
    private Boolean fisier_ae;
    private Boolean fisa_ae;
    private Boolean ignora;
    private Boolean lock_edit;
    private Document documentObj;

    public String getNumeInainteDeBarcodeSauNume(){
        String nume = null;
        if (getNumeInainteDeBarcode() != null){
            nume = getNumeInainteDeBarcode();
        } else {
            nume = getNume();
        }
        return nume;
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

    public Integer getVersiune() {
        return versiune;
    }

    public void setVersiune(Integer versiune) {
        this.versiune = versiune;
    }

    public String getBlocat_de() {
        return blocat_de;
    }

    public void setBlocat_de(String blocat_de) {
        this.blocat_de = blocat_de;
    }

    public Integer getId_document() {
        return id_document;
    }

    public void setId_document(Integer id_document) {
        this.id_document = id_document;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Boolean getSemnat() {
        return semnat;
    }

    public void setSemnat(Boolean semnat) {
        this.semnat = semnat;
    }

    public String getNr_inreg() {
        return nr_inreg;
    }

    public void setNr_inreg(String nr_inreg) {
        this.nr_inreg = nr_inreg;
    }

    public DateTime getData_inreg() {
        return data_inreg;
    }

    public void setData_inreg(DateTime data_inreg) {
        this.data_inreg = data_inreg;
    }

    public String getData_inreg_str() {
        return data_inreg_str;
    }

    public void setData_inreg_str(String data_inreg_str) {
        this.data_inreg_str = data_inreg_str;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreat_de() {
        return creat_de;
    }

    public void setCreat_de(String creat_de) {
        this.creat_de = creat_de;
    }

    public DateTime getCreat_la() {
        return creat_la;
    }

    public void setCreat_la(DateTime creat_la) {
        this.creat_la = creat_la;
    }

    public String getCreat_la_str() {
        return creat_la_str;
    }

    public void setCreat_la_str(String creat_la_str) {
        this.creat_la_str = creat_la_str;
    }

    public String getModificat_de() {
        return modificat_de;
    }

    public void setModificat_de(String modificat_de) {
        this.modificat_de = modificat_de;
    }

    public DateTime getModificat_la() {
        return modificat_la;
    }

    public void setModificat_la(DateTime modificat_la) {
        this.modificat_la = modificat_la;
    }

    public String getModificat_la_str() {
        return modificat_la_str;
    }

    public void setModificat_la_str(String modificat_la_str) {
        this.modificat_la_str = modificat_la_str;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getDirector_parinte() {
        return director_parinte;
    }

    public void setDirector_parinte(String director_parinte) {
        this.director_parinte = director_parinte;
    }

    public String getNumeBaza() {
        return numeBaza;
    }

    public void setNumeBaza(String numeBaza) {
        this.numeBaza = numeBaza;
    }

    public String getExtensie() {
        return extensie;
    }

    public void setExtensie(String extensie) {
        this.extensie = extensie;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getId_gdpr_level() {
        return id_gdpr_level;
    }

    public void setId_gdpr_level(Integer id_gdpr_level) {
        this.id_gdpr_level = id_gdpr_level;
    }

    public String getGdpr_level() {
        return gdpr_level;
    }

    public void setGdpr_level(String gdpr_level) {
        this.gdpr_level = gdpr_level;
    }

    public Integer getId_document_referinta() {
        return id_document_referinta;
    }

    public void setId_document_referinta(Integer id_document_referinta) {
        this.id_document_referinta = id_document_referinta;
    }

    public String getDocument_referinta() {
        return document_referinta;
    }

    public void setDocument_referinta(String document_referinta) {
        this.document_referinta = document_referinta;
    }

    public Integer getId_role_doc_owner() {
        return id_role_doc_owner;
    }

    public void setId_role_doc_owner(Integer id_role_doc_owner) {
        this.id_role_doc_owner = id_role_doc_owner;
    }

    public String getRole_doc_owner() {
        return role_doc_owner;
    }

    public void setRole_doc_owner(String role_doc_owner) {
        this.role_doc_owner = role_doc_owner;
    }

    public Integer getId_role_doc_disp_owner() {
        return id_role_doc_disp_owner;
    }

    public void setId_role_doc_disp_owner(Integer id_role_doc_disp_owner) {
        this.id_role_doc_disp_owner = id_role_doc_disp_owner;
    }

    public String getRole_doc_disp_owner() {
        return role_doc_disp_owner;
    }

    public void setRole_doc_disp_owner(String role_doc_disp_owner) {
        this.role_doc_disp_owner = role_doc_disp_owner;
    }

    public String getLocatie_fizica() {
        return locatie_fizica;
    }

    public void setLocatie_fizica(String locatie_fizica) {
        this.locatie_fizica = locatie_fizica;
    }

    public DateTime getData_arhivare() {
        return data_arhivare;
    }

    public void setData_arhivare(DateTime data_arhivare) {
        this.data_arhivare = data_arhivare;
    }

    public String getData_arhivare_str() {
        return data_arhivare_str;
    }

    public void setData_arhivare_str(String data_arhivare_str) {
        this.data_arhivare_str = data_arhivare_str;
    }

    public Integer getId_nomenc_securitate() {
        return id_nomenc_securitate;
    }

    public void setId_nomenc_securitate(Integer id_nomenc_securitate) {
        this.id_nomenc_securitate = id_nomenc_securitate;
    }

    public String getNomenc_securitate() {
        return nomenc_securitate;
    }

    public void setNomenc_securitate(String nomenc_securitate) {
        this.nomenc_securitate = nomenc_securitate;
    }

    public String getDimensiune() {
        return dimensiune;
    }

    public void setDimensiune(String dimensiune) {
        this.dimensiune = dimensiune;
    }

    public String getPath_fisa() {
        return path_fisa;
    }

    public void setPath_fisa(String path_fisa) {
        this.path_fisa = path_fisa;
    }

    public Boolean getOriginal() {
        return original;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public Boolean getFisier_ae() {
        return fisier_ae;
    }

    public void setFisier_ae(Boolean fisier_ae) {
        this.fisier_ae = fisier_ae;
    }

    public Boolean getFisa_ae() {
        return fisa_ae;
    }

    public void setFisa_ae(Boolean fisa_ae) {
        this.fisa_ae = fisa_ae;
    }

    public DateTime getData_expirare() {
        return data_expirare;
    }

    public void setData_expirare(DateTime data_expirare) {
        this.data_expirare = data_expirare;
    }

    public String getData_expirare_str() {
        return data_expirare_str;
    }

    public void setData_expirare_str(String data_expirare_str) {
        this.data_expirare_str = data_expirare_str;
    }

    public Integer getZile_alerta() {
        return zile_alerta;
    }

    public void setZile_alerta(Integer zile_alerta) {
        this.zile_alerta = zile_alerta;
    }

    public Boolean getIgnora() {
        return ignora;
    }

    public void setIgnora(Boolean ignora) {
        this.ignora = ignora;
    }

    public Boolean getLock_edit() {
        return lock_edit;
    }

    public void setLock_edit(Boolean lock_edit) {
        this.lock_edit = lock_edit;
    }

    public DateTime getData_disposal() {
        return data_disposal;
    }

    public void setData_disposal(DateTime data_disposal) {
        this.data_disposal = data_disposal;
    }

    public String getData_disposal_str() {
        return data_disposal_str;
    }

    public void setData_disposal_str(String data_disposal_str) {
        this.data_disposal_str = data_disposal_str;
    }

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode_type() {
        return barcode_type;
    }

    public void setBarcode_type(String barcode_type) {
        this.barcode_type = barcode_type;
    }

    public Integer getId_workflow_status() {
        return id_workflow_status;
    }

    public void setId_workflow_status(Integer id_workflow_status) {
        this.id_workflow_status = id_workflow_status;
    }

    public String getWorkflow_status() {
        return workflow_status;
    }

    public void setWorkflow_status(String workflow_status) {
        this.workflow_status = workflow_status;
    }

    public void setDocumentObj(Document documentObj) {
        this.documentObj = documentObj;
    }

    public Document getDocumentObj() {
        return documentObj;
    }

    public Integer getId_director() {
        return id_director;
    }

    public void setId_director(Integer id_director) {
        this.id_director = id_director;
    }

    public Integer getId_unitate() {
        return id_unitate;
    }

    public void setId_unitate(Integer id_unitate) {
        this.id_unitate = id_unitate;
    }

    public String getNumeInainteDeBarcode() {
        return numeInainteDeBarcode;
    }

    public void setNumeInainteDeBarcode(String numeInainteDeBarcode) {
        this.numeInainteDeBarcode = numeInainteDeBarcode;
    }
}
