//package org.example.project.security.auth;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.example.project.entity.other.Product;
//import org.example.project.model.ProductDTO;
//import org.example.project.repository.other.OrderRepository;
//import org.example.project.security.config.JwtService;
//import org.example.project.security.user.UserDetail;
//import org.example.project.security.user.UserService;
//import org.example.project.service.other.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.example.project.model.ProductDTO.convertToDto;
//
//@RestController
//@RequestMapping("/api/products")
//@RequiredArgsConstructor()
//public class AuthCheck {
//
//    @Autowired
//    private  final ProductService productService;
//@Autowired
//   private final UserService userService;
//@Autowired
//private final OrderRepository orderRepository;
//    @Autowired
//    private JwtService jwtUtil;
//
//    @GetMapping("/getCheck")
//    public ResponseEntity<List<ProductDTO>> getProducts(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String token = authorizationHeader.substring(7);
//            String email = jwtUtil.extractEmail(token);  // E-poçtu çıxarırıq
//
//            // E-poçta görə məhsulları əldə edirik
//            List<Product> products = productService.getProductByEmail(email);
//            List<ProductDTO> checkProducts = new ArrayList<>();
//
//            for (Product product : products) {
//                ProductDTO productDTO = convertToDto(product);
//                productDTO.setOrderofDay(productService.getProductOrderCount(product.getName()));
//                productDTO.setCategory(product.getCategory().getName());
//                checkProducts.add(productDTO);
//            }
//
//            return ResponseEntity.ok(checkProducts);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//    @PostMapping("/add")
//    public ResponseEntity<String> addProduct(HttpServletRequest request, @RequestBody Product product) {
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String token = authorizationHeader.substring(7);
//            String email = jwtUtil.extractEmail(token);
//
//            // E-poçt vasitəsilə istifadəçini çıxar
//            UserDetail user = userService.findByEmail(email);
//
//            if (user != null) {
//                product.setUser(user);
//                productService.addProduct(product);
//                return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
//            } else {
//                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//
//}
