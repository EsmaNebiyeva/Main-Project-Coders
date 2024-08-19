package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.Product;
import org.example.project.model.ProductDTO;
import org.example.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
       try{
           productService.addProduct(product);
       }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Data elave olundu", HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam int page,
                                                        @RequestParam int size) {
        try{
            List<Product> products = productService.getProducts(page, size).getContent();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteBy")
    public ResponseEntity<String> deleteProduct(@RequestParam Long id) {
        if (productService.deleteProductById(id)) {
            return new ResponseEntity<>("Data silindi", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Data silinmedi", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody Product product) {
        if (productService.deleteProduct(product )) {
            return new ResponseEntity<>("Data silindi", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Data silinmedi", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getBy")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam Long id) {
       try {
           ProductDTO product = productService.getProductById(id);
           return new ResponseEntity<>(product, HttpStatus.OK);
       }
       catch(Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestParam Long id,@RequestBody Product product) {
       try {
    Product product1 = productService.updateProduct(id, product);
    return new ResponseEntity<>(product1, HttpStatus.OK);
      }
    catch(Exception e){
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}

    }
    @PutMapping("/cancel")
    public ResponseEntity<Product> updateProductWithCancellation(@RequestParam Long id,@RequestBody Product product) {
        try {
            Product product1 = this.productService.updateProductWithCancel(id, product);
            return new ResponseEntity<>(product1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        try{
            List<Product> productByName = productService.getProductByName(name);
            return new ResponseEntity<>(productByName,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
