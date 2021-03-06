package com.example.catsproject;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.catsproject.db.async_tasks.AsyncTaskAddPlayer;
import com.example.catsproject.db.Player;
import com.example.catsproject.db.async_tasks.AsyncTaskGetPlayerId;

public class RegistrationFragment extends Fragment {

    MyViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.registration_fragment, container, false);

        LinearLayout parent=view.findViewById(R.id.registrationFragment);
        parent.setBackgroundResource(R.drawable.bg);

        Button add_player=view.findViewById(R.id.add_player);
        final EditText username=view.findViewById(R.id.username);
        final EditText password=view.findViewById(R.id.password);

        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);

        add_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player u=new Player();
                if(username.getText()!=null && password.getText()!=null) {
                    u.username = username.getText().toString();
                    u.password = password.getText().toString();
                    u.heart=85;
                    u.sword=0;
                    u.thunder=30;
                    new AsyncTaskAddPlayer(model).execute(u);
                    //blade i tockovi
                    //new AsyncTaskAddComponentsToPlayer(model).execute(1,3);
                    model.addPlayer_my(u.username);
                    model.postPlayer_my(u.username);
                    new AsyncTaskGetPlayerId(model,view,true).execute(u.username); //?????????????????
                    model.setPlayed(false);
                    //Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_garageFragment);

                }
                else{
                    Toast.makeText(getActivity(),"username and/or password empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
