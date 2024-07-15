package vn.elca.employer.client.component.importer;

import org.apache.commons.io.FilenameUtils;
import vn.elca.employer.client.exception.ImporterCreationException;
import vn.elca.employer.client.factory.ObservableResourceFactory;

import java.util.HashMap;
import java.util.Map;

public class EmployeeImporterFactory {
    // a map with keys as extensions and values as importers
    private static final Map<String, EmployeeImporter> importerCache = new HashMap<>();

    public static EmployeeImporter getImporter(String filePath, ObservableResourceFactory resourceFactory) throws ImporterCreationException {
        try {
            String extension = FilenameUtils.getExtension(filePath).toLowerCase();
            if (importerCache.containsKey(extension)) {
                return importerCache.get(extension);
            } else {
                for (FileType type : FileType.values()) {
                    if (type.extension.compareTo(extension) == 0) {
                        EmployeeImporter importer = type.importerClass.newInstance();
                        importer.setResourceBundle(resourceFactory);
                        importerCache.put(extension, importer);
                        return importer;
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ImporterCreationException("Can't instantiate importer", e);
        }

        throw new ImporterCreationException("Unsupported file: " + filePath, new IllegalArgumentException());
    }
}
