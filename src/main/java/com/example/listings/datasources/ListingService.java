package com.example.listings.datasources;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.listings.models.ListingModel;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.io.IOException;

@Component
public class ListingService {
    private static final String LISTING_API_URL = "https://rt-airlock-services-listing.herokuapp.com";
    private final RestClient client = RestClient.builder().baseUrl(LISTING_API_URL).build();

    private final ObjectMapper mapper = new ObjectMapper();
    public List<ListingModel> featuredListingsRequest() throws IOException {
        JsonNode response = client
                .get()
                .uri("/featured-listings")
                .retrieve()
                .body(JsonNode.class);

        if (response != null) {
            return mapper.readValue(response.traverse(), new TypeReference<List<ListingModel>>() {});
        }

        return null;
    }
}
