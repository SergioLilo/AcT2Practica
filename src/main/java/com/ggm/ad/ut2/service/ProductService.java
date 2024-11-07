package com.ggm.ad.ut2.service;

import com.ggm.ad.ut2.dao.ConnectionManager;
import com.ggm.ad.ut2.dao.IProductDao;
import com.ggm.ad.ut2.dao.impl.ProductDaoJdbc;
import com.ggm.ad.ut2.exceptions.CannotDeleteException;
import com.ggm.ad.ut2.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private IProductDao productDao;


    public Product newProduct(Product toCreate){
        toCreate.setCreateDate(LocalDateTime.now());
        toCreate.setUpdateDate(LocalDateTime.now());
        logger.info("Inserting new Product: "+toCreate);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){



            productDao=new ProductDaoJdbc(conn);
            productDao.insert(toCreate);

            logger.info("Product succesfully created. ProductID: "+toCreate.getId());

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return toCreate;
    }
    public Product updateProduct(Product newInfo){

        newInfo.setUpdateDate(LocalDateTime.now());
        logger.info("updating  Product: "+newInfo);
        ConnectionManager instance=ConnectionManager.getInstance();

        try (Connection conn= instance.getConnection()){

            productDao=new ProductDaoJdbc(conn);
            productDao.update(newInfo);
            logger.info("Product succesfully updated. ProductID: "+newInfo.getId());

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return newInfo;
    }
    public Product deleteProduct(Product prod){

        logger.info("deleting  Product: "+prod);
        ConnectionManager instance=ConnectionManager.getInstance();

        try (Connection conn= instance.getConnection()){

            productDao=new ProductDaoJdbc(conn);
            boolean borrado=productDao.delete(prod.getId());
            if (!borrado){
                String errorMessage = "product couldnt be deleted, ID: " + prod.getId();
                throw new CannotDeleteException(errorMessage);
            }

            logger.info("Product succesfully deleted. ProductID: "+prod.getId());


        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (CannotDeleteException e){
            logger.error("There has benn an error while deleting ",e);
        }
        catch (Exception e){
            logger.error("General ERROR");
        }

        return prod;
    }
    public Product getProductById(int productId) {
        logger.info("Fetching Product with ID: " + productId);
        ConnectionManager instance = ConnectionManager.getInstance();

        try (Connection conn = instance.getConnection()) {
            productDao = new ProductDaoJdbc(conn);
            Product product = productDao.getById(productId);
            if (product==null){
                throw new SQLException();
            }
            logger.info("Product successfully fetched: " + product.toString());
            return product;

        } catch (SQLException e) {
        logger.error("There has been an error while operating with the DB", e);
        throw new RuntimeException(e);
    }   catch (Exception e) {
        logger.error("General ERROR", e);
    }
        return null;
    }

}
