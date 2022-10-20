package ro.bithat.dms.microservices.portal.ecitizen.home.gui.homebanner;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Ps4ECitizenHomeBannerCarouselSearchView extends DivFlowViewBuilder<Ps4ECitizenHomeBannerCarouselSearchPresenter> {


    private List<Div> carouselSlides = new ArrayList<>();

    private Optional<Div> currentSlide = Optional.empty();

    private SearchContainer searchFormContainer = new SearchContainer(this);

    @Override
    protected void buildView() {
        addClassName("main_slide");
        carouselSlides.clear();
        searchFormContainer.addClassName("home_form_container");
        UI.getCurrent().getPage().executeJs("initCarouselSlider($0);", getElement());
        UI.getCurrent().getPage().addJavaScript("frontend/js/voice-to-text.js");
    }

    public SearchContainer getSearchFormContainer() {
        return searchFormContainer;
    }


    public void addCarouselSlide(String imageUrl) {
        Div singleSlide = new Div();
        singleSlide.setId(UUID.randomUUID().toString());
        singleSlide.addClassName("single_slide");
        singleSlide.getStyle().set("background-image", "url('" + imageUrl + "')");
        add(singleSlide);
        carouselSlides.add(singleSlide);
        if(!currentSlide.isPresent()) {
            currentSlide = Optional.of(singleSlide);
            singleSlide.add(searchFormContainer);
        }
    }

    @ClientCallable
    public void onCarouselSlideBeforeChange(int nextSlideIdx) {
        currentSlide.get().removeAll();
        carouselSlides.get(nextSlideIdx).add(searchFormContainer);
    }

    @ClientCallable
    public void afterSpeachToText(String text) {
    	searchFormContainer.setSearchTextValue(text);
    }

}
