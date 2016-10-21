package ifeanyi.com.tab.tabcomicapp.marvelapi.request;

import java.util.Calendar;
import java.util.TimeZone;

import ifeanyi.com.tab.tabcomicapp.marvelapi.utils.HashUtil;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class RequestSignature {
    public static String apiKey;
    public static String privateKey;

    public long timeStamp;
    public String publicKey = apiKey;
    public String hashSignature;
    private static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));


    private RequestSignature(){
        this.timeStamp = calendar.getTimeInMillis() / 1000L;
        this.hashSignature = HashUtil.md5(String.valueOf(this.timeStamp) + privateKey + publicKey);
    }

    /**
     * Returnes a new instance of a request signature.
     * @return
     */
    public static RequestSignature create(){
        return  new RequestSignature();
    }
}
