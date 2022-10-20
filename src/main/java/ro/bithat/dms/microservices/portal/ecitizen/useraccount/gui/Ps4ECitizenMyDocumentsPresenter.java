package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsMyDocumentsService;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;

public class Ps4ECitizenMyDocumentsPresenter extends PrepareModelFlowPresenter<Ps4ECitizenMyDocumentsView> {


    @Autowired
    private DmswsMyDocumentsService myDocumentsService;


    @Override
    public void prepareModel(String state) {
        getView().setMyDocumentsTable(myDocumentsService.getDocumentsByUser());

    }

    @ClickEventPresenterMethod(viewProperty = "anchorContainer")
    public void onRedirectToIndexClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect");
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
    }


}
