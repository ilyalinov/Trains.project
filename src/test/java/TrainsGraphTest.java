import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainsGraphTest {
    private String projDir;
    List<TrainsInfo> info;
    List<TrainsGraph> graph;
    Path[] path;

    @BeforeEach
    void setUp() throws IOException {
        projDir = Paths.get("").toAbsolutePath().toString();
        String[] configPaths = {"/src/main/resources/noTrains.txt",
                "src/main/resources/singleTrain.txt",
                "src/main/resources/trains.txt",
                "src/main/resources/trains1.txt"
        };
        initialize(configPaths);
    }

    void initialize(String[] configPaths) throws IOException {
        info = new ArrayList<>();
        graph = new ArrayList<>();
        for (int i = 0; i < configPaths.length; i++) {
            info.add(TrainsInfoReader.readFile(Paths.get(projDir, configPaths[i])));
            graph.add(new TrainsGraph(info.get(i)));
        }
    }

    // tests for zero trains ("noTrains.txt")
    @Test
    void getMaxEarningsTest1() throws IOException {
        assertEquals(graph.get(0).getMaxEarnings(), 0);
    }

    @Test
    void trainsToUnloadForMaxEarningsTest1() throws IOException {
        List<Integer> actual = graph.get(0).trainsToUnloadForMaxEarnings();
        List<Integer> expected = info.get(0).trains;
        assertIterableEquals(actual, expected);
    }

    // tests for one train ("singleTrain.txt")
    @Test
    void getMaxEarningsTest2() throws IOException {
        assertEquals(graph.get(1).getMaxEarnings(), (int)info.get(1).price.get(1));
    }

    @Test
    void trainsToUnloadForMaxEarningsTest2() throws IOException {
        List<Integer> actual = graph.get(1).trainsToUnloadForMaxEarnings();
        List<Integer> expected = info.get(1).trains;
        assertIterableEquals(actual, expected);
    }

    // tests for trains.txt
    @Test
    void getMaxEarningsTest3() throws IOException {
        assertEquals(graph.get(2).getMaxEarnings(), 60);
    }

    @Test
    void trainsToUnloadForMaxEarningsTest3() throws IOException {
        List<Integer> actual = graph.get(2).trainsToUnloadForMaxEarnings();
        List<Integer> expected = Arrays.asList(3, 6);
        assertIterableEquals(actual, expected);
    }

    // tests for trains1.txt
    @Test
    void getMaxEarningsTest4() throws IOException {
        assertEquals(graph.get(3).getMaxEarnings(), 83);
    }

    @Test
    void trainsToUnloadForMaxEarningsTest4() throws IOException {
        List<Integer> actual = graph.get(3).trainsToUnloadForMaxEarnings();
        List<Integer> expected = Arrays.asList(3, 5, 6, 9, 10);
        assertIterableEquals(actual, expected);
    }
}
