package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement

public class ExportXlsxReq {

    private String sql;
    private List<Object> params;
    private List<String> columns;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
}
