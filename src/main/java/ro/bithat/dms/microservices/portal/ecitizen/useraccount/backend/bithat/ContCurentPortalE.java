package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContCurentPortalE extends BaseModel {
   PersoanaFizicaJuridica userCurent;
   PersoanaFizicaJuridica tertParinteUserCurent;
    String listaRoluri;

    public String getListaRoluri() {
        return listaRoluri;
    }

    public void setListaRoluri(String listaRoluri) {
        this.listaRoluri = listaRoluri;
    }

    public PersoanaFizicaJuridica getUserCurent() {
        return userCurent;
    }

    public void setUserCurent(PersoanaFizicaJuridica userCurent) {
        this.userCurent = userCurent;
    }

    public PersoanaFizicaJuridica getTertParinteUserCurent() {
        return tertParinteUserCurent;
    }

    public void setTertParinteUserCurent(PersoanaFizicaJuridica tertParinteUserCurent) {
        this.tertParinteUserCurent = tertParinteUserCurent;
    }
}
