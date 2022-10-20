package ro.bithat.dms.microservices.dmsws.payment;

/**
 * Created by Bithat on 3/17/2022.
 */
public class PaymentAmountInfo{
    private String paymentState;
    private int approvedAmount;
    private int depositedAmount;
    private int refundedAmount;

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public int getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(int approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public int getDepositedAmount() {
        return depositedAmount;
    }

    public void setDepositedAmount(int depositedAmount) {
        this.depositedAmount = depositedAmount;
    }

    public int getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(int refundedAmount) {
        this.refundedAmount = refundedAmount;
    }
}
