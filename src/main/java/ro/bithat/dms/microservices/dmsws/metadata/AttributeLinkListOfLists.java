package ro.bithat.dms.microservices.dmsws.metadata;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class AttributeLinkListOfLists extends BaseModel {
    private List<AttributeLinkList> attributeLinklist;


    public List<AttributeLinkList> getAttributeLinklist() {
        return attributeLinklist;
    }

    public void setAttributeLinklist(List<AttributeLinkList> attributeLinklist) {
        this.attributeLinklist = attributeLinklist;
    }
}
