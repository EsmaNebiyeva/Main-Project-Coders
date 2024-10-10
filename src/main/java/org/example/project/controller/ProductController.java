//package org.example.project.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.example.project.entity.other.Product;
//import org.example.project.model.ProductDTO;
//import org.example.project.service.other.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/api/product")
//@RequiredArgsConstructor
//public class ProductController {
//    @Autowired
//    private final ProductService productService;
//
//    @PostMapping("/create")
//    public ResponseEntity<String> createProduct(@RequestBody Product product) {
//       try{
//           productService.addProduct(product);
//       }
//        catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Data elave olundu", HttpStatus.CREATED);
//    }
//
//    @GetMapping("/get")
//    public ResponseEntity<List<Product>> getAllProducts(@RequestParam int page,
//                                                        @RequestParam int size) {
//        try{
//            List<Product> products = productService.getProducts(page, size).getContent();
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }
//    @GetMapping("/getall")
//    public ResponseEntity<List<Product>> getAllProducts() {
//        try {
//            List<Product> products = productService.getProducts();
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping("/deleteBy")
//    public ResponseEntity<String> deleteProduct(@RequestParam Long id) {
//        if (productService.deleteProductById(id)) {
//            return new ResponseEntity<>("Data silindi", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Data silinmedi", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteProducts(@RequestBody Product product) {
//        if (productService.deleteProduct(product )) {
//            return new ResponseEntity<>("Data silindi", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Data silinmedi", HttpStatus.BAD_REQUEST);
//        }
//    }
//    @GetMapping("/getBy")
//    public ResponseEntity<ProductDTO> getProductById(@RequestParam Long id) {
//       try {
//           ProductDTO product = productService.getProductById(id);
//           return new ResponseEntity<>(product, HttpStatus.OK);
//       }
//       catch(Exception e){
//           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//       }
//    }
//    @PutMapping("/update")
//    public ResponseEntity<Product> updateProduct(@RequestParam Long id,@RequestBody Product product) {
//       try {
//    Product product1 = productService.updateProduct(id, product);
//    return new ResponseEntity<>(product1, HttpStatus.OK);
//      }
//    catch(Exception e){
//    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//}
//
//    }
//    @PutMapping("/cancel")
//    public ResponseEntity<Product> updateProductWithCancellation(@RequestParam Long id,@RequestBody Product product) {
//        try {
//            Product product1 = this.productService.updateProductWithCancel(id, product);
//            return new ResponseEntity<>(product1, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//    @GetMapping("/search")
//    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
//        try{
//            List<Product> productByName = productService.getProductByName(name);
//            return new ResponseEntity<>(productByName,HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }
//}
package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Product;

import org.example.project.service.other.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
//alindi
    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            return new ResponseEntity<>("Data added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating product: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to add product", HttpStatus.BAD_REQUEST);
        }
    }
//alindi
    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam int page, @RequestParam int size) {
        try {
            List<Product> products = productService.getProducts(page, size);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving products: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//alindi
    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving all products: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//alindi
    @DeleteMapping("/deleteBy")
    public ResponseEntity<String> deleteProduct(@RequestParam Long id) {
        try {
            if (productService.deleteProductById(id)) {
                return new ResponseEntity<>("Data deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting product by id: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to delete product", HttpStatus.BAD_REQUEST);
        }
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteProducts(@RequestBody Product product) {
//        try {
//            if (productService.deleteProduct(product)) {
//                return new ResponseEntity<>("Data deleted successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            logger.error("Error deleting product: {}", e.getMessage(), e);
//            return new ResponseEntity<>("Failed to delete product", HttpStatus.BAD_REQUEST);
//        }
//    }
//alindi____________________________________________________________________________________________
    @GetMapping("/getBy")
    public ResponseEntity<Product> getProductById(@RequestParam Long id) {
        try {
            Product product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving product by id: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//alindi__________________________________________________________________________________________________
    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestParam Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//alindi_____________________________________________________________________________________________________
    @PutMapping("/cancel")
    public ResponseEntity<Product> updateProductWithCancellation(@RequestParam Long id, @RequestBody Product product) {

        try {
            Product updatedProduct = productService.updateProductWithCancel(id, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error cancelling product update: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//alindi______________________________________________________________________________________________________________
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        try {
            List<Product> products = productService.getProductByName(name);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error searching products: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //alindi
    @GetMapping("/category")
    public ResponseEntity<Long> findCategoryCount() {
       try{
           Long categoryCount = productService.getCategoryCount();
           return new ResponseEntity<>(categoryCount, HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
}
