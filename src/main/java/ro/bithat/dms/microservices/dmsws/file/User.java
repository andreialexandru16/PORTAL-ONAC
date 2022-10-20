package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza un user in serviciile web.
 */
@XmlRootElement
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nume;
    private String prenume;
    private Integer idUnitate;
    private String email;
    private Integer wakeupPort;
    private Double effectiveQuota;
    private Double allocatedQuota;

    public Double getEffectiveQuota() {
        return effectiveQuota;
    }

    public void setEffectiveQuota(Double effectiveQuota) {
        this.effectiveQuota = effectiveQuota;
    }

    public Double getAllocatedQuota() {
        return allocatedQuota;
    }

    public void setAllocatedQuota(Double allocatedQuota) {
        this.allocatedQuota = allocatedQuota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(Integer idUnitate) {
        this.idUnitate = idUnitate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWakeupPort() {
        return wakeupPort;
    }

    public void setWakeupPort(Integer wakeupPort) {
        this.wakeupPort = wakeupPort;
    }
}
