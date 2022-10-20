package ro.bithat.dms.microservices.dmsws.metadata;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class AttributeLinkList extends BaseModel {
    private List<AttributeLink> attributeLink;

    public List<AttributeLink> getAttributeLink() {
        return attributeLink;
    }

    public void setAttributeLink(List<AttributeLink> attributeLink) {
        this.attributeLink = attributeLink;
    }
}
