package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PersoanaFizicaJuridicaList extends StandardResponse {
    private List<PersoanaFizicaJuridica> persoanaFizicaJuridicaList;

    public List<PersoanaFizicaJuridica> getPersoanaFizicaJuridicaList() {
        return persoanaFizicaJuridicaList;
    }

    public void setPersoanaFizicaJuridicaList(List<PersoanaFizicaJuridica> persoanaFizicaJuridicaList) {
        this.persoanaFizicaJuridicaList = persoanaFizicaJuridicaList;
    }
}
