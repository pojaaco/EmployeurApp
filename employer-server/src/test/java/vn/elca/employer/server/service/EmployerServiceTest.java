package vn.elca.employer.server.service;

import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import vn.elca.employer.common.*;

public class EmployerServiceTest {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        EmployerServiceGrpc.EmployerServiceBlockingStub blockingStub = EmployerServiceGrpc.newBlockingStub(channel);

        EmployeeProto employee1 = EmployeeProto.newBuilder()
                .setNumberAVS("756.1355.1235.12")
                .setLastName("Em")
                .setFirstName("ee1")
                .setStartingDate("10.10.2020")
                .setEndDate("20.10.2023")
                .setAmountOfAssuranceAVS(20000.23)
                .setAmountOfAssuranceAC(432.43)
                .setAmountOfAssuranceAF(432.3433)
                .build();

        EmployeeProto employee2 = EmployeeProto.newBuilder()
                .setId(Int64Value.of(1))
                .setNumberAVS("756.5344.3533.23")
                .setLastName("Em")
                .setFirstName("ee2")
                .setStartingDate("10.01.2019")
                .setEndDate("30.10.2022")
                .setAmountOfAssuranceAVS(243330.23)
                .setAmountOfAssuranceAC(4322.43)
                .setAmountOfAssuranceAF(4323.3433)
                .build();

        EmployerProto employer1 = EmployerProto.newBuilder()
                .setCaisse(Caisse.CAISSE_CANTONALE)
                .setName("Added")
                .setNumberIDE("CHE-000.000.000")
                .setStartingDate("10.10.2020")
                .setEndDate(StringValue.of("10.10.2022"))
                .addEmployees(employee2)
                .build();

        EmployerProto employer2 = EmployerProto.newBuilder()
                .setId(Int64Value.of(3))
                .setCaisse(Caisse.CAISSE_CANTONALE)
                .setNumber(StringValue.of("000003"))
                .setName("Edited")
                .setNumberIDE("CHE-999.999.999")
                .setStartingDate("01.01.2019")
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
    }
}
