package model;

/**
 * Created by dev on 13/9/18.
 */

public class SalesSearchModel {

    private static SalesSearchModel instance;
    public String title = "";
    public String address = "";
    public String condition;
    public boolean bestRated;
    public double latitude = 0.0;
    public double longitude = 0.0;
    public int minPrice = 0;
    public int maxPrice = 999;
    public int distance = 101;

    public static synchronized SalesSearchModel getInstance() {
        if (instance == null) {
            instance = new SalesSearchModel();
        }
        return instance;
    }

    public static void setInstance(SalesSearchModel mModel) {
        instance = mModel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isBestRated() {
        return bestRated;
    }

    public void setBestRated(boolean bestRated) {
        this.bestRated = bestRated;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
