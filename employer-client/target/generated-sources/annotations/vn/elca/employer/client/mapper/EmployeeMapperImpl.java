package vn.elca.employer.client.mapper;

import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.common.EmployeeProto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-13T16:28:00+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 1.8.0_411 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl extends EmployeeMapper {

    @Autowired
    private BasicMapper basicMapper;

    @Override
    public EmployeeProto toProto(EmployeeView view) {
        if ( view == null ) {
            return null;
        }

        EmployeeProto.Builder employeeProto = EmployeeProto.newBuilder();

        employeeProto.setId( basicMapper.toInt64( view.getId() ) );
        employeeProto.setNumberAVS( view.getNumberAVS() );
        employeeProto.setLastName( view.getLastName() );
        employeeProto.setFirstName( view.getFirstName() );
        employeeProto.setStartingDate( view.getStartingDate() );
        employeeProto.setEndDate( view.getEndDate() );
        employeeProto.setAmountOfAssuranceAVS( view.getAmountOfAssuranceAVS() );
        employeeProto.setAmountOfAssuranceAC( view.getAmountOfAssuranceAC() );
        employeeProto.setAmountOfAssuranceAF( view.getAmountOfAssuranceAF() );
        employeeProto.setEmployerId( basicMapper.toInt64( view.getEmployerId() ) );

        return employeeProto.build();
    }

    @Override
    public EmployeeView toView(EmployeeProto proto) {
        if ( proto == null ) {
            return null;
        }

        EmployeeView employeeView = new EmployeeView();

        if ( proto.hasId() ) {
            employeeView.setId( basicMapper.toLong( proto.getId() ) );
        }
        employeeView.setNumberAVS( proto.getNumberAVS() );
        employeeView.setLastName( proto.getLastName() );
        employeeView.setFirstName( proto.getFirstName() );
        employeeView.setStartingDate( proto.getStartingDate() );
        employeeView.setEndDate( proto.getEndDate() );
        employeeView.setAmountOfAssuranceAVS( proto.getAmountOfAssuranceAVS() );
        employeeView.setAmountOfAssuranceAC( proto.getAmountOfAssuranceAC() );
        employeeView.setAmountOfAssuranceAF( proto.getAmountOfAssuranceAF() );
        if ( proto.hasEmployerId() ) {
            employeeView.setEmployerId( basicMapper.toLong( proto.getEmployerId() ) );
        }

        return employeeView;
    }
}
