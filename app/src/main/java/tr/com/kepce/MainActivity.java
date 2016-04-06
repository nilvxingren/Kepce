package tr.com.kepce;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.com.kepce.address.AddressesFragment;
import tr.com.kepce.auth.LoginActivity;
import tr.com.kepce.auth.RegisterActivity;
import tr.com.kepce.cart.CartFragment;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.meal.Meal;
import tr.com.kepce.meal.MealActivity;
import tr.com.kepce.meal.MealsFragment;
import tr.com.kepce.meal.MealsPagerFragment;
import tr.com.kepce.order.Order;
import tr.com.kepce.order.OrderFragment;
import tr.com.kepce.order.OrdersFragment;
import tr.com.kepce.profile.ProfileFragment;
import tr.com.kepce.restaurant.Restaurant;
import tr.com.kepce.restaurant.RestaurantFragment;
import tr.com.kepce.restaurant.RestaurantsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MealsFragment.OnMealsFragmentInteractionListener,
        CartFragment.OnCartFragmentInteractionListener,
        RestaurantsFragment.OnRestaurantsFragmentInteractionListener,
        ProfileFragment.OnProfileFragmentInteractionListener,
        AddressesFragment.OnAddressesFragmentInteractionListener,
        OrdersFragment.OnOrdersFragmentInteractionListener,
        RestaurantFragment.OnRestaurantFragmentInteractionListener {

    public static final int REQUEST_LOGIN = 0;
    public static final int REQUEST_REGISTER = 1;

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setLogo(R.drawable.ic_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        toggle.setDrawerIndicatorEnabled(
                                getSupportFragmentManager().getBackStackEntryCount() == 0);
                    }
                });

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
                    .replace(R.id.content_frame, MealsPagerFragment.newInstance(), "home")
                    .commit();
        }
    }

    public void updateNavigationView() {
        View userInfoView = mNavigationView.getHeaderView(0);
        View loginView = mNavigationView.getHeaderView(1);

        if (Kepce.peekAuthToken(this) != null) {
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
        if (id == R.id.action_cart) {
            onCartSelected();
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        getSupportFragmentManager().popBackStack("home", 0);
        if (id == R.id.nav_meals) {
            /*
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, MealsPagerFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
            */
        } else if (id == R.id.nav_cart) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, CartFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_restaurants) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, RestaurantsFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, ProfileFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_addresses) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, AddressesFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_orders) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, OrdersFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_logout) {
            final String token = Kepce.peekAuthToken(this);
            if (token != null) {
                Kepce.getService().logout(token)
                        .enqueue(new Callback<KepceResponse<Void>>() {
                            @Override
                            public void onResponse(Call<KepceResponse<Void>> call,
                                                   Response<KepceResponse<Void>> response) {
                                Kepce.invalidateAuthToken(MainActivity.this);
                                updateNavigationView();
                            }

                            @Override
                            public void onFailure(Call<KepceResponse<Void>> call, Throwable t) {
                            }
                        });
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMealSelected(Meal meal) {
        Intent intent = new Intent(this, MealActivity.class);
        intent.putExtra(MealActivity.KEY_MEAL, meal);
        startActivity(intent);
    }

    @Override
    public void onOrderSelected(Order order) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, OrderFragment.newInstance(order))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onRestaurantSelected(Restaurant restaurant) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, RestaurantFragment.newInstance(restaurant))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCartSelected() {
        getSupportFragmentManager().popBackStack("home", 0);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, CartFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
}
