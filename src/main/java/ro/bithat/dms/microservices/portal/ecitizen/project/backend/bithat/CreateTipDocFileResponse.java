package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateTipDocFileResponse extends StandardResponse {

    private String fileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}