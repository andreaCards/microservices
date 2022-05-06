package pragma.micro.micro2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pragma.micro.micro2.entity.Customer;
import pragma.micro.micro2.entity.Region;

import java.util.List;

public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    public Customer findByNumberID(String numberID);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByRegion(Region region);
}