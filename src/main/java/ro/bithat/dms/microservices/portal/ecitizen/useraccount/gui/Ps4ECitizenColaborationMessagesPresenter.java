package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsColaborationService;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.Base64;
import java.util.Optional;

public class Ps4ECitizenColaborationMessagesPresenter extends PrepareModelFlowPresenter<Ps4ECitizenColaborationMessagesView> {

    @Autowired
    private DmswsColaborationService dmswsColaborationService;

    @Autowired
    private DmswsFileService fileService;

    private Optional<Integer> fileId = Optional.empty();

    @Override
    public void prepareModel(String state) {
        fileId = QueryParameterUtil.getQueryParameter("fileId", Integer.class);
        if (fileId.isPresent()) {
            try {
                fileService.checkSecurityByFileId(SecurityUtils.getToken(), fileId.get());
            } catch (Throwable t) {
                getLogger().error("User access PORTAL file  id:\t" + getFileId().get()
                        + "\t system user id:\t" + SecurityUtils.getUserId()
                        + "\r\n\tbackend exception:\t" + t.getMessage(), t.getStackTrace());
                UI.getCurrent().getPage().executeJs("swalError($0, $1)",
                        "Resursa se afla intr-o zona protejata sau nu va apartine!", getView());
                return;
            }
            getView().setColaborationMessagesContainer(dmswsColaborationService.getColaborationByFileId(SecurityUtils.getToken(), getFileId().get()));
        }
    }

    public Optional<Integer> getFileId() {
        return fileId;
    }

    @ClickEventPresenterMethod(viewProperty = "sendMessage")
    public void onSendMessageClick(ClickEvent<NativeButton> clickEvent) {

        String message = getView().getInputMessageValue();

        if (message == null || message.isEmpty()) {
            UI.getCurrent().getPage().executeJs("swalError($0)",
                    I18NProviderStatic.getTranslation("ps4.ecetatean.messages.send.swal.info.not.empty"));
        } else {
            dmswsColaborationService.addNewMessageToColaborare(SecurityUtils.getToken(), message, getFileId().get());
            UI.getCurrent().getPage().executeJs("swalInfo($0,$1)",
                    I18NProviderStatic.getTranslation("ps4.ecetatean.messages.send.swal.info.done"), getView());

        }

    }
    public void uploadFile(SucceededEvent event, MemoryBuffer fileBuffer) {

        try {
            String base64File = Base64.getEncoder().encodeToString(IOUtils.toByteArray(fileBuffer.getInputStream()));
            dmswsColaborationService.addNewFileToColaborare(SecurityUtils.getToken(),base64File,fileBuffer.getFileName(),getFileId().get());
            UI.getCurrent().getPage().executeJs("swalInfo($0,$1);", "Fișierul a fost încărcat cu succes!",getView());
            getLogger().info("uploadFile filename:\t", event.getFileName());
        } catch (Throwable t) {
            getLogger().error("uploadFile error get file data", t.getStackTrace());
            UI.getCurrent().getPage().executeJs("swalError($0);", "Fișierul nu a putut fi încărcat! Vă rugăm reîncercați!");
        }
    }

}
