package com.example.rvakash.swipetrail;

import android.app.Activity;
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
    CharSequence input1;
    TextView message;
    OnCheckBoxClicked onCheckBoxClicked;
//    EditText editText;
//    Button button;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         final View ios = inflater.inflate(R.layout.ios_frag, container, false);

        ((TextView) ios.findViewById(R.id.textView)).setText("In templates");
        check_box = (CheckBox) ios.findViewById(R.id.checkBox);
        check_box2 = (CheckBox) ios.findViewById(R.id.checkBox2);
        check_box3 = (CheckBox) ios.findViewById(R.id.checkBox3);
        check_box4 = (CheckBox) ios.findViewById(R.id.checkBox4);
//        editText = (EditText) ios.findViewById(R.id.editText);
//        button = (Button) ios.findViewById(R.id.button);
//        editText.setVisibility(View.INVISIBLE);
//        button.setVisibility(View.INVISIBLE);

        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box.isChecked()) {
                    input = (TextView) ios.findViewById(R.id.checkBox);
                    input1 = input.getText();
                    check_box2.setChecked(false);
                    check_box3.setChecked(false);
                    check_box4.setChecked(false);
                    onCheckBoxClicked.CheckBoxClicked(input1.toString());
//                    final View mainactivity = inflater.inflate(R.layout.activity_main, container, false);
//                    message = (TextView) mainactivity.findViewById(R.id.input);
//                    message.setText(input1);
                }
            }
        });

        check_box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box2.isChecked()) {
                    input = (TextView) ios.findViewById(R.id.checkBox2);
                    input1 = input.getText();
//                    message.setText(input.getText());
                    check_box.setChecked(false);
                    check_box3.setChecked(false);
                    check_box4.setChecked(false);
                    onCheckBoxClicked.CheckBoxClicked(input1.toString());
                }
            }
        });

        check_box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box3.isChecked()) {
                    input = (TextView) ios.findViewById(R.id.checkBox3);
                    input1 = input.getText();
//                    message.setText(input.getText());
                    check_box.setChecked(false);
                    check_box2.setChecked(false);
                    check_box4.setChecked(false);
                    onCheckBoxClicked.CheckBoxClicked(input1.toString());
                }
            }
        });

        check_box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box4.isChecked()) {
                    input = (TextView) ios.findViewById(R.id.checkBox4);
                    input1 = input.getText();
//                    message.setText(input.getText());
                    check_box.setChecked(false);
                    check_box2.setChecked(false);
                    check_box3.setChecked(false);
                    onCheckBoxClicked.CheckBoxClicked(input1.toString());
                }
            }
        });


        check_box.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                check_box.setVisibility(View.INVISIBLE);
//                check_box.setText("");
//                editText.setVisibility(View.VISIBLE);
//                editText.performClick();
//                button.setVisibility(View.VISIBLE);
                check_box.setText("");
                onCheckBoxClicked.CheckBoxClicked("check_boxLongClick" + input1.toString());
                return false;
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                check_box.setText(editText.getText());
//                editText.setVisibility(View.INVISIBLE);
//                button.setVisibility(View.INVISIBLE);
//            }
//        });
        return ios;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onCheckBoxClicked = (OnCheckBoxClicked)activity;
    }

    public interface OnCheckBoxClicked{
        public void CheckBoxClicked(String checkboxname);
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
