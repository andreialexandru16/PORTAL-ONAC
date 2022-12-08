package ro.bithat.dms.microservices.dmsws.file;



import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Silviu Iancu on 5/11/2018.
 */
@XmlRootElement
public class PortalFile extends BaseModel{
    private Integer id;
    private String nume;
    private String descriere;
    private Integer idDirector;
    private Integer idFisierReferinta;
    private String downloadLinkFisierReferinta;
    private String numeFisierReferinta;
    private String numeDirector;
    private String caleCompletaDirector;
    private String versiune;
    private String dataVersiune;
    private Integer idUser;
    private String versiuneLabel;
    private Integer idDocument;
    private String denumireDocument;
    private String serverPath;
    private String creatDe;
    private String creatLa;
    private String modificatDe;
    private String modificatLa;
    private String nrInreg;
    private String dataInreg;
    private Integer idUnitate;
    private String dimensiune;
    private Integer idWorkflowStatus;
    private String denumireWorkflowStatus;
    private String zilePreaviz;
    private String dataExpirare;
    private String ignora;
    private String zileAlerta;
    private String pageCount;
    private String attrTitle;
    private String attrShortText;
    private String attrLongText;
    private String numeBaza;
    private String creatLaStr;
    private String modificatLaStr;
    private String downloadLink;
    private String trimisLa;
    private String clasaDocument;
    private Integer idClasaDocument;
    private Integer documentRaspuns;
    private Integer idRegistru;
    private String costDocument;
    private Double valoarePlatita;
    private String extensionName;
    private String adresaImobil;
    private String tipLucrare;
    private String emitent;
    private String email;
    private Integer allowComments;
    private String persoanaReprezentantLegal;
    private String persoanaReprezentantLegalEmail;
    private String persoanaReprezentantLegalFunctie;
    private String persoanaContact1;
    private String persoanaContact1Email;
    private String persoanaContact1Telefon;
    private String persoanaContact2;
    private String persoanaContact2Email;
    private String persoanaContact2Telefon;
    private String numarFactura;
    private String dataFactura;
    private String serieFactura;

    public String getNumarFactura() {
        return numarFactura;
    }

    public void setNumarFactura(String numarFactura) {
        this.numarFactura = numarFactura;
    }

    public String getDataFactura() {
        return dataFactura;
    }

    public void setDataFactura(String dataFactura) {
        this.dataFactura = dataFactura;
    }

    public String getSerieFactura() {
        return serieFactura;
    }

    public void setSerieFactura(String serieFactura) {
        this.serieFactura = serieFactura;
    }

    public String getPersoanaReprezentantLegal() {
        return persoanaReprezentantLegal;
    }

    public void setPersoanaReprezentantLegal(String persoanaReprezentantLegal) {
        this.persoanaReprezentantLegal = persoanaReprezentantLegal;
    }

    public String getPersoanaReprezentantLegalEmail() {
        return persoanaReprezentantLegalEmail;
    }

    public void setPersoanaReprezentantLegalEmail(String persoanaReprezentantLegalEmail) {
        this.persoanaReprezentantLegalEmail = persoanaReprezentantLegalEmail;
    }

    public String getPersoanaReprezentantLegalFunctie() {
        return persoanaReprezentantLegalFunctie;
    }

    public void setPersoanaReprezentantLegalFunctie(String persoanaReprezentantLegalFunctie) {
        this.persoanaReprezentantLegalFunctie = persoanaReprezentantLegalFunctie;
    }

    public String getPersoanaContact1() {
        return persoanaContact1;
    }

    public void setPersoanaContact1(String persoanaContact1) {
        this.persoanaContact1 = persoanaContact1;
    }

    public String getPersoanaContact1Email() {
        return persoanaContact1Email;
    }

    public void setPersoanaContact1Email(String persoanaContact1Email) {
        this.persoanaContact1Email = persoanaContact1Email;
    }

    public String getPersoanaContact1Telefon() {
        return persoanaContact1Telefon;
    }

    public void setPersoanaContact1Telefon(String persoanaContact1Telefon) {
        this.persoanaContact1Telefon = persoanaContact1Telefon;
    }

    public String getPersoanaContact2() {
        return persoanaContact2;
    }

    public void setPersoanaContact2(String persoanaContact2) {
        this.persoanaContact2 = persoanaContact2;
    }

    public String getPersoanaContact2Email() {
        return persoanaContact2Email;
    }

    public void setPersoanaContact2Email(String persoanaContact2Email) {
        this.persoanaContact2Email = persoanaContact2Email;
    }

    public String getPersoanaContact2Telefon() {
        return persoanaContact2Telefon;
    }

    public void setPersoanaContact2Telefon(String persoanaContact2Telefon) {
        this.persoanaContact2Telefon = persoanaContact2Telefon;
    }

    public Integer getAllowComments() {
        return allowComments;
    }

    public void setAllowComments(Integer allowComments) {
        this.allowComments = allowComments;
    }
    public Integer getIdRegistru() {
        return idRegistru;
    }

    public void setIdRegistru(Integer idRegistru) {
        this.idRegistru = idRegistru;
    }

    public String getEmitent() {
        return emitent;
    }

    public void setEmitent(String emitent) {
        this.emitent = emitent;
    }

    public String getAdresaImobil() {
        return adresaImobil;
    }

    public void setAdresaImobil(String adresaImobil) {
        this.adresaImobil = adresaImobil;
    }

    public String getTipLucrare() {
        return tipLucrare;
    }

    public void setTipLucrare(String tipLucrare) {
        this.tipLucrare = tipLucrare;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public Double getValoarePlatita() {
        return valoarePlatita;
    }

    public void setValoarePlatita(Double valoarePlatita) {
        this.valoarePlatita = valoarePlatita;
    }

    public Integer getDocumentRaspuns() {
        return documentRaspuns;
    }

    public void setDocumentRaspuns(Integer documentRaspuns) {
        this.documentRaspuns = documentRaspuns;
    }

    public Integer getIdClasaDocument() {
        return idClasaDocument;
    }

    public void setIdClasaDocument(Integer idClasaDocument) {
        this.idClasaDocument = idClasaDocument;
    }

    public String getCostDocument() {
        return costDocument;
    }

    public void setCostDocument(String costDocument) {
        this.costDocument = costDocument;
    }

    public String getTrimisLa() {
        return trimisLa;
    }

    public void setTrimisLa(String trimisLa) {
        this.trimisLa = trimisLa;
    }

    public String getClasaDocument() {
        return clasaDocument;
    }

    public void setClasaDocument(String clasaDocument) {
        this.clasaDocument = clasaDocument;
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

    public Integer getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(Integer idDirector) {
        this.idDirector = idDirector;
    }

    public String getNumeDirector() {
        return numeDirector;
    }

    public void setNumeDirector(String numeDirector) {
        this.numeDirector = numeDirector;
    }

    public String getCaleCompletaDirector() {
        return caleCompletaDirector;
    }

    public void setCaleCompletaDirector(String caleCompletaDirector) {
        this.caleCompletaDirector = caleCompletaDirector;
    }

    public String getVersiune() {
        return versiune;
    }

    public void setVersiune(String versiune) {
        this.versiune = versiune;
    }

    public String getDataVersiune() {
        return dataVersiune;
    }

    public void setDataVersiune(String dataVersiune) {
        this.dataVersiune = dataVersiune;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getVersiuneLabel() {
        return versiuneLabel;
    }

    public void setVersiuneLabel(String versiuneLabel) {
        this.versiuneLabel = versiuneLabel;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public String getDenumireDocument() {
        return denumireDocument;
    }

    public void setDenumireDocument(String denumireDocument) {
        this.denumireDocument = denumireDocument;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getCreatDe() {
        return creatDe;
    }

    public void setCreatDe(String creatDe) {
        this.creatDe = creatDe;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public String getModificatDe() {
        return modificatDe;
    }

    public void setModificatDe(String modificatDe) {
        this.modificatDe = modificatDe;
    }

    public String getModificatLa() {
        return modificatLa;
    }

    public void setModificatLa(String modificatLa) {
        this.modificatLa = modificatLa;
    }

    public String getNrInreg() {
        return nrInreg;
    }

    public void setNrInreg(String nrInreg) {
        this.nrInreg = nrInreg;
    }

    public String getDataInreg() {
        return dataInreg;
    }

    public void setDataInreg(String dataInreg) {
        this.dataInreg = dataInreg;
    }

    public Integer getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(Integer idUnitate) {
        this.idUnitate = idUnitate;
    }

    public String getDimensiune() {
        return dimensiune;
    }

    public void setDimensiune(String dimensiune) {
        this.dimensiune = dimensiune;
    }

    public Integer getIdWorkflowStatus() {
        return idWorkflowStatus;
    }

    public void setIdWorkflowStatus(Integer idWorkflowStatus) {
        this.idWorkflowStatus = idWorkflowStatus;
    }

    public String getDenumireWorkflowStatus() {
        return denumireWorkflowStatus;
    }

    public void setDenumireWorkflowStatus(String denumireWorkflowStatus) {
        this.denumireWorkflowStatus = denumireWorkflowStatus;
    }

    public String getZilePreaviz() {
        return zilePreaviz;
    }

    public void setZilePreaviz(String zilePreaviz) {
        this.zilePreaviz = zilePreaviz;
    }

    public String getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(String dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public String getIgnora() {
        return ignora;
    }

    public void setIgnora(String ignora) {
        this.ignora = ignora;
    }

    public String getZileAlerta() {
        return zileAlerta;
    }

    public void setZileAlerta(String zileAlerta) {
        this.zileAlerta = zileAlerta;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getAttrTitle() {
        return attrTitle;
    }

    public void setAttrTitle(String attrTitle) {
        this.attrTitle = attrTitle;
    }

    public String getAttrShortText() {
        return attrShortText;
    }

    public void setAttrShortText(String attrShortText) {
        this.attrShortText = attrShortText;
    }

    public String getAttrLongText() {
        return attrLongText;
    }

    public void setAttrLongText(String attrLongText) {
        this.attrLongText = attrLongText;
    }

    public String getNumeBaza() {
        return numeBaza;
    }

    public void setNumeBaza(String numeBaza) {
        this.numeBaza = numeBaza;
    }

    public String getCreatLaStr() {
        return creatLaStr;
    }

    public void setCreatLaStr(String creatLaStr) {
        this.creatLaStr = creatLaStr;
    }

    public String getModificatLaStr() {
        return modificatLaStr;
    }

    public void setModificatLaStr(String modificatLaStr) {
        this.modificatLaStr = modificatLaStr;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public Integer getIdFisierReferinta() {
        return idFisierReferinta;
    }

    public void setIdFisierReferinta(Integer idFisierReferinta) {
        this.idFisierReferinta = idFisierReferinta;
    }

    public String getDownloadLinkFisierReferinta() {
        return downloadLinkFisierReferinta;
    }

    public void setDownloadLinkFisierReferinta(String downloadLinkFisierReferinta) {
        this.downloadLinkFisierReferinta = downloadLinkFisierReferinta;
    }

    public String getNumeFisierReferinta() {
        return numeFisierReferinta;
    }

    public void setNumeFisierReferinta(String numeFisierReferinta) {
        this.numeFisierReferinta = numeFisierReferinta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
