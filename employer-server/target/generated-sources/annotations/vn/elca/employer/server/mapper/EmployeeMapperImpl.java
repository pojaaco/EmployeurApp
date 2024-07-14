package vn.elca.employer.server.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.common.EmployeeProto;
import vn.elca.employer.server.model.dto.EmployeeDto;
import vn.elca.employer.server.model.entity.Employee;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-14T12:18:52+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 1.8.0_411 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl extends EmployeeMapper {

    @Autowired
    private BasicMapper basicMapper;
    @Autowired
    private EmployerMapper employerMapper;
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_11900521056 = DateTimeFormatter.ofPattern( "dd.MM.yyyy" );

    @Override
    public Employee toEntity(EmployeeDto dto) {
        if ( dto == null ) {
            return null;
        }

        Employee employee = new Employee();

        if ( dto.getStartingDate() != null ) {
            employee.setStartingDate( LocalDate.parse( dto.getStartingDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( dto.getEndDate() != null ) {
            employee.setEndDate( LocalDate.parse( dto.getEndDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        employee.setEmployer( employerMapper.getEntityFromId( dto.getEmployerId() ) );
        employee.setId( dto.getId() );
        employee.setNumberAVS( dto.getNumberAVS() );
        employee.setLastName( dto.getLastName() );
        employee.setFirstName( dto.getFirstName() );
        employee.setAmountOfAssuranceAVS( dto.getAmountOfAssuranceAVS() );
        employee.setAmountOfAssuranceAC( dto.getAmountOfAssuranceAC() );
        employee.setAmountOfAssuranceAF( dto.getAmountOfAssuranceAF() );

        return employee;
    }

    @Override
    public EmployeeDto toDto(Employee entity) {
        if ( entity == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        if ( entity.getStartingDate() != null ) {
            employeeDto.setStartingDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getStartingDate() ) );
        }
        if ( entity.getEndDate() != null ) {
            employeeDto.setEndDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getEndDate() ) );
        }
        employeeDto.setEmployerId( employerMapper.getIdFromEntity( entity.getEmployer() ) );
        employeeDto.setId( entity.getId() );
        employeeDto.setNumberAVS( entity.getNumberAVS() );
        employeeDto.setLastName( entity.getLastName() );
        employeeDto.setFirstName( entity.getFirstName() );
        employeeDto.setAmountOfAssuranceAVS( entity.getAmountOfAssuranceAVS() );
        employeeDto.setAmountOfAssuranceAC( entity.getAmountOfAssuranceAC() );
        employeeDto.setAmountOfAssuranceAF( entity.getAmountOfAssuranceAF() );

        return employeeDto;
    }

    @Override
    public EmployeeDto toDto(EmployeeProto proto) {
        if ( proto == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        if ( proto.hasId() ) {
            employeeDto.setId( basicMapper.toLong( proto.getId() ) );
        }
        employeeDto.setNumberAVS( proto.getNumberAVS() );
        employeeDto.setLastName( proto.getLastName() );
        employeeDto.setFirstName( proto.getFirstName() );
        employeeDto.setStartingDate( proto.getStartingDate() );
        employeeDto.setEndDate( proto.getEndDate() );
        employeeDto.setAmountOfAssuranceAVS( proto.getAmountOfAssuranceAVS() );
        employeeDto.setAmountOfAssuranceAC( proto.getAmountOfAssuranceAC() );
        employeeDto.setAmountOfAssuranceAF( proto.getAmountOfAssuranceAF() );
        if ( proto.hasEmployerId() ) {
            employeeDto.setEmployerId( basicMapper.toLong( proto.getEmployerId() ) );
        }

        return employeeDto;
    }

    @Override
    public EmployeeProto toProto(EmployeeDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmployeeProto.Builder employeeProto = EmployeeProto.newBuilder();

        employeeProto.setId( basicMapper.toInt64( dto.getId() ) );
        employeeProto.setNumberAVS( dto.getNumberAVS() );
        employeeProto.setLastName( dto.getLastName() );
        employeeProto.setFirstName( dto.getFirstName() );
        employeeProto.setStartingDate( dto.getStartingDate() );
        employeeProto.setEndDate( dto.getEndDate() );
        employeeProto.setAmountOfAssuranceAVS( dto.getAmountOfAssuranceAVS() );
        employeeProto.setAmountOfAssuranceAC( dto.getAmountOfAssuranceAC() );
        employeeProto.setAmountOfAssuranceAF( dto.getAmountOfAssuranceAF() );
        employeeProto.setEmployerId( basicMapper.toInt64( dto.getEmployerId() ) );

        return employeeProto.build();
    }
}
