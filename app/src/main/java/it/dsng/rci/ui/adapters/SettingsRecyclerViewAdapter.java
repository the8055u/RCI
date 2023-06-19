package it.dsng.rci.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import it.dsng.rci.R;
import it.dsng.rci.databinding.FragmentSettingsItemBinding;
import it.dsng.rci.entities.Camera;
import it.dsng.rci.utils.ItemMoveCallback;
import java.util.Collections;
import java.util.List;

public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {

    private final List<Camera> mValues;
    private OnDragListener dragListener;
    private OnClickListener clickListener;

    public SettingsRecyclerViewAdapter(List<Camera> items) {
        mValues = items;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(FragmentSettingsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        vh.dragHandle.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dragListener.onItemDrag(vh);
            }
            return false;
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String cameraName = mValues.get(position).getName();
        if (cameraName == null || cameraName.length() == 0)
            cameraName = holder.name.getContext().getString(R.string.stream_list_default_camera_name).replace("{camNo}", (position+1)+"");
        holder.name.setText(cameraName);
        holder.url.setText(mValues.get(position).getRtspUrl());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(holder.getBindingAdapterPosition());
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValues.remove(holder.getBindingAdapterPosition());
                notifyItemRemoved(holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //Drag&Drop TouchHelper
    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mValues, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mValues, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(RecyclerView.ViewHolder myViewHolder) {

    }

    @Override
    public void onRowClear(RecyclerView.ViewHolder myViewHolder) {

    }

    public void setOnDragListener(OnDragListener dragListener) {
        this.dragListener = dragListener;
    }
    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public List<Camera> getItems() {
        return mValues;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View root;
        public TextView name;
        public TextView url;
        public View deleteButton;
        public View dragHandle;

        public ViewHolder(FragmentSettingsItemBinding binding) {
            super(binding.getRoot());

            this.root = binding.getRoot();
            this.name = binding.cameraName;
            this.url = binding.cameraUrl;
            this.deleteButton = binding.cameraDelete;
            this.dragHandle = binding.cameraDragHandle;
        }
    }

    public interface OnDragListener {
        void onItemDrag(ViewHolder vh);
    }

    public interface OnClickListener {
        void onItemClick(int pos);
    }
}