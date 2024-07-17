package vn.elca.employer.client.callback.importer;

import vn.elca.employer.client.exception.EmployeeExtractionException;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.client.model.view.EmployeeView;

import java.util.List;

public interface EmployeeImporter {
    void setResourceBundle(ObservableResourceFactory resourceFactory);

    List<EmployeeView> extractEmployeesFromFile(String filePath) throws EmployeeExtractionException;
}
