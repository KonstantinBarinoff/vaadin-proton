package proton.products;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

import java.util.List;

@Service
@Slf4j
public class ProductService extends BaseServiceImpl<Product, ProductRepository> {

    public ProductService(ProductRepository repository) {
        super(repository);
        log.debug("CONSTRUCTOR");
    }

    public List<ProductSummary> findByCustomerId(Long id) {
        return repository.findByCustomerId(id);
    }

//    public List<ProductGeneral> findAll(Long id) {
//        return repository.findAll(id);
//    }

}