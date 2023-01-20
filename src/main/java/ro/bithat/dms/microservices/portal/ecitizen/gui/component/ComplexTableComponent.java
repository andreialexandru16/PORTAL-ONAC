package ro.bithat.dms.microservices.portal.ecitizen.gui.component;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.shared.Registration;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import ro.bithat.dms.smartform.gui.attribute.binder.AttributeBinderBean;
import ro.bithat.dms.smartform.gui.attribute.binder.MandatoryAttributeBeanPropertyValidator;
import ro.bithat.dms.smartform.gui.attribute.binder.StringAttributeBinderBean;

import java.util.*;
import java.util.stream.Stream;

public class ComplexTableComponent extends Div implements HasValue<AbstractField.ComponentValueChangeEvent<ComplexTableComponent, String>, String> {

    private Map<String,HtmlContainer> tableHeadersMap= new LinkedHashMap<>();
    private Map<String,HtmlContainer> tableFootersMap= new LinkedHashMap<>();
    private Map<String,HtmlContainer> tableFootersMapIds= new HashMap<>();
    private List<Component> componentList = new ArrayList<>();
    private HtmlContainer tableTopButtons = new HtmlContainer("tr");

    private HtmlContainer tableContainerBody = new HtmlContainer("tbody");

    private HtmlContainer tableHeaderRow = new HtmlContainer("tr");
    private HtmlContainer tableFooterRow = new HtmlContainer("tr");

    private HtmlContainer tableContainerHeader = new HtmlContainer("thead",tableTopButtons, tableHeaderRow);
    private HtmlContainer tableContainerFooter = new HtmlContainer("tfoot",tableTopButtons, tableFooterRow);

    private HtmlContainer tableContainer = new HtmlContainer("table", tableContainerHeader, tableContainerBody,tableContainerFooter);

    public ComplexTableComponent() {
        add(tableContainer);
    }

    public ComplexTableComponent(String... columnHeaders) {
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
    public void addFooter(String footer, String id) {
        HtmlContainer tableFooter= new HtmlContainer("th");
        tableFooter.setText(footer);
        tableFooter.setId(id);
        tableFooter.setClassName("text-border-gray");
        tableFootersMap.put(footer,tableFooter);
        tableFootersMapIds.put(id,tableFooter);
        tableFooterRow.add(tableFooter);
    }
    public void addFooter(String footer) {
        HtmlContainer tableFooter= new HtmlContainer("th");
        tableFooter.setText(footer);
        tableFooter.setClassName("text-border-gray");

        tableFootersMap.put(footer,tableFooter);
        tableFooterRow.add(tableFooter);
    }
    public Component getThFooterByid(String id) {

        return tableFootersMapIds.get(id);
    }
    public Optional<Component> getChildByid(String id) {

        return this.getChildren().filter(c -> c.getId().isPresent() && c.getId().get().equals(id)).findFirst();
    }
    public long getNrRows(){

        return tableContainerBody.getChildren().count();
    }
    public Map<String, HtmlContainer> getThFooters() {
        return tableFootersMapIds;
    }
    public void addHeaderWithClass(String header, String className) {
        HtmlContainer tableHeader= new HtmlContainer("th", new Label(header));
        tableHeader.addClassName(className);
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
    public void removeFooter() {
        tableFooterRow.removeAll();
    }
    public void addContainerFooter() {
        tableContainer.add(tableContainerFooter);
    }
    public Map<String,HtmlContainer> getTableHeadersMap() {
        return tableHeadersMap;
    }

    public void addRow(Component... columns) {
        HtmlContainer row = new HtmlContainer("tr");
        Stream.of(columns).forEach(column -> addRowColumn(row, column));
        tableContainerBody.add(row);
    }
    public void  addNewRow(HtmlContainer row) {
        tableContainerBody.add(row);
    }
    public void  addNewRowFooter(HtmlContainer row) {
        tableContainerFooter.add(row);
    }
    public void  removeRow(HtmlContainer row) {
        tableContainerBody.remove(row);
    }
    public void addRowColumn(HtmlContainer row, Component column) {
        HtmlContainer columnDetail = new HtmlContainer("td");
        Optional<String> columnClassName= Optional.ofNullable(column.getElement().getAttribute("td-class"));
        if(columnClassName.isPresent()){
            columnDetail.addClassName(columnClassName.get());
        }
        columnDetail.add(column);
        componentList.add(column);
        row.add(columnDetail);
    }


    public void addButton(ClickNotifierAnchor button) {
        HtmlContainer buttons = new HtmlContainer("th");
        buttons.add(button);
        tableTopButtons.add(buttons);
    }

    @Override
    public void setValue(String s) {

    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<ComplexTableComponent, String>> valueChangeListener) {
        return null;
    }

    @Override
    public void setReadOnly(boolean b) {

    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean b) {

    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    public HtmlContainer getTableContainerBody() {
        return tableContainerBody;
    }

    public void setTableContainerBody(HtmlContainer tableContainerBody) {
        this.tableContainerBody = tableContainerBody;
    }

    public void clearContainerBody() {
        this.tableContainerBody.removeAll();
    }

    public List<Component> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
    }
}
