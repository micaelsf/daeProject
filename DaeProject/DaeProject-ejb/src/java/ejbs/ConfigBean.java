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
                            1, 
                            "123123", 
                            "admin", 
                            "admin@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );            
            adminBean.create(
                    new AdminDTO(
                            2, 
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
                            3, 
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
                            4, 
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
                            5, 
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
                            6, 
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
                            7, 
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
                            8, 
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
                            9, 
                            "123123", 
                            "CompanyA", 
                            "companya@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            instituitionBean.create(
                    new InstitutionDTO(
                            10, 
                            "123123", 
                            "CompanyB", 
                            "companyb@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            instituitionBean.create(
                    new InstitutionDTO(
                            12, 
                            "123123", 
                            "CompanyC", 
                            "companyc@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            instituitionBean.create(
                    new InstitutionDTO(
                            13, 
                            "123123", 
                            "CompanyD", 
                            "companyd@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            instituitionBean.create(
                    new InstitutionDTO(
                            13, 
                            "123123", 
                            "CompanyF", 
                            "companyf@email.com",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            
            System.out.println("Inserting Teachers");
            teacherBean.create(
                    new TeacherDTO(
                            14, 
                            "123", 
                            "mic", 
                            "micaelsf@sapo.pt", 
                            "DS02-XX",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            teacherBean.create(
                    new TeacherDTO(
                            15, 
                            "t2", 
                            "t2", 
                            "dae.ei.ipleiria@gmail.com", 
                            "DS02-XX",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            teacherBean.create(
                    new TeacherDTO(
                            16, 
                            "t3", 
                            "t3", 
                            "t3@ipleiria.pt", 
                            "DS02-XX",
                            "Leiria",
                            "Rua xxxx, 2400-xxx"
                    )
            );
            
            System.out.println("Inserting Institution Proposals");
            institutionProposalBean.create(new InstitutionProposalDTO(
                    17,
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
                    9
            ));
            
            institutionProposalBean.create(new InstitutionProposalDTO(
                    18,
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
                    9
            ));
            
            institutionProposalBean.create(new InstitutionProposalDTO(
                    19,
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
                    10
            ));
            
            System.out.println("Inserting Teacher Proposals");
            teacherProposalBean.create(new TeacherProposalDTO(
                    20,
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
                    14
            ));
            
            teacherProposalBean.create(new TeacherProposalDTO(
                    21,
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
                    14
            ));
            
            teacherProposalBean.create(new TeacherProposalDTO(
                    22,
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
                    15
            ));
            
            teacherProposalBean.create(new TeacherProposalDTO(
                    23,
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
                    16
            ));
            
            publicProofBean.create(new PublicProofDTO(
                    24,
                    "2018-01-01",
                    "14:30",
                    "ESTG, Anfitiatro D0",
                    "Joaquim Almeida",
                    "joaq@email.com",
                    "Andre Costa",
                    "andr@email.com",
                    "Ana Filipa",
                    "ana@email.com",
                    8,
                    "Micael",
                    "micael@email.com",
                    "666666666",
                    "EI",
                    "Estufas Inteligentes",
                    null,
                    null
            ));
            
            publicProofBean.create(new PublicProofDTO(
                    25,
                    "2018-01-03",
                    "16:00",
                    "ESTG, Anfitiatro xx",
                    "Joaquim Almeida",
                    "joaq@email.com",
                    "Andre Costa",
                    "andr@email.com",
                    "Ana Filipa",
                    "ana@email.com",
                    7,
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
