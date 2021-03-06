/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AdminDTO;
import dtos.CourseDTO;
import dtos.DocumentDTO;
import dtos.InstitutionDTO;
import dtos.InstitutionProposalDTO;
import dtos.PublicProofDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import dtos.TeacherProposalDTO;
import dtos.WorkProposalDTO;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import utils.URILookup;

@ManagedBean
@SessionScoped
public class AdministratorManager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    private CourseDTO newCourse;
    private CourseDTO currentCourse;

    private AdminDTO newAdmin;
    private AdminDTO currentAdmin;

    private StudentDTO newStudent;
    private StudentDTO currentStudent;

    private InstitutionDTO newInstitution;
    private InstitutionDTO currentInstitution;

    private TeacherDTO newTeacher;
    private TeacherDTO currentTeacher;

    private WorkProposalDTO newProposal;
    private WorkProposalDTO currentProposal;

    private PublicProofDTO newPublicProof;
    private PublicProofDTO currentPublicProof;

    @ManagedProperty(value = "#{uploadManager}")
    private UploadManager uploadManager;

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    private DocumentDTO document;

    private UIComponent component;
    private Client client;

    private int selectOption;
    
    private String searchCourse = "";
    private String searchAdmin = "";
    private String searchStudent = "";
    private String searchTeacher = "";
    private String searchInstitution = "";
    private String searchProposal = "";
    private String searchPublicProof = "";

    private HttpAuthenticationFeature feature;

    public AdministratorManager() {
        newCourse = new CourseDTO();
        newAdmin = new AdminDTO();
        newStudent = new StudentDTO();
        newInstitution = new InstitutionDTO();
        newTeacher = new TeacherDTO();
        newProposal = new WorkProposalDTO();
        newPublicProof = new PublicProofDTO();
        client = ClientBuilder.newClient();
    }

    @PostConstruct
    public void initClient() {
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
    }

    /////////////// COURSES /////////////////
    public String createCourseREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/courses/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newCourse));
            newCourse.reset();

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<CourseDTO> getAllCoursesREST() {
        List<CourseDTO> returnedCourses;
        try {
            returnedCourses = client.target(URILookup.getBaseAPI())
                    .path("/courses/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<CourseDTO>>() {
                    });

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedCourses;
    }

    public String updateCourseREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/courses/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentCourse));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String removeCourseREST(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());

            client.target(URILookup.getBaseAPI())
                    .path("/courses/removeREST")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<CourseDTO> clickSearchCourse() {
        if (this.searchCourse == null || this.searchCourse.trim().length() == 0) {
            return getAllCoursesREST();
        }

        return searchCourseByNameREST();
    }

    public List<CourseDTO> searchCourseByNameREST() {
        List<CourseDTO> courseList = new LinkedList<>();
        try {

            CourseDTO course = client.target(URILookup.getBaseAPI())
                    .path("/courses/searchByName")
                    .path(this.searchCourse + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<CourseDTO>() {
                    });
            courseList.add(course);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return courseList;
    }

    /////////////// ADMINS /////////////////
    public String createAdmin() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/admins/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newAdmin));
            newAdmin.reset();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updateAdminREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/admins/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentAdmin));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<AdminDTO> getAllAdminsREST() {
        List<AdminDTO> returnedAdmins;
        try {
            returnedAdmins = client.target(URILookup.getBaseAPI())
                    .path("/admins/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<AdminDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedAdmins;
    }

    public String removeAdmin(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("username");
            String username = param.getValue().toString();

            client.target(URILookup.getBaseAPI())
                    .path("/admins/removeREST")
                    .path(username + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<AdminDTO> clickSearchAdmin() {
        if (this.searchAdmin == null || this.searchAdmin.trim().length() == 0) {
            return getAllAdminsREST();
        }

        return searchAdminByNameREST();
    }

    public List<AdminDTO> searchAdminByNameREST() {
        List<AdminDTO> list = new LinkedList<>();
        try {

            AdminDTO resource = client.target(URILookup.getBaseAPI())
                    .path("/admins/searchByName")
                    .path(this.searchAdmin + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<AdminDTO>() {
                    });
            list.add(resource);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return list;
    }

    /////////////// STUDENTS /////////////////
    public String createStudent() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/students/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newStudent));
            newStudent.reset();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updateStudentREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/students/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentStudent));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<StudentDTO> getAllStudentsREST() {
        List<StudentDTO> returnedStudents;
        try {
            returnedStudents = client.target(URILookup.getBaseAPI())
                    .path("/students/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<StudentDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedStudents;
    }

    public List<StudentDTO> getAllStudentsCourseREST() {
        List<StudentDTO> returnedStudents;
        try {
            returnedStudents = client.target(URILookup.getBaseAPI())
                    .path("/students/allStudentsFromCourse")
                    .path(currentCourse.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<StudentDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedStudents;
    }

    public String removeStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("username");
            String username = param.getValue().toString();

            client.target(URILookup.getBaseAPI())
                    .path("/students/removeREST")
                    .path(username + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<StudentDTO> clickSearchStudent() {
        if (this.searchStudent == null || this.searchStudent.trim().length() == 0) {
            return getAllStudentsREST();
        }

        return searchStudentByNameREST();
    }

    public List<StudentDTO> searchStudentByNameREST() {
        List<StudentDTO> list = new LinkedList<>();
        try {

            StudentDTO resource = client.target(URILookup.getBaseAPI())
                    .path("/students/searchByName")
                    .path(this.searchStudent + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<StudentDTO>() {
                    });
            list.add(resource);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return list;
    }

    //Institution
    public String createInstitution() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/institutions/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newInstitution)
                    );

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updateInstitutionREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/institutions/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentInstitution));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<InstitutionDTO> getAllInstitutionsREST() {
        List<InstitutionDTO> returnedInstitutions;
        try {
            returnedInstitutions = client.target(URILookup.getBaseAPI())
                    .path("/institutions/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<InstitutionDTO>>() {
                    });
            System.out.println(returnedInstitutions);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedInstitutions;
    }

    public String removeInstitution(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("username");
            String username = param.getValue().toString();

            client.target(URILookup.getBaseAPI())
                    .path("/institutions/removeREST")
                    .path(username + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<InstitutionDTO> clickSearchInstitution() {
        if (this.searchInstitution == null || this.searchInstitution.trim().length() == 0) {
            return getAllInstitutionsREST();
        }

        return searchInstitutionByNameREST();
    }

    public List<InstitutionDTO> searchInstitutionByNameREST() {
        List<InstitutionDTO> list = new LinkedList<>();
        try {

            InstitutionDTO resource = client.target(URILookup.getBaseAPI())
                    .path("/institutions/searchByName")
                    .path(this.searchInstitution + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<InstitutionDTO>() {
                    });
            list.add(resource);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return list;
    }

    /////////////// TEACHERS //////////////////
    public String createTeacher() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/teachers/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newTeacher)
                    );

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updateTeacherREST() {
        try {
            // client.target(baseUri)
            client.target(URILookup.getBaseAPI())
                    .path("/teachers/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentTeacher));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String removeTeacher(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("username");
            String username = param.getValue().toString();

            client.target(URILookup.getBaseAPI())
                    .path("/teachers/removeREST")
                    .path(username + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (NumberFormatException e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<TeacherDTO> getAllTeachersREST() {
        List<TeacherDTO> returnedTeachers;
        try {
            returnedTeachers = client.target(URILookup.getBaseAPI())
                    .path("/teachers/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TeacherDTO>>() {
                    });
            System.out.println(returnedTeachers);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedTeachers;
    }

    public List<TeacherDTO> clickSearchTeacher() {
        if (this.searchTeacher == null || this.searchTeacher.trim().length() == 0) {
            return getAllTeachersREST();
        }

        return searchTeacherByNameREST();
    }

    public List<TeacherDTO> searchTeacherByNameREST() {
        List<TeacherDTO> list = new LinkedList<>();
        try {

            TeacherDTO resource = client.target(URILookup.getBaseAPI())
                    .path("/teachers/searchByName")
                    .path(this.searchTeacher + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<TeacherDTO>() {
                    });
            list.add(resource);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return list;
    }

    /////////////// PROPOSAL //////////////////
    public List<WorkProposalDTO> getAllWorkProposalsREST() {
        List<WorkProposalDTO> returnedProposals;
        try {
            returnedProposals = client.target(URILookup.getBaseAPI())
                    .path("/proposals/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<WorkProposalDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedProposals;
    }

    public String updateProposalStatusREST() {
        try {
            String option = null;
            if (selectOption == 1) {
                option = "Aceite";
            }
            if (selectOption == 2) {
                option = "NãoAceite";
            }

            client.target(URILookup.getBaseAPI())
                    .path("/proposals/updateProposalStatusREST")
                    .path(currentProposal.getId() + "")
                    .path(currentProposal.getRejectReason() + "")
                    .path(currentProposal.getComments() + "")
                    .path(option)
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<InstitutionProposalDTO> getProposalsFromInstitutionREST() {
        List<InstitutionProposalDTO> returnedProposals;
        try {
            returnedProposals = client.target(URILookup.getBaseAPI())
                    .path("/institutionProposals/all/institution")
                    .path(currentInstitution.getUsername() + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<InstitutionProposalDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedProposals;
    }

    public List<TeacherProposalDTO> getProposalsFromTeacherREST() {
        List<TeacherProposalDTO> returnedProposals;
        try {
            returnedProposals = client.target(URILookup.getBaseAPI())
                    .path("/teacherProposals/all/teacher")
                    .path(currentTeacher.getUsername() + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TeacherProposalDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedProposals;
    }

    public List<WorkProposalDTO> clickSearchProposal() {
        if (this.searchProposal == null || this.searchProposal.trim().length() == 0) {
            return getAllWorkProposalsREST();
        }

        return searchProposalByNameREST();
    }

    public List<WorkProposalDTO> searchProposalByNameREST() {
        List<WorkProposalDTO> list = new LinkedList<>();
        try {

            WorkProposalDTO resource = client.target(URILookup.getBaseAPI())
                    .path("/proposals/searchByTitle")
                    .path(this.searchProposal + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<WorkProposalDTO>() {
                    });
            list.add(resource);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return list;
    }

    public List<StudentDTO> getAllStudentInWorkProposalREST() {
        List<StudentDTO> returnedStudents;
        System.out.println("AdminManager getAllStudentInWorkProposalREST");
        try {
            returnedStudents = client.target(URILookup.getBaseAPI())
                    .path("/proposals/allStudentInProposalRest")
                    .path(currentProposal.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<StudentDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedStudents;
    }

    public String workProposalAcceptApplianceRest(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("username");
            String username = param.getValue().toString();
            System.out.println("workProposalAcceptApplianceRest param received: " + username);
            
            client.target(URILookup.getBaseAPI())
                    .path("/proposals/workProposalAcceptApplianceRest")
                    .path(username + "")
                    .path(currentProposal.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "show?faces-redirect=true";
    }

    public String isStudentAcceptedRest(String username) {
        System.out.println("AdminManager isStudentAcceptedRest");
        try {
           /* UIParameter param = (UIParameter) event.getComponent().findComponent("username");
            String username = param.getValue().toString();*/
            
            String studentUsername = client.target(URILookup.getBaseAPI())
                    .path("/proposals/getAcceptedStudentRest")
                    .path(currentProposal.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<String>() {
                    });
            System.out.println("isStudentAcceptedRest stdUsername: " + studentUsername);
            return studentUsername.equals(username) ? "Aceite" : "Não Aceite";
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
        }
        return "não aceite";
    }

    /////////////// PUBLIC PROOFS //////////////////
    public String createPublicProofREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/publicProofs/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newPublicProof)
                    );

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updatePublicProofREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/publicProofs/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentPublicProof));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String removePublicProof(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());

            client.target(URILookup.getBaseAPI())
                    .path("/publicProofs/removeREST")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (NumberFormatException e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<PublicProofDTO> getAllPublicProofsREST() {
        List<PublicProofDTO> returnedPublicProofs;
        try {
            returnedPublicProofs = client.target(URILookup.getBaseAPI())
                    .path("/publicProofs/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<PublicProofDTO>>() {
                    });

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedPublicProofs;
    }

    public String uploadDocumentToPublicProofREST() {
        try {
            document = new DocumentDTO(uploadManager.getCompletePathFile(), uploadManager.getFilename(), uploadManager.getFile().getContentType());
            client.target(URILookup.getBaseAPI())
                    .path("/publicProofs/addDocument")
                    .path(currentPublicProof.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(document));

        } catch (NumberFormatException e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String sendEmailToAllInPublicProof(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("idEmail");
            int id = Integer.parseInt(param.getValue().toString());

            client.target(URILookup.getBaseAPI())
                    .path("/publicProofs/sendEmailToAllREST")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (NumberFormatException e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<PublicProofDTO> clickSearchPublicProof() {
        if (this.searchPublicProof == null || this.searchPublicProof.trim().length() == 0) {
            return getAllPublicProofsREST();
        }

        return searchPublicProofByNameREST();
    }

    public List<PublicProofDTO> searchPublicProofByNameREST() {
        List<PublicProofDTO> list = new LinkedList<>();
        try {

            PublicProofDTO resource = client.target(URILookup.getBaseAPI())
                    .path("/publicProofs/searchByWorkTitle")
                    .path(this.searchPublicProof + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<PublicProofDTO>() {
                    });
            list.add(resource);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return list;
    }

    /////////////// UTILS //////////////////
    public boolean searchFieldCourseIsEmpty() {
        if (this.searchCourse == null) {
            return true;
        }
        return this.searchCourse.trim().isEmpty();
    }
    
    public boolean searchFieldAdminIsEmpty() {
        if (this.searchAdmin == null) {
            return true;
        }
        return this.searchAdmin.trim().isEmpty();
    }
        
    public boolean searchFieldStudentIsEmpty() {
        if (this.searchStudent == null) {
            return true;
        }
        return this.searchStudent.trim().isEmpty();
    }
    
    public boolean searchFieldTeacherIsEmpty() {
        if (this.searchTeacher == null) {
            return true;
        }
        return this.searchTeacher.trim().isEmpty();
    }
    
    public boolean searchFieldInstitutionIsEmpty() {
        if (this.searchInstitution == null) {
            return true;
        }
        return this.searchInstitution.trim().isEmpty();
    }
    
    public boolean searchFieldProposalIsEmpty() {
        if (this.searchProposal == null) {
            return true;
        }
        return this.searchProposal.trim().isEmpty();
    }
    
    public boolean searchFieldPublicProofIsEmpty() {
        if (this.searchPublicProof == null) {
            return true;
        }
        return this.searchPublicProof.trim().isEmpty();
    }

    public int getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(int selectOption) {
        this.selectOption = selectOption;
    }

    public String getSearchCourse() {
        return searchCourse;
    }

    public void setSearchCourse(String searchCourse) {
        this.searchCourse = searchCourse;
    }

    public String getSearchAdmin() {
        return searchAdmin;
    }

    public void setSearchAdmin(String searchAdmin) {
        this.searchAdmin = searchAdmin;
    }

    public String getSearchStudent() {
        return searchStudent;
    }

    public void setSearchStudent(String searchStudent) {
        this.searchStudent = searchStudent;
    }

    public String getSearchTeacher() {
        return searchTeacher;
    }

    public void setSearchTeacher(String searchTeacher) {
        this.searchTeacher = searchTeacher;
    }

    public String getSearchInstitution() {
        return searchInstitution;
    }

    public void setSearchInstitution(String searchInstitution) {
        this.searchInstitution = searchInstitution;
    }

    public String getSearchProposal() {
        return searchProposal;
    }

    public void setSearchProposal(String searchProposal) {
        this.searchProposal = searchProposal;
    }

    public String getSearchPublicProof() {
        return searchPublicProof;
    }

    public void setSearchPublicProof(String searchPublicProof) {
        this.searchPublicProof = searchPublicProof;
    }

    /////////////// GETTERS & SETTERS /////////////////
    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    //Courses
    public CourseDTO getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(CourseDTO newCourse) {
        this.newCourse = newCourse;
    }

    public CourseDTO getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(CourseDTO currentCourse) {
        this.currentCourse = currentCourse;
    }

    //admins
    public AdminDTO getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(AdminDTO newAdmin) {
        this.newAdmin = newAdmin;
    }

    public AdminDTO getCurrentAdmin() {
        return currentAdmin;
    }

    public void setCurrentAdmin(AdminDTO currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    //instituition
    public InstitutionDTO getCurrentInstituition() {
        return currentInstitution;
    }

    public void setCurrentInstitution(InstitutionDTO currentInstitution) {
        this.currentInstitution = currentInstitution;
    }

    public InstitutionDTO getNewInstitution() {
        return newInstitution;
    }

    public void setNewInstitution(InstitutionDTO newInstitution) {
        this.newInstitution = newInstitution;
    }

    //student
    public StudentDTO getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(StudentDTO currentStudent) {
        this.currentStudent = currentStudent;
    }

    public StudentDTO getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(StudentDTO newStudent) {
        this.newStudent = newStudent;
    }

    /* TEACHERS GETTER & SETTER */
    public TeacherDTO getCurrentTeacher() {
        return currentTeacher;
    }

    public void setCurrentTeacher(TeacherDTO currentTeacher) {
        this.currentTeacher = currentTeacher;
    }

    public TeacherDTO getNewTeacher() {
        return newTeacher;
    }

    public void setNewTeacher(TeacherDTO newTeacher) {
        this.newTeacher = newTeacher;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public WorkProposalDTO getNewProposal() {
        return newProposal;
    }

    public void setNewProposal(WorkProposalDTO newProposal) {
        this.newProposal = newProposal;
    }

    public WorkProposalDTO getCurrentProposal() {
        return currentProposal;
    }

    public void setCurrentProposal(WorkProposalDTO currentProposal) {
        this.currentProposal = currentProposal;
    }

    public PublicProofDTO getNewPublicProof() {
        return newPublicProof;
    }

    public void setNewPublicProof(PublicProofDTO newPublicProof) {
        this.newPublicProof = newPublicProof;
    }

    public PublicProofDTO getCurrentPublicProof() {
        return currentPublicProof;
    }

    public void setCurrentPublicProof(PublicProofDTO currentPublicProof) {
        this.currentPublicProof = currentPublicProof;
    }

    public UploadManager getUploadManager() {
        return uploadManager;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

}
