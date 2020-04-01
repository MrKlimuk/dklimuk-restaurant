package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Position;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.PositionConstants.POSITION_NAME_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class PositionDaoJdbcIT {

    private final Integer POSITION_ORDER_ID = 1;
    private final BigDecimal POSITION_PRICE = new BigDecimal(10);
    private final Integer POSITION_COUNT = 1;

    private final Integer POSITION_COUNT_UPDATE = 2;
    private final Integer POSITION_ORDER_ID_UPDATE = 2;
    private final BigDecimal POSITION_PRICE_UPDATE = new BigDecimal(50);

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

    @Test
    public void shouldFindPositionById(){
        Position position = new Position()
                .setPositionOrderId(POSITION_ORDER_ID)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(POSITION_COUNT);

        Integer id = positionDao.create(position);
        Optional<Position> positionOptional = positionDao.findPositionById(id);

        Assertions.assertTrue(positionOptional.isPresent());
        assertEquals(positionOptional.get().getPositionId(), id);
        assertEquals(positionOptional.get().getPositionOrderId(), position.getPositionOrderId());
        assertEquals(positionOptional.get().getPositionName(), position.getPositionName());
        assertEquals(positionOptional.get().getPositionPrice(), position.getPositionPrice());
        assertEquals(positionOptional.get().getPositionCount(), position.getPositionCount());

    }
    @Test
    public void shouldFindPositionByOrderId(){
        List<Position> positions = positionDao.findPositionByOrderId(POSITION_ORDER_ID);
        assertNotNull(positions);
        assertTrue(positions.size() > 0);
    }

    @Test
    public void shouldCreatePosition(){
        Position position = new Position()
                .setPositionOrderId(POSITION_ORDER_ID)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(POSITION_COUNT);
        Integer id = positionDao.create(position);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdatePosition(){
        Position position = new Position()
                .setPositionOrderId(POSITION_ORDER_ID)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(POSITION_COUNT);
        Integer id = positionDao.create(position);
        assertNotNull(id);

        Optional<Position> positionOptional = positionDao.findPositionById(id);
        Assertions.assertTrue(positionOptional.isPresent());

        positionOptional.get().setPositionOrderId(POSITION_ORDER_ID_UPDATE)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE_UPDATE)
                .setPositionCount(POSITION_COUNT_UPDATE);
        int result = positionDao.update(positionOptional.get());
        assertTrue(1 == result);

        Optional<Position> updatePositionOptional = positionDao.findPositionById(id);
        Assertions.assertTrue(updatePositionOptional.isPresent());
        assertEquals(updatePositionOptional.get().getPositionId(), id);
        assertEquals(updatePositionOptional.get().getPositionOrderId(), positionOptional.get().getPositionOrderId());
        assertEquals(updatePositionOptional.get().getPositionName(), positionOptional.get().getPositionName());
        assertEquals(updatePositionOptional.get().getPositionPrice(), positionOptional.get().getPositionPrice());
        assertEquals(updatePositionOptional.get().getPositionCount(), positionOptional.get().getPositionCount());

    }


    @Test
    public void shouldDeletePosition(){
        Position position = new Position()
                .setPositionOrderId(POSITION_ORDER_ID)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(POSITION_COUNT);

        Integer id = positionDao.create(position);

        List<Position> positions = positionDao.findAllPosition();
        assertNotNull(positions);

        int result = positionDao.delete(id);
        assertTrue(1 == result);

        List<Position> currentPosition = positionDao.findAllPosition();
        assertNotNull(currentPosition);
        assertTrue(positions.size()-1 == currentPosition.size());


    }

}
