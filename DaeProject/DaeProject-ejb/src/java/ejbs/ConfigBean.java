package ejbs;

import dtos.InstitutionDTO;
import dtos.InstitutionProposalDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import dtos.TeacherProposalDTO;
import dtos.WorkProposalDTO;
import entities.InstitutionProposal.InstitutionProposalType;
import entities.TeacherProposal.TeacherProposalType;
import entities.WorkProposal.ProposalStatus;
import java.util.LinkedList;
import java.util.List;
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
    private InstitutionBean institutionBean;

    @EJB
    private TeacherBean teacherBean;

    @EJB
    private InstitutionProposalBean institutionProposalBean;

    @EJB
    private TeacherProposalBean teacherProposalBean;

    @EJB
    private WorkProposalBean workProposalBean;

    @PostConstruct
    public void populateBD() {

        try {

            System.out.println("Inserting Students");
            studentBean.create(new StudentDTO(1, "123123", "Zé", "ze@email.com", "111111111"));
            studentBean.create(new StudentDTO(2, "123123", "Maria", "maria@email.com", "222222222"));
            studentBean.create(new StudentDTO(3, "123123", "Joana", "joana@email.com", "333333333"));
            studentBean.create(new StudentDTO(4, "123123", "André", "andre@email.com", "444444444"));
            studentBean.create(new StudentDTO(5, "123123", "Bruno", "bruno@email.com", "555555555"));
            studentBean.create(new StudentDTO(6, "123123", "Micael", "micael@email.com", "666666666"));

            System.out.println("Inserting Institutions");
            institutionBean.create(new InstitutionDTO(7, "123123", "CompanyA", "companya@email.com"));
            institutionBean.create(new InstitutionDTO(8, "123123", "CompanyB", "companyb@email.com"));
            institutionBean.create(new InstitutionDTO(9, "123123", "CompanyC", "companyc@email.com"));
            institutionBean.create(new InstitutionDTO(10, "123123", "CompanyD", "companyd@email.com"));
            institutionBean.create(new InstitutionDTO(11, "123123", "CompanyF", "companyf@email.com"));

            System.out.println("Inserting Teachers");
            teacherBean.create(new TeacherDTO(12, "t1", "t1", "t1@ipleiria.pt", "DS02-XX"));
            teacherBean.create(new TeacherDTO(13, "t2", "t2", "t2@ipleiria.pt", "DS02-XX"));
            teacherBean.create(new TeacherDTO(14, "t3", "t3", "t3@ipleiria.pt", "DS02-XX"));

            System.out.println("Inserting Institution Proposals");
            institutionProposalBean.create(new InstitutionProposalDTO(
                    15,
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
                    InstitutionProposalType.Projeto
            ));
            institutionProposalBean.create(new InstitutionProposalDTO(
                    16,
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
                    InstitutionProposalType.Dissertação
            ));
            institutionProposalBean.create(new InstitutionProposalDTO(
                    17,
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
                    InstitutionProposalType.Estágio
            ));

            System.out.println("Inserting Teacher Proposals");
            teacherProposalBean.create(new TeacherProposalDTO(
                    18,
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
                    TeacherProposalType.Projeto
            ));

            teacherProposalBean.create(new TeacherProposalDTO(
                    19,
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
                    TeacherProposalType.Dissertação
            ));

            teacherProposalBean.create(new TeacherProposalDTO(
                    20,
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
                    TeacherProposalType.Dissertação
            ));

            teacherProposalBean.create(new TeacherProposalDTO(
                    21,
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
                    TeacherProposalType.Projeto
            ));

            System.out.println("enroll Student To WorkProposal ");
            workProposalBean.enrollStudent("1", "16");
            workProposalBean.enrollStudent("3", "18");
            workProposalBean.enrollStudent("5", "20");

            /*
            teacherBean.create("t1", "t1", "t1", "t1@ipleiria.pt", "O1");
            teacherBean.create("t2", "t2", "t2", "t2@ipleiria.pt", "O2");
            teacherBean.create("t3", "t3", "t3", "t3@ipleiria.pt", "O3");

            administratorBean.create("a1", "a1", "a1", "a1@ipleiria.pt");
            administratorBean.create("a2", "a2", "a2", "a2@ipleiria.pt");
            administratorBean.create("a3", "a3", "a3", "a3@ipleiria.pt");
             */
            System.out.println("End Inserting Entities Classes");
        } catch (Exception e) {
            logger.log(Level.WARNING, "RAISED A EXCEPTION Inserting Entities: {0}", e.getMessage());
        }
    }
}
