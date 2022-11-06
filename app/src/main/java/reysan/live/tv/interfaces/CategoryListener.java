package reysan.live.tv.interfaces;

import java.util.ArrayList;


import reysan.live.tv.Listltem.Listltem_Category;


 

public interface CategoryListener {
    void onStart();
    void onEnd(String success, ArrayList<Listltem_Category> arrayListCat);
}
