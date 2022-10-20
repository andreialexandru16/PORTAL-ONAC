package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.smartform.gui.SmartFormSupport;

import java.util.Map;
import java.util.Optional;

public class CnpCuiAttributeValidator extends AbstractValidator<String> {

    /**
     * Constructs a validator with the given error message. The substring "{0}"
     * is replaced by the value that failed validation.
     *
     * @param errorMessage the message to be included in a failed result, not null
     */
    private static String smartFormId;

    public CnpCuiAttributeValidator(String errorMessage, String smartFormId) {
        super(errorMessage);
        this.smartFormId =smartFormId;
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
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
            return ValidationResult.ok();
        }else{

            return CuiAttributeValidator.isValid(value) || CnpAttributeValidator.isValid(value, Optional.ofNullable(smartFormId)) ? ValidationResult.ok() :
                    ValidationResult.error(getMessage(value));
        }
    }

}
