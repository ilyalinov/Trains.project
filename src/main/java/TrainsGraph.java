import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TrainsGraph {
    GraphState state;

    public TrainsGraph(TrainsInfo info) {
        // 2 additional vertices() represent start and finish.
        // We search the shortest way between these.
        state = new GraphState(info);

        createEdges();
        findShortestWay();
    }

    public int getMaxEarnings() {
        return 0 - state.pathLength.get(state.finishVertice());
    }

    public List<Integer> trainsToUnloadForMaxEarnings() {
        List<Integer> trainsToUnload = new ArrayList<>();
        int u = state.parent.get(state.finishVertice());
        while(state.parent.get(u) != -1) {
            trainsToUnload.add(0, state.verticesToTrainsMapping().get(u));
            u = state.parent.get(u);
        }

        return trainsToUnload;
    }

    private void createEdges() {
        for (int v : state.vertices()) {
            state.edges.add(new ArrayList<>());
        }

        for (int i = 1; i < state.numberOfVertices(); i++) {
            state.edges.get(state.startVertice()).add(i);
            if (i != state.finishVertice()) {
                state.edges.get(i).add(state.finishVertice());
            }
        }

        for (int i = 1; i < state.numberOfVertices() - 1; i++) {
            for (int j = 1; j < state.numberOfVertices() - 1; j++) {
                LocalTime time1 = state.info().arrivalTimes.get(i);
                int mins1 = time1.getHour() * 60 + time1.getMinute();
                LocalTime time2 = state.info().arrivalTimes.get(j);
                int mins2 = time2.getHour() * 60 + time2.getMinute();

                if (mins1 + state.info().minsToProccess.get(i) <= mins2) {
                    state.edges.get(i).add(j);
                }
            }
        }
    }

    private void findShortestWay() {
        for (int i = 0; i < state.numberOfVertices() - 1; i++) {
            for (int u : state.vertices()) {
                for (int v : state.edges.get(u)) {
                    relaxEdge(u, v);
                }
            }
        }
    }

    private void relaxEdge(int s, int f) {
        if (state.pathLength.get(s) != Integer.MAX_VALUE && state.pathLength.get(s) + weight(s) < state.pathLength.get(f)) {
            state.pathLength.set(f, state.pathLength.get(s) + weight(s));
            setParent(s, f);
        }

        if (state.pathLength.get(s) != Integer.MAX_VALUE && state.pathLength.get(s) + weight(s) == state.pathLength.get(f)) {
            if (state.info().minsToProccess.get(s) < state.info().minsToProccess.get(state.parent.get(f))) {
                state.pathLength.set(f, state.pathLength.get(s) + weight(s));
                setParent(s, f);
            }
        }
    }

    private void setParent(int s, int f) {
        state.parent.set(f, s);
    }

    private int weight(int s) {
        return 0 - state.info().price.get(s);
    }
}