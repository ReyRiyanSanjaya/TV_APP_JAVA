package reysan.live.tv.interfaces;

import java.util.ArrayList;

import reysan.live.tv.Listltem.Listltem;


 

public interface LatestListener {
    void onStart();
    void onEnd(String success, String verifyStatus, String message, ArrayList<Listltem> arrayListTV);
}
