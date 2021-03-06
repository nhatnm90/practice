package eShop.Model;


import java.text.SimpleDateFormat;
import java.util.*;

import static utils.StringFormat.formatCurrency;

public class Product {

    //<editor-fold desc="Constant">
    private static final int NUMBER_OF_INIT_ONHAND = 100;
    //</editor-fold>

    //<editor-fold desc="Propeties">
    private UUID productId;
    private String productName;
    private UUID categoryId;
    private long onHand;
    private double unitPrice;
    private Calendar createdDate;
    private boolean isActive;
    private int order;
    private long soldItem;
    private double importUnitPrice;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public Product(UUID categoryId, String productName, double unitPrice) {
        this.createdDate = Calendar.getInstance();
        this.productId = UUID.randomUUID();
        this.categoryId = categoryId;
        this.onHand = 0;
        this.unitPrice = unitPrice;
        this.isActive = true;
        this.order = 0;
        this.productName = productName;
    }
    //</editor-fold>

    //<editor-fold desc="Getter Setter">
    public void setOrder(int order) {
        this.order = order;
    }

    public void setSoldItem(long soldItem) {
        this.soldItem = soldItem;
    }

    public int getOrder() {
        return order;
    }

    public long getSoldItem() {
        return soldItem;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public long getOnHand() {
        return onHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public void setOnHand(long onHand) {
        this.onHand = onHand;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setInitOnHand(){
        // this.onHand = 100;
        this.onHand = NUMBER_OF_INIT_ONHAND;
        // Nên đưa những thông tin là hằng số lên mục constant để ở 1 chỗ riêng thay vì để trong code như vầy e ha
    }
    public void updateProductStatus(){
        this.isActive = false;
    }
    //</editor-fold>

    //<editor-fold desc="Private functions">

    //</editor-fold>

    //<editor-fold desc="Public functions">
    public int countCharacter(){
        return this.productName.length();
    }

    public void updateProductNameToLowerCase(){
        // this.productName = getProductName().toLowerCase();

        // mình có thể dùng ngay thuộc tính this.productName trong class này luôn thay vì phải xài hàm getter như getProductName() hen
        // vì mình đang ở trong nội tại của class product mà, nên prop productName dù private vẫn dùng đc bình thường
        // nên dùng hàm setter khi gọi đến những prop private khi ở ngoài class đó
        this.productName = this.productName.toLowerCase();
    }
    public void showInfo (){
        System.out.println("Giá: " + formatCurrency(this.unitPrice, "vn", "VN"));
        System.out.println("Name: " + this.productName);
        System.out.println("Order: " + this.order);
    }


    //</editor-fold>
}


