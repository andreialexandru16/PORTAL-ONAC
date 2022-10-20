package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

    public class LicenteFilteredRequest {

    private String societate;
    private String sediu;
    private String localitate;
    private String judet;
    private String nr_licenta;
    private String stare;
    private String tip_licenta;
    private String tip_activitate;
    private String nr_decizie;

        public String getSocietate() {
            return societate;
        }

        public void setSocietate(String societate) {
            this.societate = societate;
        }

        public String getSediu() {
            return sediu;
        }

        public void setSediu(String sediu) {
            this.sediu = sediu;
        }

        public String getLocalitate() {
            return localitate;
        }

        public void setLocalitate(String localitate) {
            this.localitate = localitate;
        }

        public String getJudet() {
            return judet;
        }

        public void setJudet(String judet) {
            this.judet = judet;
        }

        public String getNr_licenta() {
            return nr_licenta;
        }

        public void setNr_licenta(String nr_licenta) {
            this.nr_licenta = nr_licenta;
        }

        public String getStare() {
            return stare;
        }

        public void setStare(String stare) {
            this.stare = stare;
        }

        public String getTip_licenta() {
            return tip_licenta;
        }

        public void setTip_licenta(String tip_licenta) {
            this.tip_licenta = tip_licenta;
        }

        public String getTip_activitate() {
            return tip_activitate;
        }

        public void setTip_activitate(String tip_activitate) {
            this.tip_activitate = tip_activitate;
        }

        public String getNr_decizie() {
            return nr_decizie;
        }

        public void setNr_decizie(String nr_decizie) {
            this.nr_decizie = nr_decizie;
        }


}
