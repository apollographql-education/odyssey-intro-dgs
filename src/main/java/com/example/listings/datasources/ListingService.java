package com.example.listings.datasources;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.listings.models.ListingModel;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.io.IOException;
import com.example.listings.generated.types.Amenity;
import com.example.listings.generated.types.CreateListingInput;
import com.example.listings.models.CreateListingModel;
import org.springframework.http.converter.json.MappingJacksonValue;

@Component
public class ListingService {
    private static final String LISTING_API_URL = "http://localhost:4010";
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

    public ListingModel listingRequest(String id) {
        return client
                .get()
                .uri("/listings/{listing_id}", id)
                .retrieve()
                .body(ListingModel.class);
    }

    public List<Amenity> amenitiesRequest(String listingId) throws IOException {
        JsonNode response = client
                .get()
                .uri("/listings/{listing_id}/amenities", listingId)
                .retrieve()
                .body(JsonNode.class);

        if (response != null) {
            return mapper.readValue(response.traverse(), new TypeReference<List<Amenity>>() {
            });
        }

        return null;

    }

    public ListingModel createListingRequest(CreateListingInput listing) {
        MappingJacksonValue serializedListing = new MappingJacksonValue(new CreateListingModel(listing));
        return client
                .post()
                .uri("/listings")
                .body(serializedListing)
                .retrieve()
                .body(ListingModel.class);
    }
}