package ro.msg.learning.shop.controller.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.controller.dto.ProductCategoryDTO;
import ro.msg.learning.shop.domain.entity.ProductCategory;

@Component
public class ProductCategoryMapper {

    public ProductCategoryDTO toProductCategoryDTO(ProductCategory productCategory) {
        if (productCategory == null) {
            return null;
        }
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();

        productCategoryDTO.setName(productCategory.getName());
        productCategoryDTO.setDescription(productCategory.getDescription());

        return productCategoryDTO;
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
}
