package dtos;

import entities.TeacherProposal.TEACHER_PROPOSAL_TYPE;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TeacherProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeacherProposalDTO extends WorkProposalDTO {
    
    private TEACHER_PROPOSAL_TYPE teacherProposalType;
    
    public TeacherProposalDTO() {
    }

    public TeacherProposalDTO(
            int id, 
            String title, 
            String scientificAreas, 
            String objectives, 
            int status,
            TEACHER_PROPOSAL_TYPE proposalType) {
        
        super(id, title, scientificAreas, objectives, status);
        this.teacherProposalType = proposalType;
    }

    public TEACHER_PROPOSAL_TYPE getTeacherProposalType() {
        return teacherProposalType;
    }

    public void setTeacherProposalType(TEACHER_PROPOSAL_TYPE teacherProposalType) {
        this.teacherProposalType = teacherProposalType;
    }
}
