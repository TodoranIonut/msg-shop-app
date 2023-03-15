package ro.msg.learning.shop.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.entity.Product;
import ro.msg.learning.shop.domain.repository.ProductRepository;
import ro.msg.learning.shop.exception.ShopAppException;
import ro.msg.learning.shop.exception.product.ProductNotFoundException;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer productId) throws ShopAppException {
        return productRepository.findById(productId)
                .orElseThrow( ()-> {
                    log.debug("product id:{} is NOT found", productId);
                    return new ProductNotFoundException(productId);
                });
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, Product product) throws ShopAppException {
        Product findProduct = getProductById(productId);
        findProduct.setName(product.getName());
        findProduct.setDescription(product.getDescription());
        findProduct.setPrice(product.getPrice());
        findProduct.setWeight(product.getWeight());
        findProduct.setCategory(product.getCategory());
        findProduct.setSupplier(product.getSupplier());
        findProduct.setImageUrl(product.getImageUrl());
        productRepository.save(findProduct);
        return findProduct;
    }

    @Override
    public void deleteProductById(Integer productId) {
        try{
            productRepository.deleteById(productId);
        }catch (Exception ignore){
            log.debug("delete id:{} failed",productId);
        }
    }
}
