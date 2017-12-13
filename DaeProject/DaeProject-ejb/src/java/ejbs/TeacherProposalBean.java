package ejbs;

import dtos.TeacherProposalDTO;
import entities.Course;
import entities.Student;
import entities.Subject;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;

@Stateless
public class TeacherProposalBean extends Bean<Subject> {

    public void create(int code, String name, int courseCode, int courseYear, String scholarYear)
        throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(Subject.class, code) != null) {
                throw new EntityAlreadyExistsException("A student with that username already exists.");
            }
            Course course = em.find(Course.class, courseCode);
            if (course == null) {
                throw new EntityDoesNotExistsException("There is no course with that code.");
            }
        
            Subject subject = new Subject(code, name, course, courseYear, scholarYear);
            em.persist(subject);
            course.addSubject(subject);
        
        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException e) {
            throw e;           
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Collection<SubjectDTO> getAllSubjects() {
        try {
            return getAll(SubjectDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @Override
    protected Collection<Subject> getAll() {
        return em.createNamedQuery("getAllSubjects").getResultList();
    }
       
    public void remove(int code) throws EntityDoesNotExistsException {
        try {
            Subject subject = em.find(Subject.class, code);
            if (subject == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }
          
            subject.getCourse().removeSubject(subject);
            
            for (Student student : subject.getStudents()) {
                student.removeSubject(subject);
            }
            
            em.remove(subject);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }    

    public Collection<SubjectDTO> getStudentSubjects(String username) throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            
            if (student == null) {
                throw new EntityDoesNotExistsException("Student does not exists.");
            }

            return toDTOs(student.getSubjects(), SubjectDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Collection<SubjectDTO> getCourseSubjects(int courseCode) throws EntityDoesNotExistsException {
        try {
            Course course = em.find(Course.class, courseCode);
            
            if (course == null) {
                throw new EntityDoesNotExistsException("Course does not exists.");
            }
            
            return toDTOs(course.getSubjects(), SubjectDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
