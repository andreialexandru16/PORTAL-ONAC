package ro.bithat.dms.smartform.gui.attribute.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import elemental.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.gui.SmartFormSupport;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

public class HolographicComponent extends HorizontalLayout implements HasValue<AbstractField.ComponentValueChangeEvent<HolographicComponent, String>, String> {


    private AttributeLink attributeLink;

    private String smartFormId;

    private HtmlContainer canvas = new HtmlContainer("canvas");

    private String image;
    //nu mai este nevoie de butonul de salvare din moment ce salvarea se va face automat
  //  private Button actionApply = new Button("Semnează");
    private Button actionClear = new Button("Curăță");

    private VerticalLayout buttonsLayout = new VerticalLayout( actionClear);

    private String value;

    private static final Logger logger = LoggerFactory.getLogger(HolographicComponent.class);


    private boolean readOnly;

    public HolographicComponent(String smartFormId, AttributeLink attributeLink) {
        this.smartFormId = smartFormId;
        this.attributeLink = attributeLink;
        canvas.setId(UUID.randomUUID().toString());
        buttonsLayout.setPadding(false);
        buttonsLayout.setSpacing(false);
        buttonsLayout.setDefaultHorizontalComponentAlignment(Alignment.END);
        add(canvas, buttonsLayout);
       // actionApply.addClickListener(this::apply);
        actionClear.addClickListener(this::clear);

    }

    @Override
    public void setWidth(String width) {
        super.setWidth(width);
        canvas.setWidth(width);
    }

    private void clear(ClickEvent<Button> buttonClickEvent) {
        setValue(null);
        SmartFormSupport.addSubjectValueComponent(this);
        SmartFormSupport.validate(smartFormId, attributeLink);
        UI.getCurrent().getPage().executeJs("clearHoloCanvas($0);",
                canvas.getId().get());

    }

    private void apply(ClickEvent<Button> buttonClickEvent) {
        UI.getCurrent().getPage().executeJs("applyHoloCanvas($0, $1);",
                canvas.getId().get(), this.getElement());
    }


    @ClientCallable
    public void afterDraw(JsonObject imageData, String image) {
//    	if (hasDraw = false) {
//    		return;
//    	}
        setValue(Base64.getEncoder().encodeToString(imageData.toJson().getBytes()));
        try {
            setImage(image.split(",")[1]);
            this.attributeLink.setImage(image.split(",")[1]);
        }catch(Exception e){
            e.printStackTrace();
        }
        SmartFormSupport.addSubjectValueComponent(this);
        SmartFormSupport.validate(smartFormId, attributeLink);
        //nu mai este nevoie de alerta ca semnatura s-a salvat din moment ce semnatura se salveaza automat
  //      UI.getCurrent().getPage().executeJs("swalInfo($0);","Semnatura a fost salvata!");

        //        try {
////            getPresenter().publishOcrOnTheFlyText(ocrService.getOcrText(ArrayUtils.toPrimitive(imageDataBytes)));
//            logger.debug("ocrText from image crop:\t");
//        } catch (IOException e) {
//            logger.error("imageData Get to ocr error", e);
//        }
//        logger.debug(imageData.toJson());
    }

    public void init(String imageData) {
        UI.getCurrent().getPage().executeJs("initHolographicAttributeLinkComponent($0, $1, $2);",
                canvas.getId().get(), Optional.ofNullable(imageData).isPresent() && !imageData.isEmpty() ?
                        new String(Base64.getDecoder().decode(imageData.getBytes())) : "", this.getElement());
    }

    public String getValue() {
        return value;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<HolographicComponent, String>> listener) {
        @SuppressWarnings("rawtypes")
        ComponentEventListener componentListener = event -> {
            AbstractField.ComponentValueChangeEvent<HolographicComponent, String> valueChangeEvent = (AbstractField.ComponentValueChangeEvent<HolographicComponent, String>) event;
            listener.valueChanged(valueChangeEvent);
        };
        return ComponentUtil.addListener(this,
                AbstractField.ComponentValueChangeEvent.class, componentListener);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        buttonsLayout.setVisible(!readOnly);
        this.readOnly = readOnly;
        UI.getCurrent().getPage().executeJs("setHoloCanvasReadOnly($0, $1);",
                canvas.getId().get(), readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {

    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    public void setValue(String value) {
//        if(Optional.ofNullable(value).isPresent() && !value.isEmpty()) {
//            this.value = Base64.getEncoder().encodeToString(value.getBytes());
//            return;
//        }
        this.value = value;
    }

    public void setImage(String image){
        this.image=image;
    }

    public String getImage(){
        return this.image;
    }
}
