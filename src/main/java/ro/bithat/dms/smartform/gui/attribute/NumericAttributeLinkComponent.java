package ro.bithat.dms.smartform.gui.attribute;
//09.06.2021 - Neata Georgiana - ANRE - am adaugat  constructor pentru a permite validare camp numeric cu numar de zecimale specificat

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import ro.bithat.dms.smartform.gui.attribute.binder.DoubleAttributeValidator;
import ro.bithat.dms.smartform.gui.attribute.binder.SignIntegerAttributeValidator;
import ro.bithat.dms.smartform.gui.attribute.binder.StringAttributeBinderBean;

import java.util.Arrays;
import java.util.Optional;

@SpringComponent
@UIScope
public class NumericAttributeLinkComponent extends AttributeLinkGenericComponent {

    private Integer intValue;

    private Long longValue;

    private Double doubleValue;


    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.NUMERIC);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        Optional<Integer> length = Optional.ofNullable(attributeLink.getLength());
        Optional<Integer> precision = Optional.ofNullable(attributeLink.getPrecision());
        Optional<String> value = Optional.ofNullable(attributeLink.getValue());
//        BeanValidationBinder<NumericAttributeLinkComponent> binder = new BeanValidationBinder<>(NumericAttributeLinkComponent.class);
//        boolean integerValueShouldSet = true;
        TextField component = new TextField( );
        smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(component), layout, true);
        component.setValueChangeMode(ValueChangeMode.EAGER);
        StringAttributeBinderBean binderBean = new StringAttributeBinderBean(((Component)smartForm).getId().get(), attributeLink);
//        if(value.isPresent() && !value.get().isEmpty()) {
//            component.setValue(BeanUtil.getBean(ConversionService.class).convert(value.get(), Double.class));
//        }
//        binder.forField((NumberField)component)
//                .withValidator(new DoubleRangeValidator("nu este valid", Double.MIN_VALUE, Double.MAX_VALUE))
//                .bind(NumericAttributeLinkComponent::getDoubleValue,
//                        NumericAttributeLinkComponent::setDoubleValue);
        if(precision.isPresent() && precision.get().compareTo(0) != 0) {
            //09.06.2021 - Neata Georgiana - ANRE - am adaugat acestr constructor pentru a permite validare camp numeric cu numar de zecimale specificat
            String errMessage="Completati cu valoare numerica cu maxim " + precision.get() + " zecimale. ";

            SmartFormSupport.bind(((Component)smartForm).getId().get(),
                    binderBean, component,
                    new DoubleAttributeValidator(errMessage,precision.get()));
//            integerValueShouldSet = false;
//            component = new NumberField();
//            ((NumberField)component).
        } else  {
            SmartFormSupport.bind(((Component)smartForm).getId().get(),
                    binderBean, component,
                    new SignIntegerAttributeValidator("Completati cu valoare numerica fara zecimale."));
        }
        component.addInputListener(e -> {
            if(!SmartFormSupport.validate(((Component)smartForm).getId().get(), attributeLink)) {
                String valueStr = ((TextField)e.getSource()).getValue();
                if(valueStr.length()>0) {
                    valueStr = valueStr.substring(0, valueStr.length() - 1);
                    ((TextField) e.getSource()).setValue(valueStr);
                }
                SmartFormSupport.validate(((Component)smartForm).getId().get(), attributeLink);
            }
        })  ;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }
}
