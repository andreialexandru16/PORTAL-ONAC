package ro.bithat.dms.passiveview.boot;


import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.util.Locale;

@SpringComponent
public class CustomVaadinServiceInitListener implements VaadinServiceInitListener, SessionInitListener {

    /** serial VUID */
    private static final long serialVersionUID = 7782078275956323697L;

    @Override
    public void serviceInit(ServiceInitEvent event) {

//        event.addBootstrapListener(new CustomBootstrapListener());

        event.getSource().addSessionInitListener(this);
    }


    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        event.getSession().setLocale(new Locale("ro", "RO"));
    }
}