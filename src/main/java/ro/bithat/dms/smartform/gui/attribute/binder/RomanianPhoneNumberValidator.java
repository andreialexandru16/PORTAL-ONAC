package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.data.validator.RegexpValidator;

public class RomanianPhoneNumberValidator extends RegexpValidator {

    private static final String PATTERN = "^"
            + "(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}" // local
            + "$";

    public RomanianPhoneNumberValidator(String errorMessage) {
        super(errorMessage, PATTERN, true);
    }
}
