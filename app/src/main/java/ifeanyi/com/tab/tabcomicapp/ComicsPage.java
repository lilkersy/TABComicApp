package ifeanyi.com.tab.tabcomicapp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import ifeanyi.com.tab.tabcomicapp.marvelapi.MarvelApi;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Comic;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.CreatorList;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.ImageInfo;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Price;
import ifeanyi.com.tab.tabcomicapp.marvelapi.manager.ComicManager;
import ifeanyi.com.tab.tabcomicapp.marvelapi.request.ComicRequest;
import ifeanyi.com.tab.tabcomicapp.marvelapi.request.RequestSignature;
import ifeanyi.com.tab.tabcomicapp.marvelapi.response.ServiceResponse;
import ifeanyi.com.tab.tabcomicapp.sorter.ComicSorter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ComicsPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ComicAdapter adapter;
    private List<ComicData> comicList = new ArrayList<ComicData>();
    private EditText searchView;
    private ProgressBar mprogressBar;
    private ObjectAnimator anim;
    private TextView resultView;
    private ImageButton searchButton;
    private LinearLayout searchLayout;

    private static final String PRIVATE_KEY = "5de1fabcda2ea08912bd8b09bca4321f50563655";
    private static final String PUBLIC_KEY = "54306733de0f5cd1418aa05a85fa062a";

    private Double searchDouble = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("First 100 Marvel Comics");

        resultView = (TextView) findViewById(R.id.resultText);
        resultView.setVisibility(View.GONE);

        searchView = (EditText) findViewById(R.id.search);
        searchView.setHint("Search By Budget");
        searchView.setHintTextColor(Color.DKGRAY);

        searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new SearchButtonListener());

        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        searchLayout.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();

        if (isConnectedToInternet()) {
            resultView.setText("");
            resultView.setVisibility(View.GONE);
            prepareComicData();
        } else {
            //TODO: Load From database or put a refresh button on the page
            Snackbar.make(findViewById(R.id.content_comics_page), "Are you sure you are connected to the Internet?", Snackbar.LENGTH_LONG)
                    .show();
            mprogressBar.setVisibility(View.INVISIBLE);
            resultView.setText("There are no comics to display, please connect to the internet!");
            resultView.setVisibility(View.VISIBLE);

        }
    }

    class SearchButtonListener implements  View.OnClickListener{

        @Override
        public void onClick(View v) {
            String newText = searchView.getText().toString().trim();

             if (!newText.equals("")){
                    try {
                        //TODO: Sanitize input...
                        NumberFormat nf = NumberFormat.getInstance();
                        searchDouble = Double.parseDouble(newText) * 100;
                        if(searchDouble.intValue() <= 25000){
                            processData(comicList, searchDouble.intValue(), true);
                        }else{
                            Snackbar.make(findViewById(R.id.content_comics_page), "Max Budget Amount is £250, expect budget above that in v2!", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        Snackbar.make(findViewById(R.id.content_comics_page), "Enter a valid price please!", Snackbar.LENGTH_LONG)
                                .show();
                    }
                } else if (newText.isEmpty() || newText.equals("")) {
                    processData(comicList, 0, false);
                    resultView.setText("");
                    resultView.setVisibility(View.GONE);
                }
        }
    }


    /*private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(true);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        appBarLayout.setTargetElevation(1.5f);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("comic App");
                    getSupportActionBar().setTitle("Comic App");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    getSupportActionBar().setTitle(" ");
                    isShow = false;
                }
            }
        });
    }*/


    private void prepareComicData() {

        MarvelApi.create(PRIVATE_KEY, PUBLIC_KEY, getApplicationContext(), 50 * 1024 * 1024);

        ComicRequest comicRequest = new ComicRequest(RequestSignature.create());
        comicRequest.setOffset(0);
        comicRequest.setLimit(100);

        ComicManager comicManager = new ComicManager();

        comicManager.listComics(comicRequest, new Callback<ServiceResponse<Comic>>() {
            @Override
            public void success(ServiceResponse<Comic> comicServiceResponse, Response response) {
                try {
                    List<Comic> results = comicServiceResponse.data.results;
                    comicList.clear();
                    for (Comic result : results) {

                        String title = result.title;
                        String issn = result.issn;
                        String description = result.description;
                        int pageCount = result.pageCount;
                        List<Price> prices = result.prices;
                        CreatorList authors = result.creators;

                        ImageInfo comicImage = result.thumbnail;
                        String comicThumbnail = comicImage.path.concat("/portrait_fantastic").concat("." + comicImage.extension);
                        String comicImg = comicImage.path.concat("/landscape_xlarge").concat("." + comicImage.extension);

                        ComicData a = new ComicData(title, 0, pageCount, issn, comicThumbnail, comicImg,
                                description, prices, authors);
                        comicList.add(a);
                    }
                    adapter = new ComicAdapter(getApplicationContext(), comicList);
                    resultView.setText("");
                    resultView.setVisibility(View.GONE);
                    searchLayout.setVisibility(View.VISIBLE);
                    setAdapter();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(findViewById(R.id.content_comics_page), "Looks like you have a slow connection?", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void setAdapter() {
        //Refresh the recycle view
        mprogressBar.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void processData(List<ComicData> comicList, int searchPrice, boolean hasSearch) {
        try {

            if (hasSearch) {
                List<ComicData> internalComicDataList = new ArrayList<>();

                //Add up the comic prices to make that price...
                for (ComicData comic : comicList) {
                    Double minP_off = getMinPrice(comic.getPrices()) * 100;
                    int minP = minP_off.intValue();
                    ComicData a = new ComicData(comic.getTitle(), minP, comic.getPageCount(), comic.getIssn(),
                            comic.getThumbnail(), comic.getImage(), comic.getDescription(), comic.getPrices(), comic.getAuthors());
                    internalComicDataList.add(a);
                }


                ComicSorter sorter = new ComicSorter(searchPrice);

                for (ComicData comic : internalComicDataList) {
                    sorter.add(comic);
                }

                List<ComicData> optimizedComicDatas = sorter.calcSolution();

                if (sorter.isCalculated()) {
                    NumberFormat nf = NumberFormat.getInstance();
                    System.out.println(
                            "Maximal weight           = " +
                                    nf.format(sorter.getMaxPrice() / 100.0) + " kg"
                    );
                    System.out.println(
                            "Max weight of solution = " +
                                    nf.format(sorter.getSolutionPrice() / 100.0) + " kg"
                    );
                    System.out.println(
                            "Max value              = " +
                                    sorter.getProfit()
                    );
                    System.out.println();
                    System.out.println(
                            "You can carry the following materials " +
                                    "in the knapsack:"
                    );
                    System.out.println(
                            "You can buy the following comics " +
                                    "in the knapsack:"
                    );
                    List<ComicData> fitComicDatas = new ArrayList<>();
                    for (ComicData comic : optimizedComicDatas) {
                        if (comic.getInKnapsack() == 1) {
                            System.out.println(
                                    "%1$-23s %2$-4s %3$-7s %4$-15s \n," +
                                            comic.getTitle() + "," +
                                            (comic.getPrice() / 100) + "," + "Pounds ," +
                                            "(Page Size = " + comic.getPageCount() + ")"
                            );
                            fitComicDatas.add(comic);
                        }
                    }

                    resultView.setText("You can buy " + fitComicDatas.size() + " comics " +
                            "having "+sorter.getProfit()+" pages for £" + nf.format((searchDouble / 100)));
                    resultView.setVisibility(View.VISIBLE);
                    adapter = new ComicAdapter(getApplicationContext(), fitComicDatas);
                    setAdapter();
                }
            }
            else{
                //TODO: //Remove the loader/ check if code reaches here...
                resultView.setText("");
                resultView.setVisibility(View.GONE);
                adapter = new ComicAdapter(getApplicationContext(), comicList);
                setAdapter();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    private double getMinPrice(List<Price> prices) {

        double minPrice = prices.get(0).price;

        for (int i = 0; i < prices.size(); i++) {
            if (prices.get(i).price < minPrice) {
                minPrice = prices.get(i).price;
            }
        }

        return minPrice;
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
