package com.ggm.ad.ut2.dao.impl;

import com.ggm.ad.ut2.dao.IClientDao;
import com.ggm.ad.ut2.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoJdbc  implements IClientDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public ClientDaoJdbc(Connection connection) {
        this.conn = connection;
    }


    @Override
    public int insert(Client toCreate) throws SQLException {
        String sql = "INSERT INTO CLIENT (name, surname, email, purchases, CREATE_DATE, UPDATE_DATE) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, toCreate.getName());
        pstmt.setString(2, toCreate.getSurname());
        pstmt.setString(3, toCreate.getEmail());
        pstmt.setInt(4, toCreate.getPurchases());
        pstmt.setTimestamp(5, Timestamp.valueOf(toCreate.getCreateDate()));
        pstmt.setTimestamp(6, Timestamp.valueOf(toCreate.getUpdateDate()));

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        }
        return -1;
    }

    @Override
    public boolean update(Client toModify) throws SQLException {
        String sql = "UPDATE CLIENT SET name = ?, surname = ?, UPDATE_DATE = ? WHERE email = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, toModify.getName());
        pstmt.setString(2, toModify.getSurname());
        pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        pstmt.setString(4, toModify.getEmail());

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
    }

    @Override
    public boolean delete(int idToDelete) throws SQLException {
        String sql = "DELETE FROM CLIENT WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, idToDelete);
        int affectedRows = pstmt.executeUpdate();

        return affectedRows > 0;
    }

    @Override
    public boolean incrementPurchases(int clientId, int amount) throws SQLException {
        String sql = "UPDATE CLIENT SET purchases = purchases + ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, amount);
        pstmt.setInt(2, clientId);

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
    }
    @Override
    public Client getById(int clientId) throws SQLException {
        String sql = "SELECT id, name, surname, email, purchases, CREATE_DATE, UPDATE_DATE FROM CLIENT WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, clientId);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setSurname(rs.getString("surname"));
            client.setEmail(rs.getString("email"));
            client.setPurchases(rs.getInt("purchases"));
            client.setCreateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
            client.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());
            return client;
        }
        return null;
    }

    @Override
    public List<Client> getAll() throws SQLException {
        String sql = "SELECT id, name, surname, email, purchases, CREATE_DATE, UPDATE_DATE FROM CLIENT";
        List<Client> clients = new ArrayList<>();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setSurname(rs.getString("surname"));
            client.setEmail(rs.getString("email"));
            client.setPurchases(rs.getInt("purchases"));
            client.setCreateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
            client.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());
            clients.add(client);
        }
        return clients;
    }

    @Override
    public Client getByEmail(String email) throws SQLException {
        String sql = "SELECT id, name, surname, email, purchases, CREATE_DATE, UPDATE_DATE FROM CLIENT WHERE email = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setSurname(rs.getString("surname"));
            client.setEmail(rs.getString("email"));
            client.setPurchases(rs.getInt("purchases"));
            client.setCreateDate(rs.getTimestamp("CREATE_DATE").toLocalDateTime());
            client.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());
            return client;
        }
        return null;
    }

}
