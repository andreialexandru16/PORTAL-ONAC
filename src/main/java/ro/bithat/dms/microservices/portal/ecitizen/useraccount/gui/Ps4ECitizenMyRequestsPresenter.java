package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;
//13.07.2021 - NG - ANRE - adaugare camp de cautare
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.api.CereriContController;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsMyRequestsService;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ps4ECitizenMyRequestsPresenter extends PrepareModelFlowPresenter<Ps4ECitizenMyRequestsView> {

    @Autowired
    private DmswsMyRequestsService myRequestsService;
    @Value("${wordpress.url}")
    private String wordpressUrl;

    @Value("${requests.show.payment:true}")
    private String showPaymentCol;
    @Override
    public void prepareModel(String state) {
        List<PortalFile> userRequests= myRequestsService.getFilesOnWorkflowByUser();

        Map<String,String> mapDocs= new HashMap<>();
        for(PortalFile file: userRequests){
            mapDocs.put( file.getDenumireDocument(),file.getClasaDocument());
        }
        List<Document> listDocs= new ArrayList<>();
        for(String tipDoc: mapDocs.keySet()){
            listDocs.add(new Document(tipDoc,mapDocs.get(tipDoc)));
        }
        getView().setMyRequestsTable(userRequests);
        getView().getSearchFormContainer().enablePresenterSearchEvents(listDocs);
        getView().getSearchFormContainer().removeSpeachBtn();
    }

    public void onSearchBtnClick(ClickEvent<Input> clickEvent) {
        doSearch(getView().getSearchFormContainer().getSearchTextValue());
    }


    private void doSearch(String searchText) {
        searchText=searchText.toLowerCase();
        List<PortalFile> userRequests= myRequestsService.getFilesOnWorkflowByUser();
        String finalSearchText = searchText;
        getView().setMyRequestsTable(userRequests.stream().filter(f->
                f.getDenumireDocument().toLowerCase().contains(finalSearchText.trim())
                        || (f.getNrInreg()==null?"":f.getNrInreg()).contains(finalSearchText.trim())
                        || (f.getDataInreg()==null?"":f.getDataInreg()).contains(finalSearchText.trim())
                        || (f.getClasaDocument()==null?"":f.getClasaDocument().toLowerCase()).contains(finalSearchText.trim())
                        ||(f.getDenumireDocument()==null?"":f.getDenumireDocument().toLowerCase()).contains(finalSearchText.trim())
                        ||f.getNume().toLowerCase().contains(finalSearchText.trim())
                        ||f.getTrimisLa().contains(finalSearchText.trim())
                        ||(f.getDenumireWorkflowStatus()==null?"":f.getDenumireWorkflowStatus().toLowerCase()).contains(finalSearchText.trim())
                        ||f.getValoarePlatita().toString().contains(finalSearchText.trim())
        ).collect(Collectors.toList()));
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
