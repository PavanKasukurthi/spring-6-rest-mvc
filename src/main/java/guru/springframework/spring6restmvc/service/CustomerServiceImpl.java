package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Jake Peralta")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version(1)
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Amy Santiago")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version(1).build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Charles Boyle")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version(1).build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {

        Customer savedCustomer = customer.builder()
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .customerName(customer.getCustomerName())
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {

        Customer existingCustomer = customerMap.get(customerId);

        existingCustomer.setCustomerName(customer.getCustomerName());

        customerMap.put(existingCustomer.getId(), existingCustomer);

    }

    @Override
    public void deleteById(UUID customerId) {

        customerMap.remove(customerId);

    }

    @Override
    public void patchCustomerById(UUID customerId, Customer customer) {

        Customer existing = customerMap.get(customerId);

        if(StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }
    }


}
