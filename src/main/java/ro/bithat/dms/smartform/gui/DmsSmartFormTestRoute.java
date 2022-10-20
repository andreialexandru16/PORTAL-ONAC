package ro.bithat.dms.smartform.gui;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.smartform.gui.attribute.AttributeLinkService;

import javax.annotation.PostConstruct;
import java.util.Optional;

//@Viewport("width=device-width, initial-scale=1, shrink-to-fit=no")
//@Meta(name = "charset", content = "utf-8")
//@StyleSheet("PORTAL/assets/css/jquery-ui.min.css")
//@StyleSheet("PORTAL/assets/css/bootstrap.min.css")
//@StyleSheet("PORTAL/assets/css/main.css")
@Route(value = "test/smartform")
public class DmsSmartFormTestRoute extends Div {

    private Logger logger = LoggerFactory.getLogger(DmsSmartFormTestRoute.class);


    @Autowired
    private SmartFormComponentService smartFormComponentService;

    @Autowired
    private AttributeLinkService attributeLinkService;

    @Autowired
    private PS4Service ps4Service;

    private VerticalLayout smartAttributeForm = new VerticalLayout();

    private Optional<Integer> documentId = Optional.empty();

    @PostConstruct
    public void testSmartForm() {
        //urbanism
//        smartAttributeForm.setSpacing(false);
//        if(true) {
//            add(new IntegerField("dadas"), new TextField("asa"), new NumberField("dsaaew"));
//            return;
//        }
        smartAttributeForm.setPadding(false);
        add(smartAttributeForm);
        documentId = QueryParameterUtil.getQueryParameter("document", Integer.class);
        if(documentId.isPresent()) {
            AttributeLinkList attributeLinkList = ps4Service.getMetadataByDocumentId(documentId.get());

//            DocAttrLinkList docAttrLinkList = new DocAttrLinkList();
//            ps4Service.replaceExistingFileAndSetMetadata()
            if(attributeLinkList != null && attributeLinkList.getAttributeLink() != null && !attributeLinkList.getAttributeLink().isEmpty()) {

//                FormLayoutSmartForm formLayoutSmartForm = new FormLayoutSmartForm();
//                formLayoutSmartForm.buildSmartForm(attributeLinkList);
//                add(formLayoutSmartForm);


                DocumentaSmartForm formLayoutSmartForm = new DocumentaSmartForm();
                formLayoutSmartForm.buildSmartForm(attributeLinkList);
                add(formLayoutSmartForm);


                //                add(new DmsSmartForm(attributeLinkList));


                //todo sort by attribute data order
//                Map<Integer, List<AttributeLink>> attributeLinkFormRowMap = attributeLinkList.getAttributeLink().stream().collect(Collectors.groupingBy(attributeLink -> attributeLink.getRand().intValue()));
//
//                Integer maxColspan = attributeLinkFormRowMap.values().stream().sorted((l1, l2) -> l2.size() - l1.size()).findFirst().get().size();
//                Double maxWidth = attributeLinkList.getAttributeLink().stream().sorted((a1, a2) -> a2.getWidthPx().compareTo(a1.getWidthPx())).findFirst().get().getWidthPx();
////                setSmartAttributeFormResponsiveSteps(maxColspan, maxWidth);
//                //TODO set form responisive row cols
//                attributeLinkFormRowMap.values().forEach(attributeLinks -> viewFormRowPostProcessor(attributeLinks));
                return;
            }
            logger.info("nu exista atribute pentru documentul:\t"+documentId.get());
            return;
        }
        logger.info("documentul nu este gasit");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/jquery-3.5.1.min.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/jquery-ui.min.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/bootstrap.min.js");

    }

//    private void viewFormRowPostProcessor(List<AttributeLink> attributeLinks) {
//        Div rowForm = new Div();
////        FormLayout.ResponsiveStep[] responsiveSteps = new FormLayout.ResponsiveStep[attributeLinks.size()];
//////        double stepWidth = maxWidth;
////        int em = 25;
////        for (int i = 0; i < attributeLinks.size(); ++i) {
////            responsiveSteps[i] = new FormLayout.ResponsiveStep(em+"em", i + 1);
//////            maxWidth+=stepWidth;
////            em += 25;
////        }
//        rowForm.setWidthFull();
////        rowForm.setResponsiveSteps(responsiveSteps);
//        HorizontalLayout row = new HorizontalLayout(rowForm);
//        row.setSpacing(false);
//        row.setPadding(false);
//        row.setAlignItems(FlexComponent.Alignment.CENTER);
//        smartAttributeForm.add(row);
//        attributeLinks.stream()
//                .sorted(Comparator.comparing(AttributeLink::getColoana))
//                .forEach(attributeLink -> viewFormRowColumnPostProcessor(attributeLink, rowForm));
//
//    }
//
//    private void viewFormRowColumnPostProcessor(AttributeLink attributeLink, Div rowForm) {
//        setSmartAttributeForm(attributeLink, rowForm);
//
//    }
//
//    private void setSmartAttributeForm(AttributeLink attributeLink, Div colspan) {
////        attributeLink.setV
//        //
//        AttributeLinkPostProcessor attributeLinkPostProcessor = new AttributeLinkPostProcessor();
//        TextField attributeLinkLabel = new TextField();
//        //        attributeLinkLabel.setWidth(attributeLink.getWidthPx() + "px");
////        attributeLink.getDataType()
//        Optional<String> dataTypeStr = Optional.ofNullable(attributeLink.getDataType());
//        if(dataTypeStr.isPresent() && !dataTypeStr.get().isEmpty()) {
//            ConversionService conversionService = BeanUtil.getBean(ConversionService.class);
//            AttributeLinkDataType attributeLinkDataType = conversionService.convert(dataTypeStr.get(), AttributeLinkDataType.class);
//            attributeLinkPostProcessor.attributeLinkDataPostProcessor(attributeLinkDataType, attributeLink, colspan);
//        } else {
//            attributeLinkPostProcessor.attributeLinkDataPostProcessor(AttributeLinkDataType.TEXT, attributeLink, colspan);
//        }
////        colspan.add(new Label(attributeLink.getLabel()), new TextField());
////        smartAttributeForm.setColspan(smartAttributeFormItem, colspan);
//
//    }
//
//    private boolean hasAttributeLinkLabel(AttributeLink attributeLink) {
//
//        return false;
//    }

//    enum AttributeLinkDataType {
//        NUMERIC, TEXT, DATA, BOOLEAN, TEXTAREA, LABEL, LINE, TITLE, HTML,
//        OPT_GROUP, OPT_SELECT, OPT_SELECT_MULTIVALUE, OPT_SELECT_DROPBOX, OPT_SELECT_DROPBOX_MULTIVALUE
//    }
//
//    abstract class AttributeLinkDataPostProcessor {
//
//        final AttributeLinkDataType attributeLinkDataType;
//
//        AttributeLinkDataPostProcessor(AttributeLinkDataType attributeLinkDataType) {
//            this.attributeLinkDataType = attributeLinkDataType;
//        }
//        boolean isSubjectPostProcessor(AttributeLinkDataType attributeLinkDataType) {
//            return this.attributeLinkDataType.equals(attributeLinkDataType);
//        }
//
//        abstract void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout);
//
//        boolean isLovAttribute(AttributeLink attributeLink) {
//            Optional<Integer> loveId = Optional.ofNullable(attributeLink.getLovId());
//            return loveId.isPresent() && loveId.get().compareTo(0) != 0;
//        }
//
//        boolean hasLabel(AttributeLink attributeLink) {
//            Optional<String> labelValue =  Optional.ofNullable(attributeLink.getValoareImplicita());
//            return  !labelValue.isPresent() || labelValue.get().isEmpty();
//        }
//    }
//
//
//
//    class DefaultAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {
//
//        DefaultAttributeLinkDataPostProcessor() {
//            super(AttributeLinkDataType.TEXT);
//        }
//
//        boolean isSubjectPostProcessor(AttributeLinkDataType attributeLinkDataType) {
//            return true;
//        }
//
//        @Override
//        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
//            if(!hasLabel(attributeLink)) {
//                TextField defaultValue = new TextField();
//                defaultValue.setValue(attributeLink.getValoareImplicita());
//                defaultValue.setReadOnly(true);
//                formLayout.add(defaultValue);
//            } else {
//                Label labelValue = new Label(attributeLink.getLabel());
//                Optional<Double> hasSetWidth = Optional.ofNullable(attributeLink.getWidthPx());
//                if(hasSetWidth.isPresent() && hasSetWidth.get().compareTo(0d) > 0) {
//                    labelValue.setWidth(attributeLink.getWidthPx() + "px");
//                }
//                formLayout.add(labelValue);
//            }
//        }
//
//    }
//
//
//
//    class TextAttributeLinkDataPostProcessor extends AttributeLinkDataPostProcessor {
//
//        TextAttributeLinkDataPostProcessor() {
//            super(AttributeLinkDataType.TEXT);
//        }
//
//        @Override
//        void attributeLinkDataPostProcessor(AttributeLink attributeLink, Div formLayout) {
//            if(!hasLabel(attributeLink)) {
//                TextField defaultValue = new TextField();
//                defaultValue.setValue(attributeLink.getValoareImplicita());
//                defaultValue.setReadOnly(true);
//                formLayout.add(defaultValue);
//            } else {
//                formLayout.add(new Label(attributeLink.getLabel()));
//                TextField defaultValue = new TextField();
//                Optional<Double> hasSetWidth = Optional.ofNullable(attributeLink.getWidthPx());
//                if(hasSetWidth.isPresent() && hasSetWidth.get().compareTo(0d) > 0) {
//                    defaultValue.setWidth(attributeLink.getWidthPx() + "px");
//                }
//                formLayout.add(defaultValue);
//            }
//        }
//
//    }
//
//    class AttributeLinkPostProcessor {
//
//
//        DefaultAttributeLinkDataPostProcessor defaultAttributeLinkDataPostProcessor = new DefaultAttributeLinkDataPostProcessor();
//
//        List<AttributeLinkDataPostProcessor> postProcessors = new ArrayList<>(); {
//            postProcessors.add(new TextAttributeLinkDataPostProcessor());
//        }
//
//
//        Optional<AttributeLinkDataPostProcessor> getAttributeLinkPostProcessor(AttributeLinkDataType attributeLinkDataType) {
//            return postProcessors.stream().filter(p -> p.isSubjectPostProcessor(attributeLinkDataType)).findFirst();
//        }
//
//        void attributeLinkDataPostProcessor(AttributeLinkDataType attributeLinkDataType, AttributeLink attributeLink, Div formLayout) {
//            Optional<AttributeLinkDataPostProcessor> attributeLinkDataPostProcessor = getAttributeLinkPostProcessor(attributeLinkDataType);
//            if(attributeLinkDataPostProcessor.isPresent()) {
//                attributeLinkDataPostProcessor.get().attributeLinkDataPostProcessor(attributeLink, formLayout);
//            } else  {
//                defaultAttributeLinkDataPostProcessor.attributeLinkDataPostProcessor(attributeLink, formLayout);
//            }
//        }
//
//    }
//
//
//
//    //    private void setSmartAttributeFormResponsiveSteps(Integer maxColspan, Double maxWidth) {
//////        smartAttributeForm.setResponsiveSteps(
//////                new FormLayout.ResponsiveStep("25em", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
//////                new FormLayout.ResponsiveStep("36em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
//////                new FormLayout.ResponsiveStep("60em", 3, FormLayout.ResponsiveStep.LabelsPosition.ASIDE),
//////                new FormLayout.ResponsiveStep("80em", 4, FormLayout.ResponsiveStep.LabelsPosition.ASIDE));
////                FormLayout.ResponsiveStep[] responsiveSteps = new FormLayout.ResponsiveStep[maxColspan];
////                double stepWidth = maxWidth;
////                int em = 25;
////                for (int i = 0; i < maxColspan; ++i) {
////                    responsiveSteps[i] = new FormLayout.ResponsiveStep(em+"em", i + 1, FormLayout.ResponsiveStep.LabelsPosition.ASIDE);
////                    maxWidth+=stepWidth;
////                    em += 25;
////                }
////                smartAttributeForm.setResponsiveSteps(responsiveSteps);
////
////    }


}
