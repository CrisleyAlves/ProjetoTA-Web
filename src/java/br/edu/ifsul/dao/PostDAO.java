package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Post;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Prof. Me. Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 * @organization IFSUL - Campus Passo Fundo
 */
@Stateful
public class PostDAO<TIPO> extends DAOGenerico<Post> implements Serializable {

    public PostDAO(){
        super();
        classePersistente = Post.class;
        ordem = "title";
    }
}
