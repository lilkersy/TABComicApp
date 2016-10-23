package ifeanyi.com.tab.tabcomicapp.marvelapi.dao;

import java.io.Serializable;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class Item implements Serializable {

    public String resourceURI;
    public String name;
    public String type;

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}