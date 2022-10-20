package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
import java.util.Set;

@XmlRootElement
public class UserToken {

	private String expires;
	private String lang;
	private String npPerson;
	private String result_code;
	private String token;
	private String unitId;
	private String userId;
	private String nume;
	private String prenume;
	private String username;
	private String email;
	private String valid;
	private Integer idPersoana;
	private Integer cookieAccepted;

	private String result_msg;
	private Map<String,String> settings;
	private Set<String> roles;


	// Getter Methods


	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	public Map<String, String> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Integer getCookieAccepted() {
		return cookieAccepted;
	}

	public void setCookieAccepted(Integer cookieAccepted) {
		this.cookieAccepted = cookieAccepted;
	}

	public Integer getIdPersoana() {
		return idPersoana;
	}

	public void setIdPersoana(Integer idPersoana) {
		this.idPersoana = idPersoana;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExpires() {
		return expires;
	}

	public String getLang() {
		return lang;
	}

	public String getNpPerson() {
		return npPerson;
	}

	public String getResult_code() {
		return result_code;
	}

	public String getToken() {
		return token;
	}

	public String getUnitId() {
		return unitId;
	}

	public String getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getValid() {
		return valid;
	}

	// Setter Methods

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setNpPerson(String npPerson) {
		this.npPerson = npPerson;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "UserToken [expires=" + expires + ", lang=" + lang + ", npPerson=" + npPerson + ", result_code="
				+ result_code + ", token=" + token + ", unitId=" + unitId + ", userId=" + userId + ", username="
				+ username + ", valid=" + valid + "]";
	}

}