package com.natigbabayev.biometricprompt

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val executor = Executors.newSingleThreadExecutor()
        val activity: FragmentActivity = this // reference to activity
        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    Log.d("BiometricPrompt", errString.toString())
                    //Toast.makeText(this@MainActivity, errString, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("BiometricPrompt", "onAuthenticationError")
                    //Toast.makeText(this@MainActivity, "onAuthenticationError", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d("BiometricPrompt", "onAuthenticationSucceeded")
                //Toast.makeText(this@MainActivity, "onAuthenticationSucceeded", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                //Toast.makeText(this@MainActivity, "onAuthenticationFailed", Toast.LENGTH_SHORT).show()
                Log.d("BiometricPrompt", "onAuthenticationFailed")
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Set the title to display.")
            .setSubtitle("Set the subtitle to display.")
            .setDescription("Set the description to display")
            .setNegativeButtonText("Cancel")
            .build()

        findViewById<Button>(R.id.authenticateButton).setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}
