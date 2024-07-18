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
import vn.elca.employer.client.component.EmployeeImportComponent;
import vn.elca.employer.client.callback.importer.EmployeeImporter;
import vn.elca.employer.client.callback.importer.EmployeeImporterFactory;
import vn.elca.employer.client.exception.ImporterCreationException;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;

import java.util.List;

@Component(id = ImportCallBack.ID, name = ImportCallBack.ID)
public class ImportCallBack implements CallbackComponent {
    public static final String ID = "ImportCallBack";
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportCallBack.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.getSourceId().endsWith(EmployeeImportComponent.ID)) {
            String filePath = message.getTypedMessageBody(String.class);
            try {
                EmployeeImporter importer = EmployeeImporterFactory.getImporter(filePath, observableResourceFactory);
                List<EmployeeView> results = importer.extractEmployeesFromFile(filePath);
                return FXCollections.observableArrayList(results);
            } catch (ImporterCreationException e) {
                LOGGER.debug(e.getMessage());
            }
        }
        return null;
    }
}