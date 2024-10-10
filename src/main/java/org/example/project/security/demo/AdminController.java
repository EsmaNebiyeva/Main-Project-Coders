//package org.example.project.security.demo;
//
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/admin")
//@PreAuthorize("hasRole('ADMIN')")
//public class AdminController {
//
//    @GetMapping("/get")
//   // @PreAuthorize("hasAuthority('')")
//    public String get() {
//        return "GET:: admin controller";
//    }
//    @PostMapping("/post")
//  //  @PreAuthorize("hasAuthority('admin:create')")
//
//    public String post() {
//        return "POST:: admin controller";
//    }
//    @PutMapping("/put")
//   // @PreAuthorize("hasAuthority('admin:update')")
//
//    public String put() {
//        return "PUT:: admin controller";
//    }
//    @DeleteMapping("/delete")
//    //@PreAuthorize("hasAuthority('admin:delete')")
//
//    public String delete() {
//        return "DELETE:: admin controller";
//    }
//}
