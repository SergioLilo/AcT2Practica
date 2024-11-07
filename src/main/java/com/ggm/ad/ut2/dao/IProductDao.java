package com.ggm.ad.ut2.dao;

import com.ggm.ad.ut2.exceptions.CannotDeleteException;
import com.ggm.ad.ut2.model.Product;

import java.sql.SQLException;

public interface IProductDao {
    public int insert(Product toCreate) throws SQLException;
    public boolean update(Product toModify) throws SQLException;
    public boolean delete(int idToDelete) throws SQLException, CannotDeleteException;
    public Product getById(int productId) throws SQLException;
}
