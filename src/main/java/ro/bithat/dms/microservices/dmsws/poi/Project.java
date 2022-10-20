package ro.bithat.dms.microservices.dmsws.poi;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Project {
    private Integer id;
    private String nume;
    private String cod;
    private String numar;
    private String codProiect;
    private String imageLink;
    private String base64Image;

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
            this.nume = nume;
        }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getCodProiect() {
        return codProiect;
    }

    public void setCodProiect(String codProiect) {
        this.codProiect = codProiect;
    }
}
