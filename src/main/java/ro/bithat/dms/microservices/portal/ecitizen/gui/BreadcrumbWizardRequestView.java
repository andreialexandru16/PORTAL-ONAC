package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.component.html.*;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

public class BreadcrumbWizardRequestView extends DivFlowViewBuilder<BreadcrumbWizardRequestPresenter> {

    private Div serviceDescription = new Div();
    private Div newRequest = new Div();
    private Div attachFiles = new Div();
    private Div requestReview = new Div();

    private Div breadcrumbNav = new Div(serviceDescription,newRequest, attachFiles, requestReview);

    @Override
    protected void buildView() {
        addClassName("bredcrumbs_nav");
        //breadcrumbNav.addClassName("bredcrumbs_nav");
        setContentCrumb();
        add(serviceDescription,newRequest, attachFiles, requestReview);
    }

    public void clearCrumbs() {
        breadcrumbNav.removeAll();
    }

    public void setCurrentPageActive(String currentPageTitle) {
        if(currentPageTitle.equals("detaliu-serviciu")){
            serviceDescription.addClassNames("active");
         }
         else  if(currentPageTitle.equals("solicitare-noua")){
            serviceDescription.addClassNames("completed");
            newRequest.addClassNames("active");
        }
        else  if(currentPageTitle.equals("solicitare-noua-atasamente") || currentPageTitle.equals("atasamente-doc-raspuns")){
            serviceDescription.addClassNames("completed");
            newRequest.addClassNames("completed");
            attachFiles.addClassNames("active");
        }
        else  if(currentPageTitle.equals("solicitare-revizie-finala")){
            serviceDescription.addClassNames("completed");
            newRequest.addClassNames("completed");
            attachFiles.addClassNames("completed");
            requestReview.addClassNames("active");
        }

    }

    public void setContentCrumb() {
         // Descriere Serviciu
        Span spanDescriereServiciu= new Span();
        spanDescriereServiciu.addClassName("icon_file_w");
        Paragraph pDescriereServiciu= new Paragraph("ps4.ecetatean.breadcrumb.document.type.title");

        serviceDescription.add(spanDescriereServiciu,pDescriereServiciu);
        // End Descriere Serviciu


        // Completare formular
        Span spanCompletareForm= new Span();
        spanCompletareForm.addClassName("icon_file_w_edit");
        Paragraph pCompletareForm= new Paragraph("document.type.service.new.request.form.step.title");

        newRequest.add(spanCompletareForm,pCompletareForm);
        // End Completare formular

        // Atasare documente
        Span spanAtasareDoc= new Span();
        spanAtasareDoc.addClassName("icon_paperclip_w");
        Paragraph pAtasareDoc= new Paragraph("document.type.service.attach.files.step.title");

        attachFiles.add(spanAtasareDoc,pAtasareDoc);
        // End Atasare documente

        // Review si lansare
        Span spanReview= new Span();
        spanReview.addClassName("icon_file_w_check");
        Paragraph pReview= new Paragraph("document.type.service.review.request.step.title");

        requestReview.add(spanReview,pReview);
        // End Review si lansare
    }
}
