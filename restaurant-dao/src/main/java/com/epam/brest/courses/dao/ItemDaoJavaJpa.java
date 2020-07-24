package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("javajpa")
public class ItemDaoJavaJpa implements ItemDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDaoJavaJpa.class);


    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.epam.brest.courses.model");
    private EntityManager em;

    public ItemDaoJavaJpa() {
        em = emf.createEntityManager();
    }

    @Override
    public List<Item> findAllItems() {

        List<Item> items = em.createQuery("from Item").getResultList();
//        emf.close();

        return items;
    }

    @Override
    public Optional<Item> findItemById(Integer itemId) {
  
        Item item = em.find(Item.class, itemId);
        return Optional.ofNullable(item);
        
    }

    @Override
    public Integer create(Item item) {
        LOGGER.info("Dao JavaJPAImpl: create ({}): ", item);
        item.setItemId(null);

        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Item item) {
        try {
            em.getTransaction().begin();
            Item itemPersist = em.find(Item.class, item.getItemId());

            itemPersist.setItemName(item.getItemName());
            itemPersist.setItemPrice(item.getItemPrice());

            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

//        emf.close();
        return 1;
    }

    @Override
    public int delete(Integer itemId) {
        try {
            em.getTransaction().begin();
            Item itemPersist = em.find(Item.class, itemId);
            em.remove(itemPersist);
            em.getTransaction().commit();
//            emf.close();
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteAllItems() {
        LOGGER.info("Dao JavaJPAImpl: delete All ");
        em.getTransaction().begin();
//        em.createQuery("DELETE from Item");
        Query query = em.createQuery("DELETE FROM Item e ");
        int rowsDeleted = query.executeUpdate();
        LOGGER.info("Dao JavaJPAImpl: delete rows({}): ", rowsDeleted);

        em.getTransaction().commit();

    }
}
