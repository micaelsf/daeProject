/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Instituition")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitutionDTO extends UserDTO implements Serializable{

    public InstitutionDTO() {
    }

    public InstitutionDTO(
            int id,
            String password,
            String name,
            String email, 
            String city, 
            String address
    ) {
        super(id, password, name, email, city, address);
    }

    @Override
    public void reset() {
        super.reset();
    }

}
