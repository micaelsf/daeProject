package dtos;

import java.io.Serializable;

public class TeacherProposalDTO extends WorkProposalDTO implements Serializable{

    public TeacherProposalDTO() {
    }

    public TeacherProposalDTO(int id, String title, String scientificAreas, String objectives, int status) {
        super(id, title, scientificAreas, objectives, status);
    }
}
