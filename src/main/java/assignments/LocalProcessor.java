package assignments;

import assignments.annotations.FullNameProcessorGeneratorAnnotation;
import assignments.annotations.ListIteratorAnnotation;
import assignments.annotations.ReadFullProcessorNameAnnotation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Logger;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public final class LocalProcessor implements ScanningProcessor {
    private static final String PROCESSOR_NAME_DELIMITER = " ";
    private StringJoiner processorNameJoiner;

    @Getter(AccessLevel.PUBLIC)
    private long period;

    @Getter(AccessLevel.PUBLIC)
    private int valueOfCheap;

    private StringBuilder processorVersionBuilder;

    @Getter(AccessLevel.PUBLIC)
    private List<String> strings;

    private Scanner informationScanner;

    @SuppressWarnings("unused")
    public Scanner getInformationScanner() {
        throw new IllegalStateException();
    }

    @SuppressWarnings("unused")
    public LocalProcessor(
            @NonNull String processorName,
            long period,
            @NonNull String processorVersion,
            int valueOfCheap,
            @NonNull Scanner informationScanner,
            @NonNull List<String> strings) {
        this.processorNameJoiner = new StringJoiner(PROCESSOR_NAME_DELIMITER);
        processorNameJoiner.add(processorName);

        this.period = period;

        this.processorVersionBuilder = new StringBuilder(processorVersion);

        this.valueOfCheap = valueOfCheap;
        this.informationScanner = informationScanner;
        this.strings = strings;
    }

    @SuppressWarnings("unused")
    public String getProcessorName() {
        return processorNameJoiner.toString();
    }

    @SuppressWarnings("unused")
    public String getProcessorVersion() {
        return processorVersionBuilder.toString();
    }

    @ListIteratorAnnotation
    @Override
    public void replaceAllStrings(@NonNull Collection<String> incomingStrings) {
        strings = new LinkedList<>(incomingStrings);

        incomingStrings.stream()
                .filter(Objects::nonNull)
                .map(Object::hashCode)
                .forEach(hash -> Logger.getGlobal().fine(hash.toString()));
    }

    @FullNameProcessorGeneratorAnnotation
    @Override
    public String getFullProcessorName(@NonNull Collection<String> stringList) {
        stringList
                .stream()
                .filter(Objects::nonNull)
                .forEach(processorNameJoiner::add);

        return processorNameJoiner.toString();
    }

    @ReadFullProcessorNameAnnotation
    @Override
    public void readFullProcessorVersion(File file) {
        if (file == null) {
            throw new IllegalStateException("File was null");
        }

        try (var scanner = new Scanner(file)) {
            this.informationScanner = scanner;

            while (this.informationScanner.hasNext()) {
                this.processorVersionBuilder.append(informationScanner.nextLine());
            }
        } catch (FileNotFoundException exception) {
            var message = "File {0} could not been found";
            Logger.getGlobal().severe(MessageFormat.format(message, file.toString()));
            throw new IllegalStateException(exception);
        }
    }
}