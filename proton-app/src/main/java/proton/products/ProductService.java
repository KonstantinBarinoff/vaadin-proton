package proton.products;

import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

@Service
public class ProductService extends BaseServiceImpl<Product, ProductRepository> {

    public ProductService(ProductRepository repository) {
        super(repository);
    }

}