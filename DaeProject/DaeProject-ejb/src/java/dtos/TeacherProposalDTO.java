package dtos;

import entities.TeacherProposal.TeacherProposalType;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TeacherProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeacherProposalDTO extends WorkProposalDTO implements Serializable {
    
    private TeacherProposalType teacherProposalType;
    
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
            TeacherProposalType proposalType
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
                support
        );
        this.teacherProposalType = proposalType;
    }
    
    @Override
    public void reset() {
        super.reset();
        setTeacherProposalType(null);  
    } 
    
    public TeacherProposalType getTeacherProposalType() {
        return teacherProposalType;
    }

    public void setTeacherProposalType(TeacherProposalType teacherProposalType) {
        this.teacherProposalType = teacherProposalType;
    }
}
