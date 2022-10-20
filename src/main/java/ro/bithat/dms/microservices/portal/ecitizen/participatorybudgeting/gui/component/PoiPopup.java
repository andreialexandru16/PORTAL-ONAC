package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.StreamResource;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.poi.ProjectFile;
import ro.bithat.dms.microservices.dmsws.poi.ProjectFilesList;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.mvp.FlowView;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PoiPopup extends FlowViewDivContainer {

    private Div poiAttachmentsLayout = new Div();

    private Div poiImageCarouselLayout = new Div();

    private Paragraph poiDescription = new Paragraph();

    private Div poiDescriptionTitle = new Div(new Text("Descriere:"));

    private Div poiDescriptionLayout = new Div(poiDescriptionTitle, poiDescription);

    private Div poiProjectDescriptionLayout = new Div(poiDescriptionLayout);

    private Span poiDueDate = new Span();

    private Div poiDueDateLayout = new Div(new Strong("Data adăugării:"), poiDueDate);

    private Span poiState = new Span();

    private Div poiStateLayout = new Div(new Strong("Statusul sesizarii:"), poiState);

    private Span poiCategory = new Span();

    private Div poiCategoryLayout = new Div(new Strong("Categorie:"), poiCategory);

    private Div poiProjectInfoLayout = new Div(poiCategoryLayout, poiStateLayout, poiDueDateLayout);

    private NativeButton dismissPopUpBtn = new NativeButton();
    Div clearfix2=new Div();
    Div clearfix1=new Div();

    public PoiPopup(FlowView view) {
        super(view);
        addClassNames("map-popup");

        clearfix1.addClassName("clearfix");
        clearfix2.addClassName("clearfix");
        add(dismissPopUpBtn, poiProjectInfoLayout, poiProjectDescriptionLayout, clearfix1,poiImageCarouselLayout, clearfix2,poiAttachmentsLayout);
        dismissPopUpBtn.addClassName("close");
        dismissPopUpBtn.add(new Span("x"));
        dismissPopUpBtn.addClickListener(e -> dismissPopup());
        poiProjectInfoLayout.addClassName("descr_details");
        poiProjectDescriptionLayout.addClassNames("text", "border_bottom");
        poiImageCarouselLayout.addClassNames("wrap-modal-slider");
        poiAttachmentsLayout.addClassName("text");

        poiCategoryLayout.addClassNames("detail", "icon_hotel");
        poiStateLayout.addClassNames("detail", "icon_blue_warning");
        poiDueDateLayout.addClassNames("detail", "icon_calendar");
        poiDescriptionTitle.addClassName("text_title");


    }

    private void dismissPopup() {
        hidePopup();
        UI.getCurrent().getPage().executeJs("dismissMapPopup();");
    }

    public void showPopUp(Integer poiId, String category, String state, String dueDate, String description) {
        UI.getCurrent().getPage().executeJs("initCarouselSliderBugetare();");
        UI.getCurrent().getPage().executeJs("  $('.wrap-modal-slider').addClass('open');");

        getStyle().remove("display");
        poiCategory.setText(category);
        poiState.setText(state);
        poiDueDate.setText(dueDate);
        poiDescription.setText(description);
        ProjectFilesList poiFileList = BeanUtil.getBean(DmswsParticipatoryBudgetingService.class).getAlteFisierePOI(SecurityUtils.getToken(), poiId, Optional.empty());
        constructImagesSlider(poiFileList.getProjectFiles()
                .stream().filter(file -> file.getNumeFisier().endsWith(".jpeg")
                        || file.getNumeFisier().endsWith(".png")
                        || file.getNumeFisier().endsWith(".bmp")
                        || file.getNumeFisier().endsWith(".jpg")).collect(Collectors.toList()));

        constructFilesList(poiFileList.getProjectFiles()
                .stream().filter(file -> !file.getNumeFisier().endsWith(".jpeg")
                        && !file.getNumeFisier().endsWith(".png")
                        && !file.getNumeFisier().endsWith(".bmp")
                        && !file.getNumeFisier().endsWith(".jpg")).collect(Collectors.toList()));

    }
    private void constructFilesList(List<ProjectFile> poiFiles) {
        poiFiles.stream().forEach(poiFile -> constructFile(poiFile));


    }
    private void constructFile(ProjectFile poiFile){
        //divFile.setText(poiFile.getNumeFisier());
        Paragraph pFile= new Paragraph();
        StreamResource poiFileStreamResource = StreamResourceUtil.getStreamResource(poiFile.getNumeFisier(), poiFile.getDownloadLink());

        Anchor poiFileLink = new Anchor();
        poiFileLink.setText(poiFile.getNumeFisier());
        poiFileLink.setHref(poiFileStreamResource);
        poiFileLink.getStyle().set("cursor","pointer");
        poiFileLink.getStyle().set("font-size","16px");

        pFile.add(poiFileLink);
        poiAttachmentsLayout.add(pFile);
    }

    private void constructImagesSlider(List<ProjectFile> poiFiles) {

        poiImageCarouselLayout.removeAll();
        poiImageCarouselLayout.add(clearfix1);

        Div sliderMap = new Div();
        sliderMap.addClassName("slider_map");
        poiImageCarouselLayout.add(sliderMap);
        poiFiles.stream().forEach(poiFile -> constructImageSlider(sliderMap, poiFile));
        poiImageCarouselLayout.add(clearfix2);


    }

    private void constructImageSlider(Div sliderMap, ProjectFile poiFile) {
        StreamResource poiFileStreamResource = StreamResourceUtil.getStreamResource(poiFile.getNumeFisier(), poiFile.getDownloadLink());
        Image poiFileImage = new Image(poiFileStreamResource, "alt");
        poiFileImage.addClassName("border_img");
        poiFileImage.setMaxHeight("100px");
        poiFileImage.setMaxWidth("100px");
        Anchor poiFileLink = new Anchor();
        poiFileLink.add(poiFileImage);
        poiFileLink.setHref(poiFileStreamResource);
        //poiFileLink.addClassNames("thumbnail", "fancybox");
        poiFileLink.getElement().setAttribute("data-fancybox", "gallery");
        //poiFileLink.getElement().setAttribute("rel", "ligthbox");
        Div poiFileLayout = new Div(poiFileLink);
        sliderMap.add(poiFileLayout);
    }

    public void hidePopup() {
        getStyle().set("display", "none");
    }
    public void hideStateProject(){
        poiProjectInfoLayout.remove(poiStateLayout);
    }
}
