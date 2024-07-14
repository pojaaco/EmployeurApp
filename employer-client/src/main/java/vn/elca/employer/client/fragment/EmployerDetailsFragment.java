package vn.elca.employer.client.fragment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.component.EmployeeInfoComponent;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.client.perspective.EmployeePerspective;

@Fragment(id = EmployerDetailsFragment.ID,
        viewLocation = "/fxml/EmployerDetailsFragment.fxml",
        scope = Scope.PROTOTYPE)
public class EmployerDetailsFragment {
    public static final String ID = "EmployerDetailsFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    Context context;

    @FXML
    private Button btnDetails;

    private EmployerView employer;

    public void init(EmployerView employer) {
        this.employer = employer;
        btnDetails.textProperty().bind(observableResourceFactory.getStringBinding("EmployerPerspective.EmployerInfoComponent.button.details"));
        btnDetails.setOnAction(event -> {
            context.send(EmployeePerspective.ID, employer);
            context.send(EmployeePerspective.ID.concat(".").concat(EmployeeInfoComponent.ID), employer);
        });
    }
}
