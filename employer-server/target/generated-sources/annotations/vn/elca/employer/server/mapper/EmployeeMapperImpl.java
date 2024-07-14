package vn.elca.employer.server.mapper;

import java.math.BigDecimal;
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
    date = "2024-07-14T17:35:39+0700",
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

        if ( dto.getStartDate() != null ) {
            employee.setStartDate( LocalDate.parse( dto.getStartDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( dto.getEndDate() != null ) {
            employee.setEndDate( LocalDate.parse( dto.getEndDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( dto.getEmployerId() != null ) {
            employee.setEmployer( employerMapper.getEntityFromId( dto.getEmployerId() ) );
        }
        if ( dto.getId() != null ) {
            employee.setId( dto.getId() );
        }
        if ( dto.getNumberAvs() != null ) {
            employee.setNumberAvs( dto.getNumberAvs() );
        }
        if ( dto.getLastName() != null ) {
            employee.setLastName( dto.getLastName() );
        }
        if ( dto.getFirstName() != null ) {
            employee.setFirstName( dto.getFirstName() );
        }
        if ( dto.getAvsAiApg() != null ) {
            employee.setAvsAiApg( dto.getAvsAiApg() );
        }
        if ( dto.getAc() != null ) {
            employee.setAc( dto.getAc() );
        }
        if ( dto.getAf() != null ) {
            employee.setAf( dto.getAf() );
        }

        return employee;
    }

    @Override
    public EmployeeDto toDto(Employee entity) {
        if ( entity == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        if ( entity.getStartDate() != null ) {
            employeeDto.setStartDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getStartDate() ) );
        }
        if ( entity.getEndDate() != null ) {
            employeeDto.setEndDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getEndDate() ) );
        }
        if ( entity.getEmployer() != null ) {
            employeeDto.setEmployerId( employerMapper.getIdFromEntity( entity.getEmployer() ) );
        }
        if ( entity.getId() != null ) {
            employeeDto.setId( entity.getId() );
        }
        if ( entity.getNumberAvs() != null ) {
            employeeDto.setNumberAvs( entity.getNumberAvs() );
        }
        if ( entity.getLastName() != null ) {
            employeeDto.setLastName( entity.getLastName() );
        }
        if ( entity.getFirstName() != null ) {
            employeeDto.setFirstName( entity.getFirstName() );
        }
        if ( entity.getAvsAiApg() != null ) {
            employeeDto.setAvsAiApg( entity.getAvsAiApg() );
        }
        if ( entity.getAc() != null ) {
            employeeDto.setAc( entity.getAc() );
        }
        if ( entity.getAf() != null ) {
            employeeDto.setAf( entity.getAf() );
        }

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
        if ( proto.getNumberAvs() != null ) {
            employeeDto.setNumberAvs( proto.getNumberAvs() );
        }
        if ( proto.getLastName() != null ) {
            employeeDto.setLastName( proto.getLastName() );
        }
        if ( proto.getFirstName() != null ) {
            employeeDto.setFirstName( proto.getFirstName() );
        }
        if ( proto.getStartDate() != null ) {
            employeeDto.setStartDate( proto.getStartDate() );
        }
        if ( proto.getEndDate() != null ) {
            employeeDto.setEndDate( proto.getEndDate() );
        }
        employeeDto.setAvsAiApg( BigDecimal.valueOf( proto.getAvsAiApg() ) );
        employeeDto.setAc( BigDecimal.valueOf( proto.getAc() ) );
        employeeDto.setAf( BigDecimal.valueOf( proto.getAf() ) );
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

        if ( dto.getId() != null ) {
            employeeProto.setId( basicMapper.toInt64( dto.getId() ) );
        }
        if ( dto.getNumberAvs() != null ) {
            employeeProto.setNumberAvs( dto.getNumberAvs() );
        }
        if ( dto.getLastName() != null ) {
            employeeProto.setLastName( dto.getLastName() );
        }
        if ( dto.getFirstName() != null ) {
            employeeProto.setFirstName( dto.getFirstName() );
        }
        if ( dto.getStartDate() != null ) {
            employeeProto.setStartDate( dto.getStartDate() );
        }
        if ( dto.getEndDate() != null ) {
            employeeProto.setEndDate( dto.getEndDate() );
        }
        if ( dto.getAvsAiApg() != null ) {
            employeeProto.setAvsAiApg( dto.getAvsAiApg().doubleValue() );
        }
        if ( dto.getAc() != null ) {
            employeeProto.setAc( dto.getAc().doubleValue() );
        }
        if ( dto.getAf() != null ) {
            employeeProto.setAf( dto.getAf().doubleValue() );
        }
        if ( dto.getEmployerId() != null ) {
            employeeProto.setEmployerId( basicMapper.toInt64( dto.getEmployerId() ) );
        }

        return employeeProto.build();
    }
}
