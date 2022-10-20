package ro.bithat.dms.microservices.portal.ecitizen.gui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadingSpinner extends Div {



    private Logger logger = LoggerFactory.getLogger(LoadingSpinner.class);

    public LoadingSpinner() {
        setContent();
    }

    public LoadingSpinner(Component... components) {
        super(components);
        setContent();
    }


    private void setContent() {
        addClassName("loading");
        Div loadingText = new Div(new Text("Vă rugăm să așteptați"));
        loadingText.addClassName("text_loading");
        Div ldsSpinner = new Div();
        ldsSpinner.addClassName("lds-spinner");
        for(int i = 0; i < 11; i++) {
            ldsSpinner.add(new Div());
        }
        add(loadingText, ldsSpinner);
    }

    public void show() {
        logger.info("loading spinner show notify");
        if(!getParent().isPresent()) {
            UI.getCurrent().add(this);
            logger.info("loading spinner show on body");
        }
    }

    public void close() {
        logger.info("loading spinner close notify");
        if(getParent().isPresent()) {
            UI.getCurrent().remove(this);
            logger.info("loading spinner remove from body");
        }

    }

}
