package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Corespondenta;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsDocumentService;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.Ps4ECitizenOnlineServiceView;
import ro.bithat.dms.passiveview.ComponentValueChangeEventPresenterMethod;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Ps4ECorespondentaFisierePresenter extends PrepareModelFlowPresenter<Ps4ECorespondentaFisiereView> {

    @Autowired
    private DmswsDocumentService documentService;


    private Map<Integer, List<Corespondenta>> pagesData = new ConcurrentHashMap<>();

    private Optional<String> fileId;

    @Override
    public void prepareModel(String state) {
        fileId= QueryParameterUtil.getQueryParameter("fileId");
        List<Corespondenta> corespondentaList= documentService.getCorespondenta(SecurityUtils.getToken(),fileId.get()).getCorespondentaList();

        for (Corespondenta c : corespondentaList){
            if (c.getSens().trim().toUpperCase().equals("INTRARE"))
                c.setSens(c.getSens().replace("INTRARE","IESIRE"));
            else if (c.getSens().trim().toUpperCase().equals("IESIRE"))
                c.setSens(c.getSens().replace("IESIRE","INTRARE"));
        }

        getView().setMyRequestsTable(corespondentaList);
     }

}