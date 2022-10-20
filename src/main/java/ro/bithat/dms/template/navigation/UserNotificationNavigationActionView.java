package ro.bithat.dms.template.navigation;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.mvp.component.horizontallayout.HorizontalLayoutFlowView;
import ro.bithat.dms.template.route.TemplateUtil;

@SpringComponent
@UIScope
@Deprecated
public class UserNotificationNavigationActionView extends HorizontalLayoutFlowView<UserNotificationNavigationActionPresenter> {

    private Span dmsNavigationDialogItemIcon = new Span();

    private Div itemCounter = new Div();

    private Div dmsSurfaceNavigationDialogItemComponent = new Div(dmsNavigationDialogItemIcon, itemCounter);

    private Span groupActions = new Span(dmsSurfaceNavigationDialogItemComponent);

    private Div dmsSurfaceNotificationsComponent = new Div(groupActions);

    @Override
    public void buildView() {
        TemplateUtil.setNoSpacingAndPaddingLayout(this);
        addClassName("dms-surface-action-icon-wrapper");
        addClickListener(getPresenter()::onClickEvent);
        dmsSurfaceNotificationsComponent.addClassName("dms-surface-notifications-component");
        add(dmsSurfaceNotificationsComponent);
        dmsSurfaceNavigationDialogItemComponent.addClassName("dms-surface-navigation-dialog-item-component");
        itemCounter.addClassName("item-counter");
        dmsNavigationDialogItemIcon.addClassNames("navigation-dialog-item-icon", "fa", "fa-bell");
        itemCounter.setVisible(false);
    }

    public void setItemCounter(Integer count) {
        itemCounter.setVisible(count > 0);
        itemCounter.setText(count + "");
    }
}
