package ro.bithat.dms.microservices.dmsws.payment;

/**
 * Created by Bithat on 3/17/2022.
 */

public class BankInfo{
    private String bankName;
    private String bankCountryCode;
    private String bankCountryName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCountryCode() {
        return bankCountryCode;
    }

    public void setBankCountryCode(String bankCountryCode) {
        this.bankCountryCode = bankCountryCode;
    }

    public String getBankCountryName() {
        return bankCountryName;
    }

    public void setBankCountryName(String bankCountryName) {
        this.bankCountryName = bankCountryName;
    }
}