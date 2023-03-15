package ro.msg.learning.shop.service.product;


import ro.msg.learning.shop.domain.entity.Product;
import ro.msg.learning.shop.exception.ShopAppException;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(Integer productId) throws ShopAppException;
    Product saveProduct(Product product);
    Product updateProduct(Integer productId, Product product) throws ShopAppException;
    void deleteProductById(Integer productId);
}
