package ro.bithat.dms.smartform.gui.attribute.binder;
//09.06.2021 - Neata Georgiana - ANRE - am adaugat  constructor pentru a permite validare camp numeric cu numar de zecimale specificat
//22.06.2021 - Neata Georgiana - ANRE - modificat regex pentru acceptare numar negativ

import com.vaadin.flow.data.validator.RegexpValidator;

public class DoubleAttributeValidatorThousandsAware extends RegexpValidator {

    private static final String PATTERN = "^"
            + "[0-9]+(\\\\.[0-9]+)?" // local
            + "$";
//09.06.2021 - Neata Georgiana - ANRE - am adaugat acest constructor pentru a permite validare camp numeric cu numar de zecimale specificat
//22.06.2021 - Neata Georgiana - ANRE - modificat regex pentru acceptare numar negativ

    public DoubleAttributeValidatorThousandsAware(String errorMessage, Integer precision) {
        super(errorMessage, "-?+[0-9,]+(\\.\\d{"+precision+"})?", true);
    }
    public DoubleAttributeValidatorThousandsAware(String errorMessage) {
        super(errorMessage, "[+-]?[0-9,]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?", true);
    }

    @Override
    protected boolean isValid(String value) {
        if(value.indexOf(".") == value.length() - 1) {
            value += "0";
        }
        return value == null || value.isEmpty() || super.isValid(value);
    }

}
