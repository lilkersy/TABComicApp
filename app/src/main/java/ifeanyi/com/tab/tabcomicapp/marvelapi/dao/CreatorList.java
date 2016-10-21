package ifeanyi.com.tab.tabcomicapp.marvelapi.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class CreatorList implements Serializable {
    public int available;
    public int returned;
    public String collectionURI;
    public String name;
    public String role;
    public List<CreatorSummary> items;
}
