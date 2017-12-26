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
    
    public TeacherDTO(int id, String password, String name, String email, String office) {
        super(id, password, name, email);
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
