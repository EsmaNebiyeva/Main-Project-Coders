package org.example.project.controller;



import org.example.project.entity.other.Product;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class GreetingController {


  @MessageMapping("/add")
  @SendTo("/api/notification/product")
  public String greeting(Product product) throws Exception {
    Thread.sleep(1000);
    String productString=product.getName()+"\n"+product.getDescription();
    System.out.println("NOTIFICASTIOIJHGFDFGHJKJHGFD \n"+productString);
    return productString;
  }

}