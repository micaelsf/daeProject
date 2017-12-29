package ejbs;

import dtos.AdminDTO;
import dtos.CourseDTO;
import dtos.InstitutionDTO;
import dtos.InstitutionProposalDTO;
import dtos.PublicProofDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import dtos.TeacherProposalDTO;
import entities.InstitutionProposal.InstitutionProposalType;
import entities.TeacherProposal.TeacherProposalType;
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
    
    @EJB
    private CourseBean courseBean;
    
    @EJB
    private AdminBean adminBean;
    
    @EJB
    private StudentBean studentBean;
    
    @EJB
    private InstitutionBean instituitionBean;
    
    @EJB
    private TeacherBean teacherBean;
    
    @EJB
    private InstitutionProposalBean institutionProposalBean;
    
    @EJB
    private TeacherProposalBean teacherProposalBean;
    
    @EJB
    private PublicProofBean publicProofBean;

    @PostConstruct
    public void populateBD() {
        try {
            
            System.out.println("Inserting Courses");
            courseBean.create(new CourseDTO(1, "EI"));
            courseBean.create(new CourseDTO(2, "IS"));
            courseBean.create(new CourseDTO(3, "JDM"));
            courseBean.create(new CourseDTO(4, "SIS"));
            courseBean.create(new CourseDTO(5, "MEI-CM"));
            courseBean.create(new CourseDTO(6, "MGSIM"));
            
            System.out.println("Inserting Admins");
            adminBean.create(
                    new AdminDTO(
                            "admin1", 
                            "123123", 
                            "admin", 
                            "admin@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );            
            adminBean.create(
                    new AdminDTO(
                            "admin2", 
                            "123123", 
                            "admin_2", 
                            "admin_2@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            
            System.out.println("Inserting Students");
            studentBean.create(
                    new StudentDTO(
                            "student1", 
                            "123123", 
                            "Zé", 
                            "ze@email.com", 
                            "111111111",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            4, 
                            "SIS"
                    )
            );            
            studentBean.create(
                    new StudentDTO(
                            "student2", 
                            "123123", 
                            "Maria", 
                            "maria@email.com",
                            "222222222",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            1, 
                            "EI"
                    )
            );
            studentBean.create(
                    new StudentDTO(
                            "student3", 
                            "123123", 
                            "Joana", 
                            "joana@email.com", 
                            "333333333",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            1, 
                            "EI"
                    )
            );
            studentBean.create(
                    new StudentDTO(
                            "student4", 
                            "123123", 
                            "André", 
                            "andre@email.com", 
                            "444444444",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            2, 
                            "IS"
                    )
            );
            studentBean.create(
                    new StudentDTO(
                            "student5", 
                            "123123", 
                            "Bruno", 
                            "bruno@email.com", 
                            "555555555",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            3, 
                            "JDM"
                    )
            );
            studentBean.create(
                    new StudentDTO(
                            "student6", 
                            "123123", 
                            "Micael", 
                            "micael@email.com", 
                            "666666666",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            1, 
                            "EI"
                    )
            );
            
            System.out.println("Inserting Institutions");
            instituitionBean.create(
                    new InstitutionDTO(
                            "institution1", 
                            "123123", 
                            "CompanyA", 
                            "companya@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx",
                            "Empresa XPTO"
                    )
            );
            instituitionBean.create(
                    new InstitutionDTO(
                            "institution2", 
                            "123123", 
                            "CompanyB", 
                            "companyb@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx",
                            "Empresa XPTO"
                    )
            );
            instituitionBean.create(
                    new InstitutionDTO(
                            "institution3", 
                            "123123", 
                            "CompanyC", 
                            "companyc@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx",
                            "Empresa XPTO"
                    )
            );
            instituitionBean.create(
                    new InstitutionDTO(
                            "institution4", 
                            "123123", 
                            "CompanyD", 
                            "companyd@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx",
                            "Empresa XPTO"
                    )
            );
            instituitionBean.create(
                    new InstitutionDTO(
                            "institution5", 
                            "123123", 
                            "CompanyF", 
                            "companyf@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx",
                            "Empresa XPTO"
                    )
            );
            
            System.out.println("Inserting Teachers");
            teacherBean.create(
                    new TeacherDTO(
                            "teacher1", 
                            "123123", 
                            "mic", 
                            "micaelsf@sapo.pt",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            "DS02-XX"
                    )
            );
            teacherBean.create(
                    new TeacherDTO(
                            "teacher2", 
                            "123123", 
                            "t2", 
                            "dae.ei.ipleiria@gmail.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            "DS02-XX"
                    )
            );
            teacherBean.create(
                    new TeacherDTO(
                            "teacher3", 
                            "t3", 
                            "t3", 
                            "t3@ipleiria.pt",
                            "Leiria",
                            "Rua xxxx, 2400-xxx", 
                            "DS02-XX"
                    )
            );
            
            System.out.println("Inserting Institution Proposals");
            institutionProposalBean.create("institution1", new InstitutionProposalDTO(
                    1,
                    "Titulo 1", 
                    "EI", 
                    "Objetivos do trabalho: ...", 
                    "O trabalho resume-se a ...",
                    "http://www.website.com",
                    "http://www.website-1.com",
                    "http://www.website-2.com",
                    "http://www.website-3.com",
                    "enunciado.pdf",
                    "Plano de trabalho será dividido em 3 fases: ...",
                    "ESTG",
                    "Sólidos conhecimentos em web design, php, JavaScript e framework VUE",
                    200,
                    "Apoio fornecido pelo docente proponente",
                    "Inácio", 
                    InstitutionProposalType.Projeto,
                    "institution1"
            ));
            
            institutionProposalBean.create("institution1", new InstitutionProposalDTO(
                    2,
                    "Titulo 2", 
                    "EI", 
                    "Objetivos do trabalho: ...", 
                    "O trabalho resume-se a ...",
                    "http://www.website.com",
                    "http://www.website-1.com",
                    "http://www.website-2.com",
                    "http://www.website-3.com",
                    "enunciado.pdf",
                    "Plano de trabalho será dividido em 2 fases: ...",
                    "ESTG",
                    "Sólidos conhecimentos em POO e Android Studio",
                    400,
                    "Apoio fornecido pelo docente proponente",
                    "Paulo Neves", 
                    InstitutionProposalType.Dissertação,
                    "institution1"
            ));
            
            institutionProposalBean.create("institution2", new InstitutionProposalDTO(
                    3,
                    "Titulo 3", 
                    "EI", 
                    "Objetivos do trabalho: ...", 
                    "O trabalho resume-se a ...",
                    "http://www.website12.com",
                    "http://www.website-xx.com",
                    "http://www.website-2.com",
                    "http://www.website-3.com",
                    "enunciado.pdf",
                    "Plano de trabalho será dividido em 3 fases: ...",
                    "ESTG",
                    "Sólidos conhecimento em integração de sistemas, micro serviços e C#",
                    800,
                    "Apoio fornecido pela empresa",
                    "João Andrade", 
                    InstitutionProposalType.Estágio,
                    "institution2"
            ));
            
            System.out.println("Inserting Teacher Proposals");
            teacherProposalBean.create("teacher1", new TeacherProposalDTO(
                    4,
                    "Titulo 4", 
                    "EI, EE", 
                    "Objetivos do trabalho: ...", 
                    "O trabalho resume-se a ...",
                    "http://www.website.com",
                    "http://www.website-aaa.com",
                    "http://www.website-2.com",
                    "base.doc",
                    "enunciado.pdf",
                    "Plano de trabalho será dividido em 5 fases: ...",
                    "ESTG",
                    "Sólidos conhecimentos em web design, php, JavaScript e framework VUE",
                    200,
                    "Apoio fornecido pelo docente proponente",
                    TeacherProposalType.Projeto,
                    "teacher1"
            ));
            
            teacherProposalBean.create("teacher1", new TeacherProposalDTO(
                    5,
                    "Titulo 5", 
                    "EI", 
                    "Objetivos do trabalho: ...", 
                    "O trabalho resume-se a ...",
                    "http://www.website.com",
                    "http://www.website-1.com",
                    "http://www.website-2.com",
                    "http://www.website-3.com",
                    "enunciado.pdf",
                    "Plano de trabalho será dividido em 4 fases: ...",
                    "ESTG, xxx",
                    "Sólidos conhecimentos em integração de sistemas",
                    200,
                    "Apoio fornecido pelo estg",
                    TeacherProposalType.Dissertação,
                    "teacher1"
            ));
            
            teacherProposalBean.create("teacher2", new TeacherProposalDTO(
                    6,
                    "Titulo 6", 
                    "EI, EE", 
                    "Objetivos do trabalho: ...", 
                    "O trabalho resume-se a ...",
                    "http://www.website.com",
                    "http://www.website-1.com",
                    "http://www.website-2.com",
                    "http://www.website-3.com",
                    "enunciado.pdf",
                    "Plano de trabalho será dividido em 2 fases: ...",
                    "ESTG",
                    "Sólidos conhecimentos na Framework VUE",
                    200,
                    "Sem apoios",
                    TeacherProposalType.Dissertação,
                    "teacher2"
            ));
            
            teacherProposalBean.create("teacher3", new TeacherProposalDTO(
                    7,
                    "Titulo 7", 
                    "EI", 
                    "Objetivos do trabalho: ...", 
                    "O trabalho resume-se a ...",
                    "http://www.website.com",
                    "http://www.website-1.com",
                    "http://www.website-2.com",
                    "http://www.website-3.com",
                    "About.pdf",
                    "Plano de trabalho será dividido em 3 fases: ...",
                    "ESTG",
                    "Sólidos conhecimentos em web design, php e JavaScript",
                    200,
                    "Apoio fornecido pelo docente proponente",
                    TeacherProposalType.Projeto,
                    "teacher3"
            ));
            
            System.out.println("Inserting Public Proofs");
            publicProofBean.create(new PublicProofDTO(
                    8,
                    "2018-01-01",
                    "14:30",
                    "ESTG, Anfitiatro D0",
                    "admin",
                    "admin@email.com",
                    "t2",
                    "dae.ei.ipleiria@gmail.com",
                    "t3",
                    "t3@ipleiria.pt",
                    "student6",
                    "Micael",
                    "micael@email.com",
                    "666666666",
                    "EI",
                    "Estufas Inteligentes",
                    null,
                    null
            ));
            
            publicProofBean.create(new PublicProofDTO(
                    9,
                    "2018-01-03",
                    "16:00",
                    "ESTG, Anfitiatro xx",
                    "admin",
                    "admin@email.com",
                    "t2",
                    "dae.ei.ipleiria@gmail.com",
                    "t3",
                    "t3@ipleiria.pt",
                    "student5",
                    "Bruno",
                    "bruno@email.com",
                    "555555555",
                    "JDM",
                    "Titulo do trabalho xpto",
                    null,
                    null
            ));
            
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
