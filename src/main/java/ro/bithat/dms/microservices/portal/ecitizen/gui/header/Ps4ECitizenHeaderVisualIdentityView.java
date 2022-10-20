package ro.bithat.dms.microservices.portal.ecitizen.gui.header;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

public class Ps4ECitizenHeaderVisualIdentityView extends DivFlowViewBuilder<Ps4ECitizenHeaderVisualIdentityPresenter> {

    private Image isImage = new Image("PORTAL/assets/images/logos/instrumente-structurale.jpg", "logo instrumente structurale");

    private Anchor isLink = new Anchor("javascript:void(0)", isImage);

    private Div isCol = new Div(isLink);

    private Image pocaImage = new Image("PORTAL/assets/images/logos/poca.jpg", "logo programul operational capacitate administrativa");

    private Anchor pocaLink = new Anchor("javascript:void(0)", pocaImage);

    private Div pocaCol = new Div(pocaLink);

    private Image govImage = new Image("PORTAL/assets/images/logos/guvernul-romaniei.jpg", "logo guvernul romaniei");

    private Anchor govLink = new Anchor("javascript:void(0)", govImage);

    private Div govCol = new Div(govLink);

    private Image ueImage = new Image("PORTAL/assets/images/logos/uniunea-europeana.jpg", "logo uniunea europeana");

    private Anchor ueLink = new Anchor("javascript:void(0)", ueImage);

    private Div ueCol = new Div(ueLink);

    private Div row = new Div(ueCol, govCol, pocaCol, isCol);

    private Div container = new Div(row);

    @Override
    protected void buildView() {
        addClassName("visual_identity");
        container.addClassName("container");
        row.addClassName("row");
        add(container);
        ueLink.setTarget("_target");
        govLink.setTarget("_target");
        pocaLink.setTarget("_target");
        isLink.setTarget("_target");
        ueCol.addClassNames("col-2", "ue");
        govCol.addClassNames("col-3", "gov", "text-center");
        pocaCol.addClassNames("col-5", "poca", "text-center");
        isCol.addClassNames("col-2", "is", "text-right");
    }

}
