package com.l2l.androided.mh122354.comp380;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView signUpTextView;
    Button   loginSignUpButton;
    EditText confirmEditText;
    boolean isLoginPage= true;
    EditText passwordEditText;
    EditText usernameEditText;
    boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signUpTextView = (TextView)findViewById(R.id.signupLoginTextView);
        loginSignUpButton = (Button)findViewById(R.id.loginSignupButton);
        confirmEditText = (EditText)findViewById(R.id.confirmEditText);
        passwordEditText= (EditText)findViewById(R.id.passwordEditText);
        usernameEditText= (EditText)findViewById(R.id.usernameEditText);


    }

    public void signUpLoginTextClick(View view) {

        if (isLoginPage) {
            signUpTextView.setText(R.string.login);
            loginSignUpButton.setText(R.string.signup);
            confirmEditText.setVisibility(View.VISIBLE);
            isLoginPage = false;

        } else {

            signUpTextView.setText(R.string.signup);
            loginSignUpButton.setText(R.string.login);
            isLoginPage = true;
            confirmEditText.setVisibility(View.INVISIBLE);


        }
    }
    public void signUpLoginButtonClick(View view){

        if(isLoginPage){

            //TODO Check with database to login user
        }

        else {

            EditText[] editTextEntries = {usernameEditText,passwordEditText,
                    confirmEditText};
            if(validateSignUp(editTextEntries)){
                Intent i = new Intent(getBaseContext(),SelectFuncActivity.class);
                startActivity(i);
            }
    }

    }

    public boolean validateSignUp(EditText[] entries){



        if(entries[0].getText().toString().equals("") || entries[1].getText().toString().equals("")
                || entries[2].getText().toString().equals("")){
            isValid=false;


            for (EditText entry : entries) {
                entry.setText("");
            }
            Toast.makeText(getBaseContext(), R.string.emptySignup, Toast.LENGTH_LONG).show();


        } else if ( !(entries[1].getText().toString().equals(entries[2].getText().toString()))){

                isValid=false;
            Toast.makeText(getBaseContext(),R.string.nonMatchPassword, Toast.LENGTH_LONG).show();
            entries[1].setText("");
            entries[2].setText("");
            }
            return true;
        }

    public void cameraTest(View view){

        Intent i = new Intent(this,CameraActivity.class);
        startActivity(i);
    }


    }


