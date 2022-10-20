package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfoList;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.portalfile.backend.Ps4ECitizenPortalFileService;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui.component.AddProjectlDialogComponent;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.Optional;

public class Ps4ECitizenUrbanCertificatesPresenter extends PrepareModelFlowPresenter<Ps4ECitizenUrbanCertificatesView> {
    @Value("${poi.project.type.doc.urbanism.id}")
    private String projectTypeDocUrbanismId;

    @Value("${poi.project.doc.urbanism.category.type.id}")
    private String projectCategoryDocUrbanismId;

    @Value("${poi.project.doc.urban.role.admin}")
    private String projectDocUrbanRoleAdmin;

    @Value("${google.maps.api.key2}")
    private String googleMapsApiKey;

    @Autowired
    private Ps4ECitizenPortalFileService portalFileService;
    private Integer documentTypeId;
    @Autowired
    DmswsPS4Service dmswsPS4Service;

    @Autowired
    DmswsParticipatoryBudgetingService budgetingService;
    @Override
    public void prepareModel(String state) {
        String hasRole=  budgetingService.checkIfHasRole(SecurityUtils.getToken(),projectDocUrbanRoleAdmin).getInfo();
        if(hasRole==null || !hasRole.equals("true")){
            getView().hideAddNewDocumentBtn();
        }
        getView().setPortalFileTable(budgetingService.getPOIs(SecurityUtils.getToken(), Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty(), Optional.of(projectCategoryDocUrbanismId)).getProjectInfoList());
        getView().getSearchFormContainerFiles().enablePresenterSearchEvents();
        getView().i18nInboxContainer();
        getView().setAddProjectDialog();

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
        ProjectInfoList portalFileList = budgetingService.getPOIsFiltered(SecurityUtils.getToken(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),Optional.of(projectCategoryDocUrbanismId),Optional.ofNullable(searchText));
        getView().setPortalFileTable(portalFileList.getProjectInfoList());    }
    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }

    public void onAddProjectBtnClick(ClickEvent<NativeButton> clickEvent, TextField nr, TextField address, TextField lastName, TextField firstName, TextField type, AddProjectlDialogComponent dialogComponent){
        ProjectInfo projectInfo= new ProjectInfo();
        if(nr.getValue()==null || nr.getValue().isEmpty()){
            UI.getCurrent().getPage().executeJs("swalError($0);", "Completati numărul!");
            return;
        }
        if(address.getValue()==null || address.getValue().isEmpty()){
            UI.getCurrent().getPage().executeJs("swalError($0);", "Selectați o adresă!");
            return;
        }
        if(firstName.getValue()==null || firstName.getValue().isEmpty()){
            UI.getCurrent().getPage().executeJs("swalError($0);", "Completati prenumele solicitantului!");
            return;
        }
        if(lastName.getValue()==null || lastName.getValue().isEmpty()){
            UI.getCurrent().getPage().executeJs("swalError($0);", "Completati numele solicitantului!");
            return;
        }


        projectInfo.setCod(nr.getValue());
        projectInfo.setDenumire(nr.getValue());
        projectInfo.setDescriere(nr.getValue());
        projectInfo.setAddress(address.getValue());
        projectInfo.setPropusDeNume(lastName.getValue());
        projectInfo.setPropusDePrenume(firstName.getValue());
        projectInfo.setTipLucrare(type.getValue());
        projectInfo.setIdTipProiect(Integer.valueOf(projectTypeDocUrbanismId));
        projectInfo.setIdCategoriePoi(Integer.valueOf(projectCategoryDocUrbanismId));
        projectInfo.setTipProiect("DU");

        budgetingService.addNewPOI(SecurityUtils.getToken(),projectInfo);
        if (projectInfo ==null || !projectInfo.getResult().equals("OK")) {
//            participatoryBudgetingService.
            UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare salvare DMSWS!");
            return;
        } else {
            UI.getCurrent().getPage().executeJs("swalInfo($0);", "Certificatul a fost incarcat cu succes!");
            dialogComponent.closeBtnClick();
            getView().setPortalFileTable(budgetingService.getPOIs(SecurityUtils.getToken(), Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty(), Optional.of(projectCategoryDocUrbanismId)).getProjectInfoList());

        }


    }
}
