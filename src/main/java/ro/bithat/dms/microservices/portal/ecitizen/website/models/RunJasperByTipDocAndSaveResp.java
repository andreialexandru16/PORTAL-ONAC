package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

public class RunJasperByTipDocAndSaveResp extends StandardResponse {
    private String idFisier;
    private String downloadLink;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(String idFisier) {
        this.idFisier = idFisier;
    }
}
