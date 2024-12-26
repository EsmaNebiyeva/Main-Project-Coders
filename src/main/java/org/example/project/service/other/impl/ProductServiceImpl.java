package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Category;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;
import org.example.project.exception.OurException;

import org.example.project.repository.other.CategoryRepository;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;
import org.example.project.service.notifications.NotificationService;
import org.example.project.service.other.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    // private final String imagePath =
    // "C:\\Users\\Asus\\IdeaProjects\\Project\\src\\main\\resources\\static";

    @Transactional
    @Override
    public boolean addProduct(Product product) {

        if (product != null) {
            Category byName = categoryRepository.findByName(product.getCategory().getName());
            if (byName != null) {
               
                product.setCategory(byName);
                System.out.println("DATA elave olundu");
                productRepository.save(product);
                notificationService.sendNotification(product.getUser().getEmail(),"Product ADD", 
                "Product: "+product.getName()+", ReceiptNo:"+product.getReceiptNo()+" was added");
                return true;
                // }
            }
        }
        return false;
    }

    // public static List<Product> paginate(List<Product> list, int pageNumber, int pageSize) {
    //     int fromIndex = (pageNumber - 1) * pageSize;
    //     int toIndex = Math.min(fromIndex + pageSize, list.size());

    //     if (fromIndex >= list.size() || fromIndex < 0) {
    //         return List.of(); // Return empty list if page is out of bounds
    //     }

    //     return list.subList(fromIndex, toIndex);
    // }

    @Transactional
    @Override
    public boolean deleteProduct(String product, String email) {
        List<Product> byEmail = productRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            Product byReceiptNo = productRepository.findByReceiptNoAndEmail(product, email);
            if (byReceiptNo != null) {
                notificationService.sendNotification(email,"Product DELETE", 
                "Product: "+byReceiptNo.getName()+", ReceiptNo:"+byReceiptNo.getReceiptNo()+" was deleted");
                productRepository.delete(byReceiptNo);
                List<Order> byEmail1 = orderRepository.findAll();
                if (!byEmail1.isEmpty()) {
                    for (Order order : byEmail1) {
                        List<Product> productsSet = order.getProductsSet();
                        int count = 0;
                        // Product a = new Product();
                        Iterator<Product> iterator = productsSet.iterator();
                        while (iterator.hasNext()) {
                            Product p = iterator.next();
                            if (p.getReceiptNo().equals(byReceiptNo.getReceiptNo())) {
                                iterator.remove(); // Safe removal
                                count++;

                            }
                        }
                        double l = order.getTotalPrice()
                                - count * (byReceiptNo.getPrice() + byReceiptNo.getPrice() * byReceiptNo.getTax()
                                        - byReceiptNo.getPrice() * byReceiptNo.getDiscount());
                        if (l > 0) {
                            order.setTotalPrice(l);
                        } else {
                            if (order.getProductsSet().isEmpty()) {
                                
                                orderRepository.deleteByOrderId(order.getOrderId());
                            }
                            order.setTotalPrice(0.0);

                        }

                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deleteProductById(Long id) throws OurException {

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true; // Silmə əməliyyatı uğurlu oldu
        } else {

            return false;
        }

    }

    // @Transactional
    // @Override
    // public boolean deleteProductById(Long id) {
    // try {
    // if (productRepository.existsById(id)) {
    // System.out.println("Delete");
    // productRepository.deleteById(id);
    // } else{
    // System.out.println("Tapilmadi");
    // }
    // } catch (OurException e) {
    // System.out.println("Our Exception");
    // System.out.println(e.getMessage());
    // return false;
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // return false;
    // }
    // return true;
    // }

    @Transactional
    @Override
    public Product updateProduct(Product product, String email) {
        try {
            List<Product> byEmail = productRepository.findByEmail(email);
            UserDetail byEmail1 = userRepository.getByEmail(email);
            product.setUser(byEmail1);
            if (!byEmail.isEmpty()) {
                Product ignored = productRepository.findByReceiptNoAndEmail(product.getReceiptNo(), email);
                if (ignored != null) {
                    Category byName = categoryRepository.findByName(product.getCategory().getName());
                    if (byName != null) {
                        ignored.setName(product.getName());
                        ignored.setPrice(product.getPrice());
                        ignored.setReceiptNo(product.getReceiptNo());
                        // ignored.get().setOrdersSet(product.getOrdersSet());
                        ignored.setUser(product.getUser());
                        ignored.setStock(product.getStock());
                        ignored.setTax(product.getTax());
                        ignored.setDiscount(product.getDiscount());
                        ignored.setCategory(byName);
                        ignored.setImageUrl(product.getImageUrl());
                        productRepository.save(ignored);
                        notificationService.sendNotification(email,"Product UPDATE", 
                "Product: "+ignored.getName()+", ReceiptNo:"+ignored.getReceiptNo()+" was updated");
                        return ignored;
                    }
                }
            }

        } catch (OurException e) {
            System.out.println("Our Exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Product getProductById(String product, String email) {
        List<Product> byId = productRepository.findByEmail(email);
        if (!byId.isEmpty()) {
            Product byReceiptNo = productRepository.findByReceiptNo(product);
            if (byReceiptNo != null) {
                return byReceiptNo;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public Page<Product> getProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> allDistinct = productRepository.findAllDistinct(pageable);
        return allDistinct;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> all = productRepository.findAll();
        return all;
    }

    @Transactional
    @Override
    public Product updateProductWithCancel(Product product, String email) {
        try {
            List<Product> byEmail = productRepository.findByEmail(email);
            if (!byEmail.isEmpty()) {
                Product byReceiptNo = productRepository.findByReceiptNo(product.getReceiptNo());
                if (byReceiptNo != null) {
                    return byReceiptNo;
                }
            }
        } catch (OurException e) {
            System.out.println("Our Exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> getProductByName(String name) {
        // List<Product> byEmail = productRepository.findByEmail(email);
        // if (!byEmail.isEmpty()) {
        List<Product> products = productRepository.findByNameProduct(name);
        return products;
        // }
        // return null;
    }

    @Override
    public List<Product> getProductByNameSpecial(String name, String email) {
        List<Product> byEmail = productRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            List<Product> products = productRepository.findByName(name, email);
            return products;
        }
        return null;
    }

    @Override
    public Boolean existsProductByReceiptNo(String receiptNo) {
        Product byReceiptNo = productRepository.findByReceiptNo(receiptNo);
        if (byReceiptNo != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long getCategoryCount() {
        return productRepository.findDistinctCategoryCount();
    }

    @Override
    public Page<Product> getProductByEmail(String email, Integer page, Integer size,String filter) {
        Pageable pageable = PageRequest.of(page, size);
        if(filter.equalsIgnoreCase("ASC")){
        Page<Product> byEmail = productRepository.findByEmailASC(email, pageable);
        return byEmail;
        }else if(filter.equalsIgnoreCase("DESC")){
            Page<Product> byEmail = productRepository.findByEmailDESC(email, pageable);
        return byEmail;
        }
        return Page.empty();
    }

    @Override
    public Integer getProductOrderCount(String name) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = now.minusDays(1);
        Integer countNameByOrdersSet = productRepository.findCountNameByOrdersSet(name, today, now);
        if (countNameByOrdersSet != null) {
            return countNameByOrdersSet;
        } else {
            return 0;
        }
    }

    @Override
    public List<Product> getProductByCategory() {
        List<Product> productsByCategory = productRepository.getProductsByCategory();
        if (productsByCategory != null) {
            return productsByCategory;
        } else {
            return null;
        }
    }

    @Override
    public List<Product> getProductByEmail() {
        List<Product> productsByCategory = productRepository.findAll();
        if (!productsByCategory.isEmpty()) {
            return productsByCategory;
        } else {
            return null;
        }
    }

    // @Override
    // public Long countUserPermission(String email) {
    //
    // }

    @Override
    public Integer countProductByEmail(String email) {
        Integer i = productRepository.countByEmail(email);
        if (i > 0) {
            return i;
        } else {
            return 0;
        }
    }

    @Override
    public Integer getProductOrderCountEmail(String name, String email) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = now.minusDays(1);
        Integer countNameByOrdersSet = productRepository.findCountNameByOrdersSetEmail(name, today, now, email);
        if (countNameByOrdersSet != null) {
            return countNameByOrdersSet;
        } else {
            return 0;
        }
    }

    @Override
    public Integer getProductCount() {
        Integer productCount = productRepository.getProductCount();
        if(productCount>0){
        return productCount;
        }
        return 0;
    }

    @Override
    public Integer getProductSize(String email) {
        Integer productCount = productRepository.getProductSize(email);
        if(productCount==null){
        return 0;
        }
        return productCount;
    }

}
