package ro.bithat.dms.microservices.dmsws.flow;

public class PortalFlowActionRequest {
    private Integer idFluxPas;
    private String action;
    private String mesaj;
    private String signature;

    public Integer getIdFluxPas() {
        return idFluxPas;
    }

    public void setIdFluxPas(Integer idFluxPas) {
        this.idFluxPas = idFluxPas;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
