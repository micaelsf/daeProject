package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Admin")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdminDTO extends UserDTO implements Serializable {
    
    public AdminDTO() {
    }    
    
    public AdminDTO(int id, String password, String name, String email, String city, String address) {
        super(id, password, name, email, city, address);
    }
    
    @Override
    public void reset() {
        super.reset();
    }    
}
