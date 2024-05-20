package com.example.listings.datafetchers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class ListingDataFetcher {
    @DgsQuery
    public void featuredListings() {
        // specific featuredListings-fetching logic goes here
    }
}
