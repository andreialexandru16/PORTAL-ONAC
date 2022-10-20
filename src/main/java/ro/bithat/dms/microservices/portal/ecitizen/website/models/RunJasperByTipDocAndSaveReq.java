package ro.bithat.dms.microservices.portal.ecitizen.website.models;

public class RunJasperByTipDocAndSaveReq {
    private Integer idDocument;
    private Integer mainId;
    private String outputName;

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public Integer getMainId() {
        return mainId;
    }

    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }
}
