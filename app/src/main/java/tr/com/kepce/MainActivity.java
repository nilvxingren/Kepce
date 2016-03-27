package tr.com.kepce;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import tr.com.kepce.address.Address;
import tr.com.kepce.address.AddressesFragment;
import tr.com.kepce.cart.CartEntity;
import tr.com.kepce.cart.CartFragment;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.meal.Meal;
import tr.com.kepce.meal.MealsFragment;
import tr.com.kepce.meal.MealsPagerFragment;
import tr.com.kepce.order.Order;
import tr.com.kepce.order.OrdersFragment;
import tr.com.kepce.profile.User;
import tr.com.kepce.profile.ProfileFragment;
import tr.com.kepce.restaurant.Restaurant;
import tr.com.kepce.restaurant.RestaurantsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MealsFragment.OnMealsFragmentInteractionListener,
        CartFragment.OnCartFragmentInteractionListener,
        RestaurantsFragment.OnRestaurantsFragmentInteractionListener,
        ProfileFragment.OnProfileFragmentInteractionListener,
        AddressesFragment.OnAddressesFragmentInteractionListener,
        OrdersFragment.OnOrdersFragmentInteractionListener {

    public static final int REQUEST_LOGIN = 0;
    public static final int REQUEST_REGISTER = 1;

    private NavigationView mNavigationView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new CartFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);
        View loginView = mNavigationView.inflateHeaderView(R.layout.nav_header_login);
        View loginButton = loginView.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            }
        });
        View registerButton = loginView.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_REGISTER);
            }
        });
        updateNavigationView();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new MealsPagerFragment())
                    .commit();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void updateNavigationView() {
        View userInfoView = mNavigationView.getHeaderView(0);
        View loginView = mNavigationView.getHeaderView(1);

        if (Kepce.getAuthToken(this, false) == null) {
            //TextView emailTextView = (TextView) userInfoView.findViewById(R.id.textView);
            //emailTextView.setText(accounts[0].name);
            userInfoView.setVisibility(View.VISIBLE);
            loginView.setVisibility(View.GONE);

            mNavigationView.getMenu().clear();
            mNavigationView.inflateMenu(R.menu.activity_main_drawer_loggedin);
        } else {
            userInfoView.setVisibility(View.GONE);
            loginView.setVisibility(View.VISIBLE);

            mNavigationView.getMenu().clear();
            mNavigationView.inflateMenu(R.menu.activity_main_drawer);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN || requestCode == REQUEST_REGISTER) {
            if (resultCode == RESULT_OK) {
                updateNavigationView();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_meals) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new MealsPagerFragment())
                    .commit();
        } else if (id == R.id.nav_cart) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new CartFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_restaurants) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new RestaurantsFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new ProfileFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_addresses) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new AddressesFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_orders) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new OrdersFragment())
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAddressSelected(Address address) {

    }

    @Override
    public void onCartEntitySelected(CartEntity entity) {

    }

    @Override
    public void onCartEntityModified(CartEntity entity) {

    }

    @Override
    public void onCartEntityDeleted(CartEntity entity) {

    }

    @Override
    public void onCartCleared() {

    }

    @Override
    public void onMealSelected(Meal meal) {

    }

    @Override
    public void onOrderSelected(Order order) {

    }

    @Override
    public void onProfileSaved(User user) {

    }

    @Override
    public void onRestaurantSelected(Restaurant restaurant) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://tr.com.kepce/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://tr.com.kepce/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
