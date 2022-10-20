package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.file.MultiFilterPortalFileList;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.portalfile.backend.Ps4ECitizenPortalFileService;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

public class Ps4ECitizenProvisionsPresenter extends PrepareModelFlowPresenter<Ps4ECitizenProvisionsView> {

    @Autowired
    private Ps4ECitizenPortalFileService portalFileService;
    private Integer documentTypeId;
    @Autowired
    DmswsPS4Service dmswsPS4Service;
    @Override
    public void prepareModel(String state) {

        getView().setPortalFileTable(portalFileService.getPortalFileByIdDocType(documentTypeId));
        getView().getSearchFormContainerFiles().enablePresenterSearchEvents();
        getView().i18nInboxContainer();

    }

    public void setDocumentTypeId(Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public void onSearchBtnClick(ClickEvent<Input> clickEvent) {
        doSearch(getView().getSearchFormContainerFiles().getSearchTextValue());
    }


    public void onSearchTextChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textChangeEvent) {

        doSearch(getView().getSearchFormContainerFiles().getSearchTextValue());
    }

    private void doSearch(String searchText) {
        MultiFilterPortalFileList portalFileList = dmswsPS4Service.getPortalFileByIdDocTypeFiltered(SecurityUtils.getToken(), documentTypeId,searchText);
        getView().setPortalFileTable(portalFileList.getPortalFileList());

    }
}
