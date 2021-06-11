package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Login extends AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private  String TAG = "Login";
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 1;
    Button to_login;
    Button to_signup;
    public static List<String> emails;
    ImageButton sign_up_email;
    ImageButton imgBtnGoogle;
    boolean n = false;
    boolean one = false;
    boolean two = false;
    boolean ligaR = false;
    String[] botNames;
    String idL;
    TextView loginWithExisting;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static GoogleSignInAccount account;
    private String method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        botNames = new String[]{"Norm", "Tommex", "Mory #", "Ido Levi", "DavidGer411", "Lugi craft", "Clickshite", "מעין ללי", "cohen", "בן סגל", "sculps", "רועי בן ששון", "עדי פ.", "Amir GAO", "y_shtivi",
                "Naorr", "Banditax", "Snek", "אסף צור", "OV3RPOWERED", "BlueZer Ka", "M.A.O.R", "Xtazza", "Asi Glanz", "Talya", "Dolev Harbi", "lia bar yossef", "Amit Beatbox", "Eyal TV", "Timer Barel",
                "pajaMAX", " ASEFA 313", "OMER", "daniel hyman", "topKid", "Bio Gic", "קלפון", "not Shalev", "סברינה", "BEsquadYT", "Eli Yosels82", "Boruto", "rambo gaming", "itay M", "Eyal",
                "Nadav", "merav Lavy", "Merav Levy", "טליה ריינשטיין", "אסף המלך", "ofir", "pugzur", "Daddy Reagen", "copper storm", "gaming", "Infinite games", "עמיחי בר", "Maor hilu gaming", "אילון לוי",
                "אורון נסים", "maor", "ITamaRs", "יואב דורון", "אוריה אלבז", "MERAV KOGAn", "Or Sadot", "יהונתן המלך", "dr nofing", "NeverPlayer", "גיא חיזקיהו", "THE BITON", "Phoantom", "SloKeR", "kfir mazuz",
                "נדב ר", "T-Pro", "Adir Elad", "ronenplay", "Tetchy", "ליאון וליקנסקי", "שילה פרץ", "Ofer Ratzbi", "Leono", "HaMaGNiV", "Yarin Zino", "NamerHacesef", "Erez Solomon", "yair :D",
                "yoav Yehonatan gaming", "alker 1995", "Pounds", "Pounds", "omrist gaming", "shibeshi!", "ofek oved", "Mr pro", "Hadar ben", "שמואל גיימינג", "גיא עניאל", "Henam", "Niv Hamagniv",
                "הלל דה פרו", "itsOhad", "world", "בועז שרעבי", "ofir gaming", "banana man", "ארציאל ישראל", "אני בסדר", "Apple", "Erez Maliri", "J. Posadas"};
        idL = String.valueOf(Math.round(Math.floor(Math.random() * 100000)));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        sign_up_email = (ImageButton) findViewById(R.id.imageButtonMail);
        imgBtnGoogle = (ImageButton) findViewById(R.id.imageButtonGoogle);
        mAuth = FirebaseAuth.getInstance();
        loginWithExisting = (TextView) findViewById(R.id.signInWithExisting);
        //emails = DownloadReadlessons.get_emails();
        /*signInButton = findViewById(R.id.sign_in_button);
        to_signup = (Button) findViewById(R.id.to_sign_up);
        to_login = (Button) findViewById(R.id.to_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, sign_upActivity.class));
            }
        });
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, usernameloginActivity.class));
            }
        });*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        sign_up_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, sign_upActivity.class));
            }
        });
        imgBtnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        loginWithExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, usernameloginActivity.class));
            }
        });

    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(Login.this,"Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e) {
            Toast.makeText(Login.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        //check if the account is null
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
        else{
            Toast.makeText(Login.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser fUser){
        DownloadReadlessons.get_emails(new DownloadReadlessons.ListCallback() {
            @Override
            public void onCallback(List<String> value) {
                emails = value;
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account !=  null){

            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            /*final File newFile = new File(Environment.getExternalStorageDirectory() +"/user");
            if (!newFile.exists()){
                newFile.mkdirs();
                Log.d("Create", "dir");
            }*/
            ReadWrite.write(Login.this.getFilesDir()+File.separator+ "user", fUser.getUid());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            if(!emails.contains(account.getId())) {
                n = true;
                //if (!one) {
                    one = true;
                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference fireBase2 = database.getReference("ligot");
                    //myRef.child(String.valueOf(account.getId())).setValue("a");
                    DatabaseReference user = myRef.child(String.valueOf(fUser.getUid()));
                    user.child("id").setValue(account.getId());
                    user.child("email").setValue(personEmail);
                    user.child("imgUrl").setValue(personPhoto.toString());
                    user.child("name").setValue(personName);
                    user.child("phoneNum").setValue("");
                    user.child("lastLessonD").child("year").setValue(0);
                    user.child("lastLessonD").child("date").setValue(0);
                    user.child("streak").setValue(0);
                    user.child("maxStreak").setValue(0);//
                    user.child("streak freeze").setValue("false");
                    user.child("7streak").setValue(0);
                    user.child("xp").setValue(0);
                    Log.d("Create", "dir");
                    user.child("weekXp").setValue(0);
                    user.child("pyXp").setValue(0);
                    user.child("htmlXp").setValue(0);
                    user.child("shabes").setValue("false");
                    user.child("progress").setValue(Text.getRandomString(5));
                    user.child("start_comp").setValue("");
                    user.child("hasDoneLesson").setValue(false);
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color1 = generator.getRandomColor();
                    user.child("imgC").setValue(color1);
                    user.child("comp_w").setValue("");
                    user.child("comp").setValue("");
                    user.child("ligaType").setValue(1);
                    user.child("comp_time").setValue("1");
                    user.child("friends").setValue("");
                    DownloadReadlessons.get_liga3(new DownloadReadlessons.HashCallback3() {
                        @Override
                        public void onCallback(HashMap<String, ArrayList> value) {
                            StringBuilder name = new StringBuilder();
                            Log.d("final_name", "final_name3");
                            String botWord = "";
                            ArrayList<String> alt_name = value.get("names");
                            for (String id : alt_name) {
                                name.append(id).append(",");
                                if (id.contains("bot")) {
                                    botWord = id + ",";
                                }
                            }
                            if (botWord.length() < 3) {
                                if (!ligaR) {
                                    ligaR = true;
                                    StringBuilder namesNewLiga = new StringBuilder();
                                    for (int i = 0; i < 150; i++) {
                                        if (i % 30 == 0) {
                                            namesNewLiga = new StringBuilder();
                                        }
                                        String idN = String.valueOf(Math.round(Math.floor(Math.random() * 10000)));
                                        String id = "bot_" + idN;
                                        DatabaseReference botUser = myRef.child(id);
                                        ColorGenerator generator = ColorGenerator.MATERIAL;
                                        int color1 = generator.getRandomColor();
                                        botUser.child("imgC").setValue(color1);
                                        botUser.child("xp").setValue(0);
                                        botUser.child("weekXp").setValue(0);
                                        botUser.child("ligaType").setValue((i / 30) + 1);
                                        botUser.child("streak").setValue(0);
                                        Random rand = new Random();
                                        String newName = botNames[rand.nextInt(botNames.length)];
                                        Log.d("final_name", "final_name2");
                                        botUser.child("name").setValue(newName);
                                        if (i == 2) {
                                            namesNewLiga.append(fUser.getUid()).append("-").append(personName).append(",");
                                            fireBase2.child(Math.round(i / 30) + 1 + "-" + idL).setValue(namesNewLiga.toString());
                                        } else {
                                            namesNewLiga.append(id).append("-").append(newName).append(",");
                                            fireBase2.child(Math.round(i / 30) + 1 + "-" + idL).setValue(namesNewLiga.toString());
                                        }
                                        method = "Google";
                                        Bundle bundle = new Bundle();
                                        bundle.putString(FirebaseAnalytics.Param.METHOD, method);
                                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
                                        startActivity(new Intent(Login.this, mainScreen.class));
                                    }
                                }

                            } else {
                                name.append(fUser.getUid()).append("-").append(personName);
                                String final_name = name.toString();
                                Log.d("final_name", "final_name");
                                if (final_name.contains("bot") && alt_name.size() > 28) {
                                    final_name = final_name.replaceAll(botWord, "");
                                }
                                fireBase2.child(String.valueOf( value.get("key").get(0))).setValue(final_name);
                                method = "Google";
                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.METHOD, method);
                                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
                                startActivity(new Intent(Login.this, mainScreen.class));
                            }
                        }
                    });
                //}
        }else{
                startActivity(new Intent(Login.this, mainScreen.class));
        }
            }
        //}
            }
        });

    }
}