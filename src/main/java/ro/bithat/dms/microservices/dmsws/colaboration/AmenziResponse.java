package ro.bithat.dms.microservices.dmsws.colaboration;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmenziResponse extends BaseModel {


    @JsonProperty("Raspuns")
    ArrayList<Object> raspuns;

    @JsonProperty("Amenda")
    ArrayList<Object> amenda;

    public ArrayList<Object> getRaspuns() {
        return raspuns;
    }

    public void setRaspuns(ArrayList<Object> raspuns) {
        this.raspuns = raspuns;
    }
    public ArrayList<Object> getAmenda() {
        return amenda;
    }

    public void setAmenda(ArrayList<Object> amenda) {
        this.amenda = amenda;
    }

}
