package ifeanyi.com.tab.tabcomicapp.marvelapi.response;

import java.util.List;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class DataContainer<E> {
    public int offset;
    public int limit;
    public int total;
    public int count;
    public List<E> results;
}
