package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENTS")
@NamedQueries({
    @NamedQuery(name = "getDocumentsOfStudent", query = "SELECT doc FROM Document doc WHERE doc.student.username = :username")
})
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String filepath;

    private String desiredName;
    
    private String mimeType;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Student student;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    private PublicProof publicProof;
    
    public Document() {
        
    }

    public Document(String filepath, String desiredName, String mimeType) {
        this.filepath = filepath;
        this.desiredName = desiredName;
        this.mimeType = mimeType;
        this.student = null;
        this.publicProof = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getDesiredName() {
        return desiredName;
    }

    public void setDesiredName(String desiredName) {
        this.desiredName = desiredName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public PublicProof getPublicProof() {
        return publicProof;
    }

    public void setPublicProof(PublicProof publicProof) {
        this.publicProof = publicProof;
    }
    
}
