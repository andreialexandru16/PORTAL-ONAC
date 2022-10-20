package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class WbsResponse2 extends StandardResponse {
    private List<Wbs2> dataList;

    public List<Wbs2> getDataList() {
        return dataList;
    }

    public void setDataList(List<Wbs2> dataList) {
        this.dataList = dataList;
    }
}
