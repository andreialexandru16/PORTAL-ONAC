package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddFileToPetitieReq {
    private Integer idCorespondenta;
    private String base64File;
    private String fileName;

    public Integer getIdCorespondenta() {
        return idCorespondenta;
    }

    public void setIdCorespondenta(Integer idCorespondenta) {
        this.idCorespondenta = idCorespondenta;
    }

    public String getBase64File() {
        return base64File;
    }

    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
