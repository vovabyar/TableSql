package com.example.table.model;

public class Person {

    public int id;
    public String userName;
    public String email;
    public boolean active;
 
   public Person(int id, String userName, String email, boolean active) {
       this.id = id;
       this.userName = userName;
       this.email = email;
       this.active = active;
   }
 
   public int getId() {
       return id;
   }
 
   public void setId(int id) {
       this.id = id;
   }
 
   public String getUserName() {
       return userName;
   }
 
   public void setUserName(String userName) {
       this.userName = userName;
   }
 
   public String getEmail() {
       return email;
   }
 
   public void setEmail(String email) {
       this.email = email;
   }

   public boolean isActive() {
       return active;
   }
 
   public void setActive(boolean active) {
       this.active = active;
   }

    @Override
    public String toString() {
        return id + " "
                + userName + " "
                + email + " "
                + active + ";";
    }

}