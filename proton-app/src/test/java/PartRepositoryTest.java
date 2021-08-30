import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import performancetuning.sqltracker.AssertSqlCount;
import proton.ProtonApplication;
import proton.parts.Part;
import proton.parts.PartRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static performancetuning.sqltracker.AssertSqlCount.*;


/**
 * Тесты репозитория на серверной базе
 * Каждый метод выполняется в отдельной транзакции
 */
@SpringBootTest(classes = ProtonApplication.class)
@Transactional
//@RunWith(SpringRunner.class)
//@DataJpaTest
public class PartRepositoryTest extends BaseTest {

    @Autowired
    private PartRepository repo;

    @Test
    void findAll() {
        assertThat(repo).isNotNull();

        List<Part> partList = repo.findAll();
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
        assertThat(repo).isNotNull();

        List<Part> partList = repo.findByProductId(162L);
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
        AssertSqlCount.reset();
        repo.saveAndFlush(part);
        assertUpdateCount(1);
    }

    @Test
    void deleteProduct() {
        AssertSqlCount.reset();
        repo.deleteById(10L);
        repo.flush();
        assertSelectCount(1);
        assertDeleteCount(1);
    }

}
