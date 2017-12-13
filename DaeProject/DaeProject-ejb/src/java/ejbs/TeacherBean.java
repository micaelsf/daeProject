package ejbs;

import dtos.TeacherDTO;
import entities.TeacherProposal;
import entities.Teacher;
import entities.User;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class TeacherBean extends Bean<Teacher> {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String password, String name, String email, String office)
            throws EntityAlreadyExistsException {
        try {
            if (em.find(User.class, id) != null) {
                throw new EntityAlreadyExistsException("A user with that username already exists.");
            }
            em.persist(new Teacher(password, name, email, office));
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(String username, String name, String email, String office)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Teacher teacher = em.find(Teacher.class, username);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("There is no teacher with that username.");
            }
            teacher.setName(name);
            teacher.setEmail(email);
            teacher.setOffice(office);
            em.merge(teacher);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Collection<TeacherDTO> getAllTeachers() {
        try {
            return getAll(TeacherDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
   
    @Override
    protected Collection<Teacher> getAll() {
        return em.createNamedQuery("getAllTeachers").getResultList();
    }
 /*
    public Collection<TeacherDTO> getSujectTeachers(int subjectCode) throws EntityDoesNotExistsException {
        try {
            TeacherProposal teacherProposal = em.find(TeacherProposal.class, subjectCode);
            
            if (teacherProposal == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }
            
            return toDTOs(teacherProposal.getTeachers(), TeacherDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Collection<TeacherDTO> getTeachersNotInSubject(int subjectCode) throws EntityDoesNotExistsException {
        try {
            TeacherProposal teacherProposal = em.find(TeacherProposal.class, subjectCode);
            
            if (teacherProposal == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }
            
            List<Teacher> teachers = (List<Teacher>) em.createNamedQuery("getAllTeachers").getResultList();
            List<Teacher> teacher = teacherProposal.getTeachers();
            teachers.removeAll(teacher);
            
            return toDTOs(teachers, TeacherDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addSubjectTeacher(int subjectCode, String username) throws EntityDoesNotExistsException {
        try {
            TeacherProposal teacherProposal = em.find(TeacherProposal.class, subjectCode);
            if (teacherProposal == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }
            Teacher teacher = em.find(Teacher.class, username);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("There is no teacher with that username.");
            }
            teacherProposal.addTeacher(teacher);
            teacher.addSubject(teacherProposal);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void removeSubjectTeacher(int subjectCode, String username) throws EntityDoesNotExistsException {
        try {
            TeacherProposal teacherProposal = em.find(TeacherProposal.class, subjectCode);
            if (teacherProposal == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }
            Teacher teacher = em.find(Teacher.class, username);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("There is no teacher with that username.");
            }
            teacherProposal.removeTeacher(teacher);
            teacher.removeSubject(teacherProposal);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }    */
}
