package assignments;

import lombok.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

@SuppressWarnings("unused")
public interface ScanningProcessor {
    void replaceAllStrings(@NonNull Collection<String> strings);

    String getFullProcessorName(@NonNull Collection<String> stringList);

    void readFullProcessorVersion(File file) throws FileNotFoundException;
}
