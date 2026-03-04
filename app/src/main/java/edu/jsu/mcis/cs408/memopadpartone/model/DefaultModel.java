package edu.jsu.mcis.cs408.memopadpartone.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

import edu.jsu.mcis.cs408.memopadpartone.Database.Memo;
import edu.jsu.mcis.cs408.memopadpartone.Database.DatabaseHandler;
import edu.jsu.mcis.cs408.memopadpartone.controller.DefaultController;

public class DefaultModel extends AbstractModel {

    public static final String TAG = "DefaultModel";

    /*
     * This is a simple implementation of an AbstractModel which encapsulates
     * two text fields, text1 and text2, which (in this example) are each
     * reflected in the View as an EditText field and a TextView label.
     */
    private Context context;
    private DatabaseHandler db;
    private List<Memo> memoList;

    // Default constructor that sets up our DBHandler
    public DefaultModel(Context c){
        this.context = c;
        this.db = new DatabaseHandler(this.context, null, null, 1);
    }

    /*
     * Initialize the model elements to known default values.  We use the setter
     * methods instead of changing the values directly so that these changes are
     * properly announced to the Controller, and so that the Views can be updated
     * accordingly.
     */


    public void initDefault() {
        this.memoList = db.getAllMemosAsList();
    }

    /*
     * Simple getter methods for text1 and text2
     */

    public List<Memo> getMemoList() {
        return memoList;
    }


    /*
     * Setters for text1 and text2.  Notice that, in addition to changing the
     * values, these methods announce the change to the controller by firing a
     * PropertyChange event.  Any registered AbstractController subclasses will
     * receive this event, and will propagate it to all registered Views so that
     * they can update themselves accordingly.
     */

    public void setMemoList(String memo) {

        List<Memo> oldList = this.memoList;

        db.addMemo(new Memo(memo));
        List<Memo> newList = db.getAllMemosAsList();

        this.memoList = newList;

        Log.i(TAG, "Text1 Change: From " + oldList + " to " + newList);
        firePropertyChange(DefaultController.ELEMENT_MEMO_LIST_PROPERTY, oldList, newList);
    }

}
