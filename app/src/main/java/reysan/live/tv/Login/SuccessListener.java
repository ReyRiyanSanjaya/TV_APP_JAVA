package reysan.live.tv.Login;


 

public interface SuccessListener {
    void onStart();
    void onEnd(String success, String registerSuccess, String message);
}