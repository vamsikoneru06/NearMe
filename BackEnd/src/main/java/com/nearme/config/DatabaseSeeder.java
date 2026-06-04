package com.nearme.config;

import com.nearme.model.Category;
import com.nearme.model.Location;
import com.nearme.model.User;
import com.nearme.repository.LocationRepository;
import com.nearme.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Seeds the unified Location table and default users on first startup.
 * All location images use real publicly-accessible URLs:
 *   - Famous landmarks  → Wikimedia Commons direct CDN links
 *   - Other venues      → Picsum Photos (seed-based, consistent per entry)
 */
@Configuration
public class DatabaseSeeder {

    // ── Picsum helper: consistent photo by seed word ──────────────────────────
    private static String pic(String seed) {
        return "https://picsum.photos/seed/" + seed.replace(" ", "-") + "/480/320";
    }

    @Bean
    public CommandLineRunner seedDatabase(
            LocationRepository locationRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            seedUsers(userRepository, passwordEncoder);
            seedLocations(locationRepository);
        };
    }

    // ── Users ─────────────────────────────────────────────────────────────────

    private void seedUsers(UserRepository repo, PasswordEncoder encoder) {
        if (repo.existsByEmail("admin@nearme.com")) return;

        User admin = new User();
        admin.setName("NearMe Admin");
        admin.setEmail("admin@nearme.com");
        admin.setPasswordHash(encoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN);
        repo.save(admin);

        User demo = new User();
        demo.setName("Demo User");
        demo.setEmail("user@nearme.com");
        demo.setPasswordHash(encoder.encode("user123"));
        demo.setRole(User.Role.USER);
        repo.save(demo);
    }

    // ── Locations ─────────────────────────────────────────────────────────────

    private void seedLocations(LocationRepository repo) {
        if (repo.count() > 0) return;

        // ── Hyderabad ─────────────────────────────────────────────────────────

        save(repo, "Charminar", Category.TOURIST_SPOT,
                "Iconic 16th-century mosque and monument at the heart of Old Hyderabad.",
                "Charminar, Old City, Hyderabad", 17.3616, 78.4747, 4.7,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Charminar-Hyderabad.jpg/480px-Charminar-Hyderabad.jpg");

        save(repo, "Paradise Biryani", Category.RESTAURANT,
                "The most famous biryani restaurant in Hyderabad, serving dum biryani since 1953.",
                "Secunderabad, Hyderabad", 17.4399, 78.4983, 4.8,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Hyderabadi_biryani1.jpg/480px-Hyderabadi_biryani1.jpg");

        save(repo, "Golconda Fort", Category.TOURIST_SPOT,
                "Magnificent medieval fort with impressive architecture and panoramic city views.",
                "Khairtabad, Hyderabad", 17.3833, 78.4011, 4.8,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/db/Golconda_Fort_1.jpg/480px-Golconda_Fort_1.jpg");

        save(repo, "Inorbit Mall", Category.SHOP,
                "One of Hyderabad's premium shopping destinations featuring 200+ brands across 4 levels.",
                "Madhapur, Hyderabad", 17.4260, 78.3717, 4.7,
                pic("shopping-mall-india"));

        save(repo, "Prasads IMAX", Category.MOVIE_THEATER,
                "India's largest IMAX screen with state-of-the-art Dolby Atmos sound.",
                "Necklace Road, Hyderabad", 17.4127, 78.4724, 4.6,
                pic("imax-cinema-hyderabad"));

        save(repo, "Wonderla Amusement Park", Category.ACTIVITY,
                "Thrilling water and land rides for the whole family spread across 55 acres.",
                "Outer Ring Road, Hyderabad", 17.3308, 78.4006, 4.7,
                pic("amusement-park-rides-india"));

        save(repo, "NH7 Weekender", Category.EVENT,
                "India's happiest multi-genre music festival with international and Indian artists.",
                "Gachibowli Stadium, Hyderabad", 17.4063, 78.3472, 4.8,
                pic("music-festival-india-night"));

        save(repo, "Hussain Sagar Lake", Category.TOURIST_SPOT,
                "Heart-shaped lake connecting Hyderabad and Secunderabad with a giant Buddha statue.",
                "Tank Bund Road, Hyderabad", 17.4239, 78.4738, 4.5,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Hussain_Sagar_1.jpg/480px-Hussain_Sagar_1.jpg");

        // ── Mumbai ────────────────────────────────────────────────────────────

        save(repo, "Gateway of India", Category.TOURIST_SPOT,
                "Iconic 26-metre basalt arch built in 1924, Mumbai's most recognisable landmark.",
                "Apollo Bunder, Colaba, Mumbai", 18.9220, 72.8347, 4.9,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Mumbai_03-2016_30_Gateway_of_India.jpg/480px-Mumbai_03-2016_30_Gateway_of_India.jpg");

        save(repo, "Leopold Café", Category.RESTAURANT,
                "Historic café-restaurant in Colaba since 1871, famous for its colonial décor.",
                "Colaba Causeway, Mumbai", 18.9224, 72.8316, 4.6,
                pic("colaba-cafe-mumbai"));

        save(repo, "Phoenix Marketcity Mumbai", Category.SHOP,
                "Sprawling lifestyle complex housing 350+ stores, food court, and a multiplex.",
                "Kurla, Mumbai", 19.0858, 72.8851, 4.7,
                pic("phoenix-mall-mumbai"));

        save(repo, "INOX R-City Mall", Category.MOVIE_THEATER,
                "Premium multiplex with recliner seats, Dolby Vision, and the latest releases.",
                "LBS Marg, Ghatkopar, Mumbai", 19.0876, 72.8866, 4.5,
                pic("multiplex-cinema-india"));

        save(repo, "Kala Ghoda Arts Festival", Category.EVENT,
                "Asia's largest urban arts festival celebrating visual and performing arts.",
                "Kala Ghoda, Fort, Mumbai", 18.9289, 72.8310, 4.8,
                pic("arts-festival-mumbai"));

        // ── Bangalore ─────────────────────────────────────────────────────────

        save(repo, "Lalbagh Botanical Garden", Category.TOURIST_SPOT,
                "240-acre green oasis housing 1,800+ plant species and a famous floral show.",
                "Mavalli, South Bangalore", 12.9507, 77.5848, 4.8,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/Lalbagh_Botanical_Garden_Glass_House_Bangalore.jpg/480px-Lalbagh_Botanical_Garden_Glass_House_Bangalore.jpg");

        save(repo, "MTR – Mavalli Tiffin Room", Category.RESTAURANT,
                "Institution since 1924, home of the original Rava Idli and Mysore Masala Dosa.",
                "Lalbagh Road, Bangalore", 12.9560, 77.5728, 4.8,
                pic("south-indian-breakfast-dosa"));

        save(repo, "UB City Mall", Category.SHOP,
                "Bangalore's most luxurious shopping destination with global luxury brands.",
                "Vittal Mallya Road, Bangalore", 12.9708, 77.5963, 4.8,
                pic("luxury-mall-bangalore"));

        save(repo, "INOX Forum Mall", Category.MOVIE_THEATER,
                "7-screen multiplex with latest projection technology in the heart of Koramangala.",
                "Koramangala, Bangalore", 12.9347, 77.6163, 4.6,
                pic("cinema-hall-india"));

        save(repo, "Sky Diving – Jakkur Aerodrome", Category.ACTIVITY,
                "Experience tandem skydiving at 10,000 ft over Bangalore — the ultimate rush.",
                "Jakkur Aerodrome, Bangalore", 13.0600, 77.5906, 4.8,
                pic("skydiving-india-aerial"));

        // ── New Delhi ─────────────────────────────────────────────────────────

        save(repo, "India Gate", Category.TOURIST_SPOT,
                "War memorial and beloved city park, a defining symbol of New Delhi.",
                "Rajpath, New Delhi", 28.6129, 77.2295, 4.8,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/India_Gate_in_New_Delhi_03-2016.jpg/480px-India_Gate_in_New_Delhi_03-2016.jpg");

        save(repo, "Karim's", Category.RESTAURANT,
                "Legendary Mughlai restaurant near Jama Masjid since 1913, slow-cooked kormas.",
                "Jama Masjid, Old Delhi", 28.6562, 77.2322, 4.8,
                pic("mughlai-food-delhi-kebab"));

        save(repo, "Connaught Place", Category.SHOP,
                "Delhi's iconic heritage shopping district — luxury brands, boutiques and street food.",
                "Connaught Place, New Delhi", 28.6315, 77.2167, 4.8,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Connaught_Place_Central_Park.jpg/480px-Connaught_Place_Central_Park.jpg");

        save(repo, "Qutub Minar", Category.TOURIST_SPOT,
                "UNESCO World Heritage site — 73-metre minaret built in 1193.",
                "Mehrauli, New Delhi", 28.5244, 77.1855, 4.8,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Qutb_Minar.jpg/480px-Qutb_Minar.jpg");

        save(repo, "Startup India Summit", Category.EVENT,
                "Government flagship event bringing together 10,000+ entrepreneurs and investors.",
                "ITPO Grounds, Pragati Maidan, New Delhi", 28.6353, 77.2867, 4.5,
                pic("startup-conference-india"));

        // ── Chennai ───────────────────────────────────────────────────────────

        save(repo, "Marina Beach", Category.TOURIST_SPOT,
                "The world's second-longest natural urban beach, 13 km along the Bay of Bengal.",
                "Santhome, Chennai", 13.0500, 80.2824, 4.8,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Marina_Beach_Chennai_Panorama.jpg/480px-Marina_Beach_Chennai_Panorama.jpg");

        save(repo, "Murugan Idli Shop", Category.RESTAURANT,
                "Iconic South Indian tiffin chain known for soft idlis and eight-chutney platters.",
                "T. Nagar, Chennai", 13.0418, 80.2338, 4.7,
                pic("idli-chutney-south-indian"));

        save(repo, "Chennai Music Festival", Category.EVENT,
                "The world's longest-running classical music festival — 300+ concerts over six weeks.",
                "Various Auditoriums, Chennai", 13.0478, 80.2356, 4.7,
                pic("classical-music-concert-india"));

        // ── Vijayawada ────────────────────────────────────────────────────────

        save(repo, "Kanaka Durga Temple", Category.TOURIST_SPOT,
                "Sacred hilltop temple atop Indrakeeladri Hill on the banks of the Krishna river.",
                "Indrakeeladri Hills, Vijayawada", 16.5144, 80.6153, 4.9,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Kanaka_Durga_Temple_View.jpg/480px-Kanaka_Durga_Temple_View.jpg");

        save(repo, "Bhavani Island", Category.ACTIVITY,
                "India's largest river island with adventure sports and boat rides on the Krishna.",
                "Krishna River, Vijayawada", 16.5131, 80.6209, 4.8,
                pic("river-island-adventure-india"));
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private void save(LocationRepository repo, String name, Category category,
                      String description, String address,
                      double lat, double lng, double rating, String imageUrl) {
        Location loc = new Location();
        loc.setName(name);
        loc.setCategory(category);
        loc.setDescription(description);
        loc.setAddress(address);
        loc.setLatitude(lat);
        loc.setLongitude(lng);
        loc.setRating(rating);
        loc.setImageUrl(imageUrl);
        repo.save(loc);
    }
}
