package ro.bithat.dms.microservices.portal.ecitizen.gui.header;

import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.passiveview.component.presenter.SimpleFlowPresenter;

public class Ps4ECitizenMainHeaderMainMenuPresenter extends SimpleFlowPresenter<Ps4ECitizenMainHeaderMainMenuView> {
    @Value("${show.header:true}")
    private Boolean showHeader;

    public Boolean getShowHeader() {
        return showHeader;
    }

    public void setShowHeader(Boolean showHeader) {
        this.showHeader = showHeader;
    }
}
