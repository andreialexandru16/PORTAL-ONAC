package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component.DocObligatoriuExtraTableContainer;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component.ServiceRequestStepsFooterButtonController;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbWizardRequestView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.ClientDetailsObserver;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.WindowResizeObserver;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;

import java.util.List;
import java.util.Optional;

public class Ps4ECitizenServiceAttacheFileView extends ContentContainerView<Ps4ECitizenServiceAttacheFilePresenter> implements ClientDetailsObserver, WindowResizeObserver {



    @FlowComponent("request-wizard-breadcrumb")
    private BreadcrumbWizardRequestView breadcrumbRequestWizardView;

    @FlowComponent("online-service-breadcrumb")
    private BreadcrumbView breadcrumbView;

    ///Btn footer container
    private ServiceRequestStepsFooterButtonController btnFooterContainer = new ServiceRequestStepsFooterButtonController(this);
    ///Btn footer container

    //Needed Document table
    private DocObligatoriuExtraTableContainer serviceNeededDocumentsContainer = new DocObligatoriuExtraTableContainer(this);
    //Needed Document table


    @Override
    public void beforeBinding() {
        getServiceListContainer().addClassNames("detaliu-serviciu", "solicitare-document");
        removeServiceListHeader();
        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap-20");
        getServiceListContainer().add(serviceNeededDocumentsContainer, clearFix, btnFooterContainer);
    }

    public Optional<String> getServiceNeededDocumentsContainer() {
        return serviceNeededDocumentsContainer.getId();
    }

    public void setServiceNeededDocumentsContainer(DocObligatoriuExtraTableContainer serviceNeededDocumentsContainer) {
        this.serviceNeededDocumentsContainer = serviceNeededDocumentsContainer;
    }

    public void setServiceNameAndRegisterPreviousStep(String serviceName) {
        if(getPresenter().isPortalFileEditable() || getPresenter().portalFileNeedChanges()) {
            if(!getPresenter().getWorkflowStateTitle().isEmpty()) {
                btnFooterContainer.registerPresenterNextStepMethod("document.type.service.request.view.save.action.label",
                        "document.type.service.request.next.step.title", "document.type.service.new.neededfiles.step.title");
            } else {
                btnFooterContainer.registerPresenterNextStepMethod("document.type.service.request.next.step.title", "document.type.service.new.neededfiles.step.title");
            }
            btnFooterContainer.registerPresenterPreviousStepMethod("document.type.service.request.previous.step.title", serviceName);
        }

        breadcrumbView.setCurrentPageTitle(getPresenter().getPortalFileTitle());
    }


    @ClientCallable
    public void swalErrorAck() {
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class));
    }

    //Needed Document table

    public void setNeededDocumentsTable(List<DocObligatoriuExtra> documenteObligatoriiServiciu) {

        serviceNeededDocumentsContainer.setNeededDocumentsTable(documenteObligatoriiServiciu, true);
        if(Optional.ofNullable(getClientWidth()).isPresent()) {
            displayForWidth(getClientWidth());
        }
        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");

    }

    //Needed Document table

    protected void displayForWidth(int width) {
        if(width <= 700) {
            serviceNeededDocumentsContainer.displayForMobile();
                Div subtitleView  = new Div();
                subtitleView.add(new Text("Pentru generare fisier cu scanarea de pagini multiple utilizati o aplicatie dedicata de mobile. Exemplu: click "));
                Anchor anchorApp= new Anchor("https://play.google.com/store/apps/details?id=com.simplescan.scanner&hl=ro");
                anchorApp.setText(" aici ");
                subtitleView.add(anchorApp);
                subtitleView.add(new Text(" pentru descarcare aplicatie."));

                subtitleView.addClassNames("subtitle_underline", "text-center");

            getServiceListContainer().remove(btnFooterContainer);
            getServiceListContainer().add(subtitleView,btnFooterContainer);


        }else {
            serviceNeededDocumentsContainer.displayForDesktop();
        }
    }

    public String getValueFileDescription(String idComponenta){

        String val="";

        TextArea textArea= null;
        try{
            textArea= serviceNeededDocumentsContainer.getTextAreas().stream().filter(componenta -> componenta.getId().get().equals(idComponenta)).findFirst().get();
        }catch (Exception e){
            textArea=null;
        }
        if(textArea!=null){
            val = textArea.getValue();
        }
        else{
            val=null;
        }

        return val;
    }
    public void buildBreadcrumbsWizard() {
        getServiceListContainer().getStyle().set("margin-top","150px");
        breadcrumbRequestWizardView.setCurrentPageActive( RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenServiceAttacheFileRoute.class));
        addComponentAsFirst(breadcrumbRequestWizardView);
    }
}
