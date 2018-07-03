package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Person;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Prof. Me. Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 * @organization IFSUL - Campus Passo Fundo
 */
@Stateful
public class PersonDAO<TIPO> extends DAOGenerico<Person> implements Serializable {

    public PersonDAO(){
        super();
        classePersistente = Person.class;
        ordem = "name";
    }
    
    public Person getObjectById(Object id) throws Exception {
        Person obj = em.find(Person.class, id);
        /**
         * A linha obj.getPermissoes().size(); é necessária para inicializar a coleção
         * para quando ela for exibida na tela não gerar um erro de 
         * lazyInicializationException
         */
        obj.getPermissions().size(); 
        return obj;
    }   
    
    public Person localizaPorNomePerson(String person){
        Query query = em.createQuery("from Person where upper(person) = :person");
        query.setParameter("person", person.toUpperCase());
        Person obj = (Person) query.getSingleResult();
        obj.getPermissions().size();
        return obj;
    }
}
