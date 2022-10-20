package ro.bithat.dms.smartform.gui;
//22.06.2021 # Neata Georgiana # ANRE #  preia valoare atribut doar daca nu e de tip complex (pentru acestea se intra pe else pentru tratare diferita)

import com.vaadin.flow.component.Component;
import org.springframework.util.Assert;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.file.RowAttrComplexList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.smartform.gui.attribute.component.HolographicComponent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SmartForm {



    default void register(String id, AttributeLinkList attributeLinkList) {
        Assert.isAssignable(Component.class, getClass());
        ((Component)this).setId(id);
        SmartFormSupport.registerSmartFormById((Component)this, attributeLinkList);
    }

    default void register(AttributeLinkList attributeLinkList) {
        Assert.isAssignable(Component.class, getClass());
        SmartFormSupport.registerSmartFormById((Component)this, attributeLinkList);
    }

    default DocAttrLinkList getInitialDocAttrLinkList() {
        return SmartFormSupport.getInitialDocAttrLinkList(((Component)this).getId().get());
    }

    default DocAttrLinkList getDocAttrLinkList() {
        SmartFormSupport smartFormSupport= new SmartFormSupport();
      //TODO 1 - check if is complex
        List<DocAttrLink> list = new ArrayList<>();
        for(DocAttrLink initialSet: SmartFormSupport.getAttributeLinkMap(((Component)this).getId().get()).values()) {
            Optional<String> value = Optional.ofNullable(SmartFormSupport.getValue(((Component)this).getId().get(), initialSet));
           //22.06.2021 # Neata Georgiana # ANRE #  preia valoare atribut doar daca nu e de tip complex (pentru acestea se intra pe else pentru tratare diferita)
            if(value.isPresent() && !initialSet.getSelectionType().equals(4L) && !initialSet.getSelectionType().equals(3L)) {
                if(initialSet.getDataType().equals("HOLOGRAPH")){
                    initialSet.setValue(value.get());
                    initialSet.setImage(SmartFormSupport.getImage(((Component)this).getId().get(), initialSet));
                    list.add(initialSet);
                }else{
                    initialSet.setValue(value.get());
                    list.add(initialSet);
                }

            }
            //daca e complex
            //TODO de verificat mai frumos selection type = complex
            else if (initialSet.getSelectionType().equals(4L)){
                //initialSet e atributul complex

                Optional<List<RowAttrComplexList>> valueAtrComplex=Optional.empty();
                //preluam lista de attr din hashmap  de attr complex

                    valueAtrComplex = Optional.ofNullable(smartFormSupport.getValueAtrComplex(((Component)this).getId().get(), initialSet));


                if(valueAtrComplex.isPresent()){
                    //asa ajung sa fie setate pe setul initial lista de attr ale atributului complex
                    initialSet.setAttrsOfComplex(valueAtrComplex.get());
                    list.add(initialSet);
                }
            }
           else if(initialSet.getSelectionType().equals(3L) && !initialSet.getDataType().equals("OPT_GROUP"))   {
                value= Optional.ofNullable(SmartFormSupport.getValueLov(((Component)this).getId().get(), initialSet));
                if(value.isPresent()){
                    initialSet.setValue(value.get());

                }
                list.add(initialSet);
            }
            else if(initialSet.getSelectionType().equals(3L) && initialSet.getDataType().equals("OPT_GROUP"))   {
                value= Optional.ofNullable(SmartFormSupport.getValueLovOptGroup(((Component)this).getId().get(), initialSet));
                if(value.isPresent()){
                    initialSet.setValue(value.get());

                }
                list.add(initialSet);
            }




        }
        return SmartFormSupport.getSmartFormBinding(((Component)this).getId().get(), list);
    }

    default void setReadOnly(Boolean readOnly) {
        SmartFormSupport.setReadOnly(((Component)this).getId().get(), readOnly);
    }


    default String getHtml() {
        return ((Component)this).getElement().getOuterHTML();
    }

    File getHtmlFile();

    default boolean validate() {
        try{
            return SmartFormSupport.validate(((Component)this).getId().get());

        }catch (Exception e){

        }
        return true;
    }

    void buildSmartForm(AttributeLinkList attributeLinkList);

    default Integer getMaxResponsiveSteps() {
        return  SmartFormSupport.getMaxResponsiveSteps(((Component)this).getId().get());
    }

    default void setLabelsClassNames(String classNames) {
        SmartFormSupport.setLabelsClassNames(((Component)this).getId().get(), classNames);
    }

    void addAttributeLinkComponent(AttributeLink attributeLink, List<Component> components, Component layout, boolean hasLabel);


}
