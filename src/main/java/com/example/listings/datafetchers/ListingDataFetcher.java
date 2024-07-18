package com.example.listings.datafetchers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.example.listings.models.ListingModel;
import java.util.List;
import com.example.listings.datasources.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class ListingDataFetcher {

    private final ListingService listingService;

    @Autowired
    public ListingDataFetcher(ListingService listingService) {
        this.listingService = listingService;
    }
    @DgsQuery
    public List<ListingModel> featuredListings() throws IOException {
        return listingService.featuredListingsRequest();
    }

    @DgsQuery
    public ListingModel listing(@InputArgument String id) {
        return listingService.listingRequest(id);
    }
}
