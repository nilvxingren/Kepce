package tr.com.kepce;

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

import tr.com.kepce.address.Address;
import tr.com.kepce.address.AddressesFragment;
import tr.com.kepce.cart.CartEntity;
import tr.com.kepce.cart.CartFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new MealsPagerFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
}
