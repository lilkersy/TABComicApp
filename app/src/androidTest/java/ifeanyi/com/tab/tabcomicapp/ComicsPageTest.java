package ifeanyi.com.tab.tabcomicapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Comic;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Predicates.instanceOf;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class ComicsPageTest extends ActivityInstrumentationTestCase2<ComicsPage> {

    public ComicsPageTest(){
        super(ComicsPage.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Test
    public void clickToCheckComicDetails() {
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.count)).check(matches(withText("£0.0")));
    }


}
