package ro.bithat.dms.passiveview.component.html;

import com.vaadin.flow.component.textfield.AbstractNumberField;
import com.vaadin.flow.function.SerializableFunction;

public class LongField extends AbstractNumberField<LongField, Long> {
    private static final SerializableFunction<String, Long> PARSER = (valueFormClient) -> {
        if (valueFormClient != null && !valueFormClient.isEmpty()) {
            try {
                return Long.parseLong(valueFormClient);
            } catch (NumberFormatException var2) {
                return null;
            }
        } else {
            return null;
        }
    };
    private static final SerializableFunction<Long, String> FORMATTER = (valueFromModel) -> {
        return valueFromModel == null ? "" : valueFromModel.toString();
    };

    public LongField() {
        super(PARSER, FORMATTER, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public LongField(String label) {
        this();
        this.setLabel(label);
    }

    public LongField(String label, String placeholder) {
        this(label);
        this.setPlaceholder(placeholder);
    }

    public LongField(ValueChangeListener<? super ComponentValueChangeEvent<LongField, Long>> listener) {
        this();
        this.addValueChangeListener(listener);
    }

    public LongField(String label, ValueChangeListener<? super ComponentValueChangeEvent<LongField, Long>> listener) {
        this(label);
        this.addValueChangeListener(listener);
    }

    public LongField(String label, Long initialValue, ValueChangeListener<? super ComponentValueChangeEvent<LongField, Long>> listener) {
        this(label);
        this.setValue(initialValue);
        this.addValueChangeListener(listener);
    }

    public void setMin(int min) {
        super.setMin((double) min);
    }

    public int getMin() {
        return (int) this.getMinDouble();
    }

    public void setMax(int max) {
        super.setMax((double) max);
    }

    public int getMax() {
        return (int) this.getMaxDouble();
    }

    public void setStep(int step) {
        if (step <= 0) {
            throw new IllegalArgumentException("The step cannot be less or equal to zero.");
        } else {
            super.setStep((double) step);
        }
    }

    public int getStep() {
        return (int) this.getStepDouble();
    }
}