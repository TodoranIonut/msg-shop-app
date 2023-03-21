package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.msg.learning.shop.controller.dto.ProductDTO;
import ro.msg.learning.shop.controller.dto.ResponseProductDTO;
import ro.msg.learning.shop.controller.mappers.ProductMapper;
import ro.msg.learning.shop.domain.entity.Product;
import ro.msg.learning.shop.exception.ShopAppException;
import ro.msg.learning.shop.service.product.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;


    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> geAllProducts(){
        List<Product> produtsList = productService.getAllProducts();
        List<ProductDTO> productsDTOList = productMapper.toProductDTOList(produtsList);
        return ResponseEntity.ok().body(productsDTOList);
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<ResponseProductDTO> getProductById(@PathVariable Integer productId) throws ShopAppException {
        Product findProduct = productService.getProductById(productId);
        ResponseProductDTO responseProductDTO = productMapper.toResponseProductDTO(findProduct);
        return ResponseEntity.ok().body(responseProductDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseProductDTO> createProduct(@RequestBody ProductDTO productDTO) throws ShopAppException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/product/create").toUriString());
        Product newProduct = productMapper.toProduct(productDTO);
        Product savedProduct = productService.saveProduct(newProduct);
        ResponseProductDTO responseProductDTO = productMapper.toResponseProductDTO(savedProduct);
        return ResponseEntity.created(uri).body(responseProductDTO);
    }

    @PutMapping("/update/id/{productId}")
    public ResponseEntity<ResponseProductDTO> updateProductById(@PathVariable Integer productId, @RequestBody ProductDTO productDTO) throws ShopAppException {
        Product newProduct = productMapper.toProduct(productDTO);
        Product savedProduct = productService.updateProduct(productId,newProduct);
        ResponseProductDTO responseProductDTO = productMapper.toResponseProductDTO(savedProduct);
        return ResponseEntity.ok().body(responseProductDTO);
    }

    @DeleteMapping("/delete/id/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.ok().body("Deleted successfully!");
    }
}
