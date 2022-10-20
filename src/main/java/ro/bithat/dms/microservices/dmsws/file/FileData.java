package ro.bithat.dms.microservices.dmsws.file;

import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Info despre un file.
 */
@XmlRootElement
public class FileData extends BaseModel {
    private Integer id;
    private String id_template;
    private Integer fileId;
    private Integer idAttr;
    private String mode;
    private String nume;
    private String descriere;
    private Integer id_director;
    private Integer stare;
    private Integer versiune;
    private Date data_versiune;
    private Integer id_user;
    private String versiune_label;
    private String server_path;
    private String creat_de;
    private Date creat_la;
    private String modificat_de;
    private Date modificat_la;
    private Integer nr_inreg;
    private Date data_inreg;
    private String text;
    private Integer id_registru;
    private Integer id_unitate;
    private Integer parcel;
    private Integer dimensiune;
    private String note;
    private Integer semnat;
    private Integer id_workflow_status;
    private Integer zile_preaviz;
    private Integer shortcut_to_id_fisier;
    private Date data_expirare;
    private Integer ignora;
    private Integer zile_alerta;
    private Integer storage_location_id;
    private Integer page_count;
    private Integer id_document;
    private String cod_document;
    private List<TipDocument> tipDocumentList;
    private String base64File;
    private String downloadLinkFile;

    public String getDownloadLinkFile() {
        return downloadLinkFile;
    }

    public void setDownloadLinkFile(String downloadLinkFile) {
        this.downloadLinkFile = downloadLinkFile;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getId_director() {
        return id_director;
    }

    public void setId_director(Integer id_director) {
        this.id_director = id_director;
    }

    public Integer getStare() {
        return stare;
    }

    public void setStare(Integer stare) {
        this.stare = stare;
    }

    public Integer getVersiune() {
        return versiune;
    }

    public void setVersiune(Integer versiune) {
        this.versiune = versiune;
    }

    public Date getData_versiune() {
        return data_versiune;
    }

    public void setData_versiune(Date data_versiune) {
        this.data_versiune = data_versiune;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getVersiune_label() {
        return versiune_label;
    }

    public void setVersiune_label(String versiune_label) {
        this.versiune_label = versiune_label;
    }

    public String getServer_path() {
        return server_path;
    }

    public void setServer_path(String server_path) {
        this.server_path = server_path;
    }

    public String getCreat_de() {
        return creat_de;
    }

    public void setCreat_de(String creat_de) {
        this.creat_de = creat_de;
    }

    public Date getCreat_la() {
        return creat_la;
    }

    public void setCreat_la(Date creat_la) {
        this.creat_la = creat_la;
    }

    public String getModificat_de() {
        return modificat_de;
    }

    public void setModificat_de(String modificat_de) {
        this.modificat_de = modificat_de;
    }

    public Date getModificat_la() {
        return modificat_la;
    }

    public void setModificat_la(Date modificat_la) {
        this.modificat_la = modificat_la;
    }

    public Integer getNr_inreg() {
        return nr_inreg;
    }

    public void setNr_inreg(Integer nr_inreg) {
        this.nr_inreg = nr_inreg;
    }

    public Date getData_inreg() {
        return data_inreg;
    }

    public void setData_inreg(Date data_inreg) {
        this.data_inreg = data_inreg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId_registru() {
        return id_registru;
    }

    public void setId_registru(Integer id_registru) {
        this.id_registru = id_registru;
    }

    public Integer getId_unitate() {
        return id_unitate;
    }

    public void setId_unitate(Integer id_unitate) {
        this.id_unitate = id_unitate;
    }

    public Integer getParcel() {
        return parcel;
    }

    public void setParcel(Integer parcel) {
        this.parcel = parcel;
    }

    public Integer getDimensiune() {
        return dimensiune;
    }

    public void setDimensiune(Integer dimensiune) {
        this.dimensiune = dimensiune;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getSemnat() {
        return semnat;
    }

    public void setSemnat(Integer semnat) {
        this.semnat = semnat;
    }

    public Integer getId_workflow_status() {
        return id_workflow_status;
    }

    public void setId_workflow_status(Integer id_workflow_status) {
        this.id_workflow_status = id_workflow_status;
    }

    public Integer getZile_preaviz() {
        return zile_preaviz;
    }

    public void setZile_preaviz(Integer zile_preaviz) {
        this.zile_preaviz = zile_preaviz;
    }

    public Integer getShortcut_to_id_fisier() {
        return shortcut_to_id_fisier;
    }

    public void setShortcut_to_id_fisier(Integer shortcut_to_id_fisier) {
        this.shortcut_to_id_fisier = shortcut_to_id_fisier;
    }

    public Date getData_expirare() {
        return data_expirare;
    }

    public void setData_expirare(Date data_expirare) {
        this.data_expirare = data_expirare;
    }

    public Integer getIgnora() {
        return ignora;
    }

    public void setIgnora(Integer ignora) {
        this.ignora = ignora;
    }

    public Integer getZile_alerta() {
        return zile_alerta;
    }

    public void setZile_alerta(Integer zile_alerta) {
        this.zile_alerta = zile_alerta;
    }

    public Integer getStorage_location_id() {
        return storage_location_id;
    }

    public void setStorage_location_id(Integer storage_location_id) {
        this.storage_location_id = storage_location_id;
    }

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public Integer getId_document() {
        return id_document;
    }

    public void setId_document(Integer id_document) {
        this.id_document = id_document;
    }

    public String getCod_document() {
        return cod_document;
    }

    public void setCod_document(String cod_document) {
        this.cod_document = cod_document;
    }

    public List<TipDocument> getTipDocumentList() {
        return tipDocumentList;
    }

    public void setTipDocumentList(List<TipDocument> tipDocumentList) {
        this.tipDocumentList = tipDocumentList;
    }

    public Integer getIdAttr() {
        return idAttr;
    }

    public void setIdAttr(Integer idAttr) {
        this.idAttr = idAttr;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getId_template() {
        return id_template;
    }

    public void setId_template(String id_template) {
        this.id_template = id_template;
    }

    public String getBase64File() {
        return base64File;
    }

    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
