package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

public class Column {
    private String nume;
    private String label;
    private ColumnType type;
    private Integer lungime;
    private Integer precizie;
    private Integer pozitie;
    private Integer displayWidth;

    public Column() {
    }

    public Column(String nume, String label, ColumnType type, Integer lungime, Integer precizie, Integer pozitie, Integer displayWidth) {
        this.nume = nume;
        this.label = label;
        this.type = type;
        this.lungime = lungime;
        this.precizie = precizie;
        this.pozitie = pozitie;
        this.displayWidth = displayWidth;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public Integer getLungime() {
        return lungime;
    }

    public void setLungime(Integer lungime) {
        this.lungime = lungime;
    }

    public Integer getPrecizie() {
        return precizie;
    }

    public void setPrecizie(Integer precizie) {
        this.precizie = precizie;
    }

    public Integer getPozitie() {
        return pozitie;
    }

    public void setPozitie(Integer pozitie) {
        this.pozitie = pozitie;
    }

    public Integer getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(Integer displayWidth) {
        this.displayWidth = displayWidth;
    }
}
