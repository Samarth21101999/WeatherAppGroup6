package com.example.weatherappgroup6.model;

public class Weather {

   String temp;
   String icon;
   String day;
   public Weather(String temp, String icon, String day) {
      this.temp = String.valueOf(temp);
      this.icon = icon;
      this.day = day;
   }

   public String getTemp() {
      return temp;
   }

   public void setTemp(String temp) {
      this.temp = temp;
   }

   public String getIcon() {
      return icon;
   }

   public void setIcon(String icon) {
      this.icon = icon;
   }

   public String getDay() {
      return day;
   }

   public void setDay(String day) {
      this.day = day;
   }


}
