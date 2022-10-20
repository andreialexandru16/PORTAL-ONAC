package ro.bithat.dms.smartform.gui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.core.convert.ConversionService;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.Lov;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.passiveview.component.html.LongField;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.smartform.backend.AttributeLinkAlignment;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ro.bithat.dms.boot.BeanUtil.getBean;

@Deprecated
public class DmsSmartForm extends VerticalLayout {

    private final Map<Integer, List<DocAttrLink>> attributeLinkFormMap = new LinkedHashMap<>();

    private final Map<Long, DocAttrLink> attributeLinkMap = new HashMap<>();

    private List<Label> formLabels = new ArrayList<>();

    private final Map<Long, List<Component>> componentsFormMap = new HashMap<>();

    private final AttributeLinkPostProcessor attributeLinkPostProcessor = new AttributeLinkPostProcessor(componentsFormMap, formLabels);

    private Div responsiveFormLayout = new Div();

    private List<HorizontalLayout> formRows = new ArrayList<>();

    private final DocAttrLinkList docAttrLinkList;

    public DmsSmartForm(AttributeLinkList attributeLinks) {
        add(responsiveFormLayout);
        docAttrLinkList = getConversionService().convert(attributeLinks, DocAttrLinkList.class);
        attributeLinks.getAttributeLink().stream()
                .collect(Collectors.groupingBy(attributeLink -> (attributeLink).getRand().intValue())).forEach(this::attributeLinkPostProcessor);
    }


    public void setClassNames(String classNames) {
        responsiveFormLayout.addClassNames(classNames.split(" "));
    }

    public void setRowClassNames(String classNames) {
        formRows.stream().map(hl -> (Div) hl.getChildren().findFirst().get())
                .forEach(divRow -> divRow.addClassNames(classNames.split(" ")));
    }
    public void setLabelsClassNames(String classNames) {
        formLabels.stream()
                .forEach(label -> label.addClassNames(classNames.split(" ")));
    }

    public void setComponentsClassNames(String classNames) {
        componentsFormMap.values()
                .stream()
                .forEach(components -> components.stream()
                        .filter(c -> HasStyle.class.isAssignableFrom(c.getClass()))
                        .map(c -> (HasStyle)c)
                        .forEach(c -> c.addClassNames(classNames.split(" "))));

    }

    public DocAttrLinkList getDocAttrLinkList() {
//        attributeLinkMap
//                .values().stream()
//        docAttrLinkList.setDocAttrLink();
        List<DocAttrLink> list = new ArrayList<>();
        for(DocAttrLink initialSet: attributeLinkMap.values()) {
            Optional<String> value = Optional.ofNullable(attributeLinkPostProcessor.getValue(initialSet));
            if(value.isPresent()) {
                initialSet.setValue(value.get());
                list.add(initialSet);
            }
        }
//        List<DocAttrLink> list = attributeLinkMap.values().stream()
//                .map(docAttrLink -> )
//                .filter(docAttrLink ->  != null)
//                .map(docAttrLink -> {
//                    String value = attributeLinkPostProcessor.getValue(docAttrLink);
//                    System.out.println(value);
//                        docAttrLink.setValue(value);
//                    return docAttrLink;
//                    }
//                ).collect(Collectors.toList());
        docAttrLinkList.setDocAttrLink(list);
        return docAttrLinkList;
    }

    private void convert(DocAttrLink originValue, List<Component> component) {
    }

    private void attributeLinkPostProcessor(Integer row, List<AttributeLink> attributeLinks) {
        Div responsiveFormRow = new Div();
        responsiveFormRow.setWidthFull();
        HorizontalLayout formRow = new HorizontalLayout(responsiveFormRow);
        formRow.setSpacing(false);
        formRow.setPadding(false);
        formRow.setWidthFull();
        formRow.setAlignItems(Alignment.CENTER);
        formRows.add(formRow);
        responsiveFormLayout.add(formRow);
        if(formRowNeedShouldUseAlignment(attributeLinks)) {
            switch (AttributeLinkAlignment.valueOf(attributeLinks.get(0).getAliniere().toUpperCase())) {
                case LEFT: formRow.setJustifyContentMode(JustifyContentMode.START);
                    break;
                case CENTER: formRow.setJustifyContentMode(JustifyContentMode.CENTER);
                    break;
                case RIGHT: formRow.setJustifyContentMode(JustifyContentMode.END);
                    break;
            }
        }
        attributeLinks.stream()
                .sorted(Comparator.comparing(AttributeLink::getColoana))
                .forEach(attributeLink -> attributeLinkPostProcessor(row, attributeLink, responsiveFormRow));
    }

    private boolean formRowNeedShouldUseAlignment(List<AttributeLink> attributeLinks) {
        return attributeLinks.size() == 1;
    }

    private void attributeLinkPostProcessor(Integer row, AttributeLink attributeLink, Div responsiveFormRow) {
        setAndPutDocAttrLink(row, attributeLink);
        responsiveFormLayoutPostProcessor(attributeLink, responsiveFormRow);
    }

    private void responsiveFormLayoutPostProcessor(AttributeLink attributeLink, Div responsiveFormRow) {
        attributeLinkPostProcessor.attributeLinkDataPostProcessor(getAttributeLinkDataType(attributeLink),
                attributeLink, responsiveFormRow);
    }

    private AttributeLinkDataType getAttributeLinkDataType(AttributeLink attributeLink) {
        Optional<String> dataTypeStr = Optional.ofNullable(attributeLink.getDataType());
        if(dataTypeStr.isPresent() && !dataTypeStr.get().isEmpty()) {
            if (Optional.ofNullable(attributeLink.getLovId()).isPresent() && attributeLink.getLovId().compareTo(0) != 0) {
                if (attributeLink.getNrSelectiiLov().compareTo(1) == 0) {
                    if (AttributeLinkDataType.valueOf(dataTypeStr.get()).equals(AttributeLinkDataType.OPT_GROUP)) {
                        return AttributeLinkDataType.OPT_SELECT;
                    } else {
                        return AttributeLinkDataType.OPT_SELECT_DROPBOX;
                    }
                } else if (Optional.ofNullable(attributeLink.getDenumireTipSelectie()).isPresent() && attributeLink.getDenumireTipSelectie().toUpperCase().equals("COMPLEX")) {
                    return AttributeLinkDataType.COMPLEX;
                } else {
                    if (AttributeLinkDataType.valueOf(dataTypeStr.get()).equals(AttributeLinkDataType.OPT_GROUP)) {
                        return AttributeLinkDataType.OPT_SELECT_MULTIVALUE;
                    } else {
                        return AttributeLinkDataType.OPT_SELECT_DROPBOX_MULTIVALUE;
                    }
                }
            } else {
                return AttributeLinkDataType.valueOf(dataTypeStr.get());
            }
        }
        return AttributeLinkDataType.TEXT;
    }

    private void setAndPutDocAttrLink(Integer row, AttributeLink attributeLink) {
        DocAttrLink docAttrLink = getConversionService().convert(attributeLink, DocAttrLink.class);
        List<DocAttrLink> docAttrLinks = Optional.ofNullable(attributeLinkFormMap.get(row)).orElseGet(() -> new ArrayList<>());
        docAttrLinks.add(docAttrLink);
        attributeLinkMap.put(docAttrLink.getAttributeId(), docAttrLink);
        attributeLinkFormMap.put(row, docAttrLinks);
    }


    private ConversionService getConversionService() {
        return  getBean(ConversionService.class);
    }



    abstract class AttributeLinkDataPostProcessor {

        final AttributeLinkDataType attributeLinkDataType;

        final Map<Long, List<Component>> componentsFormMap;

        final Set<Component> changedComponents = new HashSet<>();

        final List<Label> formLabels;

        AttributeLinkDataPostProcessor(AttributeLinkDataType attributeLinkDataType, Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            this.attributeLinkDataType = attributeLinkDataType;
            this.componentsFormMap = componentsForMap;
            this.formLabels = formLabels;
        }
        boolean isSubjectPostProcessor(AttributeLinkDataType attributeLinkDataType) {
            return this.attributeLinkDataType.equals(attributeLinkDataType);
        }

        abstract void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout);

        String getValue(Long attributeId) {
            List<Component> attributeComponents = Optional.ofNullable(getAttributeComponents(attributeId)).orElseGet(() -> new ArrayList<>());
            if(attributeComponents.size() == 0) {
                return null;
            }
            if(attributeComponents.size() == 1) {
                return getSingleValue(attributeComponents.get(0));
            }
            return getValue(attributeComponents);
        }

        protected String getValue(List<Component> attributeComponents) {
            return null;
        }

        protected String getSingleValue(Component component) {
            if(HasValue.class.isAssignableFrom(component.getClass()) && changedComponents.contains(component)) {
                changedComponents.remove(component);
                Object value = ((HasValue)component).getValue();
                if(value == null) {
                    return "";
                }
                if(Lov.class.isAssignableFrom(value.getClass())) {
                    return ((Lov)value).getId() + "";
                } else if(Number.class.isAssignableFrom(value.getClass())) {
                    return (value) + "";
                } else if(Boolean.class.isAssignableFrom(value.getClass())) {
                    return ((Boolean) value) ? "1" : "0";
                } else if(LocalDate.class.isAssignableFrom(value.getClass())) {
                    return ((LocalDate)value).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                }else {
                }
                return getBean(ConversionService.class).convert(value, String.class);
            }

            return null;
        }

        List<Component> getAttributeComponents(Long attributeId) {
            return componentsFormMap.get(attributeId);
        }

        boolean isLovAttribute(AttributeLink attributeLink) {
            Optional<Integer> loveId = Optional.ofNullable(attributeLink.getLovId());
            return loveId.isPresent() && loveId.get().compareTo(0) != 0;
        }

        boolean hasLabel(AttributeLink attributeLink) {
            Optional<String> labelValue =  Optional.ofNullable(attributeLink.getValoareImplicita());
            return  !labelValue.isPresent() || labelValue.get().isEmpty();
        }

        void addComponent(AttributeLink attributeLink, Component component, Div formLayout, boolean addLabel) {
            Long id = (long)attributeLink.getAttributeId();
            Optional<Double> hasWidthPx = Optional.ofNullable(attributeLink.getWidthPx());
            Optional<Double> hasWidthPercent = Optional.ofNullable(attributeLink.getWidthPercent());
            if(HasSize.class.isAssignableFrom(component.getClass()) ) {
                if (hasWidthPx.isPresent() && hasWidthPx.get().compareTo(0d) > 0) {
                    ((HasSize) component).setWidth(attributeLink.getWidthPx() + "px");
                }
                if (hasWidthPercent.isPresent() && hasWidthPercent.get().compareTo(0d) > 0) {
                    ((HasSize) component).setWidth(attributeLink.getWidthPercent() + "%");
                }
            }
            switch (AttributeLinkAlignment.valueOf(attributeLink.getAliniere().toUpperCase())) {
                case LEFT: addComponentJustifyContentMode(component, formLayout, JustifyContentMode.START,
                        addLabel ? Optional.ofNullable(new Label(attributeLink.getLabel())) : Optional.empty());
                    break;
                case CENTER: addComponentJustifyContentMode(component, formLayout, JustifyContentMode.CENTER,
                        addLabel ? Optional.ofNullable(new Label(attributeLink.getLabel())) : Optional.empty());
                    break;
                case RIGHT: addComponentJustifyContentMode(component, formLayout, JustifyContentMode.END,
                        addLabel ? Optional.ofNullable(new Label(attributeLink.getLabel())) : Optional.empty());
                    break;
                default:
                    if(addLabel) {
                        Label label = new Label(attributeLink.getLabel());
                        formLabels.add(label);
                        formLayout.add(label);
                    }
                    formLayout.add(component);
            }
            if(HasValue.class.isAssignableFrom(component.getClass())) {
                List<Component> attributeComponents = Optional.ofNullable(componentsFormMap.get(id)).orElseGet(()->new ArrayList<>());
                attributeComponents.add(component);
                componentsFormMap.put(id, attributeComponents);
                ((HasValue)component).addValueChangeListener( valueChangeEvent -> {
                    changedComponents.add(component);
                });
            }
        }

        void addComponentJustifyContentMode(Component component,  Div formLayout, JustifyContentMode justifyContentMode, Optional<Label> label) {
            HorizontalLayout alignmentLayout = new HorizontalLayout(component);
            if(label.isPresent()) {
                formLabels.add(label.get());
                alignmentLayout.addComponentAsFirst(label.get());
            }
            alignmentLayout.setJustifyContentMode(justifyContentMode);
            alignmentLayout.setAlignItems(Alignment.CENTER);
            formLayout.add(alignmentLayout);
        }

    }



    class DefaultAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        DefaultAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.TEXT, componentsForMap, formLabels);
        }

        boolean isSubjectPostProcessor(AttributeLinkDataType attributeLinkDataType) {
            return true;
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            if(!hasLabel(attributeLink)) {
                TextField defaultValue = new TextField();
                defaultValue.setValue(attributeLink.getValoareImplicita());
                defaultValue.setReadOnly(true);
                addComponent(attributeLink, defaultValue, formLayout, false);
            } else {
                addComponent(attributeLink, new Label(attributeLink.getLabel()), formLayout, false);
            }
        }

        @Override
        String getValue(Long attributeId) {
            return "";
        }

    }

    class LabelAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        LabelAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.LABEL, componentsForMap, formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            addComponent(attributeLink, new Label(attributeLink.getValoareImplicita()), formLayout, false);
        }

    }

    class TitleAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        TitleAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.TITLE, componentsForMap, formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            addComponent(attributeLink, new Strong(attributeLink.getValoareImplicita()), formLayout, false);
        }

    }

    class TextAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        TextAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.TEXT, componentsForMap, formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            if(!hasLabel(attributeLink)) {
                addComponent(attributeLink, new Label(attributeLink.getValoareImplicita()), formLayout, false);
            } else {
                addComponent(attributeLink, new TextField(), formLayout, true);
            }
        }

    }

    class NumericAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        NumericAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.NUMERIC, componentsForMap, formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            Optional<Integer> length = Optional.ofNullable(attributeLink.getLength());
            Optional<Integer> precision = Optional.ofNullable(attributeLink.getPrecision());
            if(precision.isPresent() && precision.get().compareTo(0) != 0) {
                addComponent(attributeLink, new NumberField(), formLayout, true);
                return;
            }
            if(length.isPresent() && length.get() > 9 ){
                addComponent(attributeLink, new LongField(), formLayout, true);
                return;
            }
            addComponent(attributeLink, new IntegerField(), formLayout, true);
        }

    }

    class DatePickerAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        DatePickerAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.DATA, componentsForMap,formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            DatePicker valueDatePicker = new DatePicker();
//            valueDatePicker.getValue()
            valueDatePicker.setLocale(UI.getCurrent().getLocale());
            LocalDate now = LocalDate.now();
            valueDatePicker.setValue(now);
            addComponent(attributeLink, valueDatePicker, formLayout, true);
        }

    }


    class CheckboxAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        CheckboxAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.BOOLEAN, componentsForMap, formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            addComponent(attributeLink, new Checkbox(attributeLink.getLabel()), formLayout, false);
        }

    }

    class CheckboxSelectAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        CheckboxSelectAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.OPT_SELECT_MULTIVALUE, componentsForMap, formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            BeanUtil.getBean(PS4Service.class).getLovList(attributeLink.getLovId())
                    .getLov().stream().forEach(lov -> addComponent(attributeLink,
                    new Checkbox(lov.getValoare()), formLayout, false));
        }

    }

    class TextAreaAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        TextAreaAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.TEXTAREA, componentsForMap, formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            addComponent(attributeLink, new TextArea(attributeLink.getLabel()), formLayout, false);
        }

    }

    class DropdownAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {

        DropdownAttributeLinkDataPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels) {
            super(AttributeLinkDataType.OPT_SELECT_DROPBOX, componentsForMap, formLabels);
        }

        @Override
        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
            ComboBox<Lov> lovComboBox = new ComboBox<>();
            lovComboBox.setItems(BeanUtil.getBean(PS4Service.class).getLovList(attributeLink.getLovId()).getLov());
            lovComboBox.setAllowCustomValue(false);
            lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
            addComponent(attributeLink, lovComboBox, formLayout, true);
        }

    }


    class AttributeLinkPostProcessor {

        DefaultAttributeLinkDataPostProcessor defaultAttributeLinkDataPostProcessor;

        List<AttributeLinkDataPostProcessor> postProcessors = new ArrayList<>();

        Map<Long, AttributeLinkDataPostProcessor> attributeLinkDataPostProcessorMap = new HashMap<>();

        AttributeLinkPostProcessor(Map<Long, List<Component>> componentsForMap, List<Label> formLabels)  {
            defaultAttributeLinkDataPostProcessor = new DefaultAttributeLinkDataPostProcessor(componentsForMap, formLabels);
            postProcessors.add(new TextAttributeLinkDataPostProcessor(componentsForMap, formLabels));
            postProcessors.add(new LabelAttributeLinkDataPostProcessor(componentsForMap, formLabels));
            postProcessors.add(new TitleAttributeLinkDataPostProcessor(componentsForMap, formLabels));
            postProcessors.add(new CheckboxAttributeLinkDataPostProcessor(componentsForMap, formLabels));
            postProcessors.add(new DropdownAttributeLinkDataPostProcessor(componentsForMap, formLabels));
            postProcessors.add(new CheckboxSelectAttributeLinkDataPostProcessor(componentsForMap, formLabels));
            postProcessors.add(new TextAreaAttributeLinkDataPostProcessor(componentsForMap, formLabels));
            postProcessors.add(new NumericAttributeLinkDataPostProcessor(componentsForMap, formLabels));
            postProcessors.add(new DatePickerAttributeLinkDataPostProcessor(componentsForMap, formLabels));
        }

        Optional<AttributeLinkDataPostProcessor> getAttributeLinkPostProcessor(AttributeLinkDataType attributeLinkDataType) {
            return postProcessors.stream().filter(p -> p.isSubjectPostProcessor(attributeLinkDataType)).findFirst();
        }

        void attributeLinkDataPostProcessor(AttributeLinkDataType attributeLinkDataType, AttributeLink attributeLink, Div formLayout) {
            Optional<AttributeLinkDataPostProcessor> attributeLinkDataPostProcessor = getAttributeLinkPostProcessor(attributeLinkDataType);
            if(attributeLinkDataPostProcessor.isPresent()) {
                attributeLinkDataPostProcessor.get().attributeLinkDataPostProcessor(attributeLink, formLayout);
                attributeLinkDataPostProcessorMap.put((long)attributeLink.getAttributeId(), attributeLinkDataPostProcessor.get());
            } else  {
                defaultAttributeLinkDataPostProcessor.attributeLinkDataPostProcessor(attributeLink, formLayout);
                attributeLinkDataPostProcessorMap.put((long)attributeLink.getAttributeId(), defaultAttributeLinkDataPostProcessor);
            }
        }

        String getValue(DocAttrLink docAttrLink) {
            Optional<AttributeLinkDataPostProcessor> attributeLinkDataPostProcessor =
                    Optional.ofNullable(attributeLinkDataPostProcessorMap.get(docAttrLink.getAttributeId()));
            if(attributeLinkDataPostProcessor.isPresent()) {
                return attributeLinkDataPostProcessor.get().getValue(docAttrLink.getAttributeId());
            }

            return "";
        }

    }

}
