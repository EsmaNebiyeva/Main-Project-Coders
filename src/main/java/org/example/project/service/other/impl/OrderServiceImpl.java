package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.example.project.entity.other.Access;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;
import org.example.project.entity.other.Tablesss;
import org.example.project.exception.OurException;
import org.example.project.model.Days;
import org.example.project.model.Months;
import org.example.project.model.Times;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.repository.other.TablesRepository;
import org.example.project.service.notifications.NotificationService;
import org.example.project.service.other.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final TablesRepository tablesRepository;
    @Autowired
    private final NotificationService notificationService;
    // alindi
    // private List<Tablesss> byOrderNumber;

    // @Transactional
    // @Override
    // public Order addOrder(OrderDTO order) {
    //
    // // Check if order is valid
    // if (order == null || order.getProductsSet().isEmpty()) {
    // throw new OurException("Order or products set is invalid.");
    // }
    //
    // // Create Order object and set properties
    // Order orderDTO = new Order();
    // orderDTO.setProductsSet(order.getProductsSet());
    // orderDTO.setOrderDate(order.getOrderDate());
    // orderDTO.setCashier(order.getCashier());
    // orderDTO.setPaymentMethod(order.getPaymentMethod());
    //
    // // Calculate total price of products
    // Long totalPrice = order.getProductsSet().stream()
    // .mapToLong(Product::getPrice)
    // .sum();
    //
    // orderDTO.setTotalPrice(totalPrice);
    //
    // // Optionally: validate products before saving
    // // Example: If you want to check if all products are valid (e.g., not already
    // ordered)
    // for (Product product : order.getProductsSet()) {
    // List<Product> byReceiptNo =
    // productRepository.findByReceiptNo(product.getReceiptNo());
    // if (byReceiptNo.isEmpty()) {
    // throw new OurException("Product with receiptNo " + product.getReceiptNo() + "
    // not found.");
    // }
    // }
    //
    // // Save the order
    // orderRepository.save(orderDTO);
    // return orderDTO;
    // }
    @Transactional
    @Override
    public Order addOrder(Order order) {
        if (order == null) {
            System.out.println("Sifariş boşdur");
            return null;
        } else {
            List<Product> products = order.getProductsSet();
            System.out.println(products);
            if (products.isEmpty()) {
                System.out.println("Məhsul siyahısı boşdur");
                return null;
            } else {
                Double totalPrice = 0.0;
                List<Product> a = new ArrayList<>();
                for (Product product : products) {
                    Product existingProduct = productRepository.findByReceiptNoAndEmail(product.getReceiptNo());
                    // System.out.println(existingProduct.getCategory().getName());
                    Double newStock = 0.0;
                    try {
                        if (existingProduct == null) {
                            throw new Exception("data yoxdur");
                        } else {
                            Double stock = existingProduct.getStock();
                            Double size = existingProduct.getSize();
                            if (stock != 0) {
                                if (size != null) {
                                    existingProduct.setSize(size + 1);
                                }
                                newStock = stock - 1;
                                existingProduct.setStock(newStock);
                            } else {
                                return null;
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (existingProduct.getCategory().getName() != null) {
                        if(existingProduct.getDiscount()==null){
                             
                        totalPrice = totalPrice + existingProduct.getPrice()
                        + (existingProduct.getPrice() * existingProduct.getTax() * 0.01)
                        - (0 * existingProduct.getPrice() * 0.01);
                a.add(existingProduct);
                        }else{
                        totalPrice = totalPrice + existingProduct.getPrice()
                                + (existingProduct.getPrice() * existingProduct.getTax() * 0.01)
                                - (existingProduct.getDiscount() * existingProduct.getPrice() * 0.01);
                        a.add(existingProduct);
                    } }else {
                        return null;
                    }
                }
                List<String> tabss = new ArrayList<>();
                if (order.getTables() != null) {
                    for (String g : order.getTables()) {

                        Tablesss tab = tablesRepository.findByNumber(g);
                        if (tab != null) {// Check if the table exists
                            if (tab.getAccess().name().equals(Access.AVALIABLE.name())
                                    || tab.getAccess().name().equals(Access.SOON.name())) {
                                tab.setOrder(order.getOrderId());
                                tab.setAccess(Access.REZERV);
                                tab.setDate(LocalDateTime.now());
                                tablesRepository.save(tab);
                                tabss.add(g); // Save the updated tab
                            } else {
                                return null;
                            }
                        }
                    }
                }
                order.setTables(tabss);
                order.setTotalPrice(totalPrice);
                order.setProductsSet(a);
                Order save = orderRepository.save(order);
              notificationService.sendNotification(order.getUser().getEmail(),"Order ADD", 
                "Cashier:"+save.getUserName()+"OrderId: "+save.getOrderId()+" was added");
                return save;
            }
        }
    }
    // @Transactional
    // @Override
    // public Order addOrder(Order order) {
    // Product product = new Product();
    // if (order != null) {
    // List<Product> list = order.getProductsSet().stream().toList();
    //
    // if (!list.isEmpty() ) {
    // Long price = 0L;
    // for (Product p : list) {
    // price = price + p.getPrice();
    // System.out.println(price);
    // if (productRepository.findByReceiptNo(p.getReceiptNo()) == null) {
    // return null;
    // }
    // }
    // order.setTotalPrice(price);
    // //productRepository.save(product);
    // orderRepository.save(order);
    // System.out.println("DATA elave olundu");
    // return order;
    // }
    // } else {
    // System.out.println("melumat sehvdir");
    //
    // }
    // return null;
    // }

    @Override
    public Page<Order> getOrders(String email, Integer page, Integer size, String date, String filter) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            if (filter.equalsIgnoreCase("ASC")) {
                // System.out.println(orderRepository);
                Pageable pageable = PageRequest.of(page, size);
                if (date.equalsIgnoreCase(Times.THIS_WEEK.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();
                    LocalDateTime with = localDate.minusDays(7);
                    Page<Order> allBy = orderRepository.findAllByASCs(pageable, with, localDate, email);
                    return allBy;
                    // return orderRepository.findAll(pageable);
                } else if (date.equalsIgnoreCase(Times.THIS_MONTH.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusMonths(1);
                    return orderRepository.findAllByASCs(pageable, minus, localDate, email);
                } else if (date.equalsIgnoreCase(Times.THIS_YEAR.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();
                    LocalDateTime minus = localDate.minusYears(1);
                    ;
                    return orderRepository.findAllByASCs(pageable, minus, localDate, email);
                } else {
                    return orderRepository.findAllByASC(pageable, email);
                }
            } else if (filter.equalsIgnoreCase("DESC")) {
                Pageable pageable = PageRequest.of(page, size);
                if (date.equalsIgnoreCase(Times.THIS_WEEK.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();
                    LocalDateTime with = localDate.minusDays(7);
                    Page<Order> allBy = orderRepository.findAllByDESCs(pageable, with, localDate, email);
                    return allBy;
                    // return orderRepository.findAll(pageable);
                } else if (date.equalsIgnoreCase(Times.THIS_MONTH.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusMonths(1);
                    return orderRepository.findAllByDESCs(pageable, minus, localDate, email);
                } else if (date.equalsIgnoreCase(Times.THIS_YEAR.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusYears(1);
                    return orderRepository.findAllByDESCs(pageable, minus, localDate, email);
                } else {
                    return orderRepository.findAllByDESC(pageable, email);
                }
            }
        }

        return Page.empty();
    }

    @Override
    public Order getOrder(String email, Long orderId) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            if (orderId != null) {
                Order order = orderRepository.findByOrderIdEmail(orderId, email);
                // OrderDTO orderDTO=converToDTO(order);
                // List<String> list=new ArrayList<>();
                // List<String> res=new ArrayList<>();
                // for (Product product : order.getProductsSet()) {
                // list.add(product.getName());
                // res.add(product.getReceiptNo());
                // }
                // orderDTO.setReceiptNumber(res);
                // orderDTO.setMenu(list);
                return order;
            }
        }
        return null;
    }

    // alindi
    @Transactional
    @Override
    public boolean deleteOrder(String email, Long id) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            Optional<Order> byOrderId = orderRepository.findByOrderId(id, email);

            if (byOrderId.isPresent()) {
                List<Tablesss> byOrderNumber = tablesRepository.findByOrderNumber(byOrderId.get().getOrderId());
                for (Tablesss tab : byOrderNumber) {
                    tab.setAccess(Access.AVALIABLE);
                    tab.setOrder(null);
                }
                
                notificationService.sendNotification(email,"Order DELETE", 
                "Cashier:"+byOrderId.get().getUserName()+"OrderId: "+byOrderId.get().getOrderId()+" was deleted");
                orderRepository.deleteByOrderId(byOrderId.get().getOrderId());
                System.out.println("DATA silindi");
                
                return true;
            }

            else {
                return false;
            }
        }
        return false;
    }

    @Override
    public Double getTotalIncome(String email) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            LocalDate now = LocalDate.now();
            LocalDate today = now.minusMonths(1);
            List<Order> totalPrice = orderRepository.getTotalPrice(now, today, email);
            if (!totalPrice.isEmpty()) {
                Double total = 0.0;
                for (Order order : totalPrice) {
                    for (Product product : order.getProductsSet()) {
                        total = total + product.getPrice() + product.getPrice() * product.getTax()
                                - product.getDiscount() * product.getPrice();
                    }
                }
                return total;
            }

        }
        return null;
    }

    @Override
    public Order updateOrder(String email, Order order) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            Order byOrderId = orderRepository.findByOrderIdEmail(order.getOrderId(), email);

            if (byOrderId != null) {
                List<Product> list = order.getProductsSet().stream().toList();
                Double totalPrice = 0.0;
                boolean b = true;
                List<Product> productsSet = byOrderId.getProductsSet();
                List<Product> productsSet2 = order.getProductsSet();
                List<Product> difference = productsSet2.stream()
                        .filter(item -> !productsSet.contains(item))
                        .collect(Collectors.toList());
                for (Product p : difference) {
                    Product byReceiptNo = productRepository.findByReceiptNo(p.getReceiptNo());
                    if (byReceiptNo != null) {
                        byReceiptNo.setStock(byReceiptNo.getStock() - 1);
                    }
                }
                List<Product> ignore = productsSet.stream()
                        .filter(item -> !productsSet2.contains(item))
                        .collect(Collectors.toList());
                for (Product p : ignore) {
                    Product byReceiptNo = productRepository.findByReceiptNo(p.getReceiptNo());
                    if (byReceiptNo != null) {
                        byReceiptNo.setStock(byReceiptNo.getStock() + 1);
                    }
                }
                for (Product product : list) {
                    Product byReceiptNo = productRepository.findByReceiptNo(product.getReceiptNo());
                    if (byReceiptNo != null) {
                        totalPrice = totalPrice + byReceiptNo.getPrice()
                                + (byReceiptNo.getPrice() * byReceiptNo.getTax()
                                        - byReceiptNo.getPrice() * byReceiptNo.getDiscount()) * 0.01;
                    } else {
                        b = false;
                    }
                }
                if (b) {
                    order.setTotalPrice(totalPrice);
                    byOrderId.setUserName(order.getUserName());
                    byOrderId.setTotalPrice(order.getTotalPrice());
                    byOrderId.setProductsSet(order.getProductsSet());
                    byOrderId.setOrderDate(order.getOrderDate());
                    byOrderId.setPaymentMethod(order.getPaymentMethod());
                    notificationService.sendNotification(email,"Order UPDATE",
                "Cashier:"+order.getUserName()+"OrderId: "+order.getOrderId()+" was updated");
                    return byOrderId;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    @Override
    public Integer getOrderCount(String email, Integer page, Integer size, String date, String filter) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            if (filter.equalsIgnoreCase("ASC")) {
                // System.out.println(orderRepository);

                if (date.equalsIgnoreCase(Times.THIS_WEEK.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusDays(1);
                    Integer allBy = orderRepository.findAllByASCsCount(minus, localDate, email);
                    return allBy;
                    // return orderRepository.findAll(pageable);
                } else if (date.equalsIgnoreCase(Times.THIS_MONTH.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusMonths(1);
                    return orderRepository.findAllByASCsCount(minus, localDate, email);
                } else if (date.equalsIgnoreCase(Times.THIS_YEAR.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusYears(1);
                    return orderRepository.findAllByASCsCount(minus, localDate, email);
                } else {
                    return orderRepository.findAllByASCCount(email);
                }
            } else if (filter.equalsIgnoreCase("DESC")) {

                if (date.equalsIgnoreCase(Times.THIS_WEEK.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusDays(1);
                    Integer allBy = orderRepository.findAllByDESCsCount(minus, localDate, email);
                    return allBy;
                    // return orderRepository.findAll(pageable);
                } else if (date.equalsIgnoreCase(Times.THIS_MONTH.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusMonths(1);
                    return orderRepository.findAllByDESCsCount(minus, localDate, email);
                } else if (date.equalsIgnoreCase(Times.THIS_YEAR.getTimes())) {
                    LocalDateTime localDate = LocalDateTime.now();

                    LocalDateTime minus = localDate.minusYears(1);
                    return orderRepository.findAllByDESCsCount(minus, localDate, email);
                } else {
                    return orderRepository.findAllByDESCCount(email);
                }
            }
        }
        return 0;
    }

    @Override
    public Order updateOrderCancel(String email, Order order) {
        try {
            List<Order> byEmail = orderRepository.findByEmail(email);
            if (!byEmail.isEmpty()) {
                Order byOrderId = orderRepository.findByOrderIdEmail(order.getOrderId(), email);
                if (byOrderId != null) {
                    return byOrderId;
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
    public Double getTotalTips(String email) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            LocalDate now = LocalDate.now();
            LocalDate today = now.minusMonths(1);
            List<Order> totalPrice = orderRepository.getTotalPrice(now, today, email);
            if (!totalPrice.isEmpty()) {
                Double total = 0.0;
                for (Order order : totalPrice) {
                    for (Product product : order.getProductsSet()) {
                        total = total + product.getPrice() * product.getTax();
                    }
                }
                return total;
            }

        }
        return null;
    }

    @Override
    public Double getAll(String email) {
        if (email != null) {
            List<Order> orders = orderRepository.findOrders(email);
            if (!orders.isEmpty()) {
                Double sale = 0.0;
                for (Order a : orders) {
                    List<Product> productsSet = a.getProductsSet();
                    for (Product p : productsSet) {
                        sale = sale + p.getPrice();
                    }
                }
                return sale;
            }
        }
        return null;
    }

    @Override
    public List<String> getCashiers(String email) {
        if (email != null) {
            List<String> byDistinctCashier = orderRepository.findByDistinctCashier(email);
            if (!byDistinctCashier.isEmpty()) {
                return byDistinctCashier;
            }
        }
        return List.of();
    }

    @Override
    public List<Order> getOrderByNameSpecial(Long id, String email) {
        String orderId = String.valueOf(id);
        List<Order> byOrderIdAndUser = orderRepository.findByOrderIdAndUser(orderId, email);
        if (!byOrderIdAndUser.isEmpty()) {
            return byOrderIdAndUser;
        }
        return List.of();
    }

    @Override
    public Double getIncomeByMonth(String year, String month, String email) {

        Optional<Double> incomeByMonth = orderRepository.getIncomeByMonth(year, month, email);
        if (incomeByMonth.isPresent()) {
            return incomeByMonth.get();
        }
        return 0.0;
    }

    @Override
    public List<Double> getIncomeByDay(String year, String month, String email) {
        String months = Months.valueOf(month.toUpperCase()).getMonths();
        Integer day = Days.valueOf(month).getDays();
        List<Double> incomeByDayMap = new ArrayList<>();
        if (month.toUpperCase().equals("FEBRUARY")) {
            int yearString = Integer.parseInt(year); // String-i int-ə çeviririk

            // Leap year yoxlaması
            boolean isLeap = (yearString % 4 == 0 && yearString % 100 != 0) || (yearString % 400 == 0);
            if (isLeap) {
                day = 29;
            }
        }
        for (int i = 1; i <= day; i++) {
            String days;
            if (i < 10) {
                days = "0" + String.valueOf(i);
            } else {
                days = String.valueOf(i);
            }
            Optional<Double> incomeByDay = orderRepository.getIncomeByDay(year, months, days, email);
            if (incomeByDay.isPresent()) {
                incomeByDayMap.add(incomeByDay.get());
            } else {
                incomeByDayMap.add(0.0);
            }
        }
        return incomeByDayMap;
    }

    @Override
    public List<String> getYears(String email) {
        List<String> distinctYears = orderRepository.getDistinctYears(email);
        return distinctYears;
    }

    @Override
    public Double getIncomeByYear(String year, String email) {
        Optional<Double> incomeByYear = orderRepository.getIncomeByYear(year, email);
        if (incomeByYear.isPresent()) {
            Double income = (incomeByYear.get() / 12) + (incomeByYear.get() % 12);
            return income;
        }
        return 0.0;
    }

    @Override
    public List<Order> findByDate(LocalDateTime now) {
        String month = now.getMonth().name();
        String year = String.valueOf(now.getYear());
        String day =String.valueOf(now.getDayOfMonth());
        List<Order> byDate = orderRepository.getByDate(year,month,day);
        if(!byDate.isEmpty()){
            return byDate;
        }
        return List.of();
    }

}
