package ro.bithat.dms.microservices.dmsws.payment;

/**
 * Created by Bithat on 3/17/2022.
 */
public class MerchantOrderParam {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
