package ro.bithat.dms.passiveview.i18n;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.formlayout.FormLayout;
import org.w3c.dom.Element;
import ro.bithat.dms.passiveview.i18n.flow.InternationalizeViewEngine;

import java.util.LinkedHashMap;
import java.util.Map;

@Tag("table")
public class TableFormI18n extends HtmlContainer {


    private HtmlContainer tableBody = new HtmlContainer("tbody");

    private final Map<Component, String> formRowInputs;

    public TableFormI18n() {
        this(new LinkedHashMap<>());

    }

    public TableFormI18n(Map<Component, String> formRowInputs) {
        this.formRowInputs = formRowInputs;
        buildTableBody();
        add(tableBody);
    }

    public void addFormRow(Component component, String label) {
        formRowInputs.put(component, label);
        buildTableRow(component);
    }

    public void addFormSeparator (Component component) {
        HtmlContainer tableRowDetail = new HtmlContainer("th");
        tableRowDetail.getElement().setAttribute("colspan","2");
        tableRowDetail.add(component);
        tableBody.add(new HtmlContainer("tr",  tableRowDetail));

    }

    public void buildTableBody() {
        tableBody.removeAll();
        formRowInputs.keySet().forEach(this::buildTableRow);
        InternationalizeViewEngine.internationalize(this);
    }

    private void buildTableRow(Component component) {
        HtmlContainer tableRowLabel = new HtmlContainer("th");
        tableRowLabel.setText(formRowInputs.get(component));
        HtmlContainer tableRowDetail = new HtmlContainer("td");
        tableRowDetail.add(component);
        tableBody.add(new HtmlContainer("tr", tableRowLabel, tableRowDetail));
    }


}
