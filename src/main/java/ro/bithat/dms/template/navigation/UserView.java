package ro.bithat.dms.template.navigation;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.mvp.component.horizontallayout.HorizontalLayoutFlowView;
import ro.bithat.dms.service.PrincipalUtil;

@SpringComponent
@UIScope
@Deprecated
public class UserView extends HorizontalLayoutFlowView<UserPresenter> {

    private VerticalLayout userDialog = new VerticalLayout();

    private Div currentUser = new Div();

    private HorizontalLayout userViewHeader = new HorizontalLayout(currentUser);

    private VerticalLayout userViewContent = new VerticalLayout();

    private Button logoutBtn = new Button("Iesire", VaadinIcon.EXIT_O.create());

    private HorizontalLayout userViewFooter = new HorizontalLayout(logoutBtn);

    private boolean clickOnUserDialog;

    private Div dmsSurfaceAvatarMenuComponent;

    @Override
    public void buildView() {
        getStyle().set("position", "absolute");
        getStyle().set("top", "0px");
        getStyle().set("left", "0px");
        getStyle().set("z-index", "1000");
        setSizeFull();
        userDialog.addClassName("user-dialog");
        userDialog.getStyle().remove("width");
        String principalUsername = PrincipalUtil.getThreadLocalPrincipalUsername();
        currentUser.getElement().setProperty("title", principalUsername);
        currentUser.getStyle().set("border-radius", "50%");
        currentUser.getStyle().set("background-color", "coral");
        currentUser.getStyle().set("text-align", "center");
        currentUser.getStyle().set("padding", "15px 5px");
        currentUser.addClassNames("fa", "fa-user");
        currentUser.setText(PrincipalUtil.getThreadLocalPrincipalUsernameLabel());
        userViewHeader.setAlignItems(Alignment.CENTER);
        userViewHeader.add(new Label(principalUsername));
        userViewHeader.setWidthFull();
        userViewHeader.setHeight("60px");
        userViewHeader.getStyle().set("border-bottom", "1px solid #666666");
        userViewContent.setHeight("250px");

        userViewContent.getStyle().set("overflow", "auto");
        userViewFooter.setHeight("56px");
        userViewFooter.setWidthFull();
        userViewFooter.getStyle().set("border-top", "1px solid #666666");
        userViewFooter.setJustifyContentMode(JustifyContentMode.END);
        userViewFooter.setAlignItems(Alignment.CENTER);
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        logoutBtn.addClickListener(this::onLogoutBtnFired);
        userDialog.add(userViewHeader, userViewContent, userViewFooter);
        add(userDialog);
        userDialog.addClickListener(e -> {
            clickOnUserDialog = true;
        });
        addClickListener(e -> {
            if(!clickOnUserDialog)
                this.close();
            clickOnUserDialog = false;
        });
    }

    private void onLogoutBtnFired(ClickEvent<Button> buttonClickEvent) {
        UI.getCurrent().getPage().executeJs("window.open(\"/logout\", \"_self\");");
    }

    public void open() {
        dmsSurfaceAvatarMenuComponent.addClassName("is-open");
        UI.getCurrent().add(this);
    }

    public void close() {
        dmsSurfaceAvatarMenuComponent.removeClassName("is-open");
        UI.getCurrent().remove(this);
    }

    public void setDmsSurfaceAvatarMenuComponent(Div dmsSurfaceAvatarMenuComponent) {
        this.dmsSurfaceAvatarMenuComponent = dmsSurfaceAvatarMenuComponent;
    }
}
