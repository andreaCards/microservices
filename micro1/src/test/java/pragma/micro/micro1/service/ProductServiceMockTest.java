package pragma.micro.micro1.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import pragma.micro.micro1.entity.Category;
import pragma.micro.micro1.entity.Product;
import pragma.micro.micro1.repository.ProductRepository;
import pragma.micro.micro1.service.product.ProductService;
import pragma.micro.micro1.service.product.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        productService=new ProductServiceImpl(productRepository);
        Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date()).build();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product01));
        Mockito.when(productRepository.save(product01)).thenReturn(product01);
        List<Product> productList = new ArrayList<Product>();
        productList.add(product01);
        Mockito.when(productRepository.findAll()).thenReturn(productList);
        Mockito.when(productRepository.findByCategory(product01.getCategory())).thenReturn(productList);
    }

    @Test
    public void  findByID_thenReturnProduct(){
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void  findByID_thenReturnNull(){
        Product found = productService.getProduct(2L);
        Assertions.assertThat(found).isEqualTo(null);
    }

    @Test
    public void  whenUpdateStock_ThenReturnNewStock(){
        Product newStock = productService.updateStock(1L, 30.0);
        Assertions.assertThat(newStock.getStock()).isEqualTo(40);
    }

    @Test
    public void  whenUpdateStock_ThenReturnNull(){
        Product newStock = productService.updateStock(2L, 30.0);
        Assertions.assertThat(newStock).isEqualTo(null);
    }
    @Test
    public void  whenListAllProduct_ThenReturnList(){
        List<Product> productList = productService.listAllProduct();
        Assertions.assertThat(productList.size()).isEqualTo(1);
    }
    @Test
    public void  whenListAllProduct_ThenReturnEmpty(){
        List<Product> productList = new ArrayList<Product>();
        Mockito.when(productService.listAllProduct()).thenReturn(productList);
        Assertions.assertThat(productList.size()).isEqualTo(0);
    }

    @Test
    public void  whenCreateProduct_ThenReturnProduct(){
    Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date()).build();
    Mockito.when(productService.createProduct(product01)).thenReturn(product01);
    }

    @Test
    public void  whenCreateProductNoStatusNoDate_ThenReturnProduct(){
        Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99")).build();
        Mockito.when(productService.createProduct(product01)).thenReturn(product01);
    }
    @Test
    public void  whenUpdateNoProduct_ThenReturnNull(){
        Product product02 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99")).build();
        Mockito.when(productService.updateProduct(product02)).thenReturn(null);
    }
    @Test
    public void  whenDeleteProduct_ThenReturnProduct() {
        Product newStatus = productService.deleteProduct(1L);
        Assertions.assertThat(newStatus.getStatus()).isEqualTo("DEACTIVATED");
    }
    @Test
    public void  whenFindByCategory_ThenReturnProduct() {
        List<Product> productList = productService.findByCategory(Category.builder().id(1L).build());
        Assertions.assertThat(productList.size()).isEqualTo(1);
    }
    }
