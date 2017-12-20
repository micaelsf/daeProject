package dtos;

import java.io.Serializable;

public class StageProposalDTO extends WorkProposalDTO implements Serializable{
    
    private String supervisor;
    
    public StageProposalDTO() {
    }    
    
    public StageProposalDTO(int id, String title, String scientificAreas, String objectives, int status, String supervisor) {
        super(id, title, scientificAreas, objectives, status);
        this.supervisor = supervisor;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
    
    
}
