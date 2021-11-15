package com.demo.dao;

import com.demo.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class LoginDao {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
    EntityManager em = emf.createEntityManager();

    public String checkAuth (String username, String password) {
        UserEntity userCheck = new UserEntity(username, password);
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("select u from UserEntity u where u.username = (:username) and u.password = (:password)", UserEntity.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            UserEntity user = (UserEntity) query.getSingleResult();
            em.getTransaction().commit();
            if(user != null){
                return user.getUsername();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return "";
    }
}
