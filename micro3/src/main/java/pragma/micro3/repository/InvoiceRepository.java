package pragma.micro3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pragma.micro3.entity.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    public List<Invoice> findByCustomerId(Long customerId );
    public Invoice findByNumberInvoice(String numberInvoice);
}