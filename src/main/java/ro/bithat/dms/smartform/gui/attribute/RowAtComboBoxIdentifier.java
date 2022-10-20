package ro.bithat.dms.smartform.gui.attribute;

public class RowAtComboBoxIdentifier {
    private Integer row;
    private Integer identifier;

    public RowAtComboBoxIdentifier(Integer row, Integer identifier) {
        this.row = row;
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RowAtComboBoxIdentifier that = (RowAtComboBoxIdentifier) o;

        if (!row.equals(that.row)) return false;
        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        int result = row.hashCode();
        result = 31 * result + identifier.hashCode();
        return result;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }
}
