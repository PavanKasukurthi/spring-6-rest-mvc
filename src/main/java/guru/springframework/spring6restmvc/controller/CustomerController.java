package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
@Autowired
    private final CustomerService customerService;
    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){

        customerService.deleteById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity putCustomer(@PathVariable("id") UUID customerId, @RequestBody Customer customer){

    customerService.updateCustomerById(customerId, customer);

    return new ResponseEntity(HttpStatus.NO_CONTENT);
}

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity postCustomer(@RequestBody Customer customer){

    Customer savedCustomer = customerService.saveNewCustomer(customer);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "api/v1/customer/" + savedCustomer.getId().toString());

    return new ResponseEntity(headers, HttpStatus.CREATED);
}

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        return customerService.listCustomers();
    }
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID id){
        return customerService.getCustomerById(id);
    }

}
