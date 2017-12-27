/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity

@NamedQueries({
    @NamedQuery(name = "getAllAdmins",
            query = "SELECT a FROM Admin a ORDER BY a.name")
})
public class Admin extends User {

    public Admin() {
    }

    public Admin(String password, String name, String email) {
        super(password, GROUP.Administrator, name, email);
    }
}
