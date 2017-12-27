package dtos;

import entities.TeacherProposal.TeacherProposalType;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TeacherProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeacherProposalDTO extends WorkProposalDTO implements Serializable {
    
    private int teacherId;
    
    public TeacherProposalDTO() {
    }

    public TeacherProposalDTO(
            int id, 
            String title, 
            String scientificAreas, 
            String objectives,  
            String workResume,  
            String bibliography1,   
            String bibliography2,   
            String bibliography3,   
            String bibliography4,   
            String bibliography5,   
            String workPlan,  
            String workLocality,  
            String successRequirements,  
            float budget,  
            String support,  
            TeacherProposalType proposalType,
            int teacherId
    ) {
        super(
                id, 
                title, 
                scientificAreas, 
                objectives, 
                workResume, 
                bibliography1,
                bibliography2,
                bibliography3,
                bibliography4,
                bibliography5, 
                workPlan, 
                workLocality, 
                successRequirements, 
                budget, 
                support,
                "N/A",
                proposalType.toString()
        );
        this.teacherId = teacherId;
    }
    
    @Override
    public void reset() {
        super.reset();
    } 

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
    
}
