import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import proton.ProtonApplication;
import proton.parts.Part;
import proton.parts.PartRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static performancetuning.sqltracker.AssertSqlCount.*;


/**
 * Тесты репозитория на MSSQL базе
 * Каждый метод выполняется в отдельной транзакции
 */
@SpringBootTest(classes = ProtonApplication.class)
@Transactional
public class PartRepositoryTest extends BaseTest {

    private final PartRepository repo;

    @Autowired
    public PartRepositoryTest(PartRepository repo) {
        this.repo = repo;
    }

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
        repo.saveAndFlush(part);
        assertUpdateCount(1);
    }

    @Test
    void deleteProduct() {
        repo.deleteById(10L);
        repo.flush();
        assertSelectCount(1);
        assertDeleteCount(1);
    }

}
