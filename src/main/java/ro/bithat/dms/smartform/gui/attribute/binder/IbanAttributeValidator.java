package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import org.apache.commons.validator.routines.IBANValidator;

public class IbanAttributeValidator extends AbstractValidator<String> {


    /**
     * Constructs a validator with the given error message. The substring "{0}"
     * is replaced by the value that failed validation.
     *
     * @param errorMessage the message to be included in a failed result, not null
     */
    public IbanAttributeValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if(value == "")
            return ValidationResult.ok();
        return IBANValidator.getInstance().isValid(value) ? ValidationResult.ok() :
                ValidationResult.error(getMessage(value));
    }

}
