package ro.msg.learning.shop.controller.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.controller.dto.SupplierDTO;
import ro.msg.learning.shop.domain.entity.Supplier;

@Component
public class SupplierMapper {

    public SupplierDTO toSupplierDTO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        SupplierDTO supplierDTO = new SupplierDTO();

        supplierDTO.setName(supplier.getName());

        return supplierDTO;
    }

    public Supplier toSupplier(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            return null;
        }
        Supplier supplier = new Supplier();

        supplier.setName(supplierDTO.getName());

        return supplier;
    }
}
