package vn.elca.backend.util.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.backend.model.dto.EmployeeDto;
import vn.elca.backend.model.entity.Employee;
import vn.elca.common.EmployeeProto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-11T14:10:54+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 1.8.0_411 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl extends EmployeeMapper {

    @Autowired
    private EmployeurMapper employeurMapper;
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
        employee.setEmployeur( employeurMapper.getEntityFromId( dto.getEmployeurId() ) );
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
        employeeDto.setEmployeurId( employeurMapper.getIdFromEntity( entity.getEmployeur() ) );
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

        employeeDto.setId( proto.getId() );
        employeeDto.setNumberAVS( proto.getNumberAVS() );
        employeeDto.setLastName( proto.getLastName() );
        employeeDto.setFirstName( proto.getFirstName() );
        employeeDto.setStartingDate( proto.getStartingDate() );
        employeeDto.setEndDate( proto.getEndDate() );
        employeeDto.setAmountOfAssuranceAVS( proto.getAmountOfAssuranceAVS() );
        employeeDto.setAmountOfAssuranceAC( proto.getAmountOfAssuranceAC() );
        employeeDto.setAmountOfAssuranceAF( proto.getAmountOfAssuranceAF() );
        employeeDto.setEmployeurId( proto.getEmployeurId() );

        return employeeDto;
    }

    @Override
    public EmployeeProto toProto(EmployeeDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmployeeProto.Builder employeeProto = EmployeeProto.newBuilder();

        if ( dto.getId() != null ) {
            employeeProto.setId( dto.getId() );
        }
        employeeProto.setNumberAVS( dto.getNumberAVS() );
        employeeProto.setLastName( dto.getLastName() );
        employeeProto.setFirstName( dto.getFirstName() );
        employeeProto.setStartingDate( dto.getStartingDate() );
        employeeProto.setEndDate( dto.getEndDate() );
        employeeProto.setAmountOfAssuranceAVS( dto.getAmountOfAssuranceAVS() );
        employeeProto.setAmountOfAssuranceAC( dto.getAmountOfAssuranceAC() );
        employeeProto.setAmountOfAssuranceAF( dto.getAmountOfAssuranceAF() );
        if ( dto.getEmployeurId() != null ) {
            employeeProto.setEmployeurId( dto.getEmployeurId() );
        }

        return employeeProto.build();
    }
}
