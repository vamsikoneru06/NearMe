package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(
            MovieRepository mRepo,
            FoodRepository fRepo,
            ShopRepository shRepo,
            ActivityRepository aRepo,
            EventRepository eRepo,
            LocationRepository lRepo
    ) {
        return args -> {
            mRepo.deleteAll();
            fRepo.deleteAll();
            shRepo.deleteAll();
            aRepo.deleteAll();
            eRepo.deleteAll();
            lRepo.deleteAll();

            // --- Data for Rajamahendravaram ---
            mRepo.save(new Movie("Kalki 2898-AD", "Sci-Fi", 9.1, encodeImage("kalki.jpg"), "Swaroop Theatre, Rajamahendravaram"));
            mRepo.save(new Movie("Jawan", "Action", 8.5, encodeImage("jawan.jpg"), "Ashoka Theatre, Rajamahendravaram"));
            Food rjyFood1 = new Food();
            rjyFood1.setName("Rose Milk"); rjyFood1.setType("Beverage"); rjyFood1.setPrice(30); rjyFood1.setRating(4.9); rjyFood1.setLocation("Main Road, Rajamahendravaram");
            rjyFood1.setImage(encodeImage("rose_milk.jpg"));
            fRepo.save(rjyFood1);
            Food rjyFood2 = new Food();
            rjyFood2.setName("Pootharekulu"); rjyFood2.setType("Sweet"); rjyFood2.setPrice(200); rjyFood2.setRating(4.8); rjyFood2.setLocation("Atreyapuram Sweets, Rajamahendravaram");
            rjyFood2.setImage(encodeImage("pootharekulu.jpg"));
            fRepo.save(rjyFood2);
            Shop rjyShop = new Shop();
            rjyShop.setName("Kandukuri Shopping Mall"); rjyShop.setCategory("Clothing"); rjyShop.setRating(4.3); rjyShop.setLocation("Main Road, Rajamahendravaram");
            rjyShop.setImage(encodeImage("kandukuri.jpg"));
            shRepo.save(rjyShop);
            Location rjyLoc1 = new Location();
            rjyLoc1.setName("Godavari Arch Bridge"); rjyLoc1.setArea("Godavari, Rajamahendravaram"); rjyLoc1.setRating(4.8); rjyLoc1.setImage(encodeImage("godavari_bridge.jpg"));
            lRepo.save(rjyLoc1);
            Location rjyLoc2 = new Location();
            rjyLoc2.setName("ISKCON Temple"); rjyLoc2.setArea("Godavari Bund Road, Rajamahendravaram"); rjyLoc2.setRating(4.7); rjyLoc2.setImage(encodeImage("iskcon_rjy.jpg"));
            lRepo.save(rjyLoc2);
            Activity rjyActivity = new Activity();
            rjyActivity.setName("Kayaking"); rjyActivity.setPlace("Godavari River, Rajamahendravaram"); rjyActivity.setCost(500); rjyActivity.setRating(4.6);
            rjyActivity.setImage(encodeImage("kayaking.jpg"));
            aRepo.save(rjyActivity);
            Event rjyEvent = new Event();
            rjyEvent.setName("Godavari Maha Pushkaram"); rjyEvent.setVenue("Pushkar Ghat, Rajamahendravaram"); rjyEvent.setDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(45))); rjyEvent.setPrice(0);
            rjyEvent.setImage(encodeImage("pushkaram.jpg"));
            eRepo.save(rjyEvent);

            // --- Data for Hyderabad ---
            mRepo.save(new Movie("Salaar", "Action", 8.8, encodeImage("salaar.jpg"), "Prasads IMAX, Hyderabad"));
            mRepo.save(new Movie("Cyberfall 2025", "Cyberpunk", 9.0, encodeImage("cyberfall.jpg"), "AMB Cinemas, Hyderabad"));
            Food foodH1 = new Food();
            foodH1.setName("Hyderabadi Biryani"); foodH1.setType("Non-Veg"); foodH1.setPrice(350); foodH1.setRating(4.8); foodH1.setLocation("Paradise Hotel, Hyderabad");
            foodH1.setImage(encodeImage("biryani.jpg"));
            fRepo.save(foodH1);
            Food foodH2 = new Food();
            foodH2.setName("Gokul Chaat"); foodH2.setType("Street Food"); foodH2.setPrice(100); foodH2.setRating(4.7); foodH2.setLocation("Koti, Hyderabad");
            foodH2.setImage(encodeImage("gokul_chaat.jpg"));
            fRepo.save(foodH2);
            Shop shopH1 = new Shop();
            shopH1.setName("Laad Bazaar"); shopH1.setCategory("Bangles"); shopH1.setRating(4.5); shopH1.setLocation("Charminar, Hyderabad");
            shopH1.setImage(encodeImage("laad_bazaar.jpg"));
            shRepo.save(shopH1);
            Shop shopH2 = new Shop();
            shopH2.setName("IKEA"); shopH2.setCategory("Furniture"); shopH2.setRating(4.6); shopH2.setLocation("HITEC City, Hyderabad");
            shopH2.setImage(encodeImage("ikea.jpg"));
            shRepo.save(shopH2);
            Location locH1 = new Location();
            locH1.setName("Charminar"); locH1.setArea("Old City, Hyderabad"); locH1.setRating(4.7); locH1.setImage(encodeImage("charminar.jpg"));
            lRepo.save(locH1);
            Location locH2 = new Location();
            locH2.setName("Ramoji Film City"); locH2.setArea("Anaspur, Hyderabad"); locH2.setRating(4.6); locH2.setImage(encodeImage("ramoji_film_city.jpg"));
            lRepo.save(locH2);
            Activity actH1 = new Activity();
            actH1.setName("Go Karting"); actH1.setPlace("Shamirpet, Hyderabad"); actH1.setCost(600); actH1.setRating(4.4);
            actH1.setImage(encodeImage("go_karting.jpg"));
            aRepo.save(actH1);
            Event eventH1 = new Event();
            eventH1.setName("Hyderabad Literary Festival"); eventH1.setVenue("Hyderabad Public School, Hyderabad"); eventH1.setDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(60))); eventH1.setPrice(100);
            eventH1.setImage(encodeImage("lit_fest.jpg"));
            eRepo.save(eventH1);

            // --- Data for Chennai ---
            mRepo.save(new Movie("Leo", "Action", 8.1, encodeImage("leo.jpg"), "PVR, Forum Mall, Chennai"));
            mRepo.save(new Movie("Tidal Fury", "Disaster", 7.9, encodeImage("tidal_fury.jpg"), "Sathyam Cinemas, Chennai"));
            Food foodC1 = new Food();
            foodC1.setName("Idli Sambar"); foodC1.setType("Veg"); foodC1.setPrice(80); foodC1.setRating(4.5); foodC1.setLocation("Murugan Idli Shop, Chennai");
            foodC1.setImage(encodeImage("idli_sambar.jpg"));
            fRepo.save(foodC1);
            Food foodC2 = new Food();
            foodC2.setName("Chettinad Chicken"); foodC2.setType("Non-Veg"); foodC2.setPrice(450); foodC2.setRating(4.8); foodC2.setLocation("Anjappar, Chennai");
            foodC2.setImage(encodeImage("chettinad_chicken.jpg"));
            fRepo.save(foodC2);
            Shop shopC1 = new Shop();
            shopC1.setName("T. Nagar"); shopC1.setCategory("Jewellery & Sarees"); shopC1.setRating(4.6); shopC1.setLocation("Thyagaraya Nagar, Chennai");
            shopC1.setImage(encodeImage("t_nagar.jpg"));
            shRepo.save(shopC1);
            Shop shopC2 = new Shop();
            shopC2.setName("Phoenix Marketcity"); shopC2.setCategory("Mall"); shopC2.setRating(4.5); shopC2.setLocation("Velachery, Chennai");
            shopC2.setImage(encodeImage("phoenix_mall_chennai.jpg"));
            shRepo.save(shopC2);
            Location locC1 = new Location();
            locC1.setName("Marina Beach"); locC1.setArea("Triplicane, Chennai"); locC1.setRating(4.6); locC1.setImage(encodeImage("marina_beach.jpg"));
            lRepo.save(locC1);
            Location locC2 = new Location();
            locC2.setName("Kapaleeshwarar Temple"); locC2.setArea("Mylapore, Chennai"); locC2.setRating(4.9); locC2.setImage(encodeImage("kapaleeshwarar_temple.jpg"));
            lRepo.save(locC2);
            Event eventC1 = new Event();
            eventC1.setName("Chennai Music Season"); eventC1.setVenue("Various Sabhas, Chennai"); eventC1.setDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(75))); eventC1.setPrice(1000);
            eventC1.setImage(encodeImage("music_season.jpg"));
            eRepo.save(eventC1);
            Activity actC1 = new Activity();
            actC1.setName("Scuba Diving"); actC1.setPlace("Covelong Beach, Chennai"); actC1.setCost(5000); actC1.setRating(4.8);
            actC1.setImage(encodeImage("scuba_driving.jpg"));
            aRepo.save(actC1);

            // --- Data for Bangalore ---
            mRepo.save(new Movie("Dune: Part Two", "Sci-Fi", 9.4, encodeImage("Dune2.jpg"), "PVR, Vega City, Bangalore"));
            Food foodB1 = new Food();
            foodB1.setName("Masala Dosa"); foodB1.setType("Veg"); foodB1.setPrice(120); foodB1.setRating(4.6); foodB1.setLocation("MTR, Bangalore");
            foodB1.setImage(encodeImage("masala_dosa.jpg"));
            fRepo.save(foodB1);
            Food foodB2 = new Food();
            foodB2.setName("Bisi Bele Bath"); foodB2.setType("Veg"); foodB2.setPrice(150); foodB2.setRating(4.5); foodB2.setLocation("Vidyarthi Bhavan, Bangalore");
            foodB2.setImage(encodeImage("bisi_bele_bath.jpg"));
            fRepo.save(foodB2);
            Shop shopB1 = new Shop();
            shopB1.setName("Commercial Street"); shopB1.setCategory("Clothing"); shopB1.setRating(4.4); shopB1.setLocation("Tasker Town, Bangalore");
            shopB1.setImage(encodeImage("commercial_street.jpg"));
            shRepo.save(shopB1);
            Location locB1 = new Location();
            locB1.setName("Lalbagh Botanical Garden"); locB1.setArea("Lalbagh, Bangalore"); locB1.setRating(4.7); locB1.setImage(encodeImage("lalbagh.jpg"));
            lRepo.save(locB1);
            Location locB2 = new Location();
            locB2.setName("Cubbon Park"); locB2.setArea("Central Bangalore, Bangalore"); locB2.setRating(4.6); locB2.setImage(encodeImage("cubbon_park.jpg"));
            lRepo.save(locB2);
            Event eventB1 = new Event();
            eventB1.setName("Bengaluru Tech Summit"); eventB1.setVenue("Palace Grounds, Bangalore"); eventB1.setDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30))); eventB1.setPrice(2500);
            eventB1.setImage(encodeImage("tech_summit.jpg"));
            eRepo.save(eventB1);
            Activity actB1 = new Activity();
            actB1.setName("Trekking"); actB1.setPlace("Nandi Hills, Bangalore"); actB1.setCost(300); actB1.setRating(4.7);
            actB1.setImage(encodeImage("nandi_hills.jpg"));
            aRepo.save(actB1);

            // --- Data for Mumbai ---
            mRepo.save(new Movie("Fighter", "Action", 8.6, encodeImage("fighter.jpg"), "INOX, R-City, Mumbai"));
            Food foodM1 = new Food();
            foodM1.setName("Vada Pav"); foodM1.setType("Veg"); foodM1.setPrice(20); foodM1.setRating(4.9); foodM1.setLocation("Ashok Vada Pav, Mumbai");
            foodM1.setImage(encodeImage("vada_pav.jpg"));
            fRepo.save(foodM1);
            Food foodM2 = new Food();
            foodM2.setName("Pav Bhaji"); foodM2.setType("Veg"); foodM2.setPrice(150); foodM2.setRating(4.7); foodM2.setLocation("Sardar Pav Bhaji, Mumbai");
            foodM2.setImage(encodeImage("pav_bhaji.jpg"));
            fRepo.save(foodM2);
            Shop shopM1 = new Shop();
            shopM1.setName("Colaba Causeway"); shopM1.setCategory("Street Shopping"); shopM1.setRating(4.3); shopM1.setLocation("Colaba, Mumbai");
            shopM1.setImage(encodeImage("colaba.jpg"));
            shRepo.save(shopM1);
            Location locM1 = new Location();
            locM1.setName("Gateway of India"); locM1.setArea("Apollo Bandar, Mumbai"); locM1.setRating(4.9); locM1.setImage(encodeImage("gateway_of_india.jpg"));
            lRepo.save(locM1);
            Activity actM1 = new Activity();
            actM1.setName("Marine Drive Walk"); actM1.setPlace("Marine Drive, Mumbai"); actM1.setCost(0); actM1.setRating(4.8);
            actM1.setImage(encodeImage("marine_drive.jpg"));
            aRepo.save(actM1);
            Event eventM1 = new Event();
            eventM1.setName("Mumbai Marathon"); eventM1.setVenue("CSMT, Mumbai"); eventM1.setDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(90))); eventM1.setPrice(2000);
            eventM1.setImage(encodeImage("mumbai_marathon.jpg"));
            eRepo.save(eventM1);

            // --- Data for Vijayawada ---
            mRepo.save(new Movie("Pushpa 2", "Action", 9.5, encodeImage("Pushpa 2.jpeg"), "INOX, LEPL, Vijayawada"));
            Food foodV1 = new Food();
            foodV1.setName("Pesarattu"); foodV1.setType("Veg"); foodV1.setPrice(70); foodV1.setRating(4.7); foodV1.setLocation("Babai Hotel, Vijayawada");
            foodV1.setImage(encodeImage("pesarattu.jpg"));
            fRepo.save(foodV1);
            Location locV1 = new Location();
            locV1.setName("Prakasam Barrage"); locV1.setArea("Krishna River, Vijayawada"); locV1.setRating(4.6); locV1.setImage(encodeImage("prakasam_barrage.jpg"));
            lRepo.save(locV1);
            Location locV2 = new Location();
            locV2.setName("Kanaka Durga Temple"); locV2.setArea("Indrakeeladri Hill, Vijayawada"); locV2.setRating(4.9); locV2.setImage(encodeImage("kanaka_durga.jpg"));
            lRepo.save(locV2);
            Event eventV1 = new Event();
            eventV1.setName("Bhavani Island Festival"); eventV1.setVenue("Bhavani Island, Vijayawada"); eventV1.setDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(20))); eventV1.setPrice(500);
            eventV1.setImage(encodeImage("bhavani_island.jpg"));
            eRepo.save(eventV1);
            
            // --- Data for Guntur ---
            Food foodG1 = new Food();
            foodG1.setName("Guntur Gongura Mutton"); foodG1.setType("Non-Veg"); foodG1.setPrice(320); foodG1.setRating(4.9); foodG1.setLocation("Subhani Hotel, Guntur");
            foodG1.setImage(encodeImage("gongura_mutton.jpg"));
            fRepo.save(foodG1);
            Shop shopG1 = new Shop();
            shopG1.setName("Guntur Mirchi Yard"); shopG1.setCategory("Spices"); shopG1.setRating(4.8); shopG1.setLocation("Mirchi Yard, Guntur");
            shopG1.setImage(encodeImage("mirchi_yard.jpg"));
            shRepo.save(shopG1);
            Location locG1 = new Location();
            locG1.setName("Uppalapadu Bird Sanctuary"); locG1.setArea("Near Guntur City, Guntur"); locG1.setRating(4.5); locG1.setImage(encodeImage("uppalapadu.jpg"));
            lRepo.save(locG1);
            Location locG2 = new Location();
            locG2.setName("Nagarjuna Sagar Dam"); locG2.setArea("Nalgonda District, Near Guntur"); locG2.setRating(4.7); locG2.setImage(encodeImage("nagarjuna_sagar.jpg"));
            lRepo.save(locG2);
        };
    }

    private String encodeImage(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        try {
            ClassPathResource resource = new ClassPathResource("images/" + fileName);
            InputStream inputStream = resource.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            System.err.println("Error reading image file from resources: " + fileName);
            return "";
        }
    }
}