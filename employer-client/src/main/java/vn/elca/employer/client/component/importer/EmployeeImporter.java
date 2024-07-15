package vn.elca.employer.client.component.importer;

import vn.elca.employer.client.exception.EmployeeExtractionException;
import vn.elca.employer.client.factory.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;

import java.util.List;

public interface EmployeeImporter {
    void setResourceBundle(ObservableResourceFactory resourceFactory);

    List<EmployeeView> extractEmployeesFromFile(String filePath) throws EmployeeExtractionException;
}
