import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import proton.ProtonApplication;
import proton.customers.Customer;
import proton.customers.CustomerRepository;
import proton.employees.Employee;
import proton.employees.EmployeeRepository;
import proton.parts.Part;
import proton.parts.PartRepository;
import proton.products.Product;
import proton.products.ProductRepository;

import java.util.List;
import java.util.Random;

@SpringBootTest(classes = ProtonApplication.class)
@Transactional
public class TestDataGenerator {

    private final PartRepository partRepo;
    private final ProductRepository productRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private CustomerRepository customerRepo;


    @Autowired
    public TestDataGenerator(PartRepository partRepo, ProductRepository productRepo) {
        this.partRepo = partRepo;
        this.productRepo = productRepo;
    }

    @Test
    @Commit
    void partsGenerator() {
        Random rand = new Random();

        List<Product> products = productRepo.findAll();

        long startId = partRepo.getNextId();
        for (long i = startId; i < startId + 10000; i++) {
            Part item = new Part();
            item.setName("Test Деталь " + String.valueOf(i));
            item.setProduct(products.get(rand.nextInt(products.size())));
            partRepo.save(item);
        }
        partRepo.flush();
    }

    @Test
    @Commit
    void productsGenerator() {
        Random rand = new Random();

        List<Employee> employees = employeeRepo.findAll();
        List<Customer> customers = customerRepo.findAll();

        long startId = productRepo.getNextId();
        for (long i = startId; i < startId + 1000; i++) {
            Product item = new Product();
            item.setName("Test Изделие " + i);
            item.setCheckEmployee(employees.get(rand.nextInt(employees.size())));
            item.setProduceEmployee(employees.get(rand.nextInt(employees.size())));
            item.setCustomer(customers.get(rand.nextInt(customers.size())));
            productRepo.save(item);
        }
        productRepo.flush();
    }
}
