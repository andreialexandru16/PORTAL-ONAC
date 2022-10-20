package ro.bithat.dms.microservices.portal.ecitizen.rehabilitationreq.gui.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.StreamResource;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.poi.ProjectFile;
import ro.bithat.dms.microservices.dmsws.poi.ProjectFilesList;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.microservices.portal.ecitizen.rehabilitationreq.backend.DmswsRehabilitationService;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.mvp.FlowView;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PoiPopupRehab extends FlowViewDivContainer {

    private Div poiAttachmentsLayout = new Div();

    private Div poiImageCarouselLayout = new Div();

    private Paragraph poiDescription = new Paragraph();

    private Div poiDescriptionTitle = new Div(new Text("Descriere:"));

    private Div poiDescriptionLayout = new Div(poiDescriptionTitle, poiDescription);

    private Div poiProjectDescriptionLayout = new Div(poiDescriptionLayout);

    private Span poiDueDate = new Span();

    private Div poiDueDateLayout = new Div(new Strong("Data:"), poiDueDate);

    private Span poiState = new Span();

    private Div poiStateLayout = new Div(new Strong("Statusul sesizarii:"), poiState);

    private Span poiCategory = new Span();

    private Div poiCategoryLayout = new Div(new Strong("Categorie:"), poiCategory);

    private Div poiProjectInfoLayout = new Div(poiCategoryLayout, poiStateLayout, poiDueDateLayout);

    private NativeButton dismissPopUpBtn = new NativeButton();

    public PoiPopupRehab(FlowView view) {
        super(view);
        addClassNames("map-popup");
        add(dismissPopUpBtn, poiProjectInfoLayout, poiProjectDescriptionLayout, poiImageCarouselLayout, poiAttachmentsLayout);
        dismissPopUpBtn.addClassName("close");
        dismissPopUpBtn.add(new Span("x"));
        dismissPopUpBtn.addClickListener(e -> dismissPopup());
        poiProjectInfoLayout.addClassName("descr_details");
        poiProjectDescriptionLayout.addClassNames("text", "border_bottom");
        poiImageCarouselLayout.addClassNames("wrap-modal-slider", "open");
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
        getStyle().remove("display");
        poiCategory.setText(category);
        poiState.setText(state);
        poiDueDate.setText(dueDate);
        poiDescription.setText(description);
        ProjectFilesList poiFileList = BeanUtil.getBean(DmswsRehabilitationService.class).getAlteFisierePOI(SecurityUtils.getToken(), poiId);
        constructImagesSlider(poiFileList.getProjectFiles()
                .stream().filter(file -> file.getNumeFisier().endsWith(".pdf") || file.getNumeFisier().endsWith(".png") || file.getNumeFisier().endsWith(".jpg")).collect(Collectors.toList()));
    }

    private void constructImagesSlider(List<ProjectFile> poiFiles) {
        poiImageCarouselLayout.removeAll();
        Div sliderMap = new Div();
        sliderMap.addClassName("slider_map");
        poiImageCarouselLayout.add(sliderMap);
        poiFiles.stream().forEach(poiFile -> constructImageSlider(sliderMap, poiFile));
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
        poiFileLink.addClassNames("thumbnail", "fancybox");
        poiFileLink.getElement().setAttribute("rel", "ligthbox");
        Div poiFileLayout = new Div(poiFileLink);
        sliderMap.add(poiFileLayout);
    }

    public void hidePopup() {
        getStyle().set("display", "none");
    }
}
