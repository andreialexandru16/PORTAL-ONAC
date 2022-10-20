package ro.bithat.dms.smartform.gui;
//22.06.2021 # Neata Georgiana # ANRE #  adaugat atrComplexReadonly; daca e pus pe true=> nu afisez coloana de actiune (adauga rand); ex utilizare in pagina de revizie finala

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.file.ParamCuIstoric;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.Lov;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.smartform.backend.AttributeLinkAlignment;
import ro.bithat.dms.smartform.gui.attribute.ComboBoxAttributeLinkComponent;
import ro.bithat.dms.smartform.gui.attribute.component.AddrMapComponent;
import ro.bithat.dms.smartform.gui.attribute.component.DateHourComponent;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
public class DocumentaSmartForm extends VerticalLayout implements SmartForm {
    private Map<String, Component> componentMap = new HashMap<String, Component>();
    private Map<AttributeLink, Component> componentMapAttr = new HashMap<AttributeLink, Component>();
    private String idComponentPersAudienta = null;
    private List<Div> smartRows = new ArrayList<>();
    private String paramAtributeAscunsePortal;
    private Boolean atrComplexReadonly;
    private String smartFormId;
//    private BeanValidationBinder

    @Override
    public File getHtmlFile() {
//        UI.getCurrent().getPage().
        return null;
    }

    @Override
    public void buildSmartForm(AttributeLinkList attributeLinkList) {
        register(attributeLinkList);
        Div cbLayout = new Div();
        Checkbox componentx = new Checkbox();
        componentx.addClassName("vaadin-ps4-theme");
        Label cbLabel = new Label("Ascunde informații");
        attributeLinkList.getAttributeLink().stream()
                //.collect(Collectors.groupingBy(attributeLink -> (attributeLink).getRand().intValue()))
                .sorted(Comparator.comparing(AttributeLink::getRand))
                .collect(Collectors.groupingBy(AttributeLink::getRand, LinkedHashMap::new, Collectors.toList()))
                .forEach((row, attributeLinks) -> {
                            // if(attributeLinks.get(0).getRand()!=null && attributeLinks.get(0).getRand()!=0 && attributeLinks.get(0).getColoana()!=null&& attributeLinks.get(0).getColoana()!=0){
                            Integer attributeColspan = attributeLinks.size();
                            Div rowLayout = new Div();
                            rowLayout.addClassNames("smart-form-row", "d-block", "d-sm-block", "d-lg-inline-flex", "d-xl-inline-flex",
                                    // "container", "mt-"+attributeColspan);
                                    "container", "mt-1");
                            rowLayout.setWidthFull();
//                    rowLayout.getStyle().set("display", "flex");
                            rowLayout.getStyle().set("justify-content", "space-between");
                            rowLayout.getStyle().set("text-align", "justify");
                            smartRows.add(rowLayout);
                            add(rowLayout);
                            if (attributeLinks.size() == 1) {
                                switch (AttributeLinkAlignment.valueOf(attributeLinks.get(0).getAliniere().toUpperCase())) {
                                    case LEFT:
                                        rowLayout.getStyle().set("justify-content", "flex-start");
                                        break;
                                    case CENTER:
                                        rowLayout.getStyle().set("justify-content", "center");
                                        break;
                                    case RIGHT:
                                        rowLayout.getStyle().set("justify-content", "flex-end");
                                        break;
                                }
                            }

                            attributeLinks.stream().forEach(attributeLink ->
                                    buildAttr(this, attributeLink, rowLayout));
                            // SmartFormSupport.getAttributeLinkService().attributeLinkDataPostProcessor(this, attributeLink, rowLayout));

                        }

                );

        String idDeAscuns=paramAtributeAscunsePortal;
        if(idDeAscuns!=null){
        String[] idAtributeDeAscuns = idDeAscuns.split(",");
        List<String> fixedLenghtList = Arrays.asList(idAtributeDeAscuns);

        DocumentaSmartForm doc=this;
       componentx.addClickListener(e -> {
                    if(componentx.getValue()){
            //        doc.componentMap.get(idDeAscuns).setVisible(false);
                   for(int i=0;i<attributeLinkList.getAttributeLink().size();i++){
                       for(String s:fixedLenghtList){
                       if(attributeLinkList.getAttributeLink().get(i).getAttributeId().equals(Integer.parseInt(s))) {
                           attributeLinkList.getAttributeLink().get(i).setHidden(true);
                           doc.componentMap.get(s).setVisible(false);
                       }
                       }
                   }
                    }else{
                      //  doc.componentMap.get(idDeAscuns).setVisible(true);
                        for(int i=0;i<attributeLinkList.getAttributeLink().size();i++){
                            for(String s:fixedLenghtList){
                            if(attributeLinkList.getAttributeLink().get(i).getAttributeId().equals(Integer.parseInt(s))){
                                attributeLinkList.getAttributeLink().get(i).setHidden(false);
                            doc.componentMap.get(s).setVisible(true);}
                        }
                        }
                    }
               }

        );
        cbLayout.add(cbLabel);
        cbLayout.add(componentx);
        this.add(cbLayout);
        }

    }

    public Map<String, Component> getComponentMap() {
        return componentMap;
    }

    public Map<AttributeLink, Component> getComponentMapAttr() {
        return componentMapAttr;
    }

    private void buildAttr(DocumentaSmartForm documentaSmartForm, AttributeLink attributeLink, Div rowLayout) {
        if (attributeLink.getRand() != null && attributeLink.getRand() != 0
            //&& attributeLink.getColoana()!=null&& attributeLink.getColoana()!=0
                ) {
            SmartFormSupport.getAttributeLinkService().attributeLinkDataPostProcessor(documentaSmartForm, attributeLink, rowLayout);
        }
    }

    @Override
    public void addAttributeLinkComponent(AttributeLink attributeLink, List<Component> components, Component layout, boolean hasLabel) {
        if (!attributeLink.getHidden()) {
            Label attributeLabel = SmartFormSupport.getAttributeLabelAndRegisterAttributeComponents(getId().get(), attributeLink, components);
            if (components.size() == 1) {
                addAttributeLinkComponent(attributeLink, components.get(0), (Div) layout, hasLabel ? Optional.of(attributeLabel) : Optional.empty());


            } else {
                ((HasStyle) layout).removeClassNames("d-lg-inline-flex", "d-xl-inline-flex");
                ((HasStyle) layout).addClassNames("d-lg-block", "d-xl-block");
                components.stream().forEach(c -> addAttributeLinkComponentBlock(attributeLink, c, (Div) layout, hasLabel ? Optional.of(attributeLabel) : Optional.empty()));

            }
            BeanUtil.getBean(SmartFormComponentService.class).getSmartFormComponentsValues().put(attributeLink,attributeLink.getValue());
        }
    }

    ///TODO is bad this way
    private void addAttributeLinkComponentBlock(AttributeLink attributeLink, Component component, Div rowLayout, Optional<Label> label) {
        Long id = (long) attributeLink.getAttributeId();
        Optional<Double> hasWidthPx = Optional.ofNullable(attributeLink.getWidthPx());
        Optional<Double> hasWidthPercent = Optional.ofNullable(attributeLink.getWidthPercent());
        if (HasSize.class.isAssignableFrom(component.getClass())) {
            if (hasWidthPx.isPresent() && hasWidthPx.get().compareTo(0d) > 0) {
                ((HasSize) component).setWidth(attributeLink.getWidthPx() + "px");
            }
            if (hasWidthPercent.isPresent() && hasWidthPercent.get().compareTo(0d) > 0) {
                ((HasSize) component).setWidth(attributeLink.getWidthPercent() + "%");
            }
        }
        Div attributeLayout = new Div();

        HorizontalLayout formControl = new HorizontalLayout(component);
        formControl.setSpacing(false);
        formControl.setPadding(false);
//        formControl.addClassNames("smart-form-control");

        attributeLayout.add(formControl);
//        if(Checkbox.class.isAssignableFrom(component.getClass())) {
//            ((Checkbox)component).getStyle().set("border", "1px solid #0000000");
//            Label cbl = new Label(((Checkbox)component).getLabel());
//            cbl.addClassNames("d-inline-flex", "d-sm-inline-flex", "d-lg-inline-flex", "d-xl-inline-flex");
//            attributeLayout.add(cbl);
//            ((Checkbox)component).setLabel("");
//        }
//        if(label.isPresent()) {
//            attributeLayout.add(label.get());
//            if(attributeLink.getMandatory()) {
//                Span mandatory = new Span("*");
//                mandatory.addClassName("text-danger");
//                label.get().add(mandatory);
//            }
//        }
//        attributeLayout.setWidthFull();
        Div attributeCell = new Div(attributeLayout);
//        attributeCell.getStyle().set("display", "block");
        rowLayout.add(attributeCell);

        if (HasValue.class.isAssignableFrom(component.getClass())) {
            ((HasValue) component).addValueChangeListener(valueChangeEvent -> {
                SmartFormSupport.addSubjectValueComponent(component);
            });
        }

    }


    private void addAttributeLinkComponent(AttributeLink attributeLink, Component component, Div rowLayout, Optional<Label> label) {
        Long id = (long) attributeLink.getAttributeId();
        Div attributeLayout = new Div();
        Div attributeCell = new Div(attributeLayout);
        attributeLayout.addClassName("smart-form-style");
        attributeCell.addClassName("smart-form-component");
        attributeLayout.setId(id + "");
        componentMap.put(id.toString(), attributeLayout);
        Optional<Double> hasWidthPx = Optional.ofNullable(attributeLink.getWidthPx());
        Optional<Double> hasWidthPercent = Optional.ofNullable(attributeLink.getWidthPercent());

        if (HasSize.class.isAssignableFrom(component.getClass())) {
            if (hasWidthPx.isPresent() && hasWidthPx.get().compareTo(0d) > 0) {
                ((HasSize) component).setWidth(attributeLink.getWidthPx() + "px");
            }
            if (hasWidthPercent.isPresent() && hasWidthPercent.get().compareTo(0d) > 0) {
//                if(hasWidthPercent.get().compareTo(100D) == 0) {
//                    ((HasStyle)component).getStyle().set("display", "block");
//                    attributeCell.setWidthFull();
//                } else {
                ((HasSize) component).setWidth(attributeLink.getWidthPercent() + "%");
//                }
            }
        }
        if (label.isPresent()) {
            attributeLayout.add(label.get());
            label.get().addClassNames("d-inline-flex", "d-sm-inline-flex", "d-lg-inline-flex", "d-xl-inline-flex");
            if (!component.getId().isPresent()) {
                component.setId(UUID.randomUUID().toString());
            }
            label.get().setFor(component);
            if (attributeLink.getMandatory()) {
                Span mandatory = new Span("*");
                mandatory.addClassName("text-danger");
                label.get().add(mandatory);
//                if(HasValue.class.isAssignableFrom(component.getClass())) {
//                    BeanValidationBinder<AttributeLink> beanValidationBinder = new BeanValidationBinder<>(AttributeLink.class);
//                    beanValidationBinder.forField((HasValue)component)
//                            .withValidator(new VisitBeanPropertyValidator("campul este obligatoriu"))
//                            .bind("value");
//                }
            }

            HorizontalLayout formControl = new HorizontalLayout(component);
            formControl.setDefaultVerticalComponentAlignment(Alignment.CENTER);

            if (Optional.ofNullable(attributeLink.getAttributeIcon()).isPresent()
                    && !attributeLink.getAttributeIcon().isEmpty()) {
                HtmlContainer icon = new HtmlContainer("i");
                icon.addClassName(attributeLink.getAttributeIcon());
                formControl.addComponentAsFirst(icon);
            }

            formControl.setSpacing(false);
            formControl.setPadding(false);
            if (HasStyle.class.isAssignableFrom(component.getClass())) {
                ((HasStyle) component).addClassNames("vaadin-ps4-theme");
            }
            if (TextField.class.isAssignableFrom(component.getClass())) {
                ((TextField) component).addThemeVariants(TextFieldVariant.LUMO_SMALL);
            }
            if (ComboBox.class.isAssignableFrom(component.getClass()) ||
                    DatePicker.class.isAssignableFrom(component.getClass())) {
                UI.getCurrent().getPage().executeJs("addThemeSmall($0);", component.getElement());
            }

            //Robert Stefan - 14.06.2021 - daca nu e Checkbox ii adaugam clasa de width 100% ca sa treaca label-ul sus.
            if (!Checkbox.class.isAssignableFrom(component.getClass())) {
                attributeLayout.addClassName("smart-form-size");
            }


            formControl.addClassNames("smart-form-control");
            if (hasWidthPercent.isPresent() && hasWidthPercent.get().compareTo(100D) == 0) {
                formControl.setWidthFull();
                attributeCell.setWidthFull();
            } else if (hasWidthPercent.isPresent() && !hasWidthPercent.get().equals(0D) ) {
                formControl.getStyle().set("width",hasWidthPercent.get().toString());
                attributeCell.setWidthFull();
            } else {
                formControl.addClassNames("d-inline-flex", "d-sm-inline-flex", "d-lg-inline-flex", "d-xl-inline-flex");

            }

            if (attributeLink.getDataType() != null && attributeLink.getName().trim().toUpperCase().equals("PERS_AUDIENTA")) {
                String addrBoxId = "PROG_" + formatAttrName(attributeLink.getName());
                component.setId(addrBoxId);
                idComponentPersAudienta = addrBoxId;
                componentMap.put(addrBoxId, component);

                component.getElement().setProperty("disabled","disabled");

            }

            if (attributeLink.getDataType() != null && attributeLink.getDataType().trim().toUpperCase().startsWith("TARA")) {
                String taraBoxId = "TARA_" + formatAttrName(attributeLink.getName());
                component.setId(taraBoxId);
               SmartFormSupport.addAtributeLinkComponentMap(component,attributeLink);
            }
            if (attributeLink.getDataType() != null && attributeLink.getDataType().trim().toUpperCase().startsWith("JUDET")) {
                String judetBoxId = "JUDET_" + formatAttrName(attributeLink.getName());
                component.setId(judetBoxId);
                SmartFormSupport.addAtributeLinkComponentMap(component,attributeLink);
            }
            if (attributeLink.getDataType() != null && attributeLink.getDataType().trim().toUpperCase().startsWith("LOCALITATE")) {
                String localitateBoxId = "LOCALITATE_" + formatAttrName(attributeLink.getName());
                component.setId(localitateBoxId);
                SmartFormSupport.addAtributeLinkComponentMap(component,attributeLink);
            }

            if (attributeLink.getDataType() != null && attributeLink.getDataType().trim().toUpperCase().equals("STRADA")) {
                String USE_MAP_TYPE = null;
                try {
                    USE_MAP_TYPE = SmartFormSupport.getDmswsPS4Service().getSysParam(SecurityUtils.getToken(), "USE_MAP_TYPE").getDescriere();
                }catch (Throwable th){}

                String TARGET_GIS_URL = null;
                try {
                    TARGET_GIS_URL = SmartFormSupport.getDmswsPS4Service().getSysParam(SecurityUtils.getToken(), "TARGET_GIS_URL").getDescriere();
                }catch (Throwable th){}

                if (TARGET_GIS_URL != null && !TARGET_GIS_URL.trim().isEmpty() && USE_MAP_TYPE != null && !USE_MAP_TYPE.trim().isEmpty() && USE_MAP_TYPE.trim().toLowerCase().equals("qgis")) {
                    String addrBoxId = "addrMapAddrBox" + formatAttrName(attributeLink.getName());

                    component.setId(addrBoxId);
                    HtmlContainer icon = new HtmlContainer("i");
                    icon.addClassName("fa");
                    icon.addClassName("fa-map");
                    Button btn = new Button(icon);
                    btn.addClassName("handpointer");

                    AddrMapComponent addrMapComponent = new AddrMapComponent();
                    addrMapComponent.setId("addrMapModal" + formatAttrName(attributeLink.getName()));
                    componentMap.put(addrBoxId, component);

                    formControl.add(addrMapComponent);
                    formControl.add(btn);

                    if(attributeLink.getIdFisier()!=null){
                        TARGET_GIS_URL = TARGET_GIS_URL.replace("{idFisier}",attributeLink.getIdFisier().toString());

                    }
                    String finalTARGET_GIS_URL = TARGET_GIS_URL;
                    btn.addClickListener(e -> {
                        getUI().get().getPage().open(finalTARGET_GIS_URL, "_blank");
                    });
                } else {
                    String addrBoxId = "addrMapAddrBox" + formatAttrName(attributeLink.getName());
                    String addrModalId = "addrMapModal" + formatAttrName(attributeLink.getName());

                    component.setId(addrBoxId);
                    HtmlContainer icon = new HtmlContainer("i");
                    icon.addClassName("fa");
                    icon.addClassName("fa-map");
                    Button btn = new Button(icon);
                    btn.addClassName("handpointer");

                    btn.getElement().setAttribute("data-toggle", "modal");
                    btn.getElement().setAttribute("data-target", "#" + addrModalId);

                    AddrMapComponent addrMapComponent = new AddrMapComponent();
                    addrMapComponent.setId("addrMapModal" + formatAttrName(attributeLink.getName()));
                    componentMap.put(addrBoxId, component);

                    UI.getCurrent().getPage().executeJs("initBootstrapOnShowFunction('" + addrBoxId + "','" + addrModalId + "', $0, $1)", this, component);

                    formControl.add(addrMapComponent);
                    formControl.add(btn);
                }
            }

            if (attributeLink.getDataType() != null && attributeLink.getDataType().trim().toUpperCase().equals("DATA_ORA")) {
                String dataProgramareBoxId = "dataProgramareBox" + formatAttrName(attributeLink.getName());
                component.setId(dataProgramareBoxId);
                component.getElement().setAttribute("readonly","on");
                componentMap.put(dataProgramareBoxId, component);

//                String oraProgramareBoxId = "oraProgramareBoxId" + formatAttrName(attributeLink.getName());
                String idModal = "modalDateHourPicker" + formatAttrName(attributeLink.getName());
                String textButton = "Selectează data și ora";


                Span span = new Span(textButton);
                Anchor href = new Anchor();
                href.add(span);
                href.setHref("javascript:void(0)");
                href.getElement().setAttribute("data-toggle", "modal");
                href.getElement().setAttribute("data-target", "#" + idModal);
                href.addClassName("btn");
                href.addClassName("btn-secondary");
                href.addClassName("btn-sm");
                href.addClassName("next");
                Div button = new Div();
                button.addClassName("col-12");
                button.add(href);

                DateHourComponent dateHourComponent = new DateHourComponent();
                dateHourComponent.setId(idModal);
                UI.getCurrent().getPage().executeJs("jsInit('" + dataProgramareBoxId + "','" + idModal + "', $0, $1, $2)", this, component,idComponentPersAudienta);
                //UI.getCurrent().getPage().executeJs("datepickerInit('"  + idModal + "')", this, component);


                formControl.add(dateHourComponent);
                formControl.add(button);
            }


            attributeLayout.add(formControl);
        } else {
            attributeLayout.add(component);
        }


//        attributeLayout.addClassNames("if_tbl");
//                "d-lg-inline-block", "d-sm-inline-block", "d-lg-inline-block");
//        attributeLayout.getStyle().set("display", "inline-block");
//        attributeLayout.getStyle().set("justify-content", "space-between");
        //        attributeLayout.getStyle().set("text-align", "center");


        attributeCell.addClassNames("d-inline-block",
                "d-lg-inline-block", "d-sm-inline-block", "d-xl-inline-block");
//        attributeCell.getStyle().set("text-align", "center");
//        attributeCell.setWidthFull();
        rowLayout.add(attributeCell);

        switch (AttributeLinkAlignment.valueOf(attributeLink.getAliniere().toUpperCase())) {
            case LEFT:
                attributeCell.getStyle().set("justify-content", "flex-start");
                break;
            case CENTER:
                attributeCell.getStyle().set("justify-content", "center");
                break;
            case RIGHT:
                attributeCell.getStyle().set("justify-content", "flex-end");
                break;
        }

//        if(Optional.ofNullable(attributeLink.getLabel()).isPresent() && !attributeLink.getLabel().isEmpty() && (
//          attributeLink.getLabel().equalsIgnoreCase("Subsemnatul") ||
//                attributeLink.getLabel().contains("domiciliat") ||
//                attributeLink.getLabel().equalsIgnoreCase("Email") ||
//                /*attributeLink.getLabel().equalsIgnoreCase("Data") ||*/
//                attributeLink.getLabel().contains("CNP"))){
//            SmartFormSupport.addSubjectValueComponent(component);
//        }


        if (HasValue.class.isAssignableFrom(component.getClass())) {
            if(attributeLink.getReadOnly()){
                component.getElement().setAttribute("readonly","on");
               ((HasValue)component).setReadOnly(true);
            }
            ((HasValue) component).addValueChangeListener(valueChangeEvent -> {
                SmartFormSupport.addSubjectValueComponent(component);
                SmartFormSupport.validate(getId().get(), attributeLink);
            });
        }

        componentMapAttr.put(attributeLink, component);

    }

    @ClientCallable
    public void saveAdresa(String id, String adresa) {
        Component component = componentMap.get(id);
        if (component != null) {
            if (TextField.class.isAssignableFrom(component.getClass())) {
                ((TextField) component).setValue(adresa);
            }
        }
    }

    @ClientCallable
    public void saveAudienta(String id, String dataOra, String idComponentPersAudienta, String idLov, String valLov) {
        Component component = componentMap.get(id);
        if (component != null) {
            if (TextField.class.isAssignableFrom(component.getClass())) {
                try {
                    String pattern = "dd/MM/yyyy hh:mm";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dataOra);
                    String date = simpleDateFormat.format(date1);
                    ((TextField) component).setValue(date);

                }catch(Exception e){

                }

            }
        }

        Component componentAudienta = componentMap.get(idComponentPersAudienta);
        Lov tes = new Lov();
        tes.setId(idLov);
        tes.setValoare(valLov);
        if (componentAudienta != null) {
            if (ComboBox.class.isAssignableFrom(componentAudienta.getClass())) {
                ((ComboBox) componentAudienta).setValue(tes);
      //          componentAudienta.getElement().setProperty("disabled","disabled");
            }
        }
    }

    private String formatAttrName(String name) {
        if (name == null) {
            return null;
        }

        return name.replaceAll("\\s", "_");
    }



//    private void buildFormGroupFor(Component inputComponent, String i18nLabelValue)  {
//        Label inputLabel = new Label(i18nLabelValue);
//        inputComponent.setId(UUID.randomUUID().toString());
//        ((HasValue)inputComponent).addValueChangeListener( e -> getPresenter().validateContactForm());
//        ((HasStyle)inputComponent).addClassNames("vaadin-ps4-theme");
//        if(TextField.class.isAssignableFrom(inputComponent.getClass())) {
//            ((TextField) inputComponent).addThemeVariants(TextFieldVariant.LUMO_SMALL);
//        }
//        ((HasSize)inputComponent).setWidthFull();
//        HorizontalLayout formControl = new HorizontalLayout(inputComponent);
//        formControl.addClassName("form-control");
//        inputLabel.setFor(inputComponent);
//        Div formGroup = new Div(inputLabel, formControl);
//        formGroup.addClassName("form-group");
//        formContainer.add(formGroup);
//    }
//

    public String getParamAtributeAscunsePortal() {
        return paramAtributeAscunsePortal;
    }

    public void setParamAtributeAscunsePortal(String paramAtributeAscunsePortal) {
        this.paramAtributeAscunsePortal = paramAtributeAscunsePortal;
    }

    public Boolean getAtrComplexReadonly() {
        return atrComplexReadonly;
    }

    public void setAtrComplexReadonly(Boolean atrComplexReadonly) {
        this.atrComplexReadonly = atrComplexReadonly;
    }


}
