package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileLinkFluxData {
    private Boolean areFlux = false;
    private String token;
    private String tokenReject;
    private String buttonLabel;
    private String buttonLabelReject;

    public Boolean getAreFlux() {
        return areFlux;
    }

    public void setAreFlux(Boolean areFlux) {
        this.areFlux = areFlux;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenReject() {
        return tokenReject;
    }

    public void setTokenReject(String tokenReject) {
        this.tokenReject = tokenReject;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getButtonLabelReject() {
        return buttonLabelReject;
    }

    public void setButtonLabelReject(String buttonLabelReject) {
        this.buttonLabelReject = buttonLabelReject;
    }
}
