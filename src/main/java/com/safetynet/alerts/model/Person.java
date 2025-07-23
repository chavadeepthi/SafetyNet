package com.safetynet.alerts.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Person {
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String phone;
    public String email;
    public String zip;

    public Person(String firstName, String lastName, String address, String city,
                  String zip, String phone,  String email) {
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.city = city;
        this.phone = phone;
        this.zip = zip;
    }


    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }
    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getCity() {
        return city;
    }
    public String getZip() {
        return zip;
    }

}
