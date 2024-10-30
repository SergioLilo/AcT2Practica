package com.ggm.ad.ut2.service;

import com.ggm.ad.ut2.dao.ConnectionManager;
import com.ggm.ad.ut2.dao.IProductDao;
import com.ggm.ad.ut2.dao.impl.ProductDaoJdbc;
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
        logger.info("Inserting new Product: "+toCreate);

        ConnectionManager instance=ConnectionManager.getInstance();
        try (Connection conn= instance.getConnection()){

            toCreate.setCreateDate(LocalDateTime.now());
            toCreate.setUpdateDate(LocalDateTime.now());

            productDao=new ProductDaoJdbc(conn);
            productDao.Insert(toCreate);

            logger.info("Product succesfully created. ProductID: ");

        } catch (SQLException e) {
            logger.error("There has been an error while operating with the DB",e);
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("General ERROR");
        }

        return toCreate;
    }

}
