package ro.bithat.dms.microservices.portal.ecitizen.gui.header;

import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.passiveview.component.presenter.SimpleFlowPresenter;



public class Ps4ECitizenMainHeaderContainerPresenter extends SimpleFlowPresenter<Ps4ECitizenMainHeaderContainerView> {
    @Value("${wordpress.url}")
    private String wordpressUrl;

    public String getWordpressUrl() {
        return wordpressUrl;
    }
}
