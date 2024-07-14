package vn.elca.employer.client.component.importer;

import org.apache.commons.io.FilenameUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeImporterFactory {
    // a map with keys as extensions and values as importers
    private static final Map<String, EmployeeImporter> importerCache = new ConcurrentHashMap<>();

    public static synchronized EmployeeImporter getImporter(String filePath) {
        try {
            String extension = FilenameUtils.getExtension(filePath).toLowerCase();
            if (importerCache.containsKey(extension)) {
                return importerCache.get(extension);
            } else {
                for (FileType type : FileType.values()) {
                    if (type.extension.compareTo(extension) == 0) {
                        EmployeeImporter importer = type.importerClass.newInstance();
                        importerCache.put(extension, importer);
                        return importer;
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
//            throw new ImporterCreationException("Can't instantiate importer", e);
            throw new IllegalArgumentException();
        }

//        throw new IllegalArgumentException();
        return null;

//        throw new ImporterCreationException("Unsupported file: " + filePath, new IllegalArgumentException());
    }
}
