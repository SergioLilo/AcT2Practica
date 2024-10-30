package com.ggm.ad.ut2;

import com.ggm.ad.ut2.model.Product;
import com.ggm.ad.ut2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {

        logger.info("Iniciando programa");
        ProductService productService=new ProductService();

        Product guitar = new Product(1, "Guitarra Acústica", "Guitarra acústica de madera", 10, 149.99, true);

        productService.newProduct(guitar);

    }
}
