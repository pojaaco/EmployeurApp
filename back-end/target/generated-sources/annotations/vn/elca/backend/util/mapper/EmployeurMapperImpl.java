package vn.elca.backend.util.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.backend.model.criteria.EmployeurSearchCriteria;
import vn.elca.backend.model.dto.EmployeeDto;
import vn.elca.backend.model.dto.EmployeurDto;
import vn.elca.backend.model.entity.Employee;
import vn.elca.backend.model.entity.Employeur;
import vn.elca.common.EmployeeProto;
import vn.elca.common.EmployeurProto;
import vn.elca.common.EmployeurSearchRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-11T14:10:54+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 1.8.0_411 (Oracle Corporation)"
)
@Component
public class EmployeurMapperImpl extends EmployeurMapper {

    @Autowired
    private BasicMapper basicMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_11900521056 = DateTimeFormatter.ofPattern( "dd.MM.yyyy" );

    @Override
    public Employeur toEntity(EmployeurDto dto) {
        if ( dto == null ) {
            return null;
        }

        Employeur employeur = new Employeur();

        if ( dto.getStartingDate() != null ) {
            employeur.setStartingDate( LocalDate.parse( dto.getStartingDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        if ( dto.getEndDate() != null ) {
            employeur.setEndDate( LocalDate.parse( dto.getEndDate(), dateTimeFormatter_dd_MM_yyyy_11900521056 ) );
        }
        employeur.setId( dto.getId() );
        employeur.setCaisse( dto.getCaisse() );
        employeur.setNumber( dto.getNumber() );
        employeur.setName( dto.getName() );
        employeur.setNumberIDE( dto.getNumberIDE() );
        if ( dto.getEmployees() != null ) {
            for ( EmployeeDto employee : dto.getEmployees() ) {
                employeur.addEmployee( employeeMapper.toEntity( employee ) );
            }
        }

        return employeur;
    }

    @Override
    public EmployeurDto toDto(Employeur entity) {
        if ( entity == null ) {
            return null;
        }

        EmployeurDto employeurDto = new EmployeurDto();

        if ( entity.getStartingDate() != null ) {
            employeurDto.setStartingDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getStartingDate() ) );
        }
        if ( entity.getEndDate() != null ) {
            employeurDto.setEndDate( dateTimeFormatter_dd_MM_yyyy_11900521056.format( entity.getEndDate() ) );
        }
        employeurDto.setId( entity.getId() );
        employeurDto.setCaisse( entity.getCaisse() );
        employeurDto.setNumber( entity.getNumber() );
        employeurDto.setName( entity.getName() );
        employeurDto.setNumberIDE( entity.getNumberIDE() );
        employeurDto.setEmployees( employeeSetToEmployeeDtoSet( entity.getEmployees() ) );

        return employeurDto;
    }

    @Override
    public EmployeurDto toDto(EmployeurProto proto) {
        if ( proto == null ) {
            return null;
        }

        EmployeurDto employeurDto = new EmployeurDto();

        employeurDto.setEmployees( employeeProtoListToEmployeeDtoSet( proto.getEmployeesList() ) );
        employeurDto.setId( proto.getId() );
        employeurDto.setCaisse( proto.getCaisse() );
        employeurDto.setNumber( proto.getNumber() );
        employeurDto.setName( proto.getName() );
        employeurDto.setNumberIDE( proto.getNumberIDE() );
        employeurDto.setStartingDate( proto.getStartingDate() );
        employeurDto.setEndDate( proto.getEndDate() );

        return employeurDto;
    }

    @Override
    public EmployeurProto toProto(EmployeurDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmployeurProto.Builder employeurProto = EmployeurProto.newBuilder();

        if ( dto.getEmployees() != null ) {
            for ( EmployeeDto employee : dto.getEmployees() ) {
                employeurProto.addEmployees( employeeMapper.toProto( employee ) );
            }
        }
        if ( dto.getId() != null ) {
            employeurProto.setId( dto.getId() );
        }
        employeurProto.setCaisse( dto.getCaisse() );
        employeurProto.setNumber( dto.getNumber() );
        employeurProto.setName( dto.getName() );
        employeurProto.setNumberIDE( dto.getNumberIDE() );
        employeurProto.setStartingDate( dto.getStartingDate() );
        employeurProto.setEndDate( dto.getEndDate() );

        return employeurProto.build();
    }

    @Override
    public EmployeurSearchCriteria toCriteria(EmployeurSearchRequest request) {
        if ( request == null ) {
            return null;
        }

        EmployeurSearchCriteria employeurSearchCriteria = new EmployeurSearchCriteria();

        employeurSearchCriteria.setCaisse( request.getCaisse() );
        if ( request.hasNumber() ) {
            employeurSearchCriteria.setNumber( basicMapper.toString( request.getNumber() ) );
        }
        if ( request.hasName() ) {
            employeurSearchCriteria.setName( basicMapper.toString( request.getName() ) );
        }
        if ( request.hasNumberIDE() ) {
            employeurSearchCriteria.setNumberIDE( basicMapper.toString( request.getNumberIDE() ) );
        }
        if ( request.hasStartingDate() ) {
            employeurSearchCriteria.setStartingDate( basicMapper.toDate( request.getStartingDate() ) );
        }
        if ( request.hasEndDate() ) {
            employeurSearchCriteria.setEndDate( basicMapper.toDate( request.getEndDate() ) );
        }

        return employeurSearchCriteria;
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
