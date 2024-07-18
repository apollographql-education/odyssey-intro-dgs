package com.example.listings.datafetchers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.example.listings.models.ListingModel;
import java.util.List;

@DgsComponent
public class ListingDataFetcher {
    @DgsQuery
    public List<ListingModel> featuredListings() {
        ListingModel meteorListing = new ListingModel();
        meteorListing.setId("1");
        meteorListing.setTitle("Beach house on the edge of the Laertes meteor");
        meteorListing.setCostPerNight(360.00);
        meteorListing.setClosedForBookings(false);
        meteorListing.setNumOfBeds(3);

        ListingModel gasGiantListing = new ListingModel();
        gasGiantListing.setId("2");
        gasGiantListing.setTitle("Unforgettable atmosphere, unbeatable heat, tasteful furnishings");
        gasGiantListing.setCostPerNight(124.00);
        gasGiantListing.setClosedForBookings(true);
        gasGiantListing.setNumOfBeds(4);

        return List.of(meteorListing, gasGiantListing);
    }
}
