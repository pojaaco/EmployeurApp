package vn.elca.employer.server.service;

import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.elca.employer.common.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmployerServiceTest {

    @Test
    public void testSetService() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        EmployerServiceGrpc.EmployerServiceBlockingStub blockingStub = EmployerServiceGrpc.newBlockingStub(channel);

        EmployeeProto employee1 = EmployeeProto.newBuilder()
                .setNumberAvs("756.1355.1235.12")
                .setLastName("Em")
                .setFirstName("ee1")
                .setStartDate("10.10.2020")
                .setEndDate("20.10.2023")
                .setAvsAiApg(20000.23)
                .setAc(432.43)
                .setAf(432.3433)
                .build();

        EmployeeProto employee2 = EmployeeProto.newBuilder()
                .setId(Int64Value.of(1))
                .setNumberAvs("756.5344.3533.23")
                .setLastName("Em")
                .setFirstName("ee2")
                .setStartDate("10.01.2019")
                .setEndDate("30.10.2022")
                .setAvsAiApg(243330.23)
                .setAc(4322.43)
                .setAf(4323.3433)
                .build();

        EmployerProto employer1 = EmployerProto.newBuilder()
                .setFund(Fund.FUND_CANTONAL)
                .setName("Added")
                .setNumberIde("CHE-000.000.000")
                .setStartDate("10.10.2020")
                .setEndDate(StringValue.of("10.10.2022"))
                .addEmployees(employee1)
                .build();

        EmployerProto employer2 = EmployerProto.newBuilder()
                .setId(Int64Value.of(3))
                .setFund(Fund.FUND_CANTONAL)
                .setNumber(StringValue.of("000003"))
                .setName("Edited")
                .setNumberIde("CHE-999.999.999")
                .setStartDate("01.01.2019")
                .setEndDate(StringValue.of("31.12.2023"))
                .addEmployees(employee2)
                .build();

        EmployerSetRequest requestAdd = EmployerSetRequest.newBuilder()
                .setEmployer(employer1)
                .build();
        EmployerSetResponse responseAdd = blockingStub.setEmployer(requestAdd);
        System.out.println("LOG ADD: " + responseAdd.getMessage());

        EmployerSetRequest requestEdit = EmployerSetRequest.newBuilder()
                .setEmployer(employer2)
                .build();
        EmployerSetResponse responseEdit = blockingStub.setEmployer(requestEdit);
        System.out.println("LOG EDIT: " + responseEdit.getMessage());

        Assert.assertTrue(true);
    }
}
