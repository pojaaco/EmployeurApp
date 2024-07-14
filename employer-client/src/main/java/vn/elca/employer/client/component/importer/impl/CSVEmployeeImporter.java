package vn.elca.employer.client.component.importer.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.text.CaseUtils;
import vn.elca.employer.client.component.importer.EmployeeImporter;
import vn.elca.employer.client.model.view.EmployeeView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVEmployeeImporter implements EmployeeImporter {
    @Override
    public List<EmployeeView> extractEmployeesFromFile(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<EmployeeView> employees = new ArrayList<>();
            String[] line;
            String[] header = null;
            while ((line = reader.readNext()) != null) {
                if (header == null) {
                    header = line;
                    for (int i = 0; i < header.length; i++) {
                        header[i] = header[i].replaceAll("[-/_.:,]", " ");
                        header[i] = CaseUtils.toCamelCase(header[i], false, ' ');
                        // TODO: Avoid Hardcode
                        if (header[i].equals("numberAvs")) {
                            header[i] = "numberAVS";
                        } else if (header[i].equals("avsAiApg")) {
                            header[i] = "amountOfAssuranceAVS";
                        } else if (header[i].equals("ac")) {
                            header[i] = "amountOfAssuranceAC";
                        } else if (header[i].equals("af")) {
                            header[i] = "amountOfAssuranceAF";
                        }
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
//            throw new CompanyExtractionException("Can't find file " + filePath, e);
            throw new IllegalArgumentException();
        }
    }
}
