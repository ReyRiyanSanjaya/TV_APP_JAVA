package reysan.live.tv.interfaces;

import reysan.live.tv.Listltem.ItemHomeBanner;
import reysan.live.tv.Listltem.Listltem;



import java.util.ArrayList;


 

public interface HomeListener {
    void onStart();
    void onEnd(String success, ArrayList<ItemHomeBanner> arrayListBanner, ArrayList<Listltem> arrayList_latest, ArrayList<Listltem> arrayList_most);
}
