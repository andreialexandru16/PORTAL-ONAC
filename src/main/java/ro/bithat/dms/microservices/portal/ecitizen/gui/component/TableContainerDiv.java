package ro.bithat.dms.microservices.portal.ecitizen.gui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class TableContainerDiv extends Div {

    private Map<String,HtmlContainer> tableHeadersMap= new LinkedHashMap<>();

    private HtmlContainer tableContainerBody = new HtmlContainer("tbody");

    private HtmlContainer tableHeaderRow = new HtmlContainer("tr");

    private HtmlContainer tableContainerHeader = new HtmlContainer("thead", tableHeaderRow);

    private HtmlContainer tableContainer = new HtmlContainer("table", tableContainerHeader, tableContainerBody);

    public TableContainerDiv() {
        add(tableContainer);
    }

    public TableContainerDiv(String... columnHeaders) {
        add(tableContainer);
        Stream.of(columnHeaders).forEach(this::addHeader);
    }


    public void setTableClassNames(String classNames) {
        tableContainer.getElement().setAttribute("class", classNames);
    }

    public void removeTableClassNames() {
        tableContainer.getElement().removeAttribute("class");
    }

    public void setTableHeaderClassNames(String classNames) {
        tableContainerHeader.getElement().setAttribute("class", classNames);
    }

    public void removeTableHeaderClassNames() {
        tableContainerHeader.getElement().removeAttribute("class");
    }

    public void setTableBodyClassNames(String classNames) {
        tableContainerBody.getElement().setAttribute("class", classNames);
    }

    public void removeTableBodyClassNames() {
        tableContainerBody.getElement().removeAttribute("class");
    }

    public void addHeader(String header) {
        HtmlContainer tableHeader= new HtmlContainer("th", new Label(header));
        tableHeadersMap.put(header,tableHeader);
        tableHeaderRow.add(tableHeader);
    }

    public void addHeader(String header, String classNames) {
        addHeader(header);
        setHeaderClassName(header,classNames);
    }
    public void setHeaderClassName(String header, String classNames){
        tableHeadersMap.get(header).getElement().setAttribute("class",classNames);
    }

    public void clearContent() {
        tableHeaderRow.removeAll();
        tableContainerBody.removeAll();
        tableHeadersMap.values().forEach(c -> tableHeaderRow.add(c));
    }

    public Map<String,HtmlContainer> getTableHeadersMap() {
        return tableHeadersMap;
    }

    public void addRow(Component... columns) {
        HtmlContainer row = new HtmlContainer("tr");
        Stream.of(columns).forEach(column -> addRowColumn(row, column));
        tableContainerBody.add(row);
    }

    private void addRowColumn(HtmlContainer row, Component column) {
        HtmlContainer columnDetail = new HtmlContainer("td");
        Optional<String> columnClassName= Optional.ofNullable(column.getElement().getAttribute("td-class"));
        if(columnClassName.isPresent()){
            columnDetail.addClassName(columnClassName.get());
        }
        columnDetail.add(column);
        row.add(columnDetail);
    }


}
