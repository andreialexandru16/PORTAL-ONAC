package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import ro.bithat.dms.microservices.dmsws.colaboration.InfoMesaje;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Corespondenta;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECorespondentaFisiereRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenColaborationMessagesRoute;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.mvp.FlowView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NotificationContainer extends FlowViewDivContainer {

    Div gap20 = new Div();
    Paragraph pText= new Paragraph();
    Div notifText = new Div(pText);
    Div notifBell = new Div();
    Div notification = new Div(notifBell,notifText);

    public NotificationContainer(FlowView view) {
        super(view);

        gap20.addClassName("gap-20");
        notifBell.addClassName("notif_bell");
        notifText.addClassName("txt_notif");
        addClassName("box_notif");
        add(gap20,notification);

    }

    public void setInfoNotification(List<InfoMesaje> myMessages) {

        if(myMessages!=null && myMessages.size()!=0){
            notification.addClassName("notif_red");

            InfoMesaje messageInfo= myMessages.get(0);
            pText.removeAll();
            if(messageInfo.getNrMesaje()==1){
                pText.add(I18NProviderStatic.getTranslation("ps4.ecetatean.request.review.page.notification.one.message"));
            }
            else{
                pText.add(I18NProviderStatic.getTranslation("ps4.ecetatean.request.review.page.notification.has.messages",messageInfo.getNrMesaje()));
            }

            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("fileId", messageInfo.getIdFisier());
            filterPageParameters.put("target","_blank");
            pText.addClickListener(e
                    -> VaadinClientUrlUtil.setLocationToMessages(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));

            notification.getStyle().set("cursor","pointer");
        }
        else{
            notification.addClassName("notif_green");
            pText.removeAll();
            pText.add("ps4.ecetatean.request.review.page.notification.0.messages");

        }
    }
    public void setInfoNotificationFiles(String nrCorespondentaList, Optional<Integer> fileId) {

        if(nrCorespondentaList!=null && !nrCorespondentaList.equals("0")){
            notification.addClassName("notif_red");

             pText.removeAll();
            if( nrCorespondentaList.equals("1")){
                pText.add(I18NProviderStatic.getTranslation("ps4.ecetatean.request.review.page.notification.one.file"));
            }
            else{
                pText.add(I18NProviderStatic.getTranslation("ps4.ecetatean.request.review.page.notification.has.files", nrCorespondentaList));
            }

            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("fileId",fileId.get() );
            pText.addClickListener(e
                    ->

                    VaadinClientUrlUtil.setLocationToMessages(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECorespondentaFisiereRoute.class)));

            notification.getStyle().set("cursor","pointer");
        }

    }
}
