package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.InternalServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.bithat.dms.service.StreamToStringUtil;

public class CustomServiceErrorTarget extends InternalServerError {

    private static Logger logger = LoggerFactory.getLogger(CustomServiceErrorTarget.class);


    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {
        logger.error("Eroare backend dmsws:\t" + parameter.getException().getMessage(), parameter.getException().getStackTrace());
        getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/page2-500.html"));
        return super.setErrorParameter(event, parameter);
    }
}