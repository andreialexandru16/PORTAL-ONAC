package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.FormulareService;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsDocumentService;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRoute;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.FisierDraftExtended;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.Formular;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.Procedura;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.ProceduraList;
import ro.bithat.dms.passiveview.ComponentValueChangeEventPresenterMethod;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Ps4ECitizenProcedureFormsPresenter extends PrepareModelFlowPresenter<Ps4ECitizenProcedureFormsView> {

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

}