package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;
import org.example.project.model.EmailNotDTO;
import org.example.project.model.EmailNots;
import org.example.project.model.Months;
import org.example.project.model.MoreActivitiesEnum;
import org.example.project.model.MoreActivityDTO;
import org.example.project.model.OrderDTO;
import org.example.project.model.OrderPage;

import org.example.project.model.ProductDTO;
import org.example.project.repository.other.TablesRepository;
import org.example.project.security.auth.MailService;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.notifications.EmailNotService;
import org.example.project.service.notifications.MoreActivityService;
import org.example.project.service.other.OrderService;
import org.example.project.service.other.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.project.model.OrderDTO.converToDTO;
import static org.example.project.model.ProductDTO.convertToDto;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4444", "https://posive.huseyn.site/"})
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private final OrderService orderService;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final MailService mailService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private final TablesRepository tablesRepository;
      @Autowired
    private final EmailNotService emailNotService;
    @Autowired
    private final MoreActivityService moreActivityService;

    // Get order by ID
    // @GetMapping("/getById")
    // public ResponseEntity<OrderDTO> getOrder(HttpServletRequest request,
    // @RequestParam Long id) {
    // String authorizationHeader = request.getHeader("Authorization");

    // if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
    // {
    // String token = authorizationHeader.substring(7);
    // String email = jwtService.extractEmail(token);
    // List<Order> o = orderService.getOrderByNameSpecial(id,email);
    // if (o == null) {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }else{
    // OrderDTO orderDTO ;
    // for(Order order:o){
    // orderDTO = converToDTO(order,tablesRepository);
    // List<String> list = new ArrayList<>();
    // List<String> res = new ArrayList<>();
    // List<Product> list1 = order.getProductsSet().stream().toList();
    // Double total= 0.0;

    // for (Product product : list1) {
    // list.add(product.getName());
    // res.add(product.getReceiptNo());
    // total=total+product.getPrice()+product.getPrice()*product.getTax()-product.getDiscount()*product.getPrice();
    // }

    // orderDTO.setReceiptNumber(res);
    // /// orderDTO.setMenu(list);
    // orderDTO.setPrice(total);

    // }}return new ResponseEntity<>(orderDTO, HttpStatus.OK);}}

    // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }

    // Get all orders with pagination and date filter
    @GetMapping("/get")
    public ResponseEntity<OrderPage> getAllOrders(HttpServletRequest request, @RequestParam int page,
            @RequestParam int size, @RequestParam String date, @RequestParam String filter) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);

                List<Order> orderList = orderService.getOrders(email, page, size, date, filter).stream().toList();
                Integer i = orderService.getOrderCount(email, page, size, date, filter);
                List<OrderDTO> orderDTOs = new ArrayList<>();

                for (Order order : orderList) {
                    OrderDTO orderDTO = converToDTO(order, tablesRepository);
                    // List<String> list = new ArrayList<>();
                    List<String> res = new ArrayList<>();
                    List<ProductDTO> imageList = new ArrayList<>();

                    Double total = 0.0;
                    for (Product product : order.getProductsSet()) {
                        imageList.add(convertToDto(product));
                        res.add(product.getReceiptNo());
                        total = total + product.getPrice()
                                + (product.getPrice() * product.getTax() - product.getDiscount() * product.getPrice())
                                        * 0.01;

                    }
                    List<String> collect = order.getProductsSet().stream()
                            .collect(Collectors.groupingBy(e -> e.getReceiptNo(), Collectors.counting()))
                            .entrySet()
                            .stream()
                            .map(entry -> entry.getKey() + ": " + entry.getValue() + " ")
                            .collect(Collectors.toList());

                    orderDTO.setReceiptNumber(res);
                    orderDTO.setMenus(imageList);
                    orderDTO.setPrice(total);
                    orderDTO.setSize(collect);
                    orderDTOs.add(orderDTO);
                }
                Double all = orderService.getAll(email);
                List<String> cashiers = orderService.getCashiers(email);
                OrderPage orderPage = new OrderPage();
                orderPage.setOrders(orderDTOs);
                orderPage.setCashiers(cashiers);
                orderPage.setCountOrders(i);
                orderPage.setSales(all);
                return new ResponseEntity<>(orderPage, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting orders: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Add a new order
    @PostMapping("/add")
    public ResponseEntity<OrderDTO> addOrder(HttpServletRequest request, @RequestBody Order order) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            MoreActivityDTO activity = moreActivityService.getActivity(email);
            EmailNotDTO activity2 = emailNotService.getActivity(email);
            if (user != null) {
                order.setUser(user);
                Order b = orderService.addOrder(order);
                OrderDTO converToDTO2 = converToDTO(b, tablesRepository);

                List<String> list = new ArrayList<>();
                List<String> res = new ArrayList<>();

                for (Product product : order.getProductsSet()) {
                    list.add(product.getName());
                    res.add(product.getReceiptNo());

                }

                converToDTO2.setReceiptNumber(res);
                converToDTO2.setMenu(list);
                if (b != null) {
                    if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())||activity2.getNots().contains(EmailNots.TIPS.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                    mailService.sendOrdeString(email,"ADD OLUNDU"+order.getOrderId());
                    }
                    return new ResponseEntity<>(converToDTO2, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    // Delete an order
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrder(HttpServletRequest request, @RequestParam Long id) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                boolean deleted = orderService.deleteOrder(email, id);
                MoreActivityDTO activity = moreActivityService.getActivity(email);
                EmailNotDTO activity2 = emailNotService.getActivity(email);
                if (deleted) {
                    if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())||activity2.getNots().contains(EmailNots.TIPS.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                    mailService.sendOrdeString(email,"DELETE OLUNDU"+id);
                    }
                    return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Order not found", HttpStatus.ACCEPTED);
                }
            }
        } catch (Exception e) {
            logger.error("Error deleting order: {}", e.getMessage());
            return new ResponseEntity<>("Failed to delete order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Order not found", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/deleteALL")
    public ResponseEntity<String> deleteOrders(HttpServletRequest request, @RequestBody List<Long> list) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                MoreActivityDTO activity = moreActivityService.getActivity(email);
                EmailNotDTO activity2 = emailNotService.getActivity(email);
                boolean allDeleted = list.stream().allMatch(id -> orderService.deleteOrder(email, id));
                
                if (allDeleted) {
                    if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())||activity2.getNots().contains(EmailNots.TIPS.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                    mailService.sendOrdeString(email,"DELETE ALL EDILDI ve IDler:"+list);
                    }
                    return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Order not found", HttpStatus.ACCEPTED);
                }
            }
        } catch (Exception e) {
            logger.error("Error deleting order: {}", e.getMessage());
            return new ResponseEntity<>("Failed to delete order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Order not found", HttpStatus.UNAUTHORIZED);
    }

    // Get total income from orders
    @GetMapping("/income")
    public ResponseEntity<Double> getIncome(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                Double income = orderService.getTotalIncome(email);
                return new ResponseEntity<>(income, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting income: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/tip")
    public ResponseEntity<Double> getTips(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                Double income = orderService.getTotalTips(email);
                return new ResponseEntity<>(income, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting income: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Update an order
    @PutMapping("/update")
    public ResponseEntity<OrderDTO> updateOrder(HttpServletRequest request, @RequestBody Order order) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            MoreActivityDTO activity = moreActivityService.getActivity(email);
            EmailNotDTO activity2 = emailNotService.getActivity(email);
            Order updatedOrder = orderService.updateOrder(email, order);
            if (updatedOrder != null) {
                OrderDTO orderDTO = converToDTO(updatedOrder, tablesRepository);
                List<String> list = new ArrayList<>();
                List<String> res = new ArrayList<>();
                for (Product product : updatedOrder.getProductsSet()) {
                    list.add(product.getName());
                    res.add(product.getReceiptNo());
                }
                orderDTO.setReceiptNumber(res);
                orderDTO.setMenu(list);
                if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())||activity2.getNots().contains(EmailNots.TIPS.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                mailService.sendOrdeString(email,"UPDATE OLUNDU"+order.getOrderId());
                }
                return new ResponseEntity<>(orderDTO, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Cancel an order
    @PutMapping("/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(HttpServletRequest request, @RequestBody Order order) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                Order canceledOrder = orderService.updateOrderCancel(email, order);
                if (canceledOrder != null) {
                    OrderDTO orderDTO = converToDTO(canceledOrder, tablesRepository);
                    List<String> list = new ArrayList<>();
                    List<String> res = new ArrayList<>();
                    for (Product product : canceledOrder.getProductsSet()) {
                        list.add(product.getName());
                        res.add(product.getReceiptNo());
                    }
                    orderDTO.setReceiptNumber(res);
                    orderDTO.setMenu(list);
                    return new ResponseEntity<>(orderDTO, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            logger.error("Error canceling order: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/searchOrder")
    public ResponseEntity<List<OrderDTO>> searchProductsSpecial(HttpServletRequest request, @RequestParam Long name) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            List<Order> orders = orderService.getOrderByNameSpecial(name, email);
            List<OrderDTO> checkProducts = new ArrayList<>();
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                for (Order order : orders) {
                    OrderDTO orderDTO = converToDTO(order, tablesRepository);
                    List<String> res = new ArrayList<>();
                    List<ProductDTO> productList = new ArrayList<>();

                    Double total = 0.0;
                    for (Product product : order.getProductsSet()) {
                        productList.add(convertToDto(product));
                        res.add(product.getReceiptNo());
                        total = total + product.getPrice()
                                + (product.getPrice() * product.getTax() - product.getDiscount() * product.getPrice())
                                        * 0.01;

                    }
                    List<String> collect = order.getProductsSet().stream()
                            .collect(Collectors.groupingBy(e -> e.getReceiptNo(), Collectors.counting()))
                            .entrySet()
                            .stream()
                            .map(entry -> entry.getKey() + ": " + entry.getValue() + " ")
                            .collect(Collectors.toList());
                    orderDTO.setReceiptNumber(res);
                    orderDTO.setMenus(productList);
                    orderDTO.setPrice(total);
                    orderDTO.setSize(collect);

                    checkProducts.add(orderDTO);
                }
            }
            return new ResponseEntity<>(checkProducts, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("incomeByMonth")
    public ResponseEntity<IncomeDTO> getIncomeMonthly(HttpServletRequest request, @RequestParam List<String> months,
            @RequestParam String year) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                IncomeDTO incomeDTO=new IncomeDTO();
                if (months.size() != 1 && !months.isEmpty()) {
                    List<Double> values = new ArrayList<>();
                    int count=0;
                    Double income=0.0;
                    for (String month : months) {
                        String value = Months.valueOf(month.toUpperCase()).getMonths();
                        Double incomeByMonth = orderService.getIncomeByMonth(year, value, email);
                        values.add(incomeByMonth);
                        count++;
                        income=income+incomeByMonth;

                    }
                    List<String> years = orderService.getYears(email);
                    incomeDTO.setYears(years);
                    incomeDTO.setIncomes(values);
                   Double incomes= (income/count)+(income%count);
                    incomeDTO.setMonthlyIncome(incomes);
                    incomeDTO.setPurchase(productService.getProductSize(email));
                    incomeDTO.setProducts(productService.getProductCount());
                    return new ResponseEntity<>(incomeDTO, HttpStatus.OK);
                } else if (months.size() == 1) {
                    List<Double> incomeByDay = orderService.getIncomeByDay(year, months.get(0).toUpperCase(), email);
                    List<String> years = orderService.getYears(email);
                    incomeDTO.setIncomes(incomeByDay);
                    incomeDTO.setYears(years);
                    return new ResponseEntity<>(incomeDTO, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
