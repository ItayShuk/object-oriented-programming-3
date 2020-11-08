import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.Arrays;
import java.util.Comparator;

public class BoopingSite {

    public Hotel[] data;

    public HotelDataset hotelDataSet = new HotelDataset();

    /**
     * This constructor receives as parameter a string, which is
     * the name of the dataset
     *
     * @param name
     */
    public BoopingSite(String name) {
        this.data = HotelDataset.getHotels(name);
    }

    /**
     * This method returns an array of hotels located in the given city, sorted from the highest star-rating
     * to the lowest. Hotels that have the
     * same rating will be organized according to the alphabet order of the hotel’s (property) name. In case
     * there are no hotels in the given city, this method returns an empty array
     *
     * @param city
     * @return
     */
    public Hotel[] getHotelsInCityByRating(String city) {
        Comparator<Hotel> Rating = new Rating();
        Hotel[] lst = getCityHotels(city);
        Arrays.sort(lst, Rating);
        return lst;
    }

    /**
     * Gets city names and returns an array of
     * all hotels in this city
     *
     * @param city
     * @return
     */
    public Hotel[] getCityHotels(String city) {
        int numOfHotels = 0;
        for (Hotel hotel : this.data) {
            if (hotel.getCity().equals(city)) {
                numOfHotels += 1;
            }
        }
        int index = 0;
        Hotel[] lst = new Hotel[numOfHotels];
        for (Hotel hotel : this.data) {
            if (hotel.getCity().equals(city)) {
                lst[index] = hotel;
                index++;
            }
        }
        return lst;
    }

    /**
     * This method
     * returns an array of hotels, sorted according to their (euclidean) distance from the given geographic
     * location, in ascending order. Hotels that are at the
     * same distance from the given location are organized
     * according to the number of points-of-interest for which they are close to (in a decreasing order). In
     * case of illegal input, this method returns an empty array.
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        if (latitude > 90 || latitude < -90 || longitude > 180 || longitude < -180) {
            return new Hotel[0];
        }
        Hotel[] hotels = this.data.clone();
        Comparator<Hotel> Proximity = new Proximity(latitude, longitude);
        Arrays.sort(hotels, Proximity);
        return hotels;
    }

    /**
     * This method returns an array of hotels in the given city,
     * sorted according to their (euclidean) distance
     * from the given geographic location, in ascending order. Hotels that are at the same distance from the
     * given location are organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order). In case of illegal input, this method returns an empty array.
     *
     * @param city
     * @param latitude
     * @param longitude
     * @return
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
        Hotel[] hotels = this.getCityHotels(city);
        Comparator<Hotel> Proximity = new Proximity(latitude, longitude);
        Arrays.sort(hotels, Proximity);
        if (Math.abs(latitude) > 90 || Math.abs(longitude) > 180) {
            return new Hotel[0];
        }
        return hotels;
    }

    /**
     * Comparator for Rating sort
     */
    private class Rating implements Comparator<Hotel> {

        /**
         * The compare function, get two hotels
         * determent who's rating is higher
         *
         * @param hotel1
         * @param hotel2
         * @return
         */
        public int compare(Hotel hotel1, Hotel hotel2) {
            int difference = hotel2.getStarRating() - hotel1.getStarRating();
            if (difference == 0) {
                hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
            }
            return difference;
        }
    }

    /**
     * Comparator for proximity sort
     */
    private class Proximity implements Comparator<Hotel> {

        public double latitude;

        public double longitude;


        /**
         * Constructor get coordinates
         *
         * @param latitude
         * @param longitude
         */
        Proximity(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        /**
         * Comparing function, get two hotels, determent
         * who's closer
         *
         * @param hotel1
         * @param hotel2
         * @return
         */
        public int compare(Hotel hotel1, Hotel hotel2) {
            double hotel1distance = Math.pow((hotel1.getLatitude() - this.latitude), 2)
                    + Math.pow((hotel1.getLongitude() - this.longitude), 2);
            double hotel2distance = Math.pow((hotel2.getLatitude() - this.latitude), 2)
                    + Math.pow((hotel2.getLongitude() - this.longitude), 2);
            double difference = hotel1distance - hotel2distance;
            int finalDifference;
            if (difference == 0) {
                int POIDiff = hotel2.getNumPOI() - hotel1.getNumPOI();
                if (POIDiff == 0) {
                    return hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
                }
                return POIDiff;
            }
            if (difference > 0) {
                finalDifference = 1;
            } else {
                finalDifference = -1;
            }
            return finalDifference;
        }
    }

}
