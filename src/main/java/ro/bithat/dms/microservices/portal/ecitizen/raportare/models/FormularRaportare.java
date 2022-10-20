package ro.bithat.dms.microservices.portal.ecitizen.raportare.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormularRaportare {
    private Integer id;
    private String numeFisier;
    private String creatLa;
    private String modificatLa;
    private Integer idTipDoc;
    private Integer idClasaDoc;
    private String tipDoc;
    private Integer pozitieTipDoc;
    private Integer activTipDoc;
    private String numeClasaDoc;
    private Integer idPerioadaRaportare;
    private Integer idTertRaportare;
    private String request;
    private String status;
    private String iconStatus;
    private String colorStatus;
    private Integer idTemplateFile;
    private String downloadLinkTemplateFile;
    private String displayDownloadLinkTemplateFile;
    private String displayDownloadLinkTemplateFileXsd;
    private Boolean permiteDoarIncarcareExcel;
    private Boolean permiteEditarePortal;
    private Boolean areFisierExcel;
    private Boolean areFisierXml;
    private Boolean permiteDoarIncarcareXml;
    private String downloadLinkTemplateFileXml;
    private String downloadLinkTemplateFileXsd;
    private String templateFileXmlToken;
    private String displayDownloadLinkTemplateFileXml;
    private Boolean hasWarnings;
    private Boolean hasErrors;
    private String errors;
    private String warnings;
    private boolean hasOk;
    private String termen;
    private String jspPage;
    private String downloadLink;

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
    public String getJspPage() {
        return jspPage;
    }

    public void setJspPage(String jspPage) {
        this.jspPage = jspPage;
    }

    public String getTermen() {
        return termen;
    }

    public void setTermen(String termen) {
        this.termen = termen;
    }

    public String getDisplayDownloadLinkTemplateFileXsd() {
        return displayDownloadLinkTemplateFileXsd;
    }

    public void setDisplayDownloadLinkTemplateFileXsd(String displayDownloadLinkTemplateFileXsd) {
        this.displayDownloadLinkTemplateFileXsd = displayDownloadLinkTemplateFileXsd;
    }

    public String getDownloadLinkTemplateFileXsd() {
        return downloadLinkTemplateFileXsd;
    }

    public void setDownloadLinkTemplateFileXsd(String downloadLinkTemplateFileXsd) {
        this.downloadLinkTemplateFileXsd = downloadLinkTemplateFileXsd;
    }

    public boolean isPermiteDoarIncarcareXml() {
        return permiteDoarIncarcareXml;
    }

    public void setPermiteDoarIncarcareXml(boolean permiteDoarIncarcareXml) {
        this.permiteDoarIncarcareXml = permiteDoarIncarcareXml;
    }

    public String getDownloadLinkTemplateFileXml() {
        return downloadLinkTemplateFileXml;
    }

    public void setDownloadLinkTemplateFileXml(String downloadLinkTemplateFileXml) {
        this.downloadLinkTemplateFileXml = downloadLinkTemplateFileXml;
    }

    public String getDisplayDownloadLinkTemplateFileXml() {
        return displayDownloadLinkTemplateFileXml;
    }

    public void setDisplayDownloadLinkTemplateFileXml(String displayDownloadLinkTemplateFileXml) {
        this.displayDownloadLinkTemplateFileXml = displayDownloadLinkTemplateFileXml;
    }

    public Boolean getAreFisierExcel() {
        return areFisierExcel;
    }

    public void setAreFisierExcel(Boolean areFisierExcel) {
        this.areFisierExcel = areFisierExcel;
    }

    public Boolean getPermiteEditarePortal() {
        return permiteEditarePortal;
    }

    public void setPermiteEditarePortal(Boolean permiteEditarePortal) {
        this.permiteEditarePortal = permiteEditarePortal;
    }
    public Boolean getPermiteDoarIncarcareExcel() {
        return permiteDoarIncarcareExcel;
    }

    public void setPermiteDoarIncarcareExcel(Boolean permiteDoarIncarcareExcel) {
        this.permiteDoarIncarcareExcel = permiteDoarIncarcareExcel;
    }

    public String getDisplayDownloadLinkTemplateFile() {
        return displayDownloadLinkTemplateFile;
    }

    public void setDisplayDownloadLinkTemplateFile(String displayDownloadLinkTemplateFile) {
        this.displayDownloadLinkTemplateFile = displayDownloadLinkTemplateFile;
    }

    public Integer getIdTemplateFile() {
        return idTemplateFile;
    }

    public void setIdTemplateFile(Integer idTemplateFile) {
        this.idTemplateFile = idTemplateFile;
    }

    public String getDownloadLinkTemplateFile() {
        return downloadLinkTemplateFile;
    }

    public void setDownloadLinkTemplateFile(String downloadLinkTemplateFile) {
        this.downloadLinkTemplateFile = downloadLinkTemplateFile;
    }

    public String getColorStatus() {
        return colorStatus;
    }

    public void setColorStatus(String colorStatus) {
        this.colorStatus = colorStatus;
    }

    public String getIconStatus() {
        return iconStatus;
    }

    public void setIconStatus(String iconStatus) {
        this.iconStatus = iconStatus;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeFisier() {
        return numeFisier;
    }

    public void setNumeFisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public Integer getIdTipDoc() {
        return idTipDoc;
    }

    public void setIdTipDoc(Integer idTipDoc) {
        this.idTipDoc = idTipDoc;
    }

    public Integer getIdClasaDoc() {
        return idClasaDoc;
    }

    public void setIdClasaDoc(Integer idClasaDoc) {
        this.idClasaDoc = idClasaDoc;
    }

    public String getTipDoc() {
        return tipDoc;
    }

    public void setTipDoc(String tipDoc) {
        this.tipDoc = tipDoc;
    }

    public Integer getPozitieTipDoc() {
        return pozitieTipDoc;
    }

    public void setPozitieTipDoc(Integer pozitieTipDoc) {
        this.pozitieTipDoc = pozitieTipDoc;
    }

    public Integer getActivTipDoc() {
        return activTipDoc;
    }

    public void setActivTipDoc(Integer activTipDoc) {
        this.activTipDoc = activTipDoc;
    }

    public String getNumeClasaDoc() {
        return numeClasaDoc;
    }

    public void setNumeClasaDoc(String numeClasaDoc) {
        this.numeClasaDoc = numeClasaDoc;
    }

    public Integer getIdPerioadaRaportare() {
        return idPerioadaRaportare;
    }

    public void setIdPerioadaRaportare(Integer idPerioadaRaportare) {
        this.idPerioadaRaportare = idPerioadaRaportare;
    }

    public Integer getIdTertRaportare() {
        return idTertRaportare;
    }

    public void setIdTertRaportare(Integer idTertRaportare) {
        this.idTertRaportare = idTertRaportare;
    }

    public String getModificatLa() {
        return modificatLa;
    }

    public void setModificatLa(String modificatLa) {
        this.modificatLa = modificatLa;
    }

    public String getTemplateFileXmlToken() {
        return templateFileXmlToken;
    }

    public void setTemplateFileXmlToken(String templateFileXmlToken) {
        this.templateFileXmlToken = templateFileXmlToken;
    }

    public Boolean getAreFisierXml() {
        return areFisierXml;
    }

    public void setAreFisierXml(Boolean areFisierXml) {
        this.areFisierXml = areFisierXml;
    }

    public Boolean getPermiteDoarIncarcareXml() {
        return permiteDoarIncarcareXml;
    }

    public void setPermiteDoarIncarcareXml(Boolean permiteDoarIncarcareXml) {
        this.permiteDoarIncarcareXml = permiteDoarIncarcareXml;
    }

    public Boolean getHasWarnings() {
        return hasWarnings;
    }

    public void setHasWarnings(Boolean hasWarnings) {
        this.hasWarnings = hasWarnings;
    }

    public Boolean getHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(Boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public boolean isHasOk() {
        return hasOk;
    }

    public void setHasOk(boolean hasOk) {
        this.hasOk = hasOk;
    }
}
