package ro.bithat.dms.microservices.dmsws.flow;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StandardResponse {
    private String status;
    private String extendedStatus;
    private String errString;

    public boolean isError() {
    	return !(this.status !=null && this.status.equalsIgnoreCase("OK"));
    }

    public String getExtendedStatus() {
        return extendedStatus;
    }

    public void setExtendedStatus(String extendedStatus) {
        this.extendedStatus = extendedStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrString() {
        return errString;
    }

    public void setErrString(String errString) {
        this.errString = errString;
    }

	@Override
	public String toString() {
		return "StandardResponse [status=" + status + ", extendedStatus=" + extendedStatus + ", errString=" + errString
				+ "]";
	}
}
