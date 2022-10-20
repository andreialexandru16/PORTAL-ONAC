package ro.bithat.dms.microservices.portal.ecitizen.home.gui.homebanner;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.textfield.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsDocumentService;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestRoute;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ps4ECitizenHomeBannerCarouselSearchPresenter
        extends PrepareModelFlowPresenter<Ps4ECitizenHomeBannerCarouselSearchView>  {

    private Logger logger = LoggerFactory.getLogger(Ps4ECitizenHomeBannerCarouselSearchPresenter.class);
    @Value("${show.header:true}")
    private Boolean showHeader;
    private List<String> carouselImagesUrls = new ArrayList<>();

    {
        carouselImagesUrls.add("PORTAL/assets/images/home-banner-3.jpeg");
        carouselImagesUrls.add("PORTAL/assets/images/home-banner-2.jpeg");
        carouselImagesUrls.add("PORTAL/assets/images/home-banner-1.jpeg");
    }

    private String searchText = "";

    @Autowired
    private PS4Service ps4Service;

    @Autowired
    private DmswsDocumentService documentService;

    @Override
    public void prepareModel(String state) {
        if(showHeader){
            logger.info("presenter state:" + state);
            carouselImagesUrls.forEach(this::addImageOnViewCarousel);
            getView().getSearchFormContainer().enablePresenterSearchEvents(documentService.getListaTipuriDocumente());

        }
          }

    public void addImageOnViewCarousel(String imageUrl) {
        getView().addCarouselSlide(imageUrl);
    }

    public void onSearchBtnClick(ClickEvent<Input> clickEvent) {
        logger.info("Search button click searchText:\t" + searchText + "\tlist return:\t"
                + doSearch(getView().getSearchFormContainer().getSearchTextValue()));
    }

    public void onSpeakSearchBtnClick(ClickEvent<Input> clickEvent) {
        logger.info("Speak Search button click");
        UI.getCurrent().getPage().executeJs("startMic($0)", getView().getElement());
    }
    public void onSearchTextChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textChangeEvent) {
        logger.info("Search text change:\t" + searchText + "\tlist return:\t"
                + doSearch(getView().getSearchFormContainer().getSearchTextValue()));
    }

    private Integer doSearch(String searchText) {
        List<Document> documents = documentService.getListaTipuriDocumenteFiltered(searchText);
        if(documents!=null && documents.size()>0){
            Document document= documents.get(0);
            if(document.getJspPage()!=null && !document.getJspPage().isEmpty()){
                UI.getCurrent().getPage().setLocation(document.getJspPage());
            }
            else{
                Map<String, Object> documentRequestParameters = new HashMap<>();
                documentRequestParameters.put("tipDocument", document.getIdDocumentType());
                documentRequestParameters.put("document", document.getId());
                UI.getCurrent().getPage().setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(documentRequestParameters, Ps4ECitizenServiceRequestRoute.class));
            }

        }
        return documents.size();
    }

}
