package proton.customers;

import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

@Service
public class CustomerService extends BaseServiceImpl<Customer, CustomerRepository> {
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }
}