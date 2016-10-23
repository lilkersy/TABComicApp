package ifeanyi.com.tab.tabcomicapp;

import android.util.Log;

import org.junit.Test;

import java.util.List;

import ifeanyi.com.tab.tabcomicapp.marvelapi.MarvelApi;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.CharacterList;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Comic;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.CreatorList;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.CreatorSummary;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.ImageInfo;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.IndustryDate;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Item;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.ItemList;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Price;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.TextObject;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Url;
import ifeanyi.com.tab.tabcomicapp.marvelapi.manager.ComicManager;
import ifeanyi.com.tab.tabcomicapp.marvelapi.request.ComicRequest;
import ifeanyi.com.tab.tabcomicapp.marvelapi.request.RequestSignature;
import ifeanyi.com.tab.tabcomicapp.marvelapi.response.ServiceResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by brainergysolutions on 10/23/16.
 */


public class ComicApiClientTest extends ComicApiTest{


    private static final String PRIVATE_KEY = "5de1fabcda2ea08912bd8b09bca4321f50563655";
    private static final String PUBLIC_KEY = "54306733de0f5cd1418aa05a85fa062a";
    private static final int ANY_OFFSET = 1;
    private static final int ANY_LIMIT = 10;
    private static final int INVALID_LIMIT = 0;
    private static final int ANY_COMIC_ID = 123456;


   private void givenComicApiClient() {

       MarvelApi.create(PRIVATE_KEY, PUBLIC_KEY, null, 50 * 1024 * 1024);

    }

    @Test
    public void shouldRequestComicsByOffsetAndLimitWithAsParams() throws Exception {

        givenComicApiClient();
        enqueueMockResponse();

        ComicRequest comicRequest = new ComicRequest(RequestSignature.create());
        comicRequest.setLimit(ANY_LIMIT);
        comicRequest.setOffset(ANY_OFFSET);

        ComicManager comicManager = new ComicManager();
        comicManager.listComics(comicRequest, new Callback<ServiceResponse<Comic>>() {

            @Override
            public void success(ServiceResponse<Comic> comicServiceResponse, Response response) {
                try{
                    assertRequestSentToContains("offset=" + ANY_OFFSET, "limit=" + ANY_LIMIT);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Error", error.getBody().toString());
            }
        });

    }

    @Test public void shouldParseGetComicResponse() throws Exception {

        givenComicApiClient();
        enqueueMockResponse("getComic.json");

        ComicManager comicManager = new ComicManager();
        comicManager.getComicWithId(ANY_COMIC_ID, new Callback<ServiceResponse<Comic>>() {

            @Override
            public void success(ServiceResponse<Comic> comicServiceResponse, Response response) {
                try{

                    Comic cResponse = comicServiceResponse.data.results.get(0);
                    assertIsLornaTheJungleGirl6(cResponse);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Error", error.getBody().toString());
            }
        });
    }

    private void assertIsLornaTheJungleGirl6(Comic comicDto) {
        assertEquals("42882", comicDto.getId());
        assertEquals(26110, comicDto.getDigitalId());
        assertEquals("Lorna the Jungle Girl (1954) #6", comicDto.getTitle());
        assertEquals(6, comicDto.getIssueNumber(), 0);
        assertEquals("variant", comicDto.getVariantDescription());
        assertEquals("description", comicDto.getDescription());
        assertEquals("2015-10-15T11:13:52-0400", comicDto.getModified());
        assertEquals("1", comicDto.getIsbn());
        assertEquals("2", comicDto.getUpc());
        assertEquals("3", comicDto.getEan());
        assertEquals("4", comicDto.getIssn());
        assertEquals("5", comicDto.getDiamondCode());
        assertEquals("Comic", comicDto.getFormat());
        assertEquals(32, comicDto.getPageCount());
        TextObject textObject = comicDto.getTextObjects().get(0);
        assertEquals("issue_solicit_text", textObject.getType());
        assertEquals("en-us", textObject.getLanguage());
        assertEquals("From the award-winning team that brought you Daredevil and Spider-Woman "
                + "comes another explosive chapter of one of the most controversial "
                + "creator-owned comics being published today. Scarlet has declared "
                + "war on a city that refuses to stop the corruption that is strangling "
                + "it from within, and now she&#39;ll be forced to make her most dangerous "
                + "move yet: letting them capture her.", textObject.getText());

        assertEquals("http://gateway.marvel.com/v1/public/comics/42882", comicDto.getResourceURI());
        Url marvelUrl = comicDto.getUrls().get(0);
        assertEquals("detail", marvelUrl.getType());
        assertEquals(
                "http://marvel.com/comics/issue/42882/lorna_the_jungle_girl_1954_6?utm_campaign=apiRef"
                        + "&utm_source=838a08a2f4c39fa3fd218b1b2d43f19a", marvelUrl.getUrl());

        Item series = comicDto.getSeries();
        assertEquals("http://gateway.marvel.com/v1/public/series/16355", series.getResourceURI());
        assertEquals("Lorna the Jungle Girl (1954 - 1957)", series.getName());

        IndustryDate marvelDate = comicDto.getDates().get(0);
        assertEquals("onsaleDate", marvelDate.getType());
        assertEquals("2054-03-01T00:00:00-0500", marvelDate.getDate());

        Price marvelPrice = comicDto.getPrices().get(0);
        assertEquals("printPrice", marvelPrice.getType());
        assertEquals(0, marvelPrice.getPrice(), 0);

        ImageInfo thumbnail = comicDto.getThumbnail();
        assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/9/40/50b4fc783d30f", thumbnail.getPath());
        assertEquals("jpg", thumbnail.getExtension());

        ImageInfo marvelImage = comicDto.getImages().get(0);
        assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/9/40/50b4fc783d30f",
                marvelImage.getPath());
        assertEquals("jpg", marvelImage.getExtension());

        CreatorList creators = comicDto.getCreators();
        assertEquals(1, creators.getAvailable());
        assertEquals("http://gateway.marvel.com/v1/public/comics/41530/creators",
                creators.getCollectionURI());
        assertEquals(1, creators.getReturned());
        CreatorSummary creatorResourceDto = creators.getItems().get(0);
        assertEquals("http://gateway.marvel.com/v1/public/creators/4430",
                creatorResourceDto.getResourceURI());
        assertEquals("Jeff Youngquist", creatorResourceDto.getName());
        assertEquals("editor", creatorResourceDto.getRole());

        CharacterList characters = comicDto.getCharacters();
        assertEquals(0, characters.getAvailable());
        assertEquals(0, characters.getReturned());
        assertTrue(characters.getItems().isEmpty());

        ItemList stories = comicDto.getStories();
        assertEquals(1, stories.getAvailable());
        assertEquals("http://gateway.marvel.com/v1/public/comics/42882/stories",
                stories.getCollectionURI());
        assertEquals(1, stories.getReturned());
        Item storyResourceDto = stories.getItems().get(0);
        assertEquals("cover from Lorna the Jungle Girl #6", storyResourceDto.getName());
        assertEquals("cover", storyResourceDto.getType());

        ItemList events = comicDto.getEvents();
        assertEquals(0, events.getAvailable());
        assertEquals(0, events.getReturned());
        assertEquals("http://gateway.marvel.com/v1/public/comics/42882/events",
                events.getCollectionURI());
        assertTrue(events.getItems().isEmpty());
    }


}
