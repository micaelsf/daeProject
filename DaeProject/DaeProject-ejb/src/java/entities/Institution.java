/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author micae
 */
@Entity
public class Institution extends Proponent implements Serializable {

    public Institution() {
    }

    public Institution(Long id, String username, String password, String name, String email) {
        super(id, username, password, name, email);
    }
    
}
