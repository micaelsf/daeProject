/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity@NamedQueries({
    @NamedQuery(name = "getAllInstituitions",
    query = "SELECT s FROM Instituition s ORDER BY s.name")
})

public class Instituition extends Proponent implements Serializable {

    @NotNull(message = "Instituition id must not be empty")
    
    public Instituition() {
    }

    public Instituition(String password, UserGroup.GROUP group, String name, String email) {
        super(password, group, name, email);
    }

    @Override
    public String toString() {
        return "Instituition{" +  
                ", name=" + name +
                ", email=" + email +"}";
    }
 
}