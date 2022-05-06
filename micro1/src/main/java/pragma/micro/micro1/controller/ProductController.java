package pragma.micro.micro1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pragma.micro.micro1.entity.Category;
import pragma.micro.micro1.entity.Product;
import pragma.micro.micro1.service.product.ProductService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        List<Product> productList= productService.listAllProduct();
        if(productList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(productList);
        }
    }
    @GetMapping(value = "category/{id}")
    public ResponseEntity<List<Product>> findByCategory(@PathVariable Long categoryId){
        List<Product> productList= productService.findByCategory(Category.builder().id(categoryId).build());
        if(productList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(productList);
        }
    }
    @GetMapping(value= "/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long id){
        Product product= productService.getProduct(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct (@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }

        Product productCreate= productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable("id") Long id, @RequestBody Product product){
        product.setId(id);
        Product productUpdate= productService.updateProduct(product);
        if(productUpdate == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct (@PathVariable("id") Long id){
        Product productDelete= productService.deleteProduct(id);
        if(productDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @PutMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStock (@PathVariable("id") Long id, @RequestParam(name="quantity", required = true) Double stock){
        Product productUpdate= productService.updateStock(id, stock);
        if(productUpdate == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productUpdate);
    }
    private String formatMessage(BindingResult result) {
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder().code("01").messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }

}
