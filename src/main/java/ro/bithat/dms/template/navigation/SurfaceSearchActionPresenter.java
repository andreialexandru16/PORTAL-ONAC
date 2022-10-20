package ro.bithat.dms.template.navigation;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.mvp.DefaultFlowPresenter;

@SpringComponent
@UIScope
@Deprecated
public class SurfaceSearchActionPresenter extends DefaultFlowPresenter<SurfaceSearchActionView> {

    public void onClickEvent(ClickEvent<HorizontalLayout> horizontalLayoutClickEvent) {
        getUIEventBus().publish(this, new SearchSlideEvent());
    }

}
