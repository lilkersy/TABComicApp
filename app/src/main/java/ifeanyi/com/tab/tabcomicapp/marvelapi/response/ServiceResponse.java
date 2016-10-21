package ifeanyi.com.tab.tabcomicapp.marvelapi.response;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class ServiceResponse<E> {
    public int code;
    public String status;
    public String etag;
    public DataContainer<E> data;
}
