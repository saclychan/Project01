package ngocamha.com.project01.model;

/**
 * Created by PL on 6/21/2017.
 */

public class ItemModel {
    String txtAccount;
    int price;
    int id;

    public ItemModel(String txtAccount, int price) {
        this.txtAccount = txtAccount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemModel(String txtAccount, int price, int id) {
        this.txtAccount = txtAccount;
        this.price = price;
        this.id = id;
    }

    public String getTxtAccount() {
        return txtAccount;
    }

    public int getPrice() {
        return price;
    }

    public void setTxtAccount(String txtAccount) {
        this.txtAccount = txtAccount;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
