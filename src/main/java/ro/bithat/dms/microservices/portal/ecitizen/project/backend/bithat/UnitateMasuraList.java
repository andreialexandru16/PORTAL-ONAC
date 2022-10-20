package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import java.util.List;

/**
 * Created by Bithat on 6/10/2020.
 */
public class UnitateMasuraList extends BaseModel {

    List<UnitateMasura> listaUnitateMasura;


    public List<UnitateMasura> getListaUnitateMasura() {
        return listaUnitateMasura;
    }

    public void setListaUnitateMasura(List<UnitateMasura> listaUnitateMasura) {
        this.listaUnitateMasura = listaUnitateMasura;
    }
}
