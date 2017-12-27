package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Course")
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseDTO implements Serializable{

    private int id;
    private String name;
    
    public CourseDTO(){
    }
    
    public CourseDTO(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public void reset(){
        this.id = 0;
        this.name = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
