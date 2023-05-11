package ru.mirea.kashina.firebaseauth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import ru.mirea.kashina.firebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.fieldEmail.getText().toString();
                String password = binding.fieldPassword.getText().toString();
                signIn(email, password);
            }
        });
        binding.emailCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.fieldEmail.getText().toString();
                String password = binding.fieldPassword.getText().toString();
                createAccount(email, password);
            }
        });
        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        binding.verifyEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailVerification();
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

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication  failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        if (!task.isSuccessful()) {
                            binding.status.setText(R.string.auth_failed);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        // Disable button
        binding.verifyEmailButton.setEnabled(false);

        // Send verification email
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = binding.fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            binding.fieldEmail.setError("Required.");
            valid = false;
        } else {
            binding.fieldEmail.setError(null);
        }

        String password = binding.fieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            binding.fieldPassword.setError("Required.");
            valid = false;
        } else {
            binding.fieldPassword.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            binding.status.setText(getString(R.string.emailpassword_status_fmt,user.getEmail(), user.isEmailVerified()));
            binding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            binding.emailPasswordButtons.setVisibility(View.GONE);
            binding.emailPasswordFields.setVisibility(View.GONE);
            binding.signedInButtons.setVisibility(View.VISIBLE);
            binding.verifyEmailButton.setEnabled(!user.isEmailVerified());
        } else {
            binding.status.setText(R.string.signed_out);
            binding.detail.setText(null);
            binding.emailPasswordButtons.setVisibility(View.VISIBLE);
            binding.emailPasswordFields.setVisibility(View.VISIBLE);
            binding.signedInButtons.setVisibility(View.GONE);
        }
    }
}

