package com.ggm.ad.ut2.service;

import com.ggm.ad.ut2.dao.ConnectionManager;
import com.ggm.ad.ut2.dao.IProductDao;
import com.ggm.ad.ut2.dao.impl.ProductDaoJdbc;
import com.ggm.ad.ut2.exceptions.CannotDeleteException;
import com.ggm.ad.ut2.exceptions.GeneralErrorException;
import com.ggm.ad.ut2.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private IProductDao productDao;


    public Product newProduct(Product toCreate) {
        toCreate.setCreateDate(LocalDateTime.now());
        toCreate.setUpdateDate(LocalDateTime.now());
        logger.info("Inserting new Product: " + toCreate);

        ConnectionManager instance = ConnectionManager.getInstance();
        try (Connection conn = instance.getConnection()) {


            productDao = new ProductDaoJdbc(conn);
            productDao.insert(toCreate);

            logger.info("Product succesfully created. ProductID: " + toCreate.getId());

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);
            throw new RuntimeException(e);
        } catch (GeneralErrorException e) {
            logger.error("General ERROR ",e);
        }catch (Exception e) {
            throw new GeneralErrorException("");
        }

        return toCreate;
    }

    public Product updateProduct(Product newInfo) {

        newInfo.setUpdateDate(LocalDateTime.now());
        logger.info("updating  Product: " + newInfo);
        ConnectionManager instance = ConnectionManager.getInstance();

        try (Connection conn = instance.getConnection()) {

            productDao = new ProductDaoJdbc(conn);
            productDao.update(newInfo);
            logger.info("Product succesfully updated. ProductID: " + newInfo.getId());

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);
            throw new RuntimeException(e);
        }catch (GeneralErrorException e) {
            logger.error("General ERROR ",e);
        } catch (Exception e) {
            throw new GeneralErrorException("");
        }

        return newInfo;
    }

    public Product deleteProduct(Product prod) {

        logger.info("deleting  Product: " + prod);
        ConnectionManager instance = ConnectionManager.getInstance();

        try (Connection conn = instance.getConnection()) {

            productDao = new ProductDaoJdbc(conn);
            boolean borrado = productDao.delete(prod.getId());
            if (!borrado) {
                String errorMessage = "product couldnt be deleted, ID: " + prod.getId();
                throw new CannotDeleteException(errorMessage);
            }

            logger.info("Product succesfully deleted. ProductID: " + prod.getId());


        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);

        } catch (CannotDeleteException e) {
            logger.error("There has benn an error while deleting ", e);
        } catch (GeneralErrorException e) {
            logger.error("General ERROR ",e);
        } catch (Exception e) {
            throw new GeneralErrorException("GENERAL ERROR");
        }

        return prod;
    }

    public Product getProductById(int productId) {

        logger.info("Fetching Product with ID: " + productId);
        ConnectionManager instance = ConnectionManager.getInstance();

        try (Connection conn = instance.getConnection()) {

            productDao = new ProductDaoJdbc(conn);

            Product product = productDao.getById(productId);
            if (product == null) {
                throw new SQLException("Product Doesn't exist");
            }
            logger.info("Product successfully fetched: " + product.toString());
            return product;

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);

        } catch (GeneralErrorException e) {
            logger.error("General ERROR ",e);
        }catch (Exception e){
            throw new GeneralErrorException("");
        }
        return null;
    }

    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        ConnectionManager instance = ConnectionManager.getInstance();

        try (Connection conn = instance.getConnection()) {
            productDao = new ProductDaoJdbc(conn);
            List<Product> products = productDao.getAll();

            if (products.isEmpty()) {
                logger.info("No products found in the database.");
            } else {
                logger.info("Products successfully fetched: " + products.size() + " products found.");
            }

            return products;

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB", e);
        } catch (GeneralErrorException e) {
            logger.error("General ERROR ",e);
        }catch (Exception e){
            throw new GeneralErrorException("");
        }

        return new ArrayList<>();  // Return an empty list if there’s an error
    }

    public boolean substractStock(int idToSubstract, int amount) {
        logger.info("Attempting to subtract " + amount + " from stock of product with ID: " + idToSubstract);
        ConnectionManager instance = ConnectionManager.getInstance();
        boolean updateSuccessful = false;


        try (Connection conn = instance.getConnection()) {
            productDao = new ProductDaoJdbc(conn);
            Product product = productDao.getById(idToSubstract);
            updateSuccessful = productDao.substractStock(idToSubstract, amount);

            if (updateSuccessful) {

                logger.info("Stock successfully subtracted. New stock for product ID " + idToSubstract);
                return true;
            } else {

                throw new SQLException("Failed to update stock for product ID " + idToSubstract);
            }
        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB ",e);

        }catch (GeneralErrorException e) {
            logger.error("General ERROR ",e);
        }catch (Exception e){
            throw new GeneralErrorException("");
        }

        return false;
    }
    public List<Product> getAllByNameAlike( String name){
        logger.info("Fetching all products");
        ConnectionManager instance = ConnectionManager.getInstance();

        try (Connection conn = instance.getConnection()) {
            productDao = new ProductDaoJdbc(conn);
            List<Product> products = productDao.getAllByNameAlike(name);

            if (products.isEmpty()) {
                throw new SQLException("No Products were find with the name "+name);
            } else {
                logger.info("Products successfully fetched: " + products.size() + " products found.");
                logger.info(products.toString());
            }


            return products;
        }

        catch (SQLException e) {
            logger.error("There has been an error while operating with the DB ",e);
        }catch (GeneralErrorException e){
            logger.error("General ERROR ",e);
        }catch (Exception e){
            throw new GeneralErrorException("");
        }

        return new ArrayList<>();
    }

}
