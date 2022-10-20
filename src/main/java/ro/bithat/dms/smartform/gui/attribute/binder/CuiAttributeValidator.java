package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class CuiAttributeValidator extends AbstractValidator<String> {

    /** The standard length of a CUI. */
    public static final int LENGTH = 10;


    private static int[] CONTROL_VALUES = new int[] {
            7,5,3,2,1,7,5,3,2
    };


    private static int[] getDigits(String cnp) {
        try {
            int[] digits = new int[cnp.length()];
            for (int i = 0; i < cnp.length(); i++) {
                char c = cnp.charAt(i);
                if (!Character.isDigit(c)) {
                    return null;
                }
                digits[i] = (byte) Character.digit(c, 10);
            }
            return digits;
        } catch (Throwable t) {
            return null;
        }
    }

    private static int getControlSum(int[] codeDigits) {
        int k = 0;
        for (int i = 0; i < codeDigits.length-1; i++) {
            k += CONTROL_VALUES[i] * codeDigits[i];
        }
//        k *= 10;
        k %= 11;
        if (k == 10) {
            k = 0;
        }
        return k;
    }


    /**
     * Constructs a validator with the given error message. The substring "{0}"
     * is replaced by the value that failed validation.
     *
     * @param errorMessage the message to be included in a failed result, not null
     */
    public CuiAttributeValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return isValid(value) ? ValidationResult.ok() :
                ValidationResult.error(getMessage(value));
    }

    public static boolean isValid(String cui) {
        //cui=cui.replace("RO","");
        if (cui == null || cui.isEmpty()) {
            return true;
        } else if (cui.length() > LENGTH) {
            return false;
        } else {
            int[] cuiDigits = getDigits(cui);
            if (cuiDigits == null) {
                return false;
                //            } else if (cuiDigits[cui.length() -1] != getControlSum(cuiDigits)) {
                //                return false;
            } else if(cui.length() < 6){
                return false;
            } else {
                return true;
            }
        }
    }

}
