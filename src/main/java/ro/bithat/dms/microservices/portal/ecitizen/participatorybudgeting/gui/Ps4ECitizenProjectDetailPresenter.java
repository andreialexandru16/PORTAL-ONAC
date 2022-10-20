package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.NativeButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class Ps4ECitizenProjectDetailPresenter extends PrepareModelFlowPresenter<Ps4ECitizenProjectDetailView> {


    private Integer idProiect;
    @Value("${poi.attachment.files.type.code}")
    private String poiAttachmentTypeCode;

    @Value("${poi.attachment.photos.type.code}")
    private String poiAttachmentPhotosTypeCode;

    @Value("${poi.attachment.videos.type.code}")
    private String poiAttachmentVideosTypeCode;

    @Autowired
    private DmswsParticipatoryBudgetingService budgetingService;

    @Override
    public void prepareModel(String state) {
        idProiect = QueryParameterUtil.getQueryParameter("idProiect", Integer.class).get();
        ProjectInfo proiectInfo = budgetingService.getInfoPOI(SecurityUtils.getToken(),idProiect);
        if(proiectInfo!=null) {
            getView().setServiceProjectTypeValue("Bugetare participativa");
            getView().setProjectNameValue(proiectInfo.getDenumire());
            getView().setProjectDescriptionValue(proiectInfo.getDescriere());
            getView().setServiceProjectCategoryValue(proiectInfo.getCategoriePoi());
            getView().setServiceProjectAddressValue(proiectInfo.getAddress());
            getView().setServiceProjectDurataValue(proiectInfo.getPropusDurataProiect());
            getView().setServiceProjectBeneficiiValue(proiectInfo.getBeneficii());
            getView().setServiceProjectBeneficiariValue(proiectInfo.getBeneficiari());
            getView().setServiceProjectStartVoteValue(proiectInfo.getDataStartVote());
            getView().setServiceProjectEndVoteValue(proiectInfo.getDataEndVote());
            getView().setServiceProjectVoturiProValue(proiectInfo.getNrVoturiPro().toString());
            getView().setServiceProjectVoturiContraValue(proiectInfo.getNrVoturiContra().toString());
            if(proiectInfo.getValoare()!=null){
                getView().setServiceProjectValueValue(proiectInfo.getValoare().toString()+ " lei");
            }
            getView().setProjectDocumentsTable(budgetingService.getAlteFisierePOI(SecurityUtils.getToken(),idProiect, Optional.ofNullable(poiAttachmentTypeCode)).getProjectFiles());
            getView().setProjectPhotosTable(budgetingService.getAlteFisierePOI(SecurityUtils.getToken(),idProiect, Optional.ofNullable(poiAttachmentPhotosTypeCode)).getProjectFiles());
            getView().setProjectVideosTable(budgetingService.getAlteFisierePOI(SecurityUtils.getToken(),idProiect, Optional.ofNullable(poiAttachmentVideosTypeCode)).getProjectFiles());

            BaseModel checkForUserVote=budgetingService.checkForUserVote(SecurityUtils.getToken(), idProiect);

            if(  hasAuthenticateUser() && !checkForUserVote.getInfo().equals("true")
                    && Ps4ECitizenParticipatoryBudgetingView.checkDatesBetweenCurrent(proiectInfo.getDataStartVote(),proiectInfo.getDataEndVote())) {

                getView().styleVotesContainerToVote();
            }
            else if(!hasAuthenticateUser()){
                getView().styleVotesContainerMessage("Pentru a putea vota trebuie sa fii autentificat!");
            }
            else if(budgetingService.checkForUserVote(SecurityUtils.getToken(), idProiect).getInfo().equals("true")){
                getView().styleVotesContainerMessage("Ati exprimat votul dumnavoastra pe acest proiect: "+checkForUserVote.getExtendedInfo()+" la data de "+checkForUserVote.getExtendedInfo2()+"!");
            }
            else if(!Ps4ECitizenParticipatoryBudgetingView.checkDatesBetweenCurrent(proiectInfo.getDataStartVote(),proiectInfo.getDataEndVote())){
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");
                if(Ps4ECitizenParticipatoryBudgetingView.checkDateBefore(proiectInfo.getDataEndVote(), simpleDateFormat.format(new Date()))){
                    getView().styleVotesContainerMessage("Perioada de votare a acestui proiect s-a incheiat!\n ("
                            +proiectInfo.getDataStartVote()+" -> "
                            +proiectInfo.getDataEndVote()+")");

                }
                else{
                    getView().styleVotesContainerMessage("Perioada de votare a acestui proiect inca nu a inceput!\n ("
                            +proiectInfo.getDataStartVote()+" -> "
                            +proiectInfo.getDataEndVote()+")");
                }

            }

        }


    }


    @ClickEventPresenterMethod(viewProperty = "upVoteProjectBtn")
    public void onUpVoteProject(ClickEvent<NativeButton> clickEvent) {
        getLogger().info("upvote project poiId:\t" + idProiect);
        if (!hasAuthenticateUser()) {
            getLogger().error("Security alert! No logged user wants to vote");
            return;
        }
        budgetingService.POIVote(SecurityUtils.getToken(), idProiect, 1);
        UI.getCurrent().getPage().executeJs(" swalInfo($0,$1);", "Votul a fost inregistrat!",getView());
    }

    @ClickEventPresenterMethod(viewProperty = "downVoteProjectBtn")
    public void onDownVoteProject(ClickEvent<NativeButton> clickEvent) {
        getLogger().info("downvote project poiId:\t" + idProiect);
        if (!hasAuthenticateUser()) {
            getLogger().error("Security alert! No logged user wants to vote");
            return;
        }
        budgetingService.POIVote(SecurityUtils.getToken(), idProiect, 0);
        UI.getCurrent().getPage().executeJs(" swalInfo($0,$1);", "Votul a fost inregistrat!",getView());
    }

    public Boolean hasAuthenticateUser() {
        return !SecurityUtils.getUsername().equalsIgnoreCase("nouser");
    }

}
