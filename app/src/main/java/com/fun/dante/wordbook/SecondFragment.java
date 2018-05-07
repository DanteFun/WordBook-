package com.fun.dante.wordbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SecondFragment extends Fragment {

    private List<String>sum;
    private ListView list;
    MyDatabase dbHelper;
    ArrayList wordItem = new ArrayList();
    ArrayAdapter<String> array;
    private OnFragmentInteractionListener mListener;

    int position;
    AdapterView.AdapterContextMenuInfo info;
    EditText edit_word;
    EditText edit_property;
    String edit_word_1;
    String edit_property_1;


    public SecondFragment() {
        // Required empty public constructor
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        dbHelper = new MyDatabase(getContext(),"DanteWord.db",null,1);
        dbHelper.getWritableDatabase();
        wordItem = dbHelper.query_all();
        list = getActivity().findViewById(R.id.display);
        sum = new ArrayList<>();
        for(int i=0;i<wordItem.size();i++)
        {
            sum.add(wordItem.get(i).toString());
        }
        array = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,sum);
        list.setAdapter(array);
        registerForContextMenu(list);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_listview, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Delete:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position =info.position;
                dbHelper.delete((wordItem.get(position)).toString());
                sum.remove(position);
                array.notifyDataSetChanged();
                break;
            case R.id.change:
                dbHelper = new MyDatabase(getContext(),"DanteWord.db",null,1);
                dbHelper.getWritableDatabase();

                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position =info.position;

                LayoutInflater inflater = getLayoutInflater().from(getActivity());
                View view = inflater.inflate(R.layout.update,null);

                edit_word = view.findViewById(R.id.EditWord_);
                edit_property =view.findViewById(R.id.EditProperty_);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setView(inflater.inflate(R.layout.update,null));
                edit_word.setText("SSSSSSAAAAAAAAAAAA");
                builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edit_word_1 = edit_word.getText().toString();
                        edit_property_1 = edit_property.getText().toString();
                        System.out.println(edit_word_1);
                        dbHelper.Single_updata(edit_word_1,edit_property_1);
                        array.notifyDataSetChanged();
                    }
                });
                builder.show();
                break;
        }
        return true;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
