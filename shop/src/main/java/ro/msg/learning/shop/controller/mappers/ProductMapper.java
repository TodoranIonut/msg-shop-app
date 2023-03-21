package ro.msg.learning.shop.controller.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.controller.dto.ProductDTO;
import ro.msg.learning.shop.controller.dto.ResponseProductDTO;
import ro.msg.learning.shop.domain.entity.Product;
import ro.msg.learning.shop.domain.entity.ProductCategory;
import ro.msg.learning.shop.domain.entity.Supplier;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductMapper {

    private final ProductCategoryMapper productCategoryMapper;
    private final SupplierMapper supplierMapper;

    public ProductDTO toProductDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setSupplierId(product.getSupplier().getId());
        productDTO.setImageUrl(product.getImageUrl());

        return productDTO;
    }
    public Product toProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(productDTO.getCategoryId());
        product.setCategory(productCategory);

        Supplier supplier = new Supplier();
        supplier.setId(productDTO.getSupplierId());
        product.setSupplier(supplier);

        product.setImageUrl(productDTO.getImageUrl());

        return product;
    }

    public List<ProductDTO> toProductDTOList(List<Product> productList) {

        if (productList.isEmpty()) {
            return Collections.emptyList();
        }

        return productList.stream()
                .map(this::toProductDTO)
                .toList();
    }

    public ResponseProductDTO toResponseProductDTO(Product product) {
        if (product == null) {
            return null;
        }

        ResponseProductDTO responseProductDTO = new ResponseProductDTO();

        responseProductDTO.setId(product.getId());
        responseProductDTO.setName(product.getName());
        responseProductDTO.setDescription(product.getDescription());
        responseProductDTO.setPrice(product.getPrice());
        responseProductDTO.setWeight(product.getWeight());
        responseProductDTO.setCategory(productCategoryMapper.toProductCategoryDTO(product.getCategory()));
        responseProductDTO.setSupplier(supplierMapper.toSupplierDTO(product.getSupplier()));
        responseProductDTO.setImageUrl(product.getImageUrl());

        return responseProductDTO;
    }
}
