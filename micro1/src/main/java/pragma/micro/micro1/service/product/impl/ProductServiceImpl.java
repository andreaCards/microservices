package pragma.micro.micro1.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pragma.micro.micro1.entity.Category;
import pragma.micro.micro1.entity.Product;
import pragma.micro.micro1.repository.ProductRepository;
import pragma.micro.micro1.service.product.ProductService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if(productDB==null){
            return null;
        }
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setPrice(product.getPrice());
        productDB.setCategory(product.getCategory());
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProduct(id);
        if(productDB==null){
            return null;
        }
        productDB.setStatus("DEACTIVATED");
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);
        if(productDB==null){
            return null;
        }
        Double stock = productDB.getStock()+quantity;
        productDB.setStock(stock);
        return productRepository.save(productDB);
    }
}
