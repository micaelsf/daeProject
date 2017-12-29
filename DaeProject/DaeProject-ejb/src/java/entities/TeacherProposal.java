/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllTeacherProposals",
            query = "SELECT tp FROM TeacherProposal tp ORDER BY tp.title")
})
public class TeacherProposal extends WorkProposal {

    public static enum TeacherProposalType implements Serializable {
        Projeto, Dissertação;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "PROPOSAL_TYPE")
    private TeacherProposalType enumProposalType;
    
    @ManyToOne
    @JoinColumn(name = "TEACHER_USERNAME")
    private Teacher teacher;
        
    public TeacherProposal() {
    }
    
    public TeacherProposal(
            String title, 
            String scientificAreas, 
            String objectives, 
            String workResume, 
            //List<String> bibliography, 
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
            Teacher teacher
    ) {
        super(
                title, 
                scientificAreas, 
                objectives, 
                workResume, 
                //bibliography, 
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
        this.enumProposalType = proposalType;
        this.teacher = teacher;
        
        //this.teachers = new LinkedList<>();
    }
 /*   
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }
*/
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public TeacherProposalType getEnumProposalType() {
        return enumProposalType;
    }

    public void setEnumProposalType(TeacherProposalType enumProposalType) {
        this.enumProposalType = enumProposalType;
    }
    
    
}
