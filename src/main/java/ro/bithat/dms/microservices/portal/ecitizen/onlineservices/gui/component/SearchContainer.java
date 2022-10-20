package ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component;
//13.07.2021 - NG - ANRE - adaugare metoda de stergere btn de icon
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.mvp.FlowView;

import java.util.List;
import java.util.stream.Collectors;

public class SearchContainer extends FlowViewDivContainer implements LocaleChangeObserver  {

    private Logger logger = LoggerFactory.getLogger(SearchContainer.class);

    private final static String PRESENTER_SPEAK_SEARCH_BTN_ACTION = "onSpeakSearchBtnClick";

    private final static String PRESENTER_SEARCH_BTN_ACTION = "onSearchBtnClick";

    private final static String PRESENTER_SEARCH_TEXT_ACTION = "onSearchTextChanged";

    private HtmlContainer speakSearchIcon = new HtmlContainer("i");

    private Div speakSearchIconAction = new Div(speakSearchIcon);

    private TextField searchText = new TextField();

    private HorizontalLayout searchTextContainer = new HorizontalLayout(searchText);

    private HtmlContainer filterSearchIcon = new HtmlContainer("i");

    private Button searchBtn = new Button(filterSearchIcon);

    private Div searchContainer = new Div(searchTextContainer, speakSearchIconAction, searchBtn);

    private Div formGroup = new Div(searchContainer);


    public SearchContainer(FlowView view) {
        super(view);
        add(formGroup);
        formGroup.addClassNames("form-group", "row", "no-gutters");
        searchContainer.addClassNames("col-lg-12", "search_container");
        speakSearchIconAction.addClassName("speak-search");
        speakSearchIcon.addClassNames("fas", "fa-microphone-alt");
        filterSearchIcon.addClassNames("fas", "fa-search");
        searchBtn.addClassNames("btn", "btn-secondary", "btn_icon_search");
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
        try{
            searchText.setValue(itemValue.split(":")[1]);

        }catch (Exception e){
            searchText.setValue(itemValue);

        }
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
    public void setSpeachButtonColor(String color) {
        speakSearchIcon.getStyle().set("color",color);
    }
    public String getSpeachButtonColor() {
        return speakSearchIcon.getStyle().get("color");
    }

    public void enablePresenterSearchEvents(List<Document> documents) {
        registerClickEvent(PRESENTER_SEARCH_BTN_ACTION, searchBtn);
        registerClickEvent(PRESENTER_SPEAK_SEARCH_BTN_ACTION,speakSearchIconAction);
        registerComponentValueChangeEventEvent(PRESENTER_SEARCH_TEXT_ACTION, searchText);
        try {
            UI.getCurrent().getPage().executeJs("initAutocompleteItems($0,$1)",
                    new ObjectMapper().writeValueAsString(documents
                            .stream().map(document ->document.getCategorieDoc() +": "+document.getDenumire()).collect(Collectors.toList())), this);
        } catch (Throwable e) {
            logger.debug("Autocomplete js call!!", e.getStackTrace());
        }

    }
    //13.07.2021 - NG - ANRE - adaugare metoda de stergere btn de icon
    public void removeSpeachBtn() {
        searchContainer.remove(speakSearchIconAction);
    }

}
