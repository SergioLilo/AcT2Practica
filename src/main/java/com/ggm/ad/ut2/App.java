package com.ggm.ad.ut2;

import com.ggm.ad.ut2.model.Client;
import com.ggm.ad.ut2.model.Product;
import com.ggm.ad.ut2.service.ClientService;
import com.ggm.ad.ut2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {

        logger.info("Iniciando programa");
        ProductService productService=new ProductService();
        ClientService clientService=new ClientService();
        Product guitar = new Product("Guitarra Acústica", "Guitarra acústica de madera", 10, 149.99, true);
        Product bassGuitar = new Product("Bajo Eléctrico", "Bajo eléctrico de 4 cuerdas", 7, 249.99, true);
        Product drumSet = new Product(11,"Batería Acústica", "Set de batería acústica de 5 piezas", 3, 499.99, true);
        Client client1=new Client("SI","PLASLSASA","S@gmail.com",6 );
        //productService.newProduct(drumSet);
        /*
        guitar.setName("Guitarra Electrica");
        guitar.setDescription("Guitarra Electrica Nuvea");
        guitar.setPrice(20);
        productService.updateProduct(guitar);
        */
        //productService.deleteProduct(drumSet);
        //productService.getProductById(5);
       // productService.getAllProducts();
        //productService.substractStock(1,1);
        //productService.getAllByNameAlike("=");
        //clientService.updateClient(client1);
        //clientService.getAllClients();
        clientService.newClient(client1);
        clientService.deleteClient(client1);
       // clientService.getAllClients();

    }
}
