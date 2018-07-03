package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PermissionDAO;
import br.edu.ifsul.dao.PersonDAO;
import br.edu.ifsul.modelo.Permission;
import br.edu.ifsul.modelo.Person;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Prof. Me. Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 * @organization IFSUL - Campus Passo Fundo
 */
@Named(value = "controlePerson")
@ViewScoped
public class ControlePerson implements Serializable {
    
    @EJB
    private PersonDAO<Person> dao;
    private Person objeto;
    private Boolean editando;
    
    @EJB
    private PermissionDAO<Permission> daoPermission;
    private Permission permission;
    private Boolean editandoPermission;
    
    public ControlePerson(){
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/private/person/listar?faces-redirect=true";
    }
    
    public void novo(){
        editando = true;
        setEditandoPermissao((Boolean) false);
        objeto = new Person();
    }
    
    public void alterar(Object id){
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            setEditandoPermissao((Boolean) false);
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: " + 
                    Util.getMensagemErro(e));
        } 
    }
    
    public void excluir(Object id){
        try {
            objeto = dao.getObjectById(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e){
            Util.mensagemErro("Erro ao remover objeto: " + 
                    Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {
            if (objeto.getId() == null){
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
            editando = false;
        } catch(Exception e){
            Util.mensagemErro("Erro ao persistir objeto: " + 
                    Util.getMensagemErro(e));
        }
    }
    
    public void novaPermissao(){
        setEditandoPermissao((Boolean) true);
        setPermission(new Permission());
    }
    
    public void salvaPermissao(){
        
        Boolean repetido = false;
        
        for (Permission obj : objeto.getPermissions()) {
            
            if(objeto.getPermissions().contains(obj)){
                repetido = true;
                break;
            }
        }
        
        if(!repetido){
            objeto.getPermissions().add(getPermission());
            Util.mensagemInformacao("Permissão adicionado com sucesso!");
        }else{
            Util.mensagemErro("O usuário já possui essa permissao");
        }
        
        setEditandoPermissao((Boolean) false);
    }
    
    public void removerPermissao(Permission obj){
        objeto.getPermissions().remove(obj);
        Util.mensagemInformacao("Permissão removida com sucesso");
    }

    public PersonDAO<Person> getDao() {
        return dao;
    }

    public void setDao(PersonDAO<Person> dao) {
        this.dao = dao;
    }

    public Person getObjeto() {
        return objeto;
    }

    public void setObjeto(Person objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Boolean getEditandoPermissao() {
        return editandoPermission;
    }

    public void setEditandoPermissao(Boolean editandoPermission) {
        this.editandoPermission = editandoPermission;
    }

    public PermissionDAO<Permission> getDaoPermission() {
        return daoPermission;
    }

    public void setDaoPermission(PermissionDAO<Permission> daoPermission) {
        this.daoPermission = daoPermission;
    }

}
