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
import vn.elca.employer.client.component.EmployeeImportComponent;
import vn.elca.employer.client.component.importer.EmployeeImporter;
import vn.elca.employer.client.component.importer.EmployeeImporterFactory;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.perspective.EmployeePerspective;

import java.util.List;

@Component(id = ImportCallBack.ID,
        name = ImportCallBack.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME)
public class ImportCallBack implements CallbackComponent {
    public static final String ID = "ImportCallBack";
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportCallBack.class);

    @Resource
    Context context;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        context.setReturnTarget(EmployeePerspective.ID.concat(".").concat(EmployeeImportComponent.ID));
        String filePath = message.getTypedMessageBody(String.class);
        EmployeeImporter importer = EmployeeImporterFactory.getImporter(filePath);
        if (importer != null) {
            LOGGER.debug("Importer not null");
            List<EmployeeView> results = importer.extractEmployeesFromFile(filePath);
            LOGGER.debug(String.valueOf(results.size()));
            return FXCollections.observableArrayList(results);
        }
        return null;
    }
}
