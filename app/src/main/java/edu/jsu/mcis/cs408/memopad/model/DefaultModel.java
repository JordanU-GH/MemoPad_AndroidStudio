package edu.jsu.mcis.cs408.memopad.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

import edu.jsu.mcis.cs408.memopad.model.dao.DAOFactory;
import edu.jsu.mcis.cs408.memopad.controller.DefaultController;

public class DefaultModel extends AbstractModel {

    public static final String TAG = "DefaultModel";

    // Member variables that allow us to handle database operations as
    // well as store memo data
    private Context context;
    private DAOFactory daoFactory;
    private List<Memo> memoList;

    // Default constructor that sets up our DBHandler
    public DefaultModel(Context c){
        this.context = c;
        this.daoFactory = new DAOFactory(this.context, null, null, 1);
    }

    /*
     * Initialize the model elements to known default values.
     */


    public void initDefault() {
        this.memoList = daoFactory.getMemoDao().list();
    }

    /*
     * Simple getter method for memoList
     */

    public List<Memo> getMemoList() {
        return memoList;
    }


    /*
     * Setter for memoList.  Notice that, in addition to changing the
     * values, these methods announce the change to the controller by firing a
     * PropertyChange event.  Any registered AbstractController subclasses will
     * receive this event, and will propagate it to all registered Views so that
     * they can update themselves accordingly.
     */

    public void setAddMemo(String memo) {
        List<Memo> oldList = this.memoList;

        daoFactory.getMemoDao().create(new Memo(memo));
        List<Memo> newList = daoFactory.getMemoDao().list();

        Log.i(TAG, "Text1 Change: From " + oldList + " to " + newList);
        firePropertyChange(DefaultController.ADD_MEMO_PROPERTY, oldList, newList);
    }

    public void setDeleteMemo(int id) {
        List<Memo> oldList = this.memoList;

        daoFactory.getMemoDao().delete(id);
        List<Memo> newList = daoFactory.getMemoDao().list();

        Log.i(TAG, "Text1 Change: From " + oldList + " to " + newList);
        firePropertyChange(DefaultController.ADD_MEMO_PROPERTY, oldList, newList);
    }

}
