package ejbs;

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
    
    //@EJB
    //private TeacherBean teacherBean;

    @PostConstruct
    public void populateBD() {

        try {
            studentBean.create(1, "123123", "Zé", "ze@email.com", "111111111");            
            studentBean.create(2, "123123", "Maria", "maria@email.com", "222222222");
            studentBean.create(3, "123123", "Joana", "joana@email.com", "333333333");
            studentBean.create(4, "123123", "André", "andre@email.com", "444444444");
            studentBean.create(5, "123123", "Bruno", "bruno@email.com", "555555555");
            studentBean.create(6, "123123", "Micael", "micael@email.com", "666666666");
            
            instituitionBean.create(7, "123123", "CompanyA", "companya@email.com", "133333333");
            instituitionBean.create(8, "123123", "CompanyB", "companyb@email.com", "144444444");
            instituitionBean.create(9, "123123", "CompanyC", "companyc@email.com", "155555555");
            instituitionBean.create(10, "123123", "CompanyD", "companyd@email.com", "166666666");
            instituitionBean.create(11, "123123", "CompanyF", "companyf@email.com", "1166666");


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

            teacherBean.addSubjectTeacher(1, "t1");
            teacherBean.addSubjectTeacher(2, "t2");
            teacherBean.addSubjectTeacher(1, "t3");
*/
        } catch(Exception e){
            logger.warning(e.getMessage());
        }
    }
}
