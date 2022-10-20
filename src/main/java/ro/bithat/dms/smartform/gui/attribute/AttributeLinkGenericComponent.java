package ro.bithat.dms.smartform.gui.attribute;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

import java.util.Optional;

public abstract class AttributeLinkGenericComponent implements AttributeLinkComponentPostProcessor {


    @Override
    public boolean isLovAttribute(AttributeLink attributeLink) {
        Optional<Integer> loveId = Optional.ofNullable(attributeLink.getLovId());
        return loveId.isPresent() && loveId.get().compareTo(0) != 0;
    }

    @Override
    public boolean hasLabel(AttributeLink attributeLink) {
        if(attributeLink.getDataType().equals("TITLE") || attributeLink.getDataType().equals("LABEL")){
            Optional<String> labelValue =  Optional.ofNullable(attributeLink.getValoareImplicita());
            return  !labelValue.isPresent() || labelValue.get().isEmpty();
        }
        else{
            return true;
        }

    }

}
