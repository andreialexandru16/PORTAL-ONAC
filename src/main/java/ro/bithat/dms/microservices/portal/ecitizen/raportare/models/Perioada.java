package ro.bithat.dms.microservices.portal.ecitizen.raportare.models;

public class Perioada {
    int id;
    String denumire;
    String startDate;
    String endDate;
    int inchisaContabil;
    int inchisaDocumente;
    int inchisaStoc;
    int inchisaHR;
    int idPerioadaBefore;
    int idPerioadaNext;
    int estePerioadaCurenta;
    String selected;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
    public int getEstePerioadaCurenta() {
        return estePerioadaCurenta;
    }

    public void setEstePerioadaCurenta(int estePerioadaCurenta) {
        this.estePerioadaCurenta = estePerioadaCurenta;
    }
    Integer nrZileLucratoare;
    Integer nrZileCalendaristice;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Integer getNrZileLucratoare() {
        return nrZileLucratoare;
    }

    public void setNrZileLucratoare(Integer nrZileLucratoare) {
        this.nrZileLucratoare = nrZileLucratoare;
    }

    public Integer getNrZileCalendaristice() {
        return nrZileCalendaristice;
    }

    public void setNrZileCalendaristice(Integer nrZileCalendaristice) {
        this.nrZileCalendaristice = nrZileCalendaristice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getInchisaContabil() {
        return inchisaContabil;
    }

    public void setInchisaContabil(int inchisaContabil) {
        this.inchisaContabil = inchisaContabil;
    }

    public int getInchisaDocumente() {
        return inchisaDocumente;
    }

    public void setInchisaDocumente(int inchisaDocumente) {
        this.inchisaDocumente = inchisaDocumente;
    }

    public int getInchisaStoc() {
        return inchisaStoc;
    }

    public void setInchisaStoc(int inchisaStoc) {
        this.inchisaStoc = inchisaStoc;
    }

    public int getInchisaHR() {
        return inchisaHR;
    }

    public void setInchisaHR(int inchisaHR) {
        this.inchisaHR = inchisaHR;
    }

    public int getIdPerioadaBefore() {
        return idPerioadaBefore;
    }

    public void setIdPerioadaBefore(int idPerioadaBefore) {
        this.idPerioadaBefore = idPerioadaBefore;
    }

    public int getIdPerioadaNext() {
        return idPerioadaNext;
    }

    public void setIdPerioadaNext(int idPerioadaNext) {
        this.idPerioadaNext = idPerioadaNext;
    }
}
