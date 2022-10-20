package ro.bithat.dms.template.route;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import ro.bithat.dms.mvp.DefaultFlowPresenter;
import ro.bithat.dms.template.navigation.SearchSlideEvent;

@SpringComponent
@UIScope
@Deprecated
public class DocumentaVaadinResponsivePresenter extends DefaultFlowPresenter<DocumentaVaadinResponsiveView> {



    @EventBusListenerMethod
    public void onSearchSlideEvent(SearchSlideEvent event) {
        if(getView().isSearchSlideVisible()){
            getView().hideSearchSlide();
        } else {
            getView().showSearchSlide();
        }
    }

}
