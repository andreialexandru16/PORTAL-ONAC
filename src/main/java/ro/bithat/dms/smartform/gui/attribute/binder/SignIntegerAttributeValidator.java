package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.data.validator.RegexpValidator;

public class SignIntegerAttributeValidator extends RegexpValidator {

    private static final String PATTERN = "^"
            + "(\\+|-)?\\d+" // local
            + "$";

    public SignIntegerAttributeValidator(String errorMessage) {
        super(errorMessage, PATTERN, true);
    }


    @Override
    protected boolean isValid(String value) {
        return value == null || value.isEmpty() || super.isValid(value);
    }
}
