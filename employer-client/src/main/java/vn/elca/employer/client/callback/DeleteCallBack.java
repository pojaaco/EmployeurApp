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
import vn.elca.employer.client.component.EmployerResultComponent;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.stub.EmployerServiceGrpcStub;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.EmployerDelRequest;
import vn.elca.employer.common.EmployerDelResponse;

@Component(id = DeleteCallBack.ID,
        name = DeleteCallBack.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME)
public class DeleteCallBack implements CallbackComponent {
    public static final String ID = "DeleteCallBack";
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCallBack.class);

    @Autowired
    private EmployerServiceGrpcStub stub;

    @Resource
    Context context;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            context.setReturnTarget(EmployerPerspective.ID.concat(".").concat(EmployerResultComponent.ID));
            EmployerDelRequest request = EmployerDelRequest.newBuilder()
                    .setId(message.getTypedMessageBody(EmployerView.class).getId())
                    .build();
            EmployerDelResponse response = stub.delEmployer(request);
            if (response.getIsOK()) {
                context.send(EmployerPerspective.ID.concat(".").concat(EmployerResultComponent.ID),
                        message.getTypedMessageBody(EmployerView.class));
            } else {
                LOGGER.debug(response.getMessage(), message.getTypedMessageBody(EmployerView.class));
            }
        }
        return null;
    }
}
