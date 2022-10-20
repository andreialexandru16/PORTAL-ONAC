package ro.bithat.dms.microservices.dmsws.flow;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class ListaPublica extends StandardResponse {



    private List<Map<String, Object>> listaPublica;


    public List<Map<String, Object>> getListaPublica() {
        return listaPublica;
    }

    public void setListaPublica(List<Map<String, Object>> listaPublica) {
        this.listaPublica = listaPublica;
    }
}