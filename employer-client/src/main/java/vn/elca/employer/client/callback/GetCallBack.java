package vn.elca.employer.client.callback;

import javafx.collections.FXCollections;
import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.component.EmployerInputComponent;
import vn.elca.employer.client.component.EmployerTableComponent;
import vn.elca.employer.client.mapper.EmployerMapper;
import vn.elca.employer.client.model.stub.EmployerServiceGrpcStub;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.EmployerGetRequest;

import java.util.List;
import java.util.stream.Collectors;

@Component(id = GetCallBack.ID, name = GetCallBack.ID)
public class GetCallBack implements CallbackComponent {
    public static final String ID = "GetCallBack";
    private static final Logger LOGGER = LoggerFactory.getLogger(GetCallBack.class);

    @Autowired
    private EmployerServiceGrpcStub stub;

    @Autowired
    private EmployerMapper employerMapper;

    @Resource
    private Context context;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.getSourceId().endsWith(EmployerInputComponent.ID)) {
            context.setReturnTarget(EmployerPerspective.ID.concat(".").concat(EmployerTableComponent.ID));
            EmployerGetRequest request = employerMapper.toRequest(message.getTypedMessageBody(EmployerView.class));
            List<EmployerView> results = stub.getEmployer(request)
                    .getEmployersList()
                    .stream()
                    .map(employerMapper::toView)
                    .collect(Collectors.toList());
            return FXCollections.observableArrayList(results);
        }
        return null;
    }
}
