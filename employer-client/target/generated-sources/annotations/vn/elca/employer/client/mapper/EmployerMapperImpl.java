package vn.elca.employer.client.mapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.common.EmployeeProto;
import vn.elca.employer.common.EmployerGetRequest;
import vn.elca.employer.common.EmployerProto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-13T16:28:00+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 1.8.0_411 (Oracle Corporation)"
)
@Component
public class EmployerMapperImpl extends EmployerMapper {

    @Autowired
    private BasicMapper basicMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployerProto toProto(EmployerView view) {
        if ( view == null ) {
            return null;
        }

        EmployerProto.Builder employerProto = EmployerProto.newBuilder();

        if ( view.getEmployees() != null ) {
            for ( EmployeeView employee : view.getEmployees() ) {
                employerProto.addEmployees( employeeMapper.toProto( employee ) );
            }
        }
        if ( view.getId() != null ) {
            employerProto.setId( basicMapper.toInt64( view.getId() ) );
        }
        if ( view.getCaisse() != null ) {
            employerProto.setCaisse( basicMapper.toCaisse( view.getCaisse() ) );
        }
        if ( view.getNumber() != null ) {
            employerProto.setNumber( basicMapper.toStringValue( view.getNumber() ) );
        }
        if ( view.getName() != null ) {
            employerProto.setName( view.getName() );
        }
        if ( view.getNumberIDE() != null ) {
            employerProto.setNumberIDE( view.getNumberIDE() );
        }
        if ( view.getStartingDate() != null ) {
            employerProto.setStartingDate( view.getStartingDate() );
        }
        if ( view.getEndDate() != null ) {
            employerProto.setEndDate( basicMapper.toStringValue( view.getEndDate() ) );
        }

        return employerProto.build();
    }

    @Override
    public EmployerView toView(EmployerProto proto) {
        if ( proto == null ) {
            return null;
        }

        EmployerView employerView = new EmployerView();

        Set<EmployeeView> set = employeeProtoListToEmployeeViewSet( proto.getEmployeesList() );
        if ( set != null ) {
            employerView.setEmployees( set );
        }
        if ( proto.hasId() ) {
            employerView.setId( basicMapper.toLong( proto.getId() ) );
        }
        if ( proto.getCaisse() != null ) {
            employerView.setCaisse( basicMapper.toString( proto.getCaisse() ) );
        }
        if ( proto.hasNumber() ) {
            employerView.setNumber( basicMapper.toString( proto.getNumber() ) );
        }
        if ( proto.getName() != null ) {
            employerView.setName( proto.getName() );
        }
        if ( proto.getNumberIDE() != null ) {
            employerView.setNumberIDE( proto.getNumberIDE() );
        }
        if ( proto.getStartingDate() != null ) {
            employerView.setStartingDate( proto.getStartingDate() );
        }
        if ( proto.hasEndDate() ) {
            employerView.setEndDate( basicMapper.toString( proto.getEndDate() ) );
        }

        return employerView;
    }

    @Override
    public EmployerGetRequest toRequest(EmployerView view) {
        if ( view == null ) {
            return null;
        }

        EmployerGetRequest.Builder employerGetRequest = EmployerGetRequest.newBuilder();

        if ( view.getCaisse() != null ) {
            employerGetRequest.setCaisse( basicMapper.toCaisse( view.getCaisse() ) );
        }
        if ( view.getNumber() != null ) {
            employerGetRequest.setNumber( basicMapper.toStringValue( view.getNumber() ) );
        }
        if ( view.getName() != null ) {
            employerGetRequest.setName( basicMapper.toStringValue( view.getName() ) );
        }
        if ( view.getNumberIDE() != null ) {
            employerGetRequest.setNumberIDE( basicMapper.toStringValue( view.getNumberIDE() ) );
        }
        if ( view.getStartingDate() != null ) {
            employerGetRequest.setStartingDate( basicMapper.toStringValue( view.getStartingDate() ) );
        }
        if ( view.getEndDate() != null ) {
            employerGetRequest.setEndDate( basicMapper.toStringValue( view.getEndDate() ) );
        }

        return employerGetRequest.build();
    }

    protected Set<EmployeeView> employeeProtoListToEmployeeViewSet(List<EmployeeProto> list) {
        if ( list == null ) {
            return null;
        }

        Set<EmployeeView> set = new LinkedHashSet<EmployeeView>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( EmployeeProto employeeProto : list ) {
            set.add( employeeMapper.toView( employeeProto ) );
        }

        return set;
    }
}
