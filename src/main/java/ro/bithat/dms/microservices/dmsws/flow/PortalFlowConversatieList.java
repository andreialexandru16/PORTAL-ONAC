package ro.bithat.dms.microservices.dmsws.flow;

import java.util.List;

public class PortalFlowConversatieList extends StandardResponse {
    private List<PortalFlowConversatie> portalFlowConversatieList;

    public List<PortalFlowConversatie> getPortalFlowConversatieList() {
        return portalFlowConversatieList;
    }

    public void setPortalFlowConversatieList(List<PortalFlowConversatie> portalFlowConversatieList) {
        this.portalFlowConversatieList = portalFlowConversatieList;
    }
}
