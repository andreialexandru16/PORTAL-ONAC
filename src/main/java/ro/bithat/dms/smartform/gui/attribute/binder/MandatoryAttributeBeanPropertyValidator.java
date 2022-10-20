package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.ComplexTableComponent;

import java.util.Optional;

public class MandatoryAttributeBeanPropertyValidator extends AbstractValidator<Object> {



    public MandatoryAttributeBeanPropertyValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(Object value, ValueContext context) {
        ValidationResult validationResult = null;

        if(value == null) {
            validationResult = ValidationResult.error(getMessage(value));
        } else if(String.class.isAssignableFrom(value.getClass())) {
            validationResult =  !((String)value).trim().isEmpty() ? ValidationResult.ok() : ValidationResult.error(getMessage(value));
        }else {
            validationResult = Optional.ofNullable(value).isPresent() ? ValidationResult.ok() : ValidationResult.error(getMessage(value));
        }
        if(validationResult.isError() && context.getComponent().isPresent()
                && HasStyle.class.isAssignableFrom(context.getComponent().get().getClass())) {
            ((HasStyle)context.getComponent().get()).addClassName("vaadin-invalid-input");
        } else {
            ((HasStyle)context.getComponent().get()).removeClassName("vaadin-invalid-input");

        }
        if(Checkbox.class.isAssignableFrom(context.getComponent().get().getClass()) )
        {

            if(value.toString().equals("true")){
                validationResult=ValidationResult.ok();
            }else{
                validationResult=ValidationResult.error(getMessage(value));
                ((HasStyle)context.getComponent().get()).addClassName("vaadin-invalid-input");
            }


        }
        if(ComplexTableComponent.class.isAssignableFrom(context.getComponent().get().getClass())) {
            if (!((ComplexTableComponent) context.getComponent().get()).getTableContainerBody().getElement().toString().equals("<tbody></tbody>")) {
                validationResult = ValidationResult.ok();
                ((HasStyle)context.getComponent().get()).removeClassName("vaadin-invalid-input");

            } else {
                validationResult = ValidationResult.error(getMessage(value));
                ((HasStyle) context.getComponent().get()).addClassName("vaadin-invalid-input");
            }
        }
        return validationResult;
    }


}
