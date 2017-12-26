package dtos;

import entities.WorkProposal.ProposalStatus;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "WorkProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkProposalDTO implements Serializable {
    
    protected int id;        
    protected String title;    
    protected String scientificAreas;
    protected String objectives;
    protected String workResume;  
    protected String bibliography1; 
    protected String bibliography2; 
    protected String bibliography3; 
    protected String bibliography4; 
    protected String bibliography5;  
    protected String workPlan;
    protected String workLocality;  
    protected String successRequirements;  
    protected float budget;
    protected String support;
    protected String supervisor;
    protected ProposalStatus status;
    protected String proposalType;

    public WorkProposalDTO() {
    }    
    
    public WorkProposalDTO(
            int id, 
            String title, 
            String scientificAreas, 
            String objectives,  
            String workResume,  
            String bibliography1, 
            String bibliography2,
            String bibliography3,
            String bibliography4,
            String bibliography5, 
            String workPlan,  
            String workLocality,  
            String successRequirements,  
            float budget,  
            String support,  
            String supervisor,
            String proposalType
    ) {
        this.id = id;        
        this.title = title;        
        this.scientificAreas = scientificAreas;        
        this.objectives = objectives;        
        this.workResume = workResume;        
        this.bibliography1 = bibliography1;       
        this.bibliography2 = bibliography2;     
        this.bibliography3 = bibliography3;     
        this.bibliography4 = bibliography4;     
        this.bibliography5 = bibliography5;      
        this.workPlan = workPlan;        
        this.workLocality = workLocality;        
        this.successRequirements = successRequirements;        
        this.budget = budget;        
        this.support = support;     
        this.supervisor = supervisor;
        this.proposalType = proposalType;
        
        this.status = ProposalStatus.Pendente; 
    }
    
    public void reset() {
        setId(0);
        setTitle(null);        
        setScientificAreas(null);        
        setObjectives(null);
        setWorkResume(null);
        setBibliography1(null);
        setBibliography2(null);
        setBibliography3(null);
        setBibliography4(null);
        setBibliography5(null);
        setWorkPlan(null);
        setWorkLocality(null);
        setSuccessRequirements(null);
        setBudget(0);
        setSupport(null);
        setStatus(null);        
        setSupervisor(null);    
        setProposalType(null);
    }    

    public String getProposalType() {
        return proposalType;
    }

    public void setProposalType(String proposalType) {
        this.proposalType = proposalType;
    }

    public String getSupervisor() {
        return supervisor != null ? supervisor : "N/A";
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(String scientificAreas) {
        this.scientificAreas = scientificAreas;
    }

    public ProposalStatus getStatus() {
        return this.status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;            
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getWorkResume() {
        return workResume;
    }

    public void setWorkResume(String workResume) {
        this.workResume = workResume;
    }
    
    public String getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }

    public String getWorkLocality() {
        return workLocality;
    }

    public void setWorkLocality(String workLocality) {
        this.workLocality = workLocality;
    }

    public String getSuccessRequirements() {
        return successRequirements;
    }

    public void setSuccessRequirements(String successRequirements) {
        this.successRequirements = successRequirements;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getBibliography1() {
        return bibliography1;
    }

    public void setBibliography1(String bibliography1) {
        this.bibliography1 = bibliography1;
    }

    public String getBibliography2() {
        return bibliography2;
    }

    public void setBibliography2(String bibliography2) {
        this.bibliography2 = bibliography2;
    }

    public String getBibliography3() {
        return bibliography3;
    }

    public void setBibliography3(String bibliography3) {
        this.bibliography3 = bibliography3;
    }

    public String getBibliography4() {
        return bibliography4;
    }

    public void setBibliography4(String bibliography4) {
        this.bibliography4 = bibliography4;
    }

    public String getBibliography5() {
        return bibliography5;
    }

    public void setBibliography5(String bibliography5) {
        this.bibliography5 = bibliography5;
    }
    
    public String getBibliographyToString() {
        return (this.bibliography1 != null ? this.bibliography1 + ", \n" : "") +
                (this.bibliography2 != null ? this.bibliography2 + ", \n" : "") +
                (this.bibliography3 != null ? this.bibliography3 + ", \n" : "") +
                (this.bibliography4 != null ? this.bibliography4 + ", \n" : "") +
                (this.bibliography5 != null ? this.bibliography5 : "") ;
    }

}
