package vn.elca.backend.util.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import vn.elca.backend.model.dto.EmployeurDto;
import vn.elca.backend.model.entity.Employeur;

@RunWith(value=SpringRunner.class)
public class EmployeurMapperTest {
    @Autowired
    EmployeurMapper employeurMapper;

    @Test
    public void testToEntity() {
        EmployeurDto dto = new EmployeurDto();
        dto.setStartingDate("01.01.2000");
        Employeur entity = employeurMapper.toEntity(dto);
        System.out.println(entity.getStartingDate());
        Assert.assertTrue(true);
    }
}
