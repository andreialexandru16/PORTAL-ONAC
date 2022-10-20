package ro.bithat.dms.template.navigation;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import ro.bithat.dms.mvp.DefaultFlowPresenter;

@SpringComponent
@UIScope
@Deprecated
public class UserNotificationNavigationActionPresenter extends DefaultFlowPresenter<UserNotificationNavigationActionView> {

    private Integer testCount = 0;

    @EventBusListenerMethod
    public void onUserNotificationChangedEvent(UserNotificationChangedEvent userNotificationChangedEvent) {
        testCount++;
        getView().setItemCounter(testCount);
    }


    public void onClickEvent(ClickEvent<HorizontalLayout> horizontalLayoutClickEvent) {
        getUIEventBus().publish(this, new UserNotificationChangedEvent());
    }

}
