package com.example.rvakash.swipetrail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

public class Ios extends Fragment {

    CheckBox check_box,check_box2,check_box3,check_box4;
    Button sendbutton;
    TextView input;
    TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View ios = inflater.inflate(R.layout.ios_frag, container, false);

        ((TextView) ios.findViewById(R.id.textView)).setText("In templates");
        check_box = (CheckBox) ios.findViewById(R.id.checkBox);
        check_box2 = (CheckBox) ios.findViewById(R.id.checkBox2);
        check_box3 = (CheckBox) ios.findViewById(R.id.checkBox3);
        check_box4 = (CheckBox) ios.findViewById(R.id.checkBox4);
        sendbutton = (Button) ios.findViewById(R.id.send);
        message = (TextView) ios.findViewById(R.id.input);
        //When send button is clicked send the checked template
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check which checkBox is ticked and send it
                if (check_box.isChecked()) {

//                    if (flagtrue.equals(received_1)) {
                        SendClick();
                        Toast toast = new Toast(getActivity().getApplicationContext());
                        Toast.makeText(getActivity().getApplicationContext(), "sent:" + input.getText(), toast.LENGTH_LONG).show();
//                    } else {
//                        Toast toast = new Toast(getApplicationContext());
//                        Toast.makeText(Second_Activity.this, "Not sent", toast.LENGTH_LONG).show();
//                    }
                }
                if (check_box2.isChecked()) {

//                    if (flagtrue.equals(received_1)) {
                        SendClick();
                        Toast toast = new Toast(getActivity().getApplicationContext());
                        Toast.makeText(getActivity().getApplicationContext(), "sent:" + input.getText(), toast.LENGTH_LONG).show();
//                    } else {
//                        Toast toast = new Toast(getApplicationContext());
//                        Toast.makeText(Second_Activity.this, "Not sent", toast.LENGTH_LONG).show();
//                    }
                }
                if (check_box3.isChecked()) {
//                    if (flagtrue.equals(received_1)) {
                        SendClick();
                        Toast toast = new Toast(getActivity().getApplicationContext());
                        Toast.makeText(getActivity().getApplicationContext(), "sent:" + input.getText(), toast.LENGTH_LONG).show();
//                    } else {
//                        Toast toast = new Toast(getApplicationContext());
//                        Toast.makeText(Second_Activity.this, "Not sent", toast.LENGTH_LONG).show();
//                    }
                }
                if (check_box4.isChecked()) {
//                    if (flagtrue.equals(received_1)) {
                        SendClick();
                        Toast toast = new Toast(getActivity().getApplicationContext());
                        Toast.makeText(getActivity().getApplicationContext(), "sent:" + input.getText(), toast.LENGTH_LONG).show();
//                    } else {
//                        Toast toast = new Toast(getApplicationContext());
//                        Toast.makeText(Second_Activity.this, "Not sent", toast.LENGTH_LONG).show();
//                    }
                }
            }
        });

        //Tick mark the checkBox which is clicked and remove tick on others
        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box.isChecked()) {
                    input = (TextView) ios.findViewById(R.id.checkBox);
                    message.setText(input.getText());
                    check_box2.setChecked(false);
                    check_box3.setChecked(false);
                    check_box4.setChecked(false);
                }
            }
        });

        check_box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box2.isChecked()) {
                    input = (TextView) ios.findViewById(R.id.checkBox2);
                    message.setText(input.getText());
                    check_box.setChecked(false);
                    check_box3.setChecked(false);
                    check_box4.setChecked(false);
                }
            }
        });

        check_box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box3.isChecked()) {
                    input = (TextView) ios.findViewById(R.id.checkBox3);
                    message.setText(input.getText());
                    check_box.setChecked(false);
                    check_box2.setChecked(false);
                    check_box4.setChecked(false);
                }
            }
        });

        check_box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box4.isChecked()) {
                    input = (TextView) ios.findViewById(R.id.checkBox4);
                    message.setText(input.getText());
                    check_box.setChecked(false);
                    check_box2.setChecked(false);
                    check_box3.setChecked(false);
                }
            }
        });

        return ios;
    }

    public void SendClick(){
//        Android fragment = new Android();
//        Bundle bundle = new Bundle();
//        bundle.putString("sent",input.getText().toString());
//        fragment.setArguments(bundle);
//        Toast toast = new Toast(getActivity().getApplicationContext());
//        Toast.makeText(getActivity().getApplicationContext(), "inSendClick: " + input.getText().toString(), toast.LENGTH_LONG).show();


    }

}
