package ro.bithat.dms.microservices.dmsws.flow;

import java.util.List;

public class PortalFlowPasiActiuniList extends StandardResponse {
    private List<PortalFlowPasiActiuni> portalFlowPasiActiuniList;

    public List<PortalFlowPasiActiuni> getPortalFlowPasiActiuniList() {
        return portalFlowPasiActiuniList;
    }

    public void setPortalFlowPasiActiuniList(List<PortalFlowPasiActiuni> portalFlowPasiActiuniList) {
        this.portalFlowPasiActiuniList = portalFlowPasiActiuniList;
    }
}
