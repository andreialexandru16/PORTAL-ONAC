package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WsAndUserInfo {
	private UserToken userToken;
	private String wsUrl;
	private String uploadWsUrl;
	private String portalUrl;
	private DmsTert tert;
	String listaRoluri;

	public String getListaRoluri() {
		return listaRoluri;
	}

	public void setListaRoluri(String listaRoluri) {
		this.listaRoluri = listaRoluri;
	}

	public UserToken getUserToken() {
		return userToken;
	}

	public void setUserToken(UserToken userToken) {
		this.userToken = userToken;
	}

	public String getWsUrl() {
		return wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

	public String getPortalUrl() {
		return portalUrl;
	}

	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}

	public DmsTert getTert() {
		return tert;
	}

	public void setTert(DmsTert tert) {
		this.tert = tert;
	}

	public String getUploadWsUrl() {
		return uploadWsUrl;
	}

	public void setUploadWsUrl(String uploadWsUrl) {
		this.uploadWsUrl = uploadWsUrl;
	}
}