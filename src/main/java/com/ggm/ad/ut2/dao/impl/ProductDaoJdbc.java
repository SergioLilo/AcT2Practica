package com.ggm.ad.ut2.dao.impl;

import com.ggm.ad.ut2.dao.IProductDao;
import com.ggm.ad.ut2.model.Product;
import com.ggm.ad.ut2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;

public class ProductDaoJdbc implements IProductDao {
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);
    private Connection conn;

    public ProductDaoJdbc(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int Insert(Product toCreate) throws SQLException {

        Statement sentencia= conn.createStatement();
        String sql = "INSERT INTO PRODUCT (id, name, description, stock, price, available, CREATE_DATE, UPDATE_DATE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, toCreate.getId());
        pstmt.setString(2, toCreate.getName());
        pstmt.setString(3, toCreate.getDescription());
        pstmt.setInt(4, toCreate.getStock());
        pstmt.setDouble(5, toCreate.getPrice());
        pstmt.setBoolean(6, toCreate.isAvailable());
        pstmt.setTimestamp(7, Timestamp.valueOf(toCreate.getCreateDate()));
        pstmt.setTimestamp(8, Timestamp.valueOf(toCreate.getUpdateDate()));

        int affectedRows = pstmt.executeUpdate();

        return affectedRows;
    }
}
