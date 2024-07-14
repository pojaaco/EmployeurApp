package vn.elca.employer.client.component.importer;

import vn.elca.employer.client.model.view.EmployeeView;

import java.util.List;

public interface EmployeeImporter {
    List<EmployeeView> extractEmployeesFromFile(String filePath);
}
