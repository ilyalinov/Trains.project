import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphState {
    private int numberOfVertices;
    private final int startVertice = 0;
    private int finishVertice;
    private TrainsInfo info;
    private HashMap<Integer, Integer> verticesToTrainsMapping;

    private List<Integer> vertices;
    List<Integer> pathLength;
    List<Integer> parent;
    List<List<Integer>> edges;

    public GraphState(TrainsInfo info) {
        // 2 additional vertices represent start and finish.
        // We search the shortest way between these.
        numberOfVertices = info.numberOfTrains + 2;
        finishVertice = numberOfVertices - 1;
        this.info = info.copy();
        addStartAndFinish();
        pathLength = new ArrayList<>(Collections.nCopies(numberOfVertices, Integer.MAX_VALUE));
        pathLength.set(startVertice, 0);
        parent = new ArrayList<>(Collections.nCopies(numberOfVertices, -1));
        vertices = IntStream.rangeClosed(startVertice, finishVertice).boxed().collect(Collectors.toList());
        mapVerticeToTrain();
        edges = new ArrayList<>(numberOfVertices);
    }

    private void addStartAndFinish() {
        info.price.add(startVertice,0);
        info.price.add(0);
        info.arrivalTimes.add(startVertice, null);
        info.arrivalTimes.add(null);
        info.minsToProccess.add(startVertice, 0L);
        info.minsToProccess.add(0L);
    }

    private void mapVerticeToTrain() {
        verticesToTrainsMapping = new HashMap<>();
        int v = startVertice + 1;
        for (int t : info.trains) {
            verticesToTrainsMapping.put(v, t);
            v++;
        }
    }

    public int numberOfVertices() {
        return numberOfVertices;
    }

    public int startVertice() {
        return startVertice;
    }

    public int finishVertice() {
        return finishVertice;
    }

    public TrainsInfo info() {
        return info;
    }

    public HashMap<Integer, Integer> verticesToTrainsMapping() {
        return verticesToTrainsMapping;
    }

    public List<Integer> vertices() {
        return vertices;
    }
}
