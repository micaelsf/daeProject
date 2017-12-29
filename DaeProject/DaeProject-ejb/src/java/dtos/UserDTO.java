/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserDTO implements Serializable{
    
    protected int id;
    protected String password;    
    protected String name;
    protected String email;
    protected String city;
    protected String address;
    protected String created_at;

    public UserDTO() {
    }    
    
    public UserDTO(int id, String password, String name, String email, String city, String address) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.city = city;
        this.address = address;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        this.created_at = dateFormat.format(cal.getTime());
    }
    
    public void reset() {
        setId(0);
        setPassword(null);
        setName(null);
        setEmail(null);
        setCity(null);
        setAddress(null);
    }        

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
