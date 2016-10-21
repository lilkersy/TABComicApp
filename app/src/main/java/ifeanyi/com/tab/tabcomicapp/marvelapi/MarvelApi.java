package ifeanyi.com.tab.tabcomicapp.marvelapi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;

import ifeanyi.com.tab.tabcomicapp.marvelapi.request.RequestSignature;
import ifeanyi.com.tab.tabcomicapp.marvelapi.response.DateAdapter;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class MarvelApi {
    public static final String PORT = "80";
    public static final String API_URL = "http://gateway.marvel.com" + ":" + PORT;

    private static MarvelApi API;

    private RestAdapter mRestAdapter;

    /**
     * Returns an instance of the MarvelApi. Must initialize static instance with
     * a call to {@link #create(String, String, android.content.Context, long)}
     * @return
     */
    public static MarvelApi getInstance(){
        return API;
    }

    /**
     * Retrieve all characters matching the provided request parameters.
     * @param privateKey The private key provided by Marvel
     * @param publicKey THe public key provided by Marvel
     * @param applicationContext Application Context for creating cache location
     * @param cacheSize Size of the Cache
     */
    public static void create(String privateKey, String publicKey, Context applicationContext, long cacheSize){
        API =  new MarvelApi(privateKey, publicKey, applicationContext, cacheSize);
    }

    /**
     * Retrieve all characters matching the provided request parameters.
     * @param privateKey The private key provided by Marvel
     * @param publicKey THe public key provided by Marvel
     * @param applicationContext Application Context for creating cache location
     * @param cacheSize Size of the Cache
     */
    private MarvelApi(String privateKey, String publicKey, Context applicationContext, long cacheSize){

        //Set the static keys so they can be used to generate the request signatures.
        RequestSignature.apiKey = publicKey;
        RequestSignature.privateKey = privateKey;

        OkHttpClient okHttpClient = new OkHttpClient();
        Cache cache = null;

        try {
            cache = new Cache(applicationContext.getCacheDir(), cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        okHttpClient.setCache(cache);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateAdapter())
                .create();

        mRestAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setConverter(new GsonConverter(gson))
                .setEndpoint(API_URL)
                .build();
    }

    public RestAdapter getRestAdapter(){
        return mRestAdapter;
    }
}
