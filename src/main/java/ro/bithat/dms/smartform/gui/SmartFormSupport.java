package ro.bithat.dms.smartform.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.gatanaso.MultiselectComboBox;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.file.RowAttrComplexList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.Lov;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.attribute.AttributeLinkService;
import ro.bithat.dms.smartform.gui.attribute.binder.AttributeBinderBean;

import java.util.*;
import java.util.stream.Stream;

public final class SmartFormSupport {


    private final static Logger logger = LoggerFactory.getLogger(SmartFormSupport.class);

    private static Boolean isReadonly;
    public static SmartFormComponentService getSmartFormComponentService() {
        return BeanUtil.getBean(SmartFormComponentService.class);
    }

    public static AttributeLinkService getAttributeLinkService() {
        return BeanUtil.getBean(AttributeLinkService.class);
    }

    public static DmswsPS4Service getDmswsPS4Service() {
        return BeanUtil.getBean(DmswsPS4Service.class);
    }

    public static void registerSmartFormById(Component smartForm, AttributeLinkList attributeLinkList) {
        if(!smartForm.getId().isPresent()) {
            smartForm.setId(UUID.randomUUID().toString());
        }
        getSmartFormComponentService().registerSmartForm(smartForm.getId().get(), smartForm, attributeLinkList);
    }

    public static Map<Long, DocAttrLink> getAttributeLinkMap(String smartFormId) {
        return getSmartFormComponentService().getAttributeLinkMap(smartFormId);
    }

    public static DocAttrLinkList getInitialDocAttrLinkList(String smartFormId) {
        return getSmartFormComponentService().getInitialDocAttrLinkList(smartFormId);
    }

    public static String getValue(String smartFormId, DocAttrLink initialSet) {
        return getSmartFormComponentService().getValue(smartFormId, initialSet);
    }
    public static String getImage(String smartFormId, DocAttrLink initialSet) {
        return getSmartFormComponentService().getImage(smartFormId, initialSet);
    }
    public static String getValueLov(String smartFormId, DocAttrLink initialSet) {
        return getSmartFormComponentService().getValueLov(smartFormId, initialSet);
    }
    public static String getValueLovOptGroup(String smartFormId, DocAttrLink initialSet) {
        return getSmartFormComponentService().getValueLovOpt(smartFormId, initialSet);
    }
    public  List<RowAttrComplexList> getValueAtrComplex(String smartFormId, DocAttrLink attrComplex) {
        return getSmartFormComponentService().getValueAtrComplexFisier(smartFormId, attrComplex);
    }
    public  List<RowAttrComplexList> getValueAtrComplexFisier(String smartFormId, DocAttrLink attrComplex) {
        return getSmartFormComponentService().getValueAtrComplexFisier(smartFormId, attrComplex);
    }
//    public static void addSubjectValueComponent(Component component, Object value) {
//        getSmartFormComponentService().addSubjectValueComponent(component, value);
//    }

    public static void addSubjectValueComponent(Component component) {
        getSmartFormComponentService().addSubjectValueComponent(component);
    }

    public static void addSubjectValueAttributeLink(String smartFormId, AttributeLink attributeLink) {
        getSmartFormComponentService().addSubjectValueAttributeLink(smartFormId, attributeLink);
    }

    public static void addAtributeLinkComponentMap(Component component, AttributeLink attributeLink) {
        getSmartFormComponentService().addAtributeLinkComponentMap(component, attributeLink);
    }


//    public static void addSubjectValueAttributeLink(String smartFormId, AttributeLink attributeLink, Object value) {
//        getSmartFormComponentService().addSubjectValueAttributeLink(smartFormId, attributeLink, value);
//    }

    public static void setReadOnly(String smartFormId, Boolean readOnly) {
        getSmartFormComponentService().setReadOnly(smartFormId, readOnly);
    }

    public static DocAttrLinkList getSmartFormBinding(String smartFormId, List<DocAttrLink> list) {
        getSmartFormComponentService().setSmartFromBindingDocAttrLinkList(smartFormId, list);
        return getSmartFormComponentService().getSmartFromBinding(smartFormId);
    }

    public static Integer getMaxResponsiveSteps(String smartFormId) {
        return getSmartFormComponentService().getMaxResponsiveSteps(smartFormId);
    }

    public static Integer getRowResponsiveSteps(String smartFormId, Integer row) {
        return getSmartFormComponentService().getSmartFormDocAttrLinkRowConfiguration(smartFormId, row).size();
    }

    public static AttributeLinkDataType getAttributeLinkDataType(AttributeLink attributeLink) {
        Optional<String> dataTypeStr = Optional.ofNullable(attributeLink.getDataType());
        if(dataTypeStr.isPresent() && !dataTypeStr.get().isEmpty()
                && Stream.of(AttributeLinkDataType.values()).filter(e -> e.name().equals(dataTypeStr.get())).count()==1) {
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
            }
            else {
                try {
                    AttributeLinkDataType attributeLinkDataType = AttributeLinkDataType.valueOf(dataTypeStr.get());
                    return attributeLinkDataType;
                }catch (Throwable e) {
                  logger.error("nu exista tipul:\t" + dataTypeStr.get());
                }
            }
        }else if(dataTypeStr.isPresent() && !dataTypeStr.get().isEmpty() && (attributeLink.getDataType().startsWith("LOCALITATE")||attributeLink.getDataType().startsWith("JUDET")||attributeLink.getDataType().startsWith("TARA"))) {
            return AttributeLinkDataType.OPT_SELECT_DROPBOX;
        }
        return AttributeLinkDataType.TEXT;
    }


    public static void makeSmartFormPrintable(SmartForm smartForm) {
        getSmartFormComponentService().makeSmartFormPrintable(smartForm);
    }

    public static void exitPrintingMode(SmartForm smartForm) {
        getSmartFormComponentService().exitPrintingMode(smartForm);
    }

    public static void bind(String smartFormId, AttributeBinderBean attributeBinderBean, HasValue attributeField, Validator... validators) {
        getSmartFormComponentService().bind(smartFormId, attributeBinderBean, attributeField, validators);
    }
    public static void bindWithRow(String smartFormId, AttributeBinderBean attributeBinderBean, HasValue attributeField, Integer rowIndex,Validator... validators) {
        getSmartFormComponentService().bindWithRow(smartFormId, attributeBinderBean, attributeField,rowIndex, validators);
    }

    public static void unbind(String smartFormId, AttributeBinderBean attributeBinderBean, HasValue attributeField, Validator... validators) {
        getSmartFormComponentService().unbind(smartFormId, attributeBinderBean, attributeField, validators);
    }
    public static void bindConverter(String smartFormId, AttributeBinderBean attributeBinderBean, HasValue attributeField, Converter... convertors) {
        getSmartFormComponentService().bindConverter(smartFormId, attributeBinderBean, attributeField, convertors);
    }

    public static boolean validate(String smartFormId) {
        return getSmartFormComponentService().validate(smartFormId);
    }

    public static boolean validate(String smartFormId, AttributeLink attributeLink) {
        return getSmartFormComponentService().validate(smartFormId, attributeLink);
    }

    public static void addLovListForAttributeLink(String smartFormId, AttributeLink attributeLink, LovList lovList) {
        getSmartFormComponentService().addLovListForAttributeLink(smartFormId, attributeLink, lovList);
    }
    public static Label getAttributeLabel(String smartFormId, AttributeLink attributeLink) {
        return getSmartFormComponentService().getAttributeLabel(smartFormId, attributeLink);
    }

    public static Label getAttributeLabelAndRegisterAttributeComponents(String smartFormId, AttributeLink attributeLink, List<Component> components) {
        return getSmartFormComponentService().registerAttributeComponents(smartFormId, attributeLink, components);
    }

    public static void setLabelsClassNames(String smartFormId, String classNames) {
        getSmartFormComponentService().setLabelsClassNames(smartFormId, classNames);
    }

    public static void addFocusOnCombobox(AttributeLink attributeLink, ComboBox<Lov> lovComboBox, SmartForm smartForm, Optional<Lov> selected) {
        getSmartFormComponentService().addFocusOnCombobox(attributeLink, lovComboBox,smartForm,selected);

    }
    public static void addFocusOnComboboxOnComplex(AttributeLink attributeLink, ComboBox<Lov> lovComboBox, Integer nrRow, AttributeLink attrComplex, HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        getSmartFormComponentService().addFocusOnComboboxOnComplex(attributeLink, lovComboBox,nrRow,attrComplex,mapComponenteRandAtribute);

    }

    public static DataProvider<Lov, String> createLovDataProviderDynamic(AttributeLink attributeLink, ComboBox<Lov> lovComboBox, Integer nrRow, AttributeLink attrComplex,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        return getSmartFormComponentService().createLovDataProviderDynamic(attributeLink, lovComboBox, nrRow, attrComplex,mapComponenteRandAtribute);
    }

    public static ListDataProvider<Lov> createLovDataProviderList(AttributeLink attributeLink, ComboBox<Lov> lovComboBox, Integer nrRow, AttributeLink attrComplex,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        return getSmartFormComponentService().createLovDataProviderList(attributeLink, lovComboBox, nrRow, attrComplex,mapComponenteRandAtribute);
    }

    public static ListDataProvider<Lov> createLovMultiDataProviderList(AttributeLink attributeLink, MultiselectComboBox<Lov> lovComboBox, Integer nrRow, AttributeLink attrComplex,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        return getSmartFormComponentService().createLovMultiDataProviderList(attributeLink, lovComboBox, nrRow, attrComplex,mapComponenteRandAtribute);
    }

    public static Boolean getIsReadonly() {
        return isReadonly;
    }

    public static void setIsReadonly(Boolean isReadonly) {
        SmartFormSupport.isReadonly = isReadonly;
    }

    public static void aplicareFormulaCalculPortal( Integer nrRow, String smartFormId, HashMap<Integer, HashMap<Component, AttributeLink>> mapComponenteRandAtribute) {
        getSmartFormComponentService().aplicareFormulaCalculPortal(nrRow, smartFormId,mapComponenteRandAtribute);

    }
    public static void aplicareFormulaCalculPortalAtribut( String smartFormId, Map<AttributeLink, Component> mapComponente) {
        getSmartFormComponentService().aplicareFormulaCalculPortalAtribut(smartFormId,mapComponente);

    }
    public static void aplicareFormulaCalculColoanaPortal( Integer nrRow, AttributeLink attributeLink,LovList lovList,HashMap<Integer, HashMap<Component, AttributeLink>>  mapComponenteRandAtribute) {
        getSmartFormComponentService().aplicareFormulaCalculColoanaPortal(nrRow, attributeLink,lovList,mapComponenteRandAtribute);

    }
}
