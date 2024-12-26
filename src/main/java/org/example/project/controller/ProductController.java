package org.example.project.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Product;
import org.example.project.model.EmailNotDTO;
import org.example.project.model.EmailNots;
import org.example.project.model.MoreActivitiesEnum;
import org.example.project.model.MoreActivityDTO;
import org.example.project.model.ProductDTO;
import org.example.project.model.ProductPage;
import org.example.project.security.auth.MailService;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.notifications.EmailNotService;
import org.example.project.service.notifications.MoreActivityService;
import org.example.project.service.other.ProductService;

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
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.vercel.app/"})
public class ProductController {

    //private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private final ProductService productService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final MailService mailService;
    @Autowired
    private final MoreActivityService moreActivityService;
    @Autowired
    private final EmailNotService emailNotService;
    @Autowired
    private JwtService jwtUtil;

    //ok
    @GetMapping("/getALL")
    public ResponseEntity<ProductPage> getProducts(HttpServletRequest request, @RequestParam int page, @RequestParam int size,@RequestParam String filter) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtUtil.extractEmail(token);  // E-poçtu çıxarırıq

            // E-poçta görə məhsulları əldə edirik
            List<Product> products = productService.getProductByEmail(email, page, size,filter).stream().toList();
            Integer i = productService.countProductByEmail(email);
            List<ProductDTO> checkProducts = new ArrayList<>();

            for (Product product : products) {
                
                ProductDTO productDTO = convertToDto(product);
                Double size2 = product.getSize();
                if(size2==null){
                    size2=0.0;
                }
                productDTO.setOrderofDay(size2.intValue());
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
    public ResponseEntity<String> addProduct(HttpServletRequest request, @RequestBody Product product) throws MessagingException {
       String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtUtil.extractEmail(token);
MoreActivityDTO activity = moreActivityService.getActivity(email);
EmailNotDTO activity2 = emailNotService.getActivity(email);
            // E-poçt vasitəsilə istifadəçini çıxar
            UserDetail user = userService.findByEmail(email);

            if (user != null) {
                product.setUser(user);
               if( productService.addProduct(product)==true){
                if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())||activity2.getNots().contains(EmailNots.TIPS.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
              mailService.sendProductString(email,"ADD OLUNDU"+product.getReceiptNo());
                
                }
                   return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
               }else{
                   return new ResponseEntity<>("Product already exists", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> deleteProduct(HttpServletRequest request, @RequestParam String product) throws MessagingException {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);  // E-poçtu çıxarırıq
                boolean b = productService.deleteProduct(product, email);
                MoreActivityDTO activity = moreActivityService.getActivity(email);
                EmailNotDTO activity2 = emailNotService.getActivity(email);
                if (b) {
                    if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())||activity2.getNots().contains(EmailNots.TIPS.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                    mailService.sendProductString(email,"DELETE OLUNDU"+product);
                    }
                    return new ResponseEntity<>("Data deleted successfully", HttpStatus.OK);

                } else {
                    return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
                }
            } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
                productDTO.setOrderofDay(productService.getProductOrderCountEmail(productById.getName(),email));

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
                MoreActivityDTO activity = moreActivityService.getActivity(email);
                EmailNotDTO activity2 = emailNotService.getActivity(email);
                Product updatedProduct = productService.updateProduct(product, email);
                ProductDTO productDTO = convertToDto(updatedProduct);
                productDTO.setOrderofDay(productService.getProductOrderCountEmail(updatedProduct.getName(),email));
                if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name()) ||activity2.getNots().contains(EmailNots.TIPS.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                mailService.sendProductString(email,"UPDATE OLUNDU"+productDTO.getReceiptNo());
                }
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
                productDTO.setOrderofDay(productService.getProductOrderCountEmail(updatedProduct.getName(),email));
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

            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // String token = authorizationHeader.substring(7);
                // String email = jwtUtil.extractEmail(token);
                List<Product> products = productService.getProductByName(name);
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


        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/searchUser")
    public ResponseEntity<List<ProductDTO>> searchProductsSpecial(HttpServletRequest request, @RequestParam String name) {

            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtUtil.extractEmail(token);
                List<Product> products = productService.getProductByNameSpecial(name,email);
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


        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //alindi
    @GetMapping("/category")
    public ResponseEntity<Long> findCategoryCount(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
              //  String token = authorizationHeader.substring(7);
               // String email = jwtUtil.extractEmail(token);
                List<Product> productByEmail = productService.getProductByEmail();
                if (!productByEmail.isEmpty() ) {
                    Long categoryCount = productService.getCategoryCount();
                    return new ResponseEntity<>(categoryCount, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/get")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(HttpServletRequest request) {

            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // String token = authorizationHeader.substring(7);
                // String email = jwtUtil.extractEmail(token);  //
                List<Product> productById = productService.getProductByCategory();
                List<ProductDTO> checkProducts = new ArrayList<>();
                if (!productById.isEmpty()) {
                    for (Product product1 : productById) {
                        ProductDTO productDTO = convertToDto(product1);
                        productDTO.setOrderofDay(productService.getProductOrderCount(product1.getName()));
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


