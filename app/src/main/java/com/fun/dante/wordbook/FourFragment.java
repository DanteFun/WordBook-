package com.fun.dante.wordbook;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * {@link FourFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView list =null;
    String text;
    MyDatabase dbHelper;
    TextView t_view;
    SimpleAdapter adapter;

    Handler handler;
    Runnable runable;
    List<Map<String,Object>> items;

    private final static String Word="Word";
    private final static String property="property";
    ArrayList wordItem = new ArrayList();
    ArrayList propertyItem = new ArrayList();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourFragment newInstance(String param1, String param2) {
        FourFragment fragment = new FourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        t_view = getActivity().findViewById(R.id.ready_txt);
        list = getActivity().findViewById(R.id.vague_list);

        dbHelper = new MyDatabase(getContext(),"DanteWord.db",null,1);
        dbHelper.getWritableDatabase();

        items=new ArrayList<Map<String,Object>>();
        handler = new Handler();
        runable = new Runnable() {
            @Override
            public void run() {
                items.clear();
                wordItem.clear();
                propertyItem.clear();

                adapter=new SimpleAdapter(getContext(),items,R.layout.item,new String[]{Word,property},new int[]{R.id.txtword,R.id.txtproperty});

                list.setAdapter(adapter);
                text = t_view.getText().toString();
                dbHelper.vague_entire(wordItem,propertyItem,text);

                for(int i=0;i<wordItem.size();i++) {
                    Map<String,Object> item=new HashMap<String,Object>();
                    item.put(Word,wordItem.get(i));
                    item.put(property, propertyItem.get(i));
                    items.add(item);
                }
                adapter=new SimpleAdapter(getContext(),items,R.layout.item,new String[]{Word,property},new int[]{R.id.txtword,R.id.txtproperty});
                list.setAdapter(adapter);
                handler.postDelayed(this, 200);
            }
        };
        handler.postDelayed(runable, 200);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_four, container, false);
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
        handler.removeCallbacks(runable);
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
