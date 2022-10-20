package ro.bithat.dms.microservices.portal.ecitizen.website.models;


public class Studii {

    int id;
    int id_tert;
    int id_nivel;
    String nivel;
    String nr_serie_data;
    String universitate;
    String loc_universitate;
    String facultate;
    String specialitate;
    String programa;
    String linkDownload;
    String fisier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tert() {
        return id_tert;
    }

    public void setId_tert(int id_tert) {
        this.id_tert = id_tert;
    }

    public int getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNr_serie_data() {
        return nr_serie_data;
    }

    public void setNr_serie_data(String nr_serie_data) {
        this.nr_serie_data = nr_serie_data;
    }

    public String getUniversitate() {
        return universitate;
    }

    public void setUniversitate(String universitate) {
        this.universitate = universitate;
    }

    public String getLoc_universitate() {
        return loc_universitate;
    }

    public void setLoc_universitate(String loc_universitate) {
        this.loc_universitate = loc_universitate;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public String getSpecialitate() {
        return specialitate;
    }

    public void setSpecialitate(String specialitate) {
        this.specialitate = specialitate;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }

    public String getFisier() {
        return fisier;
    }

    public void setFisier(String fisier) {
        this.fisier = fisier;
    }
}
