/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import util.Security;

@Entity
public class Proponent extends User implements Serializable {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    protected UserGroup group;

    public Proponent() {
    }

    public Proponent(String password, UserGroup.GROUP group, String name, String email) {
        super(password, group, name, email);
    }
}
