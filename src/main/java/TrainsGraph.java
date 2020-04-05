import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainsGraph {
    private GraphState state;

    public TrainsGraph(TrainsInfo info) {
        state = new GraphState(info);
        createEdges();

        List<Integer> orderedVertices = topSort();
        findShortestWay(orderedVertices);
    }

    public int getMaxEarnings() {
        return state.pathLength.get(state.finishVertice());
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

    private void findShortestWay(List<Integer> orderedVertices) {
        for (int v : orderedVertices) {
            for (int u : state.edges.get(v)) {
                int temp = state.pathLength.get(v) + state.info().price.get(u);
                if (temp > state.pathLength.get(u)) {
                    setParent(v, u);
                    state.pathLength.set(u, temp);
                }

                if (temp == state.pathLength.get(u)) {
                    if (state.info().minsToProccess.get(state.parent.get(u)) > state.info().minsToProccess.get(v)) {
                        setParent(v, u);
                        state.pathLength.set(u, temp);
                    }
                }
            }
        }
    }

    private List<Integer> topSort() {
        List<Boolean> used = new ArrayList<>(Collections.nCopies(state.numberOfVertices(), false));
        List<Integer> orderedVertices = new ArrayList<>();
        depthSearch(state.startVertice(), used, orderedVertices);

        return orderedVertices;
    }

    private void depthSearch(int v, List<Boolean> used, List<Integer> orderedVertices) {
        used.set(v, true);
        for (int u : state.edges.get(v)) {
            if (!used.get(u)) {
                depthSearch(u, used, orderedVertices);
            }
        }

        orderedVertices.add(0, v);
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

    private void setParent(int s, int f) {
        state.parent.set(f, s);
    }
}