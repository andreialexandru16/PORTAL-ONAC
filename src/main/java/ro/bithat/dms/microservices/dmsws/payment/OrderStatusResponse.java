package ro.bithat.dms.microservices.dmsws.payment;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import java.util.ArrayList;

public class OrderStatusResponse extends BaseModel{
    private String errorCode;
    private String errorMessage;
    private String orderNumber;
    private int orderStatus;
    private int actionCode;
    private String actionCodeDescription;
    private int amount;
    private String currency;
    private long date;
    private String orderDescription;
    private String ip;
    private ArrayList<MerchantOrderParam> merchantOrderParams;
    private ArrayList<Attribute> attributes;
    private CardAuthInfo cardAuthInfo;
    private long authDateTime;
    private String terminalId;
    private String authRefNum;
    private PaymentAmountInfo paymentAmountInfo;
    private BankInfo bankInfo;
    private boolean chargeback;

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionCodeDescription() {
        return actionCodeDescription;
    }

    public void setActionCodeDescription(String actionCodeDescription) {
        this.actionCodeDescription = actionCodeDescription;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ArrayList<MerchantOrderParam> getMerchantOrderParams() {
        return merchantOrderParams;
    }

    public void setMerchantOrderParams(ArrayList<MerchantOrderParam> merchantOrderParams) {
        this.merchantOrderParams = merchantOrderParams;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public CardAuthInfo getCardAuthInfo() {
        return cardAuthInfo;
    }

    public void setCardAuthInfo(CardAuthInfo cardAuthInfo) {
        this.cardAuthInfo = cardAuthInfo;
    }

    public long getAuthDateTime() {
        return authDateTime;
    }

    public void setAuthDateTime(long authDateTime) {
        this.authDateTime = authDateTime;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getAuthRefNum() {
        return authRefNum;
    }

    public void setAuthRefNum(String authRefNum) {
        this.authRefNum = authRefNum;
    }

    public PaymentAmountInfo getPaymentAmountInfo() {
        return paymentAmountInfo;
    }

    public void setPaymentAmountInfo(PaymentAmountInfo paymentAmountInfo) {
        this.paymentAmountInfo = paymentAmountInfo;
    }

    public BankInfo getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(BankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }

    public boolean isChargeback() {
        return chargeback;
    }

    public void setChargeback(boolean chargeback) {
        this.chargeback = chargeback;
    }
}

