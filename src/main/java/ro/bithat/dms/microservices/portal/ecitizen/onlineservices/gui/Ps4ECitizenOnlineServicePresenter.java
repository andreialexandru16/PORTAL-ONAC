package ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRoute;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsDocumentService;
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

public class Ps4ECitizenOnlineServicePresenter extends PrepareModelFlowPresenter<Ps4ECitizenOnlineServiceView> {

    @Autowired
    private DmswsDocumentService documentService;

    @Value("${ps4.ecitizen.document.type.service.view.pageLimit:6}")
    private Integer pageLimit;

    private Map<Integer, List<Document>> pagesData = new ConcurrentHashMap<>();

    private Optional<Integer> currentPage;

    private Optional<String> keyword = Optional.empty();

    private Optional<String> serviceType = Optional.empty();

    private Optional<String> personType = Optional.empty();
    @Autowired
    private PS4Service ps4Service;
    @Override
    public void prepareModel(String state) {
        currentPage = QueryParameterUtil.getQueryParameter("pagina", Integer.class);
        List<Document> allDocuments = documentService.getListaTipuriDocumente();

        getView().getSearchFormContainer().enablePresenterSearchEvents(allDocuments);

        keyword = QueryParameterUtil.getQueryParameter("filtru");
        if(keyword.isPresent()) {
            getView().getSearchFormContainer().setSearchTextValue(keyword.get());
        }
        serviceType = QueryParameterUtil.getQueryParameter("tipServiciu");
       //TODO get document by name and set service type value
        /* if(serviceType.isPresent()) {
            getView().setServiceTypeValue(serviceType.get());
        }*/
        personType = QueryParameterUtil.getQueryParameter("tipPersoana");
        if(personType.isPresent()) {
            getView().setPersonTypeValue(personType.get());
        }

        if (keyword.isPresent()) {
            setPagesData(documentService.getListaTipuriDocumenteFiltered(keyword.get()));
        } else {
            setPagesData(allDocuments);
        }


        getView().buildDocumentPageBodyAndPagination(getPagesData().get(getPage()), getPage(), getPagesData().size());
        getView().buildServiceType(SecurityUtils.getAllDocumentTypes());

    }

    public void onSearchBtnClick(ClickEvent<Input> clickEvent) {
        getLogger().info("Search button click searchText:\t" + getView().getSearchFormContainer().getSearchTextValue() + "\tlist return:\t" + doSearch(getView().getSearchFormContainer().getSearchTextValue()));
    }

    public void onSearchTextChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textChangeEvent) {
        getLogger().info("Search text change:\t" + getView().getSearchFormContainer().getSearchTextValue() + "\tlist return:\t" + doSearch(getView().getSearchFormContainer().getSearchTextValue()));
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
    private Integer doSearch(String searchText) {
        List<Document> documents = Optional.ofNullable(searchText).isPresent() && !searchText.isEmpty() ?
                documentService.getListaTipuriDocumenteFiltered(searchText) : documentService.getListaTipuriDocumente();
        setPagesData(documents);
        getView().buildDocumentPageBodyAndPagination(getPagesData().get(getPage()), getPage(), getPagesData().size());
        return documents.size();
    }


    public Integer getPageLimit() {
        return pageLimit;
    }

    public Map<Integer, List<Document>> getPagesData() {
        return pagesData;
    }

    public Optional<Integer> getCurrentPage() {
        return currentPage;
    }

    public int getPage() {
        return currentPage.isPresent() ? (currentPage.get() < pagesData.size() ? currentPage.get() : 0) : 0;
    }

    protected void setPagesData(List<Document> documents) {
        pagesData.clear();
        int chunkSize = pageLimit;
        AtomicInteger counter = new AtomicInteger();
        if(chunkSize > 0) {
            documents.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).forEach(this::setPageData);
        } else {
            setPageData(0, documents);
        }

    }


    protected void setPageData(Integer page, List<Document> documents) {
        pagesData.put(page, documents);
    }

}