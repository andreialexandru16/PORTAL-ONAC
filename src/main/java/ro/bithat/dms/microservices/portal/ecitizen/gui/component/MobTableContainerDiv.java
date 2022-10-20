package ro.bithat.dms.microservices.portal.ecitizen.gui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

import java.util.Map;
import java.util.Optional;

public class MobTableContainerDiv extends Div {


    private HtmlContainer tableContainerBody = new HtmlContainer("tbody");


    private HtmlContainer tableContainer = new HtmlContainer("table", /*tableContainerHeader,*/ tableContainerBody);

    public MobTableContainerDiv() {
        add(tableContainer);
    }


    public void setTableClassNames(String classNames) {
        tableContainer.getElement().setAttribute("class", classNames);
    }

    public void setTableBodyClassNames(String classNames) {
        tableContainerBody.getElement().setAttribute("class", classNames);
    }

    public void clearContent() {
        tableContainerBody.removeAll();
    }

    public void addRow(Map<String, Component> columns) {
        columns.keySet().forEach(label -> addRowColumn(label, columns.get(label)));
    }

    private void addRowColumn(String label, Component column) {
        HtmlContainer row = new HtmlContainer("tr");
        HtmlContainer columnLabel = new HtmlContainer("td");
        columnLabel.add(new Label(label));
        HtmlContainer columnDetail = new HtmlContainer("td");
        Optional<String> columnClassName= Optional.ofNullable(column.getElement().getAttribute("td-class"));
        if(columnClassName.isPresent()){
            columnDetail.addClassName(columnClassName.get());
        }
        columnDetail.add(column);
        row.add(columnLabel, columnDetail);
        tableContainerBody.add(row);
    }


}
