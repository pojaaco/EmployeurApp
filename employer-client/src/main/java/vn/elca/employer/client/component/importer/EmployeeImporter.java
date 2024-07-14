package vn.elca.employer.client.component.importer;

import vn.elca.employer.client.exception.EmployeeExtractionException;
import vn.elca.employer.client.model.view.EmployeeView;

import java.util.List;
import java.util.ResourceBundle;

public interface EmployeeImporter {
    void initializeReverseLookupMap(ResourceBundle resourceBundle);

    List<EmployeeView> extractEmployeesFromFile(String filePath) throws EmployeeExtractionException;
}
