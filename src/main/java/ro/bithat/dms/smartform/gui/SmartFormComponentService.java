package ro.bithat.dms.smartform.gui;
// Neata Georgiana # 23.06.2021 # ANRE # add functie care se apeleaza la click pe un lov (aduce lista de valori cu API nou care tine cont de lov uri dependente)


import com.vaadin.flow.component.*;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.DateRangeValidator;
import com.vaadin.flow.spring.annotation.UIScope;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.vaadin.gatanaso.MultiselectComboBox;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.file.RowAttrComplexList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.Lov;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.service.SpelParserUtil;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.attribute.ComplexAttributeLinkComponent;
import ro.bithat.dms.smartform.gui.attribute.binder.*;
import ro.bithat.dms.smartform.gui.attribute.component.HolographicComponent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.bithat.dms.boot.BeanUtil.getBean;

@Service
@UIScope
public class SmartFormComponentService {


    private final static Logger logger = LoggerFactory.getLogger(SmartFormComponentService.class);
    HashMap<Integer, HashMap<Component, AttributeLink>> mapComponenteRandAtribute = new HashMap<>();

    final Set<Component> changedComponents = new HashSet<>();

    private final Map<String, Component> smartFormMap = new ConcurrentHashMap<>();

    private Map<AttributeLink, Component> smartFormComponents = new HashMap<>();
    private Map<AttributeLink, String> smartFormComponentsValues = new HashMap<>();

    private final Map<String, DocAttrLinkList> smartFormBindingMap = new HashMap<>();

    private final Map<String, Map<Integer, List<DocAttrLink>>> attributeLinkFormMap = new ConcurrentHashMap<>();

    private final Map<String, Map<Long, DocAttrLink>> attributeLinkMap = new HashMap<>();

    private final Map<String, Map<Long, List<Lov>>> lovAttributeLinkMap = new HashMap<>();

    //lista cu toate atributele complexe
    private  final Map<String, Map<Long, List<RowAttrComplexList>>> attributeLinkMapComplex = new HashMap<>();

    private Map<String, Map<Long, Label>> formLabels = new HashMap<>();

    private final Map<String, Map<Long, List<Component>>> componentsFormMap = new HashMap<>();

    private final Map<String, Map<Long, List<Component>>> printableComponentsFormMap = new HashMap<>();

    private final List<String> toRemoveMandatory = new ArrayList<>();

    public  final Map<String, Map<String, BeanValidationBinder<AttributeBinderBean>>> attributeBinderMap = new HashMap<>();

    public ComplexAttributeLinkComponent complexAttributeLinkComponent=new ComplexAttributeLinkComponent();
    @Autowired
    private ConversionService conversionService;

    public void registerSmartForm(String smartFormId, Component smartForm, AttributeLinkList attributeLinkList) {
        Assert.isAssignable(SmartForm.class, smartForm.getClass());
        smartFormMap.put(smartFormId, smartForm);
        smartFormBindingMap.put(smartFormId, conversionService.convert(attributeLinkList, DocAttrLinkList.class));
        attributeLinkFormMap.put(smartFormId, new LinkedHashMap<>());
        attributeLinkMap.put(smartFormId, new HashMap<>());
        lovAttributeLinkMap.put(smartFormId, new HashMap<>());
        // imi adaug un map cu smartform ul si un hash in care pun lista de atribute complexe
        attributeLinkMapComplex.put(smartFormId, new HashMap<>());
        formLabels.put(smartFormId, new HashMap<>());
        componentsFormMap.put(smartFormId, new HashMap<>());
        printableComponentsFormMap.put(smartFormId, new HashMap<>());
        attributeBinderMap.put(smartFormId, new HashMap<>());
        attributeLinkList.getAttributeLink().stream()
                .collect(Collectors.groupingBy(attributeLink -> (attributeLink).getRand().intValue()))
                .forEach((row, attributeLinks) -> attributeLinkPostProcessor(smartFormId, row, attributeLinks));
    }

    private void attributeLinkPostProcessor(String smartFormId, Integer row, List<AttributeLink> attributeLinks) {
        attributeLinks.stream().forEach(attributeLink -> setAndPutDocAttrLink(smartFormId, row, attributeLink));
    }


    private void setAndPutDocAttrLink(String smartFormId, Integer row, AttributeLink attributeLink) {
        DocAttrLink docAttrLink = conversionService.convert(attributeLink, DocAttrLink.class);
        List<DocAttrLink> docAttrLinks = Optional.ofNullable(attributeLinkFormMap.get(smartFormId).get(row))
                .orElseGet(() -> new ArrayList<>());
        docAttrLinks.add(docAttrLink);
        attributeLinkMap.get(smartFormId).put(docAttrLink.getAttributeId(), docAttrLink);
        attributeLinkFormMap.get(smartFormId).put(row, docAttrLinks);
    }

    public <T> void bind(String smartFormId, AttributeBinderBean<T> attributeBinderBean, HasValue attributeField, Validator... validators) {
        BeanValidationBinder attributeBinder = getAttributeBinder(attributeBinderBean);
        Binder.BindingBuilder<AttributeBinderBean<T>, T> bindingBuilder = attributeBinder.forField(attributeField);
        if (!attributeBinderBean.getAttributeLink().getHidden()) {
            for (Validator validator : validators) {
                bindingBuilder = bindingBuilder.withValidator(validator);
            }
            if (attributeBinderBean.getAttributeLink().getValidator() != null && !attributeBinderBean.getAttributeLink().getValidator().isEmpty()) {
                bindingBuilder = bindingBuilder.withValidator(new SpELAttributeBeanPropertyValidator(attributeBinderBean.getAttributeLink().getValidator(), ""));
            } else if (attributeBinderBean.getAttributeLink().getMandatory()) {

                if( attributeBinderBean.getAttributeLink().getValue()!=null && attributeBinderBean.getAttributeLink().getValue().equals(" ") ){
                    attributeBinderBean.getAttributeLink().setValue("");
                }

                bindingBuilder = bindingBuilder.withValidator(new MandatoryAttributeBeanPropertyValidator(""));
            }
        }
        bindingBuilder.bind(AttributeBinderBean::getValue, AttributeBinderBean::setValue);
        attributeBinder.setBean(attributeBinderBean);
        attributeBinderMap.get(smartFormId).put(attributeBinderBean.getAttributeLink().getAttributeId().toString(), attributeBinder);
    }
    public <T> void bindWithRow(String smartFormId, AttributeBinderBean<T> attributeBinderBean, HasValue attributeField,Integer rowIndex,Validator... validators) {
        BeanValidationBinder attributeBinder = getAttributeBinder(attributeBinderBean);
        Binder.BindingBuilder<AttributeBinderBean<T>, T> bindingBuilder = attributeBinder.forField(attributeField);
        if (!attributeBinderBean.getAttributeLink().getHidden()) {
            for (Validator validator : validators) {
                bindingBuilder = bindingBuilder.withValidator(validator);
            }
            if (attributeBinderBean.getAttributeLink().getValidator() != null && !attributeBinderBean.getAttributeLink().getValidator().isEmpty()) {
                bindingBuilder = bindingBuilder.withValidator(new SpELAttributeBeanPropertyValidator(attributeBinderBean.getAttributeLink().getValidator(), ""));
            } else if (attributeBinderBean.getAttributeLink().getMandatory()) {

                if( attributeBinderBean.getAttributeLink().getValue()!=null && attributeBinderBean.getAttributeLink().getValue().equals(" ") ){
                    attributeBinderBean.getAttributeLink().setValue("");
                }

                bindingBuilder = bindingBuilder.withValidator(new MandatoryAttributeBeanPropertyValidator(""));
            }
        }
        bindingBuilder.bind(AttributeBinderBean::getValue, AttributeBinderBean::setValue);
        attributeBinder.setBean(attributeBinderBean);
        attributeBinderMap.get(smartFormId).put(attributeBinderBean.getAttributeLink().getAttributeId()+"_"+rowIndex, attributeBinder);
    }
    public <T> void unbind(String smartFormId, AttributeBinderBean<T> attributeBinderBean, HasValue attributeField, Validator... validators) {

        BeanValidationBinder attributeBinder = getAttributeBinder(attributeBinderBean);
        Binder.BindingBuilder<AttributeBinderBean<T>, T> bindingBuilder = attributeBinder.forField(attributeField);
        if (!attributeBinderBean.getAttributeLink().getHidden()) {

            attributeBinderBean.getAttributeLink().setValue(" ");
        }

        bindingBuilder.bind(AttributeBinderBean::getValue, AttributeBinderBean::setValue);
        attributeBinder.setBean(attributeBinderBean);
        attributeBinderMap.get(smartFormId).put(attributeBinderBean.getAttributeLink().getAttributeId().toString(), attributeBinder);
    }

    public <T> void bindConverter(String smartFormId, AttributeBinderBean<T> attributeBinderBean, HasValue attributeField, Converter... converters) {
        BeanValidationBinder attributeBinder = getAttributeBinder(attributeBinderBean);
        Binder.BindingBuilder<AttributeBinderBean<T>, T> bindingBuilder = attributeBinder.forField(attributeField);
        if (!attributeBinderBean.getAttributeLink().getHidden()) {
            for (Converter converter : converters) {
                bindingBuilder = bindingBuilder.withConverter(converter);
            }
        }
        bindingBuilder.bind(AttributeBinderBean::getValue, AttributeBinderBean::setValue);
        attributeBinder.setBean(attributeBinderBean);
        attributeBinderMap.get(smartFormId).put(attributeBinderBean.getAttributeLink().getAttributeId().toString(), attributeBinder);
    }

    public DocAttrLinkList getInitialDocAttrLinkList(String smartFormId) {
        return smartFormBindingMap.get(smartFormId);
    }

    public boolean validate(String smartFormId) {
        Optional<Map<String, BeanValidationBinder<AttributeBinderBean>>> binderMap =
                Optional.ofNullable(attributeBinderMap.get(smartFormId));
        boolean valid = true;
        if (binderMap.isPresent()) {
            for (BeanValidationBinder binder : binderMap.get().values()) {
                Integer idDocSel = null;
                try {
                    idDocSel = ((StringAttributeBinderBean) binder.getBean()).getAttributeLink().getIdDocumentSelectie();

                } catch (Exception e) {
                    idDocSel = null;
                }

                String codAtribut = null;
                try {
                    codAtribut = ((StringAttributeBinderBean) binder.getBean()).getAttributeLink().getName();
                } catch (Exception e) {
                    codAtribut = null;
                }

                if (codAtribut == null) {
                    try {
                        codAtribut = ((LocalDateAttributeBinderBean) binder.getBean()).getAttributeLink().getName();
                    } catch (Exception e) {
                        codAtribut = null;
                    }
                }
                //decomentat pentru a valida inclusiv atributele complexe (ex validare obligativitate completare)
               // if (idDocSel == null || idDocSel.equals(0)) {

                //Adaugat verificare pentru obligativitate - sa nu fie in lista toRemoveMandatory
                    if (!binder.isValid() && (codAtribut == null || !toRemoveMandatory.contains(codAtribut))) {
                        binder.validate();
                        valid = false;
                    }

               // }


            }
        }
        return valid;
    }

    public boolean validate(String smartFormId, AttributeLink attributeLink) {
        Optional<BeanValidationBinder> binder = Optional.ofNullable(attributeBinderMap.get(smartFormId).get(attributeLink.getAttributeId()));
        if (binder.isPresent()) {
            return binder.get().validate().isOk();
        }
        return true;
    }

    private BeanValidationBinder<? extends AttributeBinderBean> getAttributeBinder(AttributeBinderBean attributeBinderBean) {
        return new BeanValidationBinder<>(attributeBinderBean.getClass());
    }

    public Component getSmartForm(String smartFormId) {
        return smartFormMap.get(smartFormId);
    }


    public void setReadOnly(String smartFormId, Boolean readOnly) {
        componentsFormMap.get(smartFormId)
                .values().forEach(l -> l.stream()
                .filter(c -> HasValue.class.isAssignableFrom(c.getClass()))
//                .forEach(this::transformReadOnlyToLable));
                .map(c -> (HasValue) c)
                .forEach(c -> c.setReadOnly(readOnly)));

    }


    public void makeSmartFormPrintable(SmartForm smartForm) {
        Component smartFormComponent = (Component) smartForm;
        componentsFormMap.get(smartFormComponent.getId().get()).
                keySet().forEach(sfcId -> replaceWithPrintableComponent(smartForm, sfcId, componentsFormMap.get(smartFormComponent.getId().get()).get(sfcId)));
    }

    private void replaceWithPrintableComponent(SmartForm smartForm, Long sfcId, List<Component> components) {
        Component smartFormComponent = (Component) smartForm;
        List<Component> printableComponents = printableComponentsFormMap.get(smartFormComponent.getId().get()).get(sfcId);
        if (printableComponents == null) {
            components.stream().forEach(component -> appendPrintableComponent(smartForm, sfcId, component));
            printableComponents = printableComponentsFormMap.get(smartFormComponent.getId().get()).get(sfcId);
        }
        replaceWithPrintableComponent(components, printableComponents);
    }

    private void appendPrintableComponent(SmartForm smartForm, Long sfcId, Component component) {
        Component smartFormComponent = (Component) smartForm;
        List<Component> printableComponents = printableComponentsFormMap.get(smartFormComponent.getId().get()).get(sfcId);
        if (printableComponents == null) {
            printableComponentsFormMap.get(smartFormComponent.getId().get()).put(sfcId, new ArrayList<>());
            printableComponents = printableComponentsFormMap.get(smartFormComponent.getId().get()).get(sfcId);
        }
        if (TextField.class.isAssignableFrom(component.getClass())) {
            TextField textField = (TextField) component;
            Text input = new Text(textField.getValue());
//            input.getClassNames().stream().forEach(cn -> input.addClassName(cn));
//            if(Optional.ofNullable(textField.getWidth()).isPresent() && !textField.getWidth().isEmpty()) {
//                input.setWidth(textField.getWidth());
//            }
            printableComponents.add(input);
            printableComponentsFormMap.get(smartFormComponent.getId().get()).put(sfcId, printableComponents);
            return;
        }
        if (ComboBox.class.isAssignableFrom(component.getClass())) {
            ComboBox comboBox = (ComboBox) component;
            if (comboBox.getValue() != null) {
                if (Lov.class.isAssignableFrom(comboBox.getValue().getClass())) {
                    Lov lov = (Lov) comboBox.getValue();
                    Text input = new Text(lov.getValoare());
                    printableComponents.add(input);
                    printableComponentsFormMap.get(smartFormComponent.getId().get()).put(sfcId, printableComponents);
                    return;
                }
            }
        }
        if (TextArea.class.isAssignableFrom(component.getClass())) {
            TextArea textArea = (TextArea) component;
            Text input = new Text(textArea.getValue());
//            input.getClassNames().stream().forEach(cn -> input.addClassName(cn));
//            if(Optional.ofNullable(textArea.getWidth()).isPresent() && !textArea.getWidth().isEmpty()) {
//                input.setWidth(textArea.getWidth());
//            }
            printableComponents.add(input);
            printableComponentsFormMap.get(smartFormComponent.getId().get()).put(sfcId, printableComponents);
            return;
        }
        if (DatePicker.class.isAssignableFrom(component.getClass())) {
            DatePicker datePicker = (DatePicker) component;
            Text input = new Text((datePicker != null && datePicker.getValue() != null) ? datePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "");
//            input.add(new Text();
//            input.getClassNames().stream().forEach(cn -> input.addClassName(cn));
//            if(Optional.ofNullable(datePicker.getWidth()).isPresent() && !datePicker.getWidth().isEmpty()) {
//                input.setWidth(datePicker.getWidth());
//            }
            printableComponents.add(input);
            printableComponentsFormMap.get(smartFormComponent.getId().get()).put(sfcId, printableComponents);
            return;
        }
        if (Checkbox.class.isAssignableFrom(component.getClass())) {
            Checkbox checkbox = (Checkbox) component;
            if (checkbox.getValue()) {
                Text input = new Text("X\t" + checkbox.getLabel());
                printableComponents.add(input);
                printableComponentsFormMap.get(smartFormComponent.getId().get()).put(sfcId, printableComponents);
                return;
            }
        }
        printableComponents.add(component);
        printableComponentsFormMap.get(smartFormComponent.getId().get()).put(sfcId, printableComponents);

    }

    private void replaceWithPrintableComponent(List<Component> components, List<Component> printableComponents) {
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (component.getParent().isPresent()) {
                HasComponents parent = (HasComponents) component.getParent().get();
                parent.remove(component);
                parent.add(printableComponents.get(i));
            }
        }
    }


    public void exitPrintingMode(SmartForm smartForm) {
        Component smartFormComponent = (Component) smartForm;
        printableComponentsFormMap.get(smartFormComponent.getId().get()).
                keySet().forEach(sfcId -> replaceWithComponent(smartForm, sfcId, printableComponentsFormMap.get(smartFormComponent.getId().get()).get(sfcId)));

    }

    private void replaceWithComponent(SmartForm smartForm, Long sfcId, List<Component> printableComponents) {
        Component smartFormComponent = (Component) smartForm;
        List<Component> components = componentsFormMap.get(smartFormComponent.getId().get()).get(sfcId);
        replaceWithComponent(components, printableComponents);
    }

    private void replaceWithComponent(List<Component> components, List<Component> printableComponents) {
        for (int i = 0; i < printableComponents.size(); i++) {
            Component printableComponent = printableComponents.get(i);
            if (printableComponent.getParent().isPresent()) {
                HasComponents parent = (HasComponents) printableComponent.getParent().get();
                parent.remove(printableComponent);
                parent.add(components.get(i));
            }
        }
    }

    public DocAttrLinkList getSmartFromBinding(String smartFormId) {
        return smartFormBindingMap.get(smartFormId);
    }

    public void setSmartFromBindingDocAttrLinkList(String smartFormId, List<DocAttrLink> docAttrLinks) {
        smartFormBindingMap.get(smartFormId).setDocAttrLink(docAttrLinks);
    }


    public Map<Integer, List<DocAttrLink>> getSmartFormDocAttrLinkRowsConfiguration(String smartFormId) {
        if (!Optional.ofNullable(getSmartForm(smartFormId)).isPresent()) {
            //TODO throw
            logger.error("smartFormId:\t" + smartFormId + "\t not register in SmartFormComponentService!");
            new NotFoundException("smartFormId:\t" + smartFormId + "\t not register in SmartFormComponentService!");
        }
        return attributeLinkFormMap.get(smartFormId);
    }

    public String getValue(String smartFormId, DocAttrLink docAttrLink) {
        List<Component> attributeComponents =
                componentsFormMap.get(smartFormId).get(docAttrLink.getAttributeId());
        if (attributeComponents == null || attributeComponents.isEmpty()) {
            return null;
        }
        if (attributeComponents.size() == 1) {
            return getSingleValue(attributeComponents.get(0));
        }
        List<Lov> lovList = lovAttributeLinkMap.get(smartFormId).get(docAttrLink.getAttributeId());
        if (lovList == null || lovList.isEmpty()) {
            return null;
        }
        return getLovValue(attributeComponents, lovList);
    }

    public String getImage(String smartFormId, DocAttrLink docAttrLink) {
        List<Component> attributeComponents =
                componentsFormMap.get(smartFormId).get(docAttrLink.getAttributeId());
        if (attributeComponents == null || attributeComponents.isEmpty()) {
            return null;
        }
            return ((HolographicComponent)attributeComponents.get(0)).getImage();
    }

    public String getValueLov(String smartFormId, DocAttrLink docAttrLink) {
        List<Component> attributeComponents =
                componentsFormMap.get(smartFormId).get(docAttrLink.getAttributeId());
        List<Lov> lovList = lovAttributeLinkMap.get(smartFormId).get(docAttrLink.getAttributeId());
        if (lovList == null || lovList.isEmpty()) {
            return null;
        }
        Lov lov = new Lov();
        try{
            lov = (Lov) ((ComboBox) attributeComponents.get(0)).getValue();

        }catch (Exception e){

        }
        return lov.getId() == null ? lov.getValoare() : lov.getId().toString();
    }

    public String getValueLovOpt(String smartFormId, DocAttrLink docAttrLink) {
        List<Component> attributeComponents =
                componentsFormMap.get(smartFormId).get(docAttrLink.getAttributeId());
        List<Lov> lovList = lovAttributeLinkMap.get(smartFormId).get(docAttrLink.getAttributeId());
        if (lovList == null || lovList.isEmpty()) {
            return null;
        }
        if(attributeComponents.size()>1 && Checkbox.class.isAssignableFrom(attributeComponents.get(0).getClass())){
            ArrayList<String> list = new ArrayList<>();
            for (Component c : attributeComponents){
                Boolean value = ((Checkbox)c).getValue();
                if(value){
                    Optional<Lov> lovChecked = lovList.stream()
                            .filter(bv ->  ((Checkbox) c).getLabel().equalsIgnoreCase(bv.getValoare()))
                            .findFirst();
                    if(lovChecked.isPresent()){
                        list.add(lovChecked.get().getId());
                    }
                }
            }
            if(list.size()>0){
                return String.join(",",list);
            }else{
                return null;
            }
        }
       return null;
    }

    public List<RowAttrComplexList> getValueAtrComplex(String smartFormId, DocAttrLink attrComplex) {
        List<RowAttrComplexList> listaAtributeAtrComplex =
                attributeLinkMapComplex.get(smartFormId).get(attrComplex.getAttributeId());


        if (listaAtributeAtrComplex == null || listaAtributeAtrComplex.isEmpty()) {
            return null;
        }

        return listaAtributeAtrComplex;
    }
    public List<RowAttrComplexList> getValueAtrComplexFisier(String smartFormId, DocAttrLink attrComplex) {
        //componentsFormMap
        List<RowAttrComplexList> listaAtributeAtrComplex =
                attributeLinkMapComplex.get(smartFormId).get(attrComplex.getAttributeId());
        if (listaAtributeAtrComplex == null || listaAtributeAtrComplex.isEmpty()) {
            return null;
        }
        List<RowAttrComplexList> listaAtributeAtrComplexFinal= new ArrayList<>();
        for(RowAttrComplexList rowAttrComplexList: listaAtributeAtrComplex){
            List<DocAttrLink> listaAttr= new ArrayList<>();

            if( mapComponenteRandAtribute.containsKey(rowAttrComplexList.getRowNumber())){
                for( Component comp: mapComponenteRandAtribute.get(rowAttrComplexList.getRowNumber()).keySet() ){

                    if (TextField.class.isAssignableFrom(comp.getClass())) {
                        String valC = ((TextField)comp).getValue();
                        AttributeLink attr=  mapComponenteRandAtribute.get(rowAttrComplexList.getRowNumber()).get(comp);

                        if(valC.contains("(") && valC.endsWith(")")){
                            String valId=valC.substring(valC.lastIndexOf("(")+1, valC.lastIndexOf(")"));
                            attr.setValue(valId);


                        }else{

                            mapComponenteRandAtribute.get(rowAttrComplexList.getRowNumber()).get(comp).setValue(valC);
                        }
                        listaAttr.add( getBean(ConversionService.class).convert(attr, DocAttrLink.class));

                    }else if (DatePicker.class.isAssignableFrom(comp.getClass())){
                        AttributeLink attr=  mapComponenteRandAtribute.get(rowAttrComplexList.getRowNumber()).get(comp);
                        LocalDate valCDate = ((DatePicker)comp).getValue();

                        SimpleDateFormat sdf= new SimpleDateFormat("dd.mm.yyyy");
                        SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-mm-dd");
                        String valC= null;
                        if(valCDate!=null){
                            try {
                                valC = sdf.format(sdf2.parse(valCDate.toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        attr.setValue(valC);
                        listaAttr.add( getBean(ConversionService.class).convert(attr, DocAttrLink.class));
                    } else if (ComboBox.class.isAssignableFrom(comp.getClass()))
                    {
                        AttributeLink attr=  mapComponenteRandAtribute.get(rowAttrComplexList.getRowNumber()).get(comp);
                        String valC;
                        Lov lov = ((Lov) (((ComboBox)comp).getValue()));
                        if (attr.getSelectionType() == 3){
                            valC = (lov != null ? lov.getId() : null);
                        } else {
                            valC = (lov != null ? lov.getValoare() : null);
                        }
                        attr.setValue(valC);
                        listaAttr.add( getBean(ConversionService.class).convert(attr, DocAttrLink.class));

                    } else if (MultiselectComboBox.class.isAssignableFrom(comp.getClass()))
                    {
                        AttributeLink attr=  mapComponenteRandAtribute.get(rowAttrComplexList.getRowNumber()).get(comp);
                        HashSet<Lov> lov = new HashSet<>();
                        try {
                            lov = (HashSet) ((MultiselectComboBox) comp).getValue();
                        } catch (ClassCastException cce){
                        }

                        StringBuilder sb = new StringBuilder();
                        for (Lov l : lov){
                            if(l.getId() != null && !l.getId().isEmpty()){
                                sb.append(l.getId()).append(",");
                            }
                        }
                        String valC = sb.toString().isEmpty()?null:sb.deleteCharAt(sb.length()-1).toString();
                        attr.setValue(valC);
                        listaAttr.add( getBean(ConversionService.class).convert(attr, DocAttrLink.class));

                    } else if (Checkbox.class.isAssignableFrom(comp.getClass())){
                        AttributeLink attr=  mapComponenteRandAtribute.get(rowAttrComplexList.getRowNumber()).get(comp);
                        String valC = ((Checkbox)comp).getValue() != null && ((Checkbox)comp).getValue() ? "1" : "0";
                        attr.setValue(valC);
                        listaAttr.add( getBean(ConversionService.class).convert(attr, DocAttrLink.class));
                    }
                    else{
                        AttributeLink attr=  mapComponenteRandAtribute.get(rowAttrComplexList.getRowNumber()).get(comp);

                        listaAttr.add( getBean(ConversionService.class).convert(attr, DocAttrLink.class));

                    }
                }
            }else{
                //logger.info("SAVE ---------- nu a gasit in map componente; Se va salva din lista initiala primia:\t" + rowAttrComplexList.getRowNumber());
                for( DocAttrLink atr: rowAttrComplexList.getListaAtribute()){
                    listaAttr.add(atr);
                }
            }



            listaAtributeAtrComplexFinal.add(new RowAttrComplexList(rowAttrComplexList.getRowNumber(), listaAttr));

        }



        return listaAtributeAtrComplexFinal;
    }

    public void addLovListForAttributeLink(String smartFormId, AttributeLink attributeLink, LovList lovList) {
        lovAttributeLinkMap.get(smartFormId).put(Long.valueOf(attributeLink.getAttributeId()), lovList.getLov());
    }

    public void addAttrListForAttributeComplex(String smartFormId, AttributeLink attrComplex, List<RowAttrComplexList> listAttr) {
        attributeLinkMapComplex.get(smartFormId).put(Long.valueOf(attrComplex.getAttributeId()), listAttr);
    }
//    public void addSubjectValueComponent(Component component, Object value){
//        changedComponents.add(component);
//        ((HasValue)component).setValue(value);
//    }

    public void addSubjectValueComponent(Component component) {
        changedComponents.add(component);
        //13.07.2021 - NG - ANRE - setare true contor schimbari facute pentru a afisa dialog de confirmare la iesirea din pagina

        UI.getCurrent().getPage().executeJavaScript("setChanges();");
    }

    public void addSubjectValueAttributeLink(String smartFormId, AttributeLink attributeLink) {
        if (componentsFormMap.get(smartFormId).get(new Long(attributeLink.getAttributeId())) != null) {
            componentsFormMap.get(smartFormId)
                    .get(new Long(attributeLink.getAttributeId()))
                    .stream().forEach(component -> {
                addSubjectValueComponent(component);
                //Neata Georgiana # 23.06.2021 # ANRE # adaugare in map smartFormComponentsValues : atributl si valoarea
                String valoare = null;
                DocAttrLink docAttrLink = conversionService.convert(attributeLink, DocAttrLink.class);
                        if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
                            valoare = getValue(smartFormId, docAttrLink);

                        } else {
                            valoare = getSingleValue(component);


                        }
                        if (valoare == null || valoare.isEmpty()) {


                            valoare = attributeLink.getValue();
                        }
                smartFormComponentsValues.put(attributeLink, valoare);
                    }


            );
            //afisare judete by tara selectata
            if (attributeLink.getDataType().equals("TARA")) {
                smartFormComponents.keySet()
                        .stream()
                        .forEach(component ->
                        {
                            if (component.getDataType().equals("JUDET") && ((ComboBox<Lov>) smartFormComponents.get(attributeLink)).getValue() != null) {
                                component.setValueForLov(BeanUtil.getBean(PS4Service.class).getJudeteByIdTara(Integer.valueOf(((ComboBox<Lov>) smartFormComponents.get(attributeLink)).getValue().getId())).getLov());
                                ((ComboBox<Lov>) smartFormComponents.get(component)).setItems(component.getValueForLov());
                            }
                        });

            }
            //afisare localitati by judet selectat
            if (attributeLink.getDataType().equals("JUDET")) {
                smartFormComponents.keySet()
                        .stream()
                        .forEach(component ->
                        {
                            if (component.getDataType().equals("LOCALITATE") && ((ComboBox<Lov>) smartFormComponents.get(attributeLink)).getValue() != null) {
                                component.setValueForLov(BeanUtil.getBean(PS4Service.class).getLocalitatiByJudet(Integer.valueOf(((ComboBox<Lov>) smartFormComponents.get(attributeLink)).getValue().getId())).getLov());
                                ((ComboBox<Lov>) smartFormComponents.get(component)).setItems(component.getValueForLov());
                            }
                        });

            }

            if (attributeLink.getDataType().startsWith("TARA")) {
                smartFormComponents.keySet()
                        .stream()
                        .forEach(component ->
                        {
                            if (((ComboBox<Lov>) smartFormComponents.get(attributeLink)).getValue() != null && attributeLink.getDataType().split("TARA").length > 1 && component.getDataType().split("JUDET").length > 1
                                    && component.getDataType().startsWith("JUDET") && component.getDataType().split("JUDET")[1].equals(attributeLink.getDataType().split("TARA")[1])) {
                                component.setValueForLov(BeanUtil.getBean(PS4Service.class).getJudeteByIdTara(Integer.valueOf(((ComboBox<Lov>) smartFormComponents.get(attributeLink)).getValue().getId())).getLov());
                                ((ComboBox<Lov>) smartFormComponents.get(component)).setItems(component.getValueForLov());
                                //        ((ComboBox<Lov>)smartFormComponents.get(component)).setItems(BeanUtil.getBean(PS4Service.class).getJudeteByIdTara(Integer.valueOf(((ComboBox<Lov>)smartFormComponents.get(attributeLink)).getValue().getId())).getLov());

                            }
                        });


            }
            if (attributeLink.getDataType().startsWith("JUDET")) {
                smartFormComponents.keySet()
                        .stream()
                        .forEach(component ->
                        {
                            if (((ComboBox<Lov>) smartFormComponents.get(attributeLink)).getValue() != null && attributeLink.getDataType().split("JUDET").length > 1 && component.getDataType().split("LOCALITATE").length > 1
                                    && component.getDataType().startsWith("LOCALITATE") && component.getDataType().split("LOCALITATE")[1].equals(attributeLink.getDataType().split("JUDET")[1])) {
                                component.setValueForLov(BeanUtil.getBean(PS4Service.class).getLocalitatiByJudet(Integer.valueOf(((ComboBox<Lov>) smartFormComponents.get(attributeLink)).getValue().getId())).getLov());
                                ((ComboBox<Lov>) smartFormComponents.get(component)).setItems(component.getValueForLov());
                                //  ((ComboBox<Lov>)smartFormComponents.get(component)).setItems(BeanUtil.getBean(PS4Service.class).getLocalitatiByJudet(Integer.valueOf(((ComboBox<Lov>)smartFormComponents.get(attributeLink)).getValue().getId())).getLov());
                            }
                        });

            }


        }
    }


    private String getLovValue(List<Component> components, List<Lov> lovList) {
        StringBuilder loveValue = new StringBuilder();
        components
                .stream()
                .filter(c -> hasValueChanged(c))
                .forEach(c -> loveValue.append(getLovValue(c, lovList) + ","));
        return loveValue.length() == 0 ? null : loveValue.substring(0, loveValue.length() - 1);
    }

    public String getLovValue(Component component, List<Lov> lovList) {
        changedComponents.remove(component);
        Object value = ((HasValue) component).getValue();
        if (value != null) {
            if (Boolean.class.isAssignableFrom(value.getClass()) && Checkbox.class.isAssignableFrom(component.getClass())) {
                Checkbox checkbox = (Checkbox) component;
                if (checkbox.getValue()) {
                    Optional<Lov> lov = lovList.stream().filter(l -> l.getValoare().equalsIgnoreCase(checkbox.getLabel())).findFirst();
                    if (lov.isPresent()) {
                        return lov.get().getId();
                    }
                }
            }
            if (Lov.class.isAssignableFrom(value.getClass())) {
                return ((Lov) value).getId() + "";
            }
        }
        return "";
    }

    private String getSingleValue(Component component) {
        //if(hasValueChanged(component)) {
        changedComponents.remove(component);
        try {
            Object value = ((HasValue) component).getValue();
            if (value == null) {
                return "";
            }
            if (Lov.class.isAssignableFrom(value.getClass())) {
                return ((Lov) value).getId() + "";
            } else if (Number.class.isAssignableFrom(value.getClass())) {
                return value + "";
            } else if (Boolean.class.isAssignableFrom(value.getClass())) {
                return ((Boolean) value) ? "1" : "0";
            } else if (LocalDate.class.isAssignableFrom(value.getClass())) {
                return ((LocalDate) value).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } else {
            }
            return getBean(ConversionService.class).convert(value, String.class);
        } catch (Exception e) {

        }

        // }
        return null;
    }



    private boolean hasValueChanged(Component component) {
        return HasValue.class.isAssignableFrom(component.getClass()) && changedComponents.contains(component);
    }

    public Integer getMaxResponsiveSteps(String smartFormId) {
        return getSmartFormDocAttrLinkRowsConfiguration(smartFormId)
                .values().stream().sorted((l1, l2) -> l2.size() - l1.size()).findFirst().get().size();
    }

    public List<DocAttrLink> getSmartFormDocAttrLinkRowConfiguration(String smartFormId, Integer rowIndex) {
        return getSmartFormDocAttrLinkRowsConfiguration(smartFormId).get(rowIndex);
    }

    public Integer getDocAttrLinkRowConfiguration(String smartFormId, DocAttrLink docAttrLink) {
        Map<Integer, List<DocAttrLink>> attributeLinkRowMap = getSmartFormDocAttrLinkRowsConfiguration(smartFormId);
        return attributeLinkRowMap.keySet().stream()
                .filter(row -> attributeLinkRowMap.get(row).contains(docAttrLink))
                .findFirst().get();
    }

    public Integer getDocAttrLinkColumnConfiguration(String smartFormId, DocAttrLink docAttrLink) {
        Map<Integer, List<DocAttrLink>> attributeLinkRowMap = getSmartFormDocAttrLinkRowsConfiguration(smartFormId);
        List<DocAttrLink> attributeLinkColumnList = attributeLinkRowMap.keySet().stream()
                .filter(row -> attributeLinkRowMap.get(row).contains(docAttrLink))
                .findFirst()
                .map(row -> attributeLinkRowMap.get(row)).get();
        Integer column = 0;
        for (; column < attributeLinkColumnList.size(); ++column) {
            if (attributeLinkColumnList.get(column).equals(docAttrLink)) {
                return column;
            }
        }
        return column;
    }

    public Map<Long, DocAttrLink> getAttributeLinkMap(String smartFormId) {
        return attributeLinkMap.get(smartFormId);
    }

    public Label getAttributeLabel(String smartFormId, AttributeLink attributeLink) {
        Optional<Label> attributeLabel = Optional.ofNullable(formLabels.get(smartFormId)
                .get(Long.valueOf(attributeLink.getAttributeId())));
        if (!attributeLabel.isPresent()) {
            Label attrLabel = new Label();
            if (Optional.ofNullable(attributeLink.getLabel()).isPresent() && !attributeLink.getLabel().isEmpty()) {
                attrLabel.add(new Text(attributeLink.getLabel()));
            }
            attributeLabel = Optional.of(attrLabel);
            formLabels.get(smartFormId).put(Long.valueOf(attributeLink.getAttributeId()), attributeLabel.get());
        }
        return attributeLabel.get();
    }

    public Label registerAttributeComponents(String smartFormId, AttributeLink attributeLink, List<Component> components) {
//        components.stream() todo register changes for getValue
        componentsFormMap.get(smartFormId).put(Long.valueOf(attributeLink.getAttributeId()), components);
        return getAttributeLabel(smartFormId, attributeLink);
    }

    public void setLabelsClassNames(String smartFormId, String classNames) {
        formLabels.get(smartFormId).values().forEach(l -> l.addClassNames(classNames.split(" ")));
    }

    public void addAtributeLinkComponentMap(Component component, AttributeLink attributeLink) {
        smartFormComponents.put(attributeLink, component);
        if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
            if (attributeLink.getValueForLov() != null && ((HasValue) component).getValue() != null) {
                smartFormComponentsValues.put(attributeLink, getLovValue(component, attributeLink.getValueForLov()));
            }
        } else {
            smartFormComponentsValues.put(attributeLink, getSingleValue(component));

        }
    }

    // Neata Georgiana # 23.06.2021 # ANRE # functie care se apeleaza la click pe un lov (aduce lista de valori cu API nou care tine cont de lov uri dependente)


    public void addFocusOnCombobox(AttributeLink attributeLink, ComboBox<Lov> lovComboBox, SmartForm smartForm, Optional<Lov> selected) {
        if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {

            //daca face parte din lov-uri standard dependente , dar totusi nu e nicio valoare setata pentru ele le incarca (incarcare initiala)
            if (smartFormComponents.containsKey(attributeLink) && attributeLink.getValueForLov() == null) {
                LovList lovList = BeanUtil.getBean(PS4Service.class).getLovListWithDependecies(attributeLink, smartFormComponentsValues, null);
                if (lovList.getLov() != null ) {
                    List<Lov> backendLov = lovList.getLov();
                    lovComboBox.setItems(backendLov);
                    SmartFormSupport.addLovListForAttributeLink(((Component) smartForm).getId().get(), attributeLink, lovList);


                }
            } else if (!smartFormComponents.containsKey(attributeLink)) {
                LovList lovList = BeanUtil.getBean(PS4Service.class).getLovListWithDependecies(attributeLink, smartFormComponentsValues, null);
                if (lovList.getLov() != null ) {
                    List<Lov> backendLov = lovList.getLov();
                    lovComboBox.setItems(backendLov);
                    SmartFormSupport.addLovListForAttributeLink(((Component) smartForm).getId().get(), attributeLink, lovList);

                }
            } else if (smartFormComponents.containsKey(attributeLink) && attributeLink.getDataType() != null && attributeLink.getDataType().equals("JUDET")) {
                String COD_TARA = attributeLink.getDataType().replaceAll("JUDET","TARA");
                AttributeLink attributeLinkTara = attributeLink;
                for (AttributeLink attr : smartFormComponents.keySet()){
                    if (attr.getDataType() != null && attr.getDataType().equals(COD_TARA)){
                        attributeLinkTara = attr;
                        //adaugat break; altfel continua si ajungea sa seteze attributeLinkTara=null; chiar daca il gasea la un moment dat
                        break;
                    }else{
                        attributeLinkTara=null;
                    }
                }

                if (attributeLinkTara!=null){
                    LovList lovList = BeanUtil.getBean(PS4Service.class).getJudeteByIdTara(Integer.valueOf(((ComboBox<Lov>) smartFormComponents.get(attributeLinkTara)).getValue().getId()));
                    if (lovList.getLov() != null ) {
                        List<Lov> backendLov = lovList.getLov();
                        lovComboBox.setItems(backendLov);
                        SmartFormSupport.addLovListForAttributeLink(((Component) smartForm).getId().get(), attributeLink, lovList);

                    }
                }else{
                    LovList lovList = BeanUtil.getBean(PS4Service.class).getLovList(attributeLink);
                    if (lovList.getLov() != null ) {
                        List<Lov> backendLov = lovList.getLov();
                        lovComboBox.setItems(backendLov);
                        SmartFormSupport.addLovListForAttributeLink(((Component) smartForm).getId().get(), attributeLink, lovList);

                    }
                }

            } else if (smartFormComponents.containsKey(attributeLink) && attributeLink.getDataType() != null && attributeLink.getDataType().equals("LOCALITATE")) {
                String COD_JUDET = attributeLink.getDataType().replaceAll("LOCALITATE","JUDET");
                AttributeLink attributeLinkJudet = attributeLink;
                for (AttributeLink attr : smartFormComponents.keySet()){
                    if (attr.getDataType() != null && attr.getDataType().equals(COD_JUDET)){
                        attributeLinkJudet = attr;
                        break;
                    }
                }

                LovList lovList = BeanUtil.getBean(PS4Service.class).getLocalitatiByJudet(Integer.valueOf(((ComboBox<Lov>) smartFormComponents.get(attributeLinkJudet)).getValue().getId()));
                if (lovList.getLov() != null ) {
                    List<Lov> backendLov = lovList.getLov();
                    lovComboBox.setItems(backendLov);
                    SmartFormSupport.addLovListForAttributeLink(((Component) smartForm).getId().get(), attributeLink, lovList);

                }
            }


        }
    }


    // Neata Georgiana # 23.06.2021 # ANRE # functie care se apeleaza la click pe un lov (aduce lista de valori cu API nou care tine cont de lov uri dependente)


    public void addFocusOnComboboxOnComplex(AttributeLink attributeLink, ComboBox<Lov> lovComboBox, Integer nrRow, AttributeLink attrComplex,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0 && !smartFormComponents.containsKey(attributeLink)) {

            HashMap<Component, AttributeLink> randAttrComplex = mapComponenteRandAtribute.get(nrRow);
            //attributeLinkMapComplex.get(smartFormId).get(attrComplex.getAttributeId()).stream().filter(a -> a.getRowNumber().equals(nrRow)).findFirst();
            LovList lovList = new LovList();
            LovList lovListFinal = new LovList();
            List<Lov> listaLovFinal = new ArrayList<>();
            if (randAttrComplex != null) {
                Map<AttributeLink, String> mapAtr = new HashMap<>();
                for (Component c : randAttrComplex.keySet()) {

                    AttributeLink a = randAttrComplex.get(c);
                    String val = getValue(c, a);
                    if (!val.isEmpty()) {

                        mapAtr.put(a, val);
                    }else{
                        mapAtr.put(a, a.getValue());
                    }

                }
                lovList = BeanUtil.getBean(PS4Service.class).getLovListWithDependecies(attributeLink, mapAtr, attrComplex);
                List<String> valuesToRemove = new ArrayList<>();
                if (attributeLink.getCheckUniqueTert()) {

                    for (Integer nrRand : mapComponenteRandAtribute.keySet()) {
                        HashMap<Component, AttributeLink> randAttrComplex1 = mapComponenteRandAtribute.get(nrRand);
                        for (Component c : randAttrComplex1.keySet()) {

                            AttributeLink a = randAttrComplex1.get(c);
                            if (a.getIdDocumentSelectie().equals(attrComplex.getAttributeId()) && attributeLink.getLovId().equals(a.getLovId())) {
                                String val = getValue(c, a); //valoarea selectata deja
                                if (!val.isEmpty()) {
                                    valuesToRemove.add(val);

                                }
                            }

                        }

                    }
                    if (valuesToRemove.size() != 0) {

                        for (Lov lov : lovList.getLov()) {

                            if (lov.getId()!=null && !valuesToRemove.contains(lov.getId())) {
                                listaLovFinal.add(lov);
                            }
                        }
                        lovListFinal.setLov(listaLovFinal);

                    } else {
                        lovListFinal.setLov(lovList.getLov());
                    }

                } else {
                    lovListFinal = lovList;
                }
            }
            if (lovListFinal.getLov() != null) {
                List<Lov> backendLov = lovListFinal.getLov();
                lovComboBox.setItems(backendLov);

            }

        }
    }

    /**
     * Creaza un data provider pentru un combobox lov.
     */
    DataProvider<Lov, String> createLovDataProviderDynamic(AttributeLink attributeLink, ComboBox<Lov> lovComboBox, Integer nrRow, AttributeLink attrComplex,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        return DataProvider.fromFilteringCallbacks(query -> {
            // getFilter returns Optional<String>
            String filter = query.getFilter().orElse(null);

            // empty list - in caz ca nu am ce arata
            List<Lov> emptyLst = new ArrayList<Lov>();

            if (!(attributeLink.getLovId() != null && attributeLink.getLovId() != 0 && !smartFormComponents.containsKey(attributeLink))) {
                return emptyLst.stream();
            }

            HashMap<Component, AttributeLink> randAttrComplex = mapComponenteRandAtribute.get(nrRow);

            LovList lovList = new LovList();
            LovList lovListFinal = new LovList();
            List<Lov> listaLovFinal = new ArrayList<>();

            Map<AttributeLink, String> mapAtr = new HashMap<>();

            if (randAttrComplex != null) {
                for (Component c : randAttrComplex.keySet()) {
                    AttributeLink a = randAttrComplex.get(c);
                    String val = getValue(c, a);
                    if (!val.isEmpty()) {
                        mapAtr.put(a, val);
                    } else {
                        mapAtr.put(a, a.getValue());
                    }
                }
            }

            lovList = BeanUtil.getBean(PS4Service.class).getLovListWithDependeciesDynamic(attributeLink, mapAtr, attrComplex, query.getOffset(), query.getLimit(), filter);

            List<String> valuesToRemove = new ArrayList<>();

            if (attributeLink.getCheckUniqueTert()) {
                for (Integer nrRand : mapComponenteRandAtribute.keySet()) {
                    HashMap<Component, AttributeLink> randAttrComplex1 = mapComponenteRandAtribute.get(nrRand);
                    for (Component c : randAttrComplex1.keySet()) {

                        AttributeLink a = randAttrComplex1.get(c);
                        if (a.getIdDocumentSelectie().equals(attrComplex.getAttributeId()) && attributeLink.getLovId().equals(a.getLovId())) {
                            String val = getValue(c, a); //valoarea selectata deja
                            if (!val.isEmpty()) {
                                valuesToRemove.add(val);
                            }
                        }
                    }
                }

                if (valuesToRemove.size() != 0) {
                    for (Lov lov : lovList.getLov()) {
                        if (lov.getId()!=null && !valuesToRemove.contains(lov.getId())) {
                            listaLovFinal.add(lov);
                        }
                    }
                    lovListFinal.setLov(listaLovFinal);
                } else {
                    lovListFinal.setLov(lovList.getLov());
                }
            } else {
                lovListFinal = lovList;
            }

            if (lovListFinal.getLov() == null) {
                return emptyLst.stream();
            }

            List<Lov> backendLov = lovListFinal.getLov();
            return backendLov.stream();
        }, query -> {
            // getFilter returns Optional<String>
            String filter = query.getFilter().orElse(null);

            if (!(attributeLink.getLovId() != null && attributeLink.getLovId() != 0 && !smartFormComponents.containsKey(attributeLink))) {
                return 0;
            }

            HashMap<Component, AttributeLink> randAttrComplex = mapComponenteRandAtribute.get(nrRow);

            LovList lovList = new LovList();
            LovList lovListFinal = new LovList();
            List<Lov> listaLovFinal = new ArrayList<>();

            Map<AttributeLink, String> mapAtr = new HashMap<>();
            if (randAttrComplex != null) {
                for (Component c : randAttrComplex.keySet()) {
                    AttributeLink a = randAttrComplex.get(c);
                    String val = getValue(c, a);
                    if (!val.isEmpty()) {
                        mapAtr.put(a, val);
                    } else {
                        mapAtr.put(a, a.getValue());
                    }
                }
            }

            lovList = BeanUtil.getBean(PS4Service.class).getLovListWithDependeciesDynamic(attributeLink, mapAtr, attrComplex, query.getOffset(), query.getLimit(), filter);

            List<String> valuesToRemove = new ArrayList<>();

            if (attributeLink.getCheckUniqueTert()) {
                for (Integer nrRand : mapComponenteRandAtribute.keySet()) {
                    HashMap<Component, AttributeLink> randAttrComplex1 = mapComponenteRandAtribute.get(nrRand);
                    for (Component c : randAttrComplex1.keySet()) {

                        AttributeLink a = randAttrComplex1.get(c);
                        if (a.getIdDocumentSelectie().equals(attrComplex.getAttributeId()) && attributeLink.getLovId().equals(a.getLovId())) {
                            String val = getValue(c, a); //valoarea selectata deja
                            if (!val.isEmpty()) {
                                valuesToRemove.add(val);
                            }
                        }
                    }
                }

                if (valuesToRemove.size() != 0) {
                    for (Lov lov : lovList.getLov()) {
                        if (lov.getId()!=null && !valuesToRemove.contains(lov.getId())) {
                            listaLovFinal.add(lov);
                        }
                    }
                    lovListFinal.setLov(listaLovFinal);
                } else {
                    lovListFinal.setLov(lovList.getLov());
                }
            } else {
                lovListFinal = lovList;
            }

            if (lovListFinal.getLov() == null) {
                return 0;
            }

            List<Lov> backendLov = lovListFinal.getLov();
            return backendLov.size();
        });
    }

    /**
     * Creaza un data provider pentru un combobox lov.
     */
    ListDataProvider<Lov> createLovDataProviderList(AttributeLink attributeLink, ComboBox<Lov> lovComboBox, Integer nrRow, AttributeLink attrComplex,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        // lista goala in caz ca nu are ce intoarce
        List<Lov> emptyList = new ArrayList<Lov>();

        if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0 && !smartFormComponents.containsKey(attributeLink)) {
            HashMap<Component, AttributeLink> randAttrComplex = mapComponenteRandAtribute.get(nrRow);
            //attributeLinkMapComplex.get(smartFormId).get(attrComplex.getAttributeId()).stream().filter(a -> a.getRowNumber().equals(nrRow)).findFirst();
            LovList lovList = new LovList();
            LovList lovListFinal = new LovList();
            List<Lov> listaLovFinal = new ArrayList<>();
            Map<AttributeLink, String> mapAtr = new HashMap<>();

            if (randAttrComplex != null) {
                for (Component c : randAttrComplex.keySet()) {

                    AttributeLink a = randAttrComplex.get(c);
                    String val = getValue(c, a);
                    if (!val.isEmpty()) {

                        mapAtr.put(a, val);
                    }else{
                        mapAtr.put(a, a.getValue());
                    }

                }
            }

            lovList = BeanUtil.getBean(PS4Service.class).getLovListWithDependecies(attributeLink, mapAtr, attrComplex);
            List<String> valuesToRemove = new ArrayList<>();

            if (attributeLink.getCheckUniqueTert()) {
                for (Integer nrRand : mapComponenteRandAtribute.keySet()) {
                    HashMap<Component, AttributeLink> randAttrComplex1 = mapComponenteRandAtribute.get(nrRand);
                    for (Component c : randAttrComplex1.keySet()) {

                        AttributeLink a = randAttrComplex1.get(c);
                        if (a.getIdDocumentSelectie().equals(attrComplex.getAttributeId()) && attributeLink.getLovId().equals(a.getLovId())) {
                            String val = getValue(c, a); //valoarea selectata deja
                            if (!val.isEmpty()) {
                                valuesToRemove.add(val);

                            }
                        }
                    }
                }

                if (valuesToRemove.size() != 0) {

                    for (Lov lov : lovList.getLov()) {

                        if (lov.getId()!=null && !valuesToRemove.contains(lov.getId())) {
                            listaLovFinal.add(lov);
                        }
                    }
                    lovListFinal.setLov(listaLovFinal);

                } else {
                    lovListFinal.setLov(lovList.getLov());
                }
            } else {
                lovListFinal = lovList;
            }

            if (lovListFinal.getLov() != null) {
                List<Lov> backendLov = lovListFinal.getLov();
                return DataProvider.fromStream(backendLov.stream());
            }
        }

        return DataProvider.fromStream(emptyList.stream());
    }


    /**
     * Creaza un data provider pentru un combobox lov.
     */
    ListDataProvider<Lov> createLovMultiDataProviderList(AttributeLink attributeLink, MultiselectComboBox<Lov> lovComboBox, Integer nrRow, AttributeLink attrComplex,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        // lista goala in caz ca nu are ce intoarce
        List<Lov> emptyList = new ArrayList<Lov>();

        if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0 && !smartFormComponents.containsKey(attributeLink)) {
            HashMap<Component, AttributeLink> randAttrComplex = mapComponenteRandAtribute.get(nrRow);
            //attributeLinkMapComplex.get(smartFormId).get(attrComplex.getAttributeId()).stream().filter(a -> a.getRowNumber().equals(nrRow)).findFirst();
            LovList lovList = new LovList();
            LovList lovListFinal = new LovList();
            List<Lov> listaLovFinal = new ArrayList<>();
            Map<AttributeLink, String> mapAtr = new HashMap<>();

            if (randAttrComplex != null) {
                for (Component c : randAttrComplex.keySet()) {

                    AttributeLink a = randAttrComplex.get(c);
                    String val = getValue(c, a);
                    if (!val.isEmpty()) {

                        mapAtr.put(a, val);
                    }else{
                        mapAtr.put(a, a.getValue());
                    }

                }
            }

            lovList = BeanUtil.getBean(PS4Service.class).getLovListWithDependecies(attributeLink, mapAtr, attrComplex);
            List<String> valuesToRemove = new ArrayList<>();

            if (attributeLink.getCheckUniqueTert()) {
                for (Integer nrRand : mapComponenteRandAtribute.keySet()) {
                    HashMap<Component, AttributeLink> randAttrComplex1 = mapComponenteRandAtribute.get(nrRand);
                    for (Component c : randAttrComplex1.keySet()) {

                        AttributeLink a = randAttrComplex1.get(c);
                        if (a.getIdDocumentSelectie().equals(attrComplex.getAttributeId()) && attributeLink.getLovId().equals(a.getLovId())) {
                            String val = getValue(c, a); //valoarea selectata deja
                            if (!val.isEmpty()) {
                                valuesToRemove.add(val);

                            }
                        }
                    }
                }

                if (valuesToRemove.size() != 0) {

                    for (Lov lov : lovList.getLov()) {

                        if (lov.getId()!=null && !valuesToRemove.contains(lov.getId())) {
                            listaLovFinal.add(lov);
                        }
                    }
                    lovListFinal.setLov(listaLovFinal);

                } else {
                    lovListFinal.setLov(lovList.getLov());
                }
            } else {
                lovListFinal = lovList;
            }

            if (lovListFinal.getLov() != null) {
                List<Lov> backendLov = lovListFinal.getLov();
                return DataProvider.fromStream(backendLov.stream());
            }
        }

        return DataProvider.fromStream(emptyList.stream());
    }


    private String getValue(Component component, AttributeLink attributeLink) {

        //TODO complete
        String value = "";
        AttributeLinkDataType atrDataType = getAttributeLinkDataType(attributeLink);


        try {

            switch (atrDataType) {
                case DATA:
                    return ((DatePicker) component).getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

                case BOOLEAN:
                    return String.valueOf(((Checkbox) component).getValue()).equals("true") ? "1" : "0";
                case OPT_SELECT_DROPBOX:
                    return ((Lov) ((HasValue) component).getValue()).getId() + "";

                default: {
                    TextField textFieldComponent = ((TextField) component);
                    String valueTf = textFieldComponent.getValue();

                    if (valueTf != null) {
                        if (textFieldComponent.getClassNames().contains("thousands")) {
                            valueTf = valueTf.replace(",","");
                        }
                    }
                    return valueTf;
                }
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static AttributeLinkDataType getAttributeLinkDataType(AttributeLink attributeLink) {
        Optional<String> dataTypeStr = Optional.ofNullable(attributeLink.getDataType());
        if (dataTypeStr.isPresent() && !dataTypeStr.get().isEmpty()
                && Stream.of(AttributeLinkDataType.values()).filter(e -> e.name().equals(dataTypeStr.get())).count() == 1) {
            if (Optional.ofNullable(attributeLink.getLovId()).isPresent() && attributeLink.getLovId().compareTo(0) != 0) {
                if (attributeLink.getNrSelectiiLov().compareTo(1) == 0) {
                    if (AttributeLinkDataType.valueOf(dataTypeStr.get()).equals(AttributeLinkDataType.OPT_GROUP)) {
                        return AttributeLinkDataType.OPT_SELECT;
                    } else {
                        return AttributeLinkDataType.OPT_SELECT_DROPBOX;
                    }
                } else {
                    if (AttributeLinkDataType.valueOf(dataTypeStr.get()).equals(AttributeLinkDataType.OPT_GROUP)) {
                        return AttributeLinkDataType.OPT_SELECT_MULTIVALUE;
                    } else {
                        return AttributeLinkDataType.OPT_SELECT_DROPBOX_MULTIVALUE;
                    }
                }
            } else if (Optional.ofNullable(attributeLink.getDenumireTipSelectie()).isPresent() && attributeLink.getDenumireTipSelectie().toUpperCase().equals("COMPLEX")) {
                return AttributeLinkDataType.COMPLEX;
            } else {
                try {
                    AttributeLinkDataType attributeLinkDataType = AttributeLinkDataType.valueOf(dataTypeStr.get());
                    return attributeLinkDataType;
                } catch (Throwable e) {
                    // logger.error("nu exista tipul:\t" + dataTypeStr.get());
                }
            }
        }
        return AttributeLinkDataType.TEXT;
    }

    public Map<AttributeLink, String> getSmartFormComponentsValues() {
        return smartFormComponentsValues;
    }

    public Map<AttributeLink, Component> getSmartFormComponents() {
        return smartFormComponents;
    }

    public void setSmartFormComponentsValues(Map<AttributeLink, String> smartFormComponentsValues) {
        this.smartFormComponentsValues = smartFormComponentsValues;
    }

    public void aplicareFormulaCalculPortal(Integer nrRow, String smartFormId, HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        HashMap<Component, AttributeLink> randAttrComplex = mapComponenteRandAtribute.get(nrRow);
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (randAttrComplex != null) {
            Map<String, String> mapAtr = new HashMap<>();
            //calcul map atribut->valoare
            for (Component c : randAttrComplex.keySet()) {

                AttributeLink a = randAttrComplex.get(c);
                String val = getValue(c, a);
                if (!val.isEmpty()) {

                    mapAtr.put(a.getName(), val);
                } else {
                    mapAtr.put(a.getName(), "0");
                }

            }
            //check daca are formula-> calculeaza
            List<String> todoReadonly = new ArrayList<>();
            List<String> todoMandatory = new ArrayList<>();
            List<String> toRemoveReadonly = new ArrayList<>();
//            List<String> toRemoveMandatory = new ArrayList<>();
            for (Component c : randAttrComplex.keySet()) {

                AttributeLink a = randAttrComplex.get(c);
                if (a.getFormulaCalculPortal() != null && !a.getFormulaCalculPortal().isEmpty()) {
                    //NG 18.08.2021 Daca formula calcul portal contine [READONLY] -> se vor face readonly atributele al caror cod este notat intre () in cazul in care atributul curent este completat.
                    if (a.getFormulaCalculPortal().contains("[READONLY]")) {
                        Matcher matches = Pattern.compile("\\((.*?)\\)").matcher(a.getFormulaCalculPortal());
                        while (matches.find()) {
                            String codAtr = matches.group(1);
                            if (mapAtr.get(a.getName()) != null && !mapAtr.get(a.getName()).equals("0")) {
                                todoReadonly.add(codAtr);

                            } else {
                                toRemoveReadonly.add(codAtr);

                            }

                        }

                    } else      if (a.getFormulaCalculPortal().contains("[MANDATORY]")) {
                        Matcher matches = Pattern.compile("\\((.*?)\\)").matcher(a.getFormulaCalculPortal());
                        while (matches.find()) {
                            String codAtr = matches.group(1);
                            if (mapAtr.get(a.getName()) != null) {
                                if( a.getDataType().equals("BOOLEAN")){

                                    if (mapAtr.get(a.getName()).equals("0")) {
                                        todoMandatory.add(codAtr);
                                    } else {
                                        toRemoveMandatory.add(codAtr);

                                    }
                                } else{
                                    if (!mapAtr.get(a.getName()).equals("0")) {
                                        todoMandatory.add(codAtr);
                                    } else {
                                        toRemoveMandatory.add(codAtr);

                                    }
                                }

                            }

                        }

                    }
                    //altfel se parseaza formula calcul portal in mod obisnuit
                    else {
                        try {
                            Double formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtr, a.getFormulaCalculPortal());
                            String formulaCalculStr= formulaCalcul.toString();
                            if(a.getPrecision()!=null){
                                formulaCalculStr = BigDecimal.valueOf(formulaCalcul)
                                        .setScale(a.getPrecision(), RoundingMode.HALF_UP).toPlainString();
                            }
                            a.setValue(formulaCalculStr);
                            randAttrComplex.put(c, a);
                            ((TextField) c).setValue(formulaCalculStr);


                        } catch (Exception e) {

                        }
                    }


                }
            }
            //se parcurg listele de todoReadonly , respectiv toRemoveReadonly
            if (todoReadonly.size() != 0 || toRemoveReadonly.size() != 0) {
                for (Component c : randAttrComplex.keySet()) {
                    if (todoReadonly.contains(randAttrComplex.get(c).getName())) {
                        c.getElement().setAttribute("readonly", "readonly");
                    } else if (toRemoveReadonly.contains(randAttrComplex.get(c).getName())) {
                        c.getElement().removeAttribute("readonly");

                    }
                }
            }

            //se parcurg listele de todoMandatory , respectiv toRemoveMandatory
            if (todoMandatory.size() != 0 || toRemoveMandatory.size() != 0) {

                for (Component c : randAttrComplex.keySet()) {
                    if (
                            todoMandatory.contains(randAttrComplex.get(c).getName()) ||
                            toRemoveMandatory.contains(randAttrComplex.get(c).getName())

                            ) {


                        if (TextField.class.isAssignableFrom(c.getClass())) {

                            TextField textF = (TextField) c;
                            String currentVal= textF.getValue();
                            randAttrComplex.get(c).setValue(currentVal);
                            StringAttributeBinderBean binderBean = new StringAttributeBinderBean(smartFormId, (randAttrComplex.get(c)));

                            if (todoMandatory.contains(randAttrComplex.get(c).getName())) {
                                randAttrComplex.get(c).setMandatory(true);

                                try{
                                    SmartFormSupport.bind(smartFormId,
                                            binderBean, textF,
                                            new MandatoryAttributeBeanPropertyValidator(""));
                                }catch (Exception e){
                                    logger.trace("BIND FAILED for "+ randAttrComplex.get(c).getName() + " on row "+ nrRow );

                                }
                                //la aplicare Binder se repune valoarea pe care o gaseste pe atribut (ID fisier)
                                //utilizatorul trebuie sa vada si numele => aplicam valoarea care se afla pe textfiled
                                if(!currentVal.trim().isEmpty()){
                                    if(randAttrComplex.get(c).getDataType().equals("FISIER")){
                                        if (currentVal.contains("(") && currentVal.endsWith(")")){
                                            textF.setValue(currentVal);
                                        }else{
                                            textF.setValue("");
                                        }
                                    }else {
                                        textF.setValue(currentVal);
                                    }
                                }


                            } else if (toRemoveMandatory.contains(randAttrComplex.get(c).getName())) {
                                randAttrComplex.get(c).setMandatory(false);
                                try{
                                    SmartFormSupport.unbind(smartFormId,
                                            binderBean, textF,
                                            new MandatoryAttributeBeanPropertyValidator(""));
                                }catch (Exception e){

                                }
                                ((TextField) c).removeClassName("vaadin-invalid-input");
                                ((TextField) c).getElement().removeAttribute("invalid");

                                //la aplicare Binder se repune valoarea pe care o gaseste pe atribut (ID fisier)
                                //utilizatorul trebuie sa vada si numele => aplicam valoarea care se afla pe textfiled
                                if(!currentVal.trim().isEmpty()){
                                    if(randAttrComplex.get(c).getDataType().equals("FISIER")){
                                        if (currentVal.contains("(") && currentVal.endsWith(")")){
                                            textF.setValue(currentVal);
                                        }else{
                                            textF.setValue("");
                                        }
                                    }else {
                                        textF.setValue(currentVal);
                                    }
                                }
                            }
                        }

                        else if (DatePicker.class.isAssignableFrom(c.getClass())) {

                            DatePicker textF = (DatePicker) c;
                            String currentVal= textF.getValue()!=null ?textF.getValue().format(formatter):null;

                            randAttrComplex.get(c).setValue(currentVal);
                            LocalDateAttributeBinderBean localDateAttributeBinderBean = new LocalDateAttributeBinderBean(smartFormId, (randAttrComplex.get(c)));

                            if (todoMandatory.contains(randAttrComplex.get(c).getName())) {

                                randAttrComplex.get(c).setMandatory(true);

                                try{

                                    SmartFormSupport.bindWithRow(smartFormId,
                                            localDateAttributeBinderBean, textF,nrRow,
                                            new DateRangeValidator("", LocalDate.MIN, LocalDate.MAX));
                                }catch (Exception e){
                                    logger.trace("BIND FAILED for "+ randAttrComplex.get(c).getName() + " on row "+ nrRow );

                                }


                            } else if (toRemoveMandatory.contains(randAttrComplex.get(c).getName())) {
                                randAttrComplex.get(c).setMandatory(false);

                                    try{
                                        SmartFormSupport.unbind(smartFormId,
                                                localDateAttributeBinderBean, textF,
                                                new DateRangeValidator("", LocalDate.MIN, LocalDate.MAX));

                                    }catch (Exception e){

                                    }
                                    ((DatePicker) c).removeClassName("vaadin-invalid-input");
                                ((DatePicker) c).getElement().removeAttribute("invalid");


                                ((DatePicker) c).removeClassName("vaadin-invalid-input");


                            }
                        }


                    }


                }
                //reparcurgere lista pentru aplicare modificari
                for (Component c : randAttrComplex.keySet()) {


                    if (todoMandatory.contains(randAttrComplex.get(c).getName()) ||
                            toRemoveMandatory.contains(randAttrComplex.get(c).getName())) {

                        if (TextField.class.isAssignableFrom(c.getClass())) {

                            TextField textF = (TextField) c;
                            String currentVal= textF.getValue();
                            randAttrComplex.get(c).setValue(currentVal);
                            StringAttributeBinderBean binderBean = new StringAttributeBinderBean(smartFormId, (randAttrComplex.get(c)));

                            if (todoMandatory.contains(randAttrComplex.get(c).getName())) {
                                randAttrComplex.get(c).setMandatory(true);

                                try{
                                    SmartFormSupport.bind(smartFormId,
                                            binderBean, textF,
                                            new MandatoryAttributeBeanPropertyValidator(""));
                                }catch (Exception e){
                                    logger.trace("BIND FAILED for "+ randAttrComplex.get(c).getName() + " on row "+ nrRow );

                                }
                                //la aplicare Binder se repune valoarea pe care o gaseste pe atribut (ID fisier)
                                //utilizatorul trebuie sa vada si numele => aplicam valoarea care se afla pe textfiled
                                if(!currentVal.trim().isEmpty()){
                                    if(randAttrComplex.get(c).getDataType().equals("FISIER")){
                                        if (currentVal.contains("(") && currentVal.endsWith(")")){
                                            textF.setValue(currentVal);
                                        }else{
                                            textF.setValue("");
                                        }
                                    }else {
                                        textF.setValue(currentVal);
                                    }
                                }


                            } else if (toRemoveMandatory.contains(randAttrComplex.get(c).getName())) {
                                randAttrComplex.get(c).setMandatory(false);

                                try{
                                    SmartFormSupport.unbind(smartFormId,
                                            binderBean, textF,
                                            new MandatoryAttributeBeanPropertyValidator(""));
                                }catch (Exception e){

                                }
                                ((TextField) c).removeClassName("vaadin-invalid-input");
                                ((TextField) c).getElement().removeAttribute("invalid");

                                //la aplicare Binder se repune valoarea pe care o gaseste pe atribut (ID fisier)
                                //utilizatorul trebuie sa vada si numele => aplicam valoarea care se afla pe textfiled
                                if(!currentVal.trim().isEmpty()){
                                    if(randAttrComplex.get(c).getDataType().equals("FISIER")){
                                        if (currentVal.contains("(") && currentVal.endsWith(")")){
                                            textF.setValue(currentVal);
                                        }else{
                                            textF.setValue("");
                                        }
                                    }else {
                                        textF.setValue(currentVal);
                                    }
                                }
                            }
                        }
                        else if (DatePicker.class.isAssignableFrom(c.getClass())) {

                            DatePicker textF = (DatePicker) c;
                            String currentVal= textF.getValue()!=null?textF.getValue().format(formatter):null;
                            randAttrComplex.get(c).setValue(currentVal);
                            LocalDateAttributeBinderBean localDateAttributeBinderBean = new LocalDateAttributeBinderBean(smartFormId, (randAttrComplex.get(c)));

                            if (todoMandatory.contains(randAttrComplex.get(c).getName())) {

                                randAttrComplex.get(c).setMandatory(true);

                                try{
                                    SmartFormSupport.bindWithRow(smartFormId,
                                            localDateAttributeBinderBean, textF,nrRow,
                                            new DateRangeValidator("", LocalDate.MIN, LocalDate.MAX));
                                }catch (Exception e){

                                    logger.trace("BIND FAILED for "+ randAttrComplex.get(c).getName() + " on row "+ nrRow );
                                }
                                //la aplicare Binder se repune valoarea pe care o gaseste pe atribut (ID fisier)
                                //utilizatorul trebuie sa vada si numele => aplicam valoarea care se afla pe textfiled
                            if(currentVal!=null &&  !currentVal.trim().isEmpty()){

                                textF.setValue( LocalDate.parse(currentVal, formatter));

                            }


                            } else if (toRemoveMandatory.contains(randAttrComplex.get(c).getName())) {
                                randAttrComplex.get(c).setMandatory(false);

                                    try{
                                        SmartFormSupport.unbind(smartFormId,
                                                localDateAttributeBinderBean, textF,
                                                new DateRangeValidator("", LocalDate.MIN, LocalDate.MAX));

                                    }catch (Exception e){

                                    }
                                    ((DatePicker) c).removeClassName("vaadin-invalid-input");
                                    ((DatePicker) c).getElement().removeAttribute("invalid");


                            }
                        }
                    }
                }
                //END reparcurgere lsita pentru aplicare modificari

            }
            mapComponenteRandAtribute.put(nrRow, randAttrComplex);

        }

    }

    public void aplicareFormulaCalculColoanaPortal(Integer nrRow, AttributeLink attributeLink,LovList lovList,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        HashMap<Component, AttributeLink> randParinte = mapComponenteRandAtribute.get(nrRow);
        String formulaCalculStr=lovList.getLov().get(nrRow-1).getFormulaCalcul();
        if (randParinte != null) {
            Map<String, String> mapAtr = new HashMap<>();
            //calcul map atribut->valoare
            for (int i = 1; i <= mapComponenteRandAtribute.size(); i++) {
                String c = ((TextField) (mapComponenteRandAtribute.get(i).entrySet().stream().filter(componentAttributeLinkEntry -> componentAttributeLinkEntry.getValue().getName().equals(attributeLink.getName())).findFirst().get().getKey())).getValue();
                if(c.isEmpty()){
                    c="0";
                }

                mapAtr.put(String.valueOf(i), c);


            }
            //check daca are formula-> calculeaza
            if(formulaCalculStr!=null&&!formulaCalculStr.isEmpty()){
                DecimalFormat df2 = new DecimalFormat("#.##");
                Double formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtr, formulaCalculStr);

                attributeLink.setValue(df2.format(formulaCalcul).replaceAll(",","."));
                Component c = randParinte.entrySet().stream().filter(componentAttributeLinkEntry -> componentAttributeLinkEntry.getValue().getName().equals(attributeLink.getName())).findFirst().get().getKey();
                ((TextField) c).setReadOnly(true);
                randParinte.put(c, attributeLink);
                ((TextField) c).setValue(df2.format(formulaCalcul).replaceAll(",","."));
            }

            mapComponenteRandAtribute.put(nrRow, randParinte);

        }
    }
    public void addSmartFormComponentsValues(AttributeLink attributeLink,String value){
        smartFormComponentsValues.put(attributeLink,value);
    }

    public Map<String, Map<Long, List<RowAttrComplexList>>> getAttributeLinkMapComplex() {
        return attributeLinkMapComplex;
    }

    public Map<String, Map<String, BeanValidationBinder<AttributeBinderBean>>> getAttributeBinderMap() {
        return attributeBinderMap;
    }

    public void setMapComponenteRandAtribute(HashMap<Integer, HashMap<Component, AttributeLink>> mapComponenteRandAtribute) {
        this.mapComponenteRandAtribute = mapComponenteRandAtribute;
    }
}
