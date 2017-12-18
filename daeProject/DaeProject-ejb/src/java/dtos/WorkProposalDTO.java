package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "WorkProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkProposalDTO implements Serializable {
    
    private int id;        
    private String title;    
    private String scientificAreas;
    private String status;


    public WorkProposalDTO() {
    }    
    
    public WorkProposalDTO(int id, String title, String scientificAreas, String status) {
        this.id = id;        
        this.title = title;        
        this.scientificAreas = scientificAreas;
        this.status = status;
    }
    
    public void reset() {
        setId(-1);
        setTitle(null);        
        setScientificAreas(null);
        setStatus(null);
    }    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(String scientificAreas) {
        this.scientificAreas = scientificAreas;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;            
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
