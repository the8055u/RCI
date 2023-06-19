package it.dsng.rci.ui;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.dsng.rci.R;
import it.dsng.rci.Settings;
import it.dsng.rci.databinding.FragmentSettingsItemListBinding;
import it.dsng.rci.entities.Camera;
import it.dsng.rci.ui.adapters.SettingsRecyclerViewAdapter;
import it.dsng.rci.utils.ItemMoveCallback;
import java.util.List;

public class SettingsFragment extends Fragment {

    private FragmentSettingsItemListBinding binding;
    private Settings settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsItemListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Register for item click
        binding.AppToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuitem_add_camera:
                        ((MainActivity)getActivity()).navigateToFragment(R.id.action_settingsToCameraUrl);
                        return true;
                    case R.id.menuitem_info:
                        ((MainActivity)getActivity()).navigateToFragment(R.id.action_SettingsToInfoFragment);
                        return true;
                } return false;
            }
        });

        binding.AppToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        // Load cameras
        settings = Settings.fromDisk(getContext());
        List<Camera> cams = settings.getCameras();

        // Set the adapter
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingsRecyclerViewAdapter adapter = new SettingsRecyclerViewAdapter(cams);
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        adapter.setOnDragListener(touchHelper::startDrag);
        recyclerView.setAdapter(adapter);

        // Onclick listener
        adapter.setOnClickListener(new SettingsRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onItemClick(int pos) {
                Bundle b = new Bundle();
                b.putInt(StreamUrlFragment.ARG_CAMERA, pos);
                ((MainActivity)getActivity()).navigateToFragment(R.id.action_settingsToCameraUrl, b);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save cameras
        List<Camera> cams = ((SettingsRecyclerViewAdapter)binding.list.getAdapter()).getItems();
        this.settings.setCameras(cams);
        this.settings.save();
    }
}