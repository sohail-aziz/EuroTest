package aziz.sohail.eurotest.JSON;


import com.google.gson.annotations.SerializedName;

public class LocationResponse implements Comparable<LocationResponse> {

    @SerializedName("_id")
    private Integer Id;
    private Object key;
    private String name;
    private String fullName;
    @SerializedName("iata_airport_code")
    private String iataAirportCode;
    private String type;
    private String country;
    @SerializedName("geo_position")
    private GeoPosition geoPosition;
    private Object locationId;
    private Boolean inEurope;
    private String countryCode;
    private Boolean coreCountry;

    private float distance;

    /**
     * @return The Id
     */
    public Integer getId() {
        return Id;
    }

    /**
     * @param Id The _id
     */
    public void setId(Integer Id) {
        this.Id = Id;
    }

    /**
     * @return The key
     */
    public Object getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    public void setKey(Object key) {
        this.key = key;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The iataAirportCode
     */
    public String getIataAirportCode() {
        return iataAirportCode;
    }

    /**
     * @param iataAirportCode The iata_airport_code
     */
    public void setIataAirportCode(String iataAirportCode) {
        this.iataAirportCode = iataAirportCode;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The geoPosition
     */
    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    /**
     * @param geoPosition The geo_position
     */
    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    /**
     * @return The locationId
     */
    public Object getLocationId() {
        return locationId;
    }

    /**
     * @param locationId The locationId
     */
    public void setLocationId(Object locationId) {
        this.locationId = locationId;
    }

    /**
     * @return The inEurope
     */
    public Boolean getInEurope() {
        return inEurope;
    }

    /**
     * @param inEurope The inEurope
     */
    public void setInEurope(Boolean inEurope) {
        this.inEurope = inEurope;
    }

    /**
     * @return The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode The countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return The coreCountry
     */
    public Boolean getCoreCountry() {
        return coreCountry;
    }

    /**
     * @param coreCountry The coreCountry
     */
    public void setCoreCountry(Boolean coreCountry) {
        this.coreCountry = coreCountry;
    }

    /**
     * @return The distance
     */
    public float getDistance() {
        return distance;
    }

    /**
     * @param distance The distance
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }


    @Override
    public int compareTo(LocationResponse another) {

        int compareDistance = (int) another.getDistance();

        return ((int) this.distance - compareDistance);
    }
}
