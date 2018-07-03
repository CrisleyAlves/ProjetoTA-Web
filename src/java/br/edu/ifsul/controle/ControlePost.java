package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PostDAO;
import br.edu.ifsul.modelo.Post;
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
@Named(value = "controlePost")
@ViewScoped
public class ControlePost implements Serializable {
    
    @EJB
    private PostDAO<Post> dao;
    private Post objeto;
    private Boolean editando;
    
    public ControlePost(){
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/private/post/listar?faces-redirect=true";
    }
    
    public void novo(){
        editando = true;
        setObjeto(new Post());
    }
    
    public void alterar(Object id){
        try {
            setObjeto(getDao().getObjectById(id));
            editando = true;
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: " + 
                    Util.getMensagemErro(e));
        } 
    }
    
    public void excluir(Object id){
        try {
            setObjeto(getDao().getObjectById(id));
            getDao().remover(getObjeto());
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e){
            Util.mensagemErro("Erro ao remover objeto: " + 
                    Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {
            if (getObjeto().getId() == null){
                getDao().persist(getObjeto());
            } else {
                getDao().merge(getObjeto());
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
            editando = false;
        } catch(Exception e){
            Util.mensagemErro("Erro ao persistir objeto: " + 
                    Util.getMensagemErro(e));
        }
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public PostDAO<Post> getDao() {
        return dao;
    }

    public void setDao(PostDAO<Post> dao) {
        this.dao = dao;
    }

    public Post getObjeto() {
        return objeto;
    }

    public void setObjeto(Post objeto) {
        this.objeto = objeto;
    }

}
