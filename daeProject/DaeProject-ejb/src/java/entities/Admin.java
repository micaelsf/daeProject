/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Admin extends User implements Serializable {

    public Admin() {
    }

    public Admin(String password, UserGroup.GROUP group, String name, String email) {
        super(password, group, name, email);
    }
}