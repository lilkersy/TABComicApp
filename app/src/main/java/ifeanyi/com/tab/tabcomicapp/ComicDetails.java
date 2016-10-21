package ifeanyi.com.tab.tabcomicapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.CreatorSummary;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Price;

public class ComicDetails extends AppCompatActivity {


    public TextView title, description, price, authors, page_count;
    public ImageView thumbnail;

    private ComicData comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        price = (TextView) findViewById(R.id.prices);
        page_count = (TextView) findViewById(R.id.count);
        authors = (TextView) findViewById(R.id.authors);

        comic = (ComicData) getIntent().getSerializableExtra("comic");

        title.setText(comic.getTitle());

        // loading comic cover using Glide library
        Glide.with(this).load(comic.getImage()).into(thumbnail);
        description.setText(comic.getDescription());
        String prices = "";
        for(Price p : comic.getPrices()){
            prices = prices.concat("£"+String.valueOf(p.price)).concat(", ");
        }
        price.setText(prices);
        page_count.setText(String.valueOf(comic.getPageCount()));
        String author = "";
        for(CreatorSummary c : comic.getAuthors().items){
            author = author.concat(c.name);
            if(!c.name.equals(comic.getAuthors().items.get(comic.getAuthors().items.size() - 1)))
                author = author.concat(", ");
        }
        if(!author.equals(""))
            authors.setText(author);
        else
            authors.setText("No Authors For Now!");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
