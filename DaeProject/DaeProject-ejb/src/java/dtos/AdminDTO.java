package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Teacher")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdminDTO extends UserDTO implements Serializable {
    
    public AdminDTO() {
    }    
    
    public AdminDTO(int id, String password, String name, String email) {
        super(id, password, name, email);
    }
    
    @Override
    public void reset() {
        super.reset();
    }    
}
