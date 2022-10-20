package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import com.vaadin.flow.data.validator.DoubleRangeValidator;

import java.util.Optional;

@Deprecated
public class NumberAttributeBeanPropertyValidator extends AbstractValidator<Double> {


    private Class<? extends Number> valueType;

    private String errorMessage;

    public NumberAttributeBeanPropertyValidator(String errorMessage, Class<? extends Number> valueType) {
        super(errorMessage);
        this.valueType = valueType;
        this.errorMessage = errorMessage;
    }

    @Override
    public ValidationResult apply(Double value, ValueContext context) {
        if(Optional.ofNullable(value).isPresent()) {
            if (Double.class.isAssignableFrom(valueType)) {
                return new DoubleRangeValidator(errorMessage, Double.MIN_VALUE, Double.MAX_VALUE).apply(value, context);
            }
            if (Integer.class.isAssignableFrom(valueType)) {
                if ((value == Math.floor(value)) && !Double.isInfinite(value)) {
                    return ValidationResult.ok();
                }
            }
            if (Long.class.isAssignableFrom(valueType)) {
                String valueStr = String.valueOf(value);
                if (Long.parseLong(valueStr.substring(valueStr.indexOf(".") + 1)) == 0) {
                    return ValidationResult.ok();
                }
            }

            return ValidationResult.error(getMessage(value));
        }
        return ValidationResult.ok();
    }


}
