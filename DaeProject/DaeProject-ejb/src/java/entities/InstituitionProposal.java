/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

public class InstituitionProposal extends WorkProposal implements Serializable {

    private String supervisor;

    public static enum Empresa implements Serializable {
        Project, Internship, Dissertation
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
}
