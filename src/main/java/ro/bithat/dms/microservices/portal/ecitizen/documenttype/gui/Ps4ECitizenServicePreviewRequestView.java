package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouteConfiguration;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component.ServiceRequestStepsFooterButtonController;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbWizardRequestView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.LoadingSpinner;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui.Ps4ECitizenConsultationProjectsRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveReq;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveResp;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.security.UserWithUserToken;
import ro.bithat.dms.smartform.gui.DocumentaSmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;

import java.io.File;
import java.util.*;

public class Ps4ECitizenServicePreviewRequestView extends ContentContainerView<Ps4ECitizenServicePreviewRequestPresenter> {


    ///Btn footer container

    private long sendToPrintTimeMillis;

    private DocumentaSmartForm dmsSmartForm = new DocumentaSmartForm();

    private LoadingSpinner loadingSpinner = new LoadingSpinner();


    @Override
    protected void buildView() {


        super.buildView();

        SmartFormSupport.setIsReadonly(false);
        UI.getCurrent().getPage().addJavaScript("frontend/js/gmapAddr.js");
        //UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/dateHourPicker.js");
        UI.getCurrent().getPage().addJavaScript("https://maps.googleapis.com/maps/api/js?key=" + getPresenter().getGoogleMapsApiKey() + "&language=ro&region=RO");
        UI.getCurrent().getPage().addStyleSheet("website/assets/css/main.css");
        loadingSpinner.show();
        loadingSpinner.setId("loading-spinner");
        loadingSpinner.getStyle().set("display", "none");

    }

    @ClientCallable
    public void swalInfoAck(String url) {

        VaadinClientUrlUtil.setTopLocation(url);
    }

    @Override
    public void beforeBinding() {
        setServicesListHeaderIcon("/icons/document.png");
        getServiceListContainer().addClassName("new_request");
        getServiceListContainer().add(dmsSmartForm);


//        dmsSmartForm.addClassName("canvas_div_pdf");
        Button printTopBtn = new Button("Print");
        printTopBtn.getStyle().set("position", "absolute");
        printTopBtn.getStyle().set("display", "block");
        printTopBtn.getStyle().set("right", "25px");
        printTopBtn.getStyle().set("top", "5px");
        printTopBtn.addClickListener(e
                -> {
            btnPrintSmartFormPdf();
        });

        getServiceListHeader().getStyle().set("position", "relative");
        getServiceListHeader().add(printTopBtn);
    }

    public void buildDmsSmartForm(AttributeLinkList attributeLinkList, String workFlowState) {
        if (Optional.ofNullable(attributeLinkList).isPresent()
                && Optional.ofNullable(attributeLinkList.getAttributeLink()).isPresent()
                && !attributeLinkList.getAttributeLink().isEmpty()) {

            dmsSmartForm.buildSmartForm(attributeLinkList);
            dmsSmartForm.setLabelsClassNames("pr-2 mb-0 font-weight-bold");
        }

    }

    public void setAttrDocumentaSmartForm(String idAtributeAscunse) {
        dmsSmartForm.setParamAtributeAscunsePortal(idAtributeAscunse);
    }

    public DocAttrLinkList getDocAttrLinkList() {
        return dmsSmartForm.getDocAttrLinkList();
    }

    public void setServiceNameAndRegisterPreviousStep(String serviceName, String workFlowState) {
        setContentPageTile(getTranslation("preview.form"));
    }

    public String getSmartFormHtml() {
        return dmsSmartForm.getHtml();
    }

    public boolean validateSmartForm() {
        return dmsSmartForm.validate();
    }

    @ClientCallable
    public void swalInfoParam2Ack(String url) {

        VaadinClientUrlUtil.setLocation(url);
    }
    public void btnPrintSmartFormPdf() {
        getLogger().info("printSmartFormPdf command sending to client");
        sendToPrintTimeMillis = System.currentTimeMillis();
        if(getPresenter().getFileId().isPresent() || getPresenter().getRequestFileid().isPresent()){
            RunJasperByTipDocAndSaveResp runJasperByTipDocAndSaveResp = getPresenter().getIdFisierJasper();
            if (runJasperByTipDocAndSaveResp.getStatus()!=null && runJasperByTipDocAndSaveResp.getStatus().equals("OK")) {
                UI.getCurrent().getPage().executeJs("  $UTIL.downloadFileByVaadin($0,$1)", runJasperByTipDocAndSaveResp.getFileName(),
                        runJasperByTipDocAndSaveResp.getDownloadLink());

            }
            else {
                SmartFormSupport.makeSmartFormPrintable(dmsSmartForm);
                UI.getCurrent().getPage().executeJs("printSmartFormPdf($0, $1, $2);",
                        dmsSmartForm.getElement(), getElement(), getPresenter().getDocument().get().getDenumire()+".pdf");
            }
        }
        else {
            SmartFormSupport.makeSmartFormPrintable(dmsSmartForm);
            UI.getCurrent().getPage().executeJs("printSmartFormPdf($0, $1, $2);",
                    dmsSmartForm.getElement(), getElement(), getPresenter().getDocument().get().getDenumire()+".pdf");
        }
    }
    public void printSmartFormPdf() {
        getLogger().info("printSmartFormPdf command sending to client");
        sendToPrintTimeMillis = System.currentTimeMillis();
        showLoading();
        RunJasperByTipDocAndSaveResp runJasperByTipDocAndSaveResp = getPresenter().getIdFisierJasper();
        if (runJasperByTipDocAndSaveResp.getStatus()!=null && runJasperByTipDocAndSaveResp.getStatus().equals("OK")) {
            byte[] decoded = getPresenter().getPdfByIdFisier(runJasperByTipDocAndSaveResp.getIdFisier());
            String encoded = Base64.getEncoder().encodeToString(decoded);
            afterPrintSmartFormToPdf(encoded,0);

        } else {
            SmartFormSupport.makeSmartFormPrintable(dmsSmartForm);
            UI.getCurrent().getPage().executeJs("getSmartFormPdf($0, $1);", dmsSmartForm.getElement(), getElement());
        }
    }

    @ClientCallable
    public void afterPrintSmartForm(String pdfData) {
        SmartFormSupport.exitPrintingMode(dmsSmartForm);
        closeLoading();
    }

    @ClientCallable
    public void afterPrintSmartFormToPdf(String pdf, int printSmartFormExecutionTime) {
        getLogger().info("afterPrintSmartFormPdf command return to client");
        getLogger().info("serverSendReceiveExecutionTime:\t" + (System.currentTimeMillis() - sendToPrintTimeMillis));
        getLogger().info("printSmartFormExecutionTime:\t" + printSmartFormExecutionTime);
        SmartFormSupport.exitPrintingMode(dmsSmartForm);
        closeLoading();
        if (pdf.contains("ERROR")) {
            getLogger().error(pdf);
            UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare generare date client! Va rugam reincercati!");
        } else {
            getPresenter().savePdf(pdf);
        }
    }

}
