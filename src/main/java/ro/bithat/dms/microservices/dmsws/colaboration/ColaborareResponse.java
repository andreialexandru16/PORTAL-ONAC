package ro.bithat.dms.microservices.dmsws.colaboration;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import java.util.List;

public class ColaborareResponse extends StandardResponse {
    private List<Mesaj> mesajeList;
    private List<Utilizator> utilizatoriList;

    public List<Mesaj> getMesajeList() {
        return mesajeList;
    }

    public void setMesajeList(List<Mesaj> mesajeList) {
        this.mesajeList = mesajeList;
    }

    public List<Utilizator> getUtilizatoriList() {
        return utilizatoriList;
    }

    public void setUtilizatoriList(List<Utilizator> utilizatoriList) {
        this.utilizatoriList = utilizatoriList;
    }

}
