package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECitizenMyInvoicesView extends ContentContainerView<Ps4ECitizenMyInvoicesPresenter> {
    private SearchContainer searchFormContainer = new SearchContainer(this);


    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv(
                    "myaccount.my.requests.view.service.index.label",
                    "myaccount.my.requests.view.service.nrFactura.label",
                    "myaccount.my.requests.view.service.serieFactura.label",
                    "myaccount.my.requests.view.service.valoareFactura.label",
                    "myaccount.my.requests.view.service.monedaFactura.label",
                    "myaccount.my.requests.view.service.autoritateFactura.label",
                    "myaccount.my.requests.view.service.operatorFactura.label"
            );
    private Div serviceDocumentsTableContainer = new Div(serviceDocumentsTable);

    private MobTableContainerDiv mobServiceDocumentsTable = new MobTableContainerDiv();

    private Div formContainer = new Div(serviceDocumentsTableContainer, mobServiceDocumentsTable);
    private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
    private Div buttonContainer = new Div(anchorContainer);



    @Override
    public void beforeBinding() {
        if(getPresenter().getShowPaymentCol()!=null && getPresenter().getShowPaymentCol().equals("true") ){
            serviceDocumentsTable.addHeader( "myaccount.my.requests.view.service.paymentvalue.label");
        }
        //13.07.2021 - NG - ANRE - adaugare camp de cautare
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.myrequests.page.title"));
        setServicesListHeaderIcon("/icons/document.png");
        formContainer.addClassNames("my_documents", "table_scroll");

        styleNewRequestButton();

        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap_20");
       // getServiceListContainer().add(formContainer, clearFix, buttonContainer);
        getServiceListContainer().add(formContainer, clearFix);
        styleMyRequestsTable();

    }

    @Override
    protected void displayForWidth(int width) {
        if(width <= 700) {
            mobServiceDocumentsTable.getStyle().remove("display");
            serviceDocumentsTableContainer.getStyle().set("display", "none");
        } else {
            if(serviceDocumentsTableContainer.getStyle().has("display")) {
                serviceDocumentsTableContainer.getStyle().remove("display");
            }
            mobServiceDocumentsTable.getStyle().set("display", "none");
        }
    }
    public SearchContainer getSearchFormContainer() {
        return searchFormContainer;
    }

    public void setMyRequestsTable(List<PortalFile> myDocuments) {
        serviceDocumentsTable.clearContent();
        AtomicInteger index = new AtomicInteger(1);
        myDocuments.stream().forEach(document -> setDocumentTableRow(document, index));
    }

    private void setDocumentTableRow(PortalFile document, AtomicInteger index) {


        ClickNotifierAnchor categoryLink = constructClickNotifierAnchor(document.getClasaDocument(), "inherit-text-color");
        ClickNotifierAnchor mobCategoryLink = constructClickNotifierAnchor(document.getClasaDocument(), "inherit-text-color");

       /* Span categoryIcon= constructSpan("pictograma");*/
        Span mobCategoryIcon= constructSpan("pictograma");

        /*Div categoryDetail=new Div(categoryIcon,categoryLink);*/
        Div categoryDetail=new Div(categoryLink);

        Div mobCategoryDetail=new Div(mobCategoryIcon,mobCategoryLink);
        HtmlContainer iconDocument = document.getNume() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();
        HtmlContainer mobIconDocument = document.getNume() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();


        Span numarFactura=new Span();
        Span mobnumarFactura=new Span();
        numarFactura.setText(document.getNumarFactura());
        mobnumarFactura.setText(document.getNumarFactura());
        Div numarFacturaDetail=new Div(iconDocument,numarFactura);
        Div mobnumarFacturaDetail=new Div(mobIconDocument,mobnumarFactura);

        Span serieFactura=new Span();
        Span mobSerieFactura=new Span();
        serieFactura.setText(document.getSerieFactura());
        mobSerieFactura.setText(document.getSerieFactura());
        Div serieFacturaDetail=new Div(iconDocument,serieFactura);
        Div mobSerieFacturaDetail=new Div(mobIconDocument,mobSerieFactura);

        Span valoareFactura=new Span();
        Span mobvaloareFactura=new Span();
        valoareFactura.setText(document.getValoareFactura());
        mobvaloareFactura.setText(document.getValoareFactura());
        Div valoareFacturaDetail=new Div(iconDocument,valoareFactura);
        Div mobvaloareFacturaDetail=new Div(mobIconDocument,mobvaloareFactura);

        Span monedaFactura=new Span();
        Span mobmonedaFactura=new Span();
        monedaFactura.setText(document.getMonedaFactura());
        mobmonedaFactura.setText(document.getMonedaFactura());
        Div monedaFacturaDetail=new Div(iconDocument,monedaFactura);
        Div mobmonedaFacturaDetail=new Div(mobIconDocument,mobmonedaFactura);

        Span acFactura=new Span();
        Span mobacFactura=new Span();
        acFactura.setText(document.getAcFactura());
        mobacFactura.setText(document.getAcFactura());
        Div acFacturaDetail=new Div(iconDocument,acFactura);
        Div mobacFacturaDetail=new Div(mobIconDocument,mobacFactura);

        Span frFactura=new Span();
        Span mobfrFactura=new Span();
        frFactura.setText(document.getFurnizorFactura());
        mobfrFactura.setText(document.getFurnizorFactura());
        Div frFacturaDetail=new Div(iconDocument,frFactura);
        Div mobfrFacturaDetail=new Div(mobIconDocument,mobfrFactura);

        int rowIndex = index.getAndIncrement();

        if(getPresenter().getShowPaymentCol()!=null && getPresenter().getShowPaymentCol().equals("true") ){
            serviceDocumentsTable.addRow(new Label( rowIndex + ""),
                    numarFacturaDetail,
                    serieFacturaDetail,
                    valoareFacturaDetail,
                    monedaFacturaDetail,
                    acFacturaDetail,
                    frFacturaDetail
            );
        }else{
            serviceDocumentsTable.addRow(new Label( rowIndex + ""),
                    numarFacturaDetail,
                    serieFacturaDetail,
                    valoareFacturaDetail,
                    monedaFacturaDetail,
                    acFacturaDetail,
                    frFacturaDetail
            );
        }



        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("myaccount.my.requests.view.service.index.label", new Label(rowIndex + ""));
        mobileRowMap.put("myaccount.my.requests.view.service.nrFactura.label", mobnumarFacturaDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.serieFactura.label", mobSerieFacturaDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.valoareFactura.label", mobvaloareFacturaDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.monedaFactura.label", mobmonedaFacturaDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.autoritateFactura.label", mobacFacturaDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.operatorFactura.label", mobfrFacturaDetail);


        mobServiceDocumentsTable.addRow(mobileRowMap);

    }


    private void styleMyRequestsTable() {
        mobServiceDocumentsTable.addClassNames("table_mob_2col_9", "table_anre_responsive");
        serviceDocumentsTableContainer.addClassNames("table-responsive", "table-icons", "table-blue-line", "mb-5", "table_anre");
        serviceDocumentsTable.setTableClassNames("table mb-0");
    }
    private void styleNewRequestButton() {
        buttonContainer.addClassNames("new_request");
        anchorContainer.setHref("javascript:void(0);");
        anchorContainer.addClassNames("btn","btn-secondary","btn-block","font-weight-bold");
        anchorContainer.add(new Text("myaccount.my.requests.view.service.newrequest.button"));
        HtmlContainer iconArrowNext= new HtmlContainer("i");
        iconArrowNext.addClassNames("fas","fa-arrow-alt-circle-right");
        anchorContainer.add(iconArrowNext);
    }




}
