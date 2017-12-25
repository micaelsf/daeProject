package dtos;

import entities.TeacherProposal.TeacherProposalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TeacherProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeacherProposalDTO extends WorkProposalDTO {
    
    private TeacherProposalType teacherProposalType;
    
    public TeacherProposalDTO() {
    }

    public TeacherProposalDTO(
            int id, 
            String title, 
            String scientificAreas, 
            String objectives, 
            int status,
            TeacherProposalType proposalType) {
        
        super(id, title, scientificAreas, objectives, status);
        this.teacherProposalType = proposalType;
    }

    public TeacherProposalType getTeacherProposalType() {
        return teacherProposalType;
    }

    public void setTeacherProposalType(TeacherProposalType teacherProposalType) {
        this.teacherProposalType = teacherProposalType;
    }
}
