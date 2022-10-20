package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkListOfLists;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateTipDocFileResponseXml extends StandardResponse {
    private String fileId;
    private String warnings;
    private String errors;
    private AttributeLinkListOfLists attributeLinkListOfLists;

    public AttributeLinkListOfLists getAttributeLinkListOfLists() {
        return attributeLinkListOfLists;
    }

    public void setAttributeLinkListOfLists(AttributeLinkListOfLists attributeLinkListOfLists) {
        this.attributeLinkListOfLists = attributeLinkListOfLists;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}