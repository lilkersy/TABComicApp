package ifeanyi.com.tab.tabcomicapp.marvelapi.manager;

import ifeanyi.com.tab.tabcomicapp.marvelapi.MarvelApi;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Comic;
import ifeanyi.com.tab.tabcomicapp.marvelapi.request.ComicRequest;
import ifeanyi.com.tab.tabcomicapp.marvelapi.request.RequestSignature;
import ifeanyi.com.tab.tabcomicapp.marvelapi.response.ServiceResponse;
import ifeanyi.com.tab.tabcomicapp.marvelapi.service.ComicService;
import retrofit.Callback;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class ComicManager extends BaseManager {
    private ComicService comics;

    public ComicManager() {
        comics = MarvelApi.getInstance().getRestAdapter().create(ComicService.class);
    }

    /**
     * Retrieve all comics matching the provided request parameters.
     * @param request Parameters for the request
     * @param callback Handler called on request completion
     */
    public void listComics(ComicRequest request, Callback<ServiceResponse<Comic>> callback) {
        comics.listComics(request.getLimit()
                , request.getOffset()
                , String.valueOf(request.getTimestamp())
                , request.getApiKey()
                , request.getHashSignature()
                , request.getFormat().getValue()
                , request.getFormatType().getValue()
                , request.isNoVariants()
                , request.getDateDescriptor().getValue()
                , request.getDateRange()
                , request.isHasDigitalIssue()
                , request.getModifiedSince()
                , parameterizeList(request.getCreators())
                , parameterizeList(request.getSeries())
                , parameterizeList(request.getEvents())
                , parameterizeList(request.getStories())
                , parameterizeList(request.getSharedAppearances())
                , parameterizeList(request.getCollaborators())
                , request.getOrderBy().getValue(), callback);
    }

    /**
     * Retrieve a comic with a specific ID.
     * @param comicId  Unique ID for the comic
     * @param callback Handler called on request completion
     */
    public void getComicWithId(int comicId, Callback<ServiceResponse<Comic>> callback){
        RequestSignature request = RequestSignature.create();
        comics.getComicWithId(comicId
                , String.valueOf(request.timeStamp)
                , request.publicKey
                , request.hashSignature
                , callback);
    }
}
