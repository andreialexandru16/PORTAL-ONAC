package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class ThousandsConverter implements Converter<String, String> {
    @Override
    public Result<String> convertToModel(String value, ValueContext context) {
        if (value != null) {
            return Result.ok(value.replace(",", ""));
        }
        return Result.ok(value);
    }

    @Override
    public String convertToPresentation(String value, ValueContext context) {
        if (value == null){
            return "0";
        }
        return value;
    }
}

