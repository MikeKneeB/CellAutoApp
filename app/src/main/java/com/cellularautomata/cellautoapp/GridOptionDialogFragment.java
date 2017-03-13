package com.cellularautomata.cellautoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashSet;
import java.util.InputMismatchException;

/**
 * Created by mike on 11/03/17.
 */

public class GridOptionDialogFragment extends DialogFragment {

    private GridOptionCallback optionCallback;

    public static GridOptionDialogFragment newInstance(int gridID) {
        GridOptionDialogFragment frag = new GridOptionDialogFragment();
        Bundle args = new Bundle();
        args.putInt("gridID", gridID);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        optionCallback = (GridOptionCallback) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int gridID = getArguments().getInt("gridID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.rules_dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_cell_options, null);

        builder.setView(view);

        builder.setPositiveButton(R.string.rules_dialog_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        HashSet<Integer> surviveList = new HashSet<Integer>();
                        HashSet<Integer> createList = new HashSet<Integer>();

                        long spinnerVal = ((Spinner) view.findViewById(R.id.spinner)).getSelectedItemId();
                        if (spinnerVal == 0) {
                            String surviveString = ((EditText) view.findViewById(R.id.editText)).getText().toString();
                            String createString = ((EditText) view.findViewById(R.id.editText2)).getText().toString();
                            for (int i = 0; i != surviveString.length(); i++) {
                                surviveList.add(Character.getNumericValue(surviveString.charAt(i)));
                            }
                            for (int i = 0; i != createString.length(); i++) {
                                createList.add(Character.getNumericValue(createString.charAt(i)));
                            }
                        } else if (spinnerVal == 1) {
                            surviveList.add(2);
                            surviveList.add(3);
                            createList.add(3);
                        } else if (spinnerVal == 2) {
                            surviveList.add(1);
                            surviveList.add(2);
                            surviveList.add(5);
                            createList.add(3);
                            createList.add(6);
                        } else if (spinnerVal == 3) {
                            surviveList.add(1);
                            createList.add(1);
                        } else if (spinnerVal == 4) {
                            createList.add(2);
                            createList.add(3);
                            createList.add(4);
                        } else if (spinnerVal == 5) {
                            surviveList.add(3);
                            surviveList.add(4);
                            createList.add(3);
                            createList.add(4);
                        } else if (spinnerVal == 6) {
                            surviveList.add(2);
                            surviveList.add(3);
                            createList.add(3);
                            createList.add(6);
                        } else if (spinnerVal == 7) {
                            surviveList.add(1);
                            surviveList.add(2);
                            surviveList.add(3);
                            surviveList.add(4);
                            surviveList.add(5);
                            createList.add(3);
                        } else if (spinnerVal == 8) {
                            surviveList.add(2);
                            surviveList.add(3);
                            surviveList.add(8);
                            createList.add(3);
                            createList.add(5);
                            surviveList.add(7);
                        } else if (spinnerVal == 9) {
                            surviveList.add(2);
                            surviveList.add(3);
                            surviveList.add(4);
                            surviveList.add(5);
                            createList.add(4);
                            createList.add(5);
                            createList.add(6);
                            createList.add(7);
                            createList.add(8);
                        } else if (spinnerVal == 10) {
                            surviveList.add(1);
                            surviveList.add(2);
                            surviveList.add(3);
                            surviveList.add(4);
                            createList.add(3);
                        } else if (spinnerVal == 11) {
                            surviveList.add(1);
                            createList.add(1);
                            createList.add(2);
                            createList.add(3);
                        }
                        optionCallback.passUpOptions(surviveList, createList);
                    }
                });

        builder.setNegativeButton(R.string.rules_dialog_no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

        return builder.create();
    }

    public interface GridOptionCallback {
        void passUpOptions(HashSet<Integer> surviveList, HashSet<Integer> createList);
    }
}
