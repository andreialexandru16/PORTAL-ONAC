package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.smartform.gui.SmartFormSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CnpAttributeValidator extends AbstractValidator<String> {

    /** The standard length of a CNP. */
    public static final int LENGTH = 13;

    private static String smartFormId;
    private static final DateFormat CNP_DATE_FORMAT = new SimpleDateFormat("yyMMdd");

    private static int[] CONTROL_VALUES = new int[] {
            2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9
    };

    private static int[] YEAR_OFFSET = new int[] {
            0, 1900, 1900, 1800, 1800, 2000, 2000
    };

    private static int[] getDigits(String cnp) {
        int[] digits = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            char c = cnp.charAt(i);
            if (!Character.isDigit(c)) {
                return null;
            }
            digits[i] = (byte) Character.digit(c, 10);
        }
        return digits;
    }

    private static int getControlSum(int[] twelveDigits) {
        int k = 0;
        for (int i = 0; i < 12; i++) {
            k += CONTROL_VALUES[i] * twelveDigits[i];
        }
        k %= 11;
        if (k == 10) {
            k = 1;
        }
        return k;
    }

    /** Returns if the given string represents a valid CNP for the given birthdate.
     * The 2nd and the 3rd digits represent the last two digits from the year birthdate,
     * the 4th and 5th represent the month and the 7th and 8th the day.
     */
    public static boolean validateBirthdate(String cnp, Date birthdate) {
        return cnp.length() > 6 && cnp.substring(1, 7).equals(CNP_DATE_FORMAT.format(birthdate));
    }

    /** Returns if the given string represents a valid CNP. */
    public static boolean validateGender(String cnp, boolean male, Date birthdate) {
        if (cnp == null || cnp.length() < 1 || !Character.isDigit(cnp.charAt(0)))
            return false;
        int g1 = Character.digit(cnp.charAt(0), 10);
        Calendar c = new GregorianCalendar();
        c.setTime(birthdate);
        int g2 = c.get(Calendar.YEAR) < 2000
                ? male ? 1 : 2
                : male ? 5 : 6;
        return g1 == g2;
    }

    /**
     * Constructs a validator with the given error message. The substring "{0}"
     * is replaced by the value that failed validation.
     *
     * @param errorMessage the message to be included in a failed result, not null
     */
    public CnpAttributeValidator(String errorMessage, String smartFormId) {
        super(errorMessage);

        this.smartFormId =smartFormId;
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return isValid(value, Optional.ofNullable(smartFormId)) ? ValidationResult.ok() :
                ValidationResult.error(getMessage(value));
    }

    public static boolean isValid(String cnp, Optional<String> smartFormIdOpt) {
        if(smartFormId==null){
            if(smartFormIdOpt.isPresent()){
                smartFormId=smartFormIdOpt.get();
            }
        }
        String isPersoanaStraina="0";
        Map<Long, DocAttrLink> attrList = SmartFormSupport.getAttributeLinkMap(smartFormId);

        if(attrList!=null){
            for (Long docAttrLinkId: attrList.keySet()){
                if(attrList.get(docAttrLinkId).getAttributeCode().equalsIgnoreCase("PERSOANA_STRAINA")){
                    isPersoanaStraina= SmartFormSupport.getValue(smartFormId,attrList.get(docAttrLinkId));
                    break;
                }
            }
        }

        if(isPersoanaStraina==null){
            isPersoanaStraina="0";
        }

        if(isPersoanaStraina.equals("1")){
            return true;
        }else{
            if (cnp == null || cnp.isEmpty()) {
                return true;
            } else if (cnp.length() != LENGTH) {
                return false;
            } else {
                int[] cnpDigits = getDigits(cnp);
                if (cnpDigits == null) {
                    return false;
                } else if (cnpDigits[LENGTH -1] != getControlSum(cnpDigits)) {
                    return false;
                } else {
                    int month = cnpDigits[3] * 10 + cnpDigits[4];
                    if (month < 1 && month > 12) {
                        return false;
                    } else {
                        int dayOfMonth = cnpDigits[5] * 10 + cnpDigits[6];
                        if (dayOfMonth < 1) {
                            return false;
                        } else {
                            int year = YEAR_OFFSET[cnpDigits[0]] + cnpDigits[1] * 10 + cnpDigits[2];
                            int maxDayOfMonth = (new GregorianCalendar(year, month, dayOfMonth)).getActualMaximum(5);
                            if (dayOfMonth > maxDayOfMonth) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
        }

    }

}
