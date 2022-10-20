package ro.bithat.dms.passiveview.i18n;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import ro.bithat.dms.passiveview.i18n.flow.InternationalizeViewEngine;

import java.util.LinkedHashMap;
import java.util.Map;

public class MobTableFormI18n extends Div {


    private final Map<Component, String> formRowInputs;

    public MobTableFormI18n() {
        this(new LinkedHashMap<>());

    }

    public MobTableFormI18n(Map<Component, String> formRowInputs) {
        this.formRowInputs = formRowInputs;
        buildTableBody();
    }


    public void addFormRow(Component component, String label) {
        formRowInputs.put(component, label);
        buildTableRow(component);
    }

    public void buildTableBody() {
        removeAll();
        formRowInputs.keySet().forEach(this::buildTableRow);
        InternationalizeViewEngine.internationalize(this);
    }

    private void buildTableRow(Component component) {
        Div tableRowLabel = new Div();
        tableRowLabel.addClassName("title_1col");
        tableRowLabel.add(new Text(formRowInputs.get(component)));
        Div tableRowDetail = new Div();
        tableRowDetail.addClassName("txt_1col");
        tableRowDetail.add(component);
        add(new Div(tableRowLabel, tableRowDetail));
    }


}
