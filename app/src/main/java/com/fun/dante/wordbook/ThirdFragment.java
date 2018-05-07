package com.fun.dante.wordbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThirdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ThirdFragment extends Fragment {

    MyDatabase dbHelper;
    private final static String Word="Word";
    private final static String property="property";
    private final static String american="american";
    ArrayList wordItem = new ArrayList();
    ArrayList propertyItem = new ArrayList();
    ArrayList americanItem = new ArrayList();
    private OnFragmentInteractionListener mListener;

    public ThirdFragment() {
        // Required empty public constructor
    }



    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new MyDatabase(getContext(),"DanteWord.db",null,1);
        dbHelper.getWritableDatabase();
        dbHelper.query_entire(wordItem,propertyItem,americanItem);

        List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();
        for(int i=0;i<wordItem.size();i++) {
            Map<String,Object> item=new HashMap<String,Object>();
            item.put(Word,wordItem.get(i));
            item.put(property, propertyItem.get(i));
            item.put(american, americanItem.get(i));
            items.add(item);
        }
        SimpleAdapter adapter=new SimpleAdapter(getContext(),items,R.layout.item,new String[]{Word,property,american},new int[]{R.id.txtword,R.id.txtproperty,R.id.txtamerican});
        ListView list=(ListView)getActivity().findViewById(R.id.Single_M);
        list.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
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
