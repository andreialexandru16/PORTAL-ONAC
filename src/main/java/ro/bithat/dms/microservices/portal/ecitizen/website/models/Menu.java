package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Menu {
    private Integer id;
    private String nume;
    private String linkPortal;
    private Integer pozitie;
    private Integer numDrept;
    List<Menu> listaOptiuni = new ArrayList<Menu>();

    public String getLinkPortal() {
        return linkPortal;
    }

    public void setLinkPortal(String linkPortal) {
        this.linkPortal = linkPortal;
    }


    public List<Menu> getListaOptiuni() {
        return listaOptiuni;
    }

    public void setListaOptiuni(List<Menu> listaOptiuni) {
        this.listaOptiuni = listaOptiuni;
    }

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

    public Integer getPozitie() {
        return pozitie;
    }

    public void setPozitie(Integer pozitie) {
        this.pozitie = pozitie;
    }

    public Integer getNumDrept() {
        return numDrept;
    }

    public void setNumDrept(Integer numDrept) {
        this.numDrept = numDrept;
    }
}
