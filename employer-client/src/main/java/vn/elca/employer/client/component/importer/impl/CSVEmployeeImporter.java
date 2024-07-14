package vn.elca.employer.client.component.importer.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.StringUtils;
import vn.elca.employer.client.component.importer.EmployeeImporter;
import vn.elca.employer.client.exception.EmployeeExtractionException;
import vn.elca.employer.client.model.view.EmployeeView;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CSVEmployeeImporter implements EmployeeImporter {
    private Map<String, String> reverseLookupMap; // value to key

    @Override
    public void initializeReverseLookupMap(ResourceBundle resourceBundle) {
        reverseLookupMap = resourceBundle.keySet().stream()
                .filter(key -> key.contains("Property.Employee"))
                .collect(Collectors.toMap(resourceBundle::getString, key -> key));
    }

    @Override
    public List<EmployeeView> extractEmployeesFromFile(String filePath) throws EmployeeExtractionException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<EmployeeView> employees = new ArrayList<>();
            String[] line;
            String[] header = null;
            while ((line = reader.readNext()) != null) {
                if (header == null) {
                    header = line;
                    for (int i = 0; i < header.length; i++) {
                        header[i] = StringUtils.substringAfterLast(reverseLookupMap.get(header[i]), ".");
                    }
                } else {
                    EmployeeView employee = new EmployeeView();
                    for (int i = 0; i < line.length; i++) {
                        employee.setMember(header[i], line[i]);
                    }
                    employees.add(employee);
                }
            }
            return employees;
        } catch (IOException | CsvValidationException e) {
            throw new EmployeeExtractionException("Can't find file " + filePath, e);
        }
    }
}
