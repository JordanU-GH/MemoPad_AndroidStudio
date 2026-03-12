package edu.jsu.mcis.cs408.memopad.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.memopad.R;
import edu.jsu.mcis.cs408.memopad.controller.DefaultController;
import edu.jsu.mcis.cs408.memopad.databinding.ActivityMainBinding;
import edu.jsu.mcis.cs408.memopad.model.DefaultModel;
import edu.jsu.mcis.cs408.memopad.model.Memo;

public class MainActivity extends AppCompatActivity implements AbstractView {
    public static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private DefaultController controller;
    private final DefaultClickHandler itemClick = new DefaultClickHandler();

    public DefaultClickHandler getItemClick() { return itemClick; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /* Create Controller and Model */

        controller = new DefaultController();
        DefaultModel model = new DefaultModel(this);

        /* Register Activity View and Model with Controller */

        controller.addView(this);
        controller.addModel(model);

        /* Initialize Model to Default Values */

        model.initDefault();

        /* Associate Click Handler with Input Buttons */

        DefaultClickHandler click = new DefaultClickHandler();
        ConstraintLayout layout = binding.layout;

        for (int i = 0; i < layout.getChildCount(); ++i) {
            View child = layout.getChildAt(i);
            if(child instanceof Button) {
                child.setOnClickListener(click);
            }
        }

        // Load any preexisting memos
        updateRecyclerView();
    }

    @Override
    public void modelPropertyChange(final PropertyChangeEvent evt) {
        /*
         * This method is called by the "propertyChange()" method of AbstractController
         * when a change is made to an element of a Model.  It identifies the element that
         * was changed and updates the View accordingly.
         */

        String propertyName = evt.getPropertyName();
        String propertyValue = evt.getNewValue().toString();

        Log.i(TAG, "New " + propertyName + " Value from Model: " + propertyValue);

        // We could have different effects depending
        // on the property change that occurred.
        // However, for the purposes of this assignment,
        // it seems that we always want to update the recyclerview regardless
        updateRecyclerView();
    }

    class DefaultClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = binding.memoList.getChildLayoutPosition(v);
            Integer id = null;
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)binding.memoList.getAdapter();
            if (adapter != null) {
                Memo memo = adapter.getMemoAtPosition(position);
                id = memo.getId();
                Toast.makeText(v.getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
            /*
             * When a button is clicked, inform the controller of the correct change,
             * so that the Model(s) can be updated accordingly.
             */
            String tag = v.getTag().toString();
            if ( tag.equals(getResources().getString(R.string.add_button_tag)) ) {
                String newMemo = binding.memoInput.getText().toString();
                controller.changeElementMemoList(newMemo);
            }
            else if ( tag.equals(getResources().getString(R.string.delete_button_tag)) ) {
                if (id != null) {
                    controller.changeElementDeleteMemo(id);
                }else{
                    Toast.makeText(v.getContext(), "No Memo Selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updateRecyclerView() {
        if (controller.getMemoList() == null){
            System.out.println("----ERROR IN MainActivity.java IN updateRecyclerView----");
            System.out.println("ERROR: MEMO LIST IS NULL");
            return;
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, controller.getMemoList());
        binding.memoList.setHasFixedSize(true);
        binding.memoList.setLayoutManager(new LinearLayoutManager(this));
        binding.memoList.setAdapter(adapter);
    }

}