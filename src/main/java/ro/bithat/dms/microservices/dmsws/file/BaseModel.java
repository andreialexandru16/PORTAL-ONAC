package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Model de baza care tine daca a fost ok sau daca nu, ce eroare a fost.
 * Nu interfereaza cu celelalte modele doar adauga in plus.
 */
@XmlRootElement
public class BaseModel {
    private String result;
    private String info;
    private String extendedInfo;
    private Integer extendedInfo3;
    private String extendedInfo2;
    private List<String> infoList;



    public BaseModel() {
        this.result = "OK";
        this.info = "Request successful";
        this.extendedInfo = "";
    }

    public String getExtendedInfo2() {
        return extendedInfo2;
    }

    public void setExtendedInfo2(String extendedInfo2) {
        this.extendedInfo2 = extendedInfo2;
    }

    public boolean isError() {
    	return !this.result.equalsIgnoreCase("OK");
    }
    
    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        this.infoList = infoList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getExtendedInfo() {
        return extendedInfo;
    }

    public void setExtendedInfo(String extendedInfo) {
        this.extendedInfo = extendedInfo;
    }

    public Integer getExtendedInfo3() {
        return extendedInfo3;
    }

    public void setExtendedInfo3(Integer extendedInfo3) {
        this.extendedInfo3 = extendedInfo3;
    }

    @Override
	public String toString() {
		return "BaseModel [result=" + result + ", info=" + info + ", extendedInfo=" + extendedInfo + ", infoList="
				+ infoList + "]";
	}
    
    
}
