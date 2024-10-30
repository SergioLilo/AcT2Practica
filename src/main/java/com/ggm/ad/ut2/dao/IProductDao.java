package com.ggm.ad.ut2.dao;

import com.ggm.ad.ut2.model.Product;

import java.sql.SQLException;

public interface IProductDao {
    public int Insert(Product toCreate) throws SQLException;
}
