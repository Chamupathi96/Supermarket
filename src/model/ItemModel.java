package model;

public class ItemModel {
    
    private int itemId;
    private String itemName;
    private String category;
    private double price;
    private int stockQuantity;
    
    public ItemModel(int itemId, String itemName, String category, double price, int stockQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public ItemModel() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "ItemModel [itemId=" + itemId + ", itemName=" + itemName + ", category=" + category + ", price=" + price
                + ", stockQuantity=" + stockQuantity + "]";
    }

   

    

}
