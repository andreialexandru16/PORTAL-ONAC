package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.mvp.FlowView;

public class ServiceRequestStepsFooterButtonController extends FlowViewDivContainer {


    private final static String PRESENTER_PREVIOUS_ACTION = "onPreviousBtnAction";

    private final static String PRESENTER_NEXT_ACTION = "onNextBtnAction";

    private Strong nextBtnInfoTitle = new Strong();

    private Label nextBtnInfoLabel = new Label();

    private Div nextBtnInfo = new Div(nextBtnInfoTitle, nextBtnInfoLabel);

    private Strong previousBtnInfoTitle = new Strong();

    private Label previousBtnInfoLabel = new Label();

    private Div previousBtnInfo = new Div(previousBtnInfoTitle, previousBtnInfoLabel);

    private Div btnClearfix = new Div(previousBtnInfo, nextBtnInfo);

    private Div btnInfo = new Div(btnClearfix);

    private HtmlContainer nextBtnIcon = new HtmlContainer("i");

    private ClickNotifierAnchor nextBtn = new ClickNotifierAnchor();

    private HtmlContainer previousBtnIcon = new HtmlContainer("i");

    private ClickNotifierAnchor previousBtn = new ClickNotifierAnchor();

    private Div btnContainer = new Div(previousBtn, nextBtn);

    public ServiceRequestStepsFooterButtonController(FlowView view) {
        super(view);

        nextBtnIcon.addClassNames("fas", "fa-arrow-alt-circle-right");
        nextBtn.addClassNames("btn", "btn_arrow_right", "btn_green", "next");
        nextBtn.setVisible(false);
        previousBtnIcon.addClassNames("fas", "fa-arrow-alt-circle-left");
        previousBtn.addClassNames("btn", "btn_arrow_left", "btn-sm", "prev");
        previousBtn.setVisible(false);
        btnContainer.addClassNames("buttons_container");
        btnInfo.addClassName("btn_info");
        btnClearfix.addClassNames("background", "clearfix");
        previousBtnInfo.addClassName("prev_info");
        previousBtnInfo.setVisible(false);
        nextBtnInfo.addClassName("next_info");
        nextBtn.setHref("javascript:void(0);");
        previousBtn.setHref("javascript:void(0);");

        nextBtnInfo.setVisible(false);
        addClassName("btn_footer_container");
        add(btnInfo, btnContainer);

    }

    public void registerPresenterPreviousStepMethod(String title, String info) {
        registerPresenterPreviousStepMethod("document.type.service.newrequest.view.back.action.label", title, info);
    }

    public void registerPresenterPreviousStepMethod(String btnLabel, String title, String info) {
        previousBtn.removeAll();
        previousBtn.setVisible(true);
        previousBtnInfo.setVisible(true);
        previousBtnInfo.add(new Strong(new Text(title)), new Text(info));
        registerClickEvent(PRESENTER_PREVIOUS_ACTION, previousBtn);
        previousBtn.add(previousBtnIcon, new Text(btnLabel));
    }

    public void registerPresenterNextStepMethod(String title, String info) {
        registerPresenterNextStepMethod("document.type.service.newrequest.view.next.action.label", title, info);
    }


    public void registerPresenterNextStepMethod(String btnLabel, String title, String info) {
        nextBtn.removeAll();
        nextBtn.setVisible(true);
        nextBtnInfo.setVisible(true);
        nextBtnInfo.add(new Strong(new Text(title)), new Text(info));
        registerClickEvent(PRESENTER_NEXT_ACTION, nextBtn);
        nextBtn.add(new Text(btnLabel), nextBtnIcon);
    }

}
