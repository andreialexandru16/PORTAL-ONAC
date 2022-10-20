package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.passiveview.FlowComponent;

import javax.annotation.PostConstruct;

public abstract class DocumentTypeRoute extends Ps4ECitizenPortalRoute {

    @FlowComponent("online-service-breadcrumb")
    private BreadcrumbView breadcrumbView;

    @Autowired
    private PS4Service ps4Service;

    @Override
    @PostConstruct
    protected void setup() {
        super.setup();
        setFooterVisualIdentityClassNames("gap-50");
    }

    public BreadcrumbView getBreadcrumbView() {
        return breadcrumbView;
    }

    public PS4Service getPs4Service() {
        return ps4Service;
    }
}
