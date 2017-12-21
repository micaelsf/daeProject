package ejbs;

import dtos.StudentDTO;
import dtos.WorkProposalDTO;
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
    private InstituitionBean instituitionBean;
    
    @EJB
    private TeacherBean teacherBean;
    
    @EJB
    private WorkProposalBean proposalBean;

    @PostConstruct
    public void populateBD() {

        try {
            studentBean.create(new StudentDTO(1, "123123", "Zé", "ze@email.com", "111111111"));            
            studentBean.create(new StudentDTO(2, "123123", "Maria", "maria@email.com", "222222222"));
            studentBean.create(new StudentDTO(3, "123123", "Joana", "joana@email.com", "333333333"));
            studentBean.create(new StudentDTO(4, "123123", "André", "andre@email.com", "444444444"));
            studentBean.create(new StudentDTO(5, "123123", "Bruno", "bruno@email.com", "555555555"));
            studentBean.create(new StudentDTO(6, "123123", "Micael", "micael@email.com", "666666666"));
            
            instituitionBean.create(7, "123123", "CompanyA", "companya@email.com");
            instituitionBean.create(8, "123123", "CompanyB", "companyb@email.com");
            instituitionBean.create(9, "123123", "CompanyC", "companyc@email.com");
            instituitionBean.create(10, "123123", "CompanyD", "companyd@email.com");
            instituitionBean.create(11, "123123", "CompanyF", "companyf@email.com");
            
            
            teacherBean.create(12, "t1", "t1", "t1@ipleiria.pt");
            teacherBean.create(13, "t2", "t2", "t2@ipleiria.pt");
            teacherBean.create(14, "t3", "t3", "t3@ipleiria.pt");

            proposalBean.create(new WorkProposalDTO(1, "Titulo 1", "EI", "Objetivos do trabalho: ...", 3));
            proposalBean.create(new WorkProposalDTO(2, "Titulo 2", "EI", "Objetivos do trabalho: ...", 3));
            proposalBean.create(new WorkProposalDTO(3, "Titulo 3", "EI", "Objetivos do trabalho: ...", 3));            
            proposalBean.create(new WorkProposalDTO(4, "Titulo 4", "EI", "Objetivos do trabalho: ...", 3));

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
        } catch(Exception e){
            logger.warning(e.getMessage());
        }
    }
}
