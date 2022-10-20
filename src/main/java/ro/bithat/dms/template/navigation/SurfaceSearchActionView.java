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
public class SurfaceSearchActionView extends HorizontalLayoutFlowView<SurfaceSearchActionPresenter> {

    private Span dmsNavigationActionItemIcon = new Span();

    private Div dmsSurfaceNavigationActionItemComponent = new Div(dmsNavigationActionItemIcon);

    @Override
    public void buildView() {
        TemplateUtil.setNoSpacingAndPaddingLayout(this);
        addClickListener(getPresenter()::onClickEvent);
        addClassName("dms-surface-action-icon-wrapper");
        add(dmsSurfaceNavigationActionItemComponent);
        dmsSurfaceNavigationActionItemComponent.addClassName("dms-surface-navigation-action-item-component");
        dmsNavigationActionItemIcon.addClassNames("dms-navigation-action-item-icon", "fa", "fa-search");
    }

}
