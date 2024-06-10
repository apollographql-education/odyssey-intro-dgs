package com.example.listings.datafetchers;
import com.netflix.graphql.dgs.*;

import com.example.listings.models.ListingModel;
import java.util.List;
import com.example.listings.datasources.ListingService;
import com.example.listings.generated.types.Amenity;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import com.netflix.graphql.dgs.DgsMutation;
import com.example.listings.generated.types.CreateListingInput;
import com.example.listings.generated.types.CreateListingResponse;
import java.io.IOException;
import java.util.Objects;

@DgsComponent
public class ListingDataFetcher {

    private final ListingService listingService;

    @Autowired
    public ListingDataFetcher(ListingService listingService) {
        this.listingService = listingService;
    }

    @DgsQuery
    public DataFetcherResult<List<ListingModel>> featuredListings() throws IOException {
        List<ListingModel> listings = listingService.featuredListingsRequest();
        return DataFetcherResult.<List<ListingModel>>newResult()
                .data(listings)
                .localContext("featuredListings")
                .build();
    }

    @DgsQuery
    public DataFetcherResult<ListingModel> listing(@InputArgument String id) {
        ListingModel listing = listingService.listingRequest(id);
        return DataFetcherResult.<ListingModel>newResult()
                .data(listing)
                .localContext("listing")
                .build();
    }

    @DgsData(parentType="Listing")
    public List<Amenity> amenities(DgsDataFetchingEnvironment dfe) throws IOException {
        ListingModel listing = dfe.getSource();
        String id = listing.getId();
        String localContext = dfe.getLocalContext();

        if (Objects.equals(localContext, "listing")) {
            return listing.getAmenities();
        }

        return listingService.amenitiesRequest(id);
    }

    @DgsMutation
    public CreateListingResponse createListing(@InputArgument CreateListingInput input) {
        CreateListingResponse response = new CreateListingResponse();
        try {
            ListingModel createdListing = listingService.createListingRequest(input);
            response.setListing(createdListing);
            response.setCode(200);
            response.setMessage("success");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setListing(null);
            response.setCode(500);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }

        return response;
    }

}
