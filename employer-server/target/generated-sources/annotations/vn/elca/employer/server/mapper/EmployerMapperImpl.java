package vn.elca.employer.server.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.common.EmployeeProto;
import vn.elca.employer.common.EmployerDeleteRequest;
import vn.elca.employer.common.EmployerGetRequest;
import vn.elca.employer.common.EmployerProto;
import vn.elca.employer.server.model.criteria.EmployerDeleteCriteria;
import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.dto.EmployeeDto;
import vn.elca.employer.server.model.dto.EmployerDto;
import vn.elca.employer.server.model.entity.Employee;
import vn.elca.employer.server.model.entity.Employer;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-14T17:35:39+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 1.8.0_411 (Oracle Corporation)"
)
@Component
public class EmployerMapperImpl extends EmployerMapper {

    @Autowired
    private BasicMapper basicMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_11900521056 = DateTimeFormatter.ofPattern( "dd.MM.yyyy" );

    @Override
    public Employer toEntity(EmployerDto dto) {
        if ( dto == null ) {
            return null;
        }

        Employer employer = new Employer();

        if ( dto.getStartDate() != null ) {
            employer.setStartDate( LocalDate.parse( dto.getStartDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( dto.getEndDate() != null ) {
            employer.setEndDate( LocalDate.parse( dto.getEndDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( dto.getId() != null ) {
            employer.setId( dto.getId() );
        }
        if ( dto.getFund() != null ) {
            employer.setFund( dto.getFund() );
        }
        if ( dto.getNumber() != null ) {
            employer.setNumber( dto.getNumber() );
        }
        if ( dto.getName() != null ) {
            employer.setName( dto.getName() );
        }
        if ( dto.getNumberIde() != null ) {
            employer.setNumberIde( dto.getNumberIde() );
        }
        if ( dto.getEmployees() != null ) {
            for ( EmployeeDto employee : dto.getEmployees() ) {
                employer.addEmployee( employeeMapper.toEntity( employee ) );
            }
        }

        return employer;
    }

    @Override
    public EmployerDto toDto(Employer entity) {
        if ( entity == null ) {
            return null;
        }

        EmployerDto employerDto = new EmployerDto();

        if ( entity.getStartDate() != null ) {
            employerDto.setStartDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getStartDate() ) );
        }
        if ( entity.getEndDate() != null ) {
            employerDto.setEndDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getEndDate() ) );
        }
        if ( entity.getId() != null ) {
            employerDto.setId( entity.getId() );
        }
        if ( entity.getFund() != null ) {
            employerDto.setFund( entity.getFund() );
        }
        if ( entity.getNumber() != null ) {
            employerDto.setNumber( entity.getNumber() );
        }
        if ( entity.getName() != null ) {
            employerDto.setName( entity.getName() );
        }
        if ( entity.getNumberIde() != null ) {
            employerDto.setNumberIde( entity.getNumberIde() );
        }
        Set<EmployeeDto> set = employeeSetToEmployeeDtoSet( entity.getEmployees() );
        if ( set != null ) {
            employerDto.setEmployees( set );
        }

        return employerDto;
    }

    @Override
    public EmployerDto toDto(EmployerProto proto) {
        if ( proto == null ) {
            return null;
        }

        EmployerDto employerDto = new EmployerDto();

        Set<EmployeeDto> set = employeeProtoListToEmployeeDtoSet( proto.getEmployeesList() );
        if ( set != null ) {
            employerDto.setEmployees( set );
        }
        if ( proto.hasId() ) {
            employerDto.setId( basicMapper.toLong( proto.getId() ) );
        }
        if ( proto.getFund() != null ) {
            employerDto.setFund( proto.getFund() );
        }
        if ( proto.hasNumber() ) {
            employerDto.setNumber( basicMapper.toString( proto.getNumber() ) );
        }
        if ( proto.getName() != null ) {
            employerDto.setName( proto.getName() );
        }
        if ( proto.getNumberIde() != null ) {
            employerDto.setNumberIde( proto.getNumberIde() );
        }
        if ( proto.getStartDate() != null ) {
            employerDto.setStartDate( proto.getStartDate() );
        }
        if ( proto.hasEndDate() ) {
            employerDto.setEndDate( basicMapper.toString( proto.getEndDate() ) );
        }

        return employerDto;
    }

    @Override
    public EmployerProto toProto(EmployerDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmployerProto.Builder employerProto = EmployerProto.newBuilder();

        if ( dto.getEmployees() != null ) {
            for ( EmployeeDto employee : dto.getEmployees() ) {
                employerProto.addEmployees( employeeMapper.toProto( employee ) );
            }
        }
        if ( dto.getId() != null ) {
            employerProto.setId( basicMapper.toInt64( dto.getId() ) );
        }
        if ( dto.getFund() != null ) {
            employerProto.setFund( dto.getFund() );
        }
        if ( dto.getNumber() != null ) {
            employerProto.setNumber( basicMapper.toStringValue( dto.getNumber() ) );
        }
        if ( dto.getName() != null ) {
            employerProto.setName( dto.getName() );
        }
        if ( dto.getNumberIde() != null ) {
            employerProto.setNumberIde( dto.getNumberIde() );
        }
        if ( dto.getStartDate() != null ) {
            employerProto.setStartDate( dto.getStartDate() );
        }
        if ( dto.getEndDate() != null ) {
            employerProto.setEndDate( basicMapper.toStringValue( dto.getEndDate() ) );
        }

        return employerProto.build();
    }

    @Override
    public EmployerGetCriteria toCriteria(EmployerGetRequest request) {
        if ( request == null ) {
            return null;
        }

        EmployerGetCriteria employerGetCriteria = new EmployerGetCriteria();

        if ( request.getFund() != null ) {
            employerGetCriteria.setFund( request.getFund() );
        }
        if ( request.hasNumber() ) {
            employerGetCriteria.setNumber( basicMapper.toString( request.getNumber() ) );
        }
        if ( request.hasName() ) {
            employerGetCriteria.setName( basicMapper.toString( request.getName() ) );
        }
        if ( request.hasNumberIde() ) {
            employerGetCriteria.setNumberIde( basicMapper.toString( request.getNumberIde() ) );
        }
        if ( request.hasStartDate() ) {
            employerGetCriteria.setStartDate( basicMapper.toDate( request.getStartDate() ) );
        }
        if ( request.hasEndDate() ) {
            employerGetCriteria.setEndDate( basicMapper.toDate( request.getEndDate() ) );
        }

        return employerGetCriteria;
    }

    @Override
    public EmployerDeleteCriteria toCriteria(EmployerDeleteRequest request) {
        if ( request == null ) {
            return null;
        }

        EmployerDeleteCriteria employerDeleteCriteria = new EmployerDeleteCriteria();

        employerDeleteCriteria.setId( request.getId() );

        return employerDeleteCriteria;
    }

    protected Set<EmployeeDto> employeeSetToEmployeeDtoSet(Set<Employee> set) {
        if ( set == null ) {
            return null;
        }

        Set<EmployeeDto> set1 = new LinkedHashSet<EmployeeDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Employee employee : set ) {
            set1.add( employeeMapper.toDto( employee ) );
        }

        return set1;
    }

    protected Set<EmployeeDto> employeeProtoListToEmployeeDtoSet(List<EmployeeProto> list) {
        if ( list == null ) {
            return null;
        }

        Set<EmployeeDto> set = new LinkedHashSet<EmployeeDto>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( EmployeeProto employeeProto : list ) {
            set.add( employeeMapper.toDto( employeeProto ) );
        }

        return set;
    }
}
