package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.register;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

public class Ps4ECitizenUserRegisterView extends DivFlowViewBuilder<Ps4ECitizenUserRegisterPresenter> {

    private Div infor1 = new Div();
    private Div infor2 = new Div();
    private Strong title = new Strong("ps4.ecetatea.user.help.title");
    private Paragraph titleParagraph = new Paragraph(title);
    private Div howToInfo = new Div(titleParagraph, infor1, infor2);

    @Override
    protected void buildView() {
        infor1.setText("ps4.ecetatea.user.help.info1");
        infor2.setText("ps4.ecetatea.user.help.info2");
        title.add();
        howToInfo.addClassNames("how_to_info");
        add(howToInfo);
    }
}
