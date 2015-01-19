package org.freelo.model.users;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by artur on 19.01.15.
 */
public class FriendsDAO {

    // returns friend if found, null if user doesnt exist
    public static Friends getCouple(int ID){
        Session session = HibernateSessionFactoryBean.getSession();
        Friends friend = null;
        try{
            session.beginTransaction();

            friend = (Friends) session.load(Friends.class, ID);

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return friend;
    }

    // returns friend if found, null if user doesnt exist
    public static List<Friends> getFriends(int userID){
        Session session = HibernateSessionFactoryBean.getSession();
        List<Friends> friends = null;
        try{
            session.beginTransaction();

            friends = session.createQuery("FROM Friends U WHERE U.userID = " + userID).list();

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return friends;
    }

    // returns friend if found, null if user doesnt exist
    public static int addFriend(int userID, int friendID){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer ID = null;
        try{
            session.beginTransaction();

            List<Friends> friends = session.createQuery("FROM Friends U WHERE U.userID = " + userID + " AND U.friendID =" + friendID).list();
            if (friends.isEmpty()) {
                Friends friend = new Friends(userID,friendID);
                ID = (Integer) session.save(friend);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return ID;
    }

}
