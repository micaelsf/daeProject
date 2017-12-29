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

@XmlRootElement(name = "Institution")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitutionDTO extends UserDTO implements Serializable{
    
    private String enterprise;
    
    public InstitutionDTO() {
    }

    public InstitutionDTO(
            String username,
            String password,
            String name,
            String email, 
            String city, 
            String address,
            String enterprise
    ) {
        super(username, password, name, email, city, address);
        this.enterprise = enterprise;
    }

    @Override
    public void reset() {
        super.reset();
        this.enterprise = null;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

}
