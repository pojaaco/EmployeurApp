package vn.elca.employer.client.fragment;

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
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployerPerspective;

import java.util.Optional;

@Fragment(id = EmployerDeleteFragment.ID,
        viewLocation = "/fxml/EmployerDeleteFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployerDeleteFragment {
    public static final String ID = "EmployerDeleteFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Button btnDelete;

    public void init(EmployerView employer) {
        btnDelete.textProperty().bind(observableResourceFactory.getStringBinding("Button.delete"));
        btnDelete.setOnAction(event -> {
            Optional<ButtonType> option = showConfirmationDialog(employer);
            if (option.isPresent() && option.get() == ButtonType.OK) {
                context.send(EmployerPerspective.ID.concat(".").concat(DeleteCallBack.ID), employer);
            }
        });
    }

    private Optional<ButtonType> showConfirmationDialog(EmployerView employer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Employer Deletion");
        alert.setHeaderText("Are you sure to delete this employer from database?");
        alert.setContentText(employer.getNumber() + " " + employer.getName());
        return alert.showAndWait();
    }
}
