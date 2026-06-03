package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(
            MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
            ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo
    ) {
        return args -> {
            mRepo.deleteAll(); rRepo.deleteAll(); shRepo.deleteAll();
            aRepo.deleteAll(); eRepo.deleteAll(); lRepo.deleteAll();

            LocalDateTime in30  = LocalDateTime.now().plusDays(30);
            LocalDateTime in60  = LocalDateTime.now().plusDays(60);
            LocalDateTime in90  = LocalDateTime.now().plusDays(90);

            addRajahmundryData(mRepo, rRepo, shRepo, aRepo, eRepo, lRepo, in30);
            addVijayawadaData (mRepo, rRepo, shRepo, aRepo, eRepo, lRepo, in60);
            addGunturData     (mRepo, rRepo, shRepo, aRepo, eRepo, lRepo, in90);
            addHyderabadData  (mRepo, rRepo, shRepo, aRepo, eRepo, lRepo, in30);
            addChennaiData    (mRepo, rRepo, shRepo, aRepo, eRepo, lRepo, in60);
            addBangaloreData  (mRepo, rRepo, shRepo, aRepo, eRepo, lRepo, in60);
            addNewDelhiData   (mRepo, rRepo, shRepo, aRepo, eRepo, lRepo, in90);
            addMumbaiData     (mRepo, rRepo, shRepo, aRepo, eRepo, lRepo, in30);
        };
    }

    // ========= City Data =========

    private void addRajahmundryData(MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
                                    ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo,
                                    LocalDateTime eventDate) {
        String city = "Rajahmundry";
        saveMovie(mRepo, "Kalki 2898-AD", "Sci-Fi",      9.1, "kalki.jpg",   "Swaroop Theatre, " + city);
        saveMovie(mRepo, "Devara",         "Action",      9.0, "devara.jpg",  "Gaiety Theatre, "  + city);
        saveMovie(mRepo, "OG",             "Gangster",    9.2, "og.jpg",      "Kumari Theatre, "  + city);
        saveMovie(mRepo, "Mirai",          "Fantasy",     8.0, "mirai.jpg",   "Syamala Theatre, " + city);
        saveMovie(mRepo, "Jawan",          "Action",      8.5, "jawan.jpg",   "Ashoka Theatre, "  + city);

        saveRestaurant(rRepo, "Rjy Rose Milk",     "Beverage",     "Main Road, "       + city, 4.9, "rose_milk.jpg",
            Arrays.asList(new MenuItem("Rose Milk", 30, true), new MenuItem("Badam Milk", 40, false)));
        saveRestaurant(rRepo, "Ajanta Hotel",      "South Indian", "Main Road, "       + city, 4.5, "ajanta_hotel.jpg",
            Arrays.asList(new MenuItem("Veg Thali", 150, true), new MenuItem("Paneer Butter Masala", 220, false)));
        saveRestaurant(rRepo, "Sitara Coffee House","Cafe",         "Godavari Bund, "   + city, 4.2, "sitara_coffee.jpg",
            Arrays.asList(new MenuItem("Cappuccino", 120, true), new MenuItem("Sandwich", 150, false)));
        saveRestaurant(rRepo, "Udupi Akshaya",     "Tiffins",      "Tilak Road, "      + city, 4.6, "udupi_akshaya.jpg",
            Arrays.asList(new MenuItem("Ghee Karam Dosa", 90, true), new MenuItem("Idli", 50, false)));
        saveRestaurant(rRepo, "JK Restaurant",     "Multi-Cuisine","AVA Road, "        + city, 4.1, "jk_restaurant.jpg",
            Arrays.asList(new MenuItem("Chicken Biryani", 280, true), new MenuItem("Apollo Fish", 350, false)));

        saveShop(shRepo, "Kandukuri Shopping Mall", "Clothing",   "Main Road, "  + city, 4.3, "kandukuri_mall.jpg");
        saveShop(shRepo, "Cherma's",                "Fashion",    "Main Road, "  + city, 4.4, "chermas.jpg");
        saveShop(shRepo, "Sudharshan Silks",        "Sarees",     "Fort Gate, "  + city, 4.6, "sudharshan_silks.jpg");
        saveShop(shRepo, "Bommana Bros",            "Electronics","Main Road, "  + city, 4.2, "bommana.jpg");
        saveShop(shRepo, "More Supermarket",        "Grocery",    "Tilak Road, " + city, 3.9, "more_supermarket.jpg");

        saveLocation(lRepo, "Godavari Arch Bridge",  "Godavari, "           + city, 4.8, "godavari_bridge.jpg");
        saveLocation(lRepo, "ISKCON Temple",          "Godavari Bund Road, " + city, 4.7, "iskcon_rjy.jpg");
        saveLocation(lRepo, "Papi Hills",             "Papi Kondalu, near "  + city, 4.9, "papi_hills.jpg");
        saveLocation(lRepo, "Cotton Museum",          "Dowleswaram, "        + city, 4.1, "cotton_museum.jpg");
        saveLocation(lRepo, "Maredumilli Forest",     "Maredumilli, near "   + city, 4.6, "maredumilli.jpg");

        saveActivity(aRepo, "Kayaking on Godavari",    "Godavari River, "       + city, 500,  4.6, "kayaking.jpg");
        saveActivity(aRepo, "Jet Skiing",              "Pichukalanka, "         + city, 800,  4.5, "jet_ski.jpg");
        saveActivity(aRepo, "Maredumilli Trekking",    "Maredumilli Forest, near " + city, 1200, 4.7, "maredumilli_trek.jpg");
        saveActivity(aRepo, "Bird Watching",           "Coringa Sanctuary, near " + city, 100, 4.4, "coringa.jpg");
        saveActivity(aRepo, "Heritage Walk",           "Old Town, "             + city, 50,   4.0, "heritage_walk_rjy.jpg");

        saveEvent(eRepo, "Godavari Maha Pushkaram", "Pushkar Ghat, "       + city, eventDate, 0,   "pushkaram.jpg");
        saveEvent(eRepo, "NTR Beach Festival",      "Kakinada Beach, near " + city, eventDate, 100, "beach_festival.jpg");
        saveEvent(eRepo, "Dasara Utsavalu",         "Devi Chowk, "         + city, eventDate, 0,   "dasara_rjy.jpg");
        saveEvent(eRepo, "Horticulture Show",       "Gowthami Ghat, "      + city, eventDate, 50,  "horticulture_show.jpg");
        saveEvent(eRepo, "Food Festival",           "Arts College Ground, " + city, eventDate, 200, "food_festival_rjy.jpg");
    }

    private void addHyderabadData(MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
                                   ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo,
                                   LocalDateTime eventDate) {
        String city = "Hyderabad";
        saveMovie(mRepo, "Salaar",        "Action",      8.8, "salaar.jpg",      "Prasads IMAX, "       + city);
        saveMovie(mRepo, "Cyberfall 2025","Cyberpunk",   9.0, "cyberfall.jpg",   "AMB Cinemas, "        + city);
        saveMovie(mRepo, "Animal",        "Crime Drama", 8.5, "animal.jpg",      "PVR, RK Cineplex, "   + city);
        saveMovie(mRepo, "RRR",           "Action",      9.2, "rrr.jpg",         "INOX, GVK One, "      + city);
        saveMovie(mRepo, "Sita Ramam",    "Romance",     8.9, "sita_ramam.jpg",  "Asian Cinemas, "      + city);

        saveRestaurant(rRepo, "Paradise Biryani", "Mughlai",     "Secunderabad, " + city, 4.8, "hyderabad_biryani.jpg",
            Arrays.asList(new MenuItem("Chicken Biryani", 380, true), new MenuItem("Mutton Biryani", 480, false)));
        saveRestaurant(rRepo, "Gokul Chaat",      "Street Food", "Koti, "         + city, 4.7, "gokul_chaat.jpg",
            Arrays.asList(new MenuItem("Samosa Chaat", 100, true), new MenuItem("Kulfi", 80, true)));
        saveRestaurant(rRepo, "Chutneys",         "South Indian","Banjara Hills, "+ city, 4.5, "chutneys.jpg",
            Arrays.asList(new MenuItem("Guntur Idli", 120, true), new MenuItem("Steam Dosa", 150, false)));
        saveRestaurant(rRepo, "Pista House",      "Mughlai",     "Tolichowki, "   + city, 4.6, "pista_house.jpg",
            Arrays.asList(new MenuItem("Haleem", 250, true), new MenuItem("Mandi", 800, false)));
        saveRestaurant(rRepo, "Ohri's Jiva",      "Fine Dining", "Banjara Hills, "+ city, 4.4, "ohris.jpg",
            Arrays.asList(new MenuItem("Buffet", 1200, true), new MenuItem("Paneer Tikka", 400, false)));

        saveShop(shRepo, "Laad Bazaar",     "Bangles",          "Charminar, "  + city, 4.5, "laad_bazaar.jpg");
        saveShop(shRepo, "IKEA",            "Furniture",        "HITEC City, " + city, 4.6, "ikea.jpg");
        saveShop(shRepo, "Inorbit Mall",    "Mall",             "Madhapur, "   + city, 4.7, "inorbit_mall.jpg");
        saveShop(shRepo, "Karachi Bakery",  "Bakery",           "Nampally, "   + city, 4.8, "karachi_bakery.jpg");
        saveShop(shRepo, "Koti Sultan Bazar","Books/Electronics","Koti, "      + city, 4.2, "koti_bazar.jpg");

        saveLocation(lRepo, "Charminar",           "Old City, "    + city, 4.7, "charminar.jpg");
        saveLocation(lRepo, "Ramoji Film City",     "Anaspur, "     + city, 4.6, "ramoji_film_city.jpg");
        saveLocation(lRepo, "Golconda Fort",        "Khairtabad, "  + city, 4.8, "golconda_fort.jpg");
        saveLocation(lRepo, "Hussain Sagar",        "Tank Bund, "   + city, 4.5, "hussain_sagar.jpg");
        saveLocation(lRepo, "Salar Jung Museum",    "Afzal Gunj, "  + city, 4.6, "salar_jung.jpg");

        saveActivity(aRepo, "Go Karting",                 "Shamirpet, "          + city, 600,  4.4, "go_karting.jpg");
        saveActivity(aRepo, "Wonderla Amusement Park",    "Outer Ring Road, "    + city, 1500, 4.7, "wonderla.jpg");
        saveActivity(aRepo, "Boating at Lumbini Park",    "Hussain Sagar, "      + city, 300,  4.2, "lumbini_park.jpg");
        saveActivity(aRepo, "Snow World",                 "Lower Tank Bund, "    + city, 500,  4.1, "snow_world.jpg");
        saveActivity(aRepo, "Heritage Walk in Old City",  "Charminar Area, "     + city, 100,  4.5, "heritage_walk_hyd.jpg");

        saveEvent(eRepo, "Hyderabad Literary Festival","Vidyaranya High School, " + city, eventDate, 100,  "lit_fest.jpg");
        saveEvent(eRepo, "Sunburn Festival",           "GMR Arena, "              + city, eventDate, 3000, "sunburn_hyd.jpg");
        saveEvent(eRepo, "Numaish Exhibition",         "Nampally, "               + city, eventDate, 50,   "numaish.jpg");
        saveEvent(eRepo, "Bathukamma Festival",        "Tank Bund, "              + city, eventDate, 0,    "bathukamma.jpg");
        saveEvent(eRepo, "NH7 Weekender",              "Gachibowli Stadium, "     + city, eventDate, 2500, "nh7_hyd.jpg");
    }

    private void addVijayawadaData(MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
                                   ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo,
                                   LocalDateTime eventDate) {
        String city = "Vijayawada";
        saveMovie(mRepo, "Pushpa 2",                "Action",           9.3, "pushpa2.jpg",      "PVR Trendset Mall, "    + city);
        saveMovie(mRepo, "Game Changer",            "Political Thriller",8.8, "game_changer.jpg","INOX, LEPL Center, "    + city);
        saveMovie(mRepo, "Leo",                     "Action",           8.5, "leo.jpg",          "Cinepolis, "            + city);
        saveMovie(mRepo, "Jigra",                   "Drama",            8.0, "jigra.jpg",        "PVR Capital Mall, "     + city);
        saveMovie(mRepo, "The Greatest of All Time","Sci-Fi",           8.9, "goat.jpg",         "INOX, Ripples Mall, "   + city);

        saveRestaurant(rRepo, "Sweet Magic",  "Bakery",      "Benz Circle, "  + city, 4.7, "sweet_magic.jpg",
            Arrays.asList(new MenuItem("Butterscotch Pastry", 100, true), new MenuItem("Paneer Roll", 80, false)));
        saveRestaurant(rRepo, "Idly N Dosa",  "South Indian","Governorpet, "  + city, 4.5, "idly_dosa.jpg",
            Arrays.asList(new MenuItem("Ghee Dosa", 90, true), new MenuItem("Pesarattu", 70, false)));
        saveRestaurant(rRepo, "Babai Hotel",  "Tiffins",     "Gandhi Nagar, " + city, 4.6, "babai_hotel.jpg",
            Arrays.asList(new MenuItem("Idli with Chutney", 60, true), new MenuItem("Vada", 40, false)));
        saveRestaurant(rRepo, "Rasoie",       "North Indian","Benz Circle, "  + city, 4.3, "rasoie.jpg",
            Arrays.asList(new MenuItem("Paneer Butter Masala", 220, true), new MenuItem("Butter Naan", 60, false)));
        saveRestaurant(rRepo, "Temptations",  "Cafe",        "Labbipet, "     + city, 4.2, "temptations.jpg",
            Arrays.asList(new MenuItem("Cold Coffee", 120, true), new MenuItem("Veg Burger", 180, false)));

        saveShop(shRepo, "Trendset Mall",            "Mall",     "Moghalrajpuram, " + city, 4.5, "trendset_mall.jpg");
        saveShop(shRepo, "Croma",                    "Electronics","Benz Circle, "  + city, 4.4, "croma_vijayawada.jpg");
        saveShop(shRepo, "Big Bazaar",               "Retail",   "Labbipet, "       + city, 4.2, "bigbazaar_vja.jpg");
        saveShop(shRepo, "South India Shopping Mall","Clothing",  "M.G. Road, "     + city, 4.3, "southindia_mall_vja.jpg");
        saveShop(shRepo, "KLM Fashion Mall",         "Fashion",  "M.G. Road, "     + city, 4.6, "klm_vja.jpg");

        saveLocation(lRepo, "Prakasam Barrage",  "Krishna River, "    + city, 4.7, "prakasam_barrage.jpg");
        saveLocation(lRepo, "Bhavani Island",    "Krishna River, "    + city, 4.8, "bhavani_island.jpg");
        saveLocation(lRepo, "Kanaka Durga Temple","Indrakeeladri Hills, "+ city, 4.9, "kanaka_durga.jpg");
        saveLocation(lRepo, "Rajiv Gandhi Park", "River Front, "      + city, 4.3, "rajiv_gandhi_park.jpg");
        saveLocation(lRepo, "Undavalli Caves",   "Near Guntur, "      + city, 4.6, "undavalli_caves.jpg");

        saveActivity(aRepo, "Boating at Bhavani Island","Bhavani Island, "           + city, 500, 4.5, "boating_bhavani.jpg");
        saveActivity(aRepo, "Ziplining",               "Bhavani Adventure Zone, "   + city, 700, 4.4, "zipline_vja.jpg");
        saveActivity(aRepo, "Trekking at Kondapalli",  "Kondapalli, near "          + city, 400, 4.3, "kondapalli_trek.jpg");
        saveActivity(aRepo, "Cycling on Riverfront",   "MG Road, "                  + city, 150, 4.2, "cycling_vja.jpg");
        saveActivity(aRepo, "Street Food Walk",        "Benz Circle, "              + city, 100, 4.6, "streetfood_vja.jpg");

        saveEvent(eRepo, "Krishna Pushkaralu",  "River Ghat, "       + city, eventDate, 0,   "pushkaralu_vja.jpg");
        saveEvent(eRepo, "Vijayawada Food Fest","PVP Mall Grounds, " + city, eventDate, 100, "foodfest_vja.jpg");
        saveEvent(eRepo, "Music Marathon",      "IGMC Stadium, "     + city, eventDate, 200, "musicmarathon_vja.jpg");
        saveEvent(eRepo, "Cultural Dance Night","KBR Hall, "         + city, eventDate, 150, "dance_vja.jpg");
        saveEvent(eRepo, "Tech Expo",           "Convention Center, "+ city, eventDate, 300, "tech_expo_vja.jpg");
    }

    private void addGunturData(MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
                               ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo,
                               LocalDateTime eventDate) {
        String city = "Guntur";
        saveMovie(mRepo, "Lucky Baskhar","Drama",  8.6, "lucky_baskhar.jpg",  "PVR NTR Mall, "      + city);
        saveMovie(mRepo, "Kushi",        "Romance",8.4, "kushi.jpg",          "INOX, Naaz Centre, " + city);
        saveMovie(mRepo, "Tillu Square", "Comedy", 8.8, "tillu_square.jpg",   "Hari Hara Cinemas, " + city);
        saveMovie(mRepo, "Salaar",       "Action", 9.0, "salaar_guntur.jpg",  "Miraj Cinemas, "     + city);
        saveMovie(mRepo, "Devara",       "Action", 8.7, "devara_guntur.jpg",  "PVR City Centre, "   + city);

        saveRestaurant(rRepo, "Naidu Gari Kunda Biryani","Andhra",     "Brodipet, "       + city, 4.5, "naidu_biryani.jpg",
            Arrays.asList(new MenuItem("Chicken Biryani", 300, true), new MenuItem("Fish Fry", 180, false)));
        saveRestaurant(rRepo, "Sri Ram Tiffins",         "South Indian","Lakshmipuram, "  + city, 4.4, "sriram_tiffins.jpg",
            Arrays.asList(new MenuItem("Masala Dosa", 90, true), new MenuItem("Vada", 40, false)));
        saveRestaurant(rRepo, "Beans & Bites",           "Cafe",        "Arundelpet, "    + city, 4.2, "beans_bites.jpg",
            Arrays.asList(new MenuItem("Mocha", 120, true), new MenuItem("French Fries", 100, false)));
        saveRestaurant(rRepo, "Barkaas Arabic Restaurant","Arabic",     "Ring Road, "     + city, 4.7, "barkaas_guntur.jpg",
            Arrays.asList(new MenuItem("Mandi", 800, true), new MenuItem("Shawarma", 150, false)));
        saveRestaurant(rRepo, "New Taj",                 "Indian",      "Lakshmipuram, "  + city, 4.3, "new_taj.jpg",
            Arrays.asList(new MenuItem("Paneer Curry", 180, true), new MenuItem("Butter Naan", 60, false)));

        saveShop(shRepo, "City Centre Mall",   "Mall",     "Lakshmipuram, "       + city, 4.4, "citycentre_guntur.jpg");
        saveShop(shRepo, "Reliance Trends",    "Clothing", "Brodipet, "           + city, 4.5, "reliance_trends_guntur.jpg");
        saveShop(shRepo, "More Megastore",     "Grocery",  "Guntur Main Road, "   + city, 4.2, "more_guntur.jpg");
        saveShop(shRepo, "Bata",               "Footwear", "Lakshmipuram, "       + city, 4.1, "bata_guntur.jpg");
        saveShop(shRepo, "Spar Hypermarket",   "Retail",   "Inner Ring Road, "    + city, 4.3, "spar_guntur.jpg");

        saveLocation(lRepo, "Uppalapadu Bird Sanctuary","Uppalapadu, near "      + city, 4.6, "uppalapadu.jpg");
        saveLocation(lRepo, "Kondaveedu Fort",           "Kondaveedu Hills, near " + city, 4.7, "kondaveedu_fort.jpg");
        saveLocation(lRepo, "Sri Amaravati Stupa",       "Amaravati, near "       + city, 4.8, "amaravati_stupa.jpg");
        saveLocation(lRepo, "Guntur Market",             "Brodipet, "             + city, 4.0, "guntur_market.jpg");
        saveLocation(lRepo, "Ethipothala Falls",         "Near Nagarjuna Sagar, " + city, 4.9, "ethipothala_falls.jpg");

        saveActivity(aRepo, "Bird Photography","Uppalapadu Sanctuary, "  + city, 300, 4.6, "birdwatching_guntur.jpg");
        saveActivity(aRepo, "Heritage Walk",   "Amaravati, near "         + city, 200, 4.5, "heritage_walk_guntur.jpg");
        saveActivity(aRepo, "Trekking",        "Kondaveedu Hills, near "  + city, 400, 4.4, "trekking_guntur.jpg");
        saveActivity(aRepo, "Cycling",         "Inner Ring Road, "        + city, 150, 4.2, "cycling_guntur.jpg");
        saveActivity(aRepo, "Shopping Tour",   "Brodipet, "               + city, 50,  4.1, "shopping_guntur.jpg");

        saveEvent(eRepo, "Amaravati Music Fest","Amaravati Riverfront, " + city, eventDate, 250, "musicfest_guntur.jpg");
        saveEvent(eRepo, "Flower Show",          "Konda Park, "          + city, eventDate, 100, "flowershow_guntur.jpg");
        saveEvent(eRepo, "Tech Carnival",        "Vignan Grounds, "      + city, eventDate, 200, "techcarnival_guntur.jpg");
        saveEvent(eRepo, "Food & Culture Fest",  "Brodipet, "            + city, eventDate, 150, "foodculture_guntur.jpg");
        saveEvent(eRepo, "Spiritual Walk",       "Amaravati Stupa, "     + city, eventDate, 0,   "spiritual_walk.jpg");
    }

    private void addChennaiData(MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
                                ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo,
                                LocalDateTime eventDate) {
        String city = "Chennai";
        saveMovie(mRepo, "Vettaiyan",              "Action",      9.1, "vettaiyan.jpg",      "PVR Grand Mall, "              + city);
        saveMovie(mRepo, "Jigra",                  "Drama",       8.6, "jigra_chennai.jpg",  "AGS Cinemas, Navalur, "        + city);
        saveMovie(mRepo, "The Greatest of All Time","Sci-Fi",     8.9, "goat_chennai.jpg",   "INOX, Phoenix Market City, "   + city);
        saveMovie(mRepo, "Leo",                    "Action Thriller",8.7,"leo_chennai.jpg",  "SPI Palazzo, Vadapalani, "     + city);
        saveMovie(mRepo, "Kanguva",                "Epic Action", 9.0, "kanguva_chennai.jpg","Luxe Cinemas, "                + city);

        saveRestaurant(rRepo, "Murugan Idli Shop",         "South Indian","T. Nagar, "      + city, 4.7, "murugan_idli.jpg",
            Arrays.asList(new MenuItem("Idli", 60, true), new MenuItem("Podi Dosa", 100, false)));
        saveRestaurant(rRepo, "The Marina",                "Seafood",    "Santhome, "       + city, 4.6, "the_marina.jpg",
            Arrays.asList(new MenuItem("Grilled Prawns", 450, true), new MenuItem("Fish Curry Meal", 350, false)));
        saveRestaurant(rRepo, "Annalakshmi",               "Vegetarian", "Egmore, "         + city, 4.8, "annalakshmi.jpg",
            Arrays.asList(new MenuItem("Thali Meal", 250, true), new MenuItem("Paneer Curry", 200, false)));
        saveRestaurant(rRepo, "Coal Barbecues",            "Buffet",     "Velachery, "      + city, 4.5, "coal_bbq.jpg",
            Arrays.asList(new MenuItem("BBQ Chicken", 300, true), new MenuItem("Grilled Veg", 250, false)));
        saveRestaurant(rRepo, "Sandy's Chocolate Laboratory","Desserts", "Nungambakkam, "  + city, 4.4, "sandys.jpg",
            Arrays.asList(new MenuItem("Chocolate Mousse", 180, true), new MenuItem("Brownie Shake", 220, false)));

        saveShop(shRepo, "Phoenix Market City","Mall",    "Velachery, " + city, 4.7, "phoenix_chennai.jpg");
        saveShop(shRepo, "Express Avenue",     "Mall",    "Royapettah, "+ city, 4.6, "express_avenue.jpg");
        saveShop(shRepo, "Spencer Plaza",      "Mall",    "Anna Salai, "+ city, 4.3, "spencer_plaza.jpg");
        saveShop(shRepo, "Pothys",             "Clothing","T. Nagar, "  + city, 4.5, "pothys.jpg");
        saveShop(shRepo, "Chennai Silks",      "Textiles","T. Nagar, "  + city, 4.6, "chennai_silks.jpg");

        saveLocation(lRepo, "Marina Beach",          "Santhome, "        + city, 4.8, "marina_beach.jpg");
        saveLocation(lRepo, "VGP Universal Kingdom", "East Coast Road, " + city, 4.5, "vgp.jpg");
        saveLocation(lRepo, "Elliot's Beach",        "Besant Nagar, "    + city, 4.7, "elliots.jpg");
        saveLocation(lRepo, "Fort St. George",       "George Town, "     + city, 4.3, "fort_st_george.jpg");
        saveLocation(lRepo, "Kapaleeshwarar Temple", "Mylapore, "        + city, 4.9, "kapaleeshwarar_temple.jpg");

        saveActivity(aRepo, "Surfing",           "Covelong Beach, "  + city, 600, 4.6, "surfing_covelong.jpg");
        saveActivity(aRepo, "Go Karting",        "ECR Speedway, "    + city, 800, 4.5, "go_karting.jpg");
        saveActivity(aRepo, "Museum Visit",      "Egmore Museum, "   + city, 100, 4.4, "museum_visit.jpg");
        saveActivity(aRepo, "Beach Volleyball",  "Elliot's Beach, "  + city, 150, 4.3, "volleyball_elliots.jpg");
        saveActivity(aRepo, "Cycling by the Sea","Marina Loop, "     + city, 200, 4.6, "cycling_marina.jpg");

        saveEvent(eRepo, "Chennai Music Festival","Various Auditoriums, "+ city, eventDate, 200, "musicfest_chennai.jpg");
        saveEvent(eRepo, "Chennai Food Festival", "Island Grounds, "    + city, eventDate, 150, "foodfest_chennai.jpg");
        saveEvent(eRepo, "Book Fair",             "Nandanam Grounds, "  + city, eventDate, 50,  "bookfair_chennai.jpg");
        saveEvent(eRepo, "Tech Summit",           "Trade Centre, "      + city, eventDate, 300, "techsummit_chennai.jpg");
        saveEvent(eRepo, "Cultural Night",        "Vani Mahal, "        + city, eventDate, 100, "culturalnight_chennai.jpg");
    }

    private void addBangaloreData(MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
                                  ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo,
                                  LocalDateTime eventDate) {
        String city = "Bangalore";
        saveMovie(mRepo, "Devara",   "Action", 8.9, "devara_blr.jpg",    "PVR Orion Mall, "      + city);
        saveMovie(mRepo, "Salaar",   "Action", 9.0, "salaar_blr.jpg",    "INOX, Forum Mall, "    + city);
        saveMovie(mRepo, "Kantara",  "Drama",  9.2, "kantara_blr.jpg",   "Cinepolis, Nexus Mall, "+ city);
        saveMovie(mRepo, "Vettaiyan","Thriller",8.7, "vettaiyan_blr.jpg","PVR Koramangala, "     + city);
        saveMovie(mRepo, "Jawan",    "Action", 8.6, "jawan_blr.jpg",     "INOX Garuda Mall, "    + city);

        saveRestaurant(rRepo, "MTR",          "South Indian","Lalbagh Road, "  + city, 4.8, "mtr.jpg",
            Arrays.asList(new MenuItem("Rava Idli", 90, true), new MenuItem("Masala Dosa", 120, false)));
        saveRestaurant(rRepo, "Truffles",     "Cafe",        "Koramangala, "   + city, 4.6, "truffles.jpg",
            Arrays.asList(new MenuItem("Beef Burger", 280, true), new MenuItem("Choco Lava", 150, false)));
        saveRestaurant(rRepo, "Empire",       "Biryani",     "Church Street, " + city, 4.5, "empire.jpg",
            Arrays.asList(new MenuItem("Chicken Kabab", 250, true), new MenuItem("Mutton Biryani", 320, false)));
        saveRestaurant(rRepo, "Toit",         "Brewery",     "Indiranagar, "   + city, 4.7, "toit.jpg",
            Arrays.asList(new MenuItem("Craft Beer", 350, true), new MenuItem("Nachos", 220, false)));
        saveRestaurant(rRepo, "The Only Place","Continental","Museum Road, "   + city, 4.4, "only_place.jpg",
            Arrays.asList(new MenuItem("Steak", 500, true), new MenuItem("Spaghetti", 300, false)));

        saveShop(shRepo, "UB City",           "Luxury Mall","Vittal Mallya Road, "+ city, 4.8, "ubcity.jpg");
        saveShop(shRepo, "Orion Mall",        "Mall",       "Rajajinagar, "       + city, 4.6, "orion_blr.jpg");
        saveShop(shRepo, "Commercial Street", "Market",     "Central, "           + city, 4.5, "commercial_street.jpg");
        saveShop(shRepo, "Phoenix Marketcity","Mall",       "Whitefield, "        + city, 4.7, "phoenix_blr.jpg");
        saveShop(shRepo, "Garuda Mall",       "Mall",       "Ashok Nagar, "       + city, 4.4, "garuda_blr.jpg");

        saveLocation(lRepo, "Lalbagh Botanical Garden","South Bangalore, "+ city, 4.8, "lalbagh.jpg");
        saveLocation(lRepo, "Cubbon Park",              "MG Road, "        + city, 4.7, "cubbon_park.jpg");
        saveLocation(lRepo, "Bangalore Palace",         "Vasanth Nagar, "  + city, 4.6, "bangalore_palace.jpg");
        saveLocation(lRepo, "ISKCON Temple",            "Rajajinagar, "    + city, 4.9, "iskcon_blr.jpg");
        saveLocation(lRepo, "Wonderla",                 "Mysore Road, "    + city, 4.8, "wonderla.jpg");

        saveActivity(aRepo, "Go Karting",   "Meco Kartopia, "    + city, 800,  4.5, "gokart_blr.jpg");
        saveActivity(aRepo, "Sky Diving",   "Jakkur Aerodrome, " + city, 4500, 4.8, "skydive_blr.jpg");
        saveActivity(aRepo, "VR Gaming",    "Koramangala, "       + city, 400,  4.4, "vrgaming_blr.jpg");
        saveActivity(aRepo, "Rock Climbing","Play Arena, "        + city, 500,  4.5, "rockclimb_blr.jpg");
        saveActivity(aRepo, "Cycling Tour", "Cubbon Park, "       + city, 200,  4.3, "cycling_blr.jpg");

        saveEvent(eRepo, "Comic Con",   "KTPO, Whitefield, "  + city, eventDate, 250, "comiccon_blr.jpg");
        saveEvent(eRepo, "Beer Fest",   "Toit, Indiranagar, " + city, eventDate, 300, "beerfest_blr.jpg");
        saveEvent(eRepo, "Startup Expo","NIMHANS, "           + city, eventDate, 150, "startupexpo_blr.jpg");
        saveEvent(eRepo, "Food Carnival","Palace Grounds, "   + city, eventDate, 200, "foodcarnival_blr.jpg");
        saveEvent(eRepo, "Marathon",    "Cubbon Park, "       + city, eventDate, 100, "marathon_blr.jpg");
    }

    private void addNewDelhiData(MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
                                 ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo,
                                 LocalDateTime eventDate) {
        String city = "New Delhi";
        saveMovie(mRepo, "Jawan",   "Action",      8.9, "jawan_delhi.jpg",  "PVR Select City Walk, " + city);
        saveMovie(mRepo, "Animal",  "Drama",        8.8, "animal_delhi.jpg", "INOX, Connaught Place, " + city);
        saveMovie(mRepo, "Tiger 3", "Action",       8.5, "tiger3_delhi.jpg", "Cinepolis, Janakpuri, "  + city);
        saveMovie(mRepo, "Pathaan", "Action",       8.6, "pathaan_delhi.jpg","PVR Ambience Mall, "     + city);
        saveMovie(mRepo, "Dunki",   "Comedy Drama", 8.3, "dunki_delhi.jpg",  "INOX Nehru Place, "      + city);

        saveRestaurant(rRepo, "Karim's",         "Mughlai",    "Jama Masjid, "       + city, 4.8, "karims.jpg",
            Arrays.asList(new MenuItem("Mutton Korma", 350, true), new MenuItem("Seekh Kebab", 250, false)));
        saveRestaurant(rRepo, "Saravana Bhavan", "South Indian","Connaught Place, "  + city, 4.6, "saravana_cp.jpg",
            Arrays.asList(new MenuItem("Masala Dosa", 120, true), new MenuItem("Filter Coffee", 70, false)));
        saveRestaurant(rRepo, "Bukhara",         "North Indian","ITC Maurya, "       + city, 4.9, "bukhara.jpg",
            Arrays.asList(new MenuItem("Dal Bukhara", 500, true), new MenuItem("Butter Naan", 100, false)));
        saveRestaurant(rRepo, "Indian Accent",   "Fine Dining", "The Lodhi, "        + city, 4.9, "indian_accent.jpg",
            Arrays.asList(new MenuItem("Lamb Curry", 600, true), new MenuItem("Khichdi", 300, false)));
        saveRestaurant(rRepo, "Social Offline",  "Cafe",        "Hauz Khas, "        + city, 4.5, "social_delhi.jpg",
            Arrays.asList(new MenuItem("Pasta", 250, true), new MenuItem("Pizza", 300, false)));

        saveShop(shRepo, "Select City Walk",    "Mall",         "Saket, "       + city, 4.7, "selectcitywalk.jpg");
        saveShop(shRepo, "DLF Promenade",       "Mall",         "Vasant Kunj, " + city, 4.6, "dlfpromenade.jpg");
        saveShop(shRepo, "Connaught Place",     "Market",       "Central Delhi, "+ city, 4.8, "cp_delhi.jpg");
        saveShop(shRepo, "Sarojini Nagar Market","Fashion",     "South Delhi, " + city, 4.4, "sarojini.jpg");
        saveShop(shRepo, "Khan Market",         "Luxury Retail","Central Delhi, "+ city, 4.7, "khanmarket.jpg");

        saveLocation(lRepo, "India Gate",      "Central Delhi, "+ city, 4.8, "indiagate.jpg");
        saveLocation(lRepo, "Red Fort",        "Old Delhi, "    + city, 4.7, "redfort.jpg");
        saveLocation(lRepo, "Qutub Minar",     "Mehrauli, "     + city, 4.8, "qutubminar.jpg");
        saveLocation(lRepo, "Lotus Temple",    "Kalkaji, "      + city, 4.7, "lotustemple.jpg");
        saveLocation(lRepo, "Humayun's Tomb",  "Nizamuddin, "   + city, 4.7, "humayunstomb.jpg");

        saveActivity(aRepo, "Heritage Walk",     "Old Delhi, "     + city, 200,  4.5, "heritagewalk_delhi.jpg");
        saveActivity(aRepo, "Hot Air Balloon",   "Sohna Road, "    + city, 1200, 4.6, "hotair_delhi.jpg");
        saveActivity(aRepo, "Photography Tour",  "India Gate, "    + city, 300,  4.4, "photo_delhi.jpg");
        saveActivity(aRepo, "Street Food Trail", "Chandni Chowk, " + city, 250,  4.8, "foodtrail_delhi.jpg");
        saveActivity(aRepo, "Cycling",           "Rajpath, "       + city, 150,  4.3, "cycling_delhi.jpg");

        saveEvent(eRepo, "Auto Expo",           "India Expo Mart, " + city, eventDate, 300, "autoexpo_delhi.jpg");
        saveEvent(eRepo, "Book Fair",           "Pragati Maidan, "  + city, eventDate, 100, "bookfair_delhi.jpg");
        saveEvent(eRepo, "Food Fest",           "Connaught Place, " + city, eventDate, 150, "foodfest_delhi.jpg");
        saveEvent(eRepo, "Cultural Parade",     "Rajpath, "         + city, eventDate, 0,   "parade_delhi.jpg");
        saveEvent(eRepo, "Startup India Summit","ITPO Grounds, "    + city, eventDate, 200, "startup_delhi.jpg");
    }

    private void addMumbaiData(MovieRepository mRepo, RestaurantRepository rRepo, ShopRepository shRepo,
                               ActivityRepository aRepo, EventRepository eRepo, LocationRepository lRepo,
                               LocalDateTime eventDate) {
        String city = "Mumbai";
        saveMovie(mRepo, "Brahmastra","Fantasy",      8.5, "brahmastra.jpg",     "PVR Phoenix Mall, "      + city);
        saveMovie(mRepo, "Pathaan",   "Action",       8.6, "pathaan_mumbai.jpg", "INOX R-City Mall, "      + city);
        saveMovie(mRepo, "Animal",    "Thriller",     9.0, "animal_mumbai.jpg",  "Cinepolis, Andheri, "    + city);
        saveMovie(mRepo, "Dunki",     "Comedy Drama", 8.4, "dunki_mumbai.jpg",   "Carnival Cinemas, Bandra, "+ city);
        saveMovie(mRepo, "Jawan",     "Action",       8.9, "jawan_mumbai.jpg",   "PVR Lower Parel, "       + city);

        saveRestaurant(rRepo, "Leopold Café",       "Continental","Colaba, "        + city, 4.6, "leopold.jpg",
            Arrays.asList(new MenuItem("Club Sandwich", 250, true), new MenuItem("Beer Pint", 300, false)));
        saveRestaurant(rRepo, "Bademiya",           "Mughlai",    "Colaba, "        + city, 4.7, "bademiya.jpg",
            Arrays.asList(new MenuItem("Seekh Kebab", 280, true), new MenuItem("Biryani", 300, false)));
        saveRestaurant(rRepo, "Britannia & Co.",    "Parsi",      "Ballard Estate, "+ city, 4.5, "britannia.jpg",
            Arrays.asList(new MenuItem("Berry Pulao", 350, true), new MenuItem("Dhansak", 300, false)));
        saveRestaurant(rRepo, "The Table",          "Fine Dining","Colaba, "        + city, 4.8, "thetable.jpg",
            Arrays.asList(new MenuItem("Truffle Fries", 400, true), new MenuItem("Salmon", 600, false)));
        saveRestaurant(rRepo, "Haji Ali Juice Centre","Juice Bar", "Haji Ali, "     + city, 4.3, "hajiali.jpg",
            Arrays.asList(new MenuItem("Sitaphal Cream", 150, true), new MenuItem("Falooda", 180, false)));

        saveShop(shRepo, "Phoenix Marketcity",  "Mall",          "Kurla, "       + city, 4.7, "phoenix_mumbai.jpg");
        saveShop(shRepo, "High Street Phoenix", "Mall",          "Lower Parel, " + city, 4.6, "highstreet_phoenix.jpg");
        saveShop(shRepo, "Linking Road Market", "Fashion",       "Bandra, "      + city, 4.5, "linkingroad.jpg");
        saveShop(shRepo, "Colaba Causeway",     "Street Shopping","Colaba, "     + city, 4.4, "colaba_causeway.jpg");
        saveShop(shRepo, "R City Mall",         "Mall",          "Ghatkopar, "   + city, 4.5, "rcity_mumbai.jpg");

        saveLocation(lRepo, "Gateway of India",           "Colaba, "            + city, 4.9, "gateway.jpg");
        saveLocation(lRepo, "Marine Drive",               "South Mumbai, "      + city, 4.8, "marinedrive.jpg");
        saveLocation(lRepo, "Juhu Beach",                 "Juhu, "              + city, 4.6, "juhubeach.jpg");
        saveLocation(lRepo, "Sanjay Gandhi National Park","Borivali, "          + city, 4.7, "sgnp.jpg");
        saveLocation(lRepo, "Elephanta Caves",            "Gharapuri Island, near "+ city, 4.6, "elephanta.jpg");

        saveActivity(aRepo, "Boat Ride",       "Gateway of India, "       + city, 300,  4.5, "boatride_mumbai.jpg");
        saveActivity(aRepo, "Scuba Diving",    "Malvan, near "            + city, 2500, 4.8, "scuba_mumbai.jpg");
        saveActivity(aRepo, "Night Cycling",   "Marine Drive, "           + city, 400,  4.7, "nightcycle_mumbai.jpg");
        saveActivity(aRepo, "Film Studio Tour","Film City, Goregaon, "    + city, 800,  4.4, "filmcity_tour.jpg");
        saveActivity(aRepo, "Beach Volleyball","Juhu Beach, "             + city, 200,  4.3, "beachvolley_mumbai.jpg");

        saveEvent(eRepo, "Kala Ghoda Festival","Kala Ghoda, "     + city, eventDate, 100, "kalaghoda.jpg");
        saveEvent(eRepo, "Mumbai Marathon",    "South Mumbai, "   + city, eventDate, 200, "marathon_mumbai.jpg");
        saveEvent(eRepo, "Bollywood Nights",   "Bandra, "         + city, eventDate, 300, "bollywoodnights.jpg");
        saveEvent(eRepo, "Food Truck Festival","BKC Grounds, "    + city, eventDate, 150, "foodtruck_mumbai.jpg");
        saveEvent(eRepo, "Navratri Utsav",     "Goregaon Exhibition Grounds, " + city, eventDate, 200, "navratri_mumbai.jpg");
    }

    // ========= Helper Methods =========

    private void saveMovie(MovieRepository repo, String title, String genre, double rating,
                           String filename, String location) {
        Movie movie = new Movie(title, genre, rating, getImageUrl(filename), location);
        movie.setSeatingLayout(generateSeating('H', 15, title.hashCode()));
        repo.save(movie);
    }

    private void saveRestaurant(RestaurantRepository repo, String name, String type, String location,
                                double rating, String filename, List<MenuItem> menu) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setType(type);
        restaurant.setLocation(location);
        restaurant.setRating(rating);
        restaurant.setImage(getImageUrl(filename));
        restaurant.setMenu(menu);
        repo.save(restaurant);
    }

    private void saveShop(ShopRepository repo, String name, String category, String location,
                          double rating, String filename) {
        Shop shop = new Shop();
        shop.setName(name);
        shop.setCategory(category);
        shop.setLocation(location);
        shop.setRating(rating);
        shop.setImage(getImageUrl(filename));
        repo.save(shop);
    }

    private void saveLocation(LocationRepository repo, String name, String area, double rating, String filename) {
        Location location = new Location();
        location.setName(name);
        location.setArea(area);
        location.setRating(rating);
        location.setImage(getImageUrl(filename));
        repo.save(location);
    }

    private void saveActivity(ActivityRepository repo, String name, String place, double cost,
                              double rating, String filename) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setPlace(place);
        activity.setCost(cost);
        activity.setRating(rating);
        activity.setImage(getImageUrl(filename));
        activity.setAvailableSlots(generateTimeSlots(name));
        repo.save(activity);
    }

    private void saveEvent(EventRepository repo, String name, String venue, LocalDateTime date,
                           double price, String filename) {
        Event event = new Event();
        event.setName(name);
        event.setVenue(venue);
        event.setDate(date);
        event.setPrice(price);
        event.setImage(getImageUrl(filename));
        event.setAvailableSlots(generateTimeSlots(name));
        repo.save(event);
    }

    private List<Seat> generateSeating(char maxRow, int seatsPerRow, long seed) {
        Random rng = new Random(seed);
        List<Seat> layout = new ArrayList<>();
        for (char row = 'A'; row <= maxRow; row++) {
            for (int i = 1; i <= seatsPerRow; i++) {
                String status = rng.nextDouble() > 0.7 ? "BOOKED" : "AVAILABLE";
                layout.add(new Seat(String.valueOf(row), i, status));
            }
        }
        return layout;
    }

    private List<TimeSlot> generateTimeSlots(String seed) {
        Random rng = new Random((long) seed.hashCode());
        String[][] options = {
            {"09:00 AM", "11:00 AM"}, {"10:00 AM", "12:00 PM"},
            {"11:30 AM", "01:30 PM"}, {"01:00 PM", "03:00 PM"},
            {"02:30 PM", "04:30 PM"}, {"04:00 PM", "06:00 PM"}
        };
        Set<Integer> chosen = new LinkedHashSet<>();
        while (chosen.size() < 3) chosen.add(rng.nextInt(options.length));
        List<TimeSlot> slots = new ArrayList<>();
        for (int i : chosen) {
            int spots = 5 + rng.nextInt(20);
            slots.add(new TimeSlot(options[i][0] + " - " + options[i][1], spots));
        }
        return slots;
    }

    private String getImageUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) return "";
        ClassPathResource resource = new ClassPathResource("static/images/" + fileName);
        if (resource.exists()) {
            return "/images/" + fileName;
        }
        return "";
    }
}
