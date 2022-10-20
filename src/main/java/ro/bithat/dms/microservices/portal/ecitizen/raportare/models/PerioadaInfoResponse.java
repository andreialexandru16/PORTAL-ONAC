package ro.bithat.dms.microservices.portal.ecitizen.raportare.models;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import java.util.List;

public class PerioadaInfoResponse extends StandardResponse {
    private List<Perioada> perioadaList;
    private Perioada perioadaCurenta;

    public List<Perioada> getPerioadaList() {
        return perioadaList;
    }

    public void setPerioadaList(List<Perioada> perioadaList) {
        this.perioadaList = perioadaList;
    }

    public Perioada getPerioadaCurenta() {
        return perioadaCurenta;
    }

    public void setPerioadaCurenta(Perioada perioadaCurenta) {
        this.perioadaCurenta = perioadaCurenta;
    }
}
