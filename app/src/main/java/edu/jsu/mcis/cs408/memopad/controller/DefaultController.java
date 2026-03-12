package edu.jsu.mcis.cs408.memopad.controller;

import java.util.List;

import edu.jsu.mcis.cs408.memopad.model.Memo;
import edu.jsu.mcis.cs408.memopad.model.DefaultModel;

public class DefaultController extends AbstractController
{

    /*
     * These static property names are used as the identifiers for the elements
     * of the Models and Views which may need to be updated.  These updates can
     * be a result of changes to the Model which must be reflected in the View,
     * or a result of changes to the View (in response to user input) which must
     * be reflected in the Model.
     */
    public static final String ADD_MEMO_PROPERTY = "AddMemo";
    public static final String DELETE_MEMO_PROPERTY = "DeleteMemo";
    /*
     * This is the change method which corresponds to ELEMENT_MEMO_LIST_PROPERTY.
     * It receives the new data for the Model, and invokes "setModelProperty()"
     * (inherited from AbstractController) so that the proper Model can be found
     * and updated properly.
     */

    // Method to inform the model to add a new memo
    public void changeElementMemoList(String memo) {
        setModelProperty(ADD_MEMO_PROPERTY, memo);
    }

    // Method to inform the model to delete an existing memo
    public void changeElementDeleteMemo(int index) {
        setModelProperty(DELETE_MEMO_PROPERTY, index);
    }

    // Method to retrieve memos from the model
    // (primarily used in the MainActivity's updateRecyclerView() method)
    public List<Memo> getMemoList(){
        if (getModelZero() instanceof DefaultModel){
            DefaultModel m = (DefaultModel) getModelZero();
            return m.getMemoList();
        }
        System.out.println("----ERROR IN DefaultController.java IN getMemoList()----");
        System.out.println("ERROR: MODEL ZERO IS NOT AN INSTANCE OF DefaultModel");
        return null;
    }
}
