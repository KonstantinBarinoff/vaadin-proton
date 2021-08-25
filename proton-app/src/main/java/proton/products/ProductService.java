package proton.products;

import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

import java.util.List;

@Service
public class ProductService extends BaseServiceImpl<Product, ProductRepository> {

    public ProductService(ProductRepository repository) {
        super(repository);
    }

    public List<ProductGeneral> findByCustomerId(Long id) {
        return repository.findByCustomerId(id);
    }


}