package com.example.listings.datasources;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.example.listings.models.ListingModel;
import java.util.List;
import com.example.listings.generated.types.Amenity;
import com.example.listings.generated.types.CreateListingInput;
import com.example.listings.models.CreateListingModel;

@Component
public class ListingService {

    private static final String LISTING_API_URL = "https://rt-airlock-services-listing.herokuapp.com";

    private final RestClient client = RestClient.create(LISTING_API_URL);

    public List<ListingModel> featuredListingsRequest() {
        return client
                .get()
                .uri("/featured-listings")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ListingModel>>() {});
    }

    public ListingModel listingRequest(String id) {
        return client
                .get()
                .uri("/listings/{listing_id}", id)
                .retrieve()
                .body(ListingModel.class);
    }

    public List<Amenity> amenitiesRequest(String listingId) {
        return client
                .get()
                .uri("/listings/{listing_id}/amenities", listingId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Amenity>>() {});
    }

    public ListingModel createListingRequest(CreateListingInput listing) {
        return client
                .post()
                .uri("/listings")
                .body(new CreateListingModel(listing))
                .retrieve()
                .body(ListingModel.class);
    }
}
