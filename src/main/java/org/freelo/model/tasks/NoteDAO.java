package org.freelo.model.tasks;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.springframework.context.annotation.Scope;

/**
 * Created by karol on 30.11.14.
 */
@org.springframework.stereotype.Component
@Scope("singleton")
public class NoteDAO {
    @Inject
    static EntityManager em;

    public static Note getNote(int id){
        Note n;
        try{
            n = em.find(Note.class,id);
            return n;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean saveNote(Note note){
        try{
            em.persist(note);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public static List<Note> getNotesBelongingToUser(int userId){
        List<Note> notes = em.createQuery(
                            "SELECT n FROM Note n WHERE n.USER_ID = :userId")
                            .setParameter("userId", userId)
                            .getResultList();
        return notes;
    }
}

