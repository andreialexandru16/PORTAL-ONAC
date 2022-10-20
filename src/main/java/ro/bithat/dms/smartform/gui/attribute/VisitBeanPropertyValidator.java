package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class VisitBeanPropertyValidator extends AbstractValidator<Object> {


    private boolean visited = false;

    public VisitBeanPropertyValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(Object value, ValueContext context) {
        return visited ? ValidationResult.ok()
                : ValidationResult.error(getMessage(value));
    }

    public void visit() {
        visited = true;
    }

    public void invalidate() {
        visited = false;
    }

}
