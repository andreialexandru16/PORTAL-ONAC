package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import java.util.List;

/**
 * Created by Bithat on 6/5/2020.
 */
public class StatusList extends BaseModel {
    List<StatusAdaugaEtapa> statusAdaugaEtapasList;

    public List<StatusAdaugaEtapa> getStatusAdaugaEtapasList() {
        return statusAdaugaEtapasList;
    }

    public void setStatusAdaugaEtapasList(List<StatusAdaugaEtapa> statusAdaugaEtapasList) {
        this.statusAdaugaEtapasList = statusAdaugaEtapasList;
    }
}
