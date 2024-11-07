package com.ggm.ad.ut2.dao.impl;

import com.ggm.ad.ut2.dao.IProductDao;
import com.ggm.ad.ut2.exceptions.CannotDeleteException;
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
    public int insert(Product toCreate) throws SQLException {


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

    @Override
    public boolean update(Product toModify) throws SQLException {


        String sql = "UPDATE PRODUCT SET name = ?, description = ?, stock = ?, price = ?, available = ?, UPDATE_DATE = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, toModify.getName());
        pstmt.setString(2, toModify.getDescription());
        pstmt.setInt(3, toModify.getStock());
        pstmt.setDouble(4, toModify.getPrice());
        pstmt.setBoolean(5, toModify.isAvailable());
        pstmt.setTimestamp(6, Timestamp.valueOf(toModify.getUpdateDate()));
        pstmt.setInt(7, toModify.getId());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows>0){
            return true;
        }else{
        return false;}
    }

    @Override
    public boolean delete(int idToDelete) throws SQLException, CannotDeleteException {

        String sql = "DELETE FROM PRODUCT WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, idToDelete);

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected>0){

            return true;
        }else {

            return false;
        }

    }

    @Override
    public Product getById(int productId) throws SQLException {
        String sql = "SELECT ID, NAME, DESCRIPTION, STOCK, PRICE, AVAILABLE, CREATE_DATE, UPDATE_DATE FROM PRODUCT WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, productId);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Product product=new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("stock"),
                    rs.getDouble("price"),
                    rs.getBoolean("available"),
                    rs.getTimestamp("CREATE_DATE").toLocalDateTime(),
                    rs.getTimestamp("UPDATE_DATE").toLocalDateTime());
            return product;

        } else {
            return null;
        }
    }
}
