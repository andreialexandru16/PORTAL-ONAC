package ro.bithat.dms.template.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import ro.bithat.dms.mvp.FlowPresenter;
import ro.bithat.dms.mvp.component.verticallayout.VerticalLayoutFlowView;
import ro.bithat.dms.template.route.TemplateUtil;

import javax.annotation.PostConstruct;

@Deprecated
public abstract class SplitScreenView<P extends FlowPresenter> extends VerticalLayoutFlowView<P> {


    private Button switchSplitOrientationBtn = new Button(VaadinIcon.PADDING_BOTTOM.create());

    private Button switchSplitSideBtn = new Button(VaadinIcon.PADDING_LEFT.create());

    private Button closeBtn = new Button(VaadinIcon.CLOSE.create());

    private Boolean isSwitchSide = false;

    private SplitLayout splitContent = new SplitLayout();

    private Image toolBarLogo = new Image();

    private HorizontalLayout topBarButtons = new HorizontalLayout(switchSplitOrientationBtn, switchSplitSideBtn, closeBtn);

    private HorizontalLayout topToolBar = new HorizontalLayout(toolBarLogo, topBarButtons);



    @PostConstruct
    public void init() {
        setSizeFull();
        getStyle().set("position", "relative");
        getStyle().set("overflow", "hidden");
//        switchSplitOrientationBtn.getStyle().set("position", "absolute");
//        switchSplitOrientationBtn.getStyle().set("right", "110px");
//        switchSplitOrientationBtn.getStyle().set("top", "-2px");
//        switchSplitSideBtn.getStyle().set("position", "absolute");
//        switchSplitSideBtn.getStyle().set("right", "60px");
//        switchSplitSideBtn.getStyle().set("top", "-2px");
//        closeBtn.getStyle().set("position", "absolute");
//        closeBtn.getStyle().set("right", "10px");
//        closeBtn.getStyle().set("top", "-2px");
        switchSplitOrientationBtn.getStyle().set("background-color", "#ffffff");
        switchSplitOrientationBtn.addClassName("cursor-pointer");
        switchSplitOrientationBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_CONTRAST);
        switchSplitOrientationBtn.getStyle().set("margin-left", "6px");
        switchSplitSideBtn.getStyle().set("background-color", "#ffffff");
        switchSplitSideBtn.addClassName("cursor-pointer");
        switchSplitSideBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_CONTRAST);
        switchSplitSideBtn.getStyle().set("margin-left", "6px");
        closeBtn.getStyle().set("background-color", "#ffffff");
        closeBtn.addClassName("cursor-pointer");
        closeBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        closeBtn.getStyle().set("margin-left", "6px");
        TemplateUtil.setNoSpacingAndPaddingLayout(this);
        TemplateUtil.setNoSpacingAndPaddingLayout(topToolBar);
        toolBarLogo.setHeight("30px");
        toolBarLogo.getStyle().set("margin-left", "6px");
        splitContent.getStyle().set("overflow", "hidden");
        splitContent.setSizeFull();
//        splitContent.getStyle().set("margin-top", "35px");
        topToolBar.setWidthFull();
        topToolBar.setHeight("40px");
        topToolBar.setAlignItems(Alignment.CENTER);
        topToolBar.getStyle().set("background-color", "#5CB3DE");
        topBarButtons.setJustifyContentMode(JustifyContentMode.END);
        topBarButtons.setPadding(true);
        topBarButtons.setSpacing(false);
        topBarButtons.setAlignItems(Alignment.CENTER);
        topBarButtons.setWidthFull();
        add(topToolBar, splitContent);
        switchSplitOrientationBtn.addClickListener(e -> onSwitchOrientationAction());
        switchSplitSideBtn.addClickListener(e -> onSwitchSideAction());
        closeBtn.addClickListener(e -> onCloseAction());
//        add(switchSplitOrientationBtn, switchSplitSideBtn, closeBtn);
        super.init();
    }

    protected void onCloseAction() {

    }

    protected void onSwitchOrientationAction() {
        switch (splitContent.getOrientation()) {
            case VERTICAL:
                setHorizontalSplitLayout();
                break;
            case HORIZONTAL:
                setVerticalSplitLayout();
                break;
        }
    }


    public void addPrimaryComponent(Component component) {
        splitContent.addToPrimary(component);
    }

    public void addSecondaryComponent(Component component) {
        splitContent.addToSecondary(component);
    }


    public void setVerticalSplitLayout() {
        splitContent.setOrientation(SplitLayout.Orientation.VERTICAL);
        setOrientationButtonsIcon();
    }

    public void setHorizontalSplitLayout() {
        splitContent.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        setOrientationButtonsIcon();
    }

    protected void onSwitchSideAction() {
        Component primary = splitContent.getPrimaryComponent();
        Component secondary = splitContent.getSecondaryComponent();
        splitContent.removeAll();
        splitContent.addToPrimary(secondary);
        splitContent.addToSecondary(primary);
        isSwitchSide = !isSwitchSide;
        setOrientationButtonsIcon();

    }

    private void setOrientationButtonsIcon() {
        switch (splitContent.getOrientation()) {
            case VERTICAL:
                if(isSwitchSide) {
                    switchSplitOrientationBtn.setIcon(VaadinIcon.PADDING_LEFT.create());
                    switchSplitSideBtn.setIcon(VaadinIcon.PADDING_BOTTOM.create());
                } else {
                    switchSplitOrientationBtn.setIcon(VaadinIcon.PADDING_RIGHT.create());
                    switchSplitSideBtn.setIcon(VaadinIcon.PADDING_TOP.create());
                }
                break;
            case HORIZONTAL:
                if(isSwitchSide) {
                    switchSplitOrientationBtn.setIcon(VaadinIcon.PADDING_TOP.create());
                    switchSplitSideBtn.setIcon(VaadinIcon.PADDING_RIGHT.create());
                } else {
                    switchSplitOrientationBtn.setIcon(VaadinIcon.PADDING_BOTTOM.create());
                    switchSplitSideBtn.setIcon(VaadinIcon.PADDING_LEFT.create());
                }
                break;
        }
    }

    public void replaceSecondaryComponent(Component component) {
        if(isSwitchSide) {
            forceReplacePrimaryComponent(component);
        } else {
            forceReplaceSecondaryComponent(component);
        }
    }

    public void replacePrimaryComponent(Component component) {
        if(isSwitchSide) {
            forceReplaceSecondaryComponent(component);
        }else {
            forceReplacePrimaryComponent(component);
        }
    }

    protected void forceReplaceSecondaryComponent(Component component) {
        Component primary = splitContent.getPrimaryComponent();
        splitContent.removeAll();
        splitContent.addToPrimary(primary);
        splitContent.addToSecondary(component);
    }

    protected void forceReplacePrimaryComponent(Component component) {
        Component secondary = splitContent.getSecondaryComponent();
        splitContent.removeAll();
        splitContent.addToPrimary(component);
        splitContent.addToSecondary(secondary);
    }

    public SplitLayout getSplitContent() {
        return splitContent;
    }

    public HorizontalLayout getTopToolBar() {
        return topToolBar;
    }


    public void addToolBarButton(Button button) {
        button.getStyle().set("background-color", "#ffffff");
        button.addClassName("cursor-pointer");
        button.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_CONTRAST);
        button.getStyle().set("margin-left", "6px");
        topBarButtons.addComponentAsFirst(button);
    }

    public void addToolBarLogo(String path) {
        toolBarLogo.setSrc(path);
    }
}
