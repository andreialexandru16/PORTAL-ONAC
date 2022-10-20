package ro.bithat.dms.microservices.portal.ecitizen.gui.template;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Meta;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;

@Viewport("width=device-width, initial-scale=1, shrink-to-fit=no")
@Meta(name = "charset", content = "utf-8")
@PageTitle(" Acasă")
//@I18NPageTitle(messageKey = "ps4.ecitizen.page.title")
//@PWA(name = "e-CETATEAN - Primăria Sectorului 4", shortName = "e-CETATEAN")
//       , iconPath = "PORTAL/assets/images/logos/logo.png")
public class Ps4ECitizenPwa implements AppShellConfigurator {
}
