import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import proton.ProtonApplication;
import proton.products.Product;
import proton.products.ProductRepository;
import proton.products.ProductSummary;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static performancetuning.sqltracker.AssertSqlCount.assertSelectCount;


@SpringBootTest(classes = ProtonApplication.class)
@Transactional
public class ProductRepositoryTest extends BaseTest {

    private final ProductRepository repo;

    @Autowired
    public ProductRepositoryTest(ProductRepository repo) {
        this.repo = repo;
    }

    @Test
    void findAll() {
        assertThat(repo).isNotNull();

        List<Product> productList = repo.findAll();
        assertSelectCount(1);

        Product product = productList.stream().findAny().get();
        System.out.println(product);
        System.out.println(product.getCustomer());
        System.out.println(product.getCheckEmployee());
        System.out.println(product.getProduceEmployee());
    }

    @Test
    void findByCustomerId() {
        assertThat(repo).isNotNull();

        List<ProductSummary> partList = repo.findByCustomerId(162L);
        assertSelectCount(1);
    }


}
