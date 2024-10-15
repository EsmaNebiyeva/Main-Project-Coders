package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Category;
import org.example.project.entity.other.Product;
import org.example.project.exception.OurException;

import org.example.project.repository.other.CategoryRepository;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;
import org.example.project.service.other.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.concat;



@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
private final CategoryRepository categoryRepository;
private final OrderRepository orderRepository;
private final UserRepository userRepository;
private final String imagePath="C:\\Users\\Asus\\IdeaProjects\\Project\\src\\main\\resources\\static";
@Transactional
    @Override
    public boolean addProduct(Product product) {

      if(product!=null) {
          Category byName = categoryRepository.findByName(product.getCategory().getName(), product.getUser().getEmail());
         if(byName!=null) {
             product.setCategory(byName);
             System.out.println("DATA elave olundu");
             productRepository.save(product);
             return true;
             //  }
         }

      }
          return false;

    }

    @Transactional
    @Override
    public boolean deleteProduct(String product,String email) {
        try {
            List<Product> byEmail = productRepository.findByEmail(email);
            if (!byEmail.isEmpty()) {
                Product byReceiptNo = productRepository.findByReceiptNoAndEmail(product,email);
                if (byReceiptNo != null) {
                    productRepository.delete(byReceiptNo);
                    System.out.println("Silindi");
                    return true;
                }
            } else {
                return false;
            }
        } catch (OurException e) {
            System.out.println("Our Exception");
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deleteProductById(Long id) throws OurException {

            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                return true;  // Silmə əməliyyatı uğurlu oldu
            } else {

                return false;
            }

    }

//    @Transactional
//    @Override
//    public boolean deleteProductById(Long id) {
//        try {
//            if (productRepository.existsById(id)) {
//                System.out.println("Delete");
//                productRepository.deleteById(id);
//            } else{
//                System.out.println("Tapilmadi");
//            }
//        } catch (OurException e) {
//            System.out.println("Our Exception");
//            System.out.println(e.getMessage());
//            return false;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//        return true;
//    }

    @Transactional
    @Override
    public Product updateProduct( Product product,String email) {
        try {
            List<Product> byEmail = productRepository.findByEmail(email);
            UserDetail byEmail1 = userRepository.getByEmail(email);
            product.setUser(byEmail1);
            if (!byEmail.isEmpty()) {
                Product ignored = productRepository.findByReceiptNoAndEmail(product.getReceiptNo(), email);
                if (ignored != null) {
                    Category byName = categoryRepository.findByName(product.getCategory().getName(), product.getUser().getEmail());
                    if (byName != null) {
                        ignored.setName(product.getName());
                        ignored.setPrice(product.getPrice());
                        ignored.setReceiptNo(product.getReceiptNo());
                        //ignored.get().setOrdersSet(product.getOrdersSet());
                        ignored.setUser(product.getUser());
                        ignored.setStock(product.getStock());
                        ignored.setTax(product.getTax());
                        ignored.setDiscount(product.getDiscount());
                        ignored.setCategory(byName);
                        ignored.setImageUrl(product.getImageUrl());
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
    public Product getProductById(String product,String email) {
        List<Product> byId = productRepository.findByEmail(email);
       if(!byId.isEmpty()) {
           Product byReceiptNo = productRepository.findByReceiptNo(product);
           if(byReceiptNo!=null) {
               return byReceiptNo;
           } else{
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
    public Product updateProductWithCancel( Product product,String email) {
        try {
            List<Product> byEmail = productRepository.findByEmail(email);
            if (!byEmail.isEmpty()) {
                Product byReceiptNo = productRepository.findByReceiptNo(product.getReceiptNo());
                if (byReceiptNo!=null) {
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
    public List<Product> getProductByName(String name,String email) {
        List<Product> byEmail = productRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            List<Product> products = productRepository.findByName(name,email);
            return products;
        }
        return null;
    }

    @Override
    public Boolean existsProductByReceiptNo(String receiptNo) {
        Product byReceiptNo = productRepository.findByReceiptNo(receiptNo);
        if (byReceiptNo!=null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long getCategoryCount(String email) {
        return productRepository.findDistinctCategoryCount(email);
    }

    @Override
    public Page<Product> getProductByEmail(String email,Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> byEmail = productRepository.findByEmail(email,pageable);
        return byEmail;
    }

    @Override
    public Integer getProductOrderCount(String name,String email) {
        LocalDate now=LocalDate.now();
        LocalDate today=now.minusDays(1);
        Integer countNameByOrdersSet = productRepository.findCountNameByOrdersSet(name,today,now,email);
        if(countNameByOrdersSet!=null) {
            return countNameByOrdersSet;
        }
        else{
        return 0;
        }
    }

    @Override
    public List<Product> getProductByCategory(String orderNo,String email) {
        List<Product> productsByCategory = productRepository.getProductsByCategory(orderNo,email);
        if(productsByCategory!=null) {
            return productsByCategory;
        }else{
            return null;
        }
    }

    @Override
    public List<Product> getProductByEmail(String email) {
        List<Product> productsByCategory = productRepository.findByEmail(email);
        if(!productsByCategory.isEmpty()) {
            return productsByCategory;
        }else{
            return null;
        }
    }

//    @Override
//    public Long countUserPermission(String email) {
//
//    }

    @Override
    public Integer countProductByEmail(String email) {
        Integer i = productRepository.countByEmail(email);
        if(i>0) {
            return i;
        }else{
            return 0;
        }
    }

}
