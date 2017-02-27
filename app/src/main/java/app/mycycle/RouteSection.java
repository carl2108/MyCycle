package app.mycycle;

import com.google.gson.JsonObject;


/**
 * Created by carloconnor on 20/02/17.
 */

public class RouteSection {
    private MyLocation location;
    private double sectionTime, sectionDistance, sectionSpeed;
    private int sectionID;

    public RouteSection() {}

    public RouteSection(int ID, MyLocation location, double sectionTime, double sectionDistance, double sectionSpeed) {
        this.sectionID = ID;
        this.location = location;
        this.sectionTime = sectionTime;
        this.sectionDistance = sectionDistance;
        this.sectionSpeed = sectionSpeed;
    }

    public RouteSection(JsonObject jsonObject) {
        jsonObject = jsonObject.getAsJsonObject();
        this.sectionID = jsonObject.get("sectionID").getAsInt();
        this.sectionDistance = jsonObject.get("sectionDistance").getAsDouble();
        this.sectionTime = jsonObject.get("sectionTime").getAsDouble();
        this.sectionSpeed = jsonObject.get("sectionSpeed").getAsDouble();
        this.location = new MyLocation(jsonObject.get("location"));
    }

    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }

    public double getSectionTime() {
        return sectionTime;
    }

    public void setSectionTime(double sectionTime) {
        this.sectionTime = sectionTime;
    }

    public double getSectionDistance() {
        return sectionDistance;
    }

    public void setSectionDistance(double sectionDistance) {
        this.sectionDistance = sectionDistance;
    }

    public double getSectionSpeed() {
        return sectionSpeed;
    }

    public void setSectionSpeed(double sectionSpeed) {
        this.sectionSpeed = sectionSpeed;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

}
