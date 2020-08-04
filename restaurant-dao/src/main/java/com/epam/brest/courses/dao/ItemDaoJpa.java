package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemDaoJpa extends JpaRepository<Item, Integer> {


}
