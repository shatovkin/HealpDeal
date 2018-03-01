package com.example.dimitri.helpdeal.azureClasses.activities.modulEmployee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.activities.Login_Activity;
import com.example.dimitri.helpdeal.azureClasses.activities.Register_activity;
import com.example.dimitri.helpdeal.azureClasses.activities.modulCustomer.PopUp_Activity;
import com.example.dimitri.helpdeal.azureClasses.classes.SessionManager;
import com.example.dimitri.helpdeal.azureClasses.models.Category;
import com.example.dimitri.helpdeal.azureClasses.models.SearchAdapter;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

import java.util.ArrayList;

public class CategorieEmployee_Activity extends AppCompatActivity implements View.OnClickListener {

    private Drawer.Result drawerResult = null;
    private ListView list;
    private SessionManager session;
    private String[] categories = {"Altenpflege", "Handwerker", "Haushaltshilfe", "Web-/IT-Diensleistungen",
            "Web-/IT-Dienstleistungen", "Werkzeugmechaniker", "Umzugshilfe & Transport", "Konditor",
            "Notarfachangestellte", "Raumgestalter", "Schuster", "Tischler", "Werkzeugmechaniker"};

    Integer[] imgid = {R.drawable.altenpflegehelferin, R.drawable.gaertner, R.drawable.elektroniker_geraete_und_systeme, R.drawable.dachdecker, R.drawable.konditor,
            R.drawable.notarfachangestellte, R.drawable.raumausstatter, R.drawable.schuhfertiger, R.drawable.klempner, R.drawable.altenpflegehelferin, R.drawable.gaertner,
            R.drawable.raumausstatter, R.drawable.dachdecker};

    private ImageView createOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorie_layout);

        session = new SessionManager(getApplicationContext());

        list = (ListView) findViewById(R.id.buckysListView);
        createOffer = (ImageView) findViewById(R.id.createOffer);
        createOffer.setOnClickListener(this);

        //region Menue
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)
                ).build();
        //endregion
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = ((Category) parent.getItemAtPosition(position));
                Toast.makeText(getApplicationContext(), "Category:" + category.getName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CategorieEmployee_Activity.this, SubCategorieEmployee_Activity.class);
                i.putExtra("category", category.getName());
                startActivity(i);
            }
        });
    }

    //region Menue Backbutton pressed and menue Selected
    @Override
    public void onBackPressed() {
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final SearchAdapter adapter = new SearchAdapter(this, getCategories());
        list.setAdapter(adapter);

        MenuInflater inflater = getMenuInflater();

        if (session.isLoggedIn()) {
            inflater.inflate(R.menu.logout, menu);
        } else {
            inflater.inflate(R.menu.login, menu);
        }

        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(this, Login_Activity.class);
                startActivity(intent);
                break;
            case R.id.singin:
                Intent singIn = new Intent(this, Register_activity.class);
                startActivity(singIn);
                return true;
            case R.id.logout:
                Intent logout_intent = new Intent(this, Login_Activity.class);
                startActivity(logout_intent);
                session.logoutUser();
        }
        return false;
    }
    //endregion

    private ArrayList<Category> getCategories() {
        ArrayList<Category> categoryies = new ArrayList<Category>();
        for (int i = 0; i < categories.length; i++) {
            Category c = new Category(categories[i], imgid[i]);
            categoryies.add(c);
        }
        return categoryies;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createOffer:
                if (session.isLoggedIn()) {
                    Intent i = new Intent(CategorieEmployee_Activity.this, PopUp_Activity.class);
                    startActivity(i);
                } else {
                    finish();
                    Intent i = new Intent(CategorieEmployee_Activity.this, Login_Activity.class);
                    startActivity(i);
                }
                break;
        }
    }
}

