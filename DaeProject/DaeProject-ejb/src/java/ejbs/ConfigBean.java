package ejbs;

import dtos.InstitutionDTO;
import dtos.InstitutionProposalDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import dtos.TeacherProposalDTO;
import entities.InstitutionProposal.InstitutionProposalType;
import entities.TeacherProposal.TeacherProposalType;
import entities.WorkProposal.ProposalStatus;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");    
    
    //@EJB
    //private AdministratorBean administratorBean;
    
    @EJB
    private StudentBean studentBean;
    
    @EJB
    private InstitutionBean instituitionBean;
    
    @EJB
    private TeacherBean teacherBean;
    
    @EJB
    private InstitutionProposalBean institutionProposalBean;
    private TeacherProposalBean teacherProposalBean;

    @PostConstruct
    public void populateBD() {

        try {
            
            System.out.println("Inserting Students:");
            studentBean.create(new StudentDTO(1, "123123", "Zé", "ze@email.com", "111111111"));            
            studentBean.create(new StudentDTO(2, "123123", "Maria", "maria@email.com", "222222222"));
            studentBean.create(new StudentDTO(3, "123123", "Joana", "joana@email.com", "333333333"));
            studentBean.create(new StudentDTO(4, "123123", "André", "andre@email.com", "444444444"));
            studentBean.create(new StudentDTO(5, "123123", "Bruno", "bruno@email.com", "555555555"));
            studentBean.create(new StudentDTO(6, "123123", "Micael", "micael@email.com", "666666666"));
            
            System.out.println("Inserting Institutions:");
            instituitionBean.create(new InstitutionDTO(7, "123123", "CompanyA", "companya@email.com"));
            instituitionBean.create(new InstitutionDTO(8, "123123", "CompanyB", "companyb@email.com"));
            instituitionBean.create(new InstitutionDTO(9, "123123", "CompanyC", "companyc@email.com"));
            instituitionBean.create(new InstitutionDTO(10, "123123", "CompanyD", "companyd@email.com"));
            instituitionBean.create(new InstitutionDTO(11, "123123", "CompanyF", "companyf@email.com"));
            
            System.out.println("Inserting Teachers:");
            teacherBean.create(new TeacherDTO(12, "t1", "t1", "t1@ipleiria.pt"));
            teacherBean.create(new TeacherDTO(13, "t2", "t2", "t2@ipleiria.pt"));
            teacherBean.create(new TeacherDTO(14, "t3", "t3", "t3@ipleiria.pt"));
            
            System.out.println("Inserting Institution Proposals:");     
            institutionProposalBean.create(new InstitutionProposalDTO(15, "Titulo 1", "EI", "Objetivos do trabalho: ...", "Inácio", InstitutionProposalType.Estágio));
            institutionProposalBean.create(new InstitutionProposalDTO(16, "Titulo 2", "EI", "Objetivos do trabalho: ...", "Paulo Neves", InstitutionProposalType.Dissertação));
            institutionProposalBean.create(new InstitutionProposalDTO(17, "Titulo 3", "EI", "Objetivos do trabalho: ...", "João Andrade", InstitutionProposalType.Estágio));            
            institutionProposalBean.create(new InstitutionProposalDTO(18, "Titulo 4", "EI", "Objetivos do trabalho: ...", "Jonh", InstitutionProposalType.Projeto));
            
            System.out.println("Inserting Teacher Proposals:");
            teacherProposalBean.create(new TeacherProposalDTO(19, "Titulo 5", "EI", "Objetivos do trabalho: ...", TeacherProposalType.Dissertação));
            //teacherProposalBean.create(new TeacherProposalDTO(20, "Titulo 6", "EI", "Objetivos do trabalho: ...", 3, TEACHER_PROPOSAL_TYPE.Projeto));
            //teacherProposalBean.create(new TeacherProposalDTO(21, "Titulo 7", "EI", "Objetivos do trabalho: ...", 3, TEACHER_PROPOSAL_TYPE.Dissertação));            
            //teacherProposalBean.create(new TeacherProposalDTO(22, "Titulo 8", "EI", "Objetivos do trabalho: ...", 3, TEACHER_PROPOSAL_TYPE.Projeto));

            /*
            studentBean.enrollStudent("1111111", 1);
            studentBean.enrollStudent("1111111", 2);
            studentBean.enrollStudent("2222222", 3);
            studentBean.enrollStudent("2222222", 4);

            studentBean.enrollStudent("3333333", 5);
            studentBean.enrollStudent("3333333", 6);
            studentBean.enrollStudent("4444444", 6);
            studentBean.enrollStudent("4444444", 7);

            teacherBean.create("t1", "t1", "t1", "t1@ipleiria.pt", "O1");
            teacherBean.create("t2", "t2", "t2", "t2@ipleiria.pt", "O2");
            teacherBean.create("t3", "t3", "t3", "t3@ipleiria.pt", "O3");

            administratorBean.create("a1", "a1", "a1", "a1@ipleiria.pt");
            administratorBean.create("a2", "a2", "a2", "a2@ipleiria.pt");
            administratorBean.create("a3", "a3", "a3", "a3@ipleiria.pt");
*/  
            System.out.println("End Inserting Entities Classes");
        } catch(Exception e){
            logger.log(Level.WARNING, "RAISED A EXCEPTION Inserting Entities: {0}", e.getMessage());
        }
    }
}
