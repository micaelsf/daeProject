package dtos;

import java.io.Serializable;

public class TeacherProposalDTO implements Serializable{

    private String tipoTrabalho;
    private String titulo;
    private String areaCientifica;
    private String proponents;
    private String resumo;
    private String objetivo; 
    private String bibliografia;
    private String planoTrabalho;
    private String localTrabalho;

    public TeacherProposalDTO() {
    }

    public TeacherProposalDTO(String tipoTrabalho, String titulo, String areaCientifica, String proponents, String resumo, String scholarYear) {
        this.tipoTrabalho = tipoTrabalho;
        this.titulo = titulo;
        this.areaCientifica = areaCientifica;
        this.proponents = proponents;
        this.resumo = resumo;
    }
    
    public void reset(){
        this.tipoTrabalho = null;
        this.titulo = null;
        this.areaCientifica = null;
        this.proponents = null;
        this.resumo = null;     
    }

}
