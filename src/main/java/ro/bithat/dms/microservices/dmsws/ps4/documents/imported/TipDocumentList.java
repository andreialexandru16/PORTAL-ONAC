package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TipDocumentList extends BaseModel {
    private List<TipDocument> tipDocumentList;

    public List<TipDocument> getTipDocumentList() {
        return tipDocumentList;
    }

    public void setTipDocumentList(List<TipDocument> tipDocumentList) {
        this.tipDocumentList = tipDocumentList;
    }
}
