package vn.elca.backend.util.mapper;

import com.google.protobuf.StringValue;
import org.junit.Assert;
import org.junit.Test;

public class BasicMapperTest {
    @Test
    public void smallTest() {
        StringValue value1 = StringValue.newBuilder().build();
        System.out.println(value1.getValue().isEmpty());
        StringValue value2 = StringValue.of("");
        System.out.println(value2.getValue().isEmpty());
        Assert.assertTrue(true);
    }
}
