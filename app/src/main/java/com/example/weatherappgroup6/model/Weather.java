package com.example.weatherappgroup6.model;

public class Weather {

   double temp;
   String icon;
   String day;
   public Weather(double temp, String icon, String day) {
      this.temp = temp;
      this.icon = icon;
      this.day = day;
   }

   public double getTemp() {
      return temp;
   }

   public void setTemp(double temp) {
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
