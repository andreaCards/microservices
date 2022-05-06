package pragma.micro3.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pragma.micro3.model.Customer;

@Component
public class CustomerHystrixFallbackFactory  implements CustomerClient{
    @Override
    public ResponseEntity<Customer> getCustomer(long id) {
        Customer customer = Customer.builder()
                .firstName("none")
                .lastName("none")
                .email("none")
                .photoUrl("none").build();
        return ResponseEntity.ok(customer);
    }
}