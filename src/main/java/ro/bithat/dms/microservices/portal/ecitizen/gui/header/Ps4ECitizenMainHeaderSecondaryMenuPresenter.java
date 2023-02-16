package ro.bithat.dms.microservices.portal.ecitizen.gui.header;

import com.vaadin.flow.component.ClickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsEmailService;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.boot.UiI18nUtil;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.LoginController;
import ro.bithat.dms.security.SecurityUtils;

import java.util.Locale;
import java.util.Optional;

public class Ps4ECitizenMainHeaderSecondaryMenuPresenter extends PrepareModelFlowPresenter<Ps4ECitizenMainHeaderSecondaryMenuView> {


    @ClickEventPresenterMethod(viewProperty = "i18nSelectorNavItemRomanianLanguageLink")
    public void onSelectRomanianLocale(ClickEvent clickEvent) {
        UiI18nUtil.setLocale(new Locale("ro","RO"));
    }

    @ClickEventPresenterMethod(viewProperty = "i18nSelectorNavItemEnglishLanguageLink")
    public void onSelectEnglishLocale(ClickEvent clickEvent) {
        UiI18nUtil.setLocale(new Locale("en","EN"));
    }

    @Autowired
    DmswsEmailService dmswsEmailService;

    @Autowired
    private LoginController loginController;

    @Override
    public void prepareModel(String state) {
        if(hasAuthenticateUser()){
            setNotificationContainer();
        }
    }
    public void setNotificationContainer(){
        getView().setNotificationContainer(dmswsEmailService.getNotificationsNumber(SecurityUtils.getToken()).getExtendedInfo());
    }
    public Boolean hasAuthenticateUser() {
        return !SecurityUtils.getUsername().equalsIgnoreCase("nouser");
    }

    public void auth(){
        Optional<String> token = QueryParameterUtil.getQueryParameter("token", String.class);
        if(token.isPresent() && token.get()!=null && !token.get().isEmpty()){
            loginController.loginWithToken(token.get());
        }
    }

}
