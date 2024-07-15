package vn.elca.employer.client.fragment;

import com.google.protobuf.Int64Value;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.callback.DeleteCallBack;
import vn.elca.employer.client.component.EmployerTableComponent;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.stub.EmployerServiceGrpcStub;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployerPerspective;
import vn.elca.employer.common.EmployerGetRequest;
import vn.elca.employer.common.EmployerGetResponse;

import java.util.Optional;

@Fragment(id = EmployerDeleteFragment.ID,
        viewLocation = "/fxml/EmployerDeleteFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployerDeleteFragment {
    public static final String ID = "EmployerDeleteFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Autowired
    private EmployerServiceGrpcStub stub;

    @Resource
    private Context context;

    @FXML
    private Button btnDelete;

    public void init(EmployerView employer) {
        btnDelete.textProperty().bind(observableResourceFactory.getStringBinding("Button.delete"));
        btnDelete.setOnAction(event -> {
            if (verify(employer)) {
                Optional<ButtonType> option = showConfirmationDialog();
                if (option.isPresent() && option.get() == ButtonType.OK) {
                    context.send(EmployerPerspective.ID.concat(".").concat(DeleteCallBack.ID), employer);
                }
            } else {
                showInformationDialog();
                context.send(EmployerPerspective.ID.concat(".").concat(EmployerTableComponent.ID), employer);
            }
        });
    }

    public boolean verify(EmployerView employer) {
        EmployerGetRequest request = EmployerGetRequest.newBuilder()
                .setId(Int64Value.of(employer.getId()))
                .build();
        EmployerGetResponse response = stub.getEmployer(request);
        return !response.getEmployersList().isEmpty();
    }

    private Optional<ButtonType> showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Employer Deletion");
        alert.setHeaderText("Are you sure to delete this employer from database?");
        return alert.showAndWait();
    }

    private void showInformationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Employer Deletion");
        alert.setHeaderText("This employer is not in database!");
        alert.showAndWait();
    }
}
