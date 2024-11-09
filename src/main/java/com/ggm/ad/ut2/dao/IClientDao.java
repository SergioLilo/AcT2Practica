package com.ggm.ad.ut2.dao;

import com.ggm.ad.ut2.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface IClientDao {
    int insert(Client client) throws SQLException;
    boolean update(Client client) throws SQLException;
    boolean delete(int clientId) throws SQLException;
    boolean incrementPurchases(int clientId, int amount) throws SQLException;
    Client getById(int clientId) throws SQLException;
    List<Client> getAll() throws SQLException;
    Client getByEmail(String email) throws SQLException;
}
