package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;


import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class WbsResponse extends StandardResponse {
    private List<Wbs> dataList;

    public List<Wbs> getDataList() {
        return dataList;
    }

    public void setDataList(List<Wbs> dataList) {
        this.dataList = dataList;
    }
}
