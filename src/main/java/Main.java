import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String projDir = Paths.get("").toAbsolutePath().toString();
        Path path1 = Paths.get(projDir, "/src/main/resources/trains1.txt");
        TrainsInfo info1 = TrainsInfoReader.readFile(path1);
        TrainsGraph graph1 = new TrainsGraph(info1);

        System.out.println("Results for configuration file " + path1.toString() + " :");
        System.out.print("Max earnings = ");
        System.out.println(graph1.getMaxEarnings());
        System.out.print("Numbers of trains to unload (sorted by arrival time): ");
        System.out.println(graph1.trainsToUnloadForMaxEarnings());
    }
}
