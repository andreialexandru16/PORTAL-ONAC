package ro.bithat.dms.microservices.dmsws.flow;

public class PortalFlowPasiActiuni {
    private Integer id;
    private String persoana;
    private String denumirePas;
    private String denumireStare;
    private String culoareStare;
    private String denumireFlux;
    private String dataStart;
    private String dataEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersoana() {
        return persoana;
    }

    public void setPersoana(String persoana) {
        this.persoana = persoana;
    }

    public String getDenumirePas() {
        return denumirePas;
    }

    public void setDenumirePas(String denumirePas) {
        this.denumirePas = denumirePas;
    }

    public String getDenumireStare() {
        return denumireStare;
    }

    public void setDenumireStare(String denumireStare) {
        this.denumireStare = denumireStare;
    }

    public String getDenumireFlux() {
        return denumireFlux;
    }

    public void setDenumireFlux(String denumireFlux) {
        this.denumireFlux = denumireFlux;
    }

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(String dataEnd) {
        this.dataEnd = dataEnd;
    }

    public String getCuloareStare() {
        return culoareStare;
    }

    public void setCuloareStare(String culoareStare) {
        this.culoareStare = culoareStare;
    }
}
