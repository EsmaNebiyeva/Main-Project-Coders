package org.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Product;

import org.example.project.model.ProductDTO;
import org.example.project.model.ProductPage;
import org.example.project.repository.other.OrderRepository;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.other.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.project.model.ProductDTO.convertToDto;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    //private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private final ProductService productService;
    @Autowired
    private final UserService userService;
    @Autowired
    private JwtService jwtUtil;

    //ok
    @GetMapping("/getALL")
    public ResponseEntity<ProductPage> getProducts(HttpServletRequest request, @RequestParam int page, @RequestParam int size) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtUtil.extractEmail(token);  // E-poçtu çıxarırıq

            // E-poçta görə məhsulları əldə edirik
            List<Product> products = productService.getProductByEmail(email, page, size).stream().toList();
            Integer i = productService.countProductByEmail(email);
            List<ProductDTO> checkProducts = new ArrayList<>();

            for (Product product : products) {
                ProductDTO productDTO = convertToDto(product);
                productDTO.setOrderofDay(productService.getProductOrderCount(product.getName(),email));
                productDTO.setCategory(product.getCategory().getName());
                checkProducts.add(productDTO);
            }
            ProductPage productPage = new ProductPage();
            productPage.setProducts(checkProducts);
            productPage.setCountProducts(i);
            return ResponseEntity.ok(productPage);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //ok
    @Transactional
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(HttpServletRequest request, @RequestBody Product product) {
       String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtUtil.extractEmail(token);

            // E-poçt vasitəsilə istifadəçini çıxar
            UserDetail user = userService.findByEmail(email);

            if (user != null) {
                product.setUser(user);
                product.getCategory().setUserDetail(product.getUser());
               if( productService.addProduct(product)==true){
                   return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
               }else{
                   return new ResponseEntity<>("Product already exists", HttpStatus.CONFLICT);
               }
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


//    //alindi
//    @GetMapping("/getAll")
//    public ResponseEntity<List<Product>> getAllProducts(HttpServletRequest request,@RequestParam int page, @RequestParam int size) {
//        try {
//            List<Product> products = productService.getProducts(page, size).stream().toList();
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error retrieving products: {}", e.getMessage(), e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    //alindi
//    @GetMapping("/get")
//    public ResponseEntity<List<Product>> getAllProducts(HttpServletRequest request) {
//        try {
//            List<Product> products = productService.getProducts();
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error retrieving all products: {}", e.getMessage(), e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    //alindi
    @DeleteMapping("/deleteBy")
    public ResponseEntity<String> deleteProduct(HttpServletRequest request, @RequestParam String product) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);  // E-poçtu çıxarırıq
                productService.deleteProduct(product, email);

                return new ResponseEntity<>("Data deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //logger.error("Error deleting product by id: {}", e.getMessage(), e);
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
    public ResponseEntity<ProductDTO> getProductById(HttpServletRequest request, @RequestParam String product) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);  //
                Product productById = productService.getProductById(product, email);
                ProductDTO productDTO = convertToDto(productById);
                productDTO.setOrderofDay(productService.getProductOrderCount(productById.getName(),email));

                return new ResponseEntity<>(productDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
           // logger.error("Error retrieving product by id: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //alindi__________________________________________________________________________________________________

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(HttpServletRequest request, @RequestBody Product product) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);
                Product updatedProduct = productService.updateProduct(product, email);
                ProductDTO productDTO = convertToDto(updatedProduct);
                productDTO.setOrderofDay(productService.getProductOrderCount(updatedProduct.getName(),email));
                return new ResponseEntity<>(productDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
           // logger.error("Error updating product: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //alindi_____________________________________________________________________________________________________
    @PutMapping("/cancel")
    public ResponseEntity<ProductDTO> updateProductWithCancellation(HttpServletRequest request, @RequestBody Product product) {

        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);
                Product updatedProduct = productService.updateProductWithCancel(product, email);
                ProductDTO productDTO = convertToDto(updatedProduct);
                productDTO.setOrderofDay(productService.getProductOrderCount(updatedProduct.getName(),email));
                return new ResponseEntity<>(productDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
           // logger.error("Error cancelling product update: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    //alindi______________________________________________________________________________________________________________
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(HttpServletRequest request, @RequestParam String name) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);
                List<Product> products = productService.getProductByName(name,email);
                List<ProductDTO> checkProducts = new ArrayList<>();
                if (products.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else{
                    for (Product product : products) {
                         checkProducts.add(convertToDto(product));
                    }
                }

                return new ResponseEntity<>(checkProducts, HttpStatus.OK);
            }
        } catch (Exception e) {
          //  logger.error("Error searching products: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //alindi
    @GetMapping("/category")
    public ResponseEntity<Long> findCategoryCount(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);
                List<Product> productByEmail = productService.getProductByEmail(email);
                if (!productByEmail.isEmpty() ) {
                    Long categoryCount = productService.getCategoryCount(email);
                    return new ResponseEntity<>(categoryCount, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getByCategory")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(HttpServletRequest request, @RequestParam String product) {

            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);  //
                List<Product> productById = productService.getProductByCategory(product, email);
                List<ProductDTO> checkProducts = new ArrayList<>();
                if (!productById.isEmpty()) {
                    for (Product product1 : productById) {
                        ProductDTO productDTO = convertToDto(product1);
                        productDTO.setOrderofDay(productService.getProductOrderCount(product1.getName(), email));
                        checkProducts.add(productDTO);
                    }

                    return new ResponseEntity<>(checkProducts, HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
    }
}


