package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import ro.bithat.dms.service.SpelParserUtil;

import java.util.Optional;

public class SpELAttributeBeanPropertyValidator extends AbstractValidator<Object> {
	
	private String spel;


    public SpELAttributeBeanPropertyValidator(String spel, String errorMessage) {
        super(errorMessage);
        this.spel = spel;
    }

    @Override
    public ValidationResult apply(Object value, ValueContext context) {
        ValidationResult validationResult = null;
        if(value == null) {
            validationResult = ValidationResult.error(getMessage(value));
        } else if(String.class.isAssignableFrom(value.getClass())) {
            validationResult =  !((String)value).isEmpty() ? ValidationResult.ok() : ValidationResult.error(getMessage(value));
            if(!validationResult.isError()) {
            	Object result = SpelParserUtil.parseSpel((String) value, spel);
        		if (Boolean.valueOf(""+result)) {
        			validationResult = ValidationResult.ok();
        		}else {
        			validationResult =  ValidationResult.error(getMessage(value));
        		}
            }
        }else {
            validationResult = Optional.ofNullable(value).isPresent() ? ValidationResult.ok() : ValidationResult.error(getMessage(value));
            if(!validationResult.isError()) {
            	Object result = SpelParserUtil.parseSpel(value, spel);
        		if (Boolean.valueOf(""+result)) {
        			validationResult = ValidationResult.ok();
        		}else {
        			validationResult =  ValidationResult.error(getMessage(value));
        		}
            }
        }
        if(validationResult.isError() && context.getComponent().isPresent()
                && HasStyle.class.isAssignableFrom(context.getComponent().get().getClass())) {
            ((HasStyle)context.getComponent().get()).addClassName("vaadin-invalid-input");
        } else {
            ((HasStyle)context.getComponent().get()).removeClassName("vaadin-invalid-input");

        }
        return validationResult;
    }


}
