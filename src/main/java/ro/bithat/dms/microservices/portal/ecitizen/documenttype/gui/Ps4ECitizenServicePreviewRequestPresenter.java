package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;
//09.06.2021 - Neata Georgiana - ANRE - am adaugat toate verificarile necesare pentru preluare date din cont sau valoare implicita intr-o singura trecere,
// pentru a nu mai parcurge de fiecare data toata lista de atribute
//13.07.2021 - NG - ANRE - resetare contor schimbari facute pentru a nu afisa dialog de confirmare iesire din pagina

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import com.vaadin.flow.data.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.*;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.Lov;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.LoadingSpinner;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.SolicitareService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ContCurentPortalE;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveReq;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveResp;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.security.UserWithUserToken;
import ro.bithat.dms.service.URLUtil;
import ro.bithat.dms.smartform.gui.SmartFormComponentService;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static ro.bithat.dms.boot.BeanUtil.getBean;

public class Ps4ECitizenServicePreviewRequestPresenter extends DocumentTypePresenter<Ps4ECitizenServicePreviewRequestView> {

    @Value("${google.maps.api.key2}")
    private String googleMapsApiKey;

    @Value("${ps4.ecitizen.class.urbanism.id}")
    private Integer classUrbanismId;

    @Value("${ps4.ecitizen.class.reabilitare.id}")
    private Integer classReabilitareId;

    @Value("${dmsws.anonymous.services}")
    private String anonymousServices;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Optional<Integer> requestFileId = Optional.empty();

    @Autowired
    private DmswsUtilizatorService dmswsUtilizatorService;

    @Autowired
    private DmswsPS4Service dmswsPS4Service;

    @Autowired
    private DmswsFileService fileService;

    @Autowired
    private SolicitareService solictiareService;
    @Autowired
    private URLUtil urlUtil;

    private Optional<DocAttrLinkList> docAttrLinkList = Optional.empty();
    private List<DocAttrLink> attrLinkListHidden = new ArrayList<>();

    Optional<Integer> documentTypeId = QueryParameterUtil.getQueryParameter("tipDocument", Integer.class);


    @Override
    public void afterPrepareModel(String state) {
        SecurityContext context = SecurityContextHolder.getContext();

        ContCurentPortalE contCurentPortalE = SecurityUtils.getContCurentPortalE();

        AttributeLinkList all = getPs4Service().getMetadataByDocumentId(getDocumentId().get());
        getView().buildDmsSmartForm(all, "");
        getView().setServiceNameAndRegisterPreviousStep(getDocument().get().getDenumire(), "");

        UI.getCurrent().getPage().executeJavaScript("resetChanges();");

    }


    public void savePdf(String pdf) {
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        filterPageParameters.put("document", getDocumentId().get());
        filterPageParameters.put("request", requestFileId.get());
        if (getFromMainScreen().isPresent()) {
            filterPageParameters.put("fromMainScreen", getFromMainScreen().get());

        }
        try {
            byte[] pdfData = Base64.getDecoder().decode(pdf);
            CreateTipDocFileResponse resp = fileService.uploadToReplaceExistingFile(SecurityUtils.getToken(), new Long(requestFileId.get()), "cerere.pdf",
                    pdfData);
            List<DocObligatoriuExtra> listaDoc = getPs4Service().getDocumenteObligatoriiServiciu(Long.valueOf(getDocumentId().get()), getFileId());
            for (DocObligatoriuExtra doc : listaDoc) {
                if (doc.getDocObligatoriu().getView_direct().equals(1)) {
                    BaseModel fileResponse = saveDoc(doc.getDocObligatoriu().getDenumire() + ".pdf", pdfData, doc.getDocObligatoriu().getCod(), getRequestFileid().get());

                }
            }
            logger.info("pdf uploaded");
            docAttrLinkList = Optional.empty();
            if (getFromMainScreen().isPresent() && getFromMainScreen().get() != null) {
                if (!getFileId().isPresent()) {
                    Optional<DocAttrLink> emailDocAttrLink = getView().getDocAttrLinkList().getDocAttrLink().stream().filter(dl -> dl.getDataType() != null && dl.getDataType().equalsIgnoreCase("email")).findFirst();
                    String email = emailDocAttrLink.isPresent() ? emailDocAttrLink.get().getValue() : SecurityUtils.getEmail();

                    if (new EmailValidator("Email invalid").apply(email, null).isError()) {
                        UI.getCurrent().getPage().executeJs("swalError($0);", "Email invalid");
                        return;
                    }
                    solictiareService.send(SecurityUtils.getToken(), Long.valueOf(requestFileId.get()), email, urlUtil.getPathIfVaadin(), false, Optional.empty(), Optional.empty(), Optional.empty());

                }
                if (getFromMainScreen().get().equals("GN")) {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator-gn.html?&idClasaDoc=" + getDocumentType());

                } else if (getFromMainScreen().get().equals("EE")) {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator.html?&idClasaDoc=" + getDocumentType());

                } else if (getFromMainScreen().get().equals("ET")) {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator-et.html?&idClasaDoc=" + getDocumentType());

                } else if (getFromMainScreen().get().equals("COMP")) {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator-compensari.html?&idClasaDoc=" + getDocumentType());

                } else {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator.html?&idClasaDoc=" + getDocumentType());

                }
                UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

            } else {

                if (listaDoc == null || listaDoc.size() == 0) {
                    VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class));
                } else {
                    VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceAttacheFileRoute.class));
                }
            }

        } catch (Throwable e) {


            logger.error(e.getMessage(), e);

            UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare server DMSWS! Va rugam reincercati!");
        }
    }

    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }

    public RunJasperByTipDocAndSaveResp getIdFisierJasper() {
        RunJasperByTipDocAndSaveReq runJasperByTipDocAndSaveReq = new RunJasperByTipDocAndSaveReq();
        runJasperByTipDocAndSaveReq.setIdDocument(getDocumentId().get());
        runJasperByTipDocAndSaveReq.setMainId(requestFileId.get());
        String fileName = getDocument().get().getDenumire() + " " + (SecurityUtils.getFullName()) + " " + (new SimpleDateFormat("dd.MM.yyyy").format(new Date()).toString());
        runJasperByTipDocAndSaveReq.setOutputName(fileName);
        RunJasperByTipDocAndSaveResp resp = fileService.getIdFisierJasper(runJasperByTipDocAndSaveReq);
        resp.setFileName(fileName + ".pdf");
        return resp;
    }

    public byte[] getPdfByIdFisier(String idFisier) {
        return fileService.getFileAsPdf(SecurityUtils.getToken(), idFisier);
    }

    public BaseModel saveDoc(String filename, byte[] someByteArray, String codTipDoc, Integer parentFileId) {
        CreateTipDocFileResponse fileResponse = fileService.uploadFisierTipDocCode(SecurityUtils.getToken(), codTipDoc, SecurityUtils.getUserId(), filename, filename, someByteArray);
        return fileService.attachFile(SecurityUtils.getToken(), Integer.parseInt(fileResponse.getFileId()), parentFileId);
    }

    public Optional<Integer> getRequestFileid() {
        return requestFileId;
    }

}
