package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PersonDAO;
import br.edu.ifsul.modelo.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Crisl
 */
@Named(value = "controleLogin")
@SessionScoped
public class ControleLogin implements Serializable{
    
    private List<Person> person;
    private Person usuarioAutenticado;
    
    @EJB
    private PersonDAO<Person> daoUsuario;
    
    String usuario;
    String senha;

    public ControleLogin() {
        person = new ArrayList<>();
    }
    
    public String paginaLogin(){
        return "/login?faces-redirect=true";
    }
    
    public String efetuarLogin(){
        try {
            HttpServletRequest request = (HttpServletRequest) 
                    FacesContext.getCurrentInstance().getExternalContext().getRequest();
            
            request.login(this.usuario, this.senha);
            
            System.out.println("Usuario: "+this.usuario);
            System.out.println("Senha: "+this.senha);
            
            if(request.getUserPrincipal() != null){
                setUsuarioAutenticado(getDaoUsuario().localizaPorNomePerson(request.getUserPrincipal().getName()));
                getPerson().add(usuarioAutenticado);
                Util.mensagemInformacao("Login efetuado com sucesso");
                usuario = "";
                senha = "";
            }
            
            return "/index?faces-redirect=true";
            
        } catch (Exception e) {
            Util.mensagemErro("Usuário ou senha inválidos! " + Util.getMensagemErro(e));
            return "/login?faces-redirect=true";
        }
    }
    
    public String efetuarLogout(){
        try {
            getPerson().remove(usuarioAutenticado);
            setUsuarioAutenticado(null);
            HttpServletRequest request = (HttpServletRequest) 
                    FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.logout();
        } catch (Exception e) {
            Util.mensagemErro("Erro: "+Util.getMensagemErro(e));
        }
        return "/index?faces-redirect=true";
    }

    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Person getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(Person usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }

    public PersonDAO<Person> getDaoUsuario() {
        return daoUsuario;
    }

    public void setDaoUsuario(PersonDAO<Person> daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }
    
    
    
    
    
}
