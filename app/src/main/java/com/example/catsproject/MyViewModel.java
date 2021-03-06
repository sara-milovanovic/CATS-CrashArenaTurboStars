package com.example.catsproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.catsproject.db.Components;
import com.example.catsproject.db.Player;
import com.example.catsproject.db.UserBoxes;
import com.example.catsproject.db.async_tasks.AsyncTaskAddComponentsToPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskGetComponents;
import com.example.catsproject.db.async_tasks.AsyncTaskGetComponents2;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel {
    MutableLiveData<ArrayList<String>> players=new MutableLiveData<>();
    ArrayList<String> pl=new ArrayList<String>();
    MutableLiveData<String> player=new MutableLiveData<>();
    MutableLiveData<String> password2=new MutableLiveData<>();//ukucan password
    MutableLiveData<String> password=new MutableLiveData<>();
    MutableLiveData<Boolean> music=new MutableLiveData<>();
    MutableLiveData<Boolean> player_controls=new MutableLiveData<>();
    MutableLiveData<Boolean> passwordUpdated=new MutableLiveData<>();
    MutableLiveData<ArrayList<Boolean>> components=new MutableLiveData<>(); //komponente koje ima trenutni korisnik
    MutableLiveData<Integer> currentPlayerId=new MutableLiveData<>();
    MutableLiveData<Integer> heart=new MutableLiveData<>();
    MutableLiveData<Integer> heart2=new MutableLiveData<>();
    MutableLiveData<Integer> thunder=new MutableLiveData<>();
    MutableLiveData<Integer> sword=new MutableLiveData<>();
    MutableLiveData<List<Components>> currentPlayerComponents=new MutableLiveData<>();
    MutableLiveData<List<UserBoxes>> currentPlayerBoxes=new MutableLiveData<>();
    MutableLiveData<List<Components>> currentPlayerAttachedComponents=new MutableLiveData<>();
    MutableLiveData<Vehicle> vehicle=new MutableLiveData<>();
    MutableLiveData<Context> context=new MutableLiveData<>();
    MutableLiveData<Activity> activity=new MutableLiveData<>();
    MutableLiveData<Integer> player1Health=new MutableLiveData<>();
    MutableLiveData<Integer> player2Health=new MutableLiveData<>();
    MutableLiveData<Boolean> go=new MutableLiveData<>();
    MutableLiveData<Boolean> generateRocket=new MutableLiveData<>();
    MutableLiveData<Boolean> userGenerateRocket=new MutableLiveData<>();
    MutableLiveData<Boolean> playedAfterLogin=new MutableLiveData<>();
    MutableLiveData<Integer> generateBox=new MutableLiveData<>();
    MutableLiveData<Integer> wins=new MutableLiveData<>();
    MutableLiveData<Integer> win=new MutableLiveData<>();

    MutableLiveData<ArrayList<Integer>> timeRemaining=new MutableLiveData<ArrayList<Integer>>();
    MutableLiveData<ArrayList<Boolean>> boxActive=new MutableLiveData<ArrayList<Boolean>>();
    MutableLiveData<ArrayList<Integer>> boxTime=new MutableLiveData<ArrayList<Integer>>();

    public MyViewModel(){
        this.music.setValue(true);
        this.player_controls.setValue(false);
        this.players.setValue(new ArrayList<String>());

        this.components.setValue(new ArrayList<Boolean>());
        for(int i=0;i<6;i++){
            this.components.getValue().add(false);
            this.components.setValue(this.components.getValue());
        }

        this.currentPlayerComponents.setValue(new ArrayList<Components>());

        this.boxActive.setValue(new ArrayList<Boolean>());
        this.boxTime.setValue(new ArrayList<Integer>());
        this.timeRemaining.setValue(new ArrayList<Integer>());
        for(int i=0;i<4;i++){
            timeRemaining.getValue().add(0);
            boxActive.getValue().add(false);
            boxTime.getValue().add(0);
        }

        timeRemaining.setValue(timeRemaining.getValue());
        boxActive.setValue(boxActive.getValue());

        new GetPlayersAsyncTask(this).execute();

        passwordUpdated=new MutableLiveData<>();
        passwordUpdated.setValue(false);
        password=new MutableLiveData<>();
        password.setValue("");
        go.setValue(false);



    }

    public void activateBox(){
        for(int i=0;i<4;i++){
            if(!boxActive.getValue().get(i)){
                boxActive.getValue().set(i,true);
                break;
            }
        }
        boxActive.setValue(boxActive.getValue());
    }

    public void postActivateBox(){
        for(int i=0;i<4;i++){
            if(!boxActive.getValue().get(i)){
                boxActive.getValue().set(i,true);
                break;
            }
        }
        boxActive.postValue(boxActive.getValue());
    }

    public MutableLiveData<ArrayList<Integer>> getBoxTime() {
        return boxTime;
    }

    public void setBoxTime(MutableLiveData<ArrayList<Integer>> boxTime) {
        this.boxTime = boxTime;
    }

    public void postWins(int w){
        wins.postValue(w);
    }

    public void setWins(int w){
        wins.setValue(w);
    }

    public MutableLiveData<Integer> getWins() {
        return wins;
    }

    public void setWins(MutableLiveData<Integer> wins) {
        this.wins = wins;
    }

    public void postToGenerateBox(Integer i){
        this.generateBox.postValue(i);
    }

    public void setToGenerateBox(Integer i){
        this.generateBox.setValue(i);
    }

    public MutableLiveData<Integer> getGenerateBox() {
        return generateBox;
    }

    public void postCurrentPlayerBoxes(List<UserBoxes> l){
        currentPlayerBoxes.postValue(l);
    }

    public MutableLiveData<List<UserBoxes>> getCurrentPlayerBoxes() {
        return currentPlayerBoxes;
    }

    public void setCurrentPlayerBoxes(MutableLiveData<List<UserBoxes>> currentPlayerBoxes) {
        this.currentPlayerBoxes = currentPlayerBoxes;
    }

    public void setGenerateBox(MutableLiveData<Integer> generateBox) {
        this.generateBox = generateBox;
    }

    public void setPlayed(boolean f){
        playedAfterLogin.setValue(f);
    }

    public void postWin(int w){
        win.postValue(w);
    }

    public void setWin(int w){
        win.setValue(w);
    }

    public MutableLiveData<Integer> getWin() {
        return win;
    }

    public void setWin(MutableLiveData<Integer> win) {
        this.win = win;
    }

    public void postPlayed(boolean f){
        playedAfterLogin.postValue(f);
    }

    public MutableLiveData<Boolean> getPlayedAfterLogin() {
        return playedAfterLogin;
    }

    public void setPlayedAfterLogin(MutableLiveData<Boolean> playedAfterLogin) {
        this.playedAfterLogin = playedAfterLogin;
    }

    public MutableLiveData<Context> getContext() {
        return context;
    }

    public void setContext(MutableLiveData<Context> context) {
        this.context = context;
    }

    public MutableLiveData<Activity> getActivity() {
        return activity;
    }

    public void setActivity(Activity a){
        activity.setValue(a);
    }

    public MutableLiveData<Integer> getPlayer1Health() {
        return player1Health;
    }

    public MutableLiveData<Boolean> getGo() {
        return go;
    }

    public void setGo(MutableLiveData<Boolean> go) {
        this.go = go;
    }
    public void setGo(boolean go) {
        this.go.setValue(go);
    }
    public void postGo(boolean go) {
        this.go.postValue(go);
    }
    public void setPlayer1Health(MutableLiveData<Integer> player1Health) {
        this.player1Health = player1Health;
    }

    public void setPlayer1Health(Integer player1Health) {
        this.player1Health.setValue(player1Health);
    }

    public void setPlayer2Health(Integer player2Health) {
        this.player2Health.setValue(player2Health);
    }

    public MutableLiveData<Integer> getPlayer2Health() {
        return player2Health;
    }

    public void setPlayer2Health(MutableLiveData<Integer> player2Health) {
        this.player2Health = player2Health;
    }

    public MutableLiveData<Boolean> getUserGenerateRocket() {
        return userGenerateRocket;
    }

    public void setUserGenerateRocket(boolean f){
        this.userGenerateRocket.setValue(f);
    }

    public void setUserGenerateRocket(MutableLiveData<Boolean> userGenerateRocket) {
        this.userGenerateRocket = userGenerateRocket;
    }

    public void setActivity(MutableLiveData<Activity> activity) {
        this.activity = activity;
    }

    public void setContext(Context c){
        this.context.setValue(c);
    }

    public MutableLiveData<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(MutableLiveData<Vehicle> vehicle) {
        this.vehicle = vehicle;
    }

    public void setVehicle(Vehicle v){
        this.vehicle.setValue(v);
    }

    public void postVehicle(Vehicle v){
        this.vehicle.postValue(v);
    }

    public void setHeart2(int i){
        this.heart2.setValue(i);
    }

    public void postHeart2(int i){
        this.heart2.postValue(i);
    }
    public void setHeart(int i){
        this.heart.setValue(i);
    }

    public void postHeart(int i){
        this.heart.postValue(i);
    }
    public void postSword(int i){
        this.sword.postValue(i);
    }
    public void postThunder(int i){
        this.thunder.postValue(i);
    }

    public void setSword(int i){
        this.sword.setValue(i);
    }

    public void setThunder(int i){
        this.thunder.setValue(i);
    }

    public MutableLiveData<Integer> getHeart() {
        return heart;
    }
    public MutableLiveData<Integer> getHeart2() {
        return heart2;
    }

    public void setHeart(MutableLiveData<Integer> heart) {
        this.heart = heart;
    }

    public MutableLiveData<Integer> getThunder() {
        return thunder;
    }

    public void setThunder(MutableLiveData<Integer> thunder) {
        this.thunder = thunder;
    }

    public MutableLiveData<Integer> getSword() {
        return sword;
    }

    public void setSword(MutableLiveData<Integer> sword) {
        this.sword = sword;
    }

    public void postPasswordUpdated(Boolean b){
        this.passwordUpdated.postValue(b);
    }

    public void setPasswordUpdated(Boolean b){
        this.passwordUpdated.setValue(b);
    }

    public MutableLiveData<String> getPassword2() {
        return password2;
    }

    public void setPassword2(String p2){
        password2.setValue(p2);
    }

    public void setPassword2(MutableLiveData<String> password2) {
        this.password2 = password2;
    }

    public MutableLiveData<Boolean> getPasswordUpdated() {
        return passwordUpdated;
    }

    public void setPasswordUpdated(MutableLiveData<Boolean> passwordUpdated) {
        this.passwordUpdated = passwordUpdated;
    }

    public MutableLiveData<Boolean> getGenerateRocket() {
        return generateRocket;
    }

    public void setGenerateRocket(MutableLiveData<Boolean> generateRocket) {
        this.generateRocket = generateRocket;
    }

    public void setGenerateRocket(Boolean generateRocket) {
        this.generateRocket.setValue(generateRocket);
    }
    public void postGenerateRocket(Boolean generateRocket) {
        this.generateRocket.postValue(generateRocket);
    }

    public void postPassword(String p){
        this.password.postValue(p);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public MutableLiveData<ArrayList<Integer>> getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(MutableLiveData<ArrayList<Integer>> timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
    public void decTime() {
        for(int i=0;i<4;i++){
            if(this.boxActive.getValue().get(i)){
                timeRemaining.getValue().set(i,new Integer(timeRemaining.getValue().get(i)+1));
            }
        }
        timeRemaining.postValue(timeRemaining.getValue());
    }

    public MutableLiveData<ArrayList<Boolean>> getBoxActive() {
        return boxActive;
    }

    public void setBoxActive(MutableLiveData<ArrayList<Boolean>> boxActive) {
        this.boxActive = boxActive;
    }

    public MutableLiveData<List<Components>> getCurrentPlayerComponents() {
        return currentPlayerComponents;
    }

    public void setCurrentPlayerComponents(MutableLiveData<List<Components>> currentPlayerComponents) {
        this.currentPlayerComponents = currentPlayerComponents;
    }

    public void setCurrentPlayerAttachedComponents(List<Components> currentPlayerComponents) {
        this.currentPlayerAttachedComponents.setValue(currentPlayerComponents);
    }

    public MutableLiveData<List<Components>> getCurrentPlayerAttachedComponents() {
        return currentPlayerAttachedComponents;
    }

    public void setCurrentPlayerAttachedComponents(MutableLiveData<List<Components>> currentPlayerAttachedComponents) {
        this.currentPlayerAttachedComponents = currentPlayerAttachedComponents;
    }

    public void postCurrentPlayerComponents(List<Components> currentPlayerComponents) {
        this.currentPlayerComponents.postValue(currentPlayerComponents);
    }

    public void postCurrentPlayerAttachedComponents(List<Components> currentPlayerComponents) {
        this.currentPlayerAttachedComponents.postValue(currentPlayerComponents);
    }

    public MutableLiveData<Integer> getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(MutableLiveData<Integer> currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public void setCurrentPlayerId(int id){
        currentPlayerId.setValue(id);
    }

    public void postCurrentPlayerId(int id){
        currentPlayerId.postValue(id);
    }

    public void setPlayerControls_my(Boolean p) {
        this.player_controls.setValue(p);
    }

    public void postPlayerControls_my(Boolean p) {
        this.player_controls.postValue(p);
    }

    public void setMusic_my(Boolean music) {
        this.music.setValue(music);
    }

    public void postMusic_my(Boolean music) {
        this.music.postValue(music);
    }

    public MutableLiveData<Boolean> getMusic() {
        return music;
    }

    public void setMusic(MutableLiveData<Boolean> music) {
        this.music = music;
    }

    public MutableLiveData<Boolean> getPlayer_controls() {
        return player_controls;
    }

    public void setPlayer_controls(MutableLiveData<Boolean> player_controls) {
        this.player_controls = player_controls;
    }

    public void addPlayer_my(String p){
        ArrayList<String> tmp=this.players.getValue();
        tmp.add(p);
        this.players.setValue(tmp);
    }

    public MutableLiveData<String> getPlayer() {
        return player;
    }

    public void setPlayer(MutableLiveData<String> player) {
        this.player = player;
    }

    public void setPlayer_my(String player) {
        this.player.setValue(player);
    }

    public void postPlayer_my(String player) {
        this.player.postValue(player);
    }

    public int generateRandomComponent() {
        int id=0;
        Components c;
        do {
            id=(int)(Math.random()*7);
            if(id>6) id=6;
            if(id<1) id=1;
            c=Component.makeFromId(id);
        }
        while(inComponents(c));
        Log.v("KUTIJA************** ",""+c.cid);
        System.out.println("KUTIJA************** "+c.cid);
        currentPlayerComponents.getValue().add(c);
        currentPlayerComponents.setValue(currentPlayerComponents.getValue());
        /**/
        ArrayList<Components> toAdd=new ArrayList<>();
        toAdd.add(c);
        //model.setCurrentPlayerAttachedComponents(components);
        Integer[] toAddArray=new Integer[toAdd.size()];
        for(int i=0;i<toAdd.size();i++){
            toAddArray[i]=toAdd.get(i).cid;
        }
        //update baze
        new AsyncTaskAddComponentsToPlayer(this).execute(toAddArray);
        //dohvatanje iz baze
        /**/
        return id;
    }

    private boolean inComponents(Components c) {
        boolean flag=false;
        for(int i=0;i<this.currentPlayerComponents.getValue().size();i++){
            if(this.currentPlayerComponents.getValue().get(i).cid==c.cid) flag=true;
        }
        return flag;
    }

    public boolean haveWheels() {
        boolean flag=false;
        for(Components c:getCurrentPlayerAttachedComponents().getValue()){
            if(c.cid==3) flag=true;
        }
        return flag;
    }


    private class GetPlayersAsyncTask extends AsyncTask<Void, Void, Void> {

        MyViewModel model;

        public GetPlayersAsyncTask(MyViewModel model){
            this.model=model;
        }

        protected Void doInBackground(Void... urls) {
            List<Player> pl=MainActivity.appDatabase.playerDao().getAll();
            ArrayList<String> n =new ArrayList<>();
            for(int i=0;i<pl.size();i++){
                n.add(pl.get(i).username);
                Log.v("dodato iz baze",pl.get(i).username);
            }

            model.players.postValue(n);
            return null;
        }

    }

    public MutableLiveData<ArrayList<String>> getPlayers() {
        return players;
    }

    public void setPlayers(MutableLiveData<ArrayList<String>> players) {
        this.players = players;
    }

    public ArrayList<String> getPlayers_my(){
        return this.players.getValue();
    }
}
