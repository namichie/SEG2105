package com.qwerty123.cookhelper.View.Activities;

import android.content.Intent;
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

import com.qwerty123.cookhelper.Utils.Utils;
import com.qwerty123.cookhelper.View.Fragments.AdvancedSearchFragment;
import com.qwerty123.cookhelper.View.Fragments.HelpFragment;
import com.qwerty123.cookhelper.View.Fragments.RecipeBookFragment;
import com.qwerty123.cookhelper.R;

/**
 * The main activity. This class implements the functionalty required to use a navigation drawer,
 * and to switch between the three main fragments, RecipeBook, AdvancedSearch, and
 * Help. Also handles the floating action button.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the application context to be used by other classes that do not have access to a context.
        Utils.setApplicationContext(this);

        configureFAB();
        configureDrawer(toolbar);
        configureNavigationView();

        switchToRecipeBook();
    }

    private void configureNavigationView()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureDrawer(Toolbar toolbar)
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureFAB()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_recipe_fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.add_recipe_fab)
        {
            Intent intent = new Intent(this, RecipeEditActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.nav_recipe_book)
        {
            switchToRecipeBook();
        }
        else if (id == R.id.nav_advanced_search)
        {
            switchToAdvancedSearch();
        }
        else if (id == R.id.nav_help)
        {
            switchToHelp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchToHelp()
    {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new HelpFragment()).commit();
    }

    private void switchToAdvancedSearch()
    {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new AdvancedSearchFragment()).commit();
    }

    private void switchToRecipeBook()
    {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new RecipeBookFragment()).commit();
    }
}