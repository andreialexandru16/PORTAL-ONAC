package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.rehabilitationreq.backend.DmswsRehabilitationService;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.ComponentValueChangeEventPresenterMethod;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.security.SecurityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Ps4ECitizenServicePresenter extends PageableDocumentTypePresenter<Ps4ECitizenServiceView> {

    private Optional<String> keyword = Optional.empty();

    private Optional<String> serviceType = Optional.empty();

    private Optional<String> personType = Optional.empty();
    @Autowired
    PS4Service ps4Service;
    @Value("${ps4.ecitizen.class.reabilitare.id}")
    private Integer classReabilitareId;
    @Value("${ps4.ecitizen.class.audiente.id}")
    private Integer classAudienteId;



    @Override
    public void afterPrepareModel(String state) {
        keyword = QueryParameterUtil.getQueryParameter("filtru", String.class);
        if(keyword.isPresent()) {
            getView().setKeywordValue(keyword.get());
        }
        serviceType = QueryParameterUtil.getQueryParameter("tipServiciu");
        //TODO get document by name and set service type value

        /*if(serviceType.isPresent()) {
            getView().setServiceTypeValue(serviceType.get());
        }*/
        personType = QueryParameterUtil.getQueryParameter("tipPersoana");
        if(personType.isPresent()) {
            getView().setPersonTypeValue(personType.get());
        }

        if (keyword.isPresent() || serviceType.isPresent() || personType.isPresent()) {
            setPagesData(getPs4Service().getDocuments(getDocumentType(),
                    Optional.ofNullable(keyword.get()).orElse(null),
                    Optional.ofNullable(serviceType.get()).orElse(null),
                    Optional.ofNullable(personType.get()).orElse(null)));
        } else {
            setPagesData(getPs4Service().getDocuments(getDocumentType()));
        }


        getView().buildDocumentPageBodyAndPagination(getDocumentClass().get(), getPagesData().get(getPage()), getPage(), getPagesData().size());
        getView().buildServiceTypeCombobox(SecurityUtils.getAllDocumentTypes());
        if (ps4Service.checkIfHasRole(SecurityUtils.getToken(), "PROGRAMARI_AUDIENTE_ADMIN").getInfo().equals("true")) {
            if (getDocumentType() != null &&  getDocumentType().equals(classAudienteId)) {

                getView().buildVizualizareAdmAudiente();
            }
        }
    }


    @ClickEventPresenterMethod(viewProperty = "filterButton")
    public void onFilterAction(ClickEvent<HtmlContainer> clickEvent) {
        getLogger().info("filter action trigger");
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        filterPageParameters.put("filtru", getView().getKeywordValue());
        filterPageParameters.put("tipServiciu", getView().getServiceTypeValue());
        filterPageParameters.put("tipPersoana", getView().getPersonTypeValue());
        UI.getCurrent().getPage().setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRoute.class));
    }
    @ComponentValueChangeEventPresenterMethod(viewProperty = "serviceType")
    public void onServiceTypeChange(AbstractField.ComponentValueChangeEvent<ComboBox<Document>, TipDocument> serviceTypeChangeEvent){
        if(!serviceTypeChangeEvent.getHasValue().isEmpty()){
            getLogger().info("Document name: \t"+ serviceTypeChangeEvent.getValue().getDenumire());
            Map<String, Object> documentRequestParameters = new HashMap<>();
            documentRequestParameters.put("tipDocument", serviceTypeChangeEvent.getValue().getId());
            UI.getCurrent().getPage().setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(documentRequestParameters, Ps4ECitizenServiceRoute.class));

        }
    }

    public Integer getClassReabilitareId() {
        return classReabilitareId;
    }
    public Integer getClassAudienteId() {
        return classAudienteId;
    }
}
