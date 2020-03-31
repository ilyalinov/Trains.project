import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TrainsInfo {
    int numberOfTrains;
    List<Integer> trains;
    List<LocalTime> arrivalTimes;
    List<Long> minsToProccess;
    List<Integer> price;

    public TrainsInfo() {
        trains = new ArrayList<>();
        arrivalTimes = new ArrayList<>();
        minsToProccess = new ArrayList<>();
        price = new ArrayList<>();
        numberOfTrains = 0;
    }

    public TrainsInfo copy() {
        TrainsInfo info = new TrainsInfo();
        info.numberOfTrains = this.numberOfTrains;
        info.trains = this.trains;
        info.arrivalTimes = this.arrivalTimes;
        info.minsToProccess = this.minsToProccess;
        info.price = this.price;
        return info;
    }
}
