package ro.bithat.dms.smartform.gui.attribute;
//09.06.2021 - Neata Georgiana - ANRE -  daca exista valoare implicita pentru lov se cauta in optiuni si se autoselecteaza
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import freemarker.ext.beans.HashAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.Lov;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.DocumentaSmartForm;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import ro.bithat.dms.smartform.gui.attribute.binder.LovAttributeBinderBean;

import javax.management.Attribute;
import java.util.*;

@SpringComponent
@UIScope
public class ComboBoxAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Autowired
    private DmswsPS4Service dmswsPS4Service;
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.OPT_SELECT_DROPBOX);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        if(attributeLink.getLovId()!=null && attributeLink.getLovId()!=0){
            LovList lovList = new LovList();
        // Neata Georgiana # 23.06.2021 # ANRE # mutat logica de incarcare LOV la click - addFocusOnCombobox

            List<Lov> backendLov =new ArrayList<>();
               ComboBox<Lov> lovComboBox = new ComboBox<>();

               //lovComboBox.setItems(backendLov);
               lovComboBox.setAllowCustomValue(false);
               lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
               Optional<Lov> selected = Optional.empty();
               if(Optional.ofNullable(attributeLink.getValueForLov()).isPresent() && !attributeLink.getValueForLov().isEmpty()) {
                   if(Optional.ofNullable(attributeLink.getValue()).isPresent() && !attributeLink.getValue().isEmpty()) {

                       selected =  attributeLink.getValueForLov().stream()
                               .filter(bv ->  attributeLink.getValue().equalsIgnoreCase(bv.getId()))
                               .findFirst();

                   }
                   else{
                       selected = Optional.of(new Lov(attributeLink.getValueForLov().get(0).getId(), attributeLink.getValueForLov().get(0).getValoare()));

                   }
               // lovComboBox.setItems(attributeLink.getValueForLov());
               }
               else
               //09.06.2021 - Neata Georgiana - ANRE -  daca exista valoare implicita pentru lov se cauta in optiuni si se autoselecteaza
               if(Optional.ofNullable(attributeLink.getValue()).isPresent() && !attributeLink.getValue().isEmpty()) {
                   Set<AttributeLink> currentAttrsList = ((DocumentaSmartForm) smartForm).getComponentMapAttr().keySet();

                   if (attributeLink.getValoareImplicita() != null && !attributeLink.getValoareImplicita().isEmpty()) {

               //20.11.2021 - Robert Stefan - ANRE -  daca valoarea implicita este select dependent facem replace
                       String valoareImplicita = attributeLink.getValoareImplicita();
                       if(valoareImplicita.contains("^")){
                           for ( AttributeLink attrLink: currentAttrsList){
                               if(attrLink.getValue()!=null && !attrLink.getValue().isEmpty()) {
                                   String atrName = attrLink.getName();
                                   if (attrLink.getName().contains("|")) {
                                       atrName = attrLink.getName().replaceAll("\\|", "\\\\\\|");
                                      // attrLink.setName(attrLink.getName().replaceAll("\\|", "\\\\\\|"));
                                   }

                                   valoareImplicita = valoareImplicita.replaceAll("\\^" + atrName, attrLink.getValue());
                               }
                           }
                       }


                       attributeLink.setValoareImplicita(valoareImplicita);
                       String valoare = dmswsPS4Service.getSqlResult(attributeLink.getValoareImplicita()).getInfo();
                       if (valoare != null && !valoare.isEmpty()) {
                           attributeLink.setValue(valoare);
                       }

                       }


                  lovList=BeanUtil.getBean(PS4Service.class).getLovList(attributeLink.getLovId());
                   backendLov = lovList.getLov();
                   if(backendLov != null) {
                       selected = backendLov.stream()
                               .filter(bv -> attributeLink.getValue().equalsIgnoreCase(bv.getId()))
                               .findFirst();
                   }
                   //20.11.2021 - Robert Stefan - ANRE -  tratare caz special daca atributul pe care se seteaza valoarea implicita este dependent
                   else if (attributeLink.getSelectSql()!= null&&attributeLink.getSelectSql().contains("^")){
                       Map<AttributeLink, String> currentAttrsMap = new HashMap<>();
                       for(AttributeLink currentAttr : currentAttrsList){
                           currentAttrsMap.put(currentAttr, currentAttr.getValue());
                       }

                       if (!currentAttrsMap.isEmpty()){
                           lovList = BeanUtil.getBean(PS4Service.class).getLovListWithDependeciesDynamic(attributeLink, currentAttrsMap, null, 0, 2147483647, null );
                       }

                       backendLov = lovList.getLov();
                       if(backendLov != null) {
                           selected = backendLov.stream()
                                   .filter(bv -> attributeLink.getValue().equalsIgnoreCase(bv.getId()))
                                   .findFirst();
                       }
                   }

               }
               smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(lovComboBox), layout, true);
               if(attributeLink.getValueForLov()!=null &&attributeLink.getValueForLov().size()!=0 && selected.isPresent() ){
                   lovComboBox.setItems(attributeLink.getValueForLov());
                   lovComboBox.setValue(selected.get());
                   SmartFormSupport.addLovListForAttributeLink(((Component) smartForm).getId().get(), attributeLink, new LovList(attributeLink.getValueForLov()));

               }else     if(backendLov!=null && selected.isPresent() ){
                   lovComboBox.setItems(backendLov);
                   lovComboBox.setValue(selected.get());
                   SmartFormSupport.addLovListForAttributeLink(((Component) smartForm).getId().get(), attributeLink, new LovList(backendLov));

               }

         //  }
            Optional<Lov> finalSelected = selected;
               if(attributeLink.getReadOnly()==null ||!attributeLink.getReadOnly()){
                   lovComboBox.addFocusListener(focusEvent -> {
                       SmartFormSupport.addFocusOnCombobox(attributeLink,lovComboBox,smartForm, finalSelected);});
                   SmartFormSupport.bind(((Component)smartForm).getId().get(),
                           new LovAttributeBinderBean(((Component)smartForm).getId().get(), attributeLink,
                                   selected.isPresent() ? selected.get() : null), lovComboBox);
               }


        }

    }


}
