package com.nearme.service;

import com.nearme.dto.GooglePlaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GooglePlacesService {

    private static final Logger log = LoggerFactory.getLogger(GooglePlacesService.class);

    private static final String TEXT_SEARCH =
        "https://maps.googleapis.com/maps/api/place/textsearch/json";
    private static final String PHOTO =
        "https://maps.googleapis.com/maps/api/place/photo";
    private static final int    MAX_RESULTS  = 20;
    private static final int    MAX_PARAM_LEN = 100;

    @Value("${google.places.api.key:}")
    private String apiKey;

    private final RestTemplate restTemplate;

    // cache availability after first check to avoid repeated log spam
    private Boolean available;

    public GooglePlacesService(RestTemplateBuilder builder) {
        this.restTemplate = builder
            .connectTimeout(Duration.ofSeconds(5))
            .readTimeout(Duration.ofSeconds(8))
            .build();
    }

    public boolean isAvailable() {
        if (available == null) {
            available = apiKey != null && !apiKey.isBlank();
            if (!available) log.warn("google.places.api.key is not configured — Places API disabled");
        }
        return available;
    }

    @SuppressWarnings("unchecked")
    public List<GooglePlaceDTO> search(String city, String placeQuery) {
        if (!isAvailable()) return Collections.emptyList();

        String safeCity  = sanitize(city);
        String safeQuery = sanitize(placeQuery);
        if (safeCity.isEmpty()) return Collections.emptyList();

        String query = safeQuery + " in " + safeCity;
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = TEXT_SEARCH + "?query=" + encoded + "&language=en&key=" + apiKey;

        try {
            Map<?, ?> body = restTemplate.getForObject(url, Map.class);
            if (body == null) return Collections.emptyList();

            String status = (String) body.get("status");
            if ("REQUEST_DENIED".equals(status) || "INVALID_REQUEST".equals(status)) {
                log.error("Google Places API error: status={}, message={}", status, body.get("error_message"));
                return Collections.emptyList();
            }
            if (!"OK".equals(status) && !"ZERO_RESULTS".equals(status)) {
                return Collections.emptyList();
            }

            List<Map<String, Object>> results = (List<Map<String, Object>>) body.get("results");
            if (results == null) return Collections.emptyList();

            return results.stream()
                .limit(MAX_RESULTS)
                .map(this::toDTO)
                .collect(Collectors.toList());

        } catch (Exception e) {
            log.warn("Google Places request failed: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    private GooglePlaceDTO toDTO(Map<String, Object> r) {
        GooglePlaceDTO dto = new GooglePlaceDTO();

        dto.setId((String) r.get("place_id"));
        dto.setName((String) r.get("name"));
        dto.setAddress((String) r.getOrDefault("formatted_address", ""));

        Object rat = r.get("rating");
        dto.setRating(rat instanceof Number n ? n.doubleValue() : 0.0);

        Object total = r.get("user_ratings_total");
        dto.setTotalRatings(total instanceof Number n ? n.intValue() : 0);

        // Coordinates
        Map<String, Object> geo = (Map<String, Object>) r.get("geometry");
        if (geo != null) {
            Map<String, Object> loc = (Map<String, Object>) geo.get("location");
            if (loc != null) {
                dto.setLatitude(((Number) loc.get("lat")).doubleValue());
                dto.setLongitude(((Number) loc.get("lng")).doubleValue());
            }
        }

        // Photo — proxy via backend to avoid exposing key in DTO; fallback to Picsum
        List<Map<String, Object>> photos = (List<Map<String, Object>>) r.get("photos");
        if (photos != null && !photos.isEmpty()) {
            String ref = (String) photos.get(0).get("photo_reference");
            dto.setImageUrl(PHOTO + "?maxwidth=640&photo_reference=" + ref + "&key=" + apiKey);
        } else {
            String seed = Optional.ofNullable(dto.getName())
                .orElse("place").toLowerCase().replaceAll("[^a-z0-9]+", "-");
            dto.setImageUrl("https://picsum.photos/seed/" + seed + "/480/320");
        }

        // Primary type — skip generic Google tags
        List<String> types = (List<String>) r.get("types");
        if (types != null) {
            types.stream()
                .filter(t -> !t.equals("point_of_interest") && !t.equals("establishment"))
                .findFirst()
                .ifPresent(t -> dto.setType(t.replace("_", " ")));
        }

        // Open now
        Map<String, Object> hours = (Map<String, Object>) r.get("opening_hours");
        if (hours != null) dto.setOpenNow(Boolean.TRUE.equals(hours.get("open_now")));

        return dto;
    }

    private static String sanitize(String input) {
        if (input == null) return "";
        String s = input.trim();
        if (s.length() > MAX_PARAM_LEN) s = s.substring(0, MAX_PARAM_LEN);
        // strip characters that have no place in a location search query
        return s.replaceAll("[<>\"'%;()&+]", "");
    }
}
