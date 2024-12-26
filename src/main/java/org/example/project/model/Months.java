package org.example.project.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Months {
    JAN("01"),
    FEB("02") ,
     MAR("03"), 
     APR("04"), 
      MAY("05"),
      JUN("06"), 
       JUL("07"),  
      AUG("08"), 
       SEP("09"),
        OCT("10"), 
        NOV("11"), 
         DEC("12");
         private String month;
         Months (String month) {
            this.month = month;
        }
            public String getMonths() {
                return month;
            }
}
