package model;

public class PlaceInfo {

    private String name,placeId;

    public PlaceInfo(String name, String placeId) {
        this.name = name;
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
