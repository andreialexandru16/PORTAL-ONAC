package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import java.util.List;

public class ReStraziResp extends BaseModel {
    private List<ReStrada> straziList;

    public List<ReStrada> getStraziList() {
        return straziList;
    }

    public void setStraziList(List<ReStrada> straziList) {
        this.straziList = straziList;
    }
}
