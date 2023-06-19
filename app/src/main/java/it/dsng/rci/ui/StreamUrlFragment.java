package it.dsng.rci.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.snackbar.Snackbar;
import it.dsng.rci.R;
import it.dsng.rci.Settings;
import it.dsng.rci.databinding.FragmentAddStreamBinding;
import it.dsng.rci.entities.Camera;
import java.util.Objects;

public class StreamUrlFragment extends Fragment {
    public static final String ARG_CAMERA = "arg_camera";

    private FragmentAddStreamBinding binding;
    private Settings settings;
    private Integer selectedCamera = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load existing settings (if any)
        settings = Settings.fromDisk(requireContext());
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddStreamBinding.inflate(inflater, container, false);

        // If passed an url, fill the details
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_CAMERA)) {
            this.selectedCamera = args.getInt(ARG_CAMERA);

            Camera c = settings.getCameras().get(this.selectedCamera);
            binding.streamName.setText(c.getName());
            binding.streamName.setHint(requireContext().getString(R.string.stream_list_default_camera_name).replace("{camNo}", (this.selectedCamera+1)+""));
            binding.streamUrl.setText(c.getRtspUrl());
        }

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.AppToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        binding.AppToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()  {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menuitem_save) {// Check the field is filled
                    String url = Objects.requireNonNull(binding.streamUrl.getText()).toString();
                    if (!(url.startsWith("rtsp://") || url.startsWith("http://"))) {
                        Snackbar.make(view, R.string.add_stream_invalid_url, Snackbar.LENGTH_LONG)
                                .setAction(R.string.add_stream_invalid_url_dismiss, null).show();
                        return false;
                    }

                    // Name can be empty
                    String name = Objects.requireNonNull(binding.streamName.getText()).toString();

                    if (StreamUrlFragment.this.selectedCamera != null) {
                        // Update camera
                        Camera c = settings.getCameras().get(StreamUrlFragment.this.selectedCamera);
                        c.setName(name);
                        c.setRtspUrl(url);
                    } else {
                        // Add stream to list
                        settings.addCamera(new Camera(name, url));
                    }

                    // Save
                    if (!settings.save()) {
                        Snackbar.make(view, R.string.add_stream_error_saving, Snackbar.LENGTH_LONG).show();
                        return false;
                    }

                    // Back to first fragment
                    NavHostFragment.findNavController(StreamUrlFragment.this)
                            .popBackStack();
                    return false;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}