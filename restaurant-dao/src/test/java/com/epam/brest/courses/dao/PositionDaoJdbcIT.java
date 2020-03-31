package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class PositionDaoJdbcIT {

    private final PositionDao positionDao;

    @Autowired
    public PositionDaoJdbcIT(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Test
    public void shouldFindAllPosition(){
        List<Position> positions = positionDao.findAllPosition();
        assertNotNull(positions);
        assertTrue(positions.size() > 0);
    }
}
