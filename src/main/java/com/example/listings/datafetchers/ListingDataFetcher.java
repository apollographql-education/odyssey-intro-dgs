package com.example.listings.datafetchers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.example.listings.models.ListingModel;
import java.util.List;
import com.example.listings.datasources.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Map;

import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.DgsData;
import com.example.listings.generated.types.Amenity;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import graphql.execution.DataFetcherResult;
import com.netflix.graphql.dgs.DgsMutation;
import com.example.listings.generated.types.CreateListingInput;
import com.example.listings.generated.types.CreateListingResponse;

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
                .localContext(Map.of("hasAmenityData", false))
                .build();
    }

    @DgsQuery
    public DataFetcherResult<ListingModel> listing(@InputArgument String id) {
        ListingModel listing = listingService.listingRequest(id);
        return DataFetcherResult.<ListingModel>newResult()
                .data(listing)
                .localContext(Map.of("hasAmenityData", true))
                .build();
    }

    @DgsData(parentType="Listing")
    public List<Amenity> amenities(DgsDataFetchingEnvironment dfe) throws IOException {
        ListingModel listing = dfe.getSource();
        String id = listing.getId();
        Map<String, Boolean> localContext = dfe.getLocalContext();
        if (localContext != null && localContext.get("hasAmenityData")) {
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
