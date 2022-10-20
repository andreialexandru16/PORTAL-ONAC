package ro.bithat.dms.microservices.dmsws.payment;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

/**
 * Created by Bithat on 3/17/2022.
 */
public class BTPaymentResponse extends BaseModel {

    private String orderId;
    private String formUrl;
    private String errorCode;
    private String errorMessage;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
