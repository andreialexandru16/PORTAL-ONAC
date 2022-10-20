package ro.bithat.dms.microservices.dmsws.payment;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

/**
 * Created by Bithat on 3/17/2022.
 */
public class BTOrderStatusRequest extends BaseModel {

    private String apiUrl;
    private String userName;
    private String password;
    private String orderNumber;
    private String orderId;
    private String error;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
