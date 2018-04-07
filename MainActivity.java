
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    
    // Path to PHP script
    String irl = "http://192.168.33.80/sign.php";
  
    TextView forgot,register,signup,textview2,textview4;
    EditText username, password;
    String lolu;
    Button button;
    String  myemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        forgot = (TextView) findViewById(R.id.forgot);
        register = (TextView) findViewById(R.id.register);
        signup = (TextView) findViewById(R.id.signup);
        textview2 = (TextView)findViewById(R.id.textView2);
        textview4 = (TextView)findViewById(R.id.textView4);
        username = (EditText) findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
 }
 
    // User clicks Sign up option if he doesnt have an account
    public void signUpClicked (View view){
        Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(i);

    }

    // Login button clicked
    public void loginClicked (View view){
        String user = username.getText().toString().toLowerCase();
        String pass = password.getText().toString();
        if (user.matches("(.*)@(.*).(.*)")){
            if (user.equals(""))
            {
                Toast z = Toast.makeText(getApplicationContext(),"Email cannot be left empty",Toast.LENGTH_LONG);
                z.setGravity(Gravity.CENTER,0,0);
                z.show();
            }
            else if (pass.equals(""))
            {
                Toast q = Toast.makeText(getApplicationContext(),"Password cannot be left empty",Toast.LENGTH_LONG);
                q.setGravity(Gravity.CENTER,0,0);
                q.show();
            }
            else {
                sendToServer(user, pass);
            }
        }
        else
        {
            Toast v = Toast.makeText(getApplicationContext(),"Invalid email",Toast.LENGTH_LONG);
            v.setGravity(Gravity.CENTER,0,0);
            v.show();
        }

        password.setText("");
    }

    public void sendToServer (final String userN, final String passP){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, irl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("name") && jsonObject.has("email"))
                    {   button.setEnabled(true);
                        button.setText("LOGIN");
                    lolu = jsonObject.getString("name");
                  myemail = jsonObject.getString("email");
                  Toast v = Toast.makeText(getApplicationContext(),"Thank "+lolu+", You are now logged in", Toast.LENGTH_LONG);
                        v.setGravity(Gravity.CENTER,0,0);
                        v.show();
                      }
                    else if(jsonObject.has("response")){
                        button.setEnabled(true);
                        button.setText("LOGIN");
                        String bab = jsonObject.getString("response");
                        Toast v = Toast.makeText(getApplicationContext(),bab,Toast.LENGTH_LONG);
                        v.setGravity(Gravity.CENTER,0,0);
                        v.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                }  else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                if ( message!=null) {

                        // Show timeout error message
                        Toast a =  Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG);
                        a.setGravity(Gravity.CENTER,0,0);
                        a.show();
                        button.setEnabled(true);
                        button.setText("LOGIN");
                    }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user",userN);
                params.put("pass",passP);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        button.setEnabled(false);
        button.setText("Connecting ...");


    }

    // Clicked if user forgets password
    public void forgot (View view){
        Intent kl = new Intent(getApplicationContext(),retrieve.class);
        startActivity(kl);
    }


}



