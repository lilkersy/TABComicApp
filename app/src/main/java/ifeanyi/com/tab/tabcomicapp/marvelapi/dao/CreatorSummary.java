package ifeanyi.com.tab.tabcomicapp.marvelapi.dao;

import java.io.Serializable;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class CreatorSummary extends Item implements Serializable {

    public String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
