package ro.bithat.dms.microservices.dmsws.flow;

public class PortalFlowList {
	private String errString;

	private PortalFlowInstanceList[] portalFlowInstanceList;

	private String extendedStatus;

	private String status;

	public String getErrString() {
		return errString;
	}

	public void setErrString(String errString) {
		this.errString = errString;
	}

	public PortalFlowInstanceList[] getPortalFlowInstanceList() {
		return portalFlowInstanceList;
	}

	public void setPortalFlowInstanceList(PortalFlowInstanceList[] portalFlowInstanceList) {
		this.portalFlowInstanceList = portalFlowInstanceList;
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

	@Override
	public String toString() {
		return "ClassPojo [errString = " + errString + ", portalFlowInstanceList = " + portalFlowInstanceList
				+ ", extendedStatus = " + extendedStatus + ", status = " + status + "]";
	}
}