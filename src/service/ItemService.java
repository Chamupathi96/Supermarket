package service;

import java.sql.SQLException;
import java.util.List;

import dao.ItemDAO;
import model.ItemModel;

public class ItemService {

 private ItemDAO itemDAO;

      public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public void addItem(ItemModel item) throws SQLException {
        itemDAO.addItem(item);
    }

    public void updateItem(ItemModel item) throws SQLException {
        itemDAO.updateItem(item);
    }

    public void deleteItem(int itemId) throws SQLException {
        itemDAO.deleteItem(itemId);
    }

    public void updateStockQuantity(int itemId, int newQuantity) throws SQLException {
        itemDAO.updateStockQuantity(itemId, newQuantity);
    }

    public List<ItemModel> getAllItems() throws SQLException {
        return itemDAO.getAllItems();
    }

    
    
    public List<String> getCategories() throws SQLException {
        return itemDAO.getCategories();
    }
    
    public List<String> getItemNames() throws SQLException {
        return itemDAO.getItemNames();
    } 

   
}
