package com.fun.dante.wordbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import Gson.resolve;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment{
    String word;
    String voiceEnText;
    String voiceAmText;
    String meanText;
    String exampleText;

    private SQLiteDatabase dbRead;
    MyDatabase dbHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        dbHelper = new MyDatabase(getContext(),"DanteWord.db",null,1);
        dbHelper.getWritableDatabase();


        Button Find = getActivity().findViewById(R.id.Find_Button);
        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = getActivity().findViewById(R.id.edit_text);
                final String word = editText.getText().toString();
                final String arr[]={word};


                final String Url_Xml ="http://dict-co.iciba.com/api/dictionary.php?w="+word+"&key=BCD8F81F51CD99B32D6C2D322C2E3DFE";

                try {
                    if(resolve.isEnglish(word))
                    {
                        try{
                            HttpUtil.sendOkHttpRequest(Url_Xml, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Toast.makeText(getActivity(), "获取翻译数据失败！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    final String result = response.body().string();
                                    System.out.println(result);

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            resolve.EnglishToChineseXMLWithPull(result);

                                            SharedPreferences pref = getActivity().getSharedPreferences("JinshanEnglishToChinese", Context.MODE_PRIVATE);

                                            String queryText = pref.getString("queryText", "空");
                                            String voiceEnText = pref.getString("voiceEnText", "空");
                                            String voiceAmText = pref.getString("voiceAmText", "空");
                                            String meanText = pref.getString("meanText", "空");
                                            String exampleText = pref.getString("exampleText", "空");

                                            ListView list = (ListView) getActivity().findViewById(R.id.query);
                                            TextView voiceMsg = (TextView) getActivity().findViewById(R.id.voice_msg);
                                            TextView baseMean = (TextView) getActivity().findViewById(R.id.base_mean);
                                            TextView examples = (TextView) getActivity().findViewById(R.id.related_examples);

                                            examples.setMovementMethod(new ScrollingMovementMethod());
                                            dbHelper.insert(word,voiceEnText,voiceAmText,meanText,exampleText);
                                            voiceMsg.setText("英式发音："+voiceEnText+"\n"+"美式发音："+voiceAmText);
                                            baseMean.setText(meanText);
                                            examples.setText(exampleText);
                                            ArrayAdapter<String> array = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
                                            list.setAdapter(array);
                                            registerForContextMenu(list);
                                        }
                                    });
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "网络连接有问题！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "你输入的单词有问题！！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_add, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Add:
                dbHelper.insert(word,voiceEnText,voiceAmText,meanText,exampleText);
                Toast.makeText(getActivity(), "添加成功！", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
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
