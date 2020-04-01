package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Position;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.PositionConstants.POSITION_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class PositionDaoJdbcIT {

    private final Integer POSITION_ORDER_ID = 1;
    private final BigDecimal POSITION_PRICE = new BigDecimal(10);
    private final Integer POSITION_COUNT = 1;

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
                .setPositionCount(1);

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


}
