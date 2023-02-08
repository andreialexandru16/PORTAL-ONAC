package ro.bithat.dms.smartform.gui.attribute;
//09.06.2021 - Neata Georgiana - ANRE - am adaugat  constructor pentru a permite validare camp numeric cu numar de zecimale specificat
//11.06.2021 - Neata Georgiana - ANRE - setam valoare "" la creare rand nou tabel pentru a nu prelua informatiile completate pe randul anterior
//22.06.2021 # Neata Georgiana # ANRE #  adaugat atrComplexReadonly; daca e pus pe true=> nu afisez coloana de actiune (adauga rand); ex utilizare in pagina de revizie finala
//22.06.2021 - Neata Georgiana - ANRE - afisare atribut adauga rand la stanga
//21.07.2021 - Neata Georgiana - ANRE - Verificare daca sunt completate corect celulele; Daca nu, intoarce mesaj si nu salveaza randul

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.DateRangeValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.vaadin.gatanaso.MultiselectComboBox;
import org.vaadin.textfieldformatter.NumeralFieldFormatter;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.*;
import ro.bithat.dms.microservices.dmsws.metadata.*;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponseXml;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocObligatoriu;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.ComplexTableComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.SpelParserUtil;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.DocumentaSmartForm;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormComponentService;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import ro.bithat.dms.smartform.gui.attribute.binder.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.bithat.dms.boot.BeanUtil.getBean;

@SpringComponent
@UIScope
public class ComplexAttributeLinkComponent extends AttributeLinkGenericComponent {
    private final static Logger logger = LoggerFactory.getLogger(VaadinClientUrlUtil.class);

    private final Integer NR_ROWS_TABEL=10;
    private final Integer NR_MAX_PAGES_TABEL=8;

    @Autowired
    private DmswsPS4Service dmswsPS4Service;
    @Value("${portal.show.table.buttons}")
    private String showTabelButtons;
    @Value("${portal.max.file.size:100000000}") //100MB
    private Integer maxFileSize;
    @Value("${portal.show.table.buttons.xml.import:false}")
    private String showTabelButtonsXmlImport;
    @Value("${portal.show.table.buttons.xml.export:false}")
    private String showTabelButtonsXmlExport;

    @Autowired
    private SmartFormComponentService smartFormComponentService;

    //map cu nr rand si atributele pentru un rand al atributului complex
    HashMap<Integer, List<RowAttrComplexList>> mapAtributeComplexe = new HashMap<>();
    //index general formular
    AtomicInteger index = new AtomicInteger(1);
    //lista atribute importate
    AttributeLinkListOfLists listaImportata = new AttributeLinkListOfLists();
    //lista tabele formular
    List<ComplexTableComponent> listaTabele = new ArrayList<>();
    //map cu index rand +  map componenta&atribut aferent fiecarei celule
    HashMap<Integer, HashMap<Component, AttributeLink>> mapComponenteRandAtribute = new HashMap<>();
    //map id atribut & index rand
    HashMap<Integer, Integer> mapIdAtrComplexRowIndexes = new HashMap<>();
    HashMap<Integer, List<Lov>> mapLovuri = new HashMap<>();

    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.COMPLEX);
    }


    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attrComplex, Component layout) {
        mapLovuri = new HashMap<>();
        String smartFormId = ((Component) smartForm).getId().get();
        //se preiau toate atributele atributului complex
        AttributeLinkList attrLinkList = BeanUtil.getBean(PS4Service.class).getMetadataByDocumentId(attrComplex.getIdDocumentSelectie());
        attrLinkList.setAttributeLink(attrLinkList.getAttributeLink().stream().filter(a -> a.getHidden()==null || a.getHidden().equals(false)).collect(Collectors.toList()));
        List<AttributeLink> listaAtributeAtrComplex = attrLinkList.getAttributeLink();
        //creem componenta de tip tabel si o adaugam in lista de tabele
        ComplexTableComponent component = new ComplexTableComponent();
        listaTabele.add(component);

        //1. Cazul in care primim lista de linii pentru atibutul complex  (ex: la redeschidere cerere SAU revizie finala)
        if (attrComplex.getAttrsOfComplex() != null) {
            //adaugam label cu Nr. randuri tabel
            Label labelNrRanduriTabel = new Label("Nr. randuri: " + String.valueOf(attrComplex.getAttrsOfComplex().size()));
            labelNrRanduriTabel.setId("label_nr_rows_" + attrComplex.getAttributeId());
            component.add(labelNrRanduriTabel);
        }
        //2. Cazul in nu exista lista de linii pentru atibutul complex  (ex: cerere noua)

        else {
            //adaugam label cu Nr. randuri tabel = 0
            Label labelNrRanduriTabel = new Label("Nr. randuri: 0");
            labelNrRanduriTabel.setId("label_nr_rows_" + attrComplex.getAttributeId());
            component.add(labelNrRanduriTabel);

        }
        //daca atributul complex are atribute simple asociate -> start desenare tabel
        if (listaAtributeAtrComplex != null) {
            component.addClassName("table-responsive");
            component.addClassName("table-atr-complex");


            //daca parametrul din application properties este true afisam butoanele export/import tabel
            if (showTabelButtons.equals("true") && (SmartFormSupport.getIsReadonly() == null || !SmartFormSupport.getIsReadonly())) {
                Optional<String> requestFileId = QueryParameterUtil.getQueryParameter("request", String.class);
                //buton de download excel
                ClickNotifierAnchor exportXls = new ClickNotifierAnchor();
                UI.getCurrent().getPage();
                exportXls.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm-s", "btn_tooltip", "tooltip_chat", "tooltip_left");
                HtmlContainer exportXlsIcon = new HtmlContainer("i");
                exportXlsIcon.addClassNames("fa", "fa-download");
                exportXls.setTitle("Descarcă template excel");
                exportXls.getElement().setAttribute("data-toggle", "tooltip");
                exportXls.add(exportXlsIcon);

                //adaugare tag span - tootlip
                HtmlContainer exportXlsSpan = new HtmlContainer("span");
                exportXlsSpan.add(new Text("document.type.service.request.view.atribut.complex.option.download.label"));
                exportXlsSpan.addClassNames("tooltiptext");
                exportXlsIcon.add(exportXlsSpan);

                exportXls.getStyle().set("color", "white");
                exportXls.addClickListener(e
                        -> {
                    UI.getCurrent().getPage().executeJavaScript("resetChanges();");
                    VaadinClientUrlUtil.setLocation(BeanUtil.getBean(PS4Service.class).exportXls(attrComplex.getIdDocumentSelectie()));

                });
                exportXls.getStyle().set("margin", "5px");

                //buton de import excel
                ClickNotifierAnchor uploadBtn = new ClickNotifierAnchor();
                uploadBtn.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm-s", "btn_tooltip", "tooltip_chat", "tooltip_left");
                uploadBtn.setHref("javascript:void(0)");
                HtmlContainer uploadIcon = new HtmlContainer("i");
                uploadIcon.addClassNames("fa", "fa-upload");

                //adaugare tag span - tootlip
                uploadBtn.add(uploadIcon);
                HtmlContainer importXlsSpan = new HtmlContainer("span");
                importXlsSpan.add(new Text("document.type.service.request.view.atribut.complex.option.upload.label"));
                importXlsSpan.addClassNames("tooltiptext");
                uploadIcon.add(importXlsSpan);

                MemoryBuffer fileBuffer = new MemoryBuffer();
                Upload upload = new Upload(fileBuffer);
                upload.setId("upload-for-");
                upload.addClassName("upload-vaadin-no-file-list");
                upload.setUploadButton(uploadBtn);
                upload.setDropAllowed(false);
                upload.setMaxFiles(1);
                upload.setAcceptedFileTypes(".xlsx");
                upload.addClassName("upload-no-info");

                Div uploadL = new Div(exportXls, upload);
                uploadL.addClassNames("box_btn_import_export");
                uploadL.getStyle().set("display", "inline-block");
                upload.getStyle().set("margin", "5px");
                upload.addProgressListener(e -> {
                    UI.getCurrent().getPage().executeJs("displayLoadingSpinner();\r\n toggleDisplayState($0,$1);", "v-system-error", "none");
                    UI.getCurrent().getPage().executeJs(" swalInfo($0);","Va rugam asteptati. Se proceseaza fisierul.");

                });
                upload.addFinishedListener(e -> {
                    UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

                    byte[] fisier = new byte[0];
                    try {

                        fisier = IOUtils.toByteArray(fileBuffer.getInputStream());

                        listaImportata = BeanUtil.getBean(PS4Service.class).importXls(fisier, attrComplex.getIdDocumentSelectie());

                        if (listaImportata.getExtendedInfo2() == null || listaImportata.getExtendedInfo2().equals("")) {
                            if (listaImportata.getAttributeLinklist().size() == 0) {
                                UI.getCurrent().getPage().executeJs(" swalErrorTop($0);", I18NProviderStatic.getTranslation("import.excel.file"));

                            } else {
                                for (AttributeLinkList al : listaImportata.getAttributeLinklist()) {
                                    RowAttrComplexList row= new RowAttrComplexList();
                                    List<DocAttrLink> listaDocAttr = getConversionService().convert(al, DocAttrLinkList.class).getDocAttrLink();
                                    row.setListaAtribute(listaDocAttr);
                                    row.setRowNumber(index.getAndIncrement());
                                    addNewRowAttrEditable(component, listaAtributeAtrComplex, smartFormId, row, attrComplex,Optional.ofNullable("IMPORT"));

                                }
                            }
                            UI.getCurrent().getPage().executeJs(" swal.close()");

                        } else {
                            UI.getCurrent().getPage().executeJs(" swalErrorTop($0);", listaImportata.getExtendedInfo2());
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                });

                component.add(uploadL);

            }
            Div uploadLXml = new Div();
            //daca parametrul din application properties este true afisam butoanele export/import tabel
            if (showTabelButtonsXmlExport.equals("true") && (SmartFormSupport.getIsReadonly() == null || !SmartFormSupport.getIsReadonly())) {


                //buton de download xml
                ClickNotifierAnchor exportXml = new ClickNotifierAnchor();
                UI.getCurrent().getPage();
                exportXml.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm-s", "btn_tooltip", "tooltip_chat", "tooltip_left");
                HtmlContainer exportXmlIcon = new HtmlContainer("i");
                exportXmlIcon.addClassNames("fas", "fa-file-download");
                exportXml.setTitle("Descarcă instructiuni");
                exportXml.getElement().setAttribute("data-toggle", "tooltip");
                exportXml.add(exportXmlIcon);

                //adaugare tag span - tootlip
                HtmlContainer exportXmlSpan = new HtmlContainer("span");
                exportXmlSpan.add(new Text("document.type.service.request.view.atribut.complex.option.download.label.xml"));
                exportXmlSpan.addClassNames("tooltiptext");
                exportXmlIcon.add(exportXmlSpan);

                exportXml.getStyle().set("color", "white");
                exportXml.setTarget("_blank");
                if(attrComplex.getTemplateFileXml()!=null){
                    exportXml.setHref(StreamResourceUtil.getStreamResource(
                            attrComplex.getTemplateFileXmlNume(),
                            attrComplex.getTemplateFileXml()));
                    uploadLXml.add(exportXml);

                }else{
                    //UI.getCurrent().getPage().executeJs(" swalInfo($0);", I18NProviderStatic.getTranslation("document.type.service.request.view.atribut.complex.option.download.label.empty"));

                }


                exportXml.getStyle().set("margin", "5px");
            }

            //daca parametrul din application properties este true afisam butoanele export/import tabel
            if (showTabelButtonsXmlImport.equals("true")
                    && (attrComplex.getShowImportXml()!=null && attrComplex.getShowImportXml().equals(1) )
                    && (SmartFormSupport.getIsReadonly() == null || !SmartFormSupport.getIsReadonly())) {

                //buton de import XML
                ClickNotifierAnchor uploadBtnXml = new ClickNotifierAnchor();
                uploadBtnXml.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm-s", "btn_tooltip", "tooltip_chat", "tooltip_left");
                uploadBtnXml.setHref("javascript:void(0)");
                HtmlContainer uploadIconXml = new HtmlContainer("i");
                uploadIconXml.addClassNames("fas", "fa-file-import");

                //adaugare tag span - tootlip
                uploadBtnXml.add(uploadIconXml);
                HtmlContainer importXmlSpan = new HtmlContainer("span");
                importXmlSpan.add(new Text("document.type.service.request.view.atribut.complex.option.upload.xml.label"));
                importXmlSpan.addClassNames("tooltiptext");
                uploadIconXml.add(importXmlSpan);

                MemoryBuffer fileBufferXml = new MemoryBuffer();
                Upload uploadXml = new Upload(fileBufferXml);
                uploadXml.setId("upload-for-");
                uploadXml.addClassName("upload-vaadin-no-file-list");
                uploadXml.setUploadButton(uploadBtnXml);
                uploadXml.setDropAllowed(false);
                uploadXml.setMaxFiles(1);
                uploadXml.setAcceptedFileTypes(".xml");
                uploadXml.addClassName("upload-no-info");

                uploadLXml.add(uploadXml);

                uploadLXml.addClassNames("box_btn_import_export");
                uploadLXml.getStyle().set("display", "inline-block");
                uploadXml.getStyle().set("margin", "5px");
                uploadXml.addProgressListener(e -> {
                    UI.getCurrent().getPage().executeJs(" swalInfo($0); displayLoadingSpinner();\r\n toggleDisplayState($1,$2);", "Va rugam asteptati. Se proceseaza fisierul.","v-system-error", "none");

                });
                uploadXml.addFinishedListener(e -> {
                    UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");

                });
                uploadXml.addSucceededListener(e -> {
                    UI.getCurrent().getPage().executeJs(" swalInfoWithoutBtn($0); ", "Va rugam asteptati. Se proceseaza fisierul.").then(Void.class,value ->loadFileXmlImported(e,fileBufferXml,attrComplex,component,listaAtributeAtrComplex,smartFormId));
                });


                //END buton de import XML
            }

            component.add(uploadLXml);
            // Adaugam butonul de tip + pentru inserare rand nou tabel
            ClickNotifierAnchor anchorAdd = new ClickNotifierAnchor();
            HtmlContainer iconMessageAdd = new HtmlContainer("i");

            //22.06.2021 # Neata Georgiana # ANRE #daca atributele complexe nu sunt readonly (ex in pagina de revizie finala) afisam coloana de Actiune (adauga rand nou)
            try {
                if (((DocumentaSmartForm) smartForm).getAtrComplexReadonly() == null || !((DocumentaSmartForm) smartForm).getAtrComplexReadonly()) {
                    //22.06.2021 - Neata Georgiana - ANRE - afisare atribut adauga rand la stanga
                    //implicit adaugam header Adauga
                    component.addHeader("Adauga rand");
                    iconMessageAdd.addClassNames("fas", "fa-plus");
                    anchorAdd.add(iconMessageAdd);

                    //la click pe buton + => se adauga rand nou
                    anchorAdd.addClickListener(e -> addRowIfMaxSizeNotReached(component, attrLinkList, smartFormId, attrComplex, index));
                }
            } catch (Exception e) {

            }

            //1. Cazul in care nu exista randuri pentru atributul complex (ex cerere noua)
            if (attrComplex.getAttrsOfComplex() == null) {
                //implicit adaugam un rand gol pentru a situa pe el butonul de inserare rand
                addNewRowAttrInitial(component, listaAtributeAtrComplex, smartFormId, attrComplex, anchorAdd);
                //daca atributul complex are selectata optiunea de total -> adaugam rand de tip footer
                if (attrComplex.getTotal() != null) {
                    addNewRowAttrFooter(component, listaAtributeAtrComplex, smartFormId, attrComplex);
                }
                //daca atributul complex are atribut lov cu precompletare -> se precompleteaza tabelul conform valorilor din lov
                addNewRowAttrPrecompletat(component, listaAtributeAtrComplex, smartFormId, attrComplex);
            }
            //2.  Cazul in care primim lista de linii pentru atibutul complex  (ex: la redeschidere cerere SAU revizie finala)
            else {
                //2.1 Caz in care formular e readonly (ex revizie finala)
                if (SmartFormSupport.getIsReadonly() != null && SmartFormSupport.getIsReadonly()) {
                    //adaugam randurile cu toate celulele readonly
                    if (attrComplex.getTotal() != null) {
                        component.addHeader("");
                    }

                    for (RowAttrComplexList row : attrComplex.getAttrsOfComplex()) {
                        addNewRowAttrReadonly(component, listaAtributeAtrComplex, smartFormId, row,attrComplex);
                    }
                    //daca atributul complex are selectata optiunea de total -> adaugam rand de tip footer
                    if (attrComplex.getTotal() != null) {
                        //adaugam rand cu toate totalurile = 0
                        addNewRowAttrFooter(component, listaAtributeAtrComplex, smartFormId, attrComplex);

                        //calculam valorile de total si facem update la toate celulele
                        updateAttrComplexFooter(component, listaAtributeAtrComplex, smartFormId, attrComplex);

                    }
                }
                //2.2 Caz in care formularul NU e readonly (ex cerere noua)
                else {
                    //implicit adaugam un rand gol pe care pozitionam butonul de inserare rand
                    addNewRowAttrInitial(component, listaAtributeAtrComplex, smartFormId, attrComplex, anchorAdd);

                    //preluam doar primele N randuri din atributul complex
                    List<RowAttrComplexList> rowList= getFirstNRows( attrComplex.getAttrsOfComplex(),NR_ROWS_TABEL,0);

                    //parcurgem fiecare rand al atributului complex, daca exista

                    for (RowAttrComplexList row : rowList) {
                        //adaugam rand EDITABIL

                        addNewRowAttrEditable(component, listaAtributeAtrComplex, smartFormId, row, attrComplex, Optional.ofNullable("NEW_ROW"));
                        //resetare index general
                        if (index.get() <= row.getRowNumber()) {
                            index.set(row.getRowNumber() + 1);
                        }

                    }
                    //daca atributul complex are selectata optiunea de total -> adaugam rand de tip footer
                    if (attrComplex.getTotal() != null) {
                        //adaugam rand cu toate totalurile = 0
                        addNewRowAttrFooter(component, listaAtributeAtrComplex, smartFormId, attrComplex);
                        //calculam valorile de total si facem update la toate celulele
                        updateAttrComplexFooter(component, listaAtributeAtrComplex, smartFormId, attrComplex);

                    }
                    addNewRowPaginare(component, listaAtributeAtrComplex, smartFormId, attrComplex);

                    //adaugam in map id atribut complex si lista de linii
                    mapAtributeComplexe.put(attrComplex.getAttributeId(), attrComplex.getAttrsOfComplex());


                }

            }
            mapAtributeComplexe.put(attrComplex.getAttributeId(), attrComplex.getAttrsOfComplex());
            smartFormComponentService.addAttrListForAttributeComplex(smartFormId, attrComplex, attrComplex.getAttrsOfComplex());
            smartFormComponentService.setMapComponenteRandAtribute(mapComponenteRandAtribute);
            //se adauga header pentru fiecare atribut al atributului complex
            for (AttributeLink at : listaAtributeAtrComplex) {
                //se adauga doar daca atributul nu are bifa de ascuns (hidden)
                if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                    component.addHeaderWithClass(at.getLabel(), "text-border-center");
                }
            }

            //implicit adaugam header Actiune pentru a pozitiona butoane de add/delete rand
            component.addHeader("Actiune");
            smartForm.addAttributeLinkComponent(attrComplex, Arrays.asList(component), layout, true);
            SmartFormSupport.bindWithRow(((Component) smartForm).getId().get(),
                    new ComplexAttributeBinderBean(((Component) smartForm).getId().get(), attrComplex, component), component,index.get());

        }

    }


    private List<RowAttrComplexList> getFirstNRows( List<RowAttrComplexList> attrsOfComplex, Integer nrRows, Integer indexStart) {

        List<RowAttrComplexList> attrList=
                attrsOfComplex.stream()
                        .sorted(Comparator.comparing(RowAttrComplexList::getRowNumber))
                        .collect(Collectors.toList());
        List<RowAttrComplexList> rowList= new ArrayList<>();
        if( attrsOfComplex!=null && attrsOfComplex.size()!=0 ){
            if(nrRows+indexStart>attrsOfComplex.size()){
                nrRows=attrsOfComplex.size()-indexStart;
            }
            for(int i=indexStart;i<indexStart+nrRows; i++){
                rowList.add(attrList.get(i));
            }
        }

        return rowList;
    }

    private void loadFileXmlImported(SucceededEvent e, MemoryBuffer fileBufferXml, AttributeLink attrComplex, ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId) {
        byte[] fisier = new byte[0];
        try {

            fisier = IOUtils.toByteArray(fileBufferXml.getInputStream());
            CreateTipDocFileResponseXml resp=  BeanUtil.getBean(PS4Service.class).importXml(fisier, attrComplex.getIdDocumentSelectie(),fileBufferXml.getFileName());

            if(resp.getStatus().equals("OK") && (resp.getErrString()==null ||resp.getErrString().isEmpty())){
                listaImportata = resp.getAttributeLinkListOfLists();

                if (listaImportata.getAttributeLinklist().size() == 0) {
                    UI.getCurrent().getPage().executeJs(" swalErrorTop($0);", I18NProviderStatic.getTranslation("import.excel.file"));

                } else {

                    for (AttributeLinkList al : listaImportata.getAttributeLinklist()) {
                        //addNewRowAttrEditable(component, al.getAttributeLink(), smartFormId, attrComplex, index);
                        RowAttrComplexList row= new RowAttrComplexList();
                        List<DocAttrLink> listaDocAttr = new ArrayList<>();
                        if(al.getAttributeLink()!=null) {
                            try{
                                listaDocAttr=      getConversionService().convert(al, DocAttrLinkList.class).getDocAttrLink();

                            }catch (Exception e2){
                                String a="";
                            }
                            if(listaDocAttr!=null){
                                listaDocAttr = listaDocAttr.stream().sorted(Comparator.comparing(DocAttrLink::getPosition)).collect(Collectors.toList());

                            }
                        }

                        row.setListaAtribute(listaDocAttr);
                        row.setRowNumber(index.getAndIncrement());
                         if(attrComplex.getAttrsOfComplex()==null || attrComplex.getAttrsOfComplex().size()==0){
                            attrComplex.setAttrsOfComplex(new ArrayList<>());

                        }
                        attrComplex.getAttrsOfComplex().add(row);
                        addNewRowAttrEditable(component, listaAtributeAtrComplex, smartFormId, row, attrComplex,Optional.ofNullable("IMPORT"));

                    }
                }
                UI.getCurrent().getPage().executeJs(" swal.close()");

            }
            else {
                UI.getCurrent().getPage().executeJs(" swalErrorTop($0);", resp.getErrString());
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private Double getValueDoubleFromMap(Map<String, String> mapAtr, String key) {
        Double ret = null;
        String strVal = mapAtr.get(key);
        try {
            ret = Double.parseDouble(strVal);
        } catch (Throwable ignore) {
        }

        if (ret == null) {
            ret = 0d;
        }

        return ret;
    }

    private Double getValueDoubleFromString(String strVal) {
        Double ret = null;

        strVal=strVal.replaceAll(",","");
        try {
            ret = Double.parseDouble(strVal);
        } catch (Throwable ignore) {
        }

        if (ret == null) {
            ret = 0d;
        }

        return ret;
    }

    private void updateAttrComplexFooter(ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId, AttributeLink attrComplex) {
        Map<String, String> mapAtr = new HashMap<>();

       // List<RowAttrComplexList> rowList= getFirstNRows( attrComplex.getAttrsOfComplex(),NR_ROWS_TABEL,0);

        //se parcurge lista de linii ale atributului complex
        if(attrComplex.getAttrsOfComplex()!=null && attrComplex.getAttrsOfComplex().size()!=0){
            for (RowAttrComplexList row : attrComplex.getAttrsOfComplex()) {
                for (DocAttrLink attr : row.getListaAtribute()) {
                    //pentru fiecare atribut simplu care are formula calcul coloana -> adaugam valoare in map
                    //daca map-ul deja contine valoare pentru atributul X -> se va aduna noua valaore
                    if (mapAtr.containsKey(attr.getAttributeCode()) && attr.getFormula_calcul_coloana() != null) {
                        String valAtr = attr.getValue();
                        if (attr.getValue() == null || attr.getValue().isEmpty()) {
                            valAtr = "0";
                        }


                        String totalVal=  String.valueOf(getValueDoubleFromMap(mapAtr, attr.getAttributeCode()) + getValueDoubleFromString(valAtr));
                        mapAtr.put(attr.getAttributeCode(),totalVal);
                        mapAtr.put("TOTAL_"+attr.getAttributeId(),totalVal);
                    }
                    //daca map ul nu contine deloc atributul X -> adaugam valoarea acestuia
                    else {
                        if (
                                attr.getValue() != null && !attr.getValue().isEmpty()) {
                            String atrVal=attr.getValue().replaceAll(",","");
                            mapAtr.put(attr.getAttributeCode(), atrVal);
                            mapAtr.put("TOTAL_"+attr.getAttributeId(), atrVal);
                        }
                    }

                }


            }
        }

        //pentru fiecare atribut simplu al atributului complex, daca are formula calcul total, preluam celula de total si adaugam valoarea ei in map
        for (AttributeLink atribut : listaAtributeAtrComplex) {

            if (atribut.getFormula_calcul_coloana() != null && !atribut.getFormula_calcul_coloana().isEmpty()) {
                //get tr din footer pentru acest atribut
                Optional<Component> trFooter = Optional.ofNullable(component.getThFooterByid("TOTAL_" + atribut.getAttributeId()));

                //mapAtr.put("TOTAL_" + atribut.getAttributeId(), trFooter.get().getElement().getText());

                //calcul by formula atribut
                DecimalFormat df2 = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                if(atribut.getPrecision()!=null){
                    df2.setMaximumFractionDigits(atribut.getPrecision());
                    df2.setMinimumFractionDigits(atribut.getPrecision());
                }

                Double formulaCalcul = 0.0;
                try {

                    formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtr, atribut.getFormula_calcul_coloana().trim());
                } catch (Exception e) {
                    trFooter.get().getElement().setText("0");

                }
                //set noua valoare pe celula din footer
                if (trFooter.isPresent() && formulaCalcul != null) {
                    trFooter.get().getElement().setText(df2.format(formulaCalcul.isNaN()?0.0d:formulaCalcul));
                }

            }
        }
        //se reia parcurgerea formulelor pentru cazurile in care totalul se calculeaza pe baza altora (vezi PMP)
        for (AttributeLink atribut : listaAtributeAtrComplex) {

            if (atribut.getFormula_calcul_coloana() != null && !atribut.getFormula_calcul_coloana().isEmpty()) {
                //get tr din footer pentru acest atribut
                Optional<Component> trFooter = Optional.ofNullable(component.getThFooterByid("TOTAL_" + atribut.getAttributeId()));

                if (trFooter.isPresent() && (trFooter.get().getElement().getText().isEmpty() || trFooter.get().getElement().getText().equals("0"))) {

                    //calcul by formula atribut
                    DecimalFormat df2 = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    if(atribut.getPrecision()!=null){
                        df2.setMaximumFractionDigits(atribut.getPrecision());
                        df2.setMinimumFractionDigits(atribut.getPrecision());
                    }
                    Double formulaCalcul = 0.0;
                    try {
                        formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtr, atribut.getFormula_calcul_coloana().trim());
                    } catch (Exception e) {
                        trFooter.get().getElement().setText("0");

                    }

                    //set noua valoare pe tr footer
                    if (trFooter.isPresent() && formulaCalcul != null) {
                        trFooter.get().getElement().setText(df2.format(formulaCalcul.isNaN()?0.0d:formulaCalcul));


                    }

                    mapAtr.put("TOTAL_" + atribut.getAttributeId(), trFooter.get().getElement().getText());
                }


            }
        }
    }

    private void addNewRowAttrFooter(ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId, AttributeLink attrComplex) {

        component.addFooter("Total");

        //parcurgem lista de atribute si adaugam o celula noua pentru fiecare cu valoare 0
        for (AttributeLink at : listaAtributeAtrComplex) {
            if (!(at.getHidden() != null && at.getHidden().equals(true))) {

                if (at.getFormula_calcul_coloana() != null) {
                    component.addFooter("0", "TOTAL_" + at.getAttributeId());

                } else {
                    component.addFooter("");

                }
            }
        }
        //apelam resize iframe pentru eliminare scroll
        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");


    }

    private void addNewRowAttrEditable(ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId, RowAttrComplexList row, AttributeLink attrComplex, Optional<String> oper) {
        Map<RowAtComboBoxIdentifier, ComboBox<Lov>> createdComboBoxes = new HashMap<RowAtComboBoxIdentifier, ComboBox<Lov>>();
        Map<RowAtComboBoxIdentifier, ReCreatListLovCallback> reCreateCallbacks = new HashMap<RowAtComboBoxIdentifier, ReCreatListLovCallback>();

        Map<Integer, List<Integer>> atributeDependenteMap = new HashMap<Integer, List<Integer>>();

        for (AttributeLink at : listaAtributeAtrComplex) {
            if(oper.isPresent() && oper.get().equals("NEW_ROW")){
                at.setValue("");
            }
            if (at.getSelectSql() != null && at.getSelectSql().contains("^")) {
                for (AttributeLink at2 : listaAtributeAtrComplex) {

                    if (at.getSelectSql().contains("^" + at2.getName())) {
                        List<Integer> depList = atributeDependenteMap.get(at.getAttributeId());
                        if (depList == null) {
                            depList = new ArrayList<>();
                        }

                        depList.add(at2.getAttributeId());
                        atributeDependenteMap.put(at.getAttributeId(), depList);
                    }
                }
            }
        }

        HtmlContainer newRow = new HtmlContainer("tr"); // => <tr> </tr>
        HashMap<Component, AttributeLink> mapComponentaAtribut = new HashMap<>();

        //adaugare celula pentru coloana Adauga
        component.addRowColumn(newRow, new Label(""));

//stop afisare nr crt
//component.addRowColumn(newRow, labelNrCrt); // <td> 2 </td>

        final Boolean[] isShownSave = {false};
        ClickNotifierAnchor deleteButton = new ClickNotifierAnchor();
        deleteButton.addClassNames("btn_tooltip", "tooltip_chat", "tooltip_right_delete");

        deleteButton.getElement().setAttribute("data-toggle", "tooltip");
        deleteButton.getStyle().set("width", "50px!important");

        HtmlContainer iconMessageSave = new HtmlContainer("i");
        HtmlContainer iconMessageDelete = new HtmlContainer("i");

        iconMessageSave.addClassNames("fas", "fa-check");
        iconMessageDelete.addClassNames("fas", "fa-trash-alt");
        //adaugare tag span - tootlip
        HtmlContainer deleteButtonSpan = new HtmlContainer("span");
        deleteButtonSpan.add(new Text(I18NProviderStatic.getTranslation("document.type.service.request.view.atribut.complex.option.delete.label")));
        deleteButtonSpan.addClassNames("tooltiptext");
        iconMessageDelete.add(deleteButtonSpan);
        deleteButton.add(iconMessageDelete);

        //la click pe buton delete => sterg randul curent
        deleteButton.addClickListener(e -> deleteInfoRow(row.getRowNumber(), deleteButton, smartFormId, attrComplex, component, newRow, listaAtributeAtrComplex));
        deleteButton.getElement().setAttribute("td-class", "text-center");

        List<DocAttrLink> rowList2=   row.getListaAtribute().stream()
                .sorted(Comparator.comparing(DocAttrLink::getPosition))
                .collect(Collectors.toList());
        //parcurgem fiecare atribut simplu al atributului complex si construim componentele aferenta
        for (DocAttrLink at : rowList2) {

            at.setIdDocumentSelectie(attrComplex.getAttributeId());

            AttributeLinkDataType atrDataType = getDocAttributeLinkDataType(at);
            Optional<AttributeLink> attributeLinkOp = listaAtributeAtrComplex.stream().filter(a -> a.getAttributeId().toString().equals(at.getAttributeId().toString())).findFirst();

            AttributeLink attributeLink = null;
            if (attributeLinkOp.isPresent()) {
                attributeLink = attributeLinkOp.get();
                attributeLink.setIdDocumentSelectie(attrComplex.getAttributeId());
            }

            if(attributeLink!=null){
                if(at.getValue()!=null && at.getValue().isEmpty()){
                    attributeLink.setValue("");
                }

                StringAttributeBinderBean binderBean = new StringAttributeBinderBean(smartFormId, attributeLink);

                switch (atrDataType) {
                    case DATA:
                        DatePicker dataAdaugat = new DatePicker();
                        dataAdaugat.setLocale(UI.getCurrent().getLocale());
                        dataAdaugat.getElement().setAttribute("td-class", "text-border");

                        if (attributeLink.getReadOnly() != null && attributeLink.getReadOnly()) {
                            dataAdaugat.setReadOnly(attributeLink.getReadOnly());
                        }

                        mapComponentaAtribut.put(dataAdaugat, attributeLink);
                        component.addRowColumn(newRow, dataAdaugat);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        //convert String to LocalDate
                        if (at.getValue() != null && !at.getValue().isEmpty()) {
                            try {
                                LocalDate localDate = LocalDate.parse(at.getValue(), formatter);
                                dataAdaugat.setValue(localDate);
                            } catch (Throwable th) {
                                System.err.println("cannot parse value as date: " + at.getValue() == null ? "null" : at.getValue());
                            }
                        }

                        setWidthAttr(dataAdaugat, attributeLink);


                        if(at.getMandatory() && (at.getValue()==null ||  at.getValue().isEmpty() ) ) {

                            SmartFormSupport.bindWithRow(smartFormId,
                                    new LocalDateAttributeBinderBean(smartFormId, attributeLink), dataAdaugat,row.getRowNumber(),
                                    new DateRangeValidator("", LocalDate.MIN, LocalDate.MAX));
                        }
                        dataAdaugat.addValueChangeListener(e -> {
                            if(e.getValue()!=null){
                                at.setValue(formatter.format(e.getValue()));
                                dataAdaugat.setValue(e.getValue());

                            }
                            for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().setValue(null);
                                            entry.getValue().getDataProvider().refreshAll();
                                        }
                                    }
                                }
                            }

                            for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().call();
                                        }
                                    }
                                }
                            }
                            SmartFormSupport.aplicareFormulaCalculPortal(row.getRowNumber(),smartFormId,mapComponenteRandAtribute);

                            editInfoRow(row.getRowNumber(), deleteButton, smartFormId, attrComplex, component,Optional.empty());
                        });

                        break;
                    case BOOLEAN:
                        Checkbox attrAdaugat = new Checkbox();
                        attrAdaugat.getElement().setAttribute("td-class", "text-border-center");

                        if (attributeLink.getReadOnly() != null && attributeLink.getReadOnly()) {
                            attrAdaugat.setReadOnly(attributeLink.getReadOnly());
                        }

                        mapComponentaAtribut.put(attrAdaugat, attributeLink);
                        component.addRowColumn(newRow, attrAdaugat);
                        setWidthAttr(attrAdaugat, attributeLink);
                        if (at.getValue() != null) {
                            Boolean val = false;
                            try {
                                val = at.getValue().equals("1");
                            } catch (Exception e) {
                                val = false;
                            }
                            attrAdaugat.setValue(val);

                        }


                        if(at.getMandatory() && (at.getValue()==null ||  at.getValue().isEmpty() ) )  {
                            SmartFormSupport.bindWithRow(smartFormId,
                                    binderBean, attrAdaugat,row.getRowNumber(),
                                    new MandatoryAttributeBeanPropertyValidator(""));
                        }
                        attrAdaugat.addValueChangeListener(e -> {
                            for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().setValue(null);
                                            entry.getValue().getDataProvider().refreshAll();
                                        }
                                    }
                                }
                            }

                            for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().call();
                                        }
                                    }
                                }
                            }
                            SmartFormSupport.aplicareFormulaCalculPortal(row.getRowNumber(),smartFormId, mapComponenteRandAtribute);

                            editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());
                        });
                        break;
                    case OPT_SELECT_DROPBOX:
                        if (at.getMultipleSelection() != null && at.getMultipleSelection()) {
                            try {
                                MultiselectComboBox<Lov> lovComboBox = new MultiselectComboBox<>();
                                lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
                                lovComboBox.getElement().setAttribute("td-class", "text-border");
                                RowAtComboBoxIdentifier identifier = new RowAtComboBoxIdentifier(row.getRowNumber(), at.getAttributeId().intValue());

                                if (at.getReadOnly() == null || !at.getReadOnly()) {
                                    AttributeLink finalAttributeLink3 = attributeLink;
                                    ReCreatListLovCallback reCreateCallback = new ReCreatListLovCallback() {
                                        @Override
                                        public void call() {
                                            ListDataProvider<Lov> dataProvider = SmartFormSupport.createLovMultiDataProviderList(finalAttributeLink3, lovComboBox, row.getRowNumber(), attrComplex,mapComponenteRandAtribute);
                                            lovComboBox.setDataProvider(dataProvider);
                                        }
                                    };
                                    reCreateCallbacks.put(identifier, reCreateCallback);
                                    reCreateCallback.call();
                                }

                                mapComponentaAtribut.put(lovComboBox, attributeLink);

                                component.addRowColumn(newRow, lovComboBox);
                                setWidthAttr(lovComboBox, attributeLink);

                                if (at.getValue() != null) {
                                    Set<Lov> selectedList = new HashSet<Lov>();

                                    if (Optional.ofNullable(at.getValueForLov()).isPresent() && !at.getValueForLov().isEmpty()) {
                                        selectedList.addAll(at.getValueForLov());
                                    }
                                    if (selectedList.isEmpty()) {
                                        lovComboBox.setValue(selectedList);
                                    }
                                }
                                if(at.getMandatory())  {
                                    SmartFormSupport.bindWithRow(smartFormId,
                                            binderBean, lovComboBox,
                                            row.getRowNumber(),new MandatoryAttributeBeanPropertyValidator(""));
                                }
                                lovComboBox.addValueChangeListener(e -> {
                                    for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                        List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                        if (depList != null) {
                                            for (Integer depId : depList) {
                                                if (depId.equals(at.getAttributeId())) {
                                                    entry.getValue().setValue(null);
                                                    entry.getValue().getDataProvider().refreshAll();
                                                }
                                            }
                                        }
                                    }

                                    for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                        List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                        if (depList != null) {
                                            for (Integer depId : depList) {
                                                if (depId.equals(at.getAttributeId())) {
                                                    entry.getValue().call();
                                                }
                                            }
                                        }
                                    }

                                    editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());
                                });
                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                ComboBox<Lov> lovComboBox = new ComboBox<>();
                                lovComboBox.setAllowCustomValue(false);
                                lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
                                lovComboBox.getElement().setAttribute("td-class", "text-border");
                                RowAtComboBoxIdentifier identifier = new RowAtComboBoxIdentifier(row.getRowNumber(), at.getAttributeId().intValue());
                                AttributeLink finalAttributeLink1 = attributeLink;

                                if (at.getReadOnly() == null || !at.getReadOnly()) {
                                    if (at.getCheckUniqueTert() != null && at.getCheckUniqueTert()) {
                                        ReCreatListLovCallback reCreateCallback = new ReCreatListLovCallback() {
                                            @Override
                                            public void call() {
                                                ListDataProvider<Lov> dataProvider = SmartFormSupport.createLovDataProviderList(finalAttributeLink1, lovComboBox, row.getRowNumber(), attrComplex,mapComponenteRandAtribute);
                                                lovComboBox.setDataProvider(dataProvider);
                                            }
                                        };

                                        reCreateCallbacks.put(identifier, reCreateCallback);
                                        reCreateCallback.call();
                                    } else {

                                        DataProvider<Lov, String> dataProvider = SmartFormSupport.createLovDataProviderDynamic(finalAttributeLink1, lovComboBox, row.getRowNumber(), attrComplex,mapComponenteRandAtribute);
                                        lovComboBox.setDataProvider(dataProvider);
                                        createdComboBoxes.put(identifier, lovComboBox);
                                    }
                                }

                                mapComponentaAtribut.put(lovComboBox, attributeLink);

                                component.addRowColumn(newRow, lovComboBox);
                                setWidthAttr(lovComboBox, attributeLink);

                                if (attributeLink.getReadOnly() != null && attributeLink.getReadOnly()) {
                                    lovComboBox.setReadOnly(attributeLink.getReadOnly());
                                }
                                if(at.getMandatory()){
                                SmartFormSupport.bindWithRow(smartFormId,
                                        binderBean, lovComboBox,
                                        row.getRowNumber(),new MandatoryAttributeBeanPropertyValidator(""));
                            }
                                if (at.getValue() != null && !at.getValue().isEmpty()) {
                                    Optional<Lov> selected = Optional.empty();
                                    if (Optional.ofNullable(at.getValueForLov()).isPresent() && !at.getValueForLov().isEmpty()) {
                                        selected = Optional.of(new Lov(at.getValueForLov().get(0).getId(), at.getValueForLov().get(0).getValoare()));

                                    }
                                    if (selected.isPresent()) {
                                        lovComboBox.setValue(selected.get());
                                    }
                                }

                                lovComboBox.addValueChangeListener(e -> {
                                    for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                        List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                        if (depList != null) {
                                            for (Integer depId : depList) {
                                                if (depId.toString().equals(at.getAttributeId().toString())) {
                                                    entry.getValue().setValue(null);
                                                    entry.getValue().getDataProvider().refreshAll();
                                                }
                                            }
                                        }
                                    }

                                    for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                        List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                        if (depList != null) {
                                            for (Integer depId : depList) {
                                                if (depId.toString().equals(at.getAttributeId().toString())) {
                                                    entry.getValue().call();
                                                }
                                            }
                                        }
                                    }


                                    if(at.getMandatory() && (lovComboBox.getValue()==null ||  lovComboBox.getValue().getId().isEmpty() ) )  {
                                        SmartFormSupport.bindWithRow(smartFormId,
                                                binderBean, lovComboBox,
                                                row.getRowNumber(),new MandatoryAttributeBeanPropertyValidator(""));
                                    }

                                    editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());
                                });


                            } catch (Exception e) {

                            }
                        }

                        break;
                    case NUMERIC:
                        Optional<Integer> precision = Optional.ofNullable(attributeLink.getPrecision());
                        TextField attrNumericAdaugat = new TextField();
                        new NumeralFieldFormatter(",", ".", precision.orElse(0)).extend(attrNumericAdaugat);

                        attrNumericAdaugat.addClassName("thousands");

                        //21.07.2021 - Neata Georgiana - ANRE - fix check completare campuri atribut complex
                        attrNumericAdaugat.setValueChangeMode(ValueChangeMode.ON_CHANGE);
                        if (attributeLink.getReadOnly() != null && attributeLink.getReadOnly()) {
                            attrNumericAdaugat.setReadOnly(attributeLink.getReadOnly());
                        }
                        if (precision.isPresent() && precision.get().compareTo(0) != 0) {
                            //09.06.2021 - Neata Georgiana - ANRE - am adaugat acest constructor pentru a permite validare camp numeric cu numar de zecimale specificat
                            String errMessage = "Completati cu valoare numerica cu " + precision.get() + " zecimale";
                            if(oper.isPresent() && oper.get().equals("IMPORT")){
                                errMessage+="(Verificati inclusiv valoarea reala din fisierul importat)";
                            }
                            if (at.getValidatorErrMessage() != null && !at.getValidatorErrMessage().isEmpty()) {
                                errMessage += at.getValidatorErrMessage();
                            }
                            SmartFormSupport.bindWithRow(smartFormId,
                                    binderBean, attrNumericAdaugat,row.getRowNumber(),
                                    new DoubleAttributeValidatorThousandsAware(errMessage, precision.get()));
                        } else {
                            String errMessage = "Completati cu valoare numerica fara zecimale";
                            if(oper.isPresent() && oper.get().equals("IMPORT")){
                                errMessage+="(Verificati inclusiv valoarea reala din fisierul importat)";
                            }
                            if (at.getValidatorErrMessage() != null && !at.getValidatorErrMessage().isEmpty()) {
                                errMessage += at.getValidatorErrMessage();
                            }
                            SmartFormSupport.bindWithRow(smartFormId,
                                    binderBean, attrNumericAdaugat,row.getRowNumber(),
                                    new SignIntegerAttributeValidatorThousandsAware(errMessage));
                        }

                        AttributeLink finalAttributeLink = attributeLink;
                        attrNumericAdaugat.addBlurListener(e -> {

                            if (!SmartFormSupport.validate(smartFormId, finalAttributeLink)) {
                                SmartFormSupport.validate(smartFormId, finalAttributeLink);

                            }
                        });
                        if (at.getValue() != null) {
                            attrNumericAdaugat.setValue(at.getValue());

                        }

                        mapComponentaAtribut.put(attrNumericAdaugat, attributeLink);
                        attrNumericAdaugat.getElement().setAttribute("td-class", "text-border");
                        component.addRowColumn(newRow, attrNumericAdaugat);
                        setWidthAttr(attrNumericAdaugat, attributeLink);



                        if (at.getValoareImplicita() != null && !at.getValoareImplicita().isEmpty()) {
                            attrNumericAdaugat.setValue(at.getValoareImplicita());
                        }

                        attrNumericAdaugat.addValueChangeListener(e -> {

                            for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().setValue(null);
                                            entry.getValue().getDataProvider().refreshAll();
                                        }
                                    }
                                }
                            }

                            for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().call();
                                        }
                                    }
                                }
                            }

                            //calcul atribute by formula_calcul_portal
                            SmartFormSupport.aplicareFormulaCalculPortal(row.getRowNumber(),smartFormId, mapComponenteRandAtribute);

                            editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.ofNullable(listaAtributeAtrComplex));
                        });
                        break;
                    //tratare tip de data FISIER => posibilitate de upload fisier (cu tip de doc= cel setat ca document selectie)
                    case FISIER:
                        //adaugare div in care vor fi tinute toate componentele necesare
                        Div containerCell= new Div();
                        containerCell.getElement().setAttribute("td-class", "text-border");


                        //definire textfield care va tine id-ul de fisier incarcat
                        TextField attrIdFisier = new TextField();
                        attrIdFisier.setId(attributeLink.getAttributeId()+"-"+row.getRowNumber());
                        attrIdFisier.setReadOnly(true);
                        attrIdFisier.addClassName("width_70_prc");

                        //adaugare textfield care va tine id-ul de fisier incarcat
                        containerCell.addComponentAsFirst(attrIdFisier);

                        //adaugare componenta in map
                        HashMap<Component,AttributeLink> mapComponentaAtributTemp= new HashMap<>();
                        if(mapComponentaAtribut!=null && mapComponentaAtribut.size()!=0){
                            for(Component com:mapComponentaAtribut.keySet()){
                                if(mapComponentaAtribut.get(com).getAttributeId().equals(attributeLink.getAttributeId())){
                                    mapComponentaAtribut.remove(com);
                                    mapComponentaAtribut.put(attrIdFisier,attributeLink);

                                    break;
                                }else{
                                    mapComponentaAtributTemp.put(attrIdFisier,attributeLink);
                                    break;
                                }
                            }
                            mapComponentaAtribut.putAll(mapComponentaAtributTemp);
                        }else{
                            mapComponentaAtribut.put(attrIdFisier,attributeLink);
                        }


                        //definire buton upload
                        ClickNotifierAnchor uploadBtn = new ClickNotifierAnchor();
                        uploadBtn.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                        uploadBtn.setHref("javascript:void(0)");
                        HtmlContainer uploadIcon = new HtmlContainer("i");
                        uploadIcon.addClassNames("custom-icon", "icon-up","tooltip_chat");
                        uploadBtn.add(uploadIcon, new Text(""));
                        Span spanuploadIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.upload.label.100"));
                        spanuploadIcon.addClassName("tooltiptext");
                        uploadIcon.add(spanuploadIcon);
                        MemoryBuffer fileBuffer = new MemoryBuffer();
                        Upload upload = new Upload(fileBuffer);
                        upload.setId("upload-for-"+attributeLink.getAttributeId()+"-"+row.getRowNumber());
                        upload.addClassName("upload-vaadin-no-file-list");
                        upload.setUploadButton(uploadBtn);
                        upload.setDropAllowed(false);
                        upload.setMaxFiles(1);
                        if(at.getFormatObligatoriu()!=null && !at.getFormatObligatoriu().isEmpty()){
                            String extensiiAcc= "."+at.getFormatObligatoriu().replace(",",",.");
                            upload.getElement().setProperty("accept", extensiiAcc);

                        }else{
                            upload.setAcceptedFileTypes(".png",".jpg", ".jpeg", ".png", ".bmp", ".tiff", ".gif", ".raw", ".pdf",".txt",".docx","doc",".zip",".rar");

                        }
                        upload.addClassName("upload-no-info");

                        Div uploadL = new Div(upload);
                        upload.setMaxFileSize(maxFileSize);

                        uploadL.getStyle().set("display", "inline-block");
                        AttributeLink finalAttributeLink4 = attributeLink;

                        //daca exista valoare => afisez btn de download&delete
                        if(at.getValue()!=null && !at.getValue().trim().isEmpty() && !at.getValue().toLowerCase().equals("null")){
                            DocObligatoriuExtra fisierUploaded= new DocObligatoriuExtra(new DocObligatoriu());
                            //preluare  download link & name by id fisier
                            FileLink fileLink= new FileLink();
                            fileLink.setName(at.getNumeFisier());
                            fisierUploaded.setUploadedFileName(at.getNumeFisier());
                            //BeanUtil.getBean(DmswsFileService.class).getFileLink(SecurityUtils.getToken(),at.getValue());
                            if(fileLink.getDownloadLink()!=null){
                                fisierUploaded.setDownloadLinkForUploadedFile(fileLink.getDownloadLink());
                            }

                            fisierUploaded.setUploadedFileId(Long.valueOf(at.getValue()));
                            //setare valoare id fisier pe componenta
                            finalAttributeLink4.setValue(at.getValue().toString());
                            attrIdFisier.setValueChangeMode(ValueChangeMode.EAGER);
                            attrIdFisier.setValue(fisierUploaded.getUploadedFileName()+ "("+fisierUploaded.getUploadedFileId().toString()+")");




                            //definire butoane de download & delete
                            ClickNotifierAnchor download = new ClickNotifierAnchor();

                            download.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                            HtmlContainer downloadIcon = new HtmlContainer("i");
                            downloadIcon.addClassNames("custom-icon", "icon-down","tooltip_chat");
                            download.add(downloadIcon, new Text(""));
                            Span spandownloadIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.download.label"));
                            spandownloadIcon.addClassName("tooltiptext");
                            downloadIcon.add(spandownloadIcon);


                            download.addClickListener(eClick -> {
                                        if(download.getHref()==null || download.getHref().isEmpty()){
                                            FileLink fileLinkD = BeanUtil.getBean(DmswsFileService.class).getFileLink(SecurityUtils.getToken(),at.getValue());
                                            if(fileLinkD!=null && fileLinkD.getDownloadLink()!=null){
                                                fisierUploaded.setDownloadLinkForUploadedFile(fileLinkD.getDownloadLink());
                                                //VaadinClientUrlUtil.setLocation(fileLinkD.getDownloadLink());
                                                download.setHref(StreamResourceUtil.getStreamResource(
                                                        fileLinkD.getName(),
                                                        fileLinkD.getDownloadLink()));

                                            }
                                            UI.getCurrent().getPage().executeJs("triggerClick($0)", download.getHref());

                                        }else{
                                            FileLink fileLinkD = BeanUtil.getBean(DmswsFileService.class).getFileLink(SecurityUtils.getToken(),at.getValue());
                                            if(fileLinkD!=null && fileLinkD.getDownloadLink()!=null){
                                                fisierUploaded.setDownloadLinkForUploadedFile(fileLinkD.getDownloadLink());
                                                //VaadinClientUrlUtil.setLocation(fileLinkD.getDownloadLink());
                                                download.setHref(StreamResourceUtil.getStreamResource(
                                                        fileLinkD.getName(),
                                                        fileLinkD.getDownloadLink()));

                                            }
                                        }

                                    }
                            );
                          /*  if(fisierUploaded.getUploadedFileName()!=null && fisierUploaded.getDownloadLinkForUploadedFile()!=null){
                                download.setHref(StreamResourceUtil.getStreamResource(
                                        fisierUploaded.getUploadedFileName(),
                                        fisierUploaded.getDownloadLinkForUploadedFile()));
                            }
*/
                            download.setTarget("_blank");
                            ClickNotifierAnchor delete = new ClickNotifierAnchor();
                            delete.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                            delete.setHref("javascript:void(0)");
                            HtmlContainer deleteIcon = new HtmlContainer("i");
                            deleteIcon.addClassNames("custom-icon", "icon-delete","tooltip_chat");
                            delete.add(deleteIcon, new Text(""));
                            Span spandeleteIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.delete.label"));
                            spandeleteIcon.addClassName("tooltiptext");
                            deleteIcon.add(spandeleteIcon);
                            delete.addClickListener(eDel -> {
                                        deleteFileUploaded(delete, fisierUploaded, containerCell, delete, download, attrIdFisier,uploadL);
                                        //setare valoare atribut ""
                                        finalAttributeLink4.setValue("");
                                        //eliminare id fisier din textfield
                                        attrIdFisier.setValue("");
                                        //salvare "" in mapul de componente
                                        editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());
                                        if(finalAttributeLink4.getMandatory() && (finalAttributeLink4.getValue()==null ||  finalAttributeLink4.getValue().trim().isEmpty())) {
                                            SmartFormSupport.bindWithRow(smartFormId,
                                                    binderBean, attrIdFisier,
                                                    row.getRowNumber(),new MandatoryAttributeBeanPropertyValidator(""));
                                        }
                                    }
                            );


                            //adaugare butoane de download&delete
                            containerCell.add(download, delete);
                        }
                        //daca nu exista valoare pe atribut (fisier incarcat) => afisez buton de upload
                        else{
                            containerCell.add(uploadL);
                        }
                        component.addRowColumn(newRow, containerCell);
                        if(at.getMandatory() && (at.getValue()==null ||  at.getValue().trim().isEmpty())) {
                            SmartFormSupport.bindWithRow(smartFormId,
                                    binderBean, attrIdFisier,
                                    row.getRowNumber(),new MandatoryAttributeBeanPropertyValidator(""));
                        }

                        upload.addFileRejectedListener(e -> {
                            String err=e.getErrorMessage().replaceAll(" ","");

                            if(err.contains("FileisTooBig")){
                                err="FileisTooBig";
                            }
                            UI.getCurrent().getPage().executeJs("swalErrorTop($0)",
                                    I18NProviderStatic.getTranslation(err));

                        });
                        upload.addProgressListener(e -> {
                            UI.getCurrent().getPage().executeJs("displayLoadingSpinner();\r\n toggleDisplayState($0,$1);", "v-system-error", "none");
                        });
                        upload.addFinishedListener(e -> {
                            UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none").then(Void.class,value ->editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty()));
                        });
                        upload.addSucceededListener(e -> {
                            DocObligatoriuExtra fisierUploaded= new DocObligatoriuExtra(new DocObligatoriu());
                            uploadFileSuccedEvent(e, upload, fileBuffer, finalAttributeLink4, fisierUploaded);
                            finalAttributeLink4.setValue(fisierUploaded.getUploadedFileId().toString());
                            //mapComponentaAtribut.put(attrIdFisier, finalAttributeLink4);
                            attrIdFisier.setValueChangeMode(ValueChangeMode.ON_CHANGE);
                            attrIdFisier.setValue(fisierUploaded.getUploadedFileName()+ "("+fisierUploaded.getUploadedFileId().toString()+")");

                            editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());

                            //ascundere buton upload
                            containerCell.remove(uploadL);

                            //definire butoane de download & delete
                            Anchor download = new Anchor();

                            download.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                            HtmlContainer downloadIcon = new HtmlContainer("i");
                            downloadIcon.addClassNames("custom-icon", "icon-down","tooltip_chat");
                            download.add(downloadIcon, new Text(""));
                            Span spandownloadIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.download.label"));
                            spandownloadIcon.addClassName("tooltiptext");
                            downloadIcon.add(spandownloadIcon);


                            download.setHref(StreamResourceUtil.getStreamResource(
                                    fisierUploaded.getUploadedFileName(),
                                    fisierUploaded.getDownloadLinkForUploadedFile()));
                            download.setTarget("_blank");
                            ClickNotifierAnchor delete = new ClickNotifierAnchor();
                            delete.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                            delete.setHref("javascript:void(0)");
                            HtmlContainer deleteIcon = new HtmlContainer("i");
                            deleteIcon.addClassNames("custom-icon", "icon-delete","tooltip_chat");
                            delete.add(deleteIcon, new Text(""));
                            Span spandeleteIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.delete.label"));
                            spandeleteIcon.addClassName("tooltiptext");
                            deleteIcon.add(spandeleteIcon);
                            delete.addClickListener(eDel -> {
                                        deleteFileUploaded(delete, fisierUploaded, containerCell, delete, download, attrIdFisier,uploadL);
                                        //setare valoare atribut ""
                                        finalAttributeLink4.setValue("");
                                        //eliminare id fisier din textfield
                                        attrIdFisier.setValue("");
                                        //salvare "" in mapul de componente
                                        editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());
                                        if(finalAttributeLink4.getMandatory() && (finalAttributeLink4.getValue()==null ||  finalAttributeLink4.getValue().trim().isEmpty())) {
                                            SmartFormSupport.bindWithRow(smartFormId,
                                                    binderBean, attrIdFisier,
                                                    row.getRowNumber(),new MandatoryAttributeBeanPropertyValidator(""));
                                        }

                                    }
                            );

                            //adaugare butoane download & delete
                            containerCell.add(download, delete);

                        });


                        break;
                    case IBAN:
                        TextField attrIbanAdaugat = new TextField();

                        attrIbanAdaugat.addClassName("thousands");

                        //21.07.2021 - Neata Georgiana - ANRE - fix check completare campuri atribut complex
                        attrIbanAdaugat.setValueChangeMode(ValueChangeMode.ON_CHANGE);
                        if (attributeLink.getReadOnly() != null && attributeLink.getReadOnly()) {
                            attrIbanAdaugat.setReadOnly(attributeLink.getReadOnly());
                        }


                        String errMessage = "Completati cu un IBAN valid.";
                        if (at.getValidatorErrMessage() != null && !at.getValidatorErrMessage().isEmpty()) {
                            errMessage += at.getValidatorErrMessage();
                        }
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrIbanAdaugat,row.getRowNumber(),
                                new IbanAttributeValidator(errMessage));


                        AttributeLink finalattributeLinkIban = attributeLink;
                        attrIbanAdaugat.addBlurListener(e -> {
                            if (!SmartFormSupport.validate(smartFormId, finalattributeLinkIban)) {
                                SmartFormSupport.validate(smartFormId, finalattributeLinkIban);

                            }

                        });
                        if (at.getValue() != null) {
                            attrIbanAdaugat.setValue(at.getValue());

                        }

                        mapComponentaAtribut.put(attrIbanAdaugat, attributeLink);
                        attrIbanAdaugat.getElement().setAttribute("td-class", "text-border");
                        component.addRowColumn(newRow, attrIbanAdaugat);
                        setWidthAttr(attrIbanAdaugat, attributeLink);



                        if (at.getValoareImplicita() != null && !at.getValoareImplicita().isEmpty()) {
                            attrIbanAdaugat.setValue(at.getValoareImplicita());
                        }


                        attrIbanAdaugat.addValueChangeListener(e -> {
                            for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().setValue(null);
                                            entry.getValue().getDataProvider().refreshAll();
                                        }
                                    }
                                }
                            }

                            for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().call();
                                        }
                                    }
                                }
                            }

                            //calcul atribute by formula_calcul_portal
                            SmartFormSupport.aplicareFormulaCalculPortal(row.getRowNumber(),smartFormId,mapComponenteRandAtribute);

                            editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());
                        });
                        break;

                    case CNP:
                        TextField attrCnpAdaugat = new TextField();

                        attrCnpAdaugat.addClassName("thousands");

                        //14.07.2022 - Alecu Claudiu - ANRE - check CNP atribut complex
                        attrCnpAdaugat.setValueChangeMode(ValueChangeMode.ON_CHANGE);
                        if (attributeLink.getReadOnly() != null && attributeLink.getReadOnly()) {
                            attrCnpAdaugat.setReadOnly(attributeLink.getReadOnly());
                        }


                        String errMessageCnp = "Completati cu un CNP valid.";
                        if (at.getValidatorErrMessage() != null && !at.getValidatorErrMessage().isEmpty()) {
                            errMessageCnp += at.getValidatorErrMessage();
                        }
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrCnpAdaugat,row.getRowNumber(),
                                new CnpAttributeValidator(errMessageCnp, smartFormId));


                        AttributeLink finalattrCnp = attributeLink;
                        attrCnpAdaugat.addBlurListener(e -> {
                            if (!SmartFormSupport.validate(smartFormId, finalattrCnp)) {
                                SmartFormSupport.validate(smartFormId, finalattrCnp);

                            }

                        });
                        if (at.getValue() != null) {
                            attrCnpAdaugat.setValue(at.getValue());

                        }

                        mapComponentaAtribut.put(attrCnpAdaugat, attributeLink);
                        attrCnpAdaugat.getElement().setAttribute("td-class", "text-border");
                        component.addRowColumn(newRow, attrCnpAdaugat);
                        setWidthAttr(attrCnpAdaugat, attributeLink);



                        if (at.getValoareImplicita() != null && !at.getValoareImplicita().isEmpty()) {
                            attrCnpAdaugat.setValue(at.getValoareImplicita());
                        }

                        attrCnpAdaugat.addValueChangeListener(e -> {
                            for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().setValue(null);
                                            entry.getValue().getDataProvider().refreshAll();
                                        }
                                    }
                                }
                            }

                            for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().call();
                                        }
                                    }
                                }
                            }

                            //calcul atribute by formula_calcul_portal
                            SmartFormSupport.aplicareFormulaCalculPortal(row.getRowNumber(),smartFormId,mapComponenteRandAtribute);

                            editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());
                        });
                        break;

                    default:
                        TextField attrTextAdaugat = new TextField();
                        mapComponentaAtribut.put(attrTextAdaugat, attributeLink);
                        attrTextAdaugat.getElement().setAttribute("td-class", "text-border");

                        component.addRowColumn(newRow, attrTextAdaugat);
                        setWidthAttr(attrTextAdaugat, attributeLink);
                        if (at.getValue() != null) {
                            attrTextAdaugat.setValue(at.getValue());

                        }
                        if (attributeLink.getReadOnly() != null && attributeLink.getReadOnly()) {
                            attrTextAdaugat.setReadOnly(attributeLink.getReadOnly());
                        }

                        if(at.getMandatory() && (at.getValue()==null ||  at.getValue().isEmpty() ) )  {
                            SmartFormSupport.bindWithRow(smartFormId,
                                    binderBean, attrTextAdaugat,
                                    row.getRowNumber(),new MandatoryAttributeBeanPropertyValidator(""));
                        }

                        attrTextAdaugat.addValueChangeListener(e -> {
                            attrTextAdaugat.setValue(e.getValue().trim());
                            at.setValue(e.getValue().trim());
                            for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().setValue(null);
                                            entry.getValue().getDataProvider().refreshAll();
                                        }
                                    }
                                }
                            }

                            for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                if (depList != null) {
                                    for (Integer depId : depList) {
                                        if (depId.equals(at.getAttributeId())) {
                                            entry.getValue().call();
                                        }
                                    }
                                }
                            }
                            //calcul atribute by formula_calcul_portal
                            SmartFormSupport.aplicareFormulaCalculPortal(row.getRowNumber(),smartFormId, mapComponenteRandAtribute);

                            editInfoRow(row.getRowNumber(),  deleteButton, smartFormId, attrComplex, component,Optional.empty());

                        });
                        break;

                }

            }

        }

        //pun in map randul, componenta si atributul de care e legat
        mapComponenteRandAtribute.put(row.getRowNumber(), mapComponentaAtribut);
        mapIdAtrComplexRowIndexes.put(row.getRowNumber(), attrComplex.getAttributeId());

        //component.addRowColumn(newRow, saveButon);
        component.addRowColumn(newRow, deleteButton);

        //adaugam randul nou la tabel
        component.addNewRow(newRow);

        //get label nr randuri & update
        Optional<Component> labelNrRanduri= component.getChildByid("label_nr_rows_"+attrComplex.getAttributeId());
        if(labelNrRanduri.isPresent()){
            labelNrRanduri.get().getElement().setText("Nr randuri: "+component.getNrRows());
        }

        //Comentat if-ul pentru ca este nevoie de aplicareFormulaCalculPortal pentru a vedea configurarile de [MANDATORY] la incarcarea paginii
//        if(oper.isPresent() && oper.get().equals("IMPORT")){
            //calcul atribute by formula_calcul_portal
            SmartFormSupport.aplicareFormulaCalculPortal(row.getRowNumber(),smartFormId,mapComponenteRandAtribute);
//        }

        //Facem validare
        new MandatoryAttributeBeanPropertyValidator("").apply(null, new ValueContext(component, component, new Locale("ro_RO")));
        //SmartFormSupport.validate(smartFormId);
        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");


    }

    private void deleteFileUploaded(ClickNotifierAnchor delete, DocObligatoriuExtra fisierUploaded, Div containerCell, ClickNotifierAnchor clickNotifierAnchor, Anchor download, TextField attrIdFisier, Div uploadL) {
        BeanUtil.getBean(DmswsFileService.class).deleteFile(SecurityUtils.getToken(), Math.toIntExact(fisierUploaded.getUploadedFileId()));
        containerCell.remove(delete,download);
        containerCell.add(uploadL);

    }

    private void uploadFileSuccedEvent(SucceededEvent event,Upload upload, MemoryBuffer fileBuffer, AttributeLink attributeLink, DocObligatoriuExtra fisierUploaded) {

        try {
            DocObligatoriu docObligatoriu= new DocObligatoriu();
            fisierUploaded.getDocObligatoriu().setId_document(attributeLink.getIdTipDocDataFisier());
            fisierUploaded = BeanUtil.getBean(PS4Service.class).uploadFileProcesare( fisierUploaded,event.getFileName(),  IOUtils.toByteArray(fileBuffer.getInputStream()));


        } catch (Throwable t) {

            UI.getCurrent().getPage().executeJs("swalErrorTop($0);", t.getMessage());
        }

    }

    private void addNewRowAttrPrecompletat(ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId, AttributeLink attrComplex) {
        //parcurgem fiecare atribut simplu al atributului complex
        for (AttributeLink attr : listaAtributeAtrComplex) {
            if (attr.getPrecompletareAutomata() != null && attr.getPrecompletareAutomata()) {
                if (attr.getLovId() != null) {
                    LovList lovList = BeanUtil.getBean(PS4Service.class).getLovList(attr);
                    if (lovList != null && lovList.getLov().size() != 0) {
                        //pentru fiecare valoare din lov adaugam rand nou la tabel
                        for (Lov lov : lovList.getLov()) {
                            attr.setValue(lov.getValoare());
                            List<Lov> listVal = new ArrayList<>();
                            listVal.add(lov);
                            attr.setValueForLov(listVal);

                            addNewRowAttrPrecompletat(component, listaAtributeAtrComplex, smartFormId, attrComplex, index, Optional.ofNullable(lov.getId_parinte()), lovList);

                        }
                        //get label nr randuri & update
                        Optional<Component> labelNrRanduri= component.getChildByid("label_nr_rows_"+attrComplex.getAttributeId());
                        if(labelNrRanduri.isPresent()){
                            labelNrRanduri.get().getElement().setText("Nr randuri: "+lovList.getLov().size() );
                        }
                    }
                }
                break;
            }


        }
    }

    private ConversionService getConversionService() {
        return getBean(ConversionService.class);
    }

    private void addRowIfMaxSizeNotReached(ComplexTableComponent component, AttributeLinkList attributeList, String smartFormId, AttributeLink attrComplex, AtomicInteger index) {
        if (attrComplex.getAttrComplexMaxRows() != null && mapAtributeComplexe.get(attrComplex.getAttributeId()) != null
                && mapAtributeComplexe.get(attrComplex.getAttributeId()).size() >= attrComplex.getAttrComplexMaxRows()) {
            UI.getCurrent().getPage().executeJs("swalErrorTop($0);", "Acest tabel a atins deja limita de randuri permise! ");

        } else {
            RowAttrComplexList row = new RowAttrComplexList();
            List<DocAttrLink> listaDocAttr = getConversionService().convert(attributeList, DocAttrLinkList.class).getDocAttrLink();
            for (DocAttrLink at: listaDocAttr){
                if(at.getValoareImplicita()==null || at.getValoareImplicita().isEmpty()){
                    at.setValue("");
                }
            }
            row.setListaAtribute(listaDocAttr);
            row.setRowNumber(index.getAndIncrement());
            if(attrComplex.getAttrsOfComplex()==null || attrComplex.getAttrsOfComplex().size()==0){
                attrComplex.setAttrsOfComplex(new ArrayList<>());

            }
            attrComplex.getAttrsOfComplex().add(row);
            addNewRowAttrEditable(component, attributeList.getAttributeLink(), smartFormId, row, attrComplex,Optional.ofNullable("NEW_ROW"));

        }
    }
    //deprecated - se utilizeaza DOAR addNewRowAttrEditable (ca randurile din tabel sa poata fi editate)
/*
    private void addNewRowAttr(ComplexTableComponent component, List<AttributeLink> attributeList, String smartFormId, AttributeLink attrComplex, AtomicInteger index) {
        Map<RowAtComboBoxIdentifier, ComboBox<Lov>> createdComboBoxes = new HashMap<RowAtComboBoxIdentifier, ComboBox<Lov>>();
        Map<RowAtComboBoxIdentifier, ReCreatListLovCallback> reCreateCallbacks = new HashMap<RowAtComboBoxIdentifier, ReCreatListLovCallback>();

        Map<Integer, List<Integer>> atributeDependenteMap = new HashMap<Integer, List<Integer>>();
        for (AttributeLink at : attributeList) {
            if (at.getSelectSql() != null && at.getSelectSql().contains("^")){
                for (AttributeLink at2 : attributeList) {
                    if (at.getSelectSql().contains("^" + at2.getName())){
                        List<Integer> depList = atributeDependenteMap.get(at.getAttributeId());
                        if (depList == null){
                            depList = new ArrayList<>();
                        }

                        depList.add(at2.getAttributeId());
                        atributeDependenteMap.put(at.getAttributeId(), depList);
                    }
                }
            }
        }

        HtmlContainer newRow = new HtmlContainer("tr"); // => <tr> </tr>
        int rowIndex = index.getAndIncrement();

        final Boolean[] isShownSave = {false};
        ClickNotifierAnchor saveButon = new ClickNotifierAnchor();
        ClickNotifierAnchor deleteButton = new ClickNotifierAnchor();
       deleteButton.addClassNames( "btn_tooltip" , "tooltip_chat");

        deleteButton.getElement().setAttribute("data-toggle", "tooltip");deleteButton.getStyle().set("width", "50px!important");
        HtmlContainer iconMessageSave = new HtmlContainer("i");
        HtmlContainer iconMessageDelete = new HtmlContainer("i");

        iconMessageSave.addClassNames("fas", "fa-check");
        iconMessageDelete.addClassNames("fas", "fa-trash-alt");
        //adaugare tag span - tootlip
        HtmlContainer deleteButtonSpan = new HtmlContainer("span");
        deleteButtonSpan.add(new Text(I18NProviderStatic.getTranslation("document.type.service.request.view.atribut.complex.option.delete.label")));
        deleteButtonSpan.addClassNames("tooltiptext");
        iconMessageDelete.add(deleteButtonSpan);
        saveButon.add(iconMessageSave);
        deleteButton.add(iconMessageDelete);
        //la click pe buton save => editez valoare atribut aferent celulei

        saveButon.addClickListener(e ->editInfoRow(rowIndex, saveButon, deleteButton, smartFormId, attrComplex,component));
        deleteButton.addClickListener(e -> deleteInfoRow(rowIndex, deleteButton, smartFormId, attrComplex, component, newRow,attributeList));
        saveButon.getElement().setAttribute("td-class", "text-center");
        deleteButton.getElement().setAttribute("td-class", "text-center");

        //22.06.2021 - Neata Georgiana - ANRE - afisare atribut adauga rand la stanga
        //adaugam celula pentru coloana 'Adauga'
        component.addRowColumn(newRow, new Label(""));

        //stop afisare nr crt
        //component.addRowColumn(newRow, labelNrCrt); // <td> 2 </td>

        List<DocAttrLink> listaAtributeAdaugate = new ArrayList<>();
        HashMap<Component, AttributeLink> mapComponentaAtribut = new HashMap<>();

        //parcurgem lista de atribute si adaugam o celula noua pentru fiecare
        for (AttributeLink at : attributeList) {
            at.setIdDocumentSelectie(attrComplex.getAttributeId());

            //11.06.2021 - Neata Georgiana - ANRE - setam valoare "" pentru a nu prelua informatiile completate pe randul anterior
            at.setValue("");
            listaAtributeAdaugate.add(getConversionService().convert(at, DocAttrLink.class));

            AttributeLinkDataType atrDataType = getAttributeLinkDataType(at);

            switch (atrDataType) {
                case DATA:
                    DatePicker dataAdaugat = new DatePicker();
                    dataAdaugat.setLocale(UI.getCurrent().getLocale());
                    dataAdaugat.getElement().setAttribute("td-class", "text-border");
                    if (at.getReadOnly() != null && at.getReadOnly()) {
                        dataAdaugat.setReadOnly(at.getReadOnly());
                    }
                    mapComponentaAtribut.put(dataAdaugat, at);
                    component.addRowColumn(newRow, dataAdaugat);
                    setWidthAttr(dataAdaugat, at);

                    dataAdaugat.addValueChangeListener(e -> {
                        for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry: createdComboBoxes.entrySet()){
                            List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                            if (depList != null){
                                for (Integer depId: depList){
                                    if (depId.equals(at.getAttributeId())){
                                        entry.getValue().setValue(null);
                                        entry.getValue().getDataProvider().refreshAll();
                                    }
                                }
                            }
                        }

                        for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry: reCreateCallbacks.entrySet()){
                            List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                            if (depList != null){
                                for (Integer depId: depList){
                                    if (depId.equals(at.getAttributeId())){
                                        entry.getValue().call();
                                    }
                                }
                            }
                        }

                        editInfoRow(rowIndex, saveButon, deleteButton, smartFormId, attrComplex,component);
                    });

//                    dataAdaugat.addBlurListener(e -> {
//                        //afisare bifa de edit
//                        if (!isShownSave[0]) {
//                            try {
//                                component.addRowColumn(newRow, saveButon);
//                                isShownSave[0] = true;
//
//
//                            } catch (Exception ee) {
//
//                            }
//
//                        }else{
//                            saveButon.getStyle().set("display","block");
//                        }
//                        UI.getCurrent().getPage().executeJavaScript("$0.click()", saveButon.getElement());
//
//                    });
                    break;
                case BOOLEAN:
                    Checkbox attrAdaugat = new Checkbox();
                    attrAdaugat.getElement().setAttribute("td-class", "text-border-center");
                    if (at.getReadOnly() != null && at.getReadOnly()) {
                        attrAdaugat.setReadOnly(at.getReadOnly());
                    }
                    mapComponentaAtribut.put(attrAdaugat, at);
                    component.addRowColumn(newRow, attrAdaugat);
                    setWidthAttr(attrAdaugat, at);

                    attrAdaugat.addValueChangeListener(e -> {
                        for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry: createdComboBoxes.entrySet()){
                            List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                            if (depList != null){
                                for (Integer depId: depList){
                                    if (depId.equals(at.getAttributeId())){
                                        entry.getValue().setValue(null);
                                        entry.getValue().getDataProvider().refreshAll();
                                    }
                                }
                            }
                        }

                        for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry: reCreateCallbacks.entrySet()){
                            List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                            if (depList != null){
                                for (Integer depId: depList){
                                    if (depId.equals(at.getAttributeId())){
                                        entry.getValue().call();
                                    }
                                }
                            }
                        }

                        editInfoRow(rowIndex, saveButon, deleteButton, smartFormId, attrComplex,component);
                    });

//                    attrAdaugat.addBlurListener(e -> {
//                        if (!isShownSave[0]) {
//                            try {
//                                component.addRowColumn(newRow, saveButon);
//                                isShownSave[0] = true;
//
//
//                            } catch (Exception ee) {
//
//                            }
//
//                        }else{
//                            saveButon.getStyle().set("display","block");
//                        }
//                        UI.getCurrent().getPage().executeJavaScript("$0.click()", saveButon.getElement());
//
//                    });
                    break;
                case OPT_SELECT_DROPBOX:
                    if (at.getMultipleSelection() != null && at.getMultipleSelection()){
                        try {
                            MultiselectComboBox<Lov> lovComboBox = new MultiselectComboBox<>();
                            lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
                            lovComboBox.getElement().setAttribute("td-class", "text-border");
                            RowAtComboBoxIdentifier identifier = new RowAtComboBoxIdentifier(rowIndex, at.getAttributeId());

                            if (at.getReadOnly() == null || !at.getReadOnly()) {
                                ReCreatListLovCallback reCreateCallback = new ReCreatListLovCallback() {
                                    @Override
                                    public void call() {
                                        ListDataProvider<Lov> dataProvider = SmartFormSupport.createLovMultiDataProviderList(at, lovComboBox, rowIndex, attrComplex);
                                        lovComboBox.setDataProvider(dataProvider);
                                    }
                                };
                                reCreateCallbacks.put(identifier, reCreateCallback);
                                reCreateCallback.call();
                            }

                            mapComponentaAtribut.put(lovComboBox, at);

                            component.addRowColumn(newRow, lovComboBox);
                            setWidthAttr(lovComboBox, at);

                            lovComboBox.addValueChangeListener(e -> {
                                for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry: createdComboBoxes.entrySet()){
                                    List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                    if (depList != null){
                                        for (Integer depId: depList){
                                            if (depId.equals(at.getAttributeId())){
                                                entry.getValue().setValue(null);
                                                entry.getValue().getDataProvider().refreshAll();
                                            }
                                        }
                                    }
                                }

                                for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry: reCreateCallbacks.entrySet()){
                                    List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                    if (depList != null){
                                        for (Integer depId: depList){
                                            if (depId.equals(at.getAttributeId())){
                                                entry.getValue().call();
                                            }
                                        }
                                    }
                                }

                                editInfoRow(rowIndex, saveButon, deleteButton, smartFormId, attrComplex,component);
                            });
                        } catch (Exception e) {

                        }
                    } else {
                        try {
                            ComboBox<Lov> lovComboBox = new ComboBox<>();
                            lovComboBox.setAllowCustomValue(false);
                            lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
                            lovComboBox.getElement().setAttribute("td-class", "text-border");
                            RowAtComboBoxIdentifier identifier = new RowAtComboBoxIdentifier(rowIndex, at.getAttributeId());

                            if (at.getReadOnly() == null || !at.getReadOnly()) {
                                if (at.getCheckUniqueTert()) {
                                    ReCreatListLovCallback reCreateCallback = new ReCreatListLovCallback() {
                                        @Override
                                        public void call() {
                                            ListDataProvider<Lov> dataProvider = SmartFormSupport.createLovDataProviderList(at, lovComboBox, rowIndex, attrComplex);
                                            lovComboBox.setDataProvider(dataProvider);
                                        }
                                    };
                                    reCreateCallbacks.put(identifier, reCreateCallback);
                                    reCreateCallback.call();
                                } else {
                                    DataProvider<Lov, String> dataProvider = SmartFormSupport.createLovDataProviderDynamic(at, lovComboBox, rowIndex, attrComplex);
                                    lovComboBox.setDataProvider(dataProvider);
                                    createdComboBoxes.put(identifier, lovComboBox);
                                }
                            }

                            mapComponentaAtribut.put(lovComboBox, at);

                            component.addRowColumn(newRow, lovComboBox);
                            setWidthAttr(lovComboBox, at);

                            lovComboBox.addValueChangeListener(e -> {
                                for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry : createdComboBoxes.entrySet()) {
                                    List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                    if (depList != null) {
                                        for (Integer depId : depList) {
                                            if (depId.equals(at.getAttributeId())) {
                                                entry.getValue().setValue(null);
                                                entry.getValue().getDataProvider().refreshAll();
                                            }
                                        }
                                    }
                                }

                                for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry : reCreateCallbacks.entrySet()) {
                                    List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                                    if (depList != null) {
                                        for (Integer depId : depList) {
                                            if (depId.equals(at.getAttributeId())) {
                                                entry.getValue().call();
                                            }
                                        }
                                    }
                                }

                                editInfoRow(rowIndex, saveButon, deleteButton, smartFormId, attrComplex, component);
                            });

                            //                        lovComboBox.addBlurListener(e -> {
                            //                            if (!isShownSave[0]) {
                            //                                try {
                            //                                    component.addRowColumn(newRow, saveButon);
                            //                                    isShownSave[0] = true;
                            //
                            //
                            //                                } catch (Exception ee) {
                            //
                            //                                }
                            //
                            //                            }else{
                            //                                saveButon.getStyle().set("display","block");
                            //                            }
                            //                            UI.getCurrent().getPage().executeJavaScript("$0.click()", saveButon.getElement());
                            //                        });
                        } catch (Exception e) {

                        }
                    }
                    break;
                case NUMERIC:
                    Optional<Integer> precision = Optional.ofNullable(at.getPrecision());
                    TextField attrNumericAdaugat = new TextField();

                    new NumeralFieldFormatter(",", ".", precision.orElse(0)).extend(attrNumericAdaugat);

                    attrNumericAdaugat.addClassName("thousands");

                    //21.07.2021 - Neata Georgiana - ANRE - fix check completare campuri atribut complex
                    attrNumericAdaugat.setValueChangeMode(ValueChangeMode.ON_CHANGE);
                    if (at.getReadOnly() != null && at.getReadOnly()) {
                        attrNumericAdaugat.setReadOnly(at.getReadOnly());
                    }
                    StringAttributeBinderBean binderBean = new StringAttributeBinderBean(smartFormId, at);

                    if (precision.isPresent() && precision.get().compareTo(0) != 0) {
//09.06.2021 - Neata Georgiana - ANRE - am adaugat acest constructor pentru a permite validare camp numeric cu numar de zecimale specificat
                        String errMessage="Completati cu valoare numerica cu " + precision.get() + " zecimale. ";
                         if(at.getValidatorErrMessage()!=null && !at.getValidatorErrMessage().isEmpty()){
                            errMessage+=at.getValidatorErrMessage();
                        }
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrNumericAdaugat,
                                new DoubleAttributeValidatorThousandsAware(errMessage, precision.get()));
                    } else {
                        String errMessage="Completati cu valoare numerica fara zecimale.";
                        if(at.getValidatorErrMessage()!=null && !at.getValidatorErrMessage().isEmpty()){
                            errMessage+=at.getValidatorErrMessage();
                        }
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrNumericAdaugat,
                                new SignIntegerAttributeValidatorThousandsAware(errMessage));
                    }

//                    SmartFormSupport.bindConverter(smartFormId,
//                            binderBean, attrNumericAdaugat,
//                            new ThousandsConverter());

//                    attrNumericAdaugat.addBlurListener(e -> {
//                         if (!SmartFormSupport.validate(smartFormId, at)) {
//                            SmartFormSupport.validate(smartFormId, at);
//
//                        }
//                        //afisare bifa de edit
//                        if (!isShownSave[0]) {
//                            try {
//                                component.addRowColumn(newRow, saveButon);
//                                isShownSave[0] = true;
//
//
//                            } catch (Exception ee) {
//
//                            }
//
//                        }else{
//                             saveButon.getStyle().set("display","block");
//                        }
//                        //click automat btn save
//                        UI.getCurrent().getPage().executeJavaScript("$0.click()", saveButon.getElement());
//
//                    });

                    attrNumericAdaugat.addValueChangeListener(e -> {
                        for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry: createdComboBoxes.entrySet()){
                            List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                            if (depList != null){
                                for (Integer depId: depList){
                                    if (depId.equals(at.getAttributeId())){
                                        entry.getValue().setValue(null);
                                        entry.getValue().getDataProvider().refreshAll();
                                    }
                                }
                            }
                        }

                        for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry: reCreateCallbacks.entrySet()){
                            List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                            if (depList != null){
                                for (Integer depId: depList){
                                    if (depId.equals(at.getAttributeId())){
                                        entry.getValue().call();
                                    }
                                }
                            }
                        }

                        //calcul atribute by formula_calcul_portal
                        SmartFormSupport.aplicareFormulaCalculPortal(rowIndex,smartFormId);

                        editInfoRow(rowIndex, saveButon, deleteButton, smartFormId, attrComplex,component);
                    });

                    if (at.getValoareImplicita() != null && !at.getValoareImplicita().isEmpty()) {
                        attrNumericAdaugat.setValue(at.getValoareImplicita());
                    }
                    mapComponentaAtribut.put(attrNumericAdaugat, at);
                    attrNumericAdaugat.getElement().setAttribute("td-class", "text-border");
                    component.addRowColumn(newRow, attrNumericAdaugat);
                    setWidthAttr(attrNumericAdaugat, at);
                    break;
                default:
                    TextField attrTextAdaugat = new TextField();
                    mapComponentaAtribut.put(attrTextAdaugat, at);
                    attrTextAdaugat.getElement().setAttribute("td-class", "text-border");
                    component.addRowColumn(newRow, attrTextAdaugat);
                    if (at.getReadOnly() != null && at.getReadOnly()) {
                        attrTextAdaugat.setReadOnly(at.getReadOnly());
                    }
                    setWidthAttr(attrTextAdaugat, at);

                    attrTextAdaugat.addValueChangeListener(e -> {
                        for (Map.Entry<RowAtComboBoxIdentifier, ComboBox<Lov>> entry: createdComboBoxes.entrySet()){
                            List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                            if (depList != null){
                                for (Integer depId: depList){
                                    if (depId.equals(at.getAttributeId())){
                                        entry.getValue().setValue(null);
                                        entry.getValue().getDataProvider().refreshAll();
                                    }
                                }
                            }
                        }

                        for (Map.Entry<RowAtComboBoxIdentifier, ReCreatListLovCallback> entry: reCreateCallbacks.entrySet()){
                            List<Integer> depList = atributeDependenteMap.get(entry.getKey().getIdentifier());
                            if (depList != null){
                                for (Integer depId: depList){
                                    if (depId.equals(at.getAttributeId())){
                                        entry.getValue().call();
                                    }
                                }
                            }
                        }

                        editInfoRow(rowIndex, saveButon, deleteButton, smartFormId, attrComplex,component);
                    });

//                    attrTextAdaugat.addBlurListener(e -> {
//                        if (!isShownSave[0]) {
//                            try {
//                                component.addRowColumn(newRow, saveButon);
//                                isShownSave[0] = true;
//
//
//                            } catch (Exception ee) {
//
//                            }
//
//                        }else{
//                            saveButon.getStyle().set("display","block");
//                        }
//                        UI.getCurrent().getPage().executeJavaScript("$0.click()", saveButon.getElement());
//
//                    });
                    break;

            }
        }

        //pun in map randul, componenta si atributul de care e legat
        mapComponenteRandAtribute.put(rowIndex, mapComponentaAtribut);
        mapIdAtrComplexRowIndexes.put(rowIndex,attrComplex.getAttributeId());


        //adaugam celula cu butonul delete
        component.addRowColumn(newRow, deleteButton);

        //adaugam randul nou la tabel
        component.addNewRow(newRow);

        //get label nr randuri & update
        Optional<Component> labelNrRanduri= component.getChildByid("label_nr_rows_"+attrComplex.getAttributeId());
        if(labelNrRanduri.isPresent()){
            labelNrRanduri.get().getElement().setText("Nr randuri: "+component.getNrRows());
        }

        //Facem validare
        new MandatoryAttributeBeanPropertyValidator("").apply(null,new ValueContext(component,component,new Locale("ro_RO")));

        //apel functie pentru eliminare scroll dublu
        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");

    }*/
    private void removeInvalidElements(int rowIndex, AttributeLink attrComplex, String smartFormId) {

        HashMap<Component, AttributeLink> randAttrComplex=  mapComponenteRandAtribute.get(rowIndex);

        for(Component c: randAttrComplex.keySet()) {
            smartFormComponentService.getAttributeBinderMap().get(smartFormId).remove(randAttrComplex.get(c).getAttributeId()+"_"+rowIndex);
        }


    }
    private void deleteInfoRow(int rowIndex, ClickNotifierAnchor deleteButton, String smartFormId, AttributeLink attrComplex, ComplexTableComponent componentTabel, HtmlContainer newRow, List<AttributeLink> attributeList) {
        removeInvalidElements(rowIndex,attrComplex,smartFormId);

        mapComponenteRandAtribute.remove(rowIndex);
        //ascundere buton delete dupa apasare
        deleteButton.getStyle().set("display", "none");

        //iau mapul de randuri
        List<RowAttrComplexList> mapRandAtribute2 = mapAtributeComplexe.get(attrComplex.getAttributeId());

        if (mapRandAtribute2 == null) {
            mapRandAtribute2 = new ArrayList<>();
        }
        //caut randul pentru care s a apasat stergere in map
        RowAttrComplexList rowCurent = new RowAttrComplexList();
        for (RowAttrComplexList r : mapRandAtribute2) {
            if (r.getRowNumber() == rowIndex)
                rowCurent = r;
        }

        //sterg randul din map
        mapRandAtribute2.remove(rowCurent);

        //setez map-ul nou
        mapAtributeComplexe.put(attrComplex.getAttributeId(), mapRandAtribute2);
        smartFormComponentService.addAttrListForAttributeComplex(smartFormId, attrComplex, mapRandAtribute2);

        //sterg randul din interfata
        componentTabel.removeRow(newRow);

        Map<String, String> mapAtr = new HashMap<>();

        //recalculare TOTAL inclusiv randul curent

        for (Integer i : getMapComponenteRandAtribute().keySet()) {
            //Pentru tabelul curent
            //check ca row index face parte din tabel curent
            if (mapIdAtrComplexRowIndexes.get(i) != null && mapIdAtrComplexRowIndexes.get(i).equals(attrComplex.getAttributeId()) && getMapComponenteRandAtribute().containsKey(i)) {

                for (AttributeLink atribut : attributeList) {
                    mapAtr.put(atribut.getName(), "0");
                    String c = "0";
                    Optional<Map.Entry<Component, AttributeLink>> compCurent = null;
                    try {
                        compCurent = ((getMapComponenteRandAtribute().get(i).entrySet().stream().filter(componentAttributeLinkEntry -> componentAttributeLinkEntry.getValue() != null && componentAttributeLinkEntry.getValue().getFormula_calcul_coloana() != null && componentAttributeLinkEntry.getValue().getName().equals(atribut.getName())).findFirst()));
                        TextField textField = null;
                        if (compCurent != null && compCurent.isPresent()) {
                            textField = (TextField) compCurent.get().getKey();
                        }

                        if (compCurent != null && textField != null) {
                            c = textField.getValue();
                        }
                        if (c.isEmpty()) {
                            c = "0";
                        }
                    } catch (Exception e) {
                        c = "0";
                    }

                    if (compCurent != null && compCurent.isPresent()) {
                        String cheie = "TOTAL_" + compCurent.get().getValue().getAttributeId();
                        if (mapAtr.containsKey(cheie)) {
                            mapAtr.put(cheie, String.valueOf(Double.parseDouble(mapAtr.get(cheie)) + Double.parseDouble(c)));
                        } else {
                            mapAtr.put(cheie, c);
                        }


                    }


                }

            }

            //END recalculare TOTAL inclusiv randul curent
        }

        //afisare rand total recalculat
        for (AttributeLink atribut : attributeList) {


            //check daca are formula-> calculeaza
            if (atribut.getFormula_calcul_coloana() != null && !atribut.getFormula_calcul_coloana().isEmpty()) {
                //get tr din footer pentru acest atribut
                Optional<Component> trFooter = Optional.ofNullable(componentTabel.getThFooterByid("TOTAL_" + atribut.getAttributeId()));

                //calcul by formula
                DecimalFormat df2 = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                if(atribut.getPrecision()!=null){
                    df2.setMaximumFractionDigits(atribut.getPrecision());
                    df2.setMinimumFractionDigits(atribut.getPrecision());
                }
                try {
                    Double formulaCalcul = 0.0;
                    if (mapAtr != null && mapAtr.size() != 0) {
                        formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtr, atribut.getFormula_calcul_coloana().trim());

                    }
                    //set noua valoare pe tr
                    if (trFooter.isPresent()) {
                        try {

                            trFooter.get().getElement().setText(df2.format(formulaCalcul.isNaN()?0.0d:formulaCalcul));
                        } catch (Exception e) {
                            trFooter.get().getElement().setText("0");

                        }

                    }
                } catch (Exception e) {
                    trFooter.get().getElement().setText("0");

                }

            }
        }
        Map<String, String> mapAtrTotal = new HashMap<>();

        //compunere lista de totaluri pentru utilizare in formula pe atribute simple
        for (ComplexTableComponent tabel : listaTabele) {
            for (String containerId : tabel.getThFooters().keySet()) {
                mapAtrTotal.put(containerId, tabel.getThFooterByid(containerId).getElement().getText());
            }
        }

        //calcul atribute simple by formula si setare valoare noua
        Map<AttributeLink, Component> mapAttrComponentCerere = ((DocumentaSmartForm) BeanUtil.getBean(SmartFormComponentService.class).getSmartForm(smartFormId)).getComponentMapAttr();
        for (AttributeLink attrCerere : mapAttrComponentCerere.keySet()) {

            if (attrCerere.getFormulaCalculPortal() != null && attrCerere.getFormulaCalculPortal().toUpperCase().contains("TOTAL")) {
                try {
                    Double formulaCalcul = 0.0;
                    String formulaCalculStr = "0";
                    if (mapAtrTotal != null && mapAtrTotal.size() != 0) {
                        formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtrTotal, attrCerere.getFormulaCalculPortal().trim());
                        formulaCalculStr = formulaCalcul.toString();
                        if (attrCerere.getPrecision() != null) {
                            formulaCalculStr = BigDecimal.valueOf(formulaCalcul)
                                    .setScale(attrCerere.getPrecision(), RoundingMode.HALF_UP).toPlainString();
                        }
                    }
                    ((TextField) mapAttrComponentCerere.get(attrCerere)).setValue(formulaCalculStr);
                } catch (Exception e) {

                }

            }
        }
        //get label nr randuri & update
        Optional<Component> labelNrRanduri = componentTabel.getChildByid("label_nr_rows_" + attrComplex.getAttributeId());
        if (labelNrRanduri.isPresent()) {
            labelNrRanduri.get().getElement().setText("Nr randuri: " + componentTabel.getNrRows());
        }
    }

    public  HashMap<Integer, HashMap<Component, AttributeLink>> getMapComponenteRandAtribute() {
        return mapComponenteRandAtribute;
    }

    public void setMapComponenteRandAtribute(HashMap<Integer, HashMap<Component, AttributeLink>> mapComponenteRandAtribute) {
        this.mapComponenteRandAtribute = mapComponenteRandAtribute;
    }


    private void addNewRowAttrPrecompletat(ComplexTableComponent component, List<AttributeLink> attributeList, String smartFormId, AttributeLink attrComplex, AtomicInteger index, Optional<String> idParinte, LovList lovList) {
        HtmlContainer newRow = new HtmlContainer("tr"); // => <tr> </tr>

        //calcul nr crt
        int rowIndex = index.getAndIncrement();
        //stop afisare nr crt
        Label labelNrCrt = new Label("");
        labelNrCrt.getElement().setAttribute("td-class", "text-border-center");
        //adaugare celula pentru coloana Adauga

        component.addRowColumn(newRow, new Label("")); // <td> 2 </td>
        //setam nr crt pe prima celula din rand

        //component.addRowColumn(newRow, labelNrCrt); // <td> 2 </td>

        List<DocAttrLink> listaAtributeAdaugate = new ArrayList<>();
        HashMap<Component, AttributeLink> mapComponentaAtribut = new HashMap<>();

        ClickNotifierAnchor saveButon = new ClickNotifierAnchor();
        ClickNotifierAnchor deleteButton = new ClickNotifierAnchor();
        HtmlContainer iconMessageSave = new HtmlContainer("i");
        iconMessageSave.addClassNames("fas", "fa-check");
        //adaugare tag span - tootlip
        saveButon.add(iconMessageSave);

        //la click pe buton save => editez valoarea atributului aferent celulei curente
        saveButon.addClickListener(e -> editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty()));
        //la click pe buton delete => sterg randul curent
        saveButon.getElement().setAttribute("td-class", "text-center");

        //parcurgem lista de atribute si adaugam o celula noua pentru fiecare
        for (AttributeLink at : attributeList) {

            at.setIdDocumentSelectie(attrComplex.getAttributeId());

            listaAtributeAdaugate.add(getConversionService().convert(at, DocAttrLink.class));

            //TODO nu doar textfield
            AttributeLinkDataType atrDataType = getAttributeLinkDataType(at);
            StringAttributeBinderBean binderBean = new StringAttributeBinderBean(smartFormId, at);

            switch (atrDataType) {
                case DATA:
                    DatePicker dataAdaugat = new DatePicker();
                    dataAdaugat.setLocale(UI.getCurrent().getLocale());
                    dataAdaugat.getElement().setAttribute("td-class", "text-border");

                    mapComponentaAtribut.put(dataAdaugat, at);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    //convert String to LocalDate
                    LocalDate localDate = LocalDate.parse(at.getValue(), formatter);
                    dataAdaugat.setValue(localDate);
                    setWidthAttr(dataAdaugat, at);
                    //daca e hidden nu mai adaugam celula
                    // component.addRowColumn(newRow, dataAdaugat);

                    if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                        component.addRowColumn(newRow, dataAdaugat);

                    }
                    if(at.getMandatory() && (at.getValue()==null ||  at.getValue().isEmpty() ) )  {
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, dataAdaugat,rowIndex,
                                new MandatoryAttributeBeanPropertyValidator(""));
                    }
                    SmartFormSupport.aplicareFormulaCalculPortal(rowIndex,smartFormId,mapComponenteRandAtribute);

                    editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty());

                    break;
                case BOOLEAN:
                    Checkbox attrAdaugat = new Checkbox();
                    attrAdaugat.getElement().setAttribute("td-class", "text-border-center");

                    mapComponentaAtribut.put(attrAdaugat, at);
                    component.addRowColumn(newRow, attrAdaugat);
                    setWidthAttr(attrAdaugat, at);
                    if (at.getHidden() != null && at.getHidden().equals(true)) {
                        attrAdaugat.getElement().getStyle().set("display", "none");
                    }
                    if(at.getMandatory() && (at.getValue()==null ||  at.getValue().isEmpty() ) )  {
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrAdaugat,
                                rowIndex,new MandatoryAttributeBeanPropertyValidator(""));
                    }
                    editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty());

                    break;
                case OPT_SELECT_DROPBOX:
                    ComboBox<Lov> lovComboBox = new ComboBox<>();
                    lovComboBox.setItems(BeanUtil.getBean(PS4Service.class).getLovList(at.getLovId()).getLov());
                    lovComboBox.setAllowCustomValue(false);
                    lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
                    lovComboBox.getElement().setAttribute("td-class", "text-border");
                    mapComponentaAtribut.put(lovComboBox, at);

                    setWidthAttr(lovComboBox, at);

                    if (at.getReadOnly() != null && at.getReadOnly()) {
                        lovComboBox.setReadOnly(at.getReadOnly());
                    }
                    if (at.getValue() != null) {
                        Optional<Lov> selected = Optional.empty();
                        if (Optional.ofNullable(at.getValueForLov()).isPresent() && !at.getValueForLov().isEmpty()) {
                            selected = Optional.of(new Lov(at.getValueForLov().get(0).getId(), at.getValueForLov().get(0).getValoare()));

                        }
                        if (selected.isPresent()) {
                            lovComboBox.setValue(selected.get());
                        }
                    }
                    //daca e hidden nu mai adaugam celula
                    // component.addRowColumn(newRow, lovComboBox);
                    if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                        component.addRowColumn(newRow, lovComboBox);

                    }

                    editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty());

                    break;
                case NUMERIC:
                    // attrNumericAdaugat.getElement().setAttribute("type", "number");
                    Optional<Integer> precision = Optional.ofNullable(at.getPrecision());
                    TextField attrNumericAdaugat = new TextField();


                    attrNumericAdaugat.setValueChangeMode(ValueChangeMode.EAGER);

                    if (precision.isPresent() && precision.get().compareTo(0) != 0) {
//09.06.2021 - Neata Georgiana - ANRE - am adaugat acest constructor pentru a permite validare camp numeric cu numar de zecimale specificat

                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrNumericAdaugat,rowIndex,
                                new DoubleAttributeValidator("Completati cu valoare numerica cu " + precision.get() + " zecimale.", precision.get()));
                    } else {
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrNumericAdaugat,rowIndex,
                                new SignIntegerAttributeValidator("Completati cu valoare numerica fara zecimale."));
                    }

                    attrNumericAdaugat.addBlurListener(e -> {
                        if (!SmartFormSupport.validate(smartFormId, at)) {
                            SmartFormSupport.validate(smartFormId, at);

                        }

                    });
                    attrNumericAdaugat.addValueChangeListener(e -> {

                        //calcul atribute by formula_calcul_portal

                        editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.ofNullable(attributeList));
                        SmartFormSupport.aplicareFormulaCalculPortal(rowIndex,smartFormId, mapComponenteRandAtribute);


                    });
                    if (idParinte.isPresent() && !idParinte.get().isEmpty()) {
                        for (String s : idParinte.get().split(","))
                            attrNumericAdaugat.addValueChangeListener(e -> {
                                //calcul atribute PE COLOANA
                                //  ((TextField)(mapComponenteRandAtribute.get(idParinte.get()).entrySet().stream().filter(componentAttributeLinkEntry -> componentAttributeLinkEntry.getValue().getName().equals(at.getName())).findFirst().get().getKey())).setValue("12");
                                SmartFormSupport.aplicareFormulaCalculColoanaPortal(Integer.parseInt(s), at, lovList,mapComponenteRandAtribute);

                            });

                    }
                    mapComponentaAtribut.put(attrNumericAdaugat, at);
                    attrNumericAdaugat.getElement().setAttribute("td-class", "text-border");

                    if (at.getValoareImplicita() != null && !at.getValoareImplicita().isEmpty()) {
                        if (at.getValoareImplicita().contains("^")) {
                            String val = at.getValoareImplicita();
                            for (AttributeLink a : attributeList) {
                                if (at.getValoareImplicita() != null && at.getValoareImplicita().contains("^" + a.getName())) {
                                    val = val.replaceAll("\\^" + a.getName(), a.getValueForLov().get(0).getId());
                                }
                            }

                            String valoare = dmswsPS4Service.getSqlResult(val).getInfo();
                            if (valoare != null && !valoare.isEmpty()) {
                                at.setValue(valoare);
                                attrNumericAdaugat.setValue(valoare);
                            }

                        } else {
                            attrNumericAdaugat.setValue(at.getValoareImplicita());

                        }
                    }
                    setWidthAttr(attrNumericAdaugat, at);
                    //daca e hidden nu mai adaugam celula
                    //component.addRowColumn(newRow, attrNumericAdaugat);
                    if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                        component.addRowColumn(newRow, attrNumericAdaugat);

                    }
                    editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty());

                    break;

                //tratare tip de data FISIER => posibilitate de upload fisier (cu tip de doc= cel setat ca document selectie)
                case FISIER:
                    //adaugare div in care vor fi tinute toate componentele necesare
                    Div containerCell= new Div();
                    containerCell.getElement().setAttribute("td-class", "text-border");

                    //definire textfield care va tine id-ul de fisier incarcat
                    TextField attrIdFisier = new TextField();
                    attrIdFisier.setId(at.getAttributeId()+"-"+rowIndex);
                    attrIdFisier.setReadOnly(true);
                    attrIdFisier.addClassName("width_70_prc");

                    //adaugare textfield care va tine id-ul de fisier incarcat
                    containerCell.addComponentAsFirst(attrIdFisier);

                    //adaugare componenta in map
                    HashMap<Component,AttributeLink> mapComponentaAtributTemp= new HashMap<>();
                    for(Component com:mapComponentaAtribut.keySet()){
                        if(mapComponentaAtribut.get(com).getAttributeId().equals(at.getAttributeId())){
                            mapComponentaAtribut.remove(com);
                            mapComponentaAtribut.put(attrIdFisier,at);

                        }else{
                            mapComponentaAtributTemp.put(attrIdFisier,at);

                        }
                    }
                    mapComponentaAtribut.putAll(mapComponentaAtributTemp);

                    //definire buton upload
                    ClickNotifierAnchor uploadBtn = new ClickNotifierAnchor();
                    uploadBtn.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                    uploadBtn.setHref("javascript:void(0)");
                    HtmlContainer uploadIcon = new HtmlContainer("i");
                    uploadIcon.addClassNames("custom-icon", "icon-up","tooltip_chat");
                    uploadBtn.add(uploadIcon, new Text(""));
                    Span spanuploadIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.upload.label.100"));
                    spanuploadIcon.addClassName("tooltiptext");
                    uploadIcon.add(spanuploadIcon);
                    MemoryBuffer fileBuffer = new MemoryBuffer();
                    Upload upload = new Upload(fileBuffer);
                    upload.setId("upload-for-"+at.getAttributeId()+"-"+rowIndex);
                    upload.addClassName("upload-vaadin-no-file-list");
                    upload.setUploadButton(uploadBtn);
                    upload.setDropAllowed(false);
                    upload.setMaxFiles(1);

                    if(at.getFormatObligatoriu()!=null && !at.getFormatObligatoriu().isEmpty()){
                        String extensiiAcc= "."+at.getFormatObligatoriu().replace(",",",.");
                        upload.getElement().setProperty("accept", extensiiAcc);

                    }else{
                        upload.setAcceptedFileTypes(".png",".jpg", ".jpeg", ".png", ".bmp", ".tiff", ".gif", ".raw", ".pdf",".txt",".docx","doc",".zip",".rar");

                    }
                    upload.addClassName("upload-no-info");

                    Div uploadL = new Div(upload);
                    uploadL.getStyle().set("display", "inline-block");
                    upload.setMaxFileSize(maxFileSize);
                    upload.addFileRejectedListener(e -> {
                        String err=e.getErrorMessage().replaceAll(" ","");
                        if(err.contains("FileisTooBig")){
                            err="FileisTooBig";
                        }
                        UI.getCurrent().getPage().executeJs("swalErrorTop($0)",
                                I18NProviderStatic.getTranslation(err));

                    });
                    upload.addProgressListener(e -> {
                        UI.getCurrent().getPage().executeJs("displayLoadingSpinner();\r\n toggleDisplayState($0,$1);", "v-system-error", "none");
                    });
                    upload.addFinishedListener(e -> {
                        UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none").then(Void.class,value ->editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty()));
                    });
                    AttributeLink finalAttributeLink4 = at;
                    upload.addSucceededListener(e -> {
                        DocObligatoriuExtra fisierUploaded= new DocObligatoriuExtra(new DocObligatoriu());
                        uploadFileSuccedEvent(e, upload, fileBuffer, finalAttributeLink4,fisierUploaded);
                        finalAttributeLink4.setValue(fisierUploaded.getUploadedFileId().toString());
                        //mapComponentaAtribut.put(attrIdFisier, finalAttributeLink4);
                        attrIdFisier.setValueChangeMode(ValueChangeMode.ON_CHANGE);
                        attrIdFisier.setValue(fisierUploaded.getUploadedFileName()+ "("+fisierUploaded.getUploadedFileId().toString()+")");
                        editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty());

                        //ascundere buton upload
                        containerCell.remove(uploadL);
                        //definire butoane de download & delete
                        Anchor download = new Anchor();

                        download.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                        HtmlContainer downloadIcon = new HtmlContainer("i");
                        downloadIcon.addClassNames("custom-icon", "icon-down","tooltip_chat");
                        download.add(downloadIcon, new Text(""));
                        Span spandownloadIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.download.label"));
                        spandownloadIcon.addClassName("tooltiptext");
                        downloadIcon.add(spandownloadIcon);


                        download.setHref(StreamResourceUtil.getStreamResource(
                                fisierUploaded.getUploadedFileName(),
                                fisierUploaded.getDownloadLinkForUploadedFile()));
                        download.setTarget("_blank");
                        ClickNotifierAnchor delete = new ClickNotifierAnchor();
                        delete.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                        delete.setHref("javascript:void(0)");
                        HtmlContainer deleteIcon = new HtmlContainer("i");
                        deleteIcon.addClassNames("custom-icon", "icon-delete","tooltip_chat");
                        delete.add(deleteIcon, new Text(""));
                        Span spandeleteIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.delete.label"));
                        spandeleteIcon.addClassName("tooltiptext");
                        deleteIcon.add(spandeleteIcon);
                        delete.addClickListener(eDel -> {
                                    deleteFileUploaded(delete, fisierUploaded, containerCell, delete, download, attrIdFisier,uploadL);
                                    //setare valoare atribut ""
                                    finalAttributeLink4.setValue("");
                                    //eliminare id fisier din textfield
                                    attrIdFisier.setValue("");
                                    //salvare "" in mapul de componente
                                    editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty());

                                    if(at.getMandatory() && (at.getValue()==null ||  at.getValue().trim().isEmpty())) {
                                        SmartFormSupport.bindWithRow(smartFormId,
                                                binderBean, attrIdFisier,
                                                rowIndex,new MandatoryAttributeBeanPropertyValidator(""));
                                    }
                                }
                        );
                        //adaugare butoane download & delete
                        containerCell.add(download, delete);

                    });

                    containerCell.add(uploadL);
                    component.addRowColumn(newRow, containerCell);
                    if(at.getMandatory() && (at.getValue()==null ||  at.getValue().trim().isEmpty() ) )  {
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrIdFisier,
                                rowIndex,new MandatoryAttributeBeanPropertyValidator(""));
                    }

                    break;
                default:
                    TextField attrTextAdaugat = new TextField();
                    mapComponentaAtribut.put(attrTextAdaugat, at);
                    attrTextAdaugat.getElement().setAttribute("td-class", "text-border");
                    if (at.getValoareImplicita() != null && !at.getValoareImplicita().isEmpty()) {
                        if (at.getValoareImplicita().contains("^")) {
                            String val = at.getValoareImplicita();
                            for (AttributeLink a : attributeList) {
                                if (at.getValoareImplicita() != null && at.getValoareImplicita().contains("^" + a.getName())) {
                                    val = val.replaceAll("\\^" + a.getName(), a.getValueForLov().get(0).getId());
                                }
                            }

                            String valoare = dmswsPS4Service.getSqlResult(val).getInfo();
                            if (valoare != null && !valoare.isEmpty()) {
                                at.setValue(valoare);
                                attrTextAdaugat.setValue(valoare);
                            }

                        } else {
                            attrTextAdaugat.setValue(at.getValoareImplicita());

                        }
                    }
                    setWidthAttr(attrTextAdaugat, at);
                    if (at.getValue() != null) {
                        attrTextAdaugat.setValue(at.getValue());

                    }
                    //daca e hidden nu mai adaugam celula
                    //component.addRowColumn(newRow, attrTextAdaugat);

                    if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                        component.addRowColumn(newRow, attrTextAdaugat);

                    }
                    editInfoRow(rowIndex,  deleteButton, smartFormId, attrComplex, component,Optional.empty());
                    if(at.getMandatory() && (at.getValue()==null ||  at.getValue().isEmpty() ) )  {
                        SmartFormSupport.bindWithRow(smartFormId,
                                binderBean, attrTextAdaugat,
                                rowIndex,new MandatoryAttributeBeanPropertyValidator(""));
                    }
                    break;

            }


        }

        //pun in map randul, componenta si atributul de care e legat
        mapComponenteRandAtribute.put(rowIndex, mapComponentaAtribut);
        mapIdAtrComplexRowIndexes.put(rowIndex, attrComplex.getAttributeId());

        //adaugam randul nou la tabel
        component.addNewRow(newRow);
        //Facem validare
        new MandatoryAttributeBeanPropertyValidator("").apply(null, new ValueContext(component, component, new Locale("ro_RO")));

        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");

    }

    private Component setWidthAttr(Component component, AttributeLink attributeLink) {

        Optional<Double> hasWidthPx = Optional.ofNullable(attributeLink.getWidthPx());
        if (!hasWidthPx.isPresent() || hasWidthPx.get().equals(0D)) {
            hasWidthPx = Optional.of(100D);
            attributeLink.setWidthPx(100D);
        }
        Optional<Double> hasWidthPercent = Optional.ofNullable(attributeLink.getWidthPercent());
        if (HasSize.class.isAssignableFrom(component.getClass())) {
            if (hasWidthPx.isPresent() && hasWidthPx.get().compareTo(0d) > 0) {
                ((HasSize) component).setWidth(attributeLink.getWidthPx() + "px");
            }
            if (hasWidthPercent.isPresent() && hasWidthPercent.get().compareTo(0d) > 0) {
                ((HasSize) component).setWidth(attributeLink.getWidthPercent() + "%");
            }
        }
        return component;

    }

    private Component setWidthAttrDoc(Component component, DocAttrLink attributeLink) {

        Optional<Double> hasWidthPx = Optional.ofNullable(attributeLink.getWidthPx());
        if (!hasWidthPx.isPresent() || hasWidthPx.get().equals(0D)) {
            hasWidthPx = Optional.of(100D);
            attributeLink.setWidthPx(100D);
        }
        Optional<Double> hasWidthPercent = Optional.ofNullable(attributeLink.getWidthPercent());
        if (HasSize.class.isAssignableFrom(component.getClass())) {
            if (hasWidthPx.isPresent() && hasWidthPx.get().compareTo(0d) > 0) {
                ((HasSize) component).setWidth(attributeLink.getWidthPx() + "px");
            }
            if (hasWidthPercent.isPresent() && hasWidthPercent.get().compareTo(0d) > 0) {
                ((HasSize) component).setWidth(attributeLink.getWidthPercent() + "%");
            }
        }
        return component;

    }

    public static AttributeLinkDataType getAttributeLinkDataType(AttributeLink attributeLink) {
        Optional<String> dataTypeStr = Optional.ofNullable(attributeLink.getDataType());
        if (dataTypeStr.isPresent() && !dataTypeStr.get().isEmpty()
                && Stream.of(AttributeLinkDataType.values()).filter(e -> e.name().equals(dataTypeStr.get())).count() == 1) {
            if (Optional.ofNullable(attributeLink.getLovId()).isPresent() && attributeLink.getLovId().compareTo(0) != 0) {
                if (attributeLink.getNrSelectiiLov().compareTo(1) == 0) {
                    if (AttributeLinkDataType.valueOf(dataTypeStr.get()).equals(AttributeLinkDataType.OPT_GROUP)) {
                        return AttributeLinkDataType.OPT_SELECT;
                    } else {
                        return AttributeLinkDataType.OPT_SELECT_DROPBOX;
                    }
                } else {
                    if (AttributeLinkDataType.valueOf(dataTypeStr.get()).equals(AttributeLinkDataType.OPT_GROUP)) {
                        return AttributeLinkDataType.OPT_SELECT_MULTIVALUE;
                    } else {
                        return AttributeLinkDataType.OPT_SELECT_DROPBOX_MULTIVALUE;
                    }
                }
            } else if (Optional.ofNullable(attributeLink.getDenumireTipSelectie()).isPresent() && attributeLink.getDenumireTipSelectie().toUpperCase().equals("COMPLEX")) {
                return AttributeLinkDataType.COMPLEX;
            } else {
                try {
                    AttributeLinkDataType attributeLinkDataType = AttributeLinkDataType.valueOf(dataTypeStr.get());
                    return attributeLinkDataType;
                } catch (Throwable e) {
                }
            }
        }
        return AttributeLinkDataType.TEXT;
    }

    public static AttributeLinkDataType getDocAttributeLinkDataType(DocAttrLink attributeLink) {
        Optional<String> dataTypeStr = Optional.ofNullable(attributeLink.getDataType());
        if (dataTypeStr.isPresent() && !dataTypeStr.get().isEmpty()
                && Stream.of(AttributeLinkDataType.values()).filter(e -> e.name().equals(dataTypeStr.get())).count() == 1) {
            if (Optional.ofNullable(attributeLink.getLovId()).isPresent() && attributeLink.getLovId().compareTo(Long.valueOf(0)) != 0) {
                if ((attributeLink.getNrSelectiiLov() != null && attributeLink.getNrSelectiiLov().compareTo(1) == 0) || attributeLink.getNrSelectiiLov() == null) {
                    if (AttributeLinkDataType.valueOf(dataTypeStr.get()).equals(AttributeLinkDataType.OPT_GROUP)) {
                        return AttributeLinkDataType.OPT_SELECT;
                    } else {
                        return AttributeLinkDataType.OPT_SELECT_DROPBOX;
                    }
                } else {
                    if (AttributeLinkDataType.valueOf(dataTypeStr.get()).equals(AttributeLinkDataType.OPT_GROUP)) {
                        return AttributeLinkDataType.OPT_SELECT_MULTIVALUE;
                    } else {
                        return AttributeLinkDataType.OPT_SELECT_DROPBOX_MULTIVALUE;
                    }
                }
            } else if (Optional.ofNullable(attributeLink.getDenumireTipSelectie()).isPresent() && attributeLink.getDenumireTipSelectie().toUpperCase().equals("COMPLEX")) {
                return AttributeLinkDataType.COMPLEX;
            } else {
                try {
                    AttributeLinkDataType attributeLinkDataType = AttributeLinkDataType.valueOf(dataTypeStr.get());
                    return attributeLinkDataType;
                } catch (Throwable e) {
                    // logger.error("nu exista tipul:\t" + dataTypeStr.get());
                }
            }
        }
        return AttributeLinkDataType.TEXT;
    }

    private void saveInfoRow(int rowIndex, ClickNotifierAnchor saveButon, ClickNotifierAnchor deleteButton, String smartFormId, AttributeLink attrComplex, ComplexTableComponent tableComponent) {
        try {
            HashMap<Component, AttributeLink> mapComponentaAtribut = null;
            for (Integer row : mapComponenteRandAtribute.keySet()) {
                if (row.equals(rowIndex)) {
                    mapComponentaAtribut = mapComponenteRandAtribute.get(row);
                }
            }
            //Todo recalculfooter


            List<DocAttrLink> listaAtributeAdaugate = new ArrayList<>();

            Map<String, String> mapAtr = new HashMap<>();
            //calcul map atribut->valoare
            //setez ca total hidden valoare curenta
            Map<String, HtmlContainer> comp = tableComponent.getThFooters();
            for (HtmlContainer itemTr : comp.values()) {
                String currentVal = "0";
                //daca il gasesc -> iau valoarea curenta
                if (itemTr != null) {
                    currentVal = ((HtmlContainer) itemTr).getElement().getText();
                }
                mapAtr.put(String.valueOf(itemTr.getId().get()), currentVal);

            }
            for (Component component : mapComponentaAtribut.keySet()) {

                AttributeLink atribut = mapComponentaAtribut.get(component);
                if (atribut.getValidare_portal() != null) {
                    if (atribut.getValidare_portal().toUpperCase().equals("VALIDARE GRADE")) {
                        Integer[] error = {0};
                        for (Integer i : mapComponenteRandAtribute.keySet()) {
                            try {
                                mapComponenteRandAtribute.get(i).entrySet().stream().forEach(attributeLink -> {
                                    String valoare = ((Lov) ((ComboBox) attributeLink.getKey()).getValue()).getValoare();
                                    if (valoare.replace("Prel ", "").contains(((Lov) ((ComboBox) component).getValue()).getValoare().replace("Prel ", ""))) {
                                        error[0]++;
                                    }
                                });
                            } catch (Exception e) {
                                Integer x = 1;
                            }
                            if (error[0] > 1) {
                                throw new Exception("Exista deja acest grad/prelungire!");
                            }
                        }
                    }else if (atribut.getValidare_portal().toUpperCase().equals("EXCLUDE_INCOMPATIBILITATI")) {
                        Integer[] error = {0};
                        final String[] mesajEroare = {""};
                        Lov lovCurent= (Lov) ((ComboBox) component).getValue();
                        for (Integer i : mapComponenteRandAtribute.keySet()) {
                            try {
                                mapComponenteRandAtribute.get(i).entrySet().stream().forEach(attributeLink -> {
                                    //get lov de pe alta rand
                                    Lov lov = ((Lov) ((ComboBox) attributeLink.getKey()).getValue());
                                    //daca in campul de ids incopatibilitati se
                                    if (lovCurent.getFormulaCalcul()!=null && lovCurent.getFormulaCalcul().contains("["+lov.getValoare()+"]")) {
                                        error[0]++;
                                        mesajEroare[0] +="<div style='text-align:left'> Tipurile de atestate: <br>-"+lovCurent.getValoare() +"  <br>-" + lov.getValoare() +"<br>nu pot fi selectate simultan deoarece acestea se includ.</div>";
                                    }
                                });
                            } catch (Exception e) {
                                Integer x = 1;
                            }
                            if (error[0] > 1) {
                                throw new Exception(mesajEroare[0]);
                            }
                        }
                    }
                }
                atribut.setValue(getValue(component, atribut));
                mapComponentaAtribut.put(component, atribut);
                listaAtributeAdaugate.add(getConversionService().convert(atribut, DocAttrLink.class));
                //NG - 21.07.2021 - ANRE - Verificare daca sunt completate corect celulele; Daca nu, intoarce mesaj si nu salveaza randul
                if (!SmartFormSupport.validate(smartFormId, atribut)) {
                    //UI.getCurrent().getPage().executeJs("swalErrorTop($0)", I18NProviderStatic.getTranslation("ps4.ecetatean.form.swal.error"));

                    return;
                }


                for (int i = 1; i <= getMapComponenteRandAtribute().size(); i++) {
                    //Pentru tabelul curent
                    //check ca row index face parte din tabel curent
                    if (mapIdAtrComplexRowIndexes.get(i) != null && mapIdAtrComplexRowIndexes.get(i).equals(attrComplex.getAttributeId())) {
                        String c = "0";
                        Optional<Map.Entry<Component, AttributeLink>> compCurent = null;
                        try {


                            compCurent = ((getMapComponenteRandAtribute().get(i).entrySet().stream().filter(componentAttributeLinkEntry -> componentAttributeLinkEntry.getValue() != null && componentAttributeLinkEntry.getValue().getFormula_calcul_coloana() != null && componentAttributeLinkEntry.getValue().getName().equals(atribut.getName())).findFirst()));
                            TextField textField = null;
                            if (compCurent != null && compCurent.isPresent()) {
                                textField = (TextField) compCurent.get().getKey();
                            }

                            if (compCurent != null && textField != null) {
                                c = textField.getValue();
                            }
                            if (c.isEmpty()) {
                                c = "0";
                            }
                        } catch (Exception e) {
                            c = "0";
                        }

                        if (compCurent != null && compCurent.isPresent()) {
                            mapAtr.put(compCurent.get().getValue().getName(), c);

                        }
                    }

                }

            }
            for (Component component : mapComponentaAtribut.keySet()) {

                AttributeLink atribut = mapComponentaAtribut.get(component);

                //check daca are formula-> calculeaza
                if (atribut.getFormula_calcul_coloana() != null && !atribut.getFormula_calcul_coloana().isEmpty()) {
                    //get tr din footer pentru acest atribut
                    Optional<Component> trFooter = Optional.ofNullable(tableComponent.getThFooterByid("TOTAL_" + atribut.getAttributeId()));


                    //calcul by formula
                    DecimalFormat df2 = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    if(atribut.getPrecision()!=null){
                        df2.setMaximumFractionDigits(atribut.getPrecision());
                        df2.setMinimumFractionDigits(atribut.getPrecision());
                    }
                    try {
                        Double formulaCalcul = 0.0;
                        if (mapAtr != null && mapAtr.size() != 0) {
                            formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtr, atribut.getFormula_calcul_coloana().trim());

                        }
                        //set noua valoare pe tr
                        if (trFooter.isPresent()) {
                            try {

                                trFooter.get().getElement().setText(df2.format(formulaCalcul.isNaN()?0.0d:formulaCalcul));
                            } catch (Exception e) {

                            }

                        }
                    } catch (Exception e) {
                        trFooter.get().getElement().setText("0");

                    }



                }
            }


            //adaugam celula cu butonul setari
            if(mapComponenteRandAtribute!=null && mapComponenteRandAtribute.containsKey(rowIndex)){
                mapComponenteRandAtribute.replace(rowIndex, mapComponentaAtribut);

            }else{
                mapComponenteRandAtribute.put(rowIndex, mapComponentaAtribut);

            }            mapIdAtrComplexRowIndexes.put(rowIndex, attrComplex.getAttributeId());

            saveButon.getStyle().set("display", "none");
            deleteButton.getStyle().set("display", "block");
            //iau mapul de randuri
            List<RowAttrComplexList> mapRandAtribute2 = mapAtributeComplexe.get(attrComplex.getAttributeId());

            if (mapRandAtribute2 == null) {
                mapRandAtribute2 = new ArrayList<>();
            }
            //adaug la el
            mapRandAtribute2.add(new RowAttrComplexList(rowIndex, listaAtributeAdaugate));
            //il setez
            mapAtributeComplexe.put(attrComplex.getAttributeId(), mapRandAtribute2);
            smartFormComponentService.addAttrListForAttributeComplex(smartFormId, attrComplex, mapRandAtribute2);


        } catch (Exception e) {
            UI.getCurrent().getPage().executeJs("swalErrorTop($0);", e.getMessage());
        }

    }

    private void editInfoRow(int rowIndex,ClickNotifierAnchor deleteButton, String smartFormId, AttributeLink attrComplex, ComplexTableComponent tableComponent, Optional<List<AttributeLink>> listaAtributeAtrComplex) {



        try {
            //preiau map componente si atribute rand curent
            HashMap<Component, AttributeLink> mapComponentaAtribut = null;
            for (Integer row : mapComponenteRandAtribute.keySet()) {
                if (row.equals(rowIndex)) {
                    mapComponentaAtribut = mapComponenteRandAtribute.get(row);
                    break;
                }
            }

            List<DocAttrLink> listaAtributeAdaugate = new ArrayList<>();

            Map<String, String> mapAtr = new HashMap<>();
            //calcul map atribut->valoare

            if (mapComponentaAtribut != null) {
                for (Component component : mapComponentaAtribut.keySet()) {

                    AttributeLink atribut = mapComponentaAtribut.get(component);

                    //Validari speciale
                    if (atribut.getValidare_portal() != null) {
                        try {

                            if (atribut.getValidare_portal().toUpperCase().equals("VALIDARE GRADE")) {
                                Integer[] error = {0};
                                for (Integer i : mapComponenteRandAtribute.keySet()) {
                                    try {
                                        mapComponenteRandAtribute.get(i).entrySet().stream().forEach(attributeLink -> {
                                            String valoare = ((Lov) ((ComboBox) attributeLink.getKey()).getValue()).getValoare();
                                            if (valoare.replace("Prel ", "").contains(((Lov) ((ComboBox) component).getValue()).getValoare().replace("Prel ", ""))) {
                                                error[0]++;
                                            }
                                        });
                                    } catch (Exception e) {
                                        Integer x = 1;
                                    }
                                    if (error[0] > 1) {
                                        throw new Exception("Exista deja acest grad/prelungire!");
                                    }
                                }
                            } else if (atribut.getValidare_portal().toUpperCase().equals("CHECK_AUTORIZATIE_GN")) {

                                String[] idAutorizatie = {null};

                                mapComponenteRandAtribute.get(rowIndex).entrySet().stream().forEach(attributeLink -> {
                                    idAutorizatie[0] = ((Lov) ((ComboBox) attributeLink.getKey()).getValue()).getId();
                                });


                                String idTert = null;

                                Map<Long, DocAttrLink> currentAttrsList = SmartFormSupport.getAttributeLinkMap(smartFormId);
                                for (Map.Entry<Long,DocAttrLink> x :currentAttrsList.entrySet()){
                                    if(x.getValue().getAttributeCode().equals("TERT|ID_TERT")){
                                        idTert = x.getValue().getValue();

                                    }
                                }

                                String sql = "select COUNT(0) valoare from TERTI_ANRE where id_tert = " + idTert + "and activ = 1 and ID_DOMENIU = 2 and nivel = 2 and ID_DOCUMENT = (select ID_DOCUMENT from TIP_AUTORIZATIE_LVL2 WHERE ID = " + idAutorizatie[0] + ")  and VALABILITATE_PANA_LA >  DATEADD(DAY, 30, SYSDATETIME())";
                                String countAutorizatii = dmswsPS4Service.getSqlResult(sql).getInfo();
                                Integer autorizatiiValabilePtPrel = 0;

                                try{
                                    autorizatiiValabilePtPrel = Integer.parseInt(countAutorizatii);
                                } catch (Exception e){
                                    autorizatiiValabilePtPrel = 0;
                                }

                                if (autorizatiiValabilePtPrel == 0){
                                    throw new Exception("Nu exista nicio autorizatie de acest tip disponibila pentru prelungire. \n Va rugam sa completati o cerere de acordare!");
                                }

                            }
                            else if (atribut.getValidare_portal().toUpperCase().equals("EXCLUDE_INCOMPATIBILITATI")) {
                                Integer[] error = {0};
                                final String[] mesajEroare = {""};
                                Lov lovCurent = (Lov) ((ComboBox) component).getValue();
                                for (Integer i : mapComponenteRandAtribute.keySet()) {
                                    try {
                                        mapComponenteRandAtribute.get(i).entrySet().stream().forEach(attributeLink -> {
                                            //get lov de pe alta rand
                                            Lov lov = ((Lov) ((ComboBox) attributeLink.getKey()).getValue());
                                            //daca in campul de ids incopatibilitati se
                                            if (lovCurent.getFormulaCalcul() != null && lovCurent.getFormulaCalcul().contains("[" + lov.getId() + "]")) {
                                                error[0]++;
                                                mesajEroare[0] += "<div style='text-align:left'> Tipurile de atestate: <br>- " + lovCurent.getValoare() + "<br>- " + lov.getValoare() + "<br>nu pot fi selectate simultan deoarece acestea se includ.</div>";
                                            }
                                        });
                                    } catch (Exception e) {
                                        Integer x = 1;
                                    }
                                    if (error[0] > 0) {
                                        ((ComboBox) component).setValue(null);
                                        throw new Exception(mesajEroare[0]);
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            UI.getCurrent().getPage().executeJs("swalErrorTop($0);", e.getMessage());
                            ComponentUtil.fireEvent(deleteButton, new ClickEvent<>(deleteButton));

                            return;
                        }


                    }
                    //END Validari speciale


                    if(!atribut.getDataType().equals("FISIER")){
                        String getVal= getValue(component, atribut);
                        if(getVal!=null){

                            atribut.setValue(getValue(component, atribut));
                            mapComponentaAtribut.put(component, atribut);
                        }
                    }else{
                        mapComponentaAtribut.put(component, atribut);
                    }

                    //********************TRY FIX SET VALUE RAND NOU

                    if(attrComplex.getAttrsOfComplex()!=null && attrComplex.getAttrsOfComplex().size()!=0) {
                        for(RowAttrComplexList rowAttr: attrComplex.getAttrsOfComplex()){
                            if(rowAttr.getRowNumber().equals(rowIndex)){
                                for(DocAttrLink doc: rowAttr.getListaAtribute()){
                                    if(atribut.getAttributeId().equals(Integer.valueOf(doc.getAttributeId().toString()))){
                                        doc.setValue(atribut.getValue());
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    DocAttrLink docAttrLink= getConversionService().convert(atribut, DocAttrLink.class);
                    listaAtributeAdaugate.add(docAttrLink);


                    //NG - 21.07.2021 - ANRE - Verificare daca sunt completate corect celulele; Daca nu, intoarce mesaj si nu salveaza randul

                }
            }


            if(mapComponenteRandAtribute!=null && mapComponenteRandAtribute.containsKey(rowIndex)){
                mapComponenteRandAtribute.replace(rowIndex, mapComponentaAtribut);

            }else{
                mapComponenteRandAtribute.put(rowIndex, mapComponentaAtribut);

            }
            mapIdAtrComplexRowIndexes.put(rowIndex, attrComplex.getAttributeId());

            //saveButon.getStyle().set("display", "none");
            deleteButton.getStyle().set("display", "block");
            //iau mapul de randuri
            List<RowAttrComplexList> mapRandAtribute2 = mapAtributeComplexe.get(attrComplex.getAttributeId());

            if (mapRandAtribute2 == null) {
                mapRandAtribute2 = new ArrayList<>();
            }

            List<RowAttrComplexList> mapRandAtribute3 = new ArrayList<>(mapRandAtribute2);

            if (mapRandAtribute2 != null && mapRandAtribute2.size() != 0) {
                for (RowAttrComplexList rowAttr : mapRandAtribute3) {
                    if (rowAttr.getRowNumber().equals(rowIndex)) {
                        mapRandAtribute2.remove(rowAttr);
                    }
                }
            }

            //adaug la map randul cu noile valori
            mapRandAtribute2.add(new RowAttrComplexList(rowIndex, listaAtributeAdaugate));

            //setare  rand cu valori noi in map ul general
            mapAtributeComplexe.put(attrComplex.getAttributeId(), mapRandAtribute2);
            smartFormComponentService.addAttrListForAttributeComplex(smartFormId, attrComplex, mapRandAtribute2);
            smartFormComponentService.setMapComponenteRandAtribute(mapComponenteRandAtribute);
        } catch (Exception e) {
            UI.getCurrent().getPage().executeJs("swalErrorTop($0);", e.getMessage());

        }

        if(listaAtributeAtrComplex.isPresent()){
            updateAttrComplexFooter(tableComponent, listaAtributeAtrComplex.get(), smartFormId, attrComplex);
        }

        //calcul atribute simple by formula si setare valoare noua
        Map<AttributeLink, Component> mapAttrComponentCerere = new HashMap<>();
        try{
            mapAttrComponentCerere=((DocumentaSmartForm) BeanUtil.getBean(SmartFormComponentService.class).getSmartForm(smartFormId)).getComponentMapAttr();
        }catch (Exception e){

        }
        Map<String, String> mapAtrTotal = new HashMap<>();

        for (ComplexTableComponent tabel : listaTabele) {
            for (String containerId : tabel.getThFooters().keySet()) {
                mapAtrTotal.put(containerId, tabel.getThFooterByid(containerId).getElement().getText().replace(",",""));
            }
        }

        for (AttributeLink attrCerere : mapAttrComponentCerere.keySet()) {
            mapAtrTotal.put(attrCerere.getName(), getValue(mapAttrComponentCerere.get(attrCerere),attrCerere));
        }

        //sortam lista de componente conform rand, coloana pentru a fi calculate in ordine
        Set<AttributeLink> listaMapAttrComponentCereerKeysSet = mapAttrComponentCerere.keySet();
        Comparator<AttributeLink> comparator = Comparator.comparing(a -> a.getRand());
        comparator = comparator.thenComparing(Comparator.comparing(a -> a.getColoana()));

        List<AttributeLink> listaMapAttrComponentCereerKeys = listaMapAttrComponentCereerKeysSet.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        for (AttributeLink attrCerere : listaMapAttrComponentCereerKeys) {

            if (attrCerere.getFormulaCalculPortal() != null && attrCerere.getFormulaCalculPortal().toUpperCase().contains("DOUBLE")) {
                try {
                    Double formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtrTotal, attrCerere.getFormulaCalculPortal().trim());
                    String formulaCalculStr = "0";
                    formulaCalculStr = formulaCalcul.toString();
                    if (attrCerere.getPrecision() != null) {
                        formulaCalculStr = BigDecimal.valueOf(formulaCalcul)
                                .setScale(attrCerere.getPrecision(), RoundingMode.HALF_UP).toPlainString();
                    }

                    ((TextField) mapAttrComponentCerere.get(attrCerere)).setValue(formulaCalculStr);
                    mapAtrTotal.put(attrCerere.getName(), formulaCalculStr);

                } catch (Exception e) {
                }

            }
        }

        //reluare calcul valori pentru calcul recursiv
        for (AttributeLink attrCerere : listaMapAttrComponentCereerKeys) {
            mapAtrTotal.put(attrCerere.getName(), getValue(mapAttrComponentCerere.get(attrCerere),attrCerere));
        }

        for (AttributeLink attrCerere : mapAttrComponentCerere.keySet()) {

            if (attrCerere.getFormulaCalculPortal() != null && attrCerere.getFormulaCalculPortal().toUpperCase().contains("DOUBLE")) {
                try {
                    Double formulaCalcul = (Double) SpelParserUtil.parseSpel(mapAtrTotal, attrCerere.getFormulaCalculPortal().trim());
                    String formulaCalculStr = "0";
                    formulaCalculStr = formulaCalcul.toString();
                    if (attrCerere.getPrecision() != null) {
                        formulaCalculStr = BigDecimal.valueOf(formulaCalcul)
                                .setScale(attrCerere.getPrecision(), RoundingMode.HALF_UP).toPlainString();
                    }

                    ((TextField) mapAttrComponentCerere.get(attrCerere)).setValue(formulaCalculStr);
                    mapAtrTotal.put(attrCerere.getName(), formulaCalculStr);

                } catch (Exception e) {
                }

            }
        }
        //END  reluare calcul valori pentru calcul recursiv

    }


    private String getValue(Component component, AttributeLink attributeLink) {

        AttributeLinkDataType atrDataType = getAttributeLinkDataType(attributeLink);


        try {

            switch (atrDataType) {
                case DATA:
                    return ((DatePicker) component).getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

                case BOOLEAN:
                    return String.valueOf(((Checkbox) component).getValue()).equals("true") ? "1" : "0";
                case OPT_SELECT_DROPBOX:
                    if (attributeLink.getMultipleSelection() != null && attributeLink.getMultipleSelection() && component instanceof MultiselectComboBox) {
                        MultiselectComboBox<Lov> multiselectComboBox = (MultiselectComboBox) component;
                        StringBuilder sb = new StringBuilder();
                        String sep = "";

                        Set<Lov> lovSet = multiselectComboBox.getValue();
                        if (lovSet != null) {
                            for (Lov lov : lovSet) {
                                sb.append(sep).append((lov.getId() + ""));
                                sep = ",";
                            }
                        }

                        return sb.toString();
                    } else {
                        String getVal= null;
                        try{
                            getVal = ((Lov) ((HasValue) component).getValue()).getId();
                        }catch (Exception e){

                        }
                        return getVal==null?getVal:getVal+"";
                    }
                default: {
                    TextField textFieldComponent = ((TextField) component);
                    String valueTf = textFieldComponent.getValue();

                    if (valueTf != null) {
                        if (textFieldComponent.getClassNames().contains("thousands")) {
                            valueTf = valueTf.replace(",", "");
                        }
                    }
                    return valueTf;
                }

            }
        } catch (Exception e) {
            return "";
        }
    }

    private void addNewRowAttrInitial(ComplexTableComponent component, List<AttributeLink> attributeList, String smartFormId, AttributeLink attrComplex, ClickNotifierAnchor anchorAdd) {
        //21.07.2021 - Neata Georgiana - ANRE - golire map la add tabel nou
        if (mapIdAtrComplexRowIndexes != null && mapIdAtrComplexRowIndexes.size() != 0) {
            HashMap<Integer, Integer> hash = new HashMap<>(mapIdAtrComplexRowIndexes);
            for (Integer rowIndex : hash.keySet()) {
                if (mapIdAtrComplexRowIndexes.get(rowIndex).equals(attrComplex.getAttributeId())) {
                    mapIdAtrComplexRowIndexes.remove(rowIndex);
                }
            }
        }

        // mapComponenteRandAtribute.clear();
        HtmlContainer newRow = new HtmlContainer("tr"); // => <tr> </tr>
        //22.06.2021 - Neata Georgiana - ANRE - afisare atribut adauga rand la stanga
        //adaugam celula cu butonul +
        anchorAdd.getElement().setAttribute("td-class", "text-center-blue");
        anchorAdd.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm-s", "btn_tooltip");
        anchorAdd.getStyle().set("cursor", "pointer");
     /*   anchorAdd.getStyle().set("margin","-10em");
        anchorAdd.getStyle().set("padding","10em");*/

        component.addRowColumn(newRow, anchorAdd);

        //stop afisare nr crt
        //component.addRowColumn(newRow, labelNrCrt); // <td> 2 </td>

        //parcurgem lista de atribute si adaugam o celula noua pentru fiecare
        for (AttributeLink at : attributeList) {
            Label label = new Label();
            label = (Label) setWidthAttr(label, at);
            component.addRowColumn(newRow, label);

        }

        //adaugam celula cu butonul setari
        component.addRowColumn(newRow, new Label(""));
        //adaugam randul nou la tabel
        component.addNewRowFooter(newRow);
        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");


    }

    private void addNewRowPaginare(ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId, AttributeLink attrComplex) {
        if(attrComplex.getAttrsOfComplex().size()>NR_ROWS_TABEL){
             UnorderedList paginationContainer = new UnorderedList();

             Nav servicePagination = new Nav(paginationContainer);

             Div serviceListPagination = new Div(servicePagination);


            paginationContainer.addClassNames("pagination", "justify-content-end");
            servicePagination.getElement().setAttribute("aria-label", "Services Pagination");
            serviceListPagination.addClassName("pagination_container");

           // HtmlContainer newRow = new HtmlContainer("tr"); // => <tr> </tr>

            Integer nrPag=0;
            Integer totalNrPag=(attrComplex.getAttrsOfComplex().size()/NR_ROWS_TABEL)+1;
            if(attrComplex.getAttrsOfComplex().size()%NR_ROWS_TABEL==0){
                totalNrPag=attrComplex.getAttrsOfComplex().size()/NR_ROWS_TABEL;
            }
            buildPagination(nrPag,totalNrPag,paginationContainer, component, listaAtributeAtrComplex,smartFormId,attrComplex,0);


            component.add(serviceListPagination);
        }
    }

    protected void buildPagination(Integer page, Integer totalPages,UnorderedList paginationContainer,ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId, AttributeLink attrComplex, Integer indexStart) {
        if(paginationContainer.getChildren().count() > 0) {
            paginationContainer.removeAll();
        }
        if (page != 0) {
            HtmlContainer iconStart = new HtmlContainer("i");
            iconStart.addClassNames("fas", "fa-chevron-circle-left");
            buildPager(paginationContainer,0, "prev", Optional.of(iconStart), component, listaAtributeAtrComplex,smartFormId,attrComplex);


            HtmlContainer iconPrev = new HtmlContainer("i");
            iconPrev.addClassNames("fas", "fa-chevron-left");
            buildPager(paginationContainer,page - 1, "prev", Optional.of(iconPrev), component, listaAtributeAtrComplex,smartFormId,attrComplex);
        }

        int midShow= NR_MAX_PAGES_TABEL/2;
        int nrPageStart= page-midShow;
        int nrMaxPagesShow=page+midShow;
        boolean endModifiedByStart=false;
        if(  page-midShow<0){
            nrPageStart =0;
            nrMaxPagesShow= NR_MAX_PAGES_TABEL;
            endModifiedByStart=true;
        }
        if(nrMaxPagesShow>totalPages){
            nrMaxPagesShow=totalPages;
            if(!endModifiedByStart){
                nrPageStart=totalPages-NR_MAX_PAGES_TABEL;
                if(nrPageStart<0){
                    nrPageStart=0;
                }
            }
        }
        for(int i = nrPageStart; i < nrMaxPagesShow; i++) {
           // indexStart= i*NR_ROWS_TABEL;
            buildPager(paginationContainer,i, i == page ? "active" : "", Optional.empty(), component, listaAtributeAtrComplex,smartFormId,attrComplex);
        }
        if (page < totalPages - 1) {
            HtmlContainer iconPrev = new HtmlContainer("i");
            iconPrev.addClassNames("fas", "fa-chevron-right");
            buildPager(paginationContainer,page + 1, "next", Optional.of(iconPrev), component, listaAtributeAtrComplex,smartFormId,attrComplex);

            HtmlContainer iconEnd = new HtmlContainer("i");
            iconEnd.addClassNames("fas", "fa-chevron-circle-right");
            buildPager(paginationContainer,totalPages-1, "next", Optional.of(iconEnd), component, listaAtributeAtrComplex,smartFormId,attrComplex);


             }
    }

    private void buildPager(UnorderedList paginationContainer,Integer page, String classType, Optional<HtmlContainer> icon,ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId, AttributeLink attrComplex) {
        Integer indexStart= page*NR_ROWS_TABEL;
        ClickNotifierAnchor pageLink = new ClickNotifierAnchor();
        pageLink.getStyle().set("cursor", "pointer");
        pageLink.addClassName("page-link");
//        pageLink.setHref("javas");
        pageLink.addClickListener(e
                ->
                {
                    showNextTableRows(component,listaAtributeAtrComplex, smartFormId, attrComplex,NR_ROWS_TABEL,indexStart);
                    Integer totalNrPag=(attrComplex.getAttrsOfComplex().size()/NR_ROWS_TABEL)+1;
                    if(attrComplex.getAttrsOfComplex().size()%NR_ROWS_TABEL==0){
                        totalNrPag=attrComplex.getAttrsOfComplex().size()/NR_ROWS_TABEL;
                    }
                    buildPagination(page,totalNrPag,paginationContainer, component, listaAtributeAtrComplex,smartFormId,attrComplex,0);

                });


        if(icon.isPresent()){
            pageLink.add(icon.get());
        } else  {
            pageLink.setText((page + 1)+ "");
        }
        ListItem pageListItem = new ListItem(pageLink);
        if(classType!=null && classType.trim().length() > 0) {
            pageListItem.addClassNames("page-item", classType);
        } else {
            pageListItem.addClassName("page-item");
        }
        paginationContainer.add(pageListItem);
    }
/*
    private void buildPager(ClickNotifierAnchor pageLink, UnorderedList paginationContainer ) {
        pageLink.getStyle().set("cursor", "pointer");
        pageLink.addClassName("page-link");

        ListItem pageListItem = new ListItem(pageLink);

            pageListItem.addClassName("page-item");

        paginationContainer.add(pageListItem);
    }*/
    private void showNextTableRows(ComplexTableComponent component, List<AttributeLink> listaAtributeAtrComplex, String smartFormId, AttributeLink attrComplex, Integer nr_rows_tabel, Integer indexStart) {
        component.clearContainerBody();

        List<RowAttrComplexList> rowList= getFirstNRows( attrComplex.getAttrsOfComplex(),nr_rows_tabel,indexStart);

        //parcurgem fiecare rand al atributului complex, daca exista

        for (RowAttrComplexList row : rowList) {
            //adaugam rand EDITABIL

            addNewRowAttrEditable(component, listaAtributeAtrComplex, smartFormId, row, attrComplex, Optional.ofNullable("NEW_ROW"));
            //resetare index general
            if (index.get() <= row.getRowNumber()) {
                index.set(row.getRowNumber() + 1);
            }

        }
    }

    private void addNewRowAttrReadonly(ComplexTableComponent component, List<AttributeLink> attributeList, String smartFormId, RowAttrComplexList row, AttributeLink attrComplex) {
        HtmlContainer newRow = new HtmlContainer("tr"); // => <tr> </tr>

        if(attrComplex.getTotal()!=null){
            component.addRowColumn(newRow, new  Label(""));
        }
        //stop afisare nr crt
        //component.addRowColumn(newRow, labelNrCrt); // <td> 2 </td>
        component.getElement().setAttribute("td-class", "text-border-center");

        //parcurgem lista de atribute si adaugam o celula noua pentru fiecare
        for (DocAttrLink at : row.getListaAtribute()) {
            AttributeLinkDataType atrDataType = getDocAttributeLinkDataType(at);

            switch (atrDataType) {

                case BOOLEAN:
                    Checkbox checkbox = new Checkbox((at.getValue() != null && at.getValue().equals("1")) ? true : false);
                    checkbox.getElement().setAttribute("td-class", "text-border-center");
                    if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                        component.addRowColumn(newRow, checkbox);

                    }
                    break;
                case OPT_SELECT_DROPBOX:
                    boolean multiselect = false;
                    for (AttributeLink al : attributeList) {
                        if (al.getAttributeId() == at.getAttributeId().intValue()) {
                            multiselect = al.getMultipleSelection() != null && al.getMultipleSelection();
                        }
                    }

                    if (multiselect) {
                        //LovList lovList = BeanUtil.getBean(PS4Service.class).getLovList(Math.toIntExact(at.getLovId()));
                        List<Lov> backendLov = new ArrayList<>();//lovList.getLov();

                        MultiselectComboBox<Lov> lovComboBox = new MultiselectComboBox<>();
                        lovComboBox.setItems(backendLov);
                        lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());

                        StringBuilder sb = new StringBuilder();
                        String sep = "";

                        if (at.getValueForLov() != null) {
                            for (Lov l : at.getValueForLov()) {
                                sb.append(sep).append(l.getValoare());
                                sep = ", ";
                            }
                        }

                        Label labelC = new Label("");
                        labelC.setText(sb.toString());
                        labelC = (Label) setWidthAttrDoc(labelC, at);
                        labelC.getElement().setAttribute("td-class", "text-border");
                        if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                            component.addRowColumn(newRow, labelC);

                        }

                    } else {
                        //LovList lovList = BeanUtil.getBean(PS4Service.class).getLovList(Math.toIntExact(at.getLovId()));
                        List<Lov> backendLov = new ArrayList<>();//lovList.getLov();


                        ComboBox<Lov> lovComboBox = new ComboBox<>();
                        lovComboBox.setItems(backendLov);
                        lovComboBox.setAllowCustomValue(false);
                        lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
                        Optional<Lov> selected = Optional.empty();
                        if (Optional.ofNullable(at.getValueForLov()).isPresent() && !at.getValueForLov().isEmpty()) {
                            selected = Optional.of(new Lov(at.getValueForLov().get(0).getId(), at.getValueForLov().get(0).getValoare()));

                        }

                        Label labelC = new Label("");
                        if (selected.isPresent()) {
                            labelC.setText(selected.get().getValoare() + " ");
                        }
                        labelC = (Label) setWidthAttrDoc(labelC, at);
                        labelC.getElement().setAttribute("td-class", "text-border");
                        if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                            component.addRowColumn(newRow, labelC);

                        }
                    }

                    break;
                case FISIER:
                    Div containerCell= new Div();
                    containerCell.getElement().setAttribute("td-class", "text-border");

                    //definire textfield care va tine id-ul de fisier incarcat
                    Label labelC = new Label("");

                    labelC = (Label) setWidthAttrDoc(labelC, at);
                    labelC.getElement().setAttribute("td-class", "text-border");

                    //daca exista valoare => afisez btn de download&delete
                    if(at.getValue()!=null && !at.getValue().trim().isEmpty() && !at.getValue().toLowerCase().equals("null")){
                        DocObligatoriuExtra fisierUploaded= new DocObligatoriuExtra(new DocObligatoriu());
                        //preluare  download link & name by id fisier
                        FileLink fileLink= BeanUtil.getBean(DmswsFileService.class).getFileLink(SecurityUtils.getToken(),at.getValue());
                        if(fileLink.getDownloadLink()!=null){
                            fisierUploaded.setDownloadLinkForUploadedFile(fileLink.getDownloadLink());
                            fisierUploaded.setUploadedFileName(fileLink.getName());
                        }

                        fisierUploaded.setUploadedFileId(Long.valueOf(at.getValue()));

                        //setare valoare id fisier pe componenta
                        labelC.setText(fisierUploaded.getUploadedFileName()+ "("+fisierUploaded.getUploadedFileId().toString()+")");

                        //definire butoane de download & delete
                        Anchor download = new Anchor();

                        download.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm","btn-xsm-s");
                        HtmlContainer downloadIcon = new HtmlContainer("i");
                        downloadIcon.addClassNames("custom-icon", "icon-down","tooltip_chat");
                        download.add(downloadIcon, new Text(""));
                        Span spandownloadIcon = new Span(I18NProviderStatic.getTranslation("document.type.service.request.view.neededfiles.service.option.download.label"));
                        spandownloadIcon.addClassName("tooltiptext");
                        downloadIcon.add(spandownloadIcon);

                        if(fisierUploaded.getUploadedFileName()!=null && fisierUploaded.getDownloadLinkForUploadedFile()!=null){
                            download.setHref(StreamResourceUtil.getStreamResource(
                                    fisierUploaded.getUploadedFileName(),
                                    fisierUploaded.getDownloadLinkForUploadedFile()));
                        }

                        download.setTarget("_blank");

                        labelC = (Label) setWidthAttrDoc(labelC, at);
                        //adaugare buton de download
                        containerCell.add(labelC,download);
                        if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                            component.addRowColumn(newRow, containerCell);

                        }

                    }else{

                        if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                            component.addRowColumn(newRow, labelC);

                        }
                    }
                    break;
                default:
                    Label label = new Label(at.getValue() + " ");
                    label = (Label) setWidthAttrDoc(label, at);
                    label.getElement().setAttribute("td-class", "text-border");
                    if (!(at.getHidden() != null && at.getHidden().equals(true))) {
                        component.addRowColumn(newRow, label);

                    }
                    break;

            }

        }

        component.addNewRow(newRow);
        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");

    }


}
