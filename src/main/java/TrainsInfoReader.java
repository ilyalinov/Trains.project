import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TrainsInfoReader {
    public static TrainsInfo readFile(Path trainsInfoPath) throws IOException {
        TrainsInfo info = new TrainsInfo();
        int train_counter = 0;
        try(BufferedReader reader = Files.newBufferedReader(trainsInfoPath)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] trainInfo = line.split("\\s+");
                info.trains.add(Integer.parseInt(trainInfo[0]));
                DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("H:mm");
                info.arrivalTimes.add(LocalTime.parse(trainInfo[1], parseFormat));
                Duration d = Duration.ofMinutes(Integer.parseInt(trainInfo[2]));
                info.minsToProccess.add(d.toMinutes());
                info.price.add(Integer.parseInt(trainInfo[3]));
                info.numberOfTrains++;
            }
        }

        return info;
    }
}