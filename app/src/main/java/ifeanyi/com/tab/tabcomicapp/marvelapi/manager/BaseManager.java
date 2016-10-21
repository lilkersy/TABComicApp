package ifeanyi.com.tab.tabcomicapp.marvelapi.manager;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class BaseManager {
    /*
   *  Creates comma delimited string from list object
   * */
    public String parameterizeList(List<?> list){
        return StringUtils.join(list, ",");
    }
}
