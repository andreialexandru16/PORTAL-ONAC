package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaControl;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaPetitii;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsControlService;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsPetitiiService;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Ps4ECorespondentaPetitiiPresenter extends PrepareModelFlowPresenter<Ps4ECorespondentaPetitiiView> {

    @Autowired
    private DmswsPetitiiService petitiiService;

    private Map<Integer, List<CorespondentaPetitii>> pagesData = new ConcurrentHashMap<>();

    @Override
    public void prepareModel(String state) {
        List<CorespondentaPetitii> corespondentaPetitiiList = petitiiService.getCorespondentaPetitii(SecurityUtils.getToken(), SecurityUtils.getContCurentPortalE().getUserCurent().getId()).getCorespondentaPetitiiList();
        getView().setMyRequestsTable(corespondentaPetitiiList);
    }

    public void uploadFile(SucceededEvent event, MemoryBuffer fileBuffer, Integer idCorespondenta) {
        try {
            String base64File = Base64.getEncoder().encodeToString(IOUtils.toByteArray(fileBuffer.getInputStream()));
            petitiiService.addNewFileToPetitie(SecurityUtils.getToken(),base64File,fileBuffer.getFileName(),idCorespondenta);
            UI.getCurrent().getPage().executeJs("swalInfo($0,$1);", "Fișierul a fost încărcat cu succes!",getView());
            getLogger().info("uploadFile filename:\t", event.getFileName());
        } catch (Throwable t) {
            getLogger().error("uploadFile error get file data", t.getStackTrace());
            UI.getCurrent().getPage().executeJs("swalError($0);", "Fișierul nu a putut fi încărcat! Vă rugăm reîncercați!");
        }
    }
}