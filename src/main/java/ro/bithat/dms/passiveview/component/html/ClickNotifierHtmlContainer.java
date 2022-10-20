package ro.bithat.dms.passiveview.component.html;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;

public class ClickNotifierHtmlContainer extends HtmlContainer implements ClickNotifier<ClickNotifierHtmlContainer> {

    public ClickNotifierHtmlContainer() {
    }

    public ClickNotifierHtmlContainer(Component... components) {
        super(components);
    }

    public ClickNotifierHtmlContainer(String tagName) {
        super(tagName);
    }

    public ClickNotifierHtmlContainer(String tagName, Component... components) {
        super(tagName, components);
    }
}
