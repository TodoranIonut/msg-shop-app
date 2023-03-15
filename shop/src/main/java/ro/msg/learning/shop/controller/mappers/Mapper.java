package ro.msg.learning.shop.controller.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.controller.dto.ResponseCreatedProductDTO;
import ro.msg.learning.shop.controller.dto.ProductCategoryDTO;
import ro.msg.learning.shop.controller.dto.ProductDTO;
import ro.msg.learning.shop.controller.dto.SupplierDTO;
import ro.msg.learning.shop.domain.entity.Product;
import ro.msg.learning.shop.domain.entity.ProductCategory;
import ro.msg.learning.shop.domain.entity.Supplier;

import java.util.Collections;
import java.util.List;

@Component
public class Mapper {

    public SupplierDTO toSupplierDTO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        SupplierDTO supplierDTO = new SupplierDTO();

        supplierDTO.setName(supplier.getName());

        return supplierDTO;
    }

    public ProductCategoryDTO toProductCategoryDTO(ProductCategory productCategory) {
        if (productCategory == null) {
            return null;
        }
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();

        productCategoryDTO.setName(productCategory.getName());
        productCategoryDTO.setDescription(productCategory.getDescription());

        return productCategoryDTO;
    }


    public Supplier toSupplier(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            return null;
        }
        Supplier supplier = new Supplier();

        supplier.setName(supplierDTO.getName());

        return supplier;
    }

    public ProductCategory toProductCategory(ProductCategoryDTO productCategoryDTO) {
        if (productCategoryDTO == null) {
            return null;
        }
        ProductCategory productCategory = new ProductCategory();

        productCategory.setName(productCategoryDTO.getName());
        productCategory.setDescription(productCategoryDTO.getDescription());

        return productCategory;
    }

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
        productDTO.setCategory(toProductCategoryDTO(product.getCategory()));
        productDTO.setSupplier(toSupplierDTO(product.getSupplier()));
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
        product.setCategory(toProductCategory(productDTO.getCategory()));
        product.setSupplier(toSupplier(productDTO.getSupplier()));
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

    public ResponseCreatedProductDTO toResponseCreatedProductDTO(Product product) {
        if (product == null) {
            return null;
        }

        ResponseCreatedProductDTO responseCreatedProductDTO = new ResponseCreatedProductDTO();

        responseCreatedProductDTO.setId(product.getId());
        responseCreatedProductDTO.setName(product.getName());
        responseCreatedProductDTO.setDescription(product.getDescription());
        responseCreatedProductDTO.setPrice(product.getPrice());
        responseCreatedProductDTO.setWeight(product.getWeight());
        responseCreatedProductDTO.setCategory(toProductCategoryDTO(product.getCategory()));
        responseCreatedProductDTO.setSupplier(toSupplierDTO(product.getSupplier()));
        responseCreatedProductDTO.setImageUrl(product.getImageUrl());

        return responseCreatedProductDTO;
    }
}
