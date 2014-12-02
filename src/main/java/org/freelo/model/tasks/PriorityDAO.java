package org.freelo.model.tasks;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

/**
 * Created by karol on 30.11.14.
 */
@Component
@Scope("singleton")
public class PriorityDAO {
    @Inject
    private static EntityManager em;


    public static boolean savePriority(Priority p){
        em.persist(p);
        return true;
    }

    public static Priority getPriority(int id){
        Priority p;
        try {
            p = em.find(Priority.class, 0);
            return p;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
