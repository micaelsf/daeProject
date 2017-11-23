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

    @NotNull(message = "Instituition number must not be empty")
    private String instituitionNumber;
    
    public Instituition() {
    }

    public Instituition(String password, String name, String email, String instituitionNumber) {
        super(password, name, email);
        this.instituitionNumber = instituitionNumber;
    }

    public String getInstituitionNumber() {
        return instituitionNumber;
    }

    public void setInstituitionNumber(String instituitionNumber) {
        this.instituitionNumber = instituitionNumber;
    }

    @Override
    public String toString() {
        return "Instituition{" + 
                "instituitionNumber=" + instituitionNumber + 
                ", name=" + name +
                ", email=" + email +"}";
    }
 
}