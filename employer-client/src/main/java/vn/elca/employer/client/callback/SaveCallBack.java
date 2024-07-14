package vn.elca.employer.client.callback;

import javafx.collections.ObservableList;
import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.component.EmployeeImportComponent;
import vn.elca.employer.client.component.EmployeeInfoComponent;
import vn.elca.employer.client.component.EmployerResultComponent;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.mapper.EmployerMapper;
import vn.elca.employer.client.model.stub.EmployerServiceGrpcStub;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.client.workbench.EmployerWorkbench;
import vn.elca.employer.common.EmployerSetRequest;
import vn.elca.employer.common.EmployerSetResponse;

import java.util.List;

@Component(id = SaveCallBack.ID,
        name = SaveCallBack.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME)
public class SaveCallBack implements CallbackComponent {
    public static final String ID = "SaveCallBack";
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveCallBack.class);

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
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            context.setReturnTarget(EmployeePerspective.ID.concat(".").concat(EmployeeInfoComponent.ID));

            if (message.getSourceId().equals(EmployeePerspective.ID.concat(".").concat(EmployeeInfoComponent.ID))) {
                employer = message.getTypedMessageBody(EmployerView.class);
            } else if (message.getSourceId().equals(EmployeePerspective.ID.concat(".").concat(EmployeeImportComponent.ID))) {
                employees = (List<EmployeeView>) message.getMessageBody();
                LOGGER.debug(String.valueOf(employees.size()));
            }

            if (employer != null && employees != null) {
                employees.forEach(e -> employer.getEmployees().add(e));
                LOGGER.debug(String.valueOf(employer.getEmployees().size()));
                LOGGER.debug(String.valueOf(employer.getId()));
                EmployerSetRequest request = EmployerSetRequest.newBuilder()
                        .setEmployer(employerMapper.toProto(employer))
                        .build();
                EmployerSetResponse response = stub.setEmployer(request);
                if (response.getIsOK()) {
                    context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInfoComponent.ID), employer);
                } else {
                    LOGGER.debug(response.getMessage());
                }
                employer = null;
                employees = null;
            }
        }
        return null;
    }
}
