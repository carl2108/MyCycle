package app.mycycle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by carloconnor on 20/02/17.
 */

public class Route {
    private List<RouteSection> routeSections = new ArrayList<>();
    private double totalTime, totalDistance, averageSpeed;
    private int routeID;

    public Route() {}

    public List<RouteSection> getRouteSections() {
        return routeSections;
    }

    public void setRouteSections(List<RouteSection> routeSections) {
        this.routeSections = routeSections;
    }

    public void setRouteSections(String string) {
        JsonArray json = new JsonParser().parse(string).getAsJsonArray();
        Iterator<JsonElement> iterator = json.iterator();
        while(iterator.hasNext()) {
            this.getRouteSections().add(new RouteSection(iterator.next().getAsJsonObject()));
        }
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getID() {
        return routeID;
    }

    public void setID(int ID) {
        this.routeID = ID;
    }

    public void addSection(RouteSection newSection) {
        routeSections.add(newSection);
    }

}
