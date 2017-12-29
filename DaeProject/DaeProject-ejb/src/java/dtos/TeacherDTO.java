package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Teacher")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeacherDTO extends UserDTO implements Serializable {
    private String office;
    
    public TeacherDTO() {
    }    
    
    public TeacherDTO(
            String username, 
            String password, 
            String name, 
            String email, 
            String city, 
            String address, 
            String office
    ) {
        super(username, password, name, email, city, address);
        this.office = office;
    }
    
    @Override
    public void reset() {
        super.reset();
    }    

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

}
