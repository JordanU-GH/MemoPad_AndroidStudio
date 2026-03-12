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

    private MemoItemBinding binding;
    private List<Memo> memos;

    public RecyclerViewAdapter(List<Memo> memos) {
        this.memos = memos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = MemoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMemo(memos.get(position));
        holder.bindData();
    }

    @Override
    public int getItemCount() {return memos.size();}

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