package geodoor.tapsi;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import geodoor.tapsi.controller.NavigationMenuController;
import geodoor.tapsi.controller.TabController;
import geodoor.tapsi.geodoor_app.R;

public class MainActivity extends AppCompatActivity {

    private TabController tabController;
    private NavigationMenuController navigationMenuController;

    private String TAG = "tapsi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabController = new TabController(this);
        navigationMenuController = new NavigationMenuController(this, tabController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigationMenuController.getmDrawerLayout().openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
