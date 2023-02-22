package com.training.drcalory.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.training.drcalory.ApiHandler.Apihandler;
import com.training.drcalory.Login;
import com.training.drcalory.MainActivity;
import com.training.drcalory.Model.LoginModel;
import com.training.drcalory.Model.askModel;
import com.training.drcalory.Model.getReplyModel;
import com.training.drcalory.R;

import org.apache.commons.lang3.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class AskDoctor_Activity extends Fragment {

    //declaring variable
    private EditText etQuestion,etQues1,etReply;
    private Button btnask,btnref;
    String reply;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.activity_ask_doctor, container, false);

        //mapping variable with the xml components
        etQuestion = root.findViewById(R.id.etQuestion);
        etQues1 = root.findViewById(R.id.etQuest1);
        etReply = root.findViewById(R.id.etReply);
        btnref = root.findViewById(R.id.btnref);

        btnask = root.findViewById(R.id.btnask);

        //shared preference for storing the user login detail
        SharedPreferences pref = getActivity().getSharedPreferences("Pref", 0);
        final String mail = pref.getString("sharedEmail", "null");

        //on click listener of submit button
        btnask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String question = etQuestion.getText().toString();

                //Connection manager, so that we can send our data into the database
                ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
                }

                else {

                    //api handler, to send the request url and receive the response
                    Apihandler.getApiService().ask(mail,question, new Callback<askModel>() {
                        //if the return is success, Success method will be called
                        //and a message, message sent will be toasted to user
                        @Override
                        public void success(askModel askmodel, Response response) {
                            Toast.makeText(getContext(), "Message sent to Dr", Toast.LENGTH_SHORT).show();
                        }
                        //if the api call fails, Failure method will be called
                        //and a message, failed will be toasted to user
                        @Override
                        public void failure(RetrofitError error) {
                            String er = error.toString();
                            Toast.makeText(getContext(), "Failed" + er, Toast.LENGTH_SHORT).show();
                            Log.d("<>","in fail");
                        }
                    });
                }

                //after the success/failure, we set the text field back to blank
                etQues1.setText(question);
                etQuestion.setText("");
                etReply.setText("");

            }
        });




        btnref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String question = etQuestion.getText().toString();

                ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
                }

                else {

                    Apihandler.getApiService().getReply(mail, new Callback<getReplyModel>() {
                        @Override
                        public void success(getReplyModel getreplymodel, Response response) {

                            String value = new String(((TypedByteArray) response.getBody()).getBytes());
                            reply = StringUtils.substringBetween(value,"reply\":\"" , "\",");
                            etReply.setText(reply);

                            String ques = StringUtils.substringBetween(value,"question\":\"" , "\"}");
                            etQues1.setText(ques);

                            Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void failure(RetrofitError error) {
                            String er = error.toString();
                            Toast.makeText(getContext(), "Refresh Failed" + er, Toast.LENGTH_SHORT).show();
                            Log.d("<>","in fail");
                        }
                    });
                }

            }
        });

        return root;
    }
}
