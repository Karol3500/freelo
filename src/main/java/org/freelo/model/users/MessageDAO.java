package org.freelo.model.users;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by artur on 19.01.15.
 */
public class MessageDAO {

    // returns message if found, null if user doesnt exist
    public static Message getMessage(int ID){
        Session session = HibernateSessionFactoryBean.getSession();
        Message message = null;
        try{
            session.beginTransaction();

            message = (Message) session.load(Message.class, ID);

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return message;
    }

    // returns messages list for defined sender and receiver
    public static List<Message> getMessages(int senderID, int receiverID){
        Session session = HibernateSessionFactoryBean.getSession();
        List<Message> messages = null;
        try{
            session.beginTransaction();

            messages = session.createQuery("FROM Message U WHERE U.receiver = " + receiverID + " AND U.sender =" + senderID).list();

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return messages;
    }

    // return not read messages for the user
    public static List<Message> getMessages(int receiverID){
        Session session = HibernateSessionFactoryBean.getSession();
        List<Message> messages = null;
        try{
            session.beginTransaction();

            messages = session.createQuery("FROM Message U WHERE U.receiver = " + receiverID + " AND U.read = " + false).list();

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return messages;
    }

    // returns message ID, sent the message to other user
    public static int sendMessage(int senderID, int receiverID, String text){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer ID = null;
        try{
            session.beginTransaction();

            Message message = new Message(senderID,receiverID,text);
            ID = (Integer) session.save(message);

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return ID;
    }

    // returns message ID, sent the message to other user
    public static int sendMessage(Message message){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer ID = null;
        try{
            session.beginTransaction();

            Message message1 = new Message(message.getSender(),message.getReceiver(),message.getMessage());
            ID = (Integer) session.save(message1);

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
