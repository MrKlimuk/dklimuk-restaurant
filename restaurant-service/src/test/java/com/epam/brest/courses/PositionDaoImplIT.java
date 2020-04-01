package com.epam.brest.courses;


import com.epam.brest.courses.model.Position;
import com.epam.brest.courses.service.PositionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class})
@ContextConfiguration({"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
public class PositionDaoImplIT {

    @Autowired
    PositionService positionService;

    @Test
    public void shouldFindAllPosition(){
        List<Position> positions = positionService.findAllPosition();
        assertNotNull(positions);
    }
}
