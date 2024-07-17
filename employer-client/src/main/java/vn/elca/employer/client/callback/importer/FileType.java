package vn.elca.employer.client.callback.importer;

import vn.elca.employer.client.callback.importer.impl.CSVEmployeeImporter;

public enum FileType {
    CSV("csv", CSVEmployeeImporter.class);

    public final String extension;
    public final Class<? extends EmployeeImporter> importerClass;

    FileType(String extension, Class<? extends EmployeeImporter> importerClass) {
        this.extension = extension;
        this.importerClass = importerClass;
    }
}
