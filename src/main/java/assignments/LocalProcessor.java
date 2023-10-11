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
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public final class LocalProcessor implements ScanningProcessor {
    private final static String PROCESSOR_NAME_DELIMITER = " ";
    private StringJoiner processorNameJoiner;

    @Getter(AccessLevel.PUBLIC)
    private long period;

    @Getter(AccessLevel.PUBLIC)
    private int valueOfCheap;

    private StringBuilder processorVersionBuilder;

    @Getter(AccessLevel.PUBLIC)
    private Scanner informationScanner;

    @Getter(AccessLevel.PUBLIC)
    private List<String> strings;

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
                .map(Object::hashCode)
                .forEach(System.out::println);
    }

    @FullNameProcessorGeneratorAnnotation
    @Override
    public String getFullProcessorName(@NonNull Collection<String> stringList) {
        stringList.forEach(processorNameJoiner::add);

        return processorNameJoiner.toString();
    }

    @ReadFullProcessorNameAnnotation
    @Override
    public void readFullProcessorVersion(File file) throws FileNotFoundException {
        informationScanner = new Scanner(file);

        while (informationScanner.hasNext()) {
            this.processorVersionBuilder.append(informationScanner.nextLine());
        }
    }
}
