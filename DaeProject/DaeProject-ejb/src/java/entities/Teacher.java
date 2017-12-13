package entities;

import entities.UserGroup.GROUP;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "getAllTeachers", query = "SELECT t FROM Teacher t ORDER BY t.name")
public class Teacher extends User {

    private String office;
    
    @ManyToMany(mappedBy = "teachers")
    private List<TeacherProposal> teacherProposals;

    protected Teacher() {
        teacherProposals = new LinkedList<>();
    }

    public Teacher(String password, String name, String email, String office) {
        super(password, name, email);
        this.office = office;
        teacherProposals = new LinkedList<>();
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
    
    public List<TeacherProposal> getSubjects() {
        return teacherProposals;
    }

    public void setSubjects(List<TeacherProposal> teacherProposals) {
        this.teacherProposals = teacherProposals;
    }

    public void addSubject(TeacherProposal teacherProposal) {
        teacherProposals.add(teacherProposal);
    }    
    
    public void removeSubject(TeacherProposal teacherProposal) {
        teacherProposals.remove(teacherProposal);
    }    
    
}
