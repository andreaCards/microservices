package pragma.micro.micro1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.condition.Negative;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import pragma.micro.micro1.entity.Category;
import pragma.micro.micro1.entity.Product;
import pragma.micro.micro1.service.product.impl.ProductServiceImpl;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class ProductControllerMockTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductServiceImpl productService;
    @Mock
    Product product01;
    private static final Long ID=1L;

    List<Product> productList= new ArrayList<>();

    @Mock
    Category category;

    @Mock
    BindingResult result;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest(){
        productList.add(product01);
        Mockito.when(productService.listAllProduct()).thenReturn(productList);
        assertEquals(Objects.requireNonNull(productController.findAll().getBody()).size(),productList.size());
    }

    @Test
    public void findAllNoContentTest(){
        Mockito.when(productService.listAllProduct()).thenReturn(productList);
        assertEquals(productController.findAll().getStatusCode(),HttpStatus.NO_CONTENT);
    }

    @Test
    public void findByCategoryTest(){
        product01.setCategory(Category.builder().id(1L).build());
        productList.add(product01);
        Mockito.when(productService.findByCategory(Category.builder().id(1L).build())).thenReturn(productList);
        assertThat(productController.findByCategory(1L).getBody()).contains(product01);
    }

    @Test
    public void findByCategoryNoProductTest(){
        Mockito.when(productService.findByCategory(category)).thenReturn(productList);
        assertEquals(productController.findByCategory(category.getId()).getStatusCode().value(),HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void findByIdTest(){
        Mockito.when(productService.getProduct(ID)).thenReturn(product01);
        assertEquals(productController.findById(ID).getBody(),product01);
    }

    @Test
    public void findByIdNoProductTest(){
        Mockito.when(productService.getProduct(2L)).thenReturn(null);
        assertEquals(productController.findById(2L).getStatusCode(),HttpStatus.NOT_FOUND);
    }

    @Test
    public void createProductTest(){
        Mockito.when(productService.createProduct(product01)).thenReturn(product01);
        assertEquals(productController.createProduct(product01, result).getStatusCode(),HttpStatus.CREATED);
    }


    @Test
    public void updateProductTest(){
        Mockito.when(productService.updateProduct(product01)).thenReturn(product01);
        assertEquals(productController.updateProduct(ID, product01).getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void updateProductNoExistsTest(){
        Product product02=new Product();
        Mockito.when(productService.updateProduct(product02)).thenReturn(null);
        assertEquals(productController.updateProduct(2L, product02).getStatusCode(),HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteProductNoExistsTest(){
        Mockito.when(productService.deleteProduct(2L)).thenReturn(null);
        assertEquals(productController.deleteProduct(2L).getStatusCode(),HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteProductExistsTest(){
        Mockito.when(productService.deleteProduct(1L)).thenReturn(product01);
        assertEquals(productController.deleteProduct(1L).getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void updateStockTest(){
        Mockito.when(productService.updateStock(ID,20.0)).thenReturn(product01);
        assertEquals(Objects.requireNonNull(productController.updateStock(ID, 20.0).getBody()),product01);
    }

    @Test
    public void updateStockNoExistsTest(){
        Mockito.when(productService.updateStock(2L, 30.0)).thenReturn(null);
        assertEquals(productController.updateStock(2L, 30.0).getStatusCode(),HttpStatus.NOT_FOUND);
    }

}
