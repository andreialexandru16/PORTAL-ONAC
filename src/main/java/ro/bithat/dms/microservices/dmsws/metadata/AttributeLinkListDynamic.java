package ro.bithat.dms.microservices.dmsws.metadata;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class AttributeLinkListDynamic extends BaseModel {
    private Integer offset;
    private Integer limit;
    private String query;

    private List<AttributeLink> attributeLink;

    public List<AttributeLink> getAttributeLink() {
        return attributeLink;
    }

    public void setAttributeLink(List<AttributeLink> attributeLink) {
        this.attributeLink = attributeLink;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
