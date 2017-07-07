package ngocamha.com.project01.model;

/**
 * Created by PL on 6/25/2017.
 */

public class ItemModelStatisTransaction {
    private String dateTime;
    private String reason;
    private int price;
    private int balance;
    private String typeAccount;
    private String typeTransaction;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public ItemModelStatisTransaction(String dateTime, String reason, int price, int balance, String typeAccount, String typeTransaction) {

        this.dateTime = dateTime;
        this.reason = reason;
        this.price = price;
        this.balance = balance;
        this.typeAccount = typeAccount;
        this.typeTransaction = typeTransaction;
    }
}
