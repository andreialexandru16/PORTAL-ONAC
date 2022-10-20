package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.Label;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.mvp.FlowView;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaxesTableContainer extends FlowViewDivContainer {


    private TableContainerDiv taxesTable =
            new TableContainerDiv("document.type.service.request.view.taxes.index.label", "document.type.service.request.view.taxes.documentname.label", 
            		"document.type.service.request.view.taxes.documentdescription.label", "document.type.service.request.view.taxes.option.label");

    public TaxesTableContainer(FlowView view) {
        super(view);

        //Needed Document table

        taxesTable.addClassName("table-responsive");
        taxesTable.setTableClassNames("table dark-head has-buttons");
        taxesTable.setTableHeaderClassNames("thead-dark");
        addClassNames("table-responsive", "mt-3");
        add(taxesTable);
        //Needed Document table

    }

    public void displayForMobile() {
        if(taxesTable.getClassNames().contains("table-responsive")) {
            HtmlContainer indexHeader = taxesTable.getTableHeadersMap().get("document.type.service.request.view.taxes.index.label");
            indexHeader.removeAll();
            taxesTable.removeClassName("table-responsive");
            taxesTable.addClassNames("table_mob_4col", "nr_crt", "table_scroll");
            taxesTable.removeTableClassNames();
            taxesTable.removeTableHeaderClassNames();
            removeClassNames("table-responsive", "mt-3");
        }
    }

    public void displayForDesktop() {
        if(taxesTable.getClassNames().contains("table_mob_4col")) {
            HtmlContainer indexHeader = taxesTable.getTableHeadersMap().get("document.type.service.request.view.taxes.index.label");
            indexHeader.add(new Label(I18NProviderStatic.getTranslation("document.type.service.request.view.taxes.index.label")));
            taxesTable.removeClassNames("table_mob_4col", "nr_crt");
            taxesTable.addClassName("table-responsive");
            taxesTable.setTableClassNames("table dark-head has-buttons");
            taxesTable.setTableHeaderClassNames("thead-dark");
            addClassNames("table-responsive", "mt-3");
        }
    }

    //Needed Document table

    public void setNeededDocumentsTable(List<DocObligatoriuExtra> documenteObligatoriiServiciu, boolean enableDocumentAction) {
        AtomicInteger index = new AtomicInteger(1);
        documenteObligatoriiServiciu.stream().forEach(neededDocument -> setNeededDocumentTableRow(neededDocument, index, enableDocumentAction));
    }

    private void setNeededDocumentTableRow(DocObligatoriuExtra docObligatoriuExtra, AtomicInteger index, boolean enableDocumentAction) {

        String fileNameDescription = docObligatoriuExtra.getDocObligatoriu().getDenumire().substring(0,  docObligatoriuExtra.getDocObligatoriu().getDenumire().lastIndexOf("."));

        fileNameDescription = fileNameDescription.substring(0,1).toUpperCase() + fileNameDescription.substring(1);

        taxesTable.addRow(new Label(index.getAndIncrement() + ""),
                new Label(fileNameDescription.replaceAll("_", " ")),
                new Label(docObligatoriuExtra.getDocObligatoriu().getDescriere()),
                docObligatoriuExtra.getDocObligatoriu().getCost()!=null ? new Label(docObligatoriuExtra.getDocObligatoriu().getCost()): new Label(""));
    }

    //Needed Document table



}
