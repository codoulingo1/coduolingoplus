package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class sign_upActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");
    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    TextInputLayout emailField;
    TextInputLayout passwordField;
    ImageButton mLoginBtn;
    String personPhoto;
    TextInputLayout inp_name;
    private FirebaseAnalytics mFirebaseAnalytics;
    boolean ligaR = false;
    String[] botNames;
    String idL;
    private String method;
    ImageButton backBtn;
    TextView emailError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        botNames = new String[]{"Norm", "Tommex", "Mory #", "Ido Levi", "DavidGer411", "Lugi craft", "Clickshite", "מעין ללי", "cohen", "בן סגל", "sculps", "רועי בן ששון", "עדי פ.", "Amir GAO", "y_shtivi",
                "Naorr", "Banditax", "Snek", "אסף צור", "OV3RPOWERED", "BlueZer Ka", "M.A.O.R", "Xtazza", "Asi Glanz", "Talya", "Dolev Harbi", "lia bar yossef", "Amit Beatbox", "Eyal TV", "Timer Barel",
                "pajaMAX", "ASEFA 313", "OMER", "daniel hyman", "topKid", "Bio Gic", "קלפון", "not Shalev", "סברינה", "BEsquadYT", "Eli Yosels82", "Boruto", "rambo gaming", "itay M", "Eyal",
                "Nadav", "merav Lavy", "Merav Levy", "טליה ריינשטיין", "אסף המלך", "ofir", "pugzur", "Daddy Reagen", "copper storm", "gaming", "Infinite games", "עמיחי בר", "Maor hilu gaming", "אילון לוי",
                "אורון נסים", "maor", "ITamaRs", "יואב דורון", "אוריה אלבז", "MERAV KOGAn", "Or Sadot", "יהונתן המלך", "dr nofing", "NeverPlayer", "גיא חיזקיהו", "THE BITON", "Phoantom", "SloKeR", "kfir mazuz",
                "נדב ר", "T-Pro", "Adir Elad", "ronenplay", "Tetchy", "ליאון וליקנסקי", "שילה פרץ", "Ofer Ratzbi", "Leono", "HaMaGNiV", "Yarin Zino", "NamerHacesef", "Erez Solomon", "yair :D",
                "yoav Yehonatan gaming", "alker 1995", "Pounds", "Pounds", "omrist gaming", "shibeshi!", "ofek oved", "Mr pro", "Hadar ben", "שמואל גיימינג", "גיא עניאל", "Henam", "Niv Hamagniv",
                "הלל דה פרו", "itsOhad", "world", "בועז שרעבי", "ofir gaming", "banana man", "ארציאל ישראל", "אני בסדר", "Apple", "Erez Maliri", "J. Posadas"};
        idL = String.valueOf(Math.round(Math.floor(Math.random() * 100000)));
        FirebaseAuth.getInstance().signOut();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        emailField = findViewById(R.id.emailField2);
        passwordField = findViewById(R.id.passwordField);
        backBtn = findViewById(R.id.backBtn);
        inp_name =  findViewById(R.id.usernameField);
        mLoginBtn = findViewById(R.id.signUpBtn1);
        emailError = findViewById(R.id.emailError);
        mAuth = FirebaseAuth.getInstance();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_upActivity.this, Login.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {

        } else {
            method = "email";
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.METHOD, method);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
            String personEmail = user.getEmail();
            try {
                personPhoto = user.getPhotoUrl().toString();
            } catch (Exception e) {
                personPhoto = "";
            }
            ReadWrite.write(this.getFilesDir() + File.separator + "user", user.getUid());
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference myRef = database.getReference("Users");
            DatabaseReference fireBase = myRef.child(String.valueOf(user.getUid()));
            DatabaseReference fireBase2 = database.getReference("ligot");
            fireBase.child("id").setValue("nonGmail");
            fireBase.child("email").setValue(personEmail);
            fireBase.child("imgUrl").setValue(personPhoto);
            String personName = inp_name.getEditText().getText().toString();
            fireBase.child("name").setValue(personName);
            fireBase.child("pas").setValue(Text.getRandomString(10));
            fireBase.child("phoneNum").setValue(0);
            fireBase.child("7streak").setValue(0);
            fireBase.child("lastLessonD").child("year").setValue(0);
            fireBase.child("lastLessonD").child("date").setValue(0);
            fireBase.child("streak").setValue(0);
            fireBase.child("maxStreak").setValue(0);
            fireBase.child("streak freeze").setValue("false");
            fireBase.child("progress").setValue(Text.getRandomString(5));
            fireBase.child("start_comp").setValue("");
            fireBase.child("shabes").setValue("false");
            fireBase.child("hasDoneLesson").setValue(false);
            fireBase.child("comp_w").setValue("");
            fireBase.child("comp").setValue("");
            fireBase.child("ligaType").setValue(1);
            fireBase.child("comp_time").setValue("1");
            fireBase.child("xp").setValue(0);
            fireBase.child("weekXp").setValue(0);
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color1 = generator.getRandomColor();
            fireBase.child("imgC").setValue(color1);
            fireBase.child("pyXp").setValue(0);
            fireBase.child("htmlXp").setValue(0);
            fireBase.child("friends").setValue("");
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
                                    namesNewLiga.append(user.getUid()).append("-").append(personName).append(",");
                                    fireBase2.child(Math.round(i / 30) + 1 + "-" + idL).setValue(namesNewLiga.toString());
                                } else {
                                    namesNewLiga.append(id).append("-").append(newName).append(",");
                                    fireBase2.child(Math.round(i / 30) + 1 + "-" + idL).setValue(namesNewLiga.toString());
                                }
                                method = "Google";
                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.METHOD, method);
                                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
                                startActivity(new Intent(sign_upActivity.this, mainScreen.class));
                            }
                        }

                    } else {
                        name.append(user.getUid()).append("-").append(personName);
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
                        startActivity(new Intent(sign_upActivity.this, mainScreen.class));
                    }
                }
            });
            Toast.makeText(sign_upActivity.this, "שלום " + personName, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(sign_upActivity.this, mainScreen.class));
            Toast.makeText(sign_upActivity.this, "Success", Toast.LENGTH_LONG).show();
        }
    }

    private void signUp() {
        if(!validateEmail() | !validatePassword()){
            return;
        } else {
            String email = emailField.getEditText().getText().toString();
            String password = passwordField.getEditText().getText().toString();

            Log.d("emailThing", email + password);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("savta", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w("taskthing", task.getException());
                        emailError.setVisibility(View.VISIBLE);
                        updateUI(null);
                    }

                }
            });
        }
    }

    private Boolean validateEmail() {
        String emailInput = emailField.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailField.setErrorEnabled(true);
            emailField.setError("אנא הכניסו מייל.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailField.setErrorEnabled(true);
            emailField.setError("אנא הכניסו מייל תקין.");
            return false;
        } else {
            emailField.setError(null);
            emailField.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String passInput = passwordField.getEditText().getText().toString();

        if (passInput.isEmpty()){
            passwordField.setErrorEnabled(true);
            passwordField.setError("אנא הכניסו סיסמה.");
            return false;

        } else if(!PASSWORD_PATTERN.matcher(passInput).matches()){
            passwordField.setErrorEnabled(true);
            passwordField.setError("הסיסמה חייבת להיות לפחות 6 תווים וללא רווחים.");
            return false;
        } else {
            emailField.setError(null);
            emailField.setErrorEnabled(false);
            return true;
        }
    }
}
