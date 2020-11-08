import oop.ex3.searchengine.Hotel;
import org.junit.Test;

import static org.junit.Assert.*;


public class BoopingSiteTest {

    private BoopingSite site = new BoopingSite("hotels_tst1.txt");

    /**
     * Doing rating test
     */
    @Test
    public void RatingTest() {

        Hotel[] hotels = site.getHotelsInCityByRating("manali");

        /*manali city*/
        assertEquals("Baragarh Villa first hotel", "72968b25d39d6fbc47f342fc6ca80cee",
                hotels[0].getUniqueId());

        assertEquals("Baragarh Regency, 25th hotel", "2c8db027d43a9452a43e88eb30d9f983",
                hotels[25].getUniqueId());

        assertArrayEquals("Not a valid city", new Hotel[0],
                site.getHotelsInCityByRating("BLA BLA BLA BLA"));

        for (int i = 0; i < hotels.length - 1; i++) {
            if (hotels[i].getStarRating() == hotels[i+1].getStarRating()){
                assertTrue(hotels[i].getPropertyName().compareTo(hotels[i+1].getPropertyName()) <= 0);
            }
            else assertTrue(hotels[i].getStarRating() >= hotels[i+1].getStarRating());
        }
    }

    /**
     * Proximity test
     */
    @Test
    public void ByProximity() {
        Double latitude = 32.2459026;
        Double longitude = 77.1880853;

        Hotel[] hotels1 = site.getHotelsByProximity(latitude, longitude);

        assertEquals("Baragarh Villa first hotel", "72968b25d39d6fbc47f342fc6ca80cee",
                hotels1[0].getUniqueId());

        assertEquals("The Sunshine Heritage (since1944)", "a0cfe38a5d3c765c6be6a985cdbaf83a",
                hotels1[25].getUniqueId());

        Hotel[] hotels2 = site.getHotelsByProximity(30, 190);

        assertArrayEquals("Not valid a input", new Hotel[0], hotels2);

        for (int i = 0; i < hotels2.length - 1; i++) {
            assertTrue(doesItCloser(latitude,longitude,hotels2[i],hotels2[i+1])>=0);
            if (doesItCloser(latitude,longitude,hotels2[i],hotels2[i+1])==0){
                assertTrue((hotels2[i+1].getNumPOI())>=(hotels2[i].getNumPOI()));
            }
        }
    }

    /**
     * get coordinates and hotels, determent who's closer to
     * this coordinates.
     * if 1 return poistive number, if 2 returns negative
     * else return 0
     * @param latitude
     * @param longitude
     * @param hotel1
     * @param hotel2
     * @return
     */
    public double doesItCloser(Double latitude,Double longitude,Hotel hotel1,Hotel hotel2){
        double hotel1distance = Math.pow((hotel1.getLatitude() - latitude), 2)
                + Math.pow((hotel1.getLongitude() - longitude), 2);
        double hotel2distance = Math.pow((hotel2.getLatitude() - latitude), 2)
                + Math.pow((hotel2.getLongitude() - longitude), 2);
        return (hotel2distance-hotel1distance);
    }

    /**
     * City and proximity tests
     */
    @Test
    public void byCityAndProximity() {

        double latitude = 32.31021537;
        double longitude = 77.1772561;


        Hotel[] hotels = site.getHotelsInCityByProximity("manali", 32.31021537, 77.1772561);

        assertEquals("closest hotel", "dfefae12c65327da24a4bb89174b399c", hotels[0].getUniqueId());

        assertEquals("11th closest hotel", "0f511e761abdd126a738f1b1b37318ba",
                hotels[10].getUniqueId());

        hotels = site.getHotelsInCityByProximity("Bob", 31, 51);

        assertArrayEquals("Not valid name", new Hotel[0], hotels);

        hotels = site.getHotelsInCityByProximity("manali", 120, 0);

        assertArrayEquals("Not valid coordinates", new Hotel[0], hotels);

        hotels = site.getHotelsInCityByProximity("manali", 30, 200);

        assertArrayEquals("Not valid coordinates", new Hotel[0], hotels);


        for (int i = 0; i < hotels.length - 1; i++) {
            assertTrue(doesItCloser(latitude,longitude,hotels[i],hotels[i+1])>=0);
            if (doesItCloser(latitude,longitude,hotels[i],hotels[i+1])==0){
                assertTrue((hotels[i+1].getNumPOI())<=(hotels[i].getNumPOI()));
            }
        }

    }
}
