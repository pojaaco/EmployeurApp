package vn.elca.employer.client.callback;

import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.component.EmployeeImportComponent;
import vn.elca.employer.client.component.EmployeeInputComponent;
import vn.elca.employer.client.mapper.EmployerMapper;
import vn.elca.employer.client.model.message.MessageType;
import vn.elca.employer.client.model.stub.EmployerServiceGrpcStub;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.common.EmployerSetRequest;
import vn.elca.employer.common.EmployerSetResponse;

import java.util.HashSet;
import java.util.List;

@Component(id = SetCallBack.ID, name = SetCallBack.ID)
public class SetCallBack implements CallbackComponent {
    public static final String ID = "SetCallBack";
    private static final Logger LOGGER = LoggerFactory.getLogger(SetCallBack.class);

    @Autowired
    private EmployerServiceGrpcStub stub;

    @Autowired
    private EmployerMapper employerMapper;

    @Resource
    private Context context;

    private EmployerView employer;

    private List<EmployeeView> employees;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        String sourceId = message.getSourceId();
        if (sourceId.endsWith(EmployeeInputComponent.ID)) {
            if (message.isMessageBodyTypeOf(MessageType.class)
                    && message.getTypedMessageBody(MessageType.class).compareTo(MessageType.RESET) == 0) {
                resetData();
            } else {
                employer = message.getTypedMessageBody(EmployerView.class);
            }
        } else if (sourceId.endsWith(EmployeeImportComponent.ID)) {
            employees = (List<EmployeeView>) message.getMessageBody();
        }

        if (employer != null && employees != null) {
            setData();
            resetData();
        }
        return null;
    }

    private void setData() {
        if (employer.getEmployees() == null) {
            employer.setEmployees(new HashSet<>());
        }
        employees.forEach(e -> employer.getEmployees().add(e));
        EmployerSetRequest request = EmployerSetRequest.newBuilder()
                .setEmployer(employerMapper.toProto(employer))
                .build();
        EmployerSetResponse response = stub.setEmployer(request);
        if (response.getIsOK()) {
            context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInputComponent.ID), MessageType.SUCCESS);
        } else {
            context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInputComponent.ID), MessageType.FAILURE);
            LOGGER.debug(response.getMessage());
        }
    }

    private void resetData() {
        employer = null;
        employees = null;
    }
}
