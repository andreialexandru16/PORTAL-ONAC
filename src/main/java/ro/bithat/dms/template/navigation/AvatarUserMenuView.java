package ro.bithat.dms.template.navigation;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.mvp.component.horizontallayout.HorizontalLayoutFlowView;
import ro.bithat.dms.template.route.TemplateUtil;

@SpringComponent
@UIScope
@Deprecated
public class AvatarUserMenuView extends HorizontalLayoutFlowView<AvatarUserMenuPresenter> {


    private Image avatar = new Image("frontend/img/dapulse_default_photo.png", "");


    @Autowired
    private UserView userView;

    private Div dmsAvatarPhotoDot = new Div();

    private Div dmsAvatarPhotoButtonWrapper = new Div(dmsAvatarPhotoDot, avatar);

    private Div dmsDsMenuButtonContainer = new Div(dmsAvatarPhotoButtonWrapper);

    private Div dmsSurfaceAvatarMenuComponent = new Div(dmsDsMenuButtonContainer);


    @Override
    public void buildView() {
        TemplateUtil.setNoSpacingAndPaddingLayout(this);
        addClassName("dms-surface-avatar-menu-connector-wrapper");
        addClickListener(getPresenter()::onClickEvent);
        add(dmsSurfaceAvatarMenuComponent);
        dmsSurfaceAvatarMenuComponent.addClassName("dms-surface-avatar-menu-component");
        dmsDsMenuButtonContainer.addClassName("dms-ds-menu-button-container");
        dmsAvatarPhotoButtonWrapper.addClassName("dms-avatar-photo-button-wrapper");
        dmsAvatarPhotoDot.addClassNames("dms-avatar-photo-dot", "red-dot");
        userView.setDmsSurfaceAvatarMenuComponent(dmsSurfaceAvatarMenuComponent);
        avatar.addClassNames("dms-person-bullet-image", "dms-person-bullet-component");
    }

    public void changeOpenState() {
        if(dmsSurfaceAvatarMenuComponent.hasClassName("is-open")) {
            userView.close();
        } else {
            userView.open();
        }
    }

}
