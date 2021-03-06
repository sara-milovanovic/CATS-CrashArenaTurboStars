package com.example.catsproject;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.catsproject.db.async_tasks.AsyncTaskGetComponents2;
import com.example.catsproject.db.async_tasks.AsyncTaskUpdateAtributes;

public class CanvasFragment extends Fragment {

    EditVehicleCustomView editVehicleCustomView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);



        final View v= inflater.inflate(R.layout.canvas_fragment, container, false);
        //Button b=v.findViewById(R.id.button);

        GestureDetector.SimpleOnGestureListener onGestureListener=
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e){return true;}
                };

        final GestureDetector gestureDetector=new GestureDetector(getActivity(),onGestureListener);
        editVehicleCustomView =v.findViewById(R.id.customView);
        final MyViewModel model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        //?
        //new AsyncTaskGetComponents(model,v).execute(model.getCurrentPlayerId().getValue());

        editVehicleCustomView.setModel(model);
        editVehicleCustomView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.v("Pozicija: ",motionEvent.getX()+", "+motionEvent.getY());

                editVehicleCustomView.invalidate();

                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:{
                        if(editVehicleCustomView.componentExist(1)&&(!editVehicleCustomView.placed.get(0) && motionEvent.getX()> EditVehicleCustomView.c_default.get(0).getX() && motionEvent.getX()<200+ EditVehicleCustomView.c_default.get(0).getX()&& motionEvent.getY()> EditVehicleCustomView.c_default.get(0).getY() && motionEvent.getY()<200+ EditVehicleCustomView.c_default.get(0).getY())){
                            editVehicleCustomView.moveBlade();
                        }
                        else {
                            if (editVehicleCustomView.componentExist(1)&&(editVehicleCustomView.placed.get(0) && motionEvent.getX()> EditVehicleCustomView.c.get(0).getX() && motionEvent.getX()<200+ EditVehicleCustomView.c.get(0).getX()&& motionEvent.getY()> EditVehicleCustomView.c.get(0).getY() && motionEvent.getY()<200+ EditVehicleCustomView.c.get(0).getY())) {
                                //vrati deo na mesto
                                editVehicleCustomView.removeWeapon(1);

                            } else {
                                if (editVehicleCustomView.componentExist(2)&&(!editVehicleCustomView.placed.get(1) && motionEvent.getX() > EditVehicleCustomView.c_default.get(1).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(1).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(1).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(1).getY())
                                     ){
                                    editVehicleCustomView.moveForklift();
                                }
                                else{
                                    if(editVehicleCustomView.componentExist(2)&&(editVehicleCustomView.placed.get(1) && motionEvent.getX() > EditVehicleCustomView.c.get(1).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(1).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(1).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(1).getY())) {
                                        editVehicleCustomView.removeWeapon(2);
                                    }
                                    else {
                                        if (editVehicleCustomView.componentExist(3)&&(!editVehicleCustomView.placed.get(2) && motionEvent.getX() > EditVehicleCustomView.c_default.get(2).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(2).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(2).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(2).getY())
                                                ) {
                                            editVehicleCustomView.moveWheels();
                                        }
                                        else{
                                            if(editVehicleCustomView.componentExist(3)&&(editVehicleCustomView.placed.get(2) && motionEvent.getX() > EditVehicleCustomView.c.get(2).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(2).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(2).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(2).getY())){
                                                editVehicleCustomView.removeWeapon(3);
                                            }
                                            else {
                                                if (editVehicleCustomView.componentExist(4)&&(!editVehicleCustomView.placed.get(3) && motionEvent.getX() > EditVehicleCustomView.c_default.get(3).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(3).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(3).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(3).getY())) {
                                                    editVehicleCustomView.moveChainsaw();
                                                }
                                                else{
                                                    if(editVehicleCustomView.componentExist(4)&&(editVehicleCustomView.placed.get(3) && motionEvent.getX() > EditVehicleCustomView.c.get(3).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(3).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(3).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(3).getY())){
                                                        editVehicleCustomView.removeWeapon(4);
                                                    }
                                                    else{
                                                        if (editVehicleCustomView.componentExist(5)&&(!editVehicleCustomView.placed.get(4) && motionEvent.getX() > EditVehicleCustomView.c_default.get(4).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(4).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(4).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(4).getY())) {
                                                            editVehicleCustomView.moveRocket();
                                                        }
                                                        else{
                                                            if(editVehicleCustomView.componentExist(5)&&(editVehicleCustomView.placed.get(4) && motionEvent.getX() > EditVehicleCustomView.c.get(4).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(4).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(4).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(4).getY())){
                                                                editVehicleCustomView.removeWeapon(5);
                                                            }
                                                            else{
                                                                if (editVehicleCustomView.componentExist(6)&&(!editVehicleCustomView.placed.get(5) && motionEvent.getX() > EditVehicleCustomView.c_default.get(5).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(5).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(5).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(5).getY())) {
                                                                    editVehicleCustomView.moveStinger();
                                                                }
                                                               else{
                                                                    if(editVehicleCustomView.componentExist(6)&&(editVehicleCustomView.placed.get(5) && motionEvent.getX() > EditVehicleCustomView.c.get(5).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(5).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(5).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(5).getY())){
                                                                        editVehicleCustomView.removeWeapon(6);
                                                                    }
                                                                   else{
                                                                       if ((EditVehicleCustomView.c.get(0).getX() < 50 && motionEvent.getX() > EditVehicleCustomView.leftC.getX() && motionEvent.getX() < 80 + EditVehicleCustomView.leftC.getX() && motionEvent.getY() > EditVehicleCustomView.leftC.getY() && motionEvent.getY() < 50 + EditVehicleCustomView.leftC.getY())) {
                                                                           editVehicleCustomView.moveLeft();
                                                                       } else {
                                                                           if ((EditVehicleCustomView.c.get(5).getX() > 900 && motionEvent.getX() > EditVehicleCustomView.rightC.getX() && motionEvent.getX() < 80 + EditVehicleCustomView.rightC.getX() && motionEvent.getY() > EditVehicleCustomView.rightC.getY() && motionEvent.getY() < 50 + EditVehicleCustomView.rightC.getY())) {
                                                                              //numOfWeapons-1 zamenjeno sa 5
                                                                               editVehicleCustomView.moveRight();
                                                                           } else {
                                                                               if(motionEvent.getX()>500 && motionEvent.getX()<740 && motionEvent.getY()>610 && motionEvent.getY()<760){
                                                                                   editVehicleCustomView.saveCar();
                                                                                   new AsyncTaskGetComponents2(model).execute(model.getCurrentPlayerId().getValue());
                                                                                   //new AsyncTaskUpdateAtributes(model).execute(model.getHeart().getValue(), model.getSword().getValue(), model.getThunder().getValue());
                                                                                   Navigation.findNavController(view).navigate(R.id.action_canvasFragment_to_garageFragment);
                                                                               }
                                                                               else{
                                                                                   System.out.println("nista");
                                                                               }
                                                                           }
                                                                       }
                                                                   }
                                                               }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }



                                    }
                                }


                            }
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        editVehicleCustomView.moveNothing();
                        editVehicleCustomView.clearLists();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        if((EditVehicleCustomView.c.get(0).getX()>50 &&motionEvent.getX()> EditVehicleCustomView.leftC.getX() && motionEvent.getX()<80+ EditVehicleCustomView.leftC.getX()&& motionEvent.getY()> EditVehicleCustomView.leftC.getY() && motionEvent.getY()<50+ EditVehicleCustomView.leftC.getY())){
                            editVehicleCustomView.moveLeft();
                        }
                        else {
                            if((EditVehicleCustomView.c.get(5).getX()>900 &&  motionEvent.getX()> EditVehicleCustomView.rightC.getX() && motionEvent.getX()<80+ EditVehicleCustomView.rightC.getX()&& motionEvent.getY()> EditVehicleCustomView.rightC.getY() && motionEvent.getY()<50+ EditVehicleCustomView.rightC.getY())){
                                /*CustomView.numOfWeapons-1 zamenjano sa 5*/
                                editVehicleCustomView.moveRight();
                            }
                            else {
                                System.out.println("nista");
                            }
                        }

                        int tActionIndex=motionEvent.getActionIndex();
                        int tPointerCount=motionEvent.getPointerCount();
                        Log.v("cnt",motionEvent.getPointerCount()+"");
                        for(int i=0;i<tPointerCount;i++){
                            EditVehicleCustomView.Coordinate c=new EditVehicleCustomView.Coordinate(motionEvent.getX(i),motionEvent.getY(i));
                            editVehicleCustomView.add(c,motionEvent.getPointerId(i));
                            //Log.v("petlja",i+"");
                        }

                        editVehicleCustomView.invalidate();
                        break;

                    }



                }


                return  true;
            }
        });



        return v;
    }


}
