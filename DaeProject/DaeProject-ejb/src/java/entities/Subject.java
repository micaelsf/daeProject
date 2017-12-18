package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SUBJECTS", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME", "COURSE_CODE", "SCHOLAR_YEAR" }))
@NamedQuery(name = "getAllSubjects", query = "SELECT s FROM Subject s ORDER BY s.course.name, s.courseYear DESC, s.scholarYear, s.name")
public class Subject implements Serializable {

    @Id
    private int code;
    
    @Column(nullable = false)
    private String name;
    /*
    @ManyToOne
    @JoinColumn(name = "COURSE_CODE", nullable = false)
    private Course course;
    */
    @Column(name = "COURSE_YEAR")
    private int courseYear;
    
    @Column(name = "SCHOLAR_YEAR", nullable = false)
    private String scholarYear;
    
    @ManyToMany
    @JoinTable(name = "SUBJECT_STUDENT",
            joinColumns = @JoinColumn(name = "SUBJECT_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_STUDENTNUMBER", referencedColumnName = "STUDENTNUMBER"))
    private List<Student> students;
    
    @ManyToMany
    @JoinTable(name = "SUBJECT_TEACHER",
            joinColumns = @JoinColumn(name = "SUBJECT_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "TEACHER_NAME", referencedColumnName = "NAME"))
    private List<Teacher> teachers;

    public Subject() {
        students = new LinkedList<>();
        teachers = new LinkedList<>();
    }

    public Subject(int code, String name, Course course, int courseYear, String scholarYear) {
        this.code = code;
        this.name = name;
       // this.course = course;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
        students = new LinkedList<>();
        teachers = new LinkedList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
*/
    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public String getScholarYear() {
        return scholarYear;
    }

    public void setScholarYear(String scholarYear) {
        this.scholarYear = scholarYear;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }
    
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
    
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }
}
