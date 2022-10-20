package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeDocumentClassView;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.homebanner.Ps4ECitizenHomeBannerCarouselSearchView;
import ro.bithat.dms.passiveview.FlowComponent;

@Route("")
public class Ps4ECitizenAnonymousHomeRoute extends Ps4ECitizenPortalRoute {
//
//    @Autowired
//    private CustomRequestCache requestCache;


    private Paragraph footerVisualIdentityContainerP2 = new Paragraph("footer.visual.identity.container.p2");

    private Anchor footerVisualIdentityContainerP1Link = new Anchor("https://www.fonduri-ue.ro/");

    private Paragraph footerVisualIdentityContainerP1 = new Paragraph("footer.visual.identity.container.p1");

    @FlowComponent
    private Ps4ECitizenHomeBannerCarouselSearchView homeBannerCarouselSearchView;

    @FlowComponent
    private Ps4ECitizenHomeDocumentClassView homeDocumentClassView;

    private Div subtitleView = new Div();

    private Div subtitleViewDescription = new Div();
    @Value("${show.header.title:true}")
    private Boolean showHeaderTitle;
    @Override
    protected boolean buildPortalRoute() {
        footerVisualIdentityContainerP1Link.setText("footer.visual.identity.container.p1.link.site");
        footerVisualIdentityContainerP1Link.setTarget("_blank");
        if(showHeaderTitle){

            subtitleView.setText("home.route.subtitle.view.text");
            subtitleViewDescription.setText("home.route.subtitle.view.text.description");
            addContentContainer(subtitleView,subtitleViewDescription);

        }
        subtitleView.addClassNames("subtitle_underline", "text-center");
        subtitleViewDescription.addClassNames("subtitle_underline", "text-center");
        footerVisualIdentityContainerP1.add(footerVisualIdentityContainerP1Link);
        String[] classNames = {"homepage_banner"};
        addContentHeaderContainer("home-banner", classNames, homeBannerCarouselSearchView);
        addFooterVisualIdentityContainer(footerVisualIdentityContainerP1, footerVisualIdentityContainerP2);
        boolean dmswsenable = true;
        if(dmswsenable) {
            addContentContainer(homeDocumentClassView);
        }
        return dmswsenable;
    }

}
