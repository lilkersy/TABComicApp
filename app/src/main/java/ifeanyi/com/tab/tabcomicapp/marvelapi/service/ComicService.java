package ifeanyi.com.tab.tabcomicapp.marvelapi.service;

import java.util.Date;

import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Comic;
import ifeanyi.com.tab.tabcomicapp.marvelapi.response.ServiceResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public interface ComicService {
    @GET("/v1/public/comics")
    public void listComics(@Query("limit") int limit
            , @Query("offset") int offset
            , @Query("ts") String timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hashSignature
            , @Query("format") String format
            , @Query("formatType") String formatType
            , @Query("noVariants") boolean noVariants
            , @Query("dateDescriptor") String dateDescriptor
            , @Query("dateRange") String dateRange
            , @Query("hasDigitalIssue") Boolean hasDigitalIssue
            , @Query("modifiedSince") Date modifiedSince
            , @Query("creators") String creators
            , @Query("series") String series
            , @Query("events") String events
            , @Query("stories") String stories
            , @Query("sharedAppearances") String sharedAppearances
            , @Query("collaborators") String collaborators
            , @Query("orderBy") String orderBy
            , Callback<ServiceResponse<ComicService>> callback);

    @GET("/v1/public/comics/{comicId}")
    public void getComicWithId(@Path("comicId") int comicId
            , @Query("ts") String timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hashSignature
            , Callback<ServiceResponse<Comic>> callback);

}
