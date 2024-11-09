package com.ggm.ad.ut2.dao.impl;

import com.ggm.ad.ut2.dao.IProductDao;
import com.ggm.ad.ut2.exceptions.CannotDeleteException;
import com.ggm.ad.ut2.model.Product;
import com.ggm.ad.ut2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements IProductDao {

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
        String sql = "SELECT ID, NAME, DESCRIPTION, STOCK, PRICE, AVAILABLE, CREATE_DATE, UPDATE_DATE FROM PRODUCT WHERE ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, productId);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {

            Product product=new Product();

            product.setId(rs.getInt("ID"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            product.setStock(rs.getInt("STOCK"));
            product.setPrice(rs.getDouble("PRICE"));
            product.setAvailable(rs.getBoolean("AVAILABLE"));
            product.setCreateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
            product.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());

            return product;

        } else {
            return null;
        }
    }

    @Override
    public List<Product> getAll() throws SQLException {
        String sql = "SELECT ID, NAME, DESCRIPTION, STOCK, PRICE, AVAILABLE, CREATE_DATE, UPDATE_DATE FROM PRODUCT";
        List<Product> products = new ArrayList<>();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("ID"));
                product.setName(rs.getString("NAME"));
                product.setDescription(rs.getString("DESCRIPTION"));
                product.setStock(rs.getInt("STOCK"));
                product.setPrice(rs.getDouble("PRICE"));
                product.setAvailable(rs.getBoolean("AVAILABLE"));
                product.setCreateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
                product.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());

                products.add(product);
            }

        return products;
    }

    @Override
    public List<Product> getAllByNameAlike(String name) throws SQLException {
        String sql = "SELECT ID, NAME, DESCRIPTION, STOCK, PRICE, AVAILABLE, CREATE_DATE, UPDATE_DATE FROM PRODUCT WHERE NAME LIKE ?";
        List<Product> products = new ArrayList<>();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + name + "%");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("ID"));
            product.setName(rs.getString("NAME"));
            product.setDescription(rs.getString("DESCRIPTION"));
            product.setStock(rs.getInt("STOCK"));
            product.setPrice(rs.getDouble("PRICE"));
            product.setAvailable(rs.getBoolean("AVAILABLE"));
            product.setCreateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
            product.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());

            products.add(product);
        }

        return products;
    }

    @Override
    public boolean substractStock(int productId, int newStock) throws SQLException {

        String sql = "UPDATE PRODUCT SET STOCK = ? WHERE ID = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productId);

            int rowsAffected = pstmt.executeUpdate();

        return rowsAffected > 0;
            }

    }

