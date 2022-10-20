package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.component.ClickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.SimpleFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

public class CookieConsentPresenter extends SimpleFlowPresenter<CookieConsentView> {


    @Autowired
    private DmswsUtilizatorService utilizatorService;

    @ClickEventPresenterMethod(viewProperty = "dismissBtn")
    public void onDismissCookieConsent(ClickEvent<ClickNotifierAnchor> dismissEvent){
        userDismissed();
        getView().dismiss();
    }

    public void userDismissed()  {
        if(!SecurityUtils.getUsername().equalsIgnoreCase("nouser")) {
            utilizatorService.userHasAcceptCookie(SecurityUtils.getToken());
            SecurityUtils.getUserToken().setCookieAccepted(1);
        }
    }

}
