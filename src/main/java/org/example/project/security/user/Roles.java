//package org.example.project.security.user;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Collections;
//import java.util.List;
//
//
//
//@RequiredArgsConstructor
//public enum Roles {
//  SUPER_ADMIN("SUPER ADMIN"),
//  ADMIN("ADMIN"),
//  MEMBER("MEMBER");
//
//  private String role;
//
//  // Constructor
//  Roles(String role) {
//    this.role = role;
//  }
//
//  // Getter method
//  public String getRole() {
//    return role;
//  }
//
//  public List<SimpleGrantedAuthority> getAuthorities() {
//    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + name()));
//  }
//}
////  public List<SimpleGrantedAuthority> getAuthorities() {
////    var authorities = getPermissions()
////            .stream()
////            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
////            .collect(Collectors.toList());
////    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
////    return authorities;
////  }
////}
