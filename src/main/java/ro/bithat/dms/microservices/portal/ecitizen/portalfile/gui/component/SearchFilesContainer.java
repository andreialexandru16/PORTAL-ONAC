package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui.component;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.i18n.flow.InternationalizeViewEngine;
import ro.bithat.dms.passiveview.mvp.FlowView;

public class SearchFilesContainer extends FlowViewDivContainer implements LocaleChangeObserver  {

    private Logger logger = LoggerFactory.getLogger(SearchFilesContainer.class);

    private final static String PRESENTER_SPEAK_SEARCH_BTN_ACTION = "onSpeakSearchBtnClick";

    private final static String PRESENTER_SEARCH_BTN_ACTION = "onSearchBtnClick";

    private final static String PRESENTER_SEARCH_TEXT_ACTION = "onSearchTextChanged";

    private TextField searchText = new TextField();

    private HorizontalLayout searchTextContainer = new HorizontalLayout(searchText);

    private Div searchContainer = new Div(searchTextContainer);

    private Input searchBtn = new Input();

    private Div formGroup = new Div(searchContainer, searchBtn);


    public SearchFilesContainer(FlowView view) {
        super(view);
        add(formGroup);
        formGroup.addClassNames("form-group", "row", "no-gutters");
        searchContainer.addClassNames("col-sm-8", "col-md-9", "col-lg-10", "search_container");

        searchBtn.setValue(I18NProviderStatic.getTranslation("home.banner.carousel.search.view.search.button.label"));
        searchBtn.setType("button");
        searchBtn.addClassNames("btn", "btn-secondary", "col-sm-4", "col-md-3", "col-lg-2");
        searchTextContainer.setPadding(false);
        searchTextContainer.setSpacing(false);
        searchTextContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        searchTextContainer.addClassNames("form-control");
        searchTextContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        searchText.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchText.addClassName("search-text-autocomplete");
        searchText.setSizeFull();
        searchText.addClassName("vaadin-ps4-theme");
    }


    @ClientCallable
    public void selectedItem(String itemValue){
        searchText.setValue(itemValue.split(":")[1]);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        searchBtn.getElement().setAttribute("value", I18NProviderStatic.getTranslation("home.banner.carousel.search.view.search.button.label"));
        searchText.setPlaceholder(I18NProviderStatic.getTranslation("home.banner.carousel.search.view.search.text.placeholder"));
    }

    public String getSearchTextValue() {
        return searchText.getValue();
    }

    public void setSearchTextValue(String searchTextValue) {
        searchText.setValue(searchTextValue);
    }

    public void setSearchTextPlaceholder(String placeholder) {
        searchText.setPlaceholder(I18NProviderStatic.getTranslation(placeholder));
    }

    public void enablePresenterSearchEvents() {
        registerClickEvent(PRESENTER_SEARCH_BTN_ACTION, searchBtn);
        registerComponentValueChangeEventEvent(PRESENTER_SEARCH_TEXT_ACTION, searchText);
       /* try {
            UI.getCurrent().getPage().executeJs("initAutocompleteItems($0,$1)",
                    new ObjectMapper().writeValueAsString(documents
                            .stream().map(document ->document.getCategorieDoc() +": "+document.getDenumire()).collect(Collectors.toList())), this);
        } catch (Throwable e) {
            logger.debug("Autocomplete js call!!", e.getStackTrace());
        }*/

    }
    public void i18nInboxContainer(){
        InternationalizeViewEngine.internationalize(this);
    }
}
