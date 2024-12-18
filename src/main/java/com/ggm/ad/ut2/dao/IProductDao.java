package com.ggm.ad.ut2.dao;

import com.ggm.ad.ut2.exceptions.CannotDeleteException;
import com.ggm.ad.ut2.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDao {
    public int insert(Product toCreate) throws SQLException;
    public boolean update(Product toModify) throws SQLException;
    public boolean delete(int idToDelete) throws SQLException, CannotDeleteException;
    public Product getById(int productId) throws SQLException;
    public List<Product> getAll() throws SQLException;
    public List<Product> getAllByNameAlike( String name) throws SQLException;
    public boolean substractStock(int productId, int newStock) throws SQLException;
}
