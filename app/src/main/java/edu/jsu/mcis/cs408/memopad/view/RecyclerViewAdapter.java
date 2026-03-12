package edu.jsu.mcis.cs408.memopad.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.jsu.mcis.cs408.memopad.model.Memo;
import edu.jsu.mcis.cs408.memopad.R;
import edu.jsu.mcis.cs408.memopad.databinding.MemoItemBinding;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final MainActivity activity;
    private List<Memo> memoList;

    public RecyclerViewAdapter(MainActivity activity, List<Memo> memos) {
        super();
        this.memoList = memos;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MemoItemBinding binding = MemoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(activity.getItemClick()); // the click handler
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMemo(memoList.get(position));
        holder.bindData();
    }

    @Override
    public int getItemCount() {return memoList.size();}

    public Memo getMemoAtPosition(int position) {
        return memoList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Memo memo;
        private TextView memoLabel;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public Memo getMemo() { return memo; }

        public void setMemo(Memo memo) {
            this.memo = memo;
        }

        public void bindData() {

            if (memoLabel == null) {
                memoLabel = (TextView) itemView.findViewById(R.id.memoItemLabel);
            }

            memoLabel.setText(memo.toString());

        }

    }

}