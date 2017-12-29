package web;

import entities.UserGroup;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class UserManager implements Serializable {

    private String username;
    private String password;
    private boolean isLoggedIn = false;
    private static final Logger logger = Logger.getLogger("web.UserManager");

    public UserManager() {
        isLoggedIn = false;
    }
    
    public String redirect() {
        if (isUserInRole(UserGroup.GROUP.Administrator)) {
            return "/faces/admin/index?faces-redirect=true";
        }

        if (isUserInRole(UserGroup.GROUP.Student)) {
            return "/faces/students/index?faces-redirect=true";
        }

        if (isUserInRole(UserGroup.GROUP.Teacher)) {
            return "/faces/teachers/index?faces-redirect=true";
        }

        if (isUserInRole(UserGroup.GROUP.Institution)) {
            return "/faces/institutions/index?faces-redirect=true";
        }

        return "/error?faces-redirect=true";
    }

    public String login() {
        // se a flag não estiver a true significa que existe alguem logado mas que não fez login.. !?
        // portanto, faz logout dessa sessão e loga com a nova sessão
        if(!isLoggedIn) {
            clearLogin();
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(username, password);
            isLoggedIn = true;
        } catch (ServletException e) {
            logger.log(Level.WARNING, e.getMessage());
            return "/error?faces-redirect=true";
        }

        return redirect();
    }

    public boolean isUserInRole(UserGroup.GROUP group) {
        return isUserInRole(group.toString());
    }

    public boolean isUserInRole(String role) {
        return (isSomeUserAuthenticated() && FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role));
    }

    public boolean isSomeUserAuthenticated() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null;
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        // remove data from beans:
        for (String bean : context.getExternalContext().getSessionMap().keySet()) {
            context.getExternalContext().getSessionMap().remove(bean);
        }
        // destroy session:
        HttpSession session
                = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        isLoggedIn = false;
    }

    public String clearLogin() {
        if (isSomeUserAuthenticated()) {
            logout();
            return "/login.xhtml?faces-redirect=true";
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

}
