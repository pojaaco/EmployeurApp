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
import vn.elca.employer.common.EmployerDelRequest;
import vn.elca.employer.common.EmployerGetRequest;
import vn.elca.employer.common.EmployerProto;
import vn.elca.employer.server.model.criteria.EmployerDelCriteria;
import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.dto.EmployeeDto;
import vn.elca.employer.server.model.dto.EmployerDto;
import vn.elca.employer.server.model.entity.Employee;
import vn.elca.employer.server.model.entity.Employer;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-14T12:18:52+0700",
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

        if ( dto.getStartingDate() != null ) {
            employer.setStartingDate( LocalDate.parse( dto.getStartingDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( dto.getEndDate() != null ) {
            employer.setEndDate( LocalDate.parse( dto.getEndDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        employer.setId( dto.getId() );
        employer.setCaisse( dto.getCaisse() );
        employer.setNumber( dto.getNumber() );
        employer.setName( dto.getName() );
        employer.setNumberIDE( dto.getNumberIDE() );
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

        if ( entity.getStartingDate() != null ) {
            employerDto.setStartingDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getStartingDate() ) );
        }
        if ( entity.getEndDate() != null ) {
            employerDto.setEndDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getEndDate() ) );
        }
        employerDto.setId( entity.getId() );
        employerDto.setCaisse( entity.getCaisse() );
        employerDto.setNumber( entity.getNumber() );
        employerDto.setName( entity.getName() );
        employerDto.setNumberIDE( entity.getNumberIDE() );
        employerDto.setEmployees( employeeSetToEmployeeDtoSet( entity.getEmployees() ) );

        return employerDto;
    }

    @Override
    public EmployerDto toDto(EmployerProto proto) {
        if ( proto == null ) {
            return null;
        }

        EmployerDto employerDto = new EmployerDto();

        employerDto.setEmployees( employeeProtoListToEmployeeDtoSet( proto.getEmployeesList() ) );
        if ( proto.hasId() ) {
            employerDto.setId( basicMapper.toLong( proto.getId() ) );
        }
        employerDto.setCaisse( proto.getCaisse() );
        if ( proto.hasNumber() ) {
            employerDto.setNumber( basicMapper.toString( proto.getNumber() ) );
        }
        employerDto.setName( proto.getName() );
        employerDto.setNumberIDE( proto.getNumberIDE() );
        employerDto.setStartingDate( proto.getStartingDate() );
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
        employerProto.setId( basicMapper.toInt64( dto.getId() ) );
        employerProto.setCaisse( dto.getCaisse() );
        employerProto.setNumber( basicMapper.toStringValue( dto.getNumber() ) );
        employerProto.setName( dto.getName() );
        employerProto.setNumberIDE( dto.getNumberIDE() );
        employerProto.setStartingDate( dto.getStartingDate() );
        employerProto.setEndDate( basicMapper.toStringValue( dto.getEndDate() ) );

        return employerProto.build();
    }

    @Override
    public EmployerGetCriteria toCriteria(EmployerGetRequest request) {
        if ( request == null ) {
            return null;
        }

        EmployerGetCriteria employerGetCriteria = new EmployerGetCriteria();

        employerGetCriteria.setCaisse( request.getCaisse() );
        if ( request.hasNumber() ) {
            employerGetCriteria.setNumber( basicMapper.toString( request.getNumber() ) );
        }
        if ( request.hasName() ) {
            employerGetCriteria.setName( basicMapper.toString( request.getName() ) );
        }
        if ( request.hasNumberIDE() ) {
            employerGetCriteria.setNumberIDE( basicMapper.toString( request.getNumberIDE() ) );
        }
        if ( request.hasStartingDate() ) {
            employerGetCriteria.setStartingDate( basicMapper.toDate( request.getStartingDate() ) );
        }
        if ( request.hasEndDate() ) {
            employerGetCriteria.setEndDate( basicMapper.toDate( request.getEndDate() ) );
        }

        return employerGetCriteria;
    }

    @Override
    public EmployerDelCriteria toCriteria(EmployerDelRequest request) {
        if ( request == null ) {
            return null;
        }

        EmployerDelCriteria employerDelCriteria = new EmployerDelCriteria();

        employerDelCriteria.setId( request.getId() );

        return employerDelCriteria;
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
