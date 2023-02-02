package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.api.CereriContController;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.api.DmswsUtilizatorController;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.FisierDraftExtended;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ps4ECitizenMyDraftRequestsPresenter extends PrepareModelFlowPresenter<Ps4ECitizenMyDraftRequestsView> {

    @Autowired
    private DmswsFileService fileService;
    @Value("${wordpress.url}")
    private String wordpressUrl;

    @Value("${requests.show.payment:true}")
    private String showPaymentCol;
    @Override
    public void prepareModel(String state) {
        List<FisierDraftExtended> userRequests= fileService.getDrafts(SecurityUtils.getToken()).getFisierDraftList();

        getView().setMyRequestsTable(userRequests);
    }


    @ClickEventPresenterMethod(viewProperty = "anchorContainer")
    public void onRedirectToIndexClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
    getLogger().info("redirect");
        UI.getCurrent().getPage().setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class));
    }

    public String getWordpressUrl() {
        return wordpressUrl;
    }

    public String getShowPaymentCol() {
        return showPaymentCol;
    }
}
