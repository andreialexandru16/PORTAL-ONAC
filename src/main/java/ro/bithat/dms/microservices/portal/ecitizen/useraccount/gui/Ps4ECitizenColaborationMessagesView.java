package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.colaboration.ColaborareResponse;
import ro.bithat.dms.microservices.dmsws.colaboration.Mesaj;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeRoute;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

import java.util.List;

public class Ps4ECitizenColaborationMessagesView extends DivFlowViewBuilder<Ps4ECitizenColaborationMessagesPresenter> {


    private Div topOnlineChat = new Div();

    private Div topMembersChat = new Div();

    private Div topChatContainer = new Div(topOnlineChat, topMembersChat);

    private Div contentChat = new Div();

    private Div scroll3ContentChat = new Div(contentChat);

    private Div scrollVerticalDraggerBar = new Div();

    private Div scrollVerticalDraggerVertical = new Div(scrollVerticalDraggerBar);

    private Div scrollVerticalDraggerRail = new Div();

    private Div scrollVerticalDraggerContainer = new Div(scrollVerticalDraggerVertical, scrollVerticalDraggerRail);

    private Div scrollVerticalContentChat = new Div(scrollVerticalDraggerContainer);

    private Div scroll2ContentChat = new Div(scroll3ContentChat, scrollVerticalContentChat);

    private Div scroll1ContentChat = new Div(scroll2ContentChat);

    private Div divContentChat = new Div(scroll1ContentChat);

    private Div chatThread = new Div();

    private Div boxContentChat = new Div(divContentChat, chatThread);

    Input inputMessage = new Input();

    NativeButton sendMessage = new NativeButton();




    Input inputAttachement = new Input();

    NativeButton btnAttachements = new NativeButton();

    private Div bottomChat = new Div();

    private Div chatContainer = new Div(topChatContainer, boxContentChat, bottomChat);

    private Div divClearFix = new Div();

    private Div boxChatContainer = new Div(chatContainer, divClearFix);

    private Div divContainer = new Div(boxChatContainer, divClearFix);

    @Override
    protected void buildView() {

        setNeededStylesForBoxChatContainer();
        add(divContainer);
    }

    private void setNeededStylesForBoxChatContainer() {
        boxChatContainer.addClassName("box_chat");
        divClearFix.addClassName("clearfix");
        chatContainer.addClassName("chat");
        topChatContainer.addClassName("top_chat");
        topOnlineChat.addClassName("online_chat");
        topMembersChat.addClassName("chat_members");
        boxContentChat.addClassName("box_content_chat");
        contentChat.addClassName("content_chat");
        divContentChat.addClassName("div_content_chat");
        chatThread.addClassName("chat_thread");
        bottomChat.addClassName("bottom_chat");

        setStylesForScrollDivs();
    }

    private void setStylesForScrollDivs() {
        scroll1ContentChat.addClassNames("scrollbar_chat", "mCustomScrollbar", "_mCS_1", "mCS-autoHide");
        scroll2ContentChat.addClassNames("mCustomScrollBox", "mCS-light", "mCSB_vertical", "mCSB_inside");
        scroll3ContentChat.addClassName("mCSB_container");

        scroll2ContentChat.getStyle().set("max-height", "none");
        scroll2ContentChat.getElement().setProperty("tabindex", "0");

        scroll3ContentChat.getStyle().set("position", "relative");
        scroll3ContentChat.getStyle().set("top", "0px");
        scroll3ContentChat.getStyle().set("left", "0px");
        scroll3ContentChat.getElement().setProperty("dir", "ltr");

        scrollVerticalContentChat.addClassName("mCSB_dragger");
        scrollVerticalContentChat.getStyle().set("position", "absolute");
        scrollVerticalContentChat.getStyle().set("min-height", "30px");
        scrollVerticalContentChat.getStyle().set("display", "block");
        scrollVerticalContentChat.getStyle().set("height", "560px");
        scrollVerticalContentChat.getStyle().set("max-height", "613px");
        scrollVerticalContentChat.getStyle().set("top", "0px");

        scrollVerticalDraggerBar.addClassName("mCSB_dragger_bar");
        scrollVerticalContentChat.getStyle().set("line-height", "30px");
        scrollVerticalDraggerRail.addClassName("mCSB_draggerRail");

    }

    public void setColaborationMessagesContainer(ColaborareResponse messagesInfo) {

        setTopChat(messagesInfo.getUtilizatoriList());

        setBoxContentChat(messagesInfo.getMesajeList());

        setBottomChat();
    }

    private void setBottomChat() {

        inputAttachement.setType("file");

        inputMessage.setType("text");
        Div usersBarChat = new Div();
        Div inputMessageChat = new Div(usersBarChat, inputMessage, sendMessage);
        Div messageChat = new Div(inputMessageChat);

        sendMessage.addClassName("send_message");
        usersBarChat.addClassName("users_bar_chat");
        inputMessageChat.addClassName("input_message_chat");
        messageChat.addClassName("message_chat");

        ClickNotifierAnchor uploadBtn = new ClickNotifierAnchor();
        uploadBtn.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm");
        uploadBtn.setHref("javascript:void(0)");
        HtmlContainer uploadIcon = new HtmlContainer("i");
        uploadIcon.addClassNames("custom-icon", "icon-up");
        uploadBtn.add(uploadIcon, new Text("document.type.service.request.view.neededfiles.service.option.upload.label"));

        MemoryBuffer fileBuffer = new MemoryBuffer();
        Upload upload = new Upload(fileBuffer);
        upload.setId("upload-for-"+getPresenter().getFileId());
        upload.addClassName("upload-vaadin-no-file-list");
        upload.setUploadButton(btnAttachements);
        upload.setDropAllowed(false);
        upload.setMaxFiles(1);
        upload.setAcceptedFileTypes(".png",".jpg", ".jpeg", ".png", ".bmp", ".tiff", ".gif", ".raw", ".pdf", ".txt");
        upload.addClassName("upload-no-info");

        Div uploadL = new Div(upload);
        uploadL.getStyle().set("display", "inline-block");

        upload.addSucceededListener(event -> {
            getPresenter().uploadFile(event,fileBuffer);

        });

        Div btnAttachementsDiv = new Div(upload);

        btnAttachementsDiv.addClassName("btn_attachements");

        Div mainDiv = new Div(messageChat, btnAttachementsDiv);

        bottomChat.add(mainDiv);
    }

    private void setBoxContentChat(List<Mesaj> messagesList) {

        messagesList.stream().forEach(message -> setBoxContentChatRow(message));

    }

    private void setBoxContentChatRow(Mesaj message) {
        Div userName = new Div();
        Div userDate = new Div();
        Div boxUserName = new Div(userName, userDate);

        Div userComment = new Div();
        Div nrAnswers = new Div();
        Div dateLastAnswer = new Div();
        Div answersComment = new Div(nrAnswers, dateLastAnswer);

        Div textComment = new Div(userComment, answersComment);

        Div boxUserComment = new Div(boxUserName, textComment);

        contentChat.add(boxUserComment);

        userName.addClassName("user_name");
        userDate.addClassName("user_date");
        boxUserName.addClassName("box_user_name");

        userComment.addClassName("user_comment");

        nrAnswers.addClassName("date_last_answer");
        dateLastAnswer.addClassName("nr_answers");
        answersComment.addClassName("answers_comment");

        textComment.addClassName("text_comment");

        boxUserComment.addClassName("box_user_comment");

        userName.add(new Text(message.getCreatDe()));

        String date = message.getCreatLa().substring(0, message.getCreatLa().indexOf(" "));
        String hour = message.getCreatLa().substring(message.getCreatLa().indexOf(" ") + 1);
        String createdAt = hour == null ? "" : hour + " | " + date == null ? "" : date;
        Span sendAtSpan=new Span();
        sendAtSpan.setText(message.getCreatLa());
        userDate.add(sendAtSpan);

        if(message.getDownloadFileLink()!=null){
            ClickNotifierAnchor userCommentFile= new ClickNotifierAnchor();
            HtmlContainer downloadIcon = new HtmlContainer("i");
            downloadIcon.addClassNames("fas", "fa-file-download");
            userCommentFile.add(new Text(message.getFilename()),new Text("  "),downloadIcon);
            userCommentFile.setHref(StreamResourceUtil.getStreamResource(message.getFilename(),message.getDownloadFileLink()));
            userCommentFile.setTarget("_blank");

            userComment.add(userCommentFile);
        }
        else{
            userComment.getElement().setProperty("innerHTML", message.getMesaj());
        }

       // userComment.add(new Paragraph(message.getMesaj()));

    }

    private void setTopChat(List<Utilizator> usersList) {

        if (usersList.size() > 0) {
            topOnlineChat.add(new Text(I18NProviderStatic.getTranslation("myaccount.colaboration.messages.no.contribuitors.label", usersList.size())));
            usersList.stream().forEach(user -> setColaborationContribuitorRow(user));

        }


    }

    private void setColaborationContribuitorRow(Utilizator user) {

        Span spanTooltipText= new Span();
        Div tooltipChat = new Div(spanTooltipText);
        Div chatMember = new Div(tooltipChat);
        spanTooltipText.addClassName("tooltiptext");
        tooltipChat.addClassName("tooltip_chat");
        chatMember.addClassName("chat_member");

        spanTooltipText.add(user.getNume()+" "+user.getPrenume());
        Html img = new Html("<img src='frontend/ps4/ecitizen/assets/images/default-user.png'>");
        tooltipChat.add(img);

        topMembersChat.add(chatMember);
    }

    public String getInputMessageValue() {
        return inputMessage.getValue();
    }

    @ClientCallable
    public void swalInfoAck() {
       VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyAccountRoute.class));
    }
    @ClientCallable
    public void swalErrorAck() {
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class));
    }
}
