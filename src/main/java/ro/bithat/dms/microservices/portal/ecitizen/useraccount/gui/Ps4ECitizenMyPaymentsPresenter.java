package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.DmswsBankingService;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeRoute;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;

public class Ps4ECitizenMyPaymentsPresenter extends PrepareModelFlowPresenter<Ps4ECitizenMyPaymentsView> {

    @Autowired
    private DmswsBankingService dmswsBankingService;

    @Override
    public void prepareModel(String state) {
        getView().setMyPaymentsTable(dmswsBankingService.getPaymentsListByUser());

    }

    @ClickEventPresenterMethod(viewProperty = "anchorContainer")
    public void onRedirectToIndexClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
    getLogger().info("redirect");
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class));
    }


}
