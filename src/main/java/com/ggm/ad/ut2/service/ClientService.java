package com.ggm.ad.ut2.service;

import com.ggm.ad.ut2.dao.ConnectionManager;
import com.ggm.ad.ut2.dao.IClientDao;
import com.ggm.ad.ut2.dao.impl.ClientDaoJdbc;
import com.ggm.ad.ut2.dao.impl.ProductDaoJdbc;
import com.ggm.ad.ut2.exceptions.CannotDeleteException;
import com.ggm.ad.ut2.exceptions.DuplicateClientException;
import com.ggm.ad.ut2.exceptions.GeneralErrorException;
import com.ggm.ad.ut2.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private IClientDao clientDao;
    public Client newClient(Client toCreate)  {
        toCreate.setCreateDate(LocalDateTime.now());
        toCreate.setUpdateDate(LocalDateTime.now());
        logger.info("Inserting new Client: " + toCreate);

        ConnectionManager instance = ConnectionManager.getInstance();
        try (Connection conn = instance.getConnection()) {
            clientDao = new ClientDaoJdbc(conn);
            Client existingClient = clientDao.getByEmail(toCreate.getEmail());
            if (existingClient != null) {
                String errorMessage = "A client with the email " + toCreate.getEmail() + " already exists.";
                logger.error(errorMessage);
                throw new DuplicateClientException(errorMessage);
            }


            clientDao.insert(toCreate);
            logger.info("Client successfully created.");

        } catch (DuplicateClientException e) {
            logger.error("Duplicate client detected: " + e);

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);
            throw new RuntimeException(e);
        } catch (GeneralErrorException e) {
            logger.error("General ERROR", e);
        } catch (Exception e) {
            throw new GeneralErrorException("General error occurred while creating client");
        }

        return toCreate;
    }
    public Client getClientByEmail(String email) throws SQLException{
        logger.info("Fetching Client with Email: " + email);

        ConnectionManager instance = ConnectionManager.getInstance();
        try (Connection conn = instance.getConnection()) {
            clientDao = new ClientDaoJdbc(conn);
            Client client = clientDao.getByEmail(email);

            if (client == null) {
                throw new SQLException("Client with the given email does not exist");
            }

            logger.info("Client successfully fetched: " + client);
            return client;
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);
        } catch (GeneralErrorException e) {
            logger.error("General ERROR", e);
        } catch (Exception e) {
            throw new GeneralErrorException("General error occurred while fetching client by email");
        }

        return null;
    }
    public Client updateClient(Client newInfo) {
        newInfo.setUpdateDate(LocalDateTime.now());
        logger.info("Updating Client: " + newInfo);

        ConnectionManager instance = ConnectionManager.getInstance();
        try (Connection conn = instance.getConnection()) {
            clientDao = new ClientDaoJdbc(conn);
           Boolean update= clientDao.update(newInfo);

           if (!update){
               throw new SQLException("Error while updatin Client with email: "+newInfo.getEmail());
           }

            logger.info("Client successfully updated");
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);
            throw new RuntimeException(e);
        } catch (GeneralErrorException e) {
            logger.error("General ERROR", e);
        } catch (Exception e) {
            throw new GeneralErrorException("General error occurred while updating client");
        }

        return newInfo;
    }

    public Client deleteClient(Client client) {



        ConnectionManager instance = ConnectionManager.getInstance();
        try (Connection conn = instance.getConnection()) {
            clientDao = new ClientDaoJdbc(conn);
            client=clientDao.getByEmail(client.getEmail());
            logger.info("Deleting Client: " + client);
            boolean deleted = clientDao.delete(client.getId());

            if (!deleted) {
                String errorMessage = "Client couldn't be deleted ";
                throw new CannotDeleteException(errorMessage);
            }

            logger.info("Client successfully deleted. ClientID: " + client.getId());
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);
        } catch (CannotDeleteException e) {
            logger.error("Error while deleting client", e);
        } catch (GeneralErrorException e) {
            logger.error("General ERROR", e);
        } catch (Exception e) {
            throw new GeneralErrorException("General error occurred while deleting client");
        }

        return client;
    }
    public List<Client> getAllClients() {
        logger.info("Fetching all clients");

        ConnectionManager instance = ConnectionManager.getInstance();
        try (Connection conn = instance.getConnection()) {
            clientDao = new ClientDaoJdbc(conn);
            List<Client> clients = clientDao.getAll();

            if (clients.isEmpty()) {
                logger.info("No clients found in the database.");
            } else {
                System.out.println(clients);
                logger.info("Clients successfully fetched: " + clients.size() + " clients found.");
            }

            return clients;
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);
        } catch (GeneralErrorException e) {
            logger.error("General ERROR", e);
        } catch (Exception e) {
            throw new GeneralErrorException("General error occurred while fetching all clients");
        }

        return new ArrayList<>();  // Return an empty list if there’s an error
    }

}
