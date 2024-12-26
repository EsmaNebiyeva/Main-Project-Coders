package org.example.project.entity.other;


import org.example.project.security.user.UserDetail;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Setting{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String theme;
private String language;
private String currency;
private String timeZone;
private String size;
private String icons;

 @OneToOne(fetch = FetchType.EAGER)
 //(fetch = FetchType.EAGER)
 @JoinColumn(name = "user_email",referencedColumnName = "email")

 private UserDetail user;

 @Override
 public String toString() {
  return "Setting{" +
          "size='" + size + '\'' +
          ", theme='" + theme + '\'' +
          ", language='" + language + '\'' +
          ", currency='" + currency + '\'' +
          ", timeZone='" + timeZone + '\'' +
          ", icons='" + icons + '\'' +
          '}';
 }

 public Setting(String theme, String language, String currency, String timeZone, String size, String icons){
this.theme=theme;
this.language=language;
this.currency=currency;
this.timeZone=timeZone;
this.size=size;
this.icons=icons;
}
}