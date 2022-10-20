package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocObligatoriu {
    private Integer id;
    private Integer id_document_p;
    private Integer id_document;
    private String cod;
    private String denumire;
    private String descriere;
    private String idTemplateFile;
    private String linkTemplateFile;
    private String denumireTemplateFile;
    private String idFisierAnexat;
    private String linkFisierAnexat;
    private String denumireFisierAnexat;
    private Boolean obligatoriu;
    private Integer pozitiE;
    private String cost;
    private Double costD;
    private Integer documentPlata;
    private String descriereFisier;
    private String nota;
    private Integer documentRaspuns;
    private Integer view_direct;
    private Boolean replaceMainFile;

    public Integer getDocumentRaspuns() {
        return documentRaspuns;
    }

    public void setDocumentRaspuns(Integer documentRaspuns) {
        this.documentRaspuns = documentRaspuns;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getDenumireTemplateFile() {
        return denumireTemplateFile;
    }

    public void setDenumireTemplateFile(String denumireTemplateFile) {
        this.denumireTemplateFile = denumireTemplateFile;
    }

    public String getDenumireFisierAnexat() {
        return denumireFisierAnexat;
    }

    public void setDenumireFisierAnexat(String denumireFisierAnexat) {
        this.denumireFisierAnexat = denumireFisierAnexat;
    }

    public Integer getDocumentPlata() {
        return documentPlata;
    }

    public void setDocumentPlata(Integer documentPlata) {
        this.documentPlata = documentPlata;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getIdFisierAnexat() {
        return idFisierAnexat;
    }

    public void setIdFisierAnexat(String idFisierAnexat) {
        this.idFisierAnexat = idFisierAnexat;
    }

    public String getLinkFisierAnexat() {
        return linkFisierAnexat;
    }

    public void setLinkFisierAnexat(String linkFisierAnexat) {
        this.linkFisierAnexat = linkFisierAnexat;
    }

    public String getLinkTemplateFile() {
        return linkTemplateFile;
    }

    public void setLinkTemplateFile(String linkTemplateFile) {
        this.linkTemplateFile = linkTemplateFile;
    }

    public String getIdTemplateFile() {
        return idTemplateFile;
    }

    public void setIdTemplateFile(String idTemplateFile) {
        this.idTemplateFile = idTemplateFile;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_document_p() {
        return id_document_p;
    }

    public void setId_document_p(Integer id_document_p) {
        this.id_document_p = id_document_p;
    }

    public Integer getId_document() {
        return id_document;
    }

    public void setId_document(Integer id_document) {
        this.id_document = id_document;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Boolean getObligatoriu() {
        return obligatoriu;
    }

    public void setObligatoriu(Boolean obligatoriu) {
        this.obligatoriu = obligatoriu;
    }

    public Integer getPozitiE() {
        return pozitiE;
    }

    public void setPozitiE(Integer pozitiE) {
        this.pozitiE = pozitiE;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

	public Double getCostD() {
		return costD;
	}

	public void setCostD(Double costD) {
		this.costD = costD;
	}

    public String getDescriereFisier() {
        return descriereFisier;
    }

    public void setDescriereFisier(String descriereFisier) {
        this.descriereFisier = descriereFisier;
    }

    public Integer getView_direct() {
        return view_direct;
    }

    public void setView_direct(Integer view_direct) {
        this.view_direct = view_direct;
    }

    public Boolean getReplaceMainFile() {
        return replaceMainFile;
    }

    public void setReplaceMainFile(Boolean replaceMainFile) {
        this.replaceMainFile = replaceMainFile;
    }
}
