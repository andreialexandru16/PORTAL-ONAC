package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Document extends BaseModel{
    private String DT_RowId;
    private Integer id;
    private String cod;
    private String denumire;
    private String descriere;
    private Integer id_tip_document;
    private Integer id_unitate;
    private String tip_document;
    private Integer activ;
    private String creat_de;
    private String creat_la;
    private String modificat_de;
    private String modificat_la;
    private Boolean barcode;
    private Boolean barcode_insert;

    private Integer Id;
    private Integer IdDocumentType;
    private String Code;
    private String Name;
    private Integer Position;
    private String NameFormula;
    private String DescriptionFormula;
    private Integer DocVariables;
    private Integer UnitId;
    private Integer BarCode;
    private String CategorieDoc;
    private String jspPage;
    private Integer idFolder;
    private String codCategorieDoc;
    private Boolean skipIntro;
    private String portalTextMessage;
    private boolean captcha;
    private String portalRedirectNoButton;

    public Document() {

    }
    public Document(String tipDoc, String clasaDoc) {

        this.denumire=tipDoc;
        this.CategorieDoc=clasaDoc;
    }

    public String getCodCategorieDoc() {
        return codCategorieDoc;
    }

    public void setCodCategorieDoc(String codCategorieDoc) {
        this.codCategorieDoc = codCategorieDoc;
    }
    // Alex M - 01.10.2019 - F61172 - adaugat tip document (optional) pentru pagina de template-uri din portal_e
    private TipDocument tipDocumentObj;

    // Alex M - 01.10.2019 - F61172 - adaugat fisier (optional) pentru pagina de template-uri din portal_e
    private Fisier fisierObj;

    public String getJspPage() {
        return jspPage;
    }

    public void setJspPage(String jspPage) {
        this.jspPage = jspPage;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getDT_RowId() {
        return DT_RowId;
    }

    public void setDT_RowId(String DT_RowId) {
        this.DT_RowId = DT_RowId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Integer getId_tip_document() {
        return id_tip_document;
    }

    public void setId_tip_document(Integer id_tip_document) {
        this.id_tip_document = id_tip_document;
    }

    public Integer getId_unitate() {
        return id_unitate;
    }

    public void setId_unitate(Integer id_unitate) {
        this.id_unitate = id_unitate;
    }

    public String getTip_document() {
        return tip_document;
    }

    public void setTip_document(String tip_document) {
        this.tip_document = tip_document;
    }

    public Integer getActiv() {
        return activ;
    }

    public void setActiv(Integer activ) {
        this.activ = activ;
    }

    public String getCreat_de() {
        return creat_de;
    }

    public void setCreat_de(String creat_de) {
        this.creat_de = creat_de;
    }

    public String getCreat_la() {
        return creat_la;
    }

    public void setCreat_la(String creat_la) {
        this.creat_la = creat_la;
    }

    public String getModificat_de() {
        return modificat_de;
    }

    public void setModificat_de(String modificat_de) {
        this.modificat_de = modificat_de;
    }

    public String getModificat_la() {
        return modificat_la;
    }

    public void setModificat_la(String modificat_la) {
        this.modificat_la = modificat_la;
    }

    public Boolean getBarcode() {
        return barcode;
    }

    public void setBarcode(Boolean barcode) {
        this.barcode = barcode;
    }

    public Boolean getBarcode_insert() {
        return barcode_insert;
    }

    public void setBarcode_insert(Boolean barcode_insert) {
        this.barcode_insert = barcode_insert;
    }

    public Integer getIdDocumentType() {
        return IdDocumentType;
    }

    public void setIdDocumentType(Integer idDocumentType) {
        IdDocumentType = idDocumentType;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPosition() {
        return Position;
    }

    public void setPosition(Integer position) {
        Position = position;
    }

    public String getNameFormula() {
        return NameFormula;
    }

    public void setNameFormula(String nameFormula) {
        NameFormula = nameFormula;
    }

    public String getDescriptionFormula() {
        return DescriptionFormula;
    }

    public void setDescriptionFormula(String descriptionFormula) {
        DescriptionFormula = descriptionFormula;
    }

    public Integer getDocVariables() {
        return DocVariables;
    }

    public void setDocVariables(Integer docVariables) {
        DocVariables = docVariables;
    }

    public Integer getUnitId() {
        return UnitId;
    }

    public void setUnitId(Integer unitId) {
        UnitId = unitId;
    }

    public Integer getBarCode() {
        return BarCode;
    }

    public void setBarCode(Integer barCode) {
        BarCode = barCode;
    }

    public String getCategorieDoc() {
        return CategorieDoc;
    }

    public void setCategorieDoc(String categorieDoc) {
        CategorieDoc = categorieDoc;
    }

    public Integer getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(Integer idFolder) {
        this.idFolder = idFolder;
    }

    public TipDocument getTipDocumentObj() {
        return tipDocumentObj;
    }

    public void setTipDocumentObj(TipDocument tipDocumentObj) {
        this.tipDocumentObj = tipDocumentObj;
    }

    public Fisier getFisierObj() {
        return fisierObj;
    }

    public void setFisierObj(Fisier fisierObj) {
        this.fisierObj = fisierObj;
    }

    public Boolean getSkipIntro() {
        return skipIntro;
    }

    public void setSkipIntro(Boolean skipIntro) {
        this.skipIntro = skipIntro;
    }

    public String getPortalTextMessage() {
        return portalTextMessage;
    }

    public void setPortalTextMessage(String portalTextMessage) {
        this.portalTextMessage = portalTextMessage;
    }

    public boolean isCaptcha() {
        return captcha;
    }

    public void setCaptcha(boolean captcha) {
        this.captcha = captcha;
    }

    public String getPortalRedirectNoButton() {
        return portalRedirectNoButton;
    }

    public void setPortalRedirectNoButton(String portalRedirectNoButton) {
        this.portalRedirectNoButton = portalRedirectNoButton;
    }
}
