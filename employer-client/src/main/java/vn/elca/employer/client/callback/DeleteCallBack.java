package vn.elca.employer.client.callback;

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
import vn.elca.employer.client.component.EmployerTableComponent;
import vn.elca.employer.client.model.stub.EmployerServiceGrpcStub;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.EmployerDeleteRequest;
import vn.elca.employer.common.EmployerDeleteResponse;

@Component(id = DeleteCallBack.ID, name = DeleteCallBack.ID)
public class DeleteCallBack implements CallbackComponent {
    public static final String ID = "DeleteCallBack";
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCallBack.class);

    @Autowired
    private EmployerServiceGrpcStub stub;

    @Resource
    private Context context;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            EmployerView object = message.getTypedMessageBody(EmployerView.class);
            EmployerDeleteRequest request = EmployerDeleteRequest.newBuilder()
                    .setId(object.getId())
                    .build();
            EmployerDeleteResponse response = stub.deleteEmployer(request);
            if (response.getIsOK()) {
                context.send(EmployerPerspective.ID.concat(".").concat(EmployerTableComponent.ID), object);
            } else {
                LOGGER.debug(response.getMessage(), object);
            }
        }
        return null;
    }
}
