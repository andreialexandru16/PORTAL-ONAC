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
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyDraftRequestsRoute;
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

public class Ps4ECitizenServiceNewRequestPresenter extends DocumentTypePresenter<Ps4ECitizenServiceNewRequestView> {

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

    private boolean isDraft = false;

    @Override
    public void afterPrepareModel(String state) {
        // 14.06.2021 - Robert Stefan - CPR - daca utilizatorul nu este logat se verifica daca e un serviciu public
        // disponibil fara cont, altfel ii face redirect in /index
        // serviciile disponibile fara cont se definesc in application.properties pe dmsws.anonymous.services
        String anonymousServices = getAnonymousServices();
        Optional<Integer> currentService = documentTypeId;
        Boolean accessGranted = false;
        List<String> servicesList = new ArrayList<String>();
        if (anonymousServices != null && !anonymousServices.isEmpty()) {
            if (anonymousServices.contains(",")) {
                servicesList = Arrays.asList(anonymousServices.split(","));
            } else {
                servicesList.add(anonymousServices.trim());
            }
        }

        if (!servicesList.isEmpty())
            for (String service : servicesList) {
                if (service.trim().equals(currentService.get().toString().trim())) {
                    accessGranted = true;
                }
            }

        SecurityContext context = SecurityContextHolder.getContext();
        if (!(context.getAuthentication().getPrincipal() instanceof UserWithUserToken) && !accessGranted) {
            // VaadinClientUrlUtil.setTopLocation("/index");
            UI.getCurrent().getPage().executeJs("swalInfoParam($0, $1,$2);", "Vă rugăm să vă autentificați!", getView(), "/index");

            return;
        }


        Boolean isEmpty = true;
        if (getFileId().isPresent()) {
            requestFileId = getFileId();
            isEmpty = false;
            if (isPortalFileEditable() || portalFileNeedChanges() || isDraft(getFileId().get())) {
//                if(getPortalFile().get().getIdUser() == SecurityUtils.getUserId().intValue()) {

                getView().buildDmsSmartForm(getPs4Service().getAttributeLinkListByFileId(getFileId().get()),
                        getPortalFile().get().getDenumireWorkflowStatus());
                getView().setServiceNameAndRegisterPreviousStep(getDocument().get().getDenumire(),
                        getPortalFile().get().getDenumireWorkflowStatus());

//                } else {
//                    getLogger().error("User access portal file  id:\t"+getFileId().get()
//                            +"\tportal file user id:\t"+getPortalFile().get().getIdUser()
//                            +"\t system user id:\t"+SecurityUtils.getUserId());
//                    UI.getCurrent().getPage().executeJs("swalError($0)", "Nu aveti acces la cererea!");
//                }
            } else {
                VaadinClientUrlUtil
                        .setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(getPortalFileParamsMap(),
                                Ps4ECitizenServiceRequestReviewRoute.class));
            }
        } else {

            //daca e atribut de tip GIS creeaza acum fisierul dummy al cererii

            String USE_MAP_TYPE = null;
            try {
                USE_MAP_TYPE = dmswsPS4Service.getSysParam(SecurityUtils.getToken(), "USE_MAP_TYPE").getDescriere();
            } catch (Throwable th) {
            }

            if (USE_MAP_TYPE != null && USE_MAP_TYPE.trim().toLowerCase().equals("qgis")) {
                if (!getFileId().isPresent()) {
                    FileData fileData = new FileData();
                    fileData.setId_document(getDocumentId().get());

                    Integer fileId = getPs4Service().createDummyFileIncepereSolicitareRegistratura(fileData);
                    requestFileId = Optional.ofNullable(fileId);
                    setFileId(Optional.ofNullable(requestFileId.get()));

                }
            }
            //END daca e atribut de tip GIS creeaza acum fisierul dummy al cererii

            ContCurentPortalE contCurentPortalE = SecurityUtils.getContCurentPortalE();

            //TODO after set default save the data ...
            AttributeLinkList all = getPs4Service().getMetadataByDocumentId(getDocumentId().get());
            if (all.getAttributeLink() != null && all.getAttributeLink().size() != 0) {
                isEmpty = false;
                //09.06.2021 - Neata Georgiana - ANRE - am adaugat toate verificarile necesare pentru preluare date din cont sau valoare implicita intr-o singura trecere,
                // pentru a nu mai parcurge de fiecare data toata lista de atribute
                all.getAttributeLink().stream()
                        .forEach(attributeLink -> {
                            List<Lov> lovList;

                            if ((context.getAuthentication().getPrincipal() instanceof UserWithUserToken) && contCurentPortalE.getUserCurent() != null) {

                                //preluare nume din cont daca tip data=NUME
                                if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && attributeLink.getDataType().equalsIgnoreCase("NUME")) {

                                    if (attributeLink.getLovId() != null && !attributeLink.getLovId().equals(0)) {
                                        if (contCurentPortalE.getUserCurent().getIdTert() != null) {
                                            lovList = getPs4Service().getLovList(attributeLink.getLovId()).getLov();
                                            if (contCurentPortalE.getUserCurent().getIdTert() != null) {
                                                attributeLink.setValueForLov(lovList);
                                                attributeLink.setValue(contCurentPortalE.getUserCurent().getIdTert().toString());
                                            }

                                        }
                                    } else {
                                        attributeLink.setValue(contCurentPortalE.getUserCurent().getNumeComplet());
                                    }
                                }

                                //preluare EMAIL din cont daca tip data=EMAIL
                                else if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && attributeLink.getDataType().equalsIgnoreCase("EMAIL")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getEmail());
                                }
                                //preluare TELEFON din cont daca tip data=TELEFON
                                else if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && attributeLink.getDataType().equalsIgnoreCase("TELEFON")) {
                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getTelefon());
                                }
                                //preluare   CNP din cont daca tip data=CNP
                                else if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && attributeLink.getDataType().equalsIgnoreCase("CNP")) {
                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getCnp());
                                }
                                //preluare   CUI din cont daca tip data=CUI
                                else if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && attributeLink.getDataType().equalsIgnoreCase("CUI")) {
                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getCodCui());
                                }
                                //preluare CNP sau CUI din cont daca tip data=CNP_CUI
                                else if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && attributeLink.getDataType().equalsIgnoreCase("CNP_CUI")) {
                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getCodCui() == null || contCurentPortalE.getUserCurent().getCodCui().isEmpty() || contCurentPortalE.getUserCurent().getCodCui().equals("0") ? contCurentPortalE.getUserCurent().getCnp() : contCurentPortalE.getUserCurent().getCodCui());
                                }

                                //preluare RJ din cont daca tip data=RJ
                                else if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && attributeLink.getDataType().equalsIgnoreCase("RJ")) {
                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getRj());
                                }

                                //preluare REPREZENTANT LEGAL din cont daca tip data=REP_LEGAL
                                else if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && attributeLink.getDataType().equalsIgnoreCase("REP_LEGAL")) {
                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getReprezentantLegal());
                                }


                                //preluare localitate din cont daca tip data=ADRESA, LOCATIE sau LOCALITATE
                                else if (Optional.ofNullable(attributeLink.getDataType()).isPresent()
                                        && (attributeLink.getDataType().equalsIgnoreCase("ADRESA")
                                        || attributeLink.getDataType().equalsIgnoreCase("LOCATIE")
                                        || attributeLink.getDataType().equalsIgnoreCase("LOCALITATE")
                                )
                                        ) {
                                    if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
                                        if (contCurentPortalE.getUserCurent().getIdJudet() != null) {
                                            lovList = getPs4Service().getLovList(attributeLink.getLovId()).getLov();

                                            attributeLink.setValueForLov(lovList);
                                            if (contCurentPortalE.getUserCurent().getIdLocalitate() != null) {
                                                attributeLink.setValue(contCurentPortalE.getUserCurent().getIdLocalitate().toString());

                                            }
                                        }
                                    }

                                }
                                //preluare judet din cont daca cod attr=SECTOR_SEDIU_DOMICILIU
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("SECTOR_SEDIU_DOMICILIU")) {
                                    if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
                                        if (contCurentPortalE.getUserCurent().getIdJudet() != null) {

                                            lovList = getPs4Service().getLovList(attributeLink.getLovId()).getLov();
                                            attributeLink.setValueForLov(lovList.stream()
                                                    .filter(bv -> bv.getId().equalsIgnoreCase(contCurentPortalE.getUserCurent().getIdJudet() + "")).collect(Collectors.toList()));
                                        }

                                    }

                                }
                                //preluare strada din cont daca cod attr=SUBSEMNAT_STRADA
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("SUBSEMNAT_STRADA")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getStrada());
                                }

                                //preluare nr strada din cont daca cod attr=SUBSEMNAT_NR
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("SUBSEMNAT_NR")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getNrStrada());
                                }

                                //preluare bloc din cont daca cod attr=SUBSEMNAT_BL
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("SUBSEMNAT_BL")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getBloc());
                                }
                                //preluare scara din cont daca cod attr=SUBSEMNAT_SC
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("SUBSEMNAT_SC")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getScara());
                                }

                                //preluare etaj din cont daca cod attr=SUBSEMNAT_ET
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("SUBSEMNAT_ET")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getEtaj());
                                }
                                //preluare apartament din cont daca cod attr=SUBSEMNAT_AP
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("SUBSEMNAT_AP")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getApartament());
                                }
                                //preluare serie CI din cont daca cod attr=SERIE_CI
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("SERIE_CI")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getSerieAct());
                                }
                                //preluare nr CI din cont daca cod attr=NR_CI
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("NR_CI")) {

                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getNrAct());
                                }
                                //preluare tip CI din cont daca cod attr=CI_BI
                                if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getName().equalsIgnoreCase("CI_BI")) {
                                    if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
                                        lovList = getPs4Service().getLovList(attributeLink.getLovId()).getLov();
                                        if (contCurentPortalE.getUserCurent().getTipAct() != null) {
                                            attributeLink.setValueForLov(lovList.stream()
                                                    .filter(bv -> bv.getValoare().equalsIgnoreCase(contCurentPortalE.getUserCurent().getTipAct())).collect(Collectors.toList()));
                                        }
                                    }

                                }
                                //preluare tara din cont daca cod attr=TARA sau tip data=TARA
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && (attributeLink.getName().equalsIgnoreCase("TARA") || attributeLink.getDataType().equalsIgnoreCase("TARA"))) {
                                    if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
                                        if (contCurentPortalE.getUserCurent().getIdTara() != null) {
                                            lovList = getPs4Service().getLovList(attributeLink.getLovId()).getLov();

                                            attributeLink.setValueForLov(lovList);
                                            attributeLink.setValue(contCurentPortalE.getUserCurent().getIdTara().toString());

                                        }
                                    }
                                }
                                //preluare judet din cont daca cod attr=JUDET sau tip data=JUDET
                                else if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && (attributeLink.getName().equalsIgnoreCase("JUDET") || attributeLink.getDataType().equalsIgnoreCase("JUDET"))) {
                                    if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
                                        if (contCurentPortalE.getUserCurent().getIdJudet() != null) {
                                            lovList = getPs4Service().getLovList(attributeLink.getLovId()).getLov();

                                            attributeLink.setValueForLov(lovList);
                                            attributeLink.setValue(contCurentPortalE.getUserCurent().getIdJudet().toString());

                                        }
                                    }
                                }

                            }

                            if (attributeLink.getValoareImplicita() != null && !attributeLink.getValoareImplicita().isEmpty() && attributeLink.getValoareImplicita().equals("PORTAL_CONTEXT_PERIOADA")) {
                                if (getIdPerioada().isPresent()) {

                                    if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
                                        lovList = getPs4Service().getLovList(attributeLink.getLovId()).getLov();
                                        attributeLink.setValueForLov(lovList);
                                        attributeLink.setValue(getIdPerioada().get().toString());


                                    }

                                }
                            } else if (attributeLink.getValoareImplicita() != null && !attributeLink.getValoareImplicita().isEmpty() && attributeLink.getValoareImplicita().equals("PORTAL_CONTEXT_TERT")) {
                                if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0) {
                                    if (contCurentPortalE.getTertParinteUserCurent().getIdTert() != null && contCurentPortalE.getTertParinteUserCurent().getIdTert() != 0) {

                                        lovList = getPs4Service().getLovListFilteredById(attributeLink.getLovId(), contCurentPortalE.getTertParinteUserCurent().getIdTert().toString()).getLov();

                                        attributeLink.setValueForLov(lovList);
                                        attributeLink.setValue(contCurentPortalE.getTertParinteUserCurent().getIdTert().toString());
                                    } else if (contCurentPortalE.getUserCurent().getIdTert() != null && contCurentPortalE.getUserCurent().getIdTert() != 0) {

                                        lovList = getPs4Service().getLovListFilteredById(attributeLink.getLovId(), contCurentPortalE.getUserCurent().getIdTert().toString()).getLov();

                                        attributeLink.setValueForLov(lovList);
                                        attributeLink.setValue(contCurentPortalE.getUserCurent().getIdTert().toString());
                                    } else if (getIdTert().isPresent()) {


                                        lovList = getPs4Service().getLovListFilteredById(attributeLink.getLovId(), getIdTert().get().toString()).getLov();

                                        attributeLink.setValueForLov(lovList);
                                        attributeLink.setValue(getIdTert().get().toString());
                                    }
                                } else {
                                    attributeLink.setValue(contCurentPortalE.getUserCurent().getNumeComplet());
                                }

                            } else if (attributeLink.getValoareImplicita() != null && !attributeLink.getValoareImplicita().isEmpty() && attributeLink.getValoareImplicita().contains("PORTAL_CONTEXT")) {
                                String sql = attributeLink.getValoareImplicita();
                                if (getIdPerioada().isPresent()) {
                                    sql = sql.replaceAll("PORTAL_CONTEXT_PERIOADA", getIdPerioada().get().toString());
                                }
                                if (getDocumentId().isPresent()) {
                                    sql = sql.replaceAll("PORTAL_CONTEXT_ID_TIP_DOC", getDocumentId().get().toString());
                                }
                                if (getIdTert().isPresent()) {
                                    sql = sql.replaceAll("PORTAL_CONTEXT_TERT", getIdTert().get().toString());
                                } else if (contCurentPortalE.getTertParinteUserCurent().getIdTert() != null) {
                                    sql = sql.replaceAll("PORTAL_CONTEXT_TERT", contCurentPortalE.getTertParinteUserCurent().getIdTert().toString());

                                } else if (contCurentPortalE.getUserCurent().getIdTert() != null) {
                                    sql = sql.replaceAll("PORTAL_CONTEXT_TERT", contCurentPortalE.getUserCurent().getIdTert().toString());

                                }
                                String value = dmswsPS4Service.getSqlResult(sql).getInfo();
                                attributeLink.setValue(value);
                            } else
                                //09.06.2021 - Neata Georgiana - ANRE -  daca exista valoare implicita pentru atribut se seteaza automat
                                //setare valoare implicita pe atribut daca exista
                                if (Optional.ofNullable(attributeLink.getName()).isPresent()
                                        && attributeLink.getValoareImplicita() != null && !attributeLink.getValoareImplicita().isEmpty()) {


                                    String valoareImplicita = attributeLink.getValoareImplicita();
                                    if (valoareImplicita.contains("^")) {
                                        for (AttributeLink attrLink : all.getAttributeLink()) {
                                            if (attrLink.getValue() != null && !attrLink.getValue().isEmpty()) {
                                                String atrName = attrLink.getName();
                                                if (attrLink.getName().contains("|")) {
                                                    atrName = attrLink.getName().replaceAll("\\|", "\\\\\\|");
                                                    // attrLink.setName(attrLink.getName().replaceAll("\\|", "\\\\\\|"));
                                                }

                                                valoareImplicita = valoareImplicita.replaceAll("\\^" + atrName, attrLink.getValue());
                                            }
                                        }
                                        attributeLink.setValoareImplicita(valoareImplicita);

                                        String value = dmswsPS4Service.getSqlResult(attributeLink.getValoareImplicita()).getInfo();
                                        if (value != null && !value.isEmpty()) {
                                            attributeLink.setValue(value);

                                        } else {
                                            attributeLink.setValue(attributeLink.getValoareImplicita());
                                        }
                                    } else {
                                        attributeLink.setValue(valoareImplicita);
                                    }


                                }

                            if (attributeLink.getName().contains("USER|") || attributeLink.getName().contains("TERT|")) {
                                String val = getContCurentInfo(attributeLink, contCurentPortalE);
                                BeanUtil.getBean(SmartFormComponentService.class).addSmartFormComponentsValues(attributeLink, val);
                                if (attributeLink.getLovId() != null && attributeLink.getLovId() != 0 && val != null) {

                                    if (attributeLink.getReadOnly() == null || !attributeLink.getReadOnly()) {
                                        lovList = getPs4Service().getLovList(attributeLink.getLovId()).getLov();
                                        attributeLink.setValueForLov(lovList);
                                        if (val != null && !val.isEmpty())
                                            attributeLink.setValue(val);
                                    } else {
                                        lovList = getPs4Service().getLovListFilteredById(attributeLink.getLovId(), val).getLov();

                                        attributeLink.setValueForLov(lovList);
                                        attributeLink.setValue(val);
                                    }

                                } else {
                                    attributeLink.setValue(val);

                                }
                                if (val != null) {
                                    if (attributeLink.getRand() == null || attributeLink.getRand() == 0.0) {
                                        attrLinkListHidden.add(getConversionService().convert(attributeLink, DocAttrLink.class));
                                    }
                                }

                            }
                            if (getRequestFileid().isPresent() || getFileId().isPresent()) {
                                if (getRequestFileid().isPresent()) {
                                    attributeLink.setIdFisier(getRequestFileid().get());
                                } else if (getFileId().isPresent()) {
                                    attributeLink.setIdFisier(getFileId().get());
                                }
                            }
                        });

            }
            getView().buildDmsSmartForm(all, "");
            getView().setServiceNameAndRegisterPreviousStep(getDocument().get().getDenumire(), "");


        }
        if (!getFromMainScreen().isPresent() || getFromMainScreen().get() == null) {
            getView().buildBreadcrumbsWizard();

        }
        if (!isEmpty) {
            getView().validateSmartForm();

        }
        UI.getCurrent().getPage().executeJavaScript("resetChanges();");

    }

    private ConversionService getConversionService() {
        return getBean(ConversionService.class);
    }

    private String getContCurentInfo(AttributeLink attributeLink, ContCurentPortalE contCurentPortalE) {
        String val = "";
        if (attributeLink.getName().contains("USER|") && contCurentPortalE.getUserCurent() != null) {
            switch (attributeLink.getName()) {
                case "USER|ID_UTILIZATOR":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdUtilizator());
                    break;
                case "USER|NUME":
                    val = contCurentPortalE.getUserCurent().getNume();
                    break;
                case "USER|PRENUME":
                    val = contCurentPortalE.getUserCurent().getPrenume();
                    break;
                case "USER|EMAIL":
                    val = contCurentPortalE.getUserCurent().getEmail();
                    break;
                case "USER|ID_UNITATE":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdUnitate());
                    break;
                case "USER|ID_TERT":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdTert());
                    break;
                case "USER|USERNAME":
                    val = contCurentPortalE.getUserCurent().getUsername();
                    break;
                case "USER|SERIE_ACT":
                    val = contCurentPortalE.getUserCurent().getSerieAct();
                    break;
                case "USER|NR_ACT":
                    val = contCurentPortalE.getUserCurent().getNrAct();
                    break;
                case "USER|CNP":
                    val = contCurentPortalE.getUserCurent().getCnp();
                    break;
                case "USER|CNP2":
                    val = contCurentPortalE.getUserCurent().getCnp();
                    break;
                case "USER|COD_CUI":
                    val = contCurentPortalE.getUserCurent().getCodCui();
                    break;
                case "USER|COD_CUI2":
                    val = contCurentPortalE.getUserCurent().getCodCui();
                    break;
                case "USER|RJ":
                    val = contCurentPortalE.getUserCurent().getRj();
                    break;
                case "USER|TIP_ACT":
                    val = contCurentPortalE.getUserCurent().getTipAct();
                    break;
                case "USER|ID_DEPARTAMENT":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdDepartament());
                    break;
                case "USER|ID_FUNCTIE":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdFunctie());
                    break;
                case "USER|ID_TIP_ACT":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdTipAct());
                    break;
                case "USER|ID_UNITATE_LOGISTICA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdUnitateLogistica());
                    break;
                case "USER|NR_ORE":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getNrOre());
                    break;
                case "USER|NORMA_LUNARA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getNormaLunara());
                    break;
                case "USER|HAS_TICHETE":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getHasTichete());
                    break;
                case "USER|DATA_NASTERE":
                    val = contCurentPortalE.getUserCurent().getDataNastere();
                    break;
                case "USER|DEPARTAMENT":
                    val = contCurentPortalE.getUserCurent().getDepartament();
                    break;
                case "USER|FUNCTIE":
                    val = contCurentPortalE.getUserCurent().getFunctie();
                    break;
                case "USER|UNITATE_LOGISTICA":
                    val = contCurentPortalE.getUserCurent().getUnitateLogistica();
                    break;
                case "USER|NUME_COMPLET":
                    val = contCurentPortalE.getUserCurent().getNumeComplet();
                    break;
                case "USER|ADRESA":
                    val = contCurentPortalE.getUserCurent().getAdresa();
                    break;
                case "USER|NR_PERMIS_CONDUCERE":
                    val = contCurentPortalE.getUserCurent().getNrPermisConducere();
                    break;
                case "USER|MARCA":
                    val = contCurentPortalE.getUserCurent().getMarca();
                    break;
                case "USER|DATA_IN":
                    val = contCurentPortalE.getUserCurent().getDataIn();
                    break;
                case "USER|DATA_OUT":
                    val = contCurentPortalE.getUserCurent().getDataOut();
                    break;
                case "USER|CONTACT":
                    val = contCurentPortalE.getUserCurent().getContact();
                    break;
                case "USER|ID_PERSOANA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdPersoana());
                    break;
                case "USER|COD_POSTAL":
                    val = contCurentPortalE.getUserCurent().getCodPostal();
                    break;
                case "USER|NUME_SUCURSALA":
                    val = contCurentPortalE.getUserCurent().getNumeSucursala();
                    break;
                case "USER|CONT_BANCAR":
                    val = contCurentPortalE.getUserCurent().getContBancar();
                    break;
                case "USER|FAX":
                    val = contCurentPortalE.getUserCurent().getFax();
                    break;
                case "USER|TELEFON":
                    val = contCurentPortalE.getUserCurent().getTelefon();
                    break;
                case "USER|WEB":
                    val = contCurentPortalE.getUserCurent().getWeb();
                    break;
                case "USER|PERSOANA_CONTACT":
                    val = contCurentPortalE.getUserCurent().getPersoanaContact();
                    break;
                case "USER|DOMENIU_DE_ACTIVITATE":
                    val = contCurentPortalE.getUserCurent().getDomeniuActivitate();
                    break;
                case "USER|COD_CAEN":
                    val = contCurentPortalE.getUserCurent().getCodCaen();
                    break;
                case "USER|BANCA":
                    val = contCurentPortalE.getUserCurent().getBanca();
                    break;
                case "USER|COD_IBAN":
                    val = contCurentPortalE.getUserCurent().getCodIban();
                    break;
                case "USER|MAMA":
                    val = contCurentPortalE.getUserCurent().getMama();
                    break;
                case "USER|TATA":
                    val = contCurentPortalE.getUserCurent().getTata();
                    break;
                case "USER|TIP_TERT":
                    val = contCurentPortalE.getUserCurent().getTipTert();
                    break;
                case "USER|NUMAR_AUTORIZATIE":
                    val = contCurentPortalE.getUserCurent().getNumarAutorizatie();
                    break;
                case "USER|NUME_VECHI":
                    val = contCurentPortalE.getUserCurent().getNumeVechi();
                    break;
                case "USER|STRADA":
                    val = contCurentPortalE.getUserCurent().getStrada();
                    break;
                case "USER|NUMAR":
                    val = contCurentPortalE.getUserCurent().getNrStrada();
                    break;
                case "USER|BLOC":
                    val = contCurentPortalE.getUserCurent().getBloc();
                    break;
                case "USER|SCARA":
                    val = contCurentPortalE.getUserCurent().getScara();
                    break;
                case "USER|ETAJ":
                    val = contCurentPortalE.getUserCurent().getEtaj();
                    break;
                case "USER|APARTAMENT":
                    val = contCurentPortalE.getUserCurent().getApartament();
                    break;
                case "USER|PERS_FIZ":
                    val = contCurentPortalE.getUserCurent().getEstePersoanaFizica();
                    break;
                case "USER|REPREZ_LEGAL":
                    val = contCurentPortalE.getUserCurent().getReprezentantLegal();
                    break;
                case "USER|REPREZ_LEGAL2":
                    val = contCurentPortalE.getUserCurent().getReprezentantLegal();
                    break;
                case "USER|CLIENT":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getClient());
                    break;
                case "USER|FURNIZOR":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getFurnizor());
                    break;
                case "USER|PFA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getPfa());
                    break;
                case "USER|PLATESTE_TVA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getPlatesteTva());
                    break;
                case "USER|COD_FISCAL":
                    val = contCurentPortalE.getUserCurent().getCodFiscal();
                    break;
                case "USER|ID_LOCALITATE":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdLocalitate());
                    break;
                case "USER|ID_JUDET":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdJudet());
                    break;
                case "USER|ID_TARA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdTara());
                    break;
                case "USER|ID_FILIALA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdFiliala());
                    break;
                case "USER|ID_JUDET_FILIALA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdJudetFiliala());
                    break;
                case "USER|ID_LOCALITATE_FILIALA":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getIdLocalitateFiliala());
                    break;
                case "USER|COD_RUP":
                    val = String.valueOf(contCurentPortalE.getUserCurent().getCodRup());
                    break;
            }
        } else if (attributeLink.getName().contains("TERT|")) {
            switch (attributeLink.getName()) {
                case "TERT|ID_UTILIZATOR":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdUtilizator());
                    break;
                case "TERT|NUME":
                    val = contCurentPortalE.getTertParinteUserCurent().getNume();
                    break;
                case "TERT|PRENUME":
                    val = contCurentPortalE.getTertParinteUserCurent().getPrenume();
                    break;
                case "TERT|EMAIL":
                    val = contCurentPortalE.getTertParinteUserCurent().getEmail();
                    break;
                case "TERT|ID_UNITATE":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdUnitate());
                    break;
                case "TERT|ID_TERT":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdTert());
                    break;
                case "TERT|USERNAME":
                    val = contCurentPortalE.getTertParinteUserCurent().getUsername();
                    break;
                case "TERT|SERIE_ACT":
                    val = contCurentPortalE.getTertParinteUserCurent().getSerieAct();
                    break;
                case "TERT|NR_ACT":
                    val = contCurentPortalE.getTertParinteUserCurent().getNrAct();
                    break;
                case "TERT|CNP":
                    val = contCurentPortalE.getTertParinteUserCurent().getCnp();
                    break;
                case "TERT|CNP2":
                    val = contCurentPortalE.getTertParinteUserCurent().getCnp();
                    break;
                case "TERT|COD_CUI":
                    val = contCurentPortalE.getTertParinteUserCurent().getCodCui();
                    break;
                case "TERT|COD_CUI2":
                    val = contCurentPortalE.getTertParinteUserCurent().getCodCui();
                    break;
                case "TERT|RJ":
                    val = contCurentPortalE.getTertParinteUserCurent().getRj();
                    break;
                case "TERT|TIP_ACT":
                    val = contCurentPortalE.getTertParinteUserCurent().getTipAct();
                    break;
                case "TERT|ID_DEPARTAMENT":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdDepartament());
                    break;
                case "TERT|ID_FUNCTIE":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdFunctie());
                    break;
                case "TERT|ID_TIP_ACT":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdTipAct());
                    break;
                case "TERT|ID_UNITATE_LOGISTICA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdUnitateLogistica());
                    break;
                case "TERT|NR_ORE":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getNrOre());
                    break;
                case "TERT|NORMA_LUNARA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getNormaLunara());
                    break;
                case "TERT|HAS_TICHETE":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getHasTichete());
                    break;
                case "TERT|DATA_NASTERE":
                    val = contCurentPortalE.getTertParinteUserCurent().getDataNastere();
                    break;
                case "TERT|DEPARTAMENT":
                    val = contCurentPortalE.getTertParinteUserCurent().getDepartament();
                    break;
                case "TERT|FUNCTIE":
                    val = contCurentPortalE.getTertParinteUserCurent().getFunctie();
                    break;
                case "TERT|UNITATE_LOGISTICA":
                    val = contCurentPortalE.getTertParinteUserCurent().getUnitateLogistica();
                    break;
                case "TERT|NUME_COMPLET":
                    val = contCurentPortalE.getTertParinteUserCurent().getNumeComplet();
                    break;
                case "TERT|ADRESA":
                    val = contCurentPortalE.getTertParinteUserCurent().getAdresa();
                    break;
                case "TERT|NR_PERMIS_CONDUCERE":
                    val = contCurentPortalE.getTertParinteUserCurent().getNrPermisConducere();
                    break;
                case "TERT|MARCA":
                    val = contCurentPortalE.getTertParinteUserCurent().getMarca();
                    break;
                case "TERT|DATA_IN":
                    val = contCurentPortalE.getTertParinteUserCurent().getDataIn();
                    break;
                case "TERT|DATA_OUT":
                    val = contCurentPortalE.getTertParinteUserCurent().getDataOut();
                    break;
                case "TERT|CONTACT":
                    val = contCurentPortalE.getTertParinteUserCurent().getContact();
                    break;
                case "TERT|ID_PERSOANA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdPersoana());
                    break;
                case "TERT|COD_POSTAL":
                    val = contCurentPortalE.getTertParinteUserCurent().getCodPostal();
                    break;
                case "TERT|NUME_SUCURSALA":
                    val = contCurentPortalE.getTertParinteUserCurent().getNumeSucursala();
                    break;
                case "TERT|CONT_BANCAR":
                    val = contCurentPortalE.getTertParinteUserCurent().getContBancar();
                    break;
                case "TERT|FAX":
                    val = contCurentPortalE.getTertParinteUserCurent().getFax();
                    break;
                case "TERT|TELEFON":
                    val = contCurentPortalE.getTertParinteUserCurent().getTelefon();
                    break;
                case "TERT|WEB":
                    val = contCurentPortalE.getTertParinteUserCurent().getWeb();
                    break;
                case "TERT|PERSOANA_CONTACT":
                    val = contCurentPortalE.getTertParinteUserCurent().getPersoanaContact();
                    break;
                case "TERT|DOMENIU_DE_ACTIVITATE":
                    val = contCurentPortalE.getTertParinteUserCurent().getDomeniuActivitate();
                    break;
                case "TERT|COD_CAEN":
                    val = contCurentPortalE.getTertParinteUserCurent().getCodCaen();
                    break;
                case "TERT|BANCA":
                    val = contCurentPortalE.getTertParinteUserCurent().getBanca();
                    break;
                case "TERT|COD_IBAN":
                    val = contCurentPortalE.getTertParinteUserCurent().getCodIban();
                    break;
                case "TERT|MAMA":
                    val = contCurentPortalE.getTertParinteUserCurent().getMama();
                    break;
                case "TERT|TATA":
                    val = contCurentPortalE.getTertParinteUserCurent().getTata();
                    break;
                case "TERT|TIP_TERT":
                    val = contCurentPortalE.getTertParinteUserCurent().getTipTert();
                    break;
                case "TERT|NUMAR_AUTORIZATIE":
                    val = contCurentPortalE.getTertParinteUserCurent().getNumarAutorizatie();
                    break;
                case "TERT|NUME_VECHI":
                    val = contCurentPortalE.getTertParinteUserCurent().getNumeVechi();
                    break;
                case "TERT|STRADA":
                    val = contCurentPortalE.getTertParinteUserCurent().getStrada();
                    break;
                case "TERT|NUMAR":
                    val = contCurentPortalE.getTertParinteUserCurent().getNrStrada();
                    break;
                case "TERT|BLOC":
                    val = contCurentPortalE.getTertParinteUserCurent().getBloc();
                    break;
                case "TERT|SCARA":
                    val = contCurentPortalE.getTertParinteUserCurent().getScara();
                    break;
                case "TERT|ETAJ":
                    val = contCurentPortalE.getTertParinteUserCurent().getEtaj();
                    break;
                case "TERT|APARTAMENT":
                    val = contCurentPortalE.getTertParinteUserCurent().getApartament();
                    break;
                case "TERT|PERS_FIZ":
                    val = contCurentPortalE.getTertParinteUserCurent().getEstePersoanaFizica();
                    break;
                case "TERT|REPREZ_LEGAL":
                    val = contCurentPortalE.getTertParinteUserCurent().getReprezentantLegal();
                    break;
                case "TERT|REPREZ_LEGAL2":
                    val = contCurentPortalE.getTertParinteUserCurent().getReprezentantLegal();
                    break;
                case "TERT|CLIENT":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getClient());
                    break;
                case "TERT|FURNIZOR":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getFurnizor());
                    break;
                case "TERT|PFA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getPfa());
                    break;
                case "TERT|PLATESTE_TVA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getPlatesteTva());
                    break;
                case "TERT|COD_FISCAL":
                    val = contCurentPortalE.getTertParinteUserCurent().getCodFiscal();
                    break;
                case "TERT|ID_LOCALITATE":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdLocalitate());
                    break;
                case "TERT|ID_JUDET":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdJudet());
                    break;
                case "TERT|ID_TARA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdTara());
                    break;
                case "TERT|ID_FILIALA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdFiliala());
                    break;
                case "TERT|ID_JUDET_FILIALA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdJudetFiliala());
                    break;
                case "TERT|ID_LOCALITATE_FILIALA":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getIdLocalitateFiliala());
                    break;
                case "TERT|COD_RUP":
                    val = String.valueOf(contCurentPortalE.getTertParinteUserCurent().getCodRup());
                    break;
            }
        }
        return val;
    }


    public void onPreviousBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        isDraft = false;
        getLogger().info("filter previous button");
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        filterPageParameters.put("document", getDocumentId().get());
        filterPageParameters.put("idUser", SecurityUtils.getUserId());
        filterPageParameters.put("token", SecurityUtils.getToken());

        if (getFromMainScreen().isPresent()) {
            if (getFromMainScreen().get().contains("VIEW")) {

                UI.getCurrent().getPage().executeJavaScript("resetChanges();");
            }
            //btn inapoi to main screen operator

            UI.getCurrent().getPage().executeJavaScript("  window.history.back();");

        } else {

            VaadinClientUrlUtil.setLocation(QueryParameterUtil
                    .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestRoute.class));
        }
    }

    public void onNextBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        isDraft = false;

        if (getView().validateSmartForm())

        {
            //06.01.2022 - CA -ANRE -  swal de loading
            UI.getCurrent().getPage().executeJs("window.parent.parent.scrollTo(0, 0);");
            UI.getCurrent().getPage().executeJs("displayLoadingSpinner();").then(Integer.class, value -> nextStep(clickEvent));

        } else {
            UI.getCurrent().getPage().executeJs("swal.close()");
            UI.getCurrent().getPage().executeJs("swalError($0)", I18NProviderStatic.getTranslation("ps4.ecetatean.form.swal.error"));
            return;
        }

    }

    public void onSaveDraftAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        isDraft = true;
        UI.getCurrent().getPage().executeJs("window.parent.parent.scrollTo(0, 0);");
        UI.getCurrent().getPage().executeJs("displayLoadingSpinner();").then(Integer.class, value -> saveDraft(clickEvent));

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
                if(isDraft){
                    VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenMyDraftRequestsRoute.class));
                }else {
                    if (listaDoc == null || listaDoc.size() == 0) {
                        VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class));
                    } else {
                        VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceAttacheFileRoute.class));
                    }
                }
            }

        } catch (Throwable e) {


            logger.error(e.getMessage(), e);

            UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare server DMSWS! Va rugam reincercati!");
        }
//        logger.debug(pdf.toJson());
    }

    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }

    public void setGoogleMapsApiKey(String googleMapsApiKey) {
        this.googleMapsApiKey = googleMapsApiKey;
    }

    public String getAnonymousServices() {
        return anonymousServices;
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

    public Integer nextStep(ClickEvent<ClickNotifierAnchor> clickEvent) {

        UI.getCurrent().getPage().executeJavaScript("resetChanges();");
        getLogger().info("on lanseaza comanda");
        Map<String, Object> filterPageParameters = new HashMap<>();
        FileData fileData = new FileData();
        fileData.setId_document(getDocumentId().get());
        filterPageParameters.put("idUser", SecurityUtils.getUserId());
        filterPageParameters.put("token", SecurityUtils.getToken());
        if (!docAttrLinkList.isPresent()) {
            docAttrLinkList = Optional.of(getView().getDocAttrLinkList());
        }


//            Map<String, Object> filterPageParameters = new HashMap<>();
//            filterPageParameters.put("tipDocument", getDocumentType());
//            filterPageParameters.put("document", getDocumentId().get());
        try {
            if (docAttrLinkList.get().getDocAttrLink().size() > 0) {
                if (!getFileId().isPresent()) {
                    //TODO
//                        getPs4Service().replaceExistingFileAndSetMetadata()
                    Integer fileId = getPs4Service().createDummyFileIncepereSolicitareRegistratura(fileData);
                    requestFileId = Optional.ofNullable(fileId);
//                        filterPageParameters.put("request", fileId);
                    if (attrLinkListHidden != null && attrLinkListHidden.size() != 0) {
                        docAttrLinkList.get().getDocAttrLink().addAll(attrLinkListHidden);
                    }
                    getPs4Service().saveDocAttribute(fileId, docAttrLinkList.get());
                    FileOb fileOb = new FileOb();
                    fileOb.setFileId(fileId);

                    if (getDocumentType().equals(classReabilitareId)) {
                        getPs4Service().createProiectFromCerere(fileId, fileOb);

                    }
                } else {
                    requestFileId = getFileId();
//                        filterPageParameters.put("request", getFileId().get());
                    getPs4Service().saveDocAttribute(getFileId().get(), docAttrLinkList.get());
                }
                getView().printSmartFormPdf();
                UI.getCurrent().getPage().executeJs("swal.close()");
                UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

                return 0;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            UI.getCurrent().getPage().executeJs("swal.close()");
            UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare server DMSWS! Va rugam reincercati!");
            UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

            return 1;
        }

        UI.getCurrent().getPage().executeJs("swal.close()");
        UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

        return 0;
    }

    public Integer saveDraft(ClickEvent<ClickNotifierAnchor> clickEvent) {

        UI.getCurrent().getPage().executeJavaScript("resetChanges();");
        getLogger().info("on lanseaza comanda");
        Map<String, Object> filterPageParameters = new HashMap<>();
        FileData fileData = new FileData();
        fileData.setId_document(getDocumentId().get());
        filterPageParameters.put("idUser", SecurityUtils.getUserId());
        filterPageParameters.put("token", SecurityUtils.getToken());
        if (!docAttrLinkList.isPresent()) {
            docAttrLinkList = Optional.of(getView().getDocAttrLinkList());
        }

        try {
            if (docAttrLinkList.get().getDocAttrLink().size() > 0) {
                if (!getFileId().isPresent()) {
                    Integer fileId = getPs4Service().createDummyFileIncepereSolicitareRegistratura(fileData);
                    requestFileId = Optional.ofNullable(fileId);
                    if (attrLinkListHidden != null && attrLinkListHidden.size() != 0) {
                        docAttrLinkList.get().getDocAttrLink().addAll(attrLinkListHidden);
                    }
                    getPs4Service().saveDocAttribute(fileId, docAttrLinkList.get());
                    FileOb fileOb = new FileOb();
                    fileOb.setFileId(fileId);

                    if (getDocumentType().equals(classReabilitareId)) {
                        getPs4Service().createProiectFromCerere(fileId, fileOb);
                    }
                } else {
                    requestFileId = getFileId();
                    getPs4Service().saveDocAttribute(getFileId().get(), docAttrLinkList.get());
                }
                if(requestFileId.isPresent()){
                    fileService.saveDraft(SecurityUtils.getToken(), requestFileId.get());
                }
                getView().printSmartFormPdf();
                UI.getCurrent().getPage().executeJs("swal.close()");
                UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

                return 0;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            UI.getCurrent().getPage().executeJs("swal.close()");
            UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare server DMSWS! Va rugam reincercati!");
            UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

            return 1;
        }

        UI.getCurrent().getPage().executeJs("swal.close()");
        UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

        return 0;
    }
}
