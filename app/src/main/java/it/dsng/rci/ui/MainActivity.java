package it.dsng.rci.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import it.dsng.rci.R;
import it.dsng.rci.Settings;
import it.dsng.rci.databinding.ActivityMainBinding;
import it.dsng.rci.entities.Camera;

import java.time.temporal.Temporal;
import java.util.List;
import it.dsng.rci.ui.SettingsFragment;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Settings settings;
    private ActivityMainBinding binding;
    private NavController navController;
    private OnBackButtonPressedListener onBackButtonPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Show bottom appbar only on first fragment
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.HomeFragment) {
                binding.fab.show();
                binding.bottomAppBar.performShow(true);
            }
            else {
                binding.fab.hide();
                binding.bottomAppBar.performHide(true);
            }
        });

        binding.fab.setOnClickListener(
                view -> navigateToFragment(R.id.action_homeToAddCamera)
        );

        binding.bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuitem_info:
                        navigateToFragment(R.id.action_homeToInfo);
                        return true;
                    case R.id.menuitem_settings:
                        navigateToFragment(R.id.action_homeToSettings);
                        return true;
                } return false;
            }
        });
    }

    public void setOnBackButtonPressedListener(it.dsng.rci.ui.OnBackButtonPressedListener onBackButtonPressedListener) {
    }

    @Override
    public void onBackPressed() {
        if (this.onBackButtonPressedListener != null && this.onBackButtonPressedListener.onBackPressed())
            return;
        super.onBackPressed();
    }

    public void navigateToFragment(int actionId) {
        navigateToFragment(actionId, null);
    }

    public void navigateToFragment(int actionId, Bundle bundle) {
        if (navController == null) {
            Log.e(TAG, "Not initialized");
            return;
        }

        try {
            if (bundle != null)
                navController.navigate(actionId, bundle);
            else
                navController.navigate(actionId);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Unable to navigate to fragment: " + e.getMessage());
        }
    }

}