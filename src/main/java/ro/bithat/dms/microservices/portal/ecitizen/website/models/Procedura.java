
package ro.bithat.dms.microservices.portal.ecitizen.website.models;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Procedura{
    private Integer id;
    private String nume;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
