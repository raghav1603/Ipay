package in.moneytransfer.ipay.home.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.bustickets.activity.BusTicketsActivity;
import in.moneytransfer.ipay.buysell.activity.BuySellActivity;
import in.moneytransfer.ipay.electronics.activity.ElectronicsActivity;
import in.moneytransfer.ipay.home.fragment.OffersFragment;
import in.moneytransfer.ipay.home.fragment.UserOptionsFragment;
import in.moneytransfer.ipay.home.manager.PassbookService;
import in.moneytransfer.ipay.hotels.activity.HotelsActivity;
import in.moneytransfer.ipay.mensfashion.activity.MensFashionActivity;
import in.moneytransfer.ipay.mobile.activity.MobileAccessoriesActivity;
import in.moneytransfer.ipay.profile.activity.MyProfileActivity;
import in.moneytransfer.ipay.recharge.activity.RechargeActivity;
import in.moneytransfer.ipay.sports.activity.SportsActivity;
import in.moneytransfer.ipay.stationary.activity.StationaryActivity;
import in.moneytransfer.ipay.womensfashion.activity.WomensFashionActivity;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout homeDrawer;
    private Toolbar homeToolbar;
    private NavigationView homeNavigation;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        createDrawer();
        showOffersFragment();
        showUsersFragement();
       // startPBookService();
    }
    private void createDrawer()
    {
        homeToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(homeToolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        homeDrawer =  findViewById(R.id.home_drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,homeDrawer,homeToolbar,R.string.OPEN,R.string.CLOSE);
        actionBarDrawerToggle.syncState();

        homeDrawer.addDrawerListener(actionBarDrawerToggle);

        homeNavigation = findViewById(R.id.home_navigation_view);
        homeNavigation.setNavigationItemSelectedListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        View view = homeNavigation.getHeaderView(0);
        TextView nameText =  view.findViewById(R.id.profile_username);
        nameText.setText(user.getDisplayName());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.rechargeNav:
            {
                startActivity(new Intent(this, RechargeActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.mobileNav:
            {
                startActivity(new Intent(this, MobileAccessoriesActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.electronicsNav:
            {
                startActivity(new Intent(this, ElectronicsActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.busTicketsNav:
            {
                startActivity(new Intent(this, BusTicketsActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.hotelsNav:
            {
                startActivity(new Intent(this, HotelsActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.menFashionnav:
            {
                startActivity(new Intent(this, MensFashionActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.womenFashionNav:
            {
                startActivity(new Intent(this, WomensFashionActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.sportsNav:
            {
                startActivity(new Intent(this, SportsActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.booksNav:
            {
                startActivity(new Intent(this, StationaryActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.buySellNav:
            {
                startActivity(new Intent(this, BuySellActivity.class));
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            }
            default:
                break;
        }

        return true;
    }

    private void showUsersFragement()
    {
        UserOptionsFragment fragment = new UserOptionsFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.userOptions,fragment)
                .commit();
    }
    private void showOffersFragment()
    {
        OffersFragment fragment = new OffersFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.todaysOffer,fragment)
                .commit();

    }

    public void openProfileSettings(View view)
    {
        startActivity(new Intent(this, MyProfileActivity.class));
        homeDrawer.closeDrawer(GravityCompat.START);
    }
    private void startPBookService()
    {
        Intent intent = new Intent(getApplicationContext(), PassbookService.class);
        startService(intent);
    }

}
