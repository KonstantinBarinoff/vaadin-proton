import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import proton.ProtonApplication;
import proton.parts.Part;
import proton.parts.PartRepository;
import proton.products.ProductRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static performancetuning.sqltracker.AssertSqlCount.*;


/**
 * Тесты репозитория на MSSQL базе
 * Каждый метод выполняется в отдельной транзакции
 */
@SpringBootTest(classes = ProtonApplication.class)
@Transactional
public class PartRepositoryTest extends BaseRepositoryTest {

    private final PartRepository partRepo;
    private final ProductRepository productRepo;

    @Autowired
    public PartRepositoryTest(PartRepository partRepo, ProductRepository productRepo) {
        this.partRepo = partRepo;
        this.productRepo = productRepo;
    }

    @Test
    void findAll() {
        assertThat(partRepo).isNotNull();

        List<Part> partList = partRepo.findAll();
        assertSelectCount(1);

        Part part = partList.stream().findAny().get();
        System.out.println(part);
        System.out.println(part.getProduct());
        System.out.println(part.getProduct().getCheckEmployee());
        System.out.println(part.getProduct().getProduceEmployee());
        System.out.println(part.getProduct().getCustomer());
    }

    @Test
    void findByProductIdAndUpdate() {
        assertThat(partRepo).isNotNull();

        List<Part> partList = partRepo.findByProductId(162L);
        // 1 запрос: Деталь и Изделие
        // 2 запрос: Заказчиков, Сотрудников (изготовил, проверил) для Изделия
        assertSelectCount(2);

        Part part = partList.stream().findAny().get();
        System.out.println(part);
        System.out.println(part.getProduct());
        System.out.println(part.getProduct().getCheckEmployee());
        System.out.println(part.getProduct().getProduceEmployee());
        System.out.println(part.getProduct().getCustomer());

        part.setName("New Name");
        partRepo.saveAndFlush(part);
        assertUpdateCount(1);
    }

    @Test
    void deleteProduct() {
        partRepo.deleteById(10L);
        partRepo.flush();
        assertSelectCount(1);
        assertDeleteCount(1);
    }

    @Test
    void findByProductIdFilter() {
        List<Part> partList = partRepo.findByProductIdFilter(162L, "Крышка");
        assertSelectCount(1);
        Part part = partList.stream().findAny().get();
        assertThat(part.getId() == 9);


    }


}
