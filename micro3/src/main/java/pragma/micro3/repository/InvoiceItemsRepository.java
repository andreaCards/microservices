package pragma.micro3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pragma.micro3.entity.InvoiceItem;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long> {
}
