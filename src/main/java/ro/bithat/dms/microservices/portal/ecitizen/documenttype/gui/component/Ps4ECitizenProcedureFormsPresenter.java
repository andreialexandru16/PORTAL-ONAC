package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.FormulareService;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.Formular;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.Procedura;
import ro.bithat.dms.passiveview.ComponentValueChangeEventPresenterMethod;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;
import java.util.List;

public class Ps4ECitizenProcedureFormsPresenter extends PrepareModelFlowPresenter<Ps4ECitizenProcedureFormsView> {

    @Value("${ps4.ecitizen.workflow.status.changes.required}")
    private String idWorkflowStatusEditable;

    @Autowired
    private FormulareService formulareService;

    @Override
    public void prepareModel(String state) {
        getView().buildServiceType(formulareService.getProceduri(SecurityUtils.getToken()));
    }


    @ComponentValueChangeEventPresenterMethod(viewProperty = "serviceType")
    public void onServiceTypeChange(AbstractField.ComponentValueChangeEvent<ComboBox<Procedura>, Procedura> serviceTypeChangeEvent){
        List<Formular> userRequests= formulareService.getFormulare(SecurityUtils.getToken(), serviceTypeChangeEvent.getValue().getId()).getFormularList();
        getView().setMyRequestsTable(userRequests);
    }

    public String getIdWorkflowStatusEditable() {
        return idWorkflowStatusEditable;
    }

    public void setIdWorkflowStatusEditable(String idWorkflowStatusEditable) {
        this.idWorkflowStatusEditable = idWorkflowStatusEditable;
    }
}