package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Permission;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Prof. Me. Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 * @organization IFSUL - Campus Passo Fundo
 */
@Stateful
public class PermissionDAO<TIPO> extends DAOGenerico<Permission> implements Serializable {

    public PermissionDAO(){
        super();
        classePersistente = Permission.class;
        ordem = "name";
    }
}
