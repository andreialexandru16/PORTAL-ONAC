package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import elemental.json.JsonObject;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui.component.PoiPopup;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.service.StreamToStringUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ps4ECitizenUrbanDocumentsView extends DivFlowViewBuilder<Ps4ECitizenUrbanDocumentsPresenter> {


    private ComboBox<ProjectInfo> budgetaryFormProjects = new ComboBox<>();

    private Div budgetaryFormRowAdminProjects = new Div(budgetaryFormProjects);

    private ComboBox<ProjectInfo> consultationFormProjects = new ComboBox<>();

    private Div consultationFormRowAdminProjects = new Div(consultationFormProjects);

    private Text budgetaryProjectAddress = new Text("");

    private Div budgetaryProjectAddressLayout = new Div(new Strong("Adresa:"), budgetaryProjectAddress);


    private Text budgetaryTipLucrare = new Text("");

    private Div budgetaryTipLucrareLayout = new Div(new Strong("Tip lucrare:"), budgetaryTipLucrare);

    private Text budgetaryCategory = new Text("");

    private Div budgetaryCategoryLayout = new Div(new Strong("Categorie:"), budgetaryCategory);


    private Text budgetaryProjectDetails = new Text("");

    private Div budgetaryProjectDetailsLayout = new Div(new Strong("Numar: "), budgetaryProjectDetails);

    private Text budgetaryProjectRequester = new Text("");

    private Div budgetaryProjectRequesterLayout = new Div(new Strong("Solicitant: "), budgetaryProjectRequester);


    private Div budgetaryDetails = new Div( budgetaryCategoryLayout, budgetaryProjectDetailsLayout, budgetaryProjectAddressLayout,budgetaryProjectRequesterLayout, budgetaryTipLucrareLayout);

    private Div budgetaryTab = new Div( budgetaryFormRowAdminProjects, budgetaryDetails);


    private Div panelContainer = new Div( budgetaryTab);

    private NativeButton budgetaryTabBtn = new NativeButton();

    private NativeButton consultationTabBtn = new NativeButton();

    private Div panelHeader = new Div(budgetaryTabBtn, consultationTabBtn);

    private Div toggleSideBar = new Div();

    private Div leftPanel = new Div(toggleSideBar, panelHeader, panelContainer);

    private Div gap30 = new Div();

    private Div budgetMap = new Div();

    private Div mapContainer = new Div(leftPanel, gap30, budgetMap);

    private PoiPopup poiPopup = new PoiPopup(this);


    public void setProjectsConsultation(List<ProjectInfo> projects) {
        try{
            consultationFormProjects.setItems(projects);
        }catch (Exception e){

        }
    }

    public Optional<ProjectInfo> getProjectConsultation() {
        return Optional.ofNullable(consultationFormProjects.getValue());
    }

    public void setProjectsBudgetary(List<ProjectInfo> projects, Integer id) {
        try{
            budgetaryFormProjects.setItems(projects);
            budgetaryFormProjects.setValue(projects.stream().filter( p -> p.getId().equals(id)).collect(Collectors.toList()).get(0));
        }catch (Exception e){

        }
    }

    public Optional<ProjectInfo> getProjectBudgetary() {
        return Optional.ofNullable(budgetaryFormProjects.getValue());
    }



    @Override
    protected void buildView() {
        gap30.addClassName("gap-30");
        configLeftPanel();
        mapContainer.addClassName("map_container");
        add(mapContainer);
        budgetMap.setId("map");
//        budgetMap.getStyle().set("min-height", "600px");
        budgetMap.getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/google_map_doc_urban.html"));
        UI.getCurrent().getPage().addJavaScript("frontend/js/gmap.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/doc-urban-map.js");
        UI.getCurrent().getPage().executeJs("loadAfterMapElement($0)", this.getElement());
        poiPopup.hidePopup();
        poiPopup.hideStateProject();
        configBudgetaryFormRowAdminProjects();
        add(poiPopup);
    }

    @ClientCallable
    public void loadGmapApi() {
        UI.getCurrent().getPage().addJavaScript("https://maps.googleapis.com/maps/api/js?libraries=geometry&key=" + getPresenter().getGoogleMapsApiKey() + "&language=ro&region=RO&callback=initMap");
        getPresenter().afterLoadingGmaps();
    }

    @ClientCallable
    public void clickMarker(JsonObject markerInfo) {
        getLogger().info("marker Info:\t" + markerInfo.toJson());

        configBudgetaryTabInfo(markerInfo);

        int poiId = (int) markerInfo.getNumber("id");
        /*poiPopup.showPopUp(poiId, markerInfo.getString("categoriePoi"),
                JreJsonNull.class.isAssignableFrom(markerInfo.get("statusProiect").getClass()) ? "" : markerInfo.getString("statusProiect"),
                markerInfo.getString("creatLa"), markerInfo.getString("descriere"));
        UI.getCurrent().getPage().executeJs("showPopupOnMap($0, $1);", markerInfo, poiPopup.getElement());
*/
        getPresenter().setPoiId(poiId);


    }


    @ClientCallable
    public void clickMap(JsonObject mapInfo) {
        getLogger().info("mapInfo:\t" + mapInfo.toJson());
        configNewBudgetaryTab();

    }

    @ClientCallable
    public void hideMapPopup() {
        poiPopup.hidePopup();
    }

    private void configLeftPanel() {
        leftPanel.getStyle().set("min-height", "600px");
        leftPanel.getStyle().set("margin-top", "100px");
        leftPanel.addClassName("left_panel");
        toggleSideBar.addClassName("toggle_sidebar");
//        toggleSideBar.addClickListener(e -> )
        panelHeader.addClassNames("panel_header", "clearfix");
        panelHeader.getStyle().set("text-align","center");
        panelContainer.addClassName("panel_container");
        budgetaryTabBtn.addClickListener(e -> displayBudgetaryTab());
        configPanelHeaderTabButton(budgetaryTabBtn, "consultare", "fa-user-friends", false, "Documente Urbanistice Emise");
        configBudgetaryTab();
    }

    protected void displayConsultationTab() {
        if (!consultationTabBtn.hasClassName("active")) {
            consultationTabBtn.addClassName("active");
            consultationTabBtn.getElement().setAttribute("status", "active");
            budgetaryTabBtn.removeClassName("active");
            budgetaryTab.removeClassName("show");
            budgetaryTabBtn.getElement().setAttribute("status", "false");
            UI.getCurrent().getPage().executeJs("clearMapPopup();");
        }

    }

    public void hideBudgetary(){
        budgetaryTab.setVisible(false);
        budgetaryTabBtn.setVisible(false);
    }

    public void displayPoiAdmin() {
        displayConsultationTab();
        budgetaryTab.setVisible(false);
        budgetaryTabBtn.setVisible(false);

         }

    protected void displayBudgetaryTab() {
        budgetaryFormRowAdminProjects.addClassName("form-group");
        if (!budgetaryTabBtn.hasClassName("active")) {
            budgetaryTabBtn.addClassName("active");
            budgetaryTab.addClassName("show");
            budgetaryTabBtn.getElement().setAttribute("status", "active");
            consultationTabBtn.removeClassName("active");
            consultationTabBtn.getElement().setAttribute("status", "false");
            configNewBudgetaryTab();
            UI.getCurrent().getPage().executeJs("clearMapPopup();");
        }
      }

    private void configPanelHeaderTabButton(NativeButton tabBtn, String tab, String fasIco, Boolean active, String label) {
        tabBtn.addClassNames("btn", "map_tab");
        tabBtn.getElement().setAttribute("map-tab", tab);
        tabBtn.getElement().setAttribute("status", active ? "active" : "false");
        if (active) {
            tabBtn.addClassName("active");
        }
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", fasIco);
        Span span = new Span(label);
        tabBtn.add(icon, span);
    }

    private HtmlContainer constructConsultationFormActionBtnIco() {
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", "fa-arrow-alt-circle-right");
        return icon;
    }
    private HtmlContainer constructConsultationFormActionBtnIcoCancel() {
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", "fa-arrow-alt-circle-left");
        return icon;
    }

    private void configBudgetaryFormRowAdminProjects() {
        budgetaryFormRowAdminProjects.addClassName("underline");
        budgetaryFormRowAdminProjects.getStyle().set("margin-top","10px");
        budgetaryFormProjects.setAllowCustomValue(false);
        budgetaryFormProjects.setItemLabelGenerator(ProjectInfo::getDenumireSiAdresa);
        budgetaryFormProjects.setPlaceholder("Alege document");

        budgetaryFormProjects.addClassNames("smart-form-control", "chosen-container", "chosen-container-single", "chosen-container-single-nosearch");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", budgetaryFormProjects.getElement());
        budgetaryFormProjects.addValueChangeListener(e ->{
            UI.getCurrent().getPage().executeJs("triggerClickMarker($0,$1);", budgetaryFormProjects.getValue().getId(),this);

        });

    }
    private void configBudgetaryTab() {
        budgetaryTab.setId("bugetare");
        budgetaryTab.addClassNames("panel_tab_content", "show");
        budgetaryTabBtn.getStyle().set("float","none");
        budgetaryDetails.addClassName("details");
        configNewBudgetaryTab();

    }


    public void configNewBudgetaryTab() {
        budgetaryDetails.getStyle().set("display", "none");
  }

    private void configBudgetaryTabInfo(JsonObject poiInfo) {

        budgetaryDetails.getStyle().remove("display");

        budgetaryCategory.setText(poiInfo.getString("categoriePoi"));

 if(poiInfo.getString("denumire")!=null){
            budgetaryProjectDetails.setText(poiInfo.getString("denumire"));
        }

        if(poiInfo.getString("propusDeNume")!=null){

            if(poiInfo.getString("propusDePrenume")!=null) {
                budgetaryProjectRequester.setText(poiInfo.getString("propusDeNume")+" "+ poiInfo.getString("propusDePrenume"));

            }
            else{
                budgetaryProjectRequester.setText(poiInfo.getString("propusDeNume"));

            }
        }


        if(poiInfo.getString("tipLucrare")!=null){
            budgetaryTipLucrare.setText(poiInfo.getString("tipLucrare"));
        }
        try{
            budgetaryProjectAddress.setText(poiInfo.getString("address") );
        }catch (Exception e) {

        }
    }


    private void displayInputLabelOnValue(TextField inputComponent, Div layout) {
        inputComponent.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        inputComponent.getElement().addEventListener("keyup", e -> {
            if (!e.getEventData().getString("element.value").isEmpty()) {
                if (!layout.hasClassName("show_label")) {
                    layout.addClassNames("show_label");
                }
            } else {
                if (layout.hasClassName("show_label")) {
                    layout.removeClassNames("show_label");
                }
            }
        }).addEventData("element.value");

    }

    private void displayInputLabelOnValue(TextArea inputComponent, Div layout) {
        inputComponent.getElement().addEventListener("keyup", e -> {
            if (!e.getEventData().getString("element.value").isEmpty()) {
                if (!layout.hasClassName("show_label")) {
                    layout.addClassNames("show_label");
                }
            } else {
                if (layout.hasClassName("show_label")) {
                    layout.removeClassNames("show_label");
                }
            }
        }).addEventData("element.value");

    }

    @ClientCallable
    public void swalInfoAck() {

        VaadinClientUrlUtil.setLocation("/logout");
    }

    public void setDocumentSelected(ProjectInfo document){
        budgetaryFormProjects.setValue(document);
    }
}
