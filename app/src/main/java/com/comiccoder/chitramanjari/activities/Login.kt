package com.comiccoder.chitramanjari.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.database.loginUser
import com.comiccoder.chitramanjari.databinding.ActivityLoginBinding
import com.comiccoder.chitramanjari.constants.SUCCESS
import com.comiccoder.chitramanjari.constants.USER_PROFILE_FOLDER
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private var emailStr: String? = null
    private var passwordStr: String? = null
    private var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val text =
            "<font color=\"black\">Don't have an account? </font> <font color=\"blue\">Register</font>"
        binding.tvRegisterIntent.text = Html.fromHtml(text)
        binding.tvRegisterIntent.setOnClickListener {
            startActivity(Intent(this@Login, Register::class.java))
            finish()
        }

        binding.btnSubmit.setOnClickListener(View.OnClickListener {
            emailStr = binding.email.editText?.text.toString().trim()
            passwordStr = binding.password.editText?.text.toString().trim()

            if (emailStr.equals("")) {
                showErrorSnackBar("Email cannot be empty", this)
                flag = true
            } else if (passwordStr.equals("")) {
                showErrorSnackBar("Password cannot be empty", this)
                flag = true
            }
            if (flag) {
                flag = false
                return@OnClickListener
                // If any data entered by user is incorrect, below code will not be executed
            }
            loginUser(emailStr!!, passwordStr!!) {
                if (it == SUCCESS) {
                    startActivity(Intent(this@Login, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@Login,
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    fun showErrorSnackBar(message: String, context: Activity) {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.snackbar_error_color
            )
        )
        snackbar.show()
    }
}