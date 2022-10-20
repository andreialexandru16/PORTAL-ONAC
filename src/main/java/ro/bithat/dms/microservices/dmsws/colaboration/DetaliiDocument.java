package ro.bithat.dms.microservices.dmsws.colaboration;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DetaliiDocument extends BaseModel {
    private Integer idLinieDecont;
    private String document;
    private String documentPlata;
    private String documentName;
    private String documentPlataName;
    private byte[] documentByteArray;

    public byte[] getDocumentByteArray() {
        return documentByteArray;
    }

    public void setDocumentByteArray(byte[] documentByteArray) {
        this.documentByteArray = documentByteArray;
    }

    public Integer getIdLinieDecont() {
        return idLinieDecont;
    }

    public void setIdLinieDecont(Integer idLinieDecont) {
        this.idLinieDecont = idLinieDecont;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPlata() {
        return documentPlata;
    }

    public void setDocumentPlata(String documentPlata) {
        this.documentPlata = documentPlata;
    }

    public String getDocumentPlataName() {
        return documentPlataName;
    }

    public void setDocumentPlataName(String documentPlataName) {
        this.documentPlataName = documentPlataName;
    }
}
