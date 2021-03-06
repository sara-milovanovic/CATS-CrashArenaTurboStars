package com.example.catsproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.catsproject.db.async_tasks.AsyncTaskGetPlayerId;
import com.example.catsproject.db.async_tasks.AsyncTaskGetPlayerPassword;

import java.util.ArrayList;

public class StartFragment extends Fragment {

    MyViewModel model;
    ArrayAdapter<String> adapter;
    int i=0;
    private String m_Text = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.start_fragment, container, false);

        LinearLayout parent=v.findViewById(R.id.startFragment);
        parent.setBackgroundResource(R.drawable.bg);

        ImageButton registration=v.findViewById(R.id.registration);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_startFragment_to_registrationFragment);
            }
        });

        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);

        final Spinner choose_player=v.findViewById(R.id.choose_player);

        final ArrayList<String> players_=model.players.getValue();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,players_);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_player.setAdapter(adapter);
/*
        Button canvaas=view.findViewById(R.id.button);
        canvaas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTaskGetComponents(model,view).execute(model.getCurrentPlayerId().getValue());
                //Navigation.findNavController(view).navigate(R.id.canvasFragment);
            }
        });*/

        choose_player.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                model.setPlayer_my(adapter.getItem(0));
                if(i>0 && adapter.getItem(i)!=model.getPlayer().getValue()){
                    Log.v("Player: ",adapter.getItem(i));
                    model.setPlayer_my(adapter.getItem(i));
                    new AsyncTaskGetPlayerId(model,view,false).execute(adapter.getItem(i));
                    /**********************************/


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Title");

// Set up the input
                    final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            System.out.println("m_Text "+m_Text);
                            model.setPassword2(m_Text);
                            //provera passworda
                            //ako je tacan
                            new AsyncTaskGetPlayerPassword(model).execute(model.getCurrentPlayerId().getValue());

                            model.passwordUpdated.observe(getActivity(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean aBoolean) {
                                    if(aBoolean){

                                        if(correctPassword(model.password2.getValue())){
                                            model.setPlayed(false);
                                            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_garageFragment);
                                        }
                                        else{
                                            choose_player.setSelection(0);
                                            dialog.cancel();
                                        }
                                    }
                                    else{
                                        dialog.cancel();
                                    }
                                }
                            });
                        }
                    });

                    builder.show();

                    /**********************************/


                }
                i++;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        model.players.observe(getActivity(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,strings);
                choose_player.setAdapter(adapter);

            }
        });

        return v;
    }

    private boolean correctPassword(String m_text) {
        boolean correct=false;
        //new AsyncTaskGetPlayerPassword(model).execute(model.getCurrentPlayerId().getValue());
        while(!model.passwordUpdated.getValue()){

        }
        model.setPasswordUpdated(false);
        if(m_text.equals(model.password.getValue())){
            return true;
        }
        return false;
    }
}
