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
import ro.msg.learning.shop.controller.dto.ResponseCreatedProductDTO;
import ro.msg.learning.shop.controller.mappers.Mapper;
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
    private final Mapper mapper;


    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> geAllProducts(){
        List<Product> produtsList = productService.getAllProducts();
        List<ProductDTO> productsDTOList = mapper.toProductDTOList(produtsList);
        return ResponseEntity.ok().body(productsDTOList);
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId) throws ShopAppException {
        Product findProduct = productService.getProductById(productId);
        ProductDTO responseProductDTO = mapper.toProductDTO(findProduct);
        return ResponseEntity.ok().body(responseProductDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseCreatedProductDTO> createProduct(@RequestBody ProductDTO productDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/product/create").toUriString());
        Product newProduct = mapper.toProduct(productDTO);
        Product savedProduct = productService.saveProduct(newProduct);
        ResponseCreatedProductDTO responseCreatedProductDTO = mapper.toResponseCreatedProductDTO(savedProduct);
        return ResponseEntity.created(uri).body(responseCreatedProductDTO);
    }

    @PutMapping("/update/id/{productId}")
    public ResponseEntity<ResponseCreatedProductDTO> updateProductById(@PathVariable Integer productId, @RequestBody ProductDTO productDTO) throws ShopAppException {
        Product newProduct = mapper.toProduct(productDTO);
        Product savedProduct = productService.updateProduct(productId,newProduct);
        ResponseCreatedProductDTO responseCreatedProductDTO = mapper.toResponseCreatedProductDTO(savedProduct);
        return ResponseEntity.ok().body(responseCreatedProductDTO);
    }

    @DeleteMapping("/delete/id/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.ok().body("Deleted successfully!");
    }

}
