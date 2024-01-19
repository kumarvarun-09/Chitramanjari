package com.comiccoder.chitramanjari.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.dataModels.User
import com.comiccoder.chitramanjari.database.registerUser
import com.comiccoder.chitramanjari.databinding.ActivityRegisterBinding
import com.comiccoder.chitramanjari.constants.SUCCESS
import com.comiccoder.chitramanjari.constants.USER_PROFILE_FOLDER
import com.comiccoder.chitramanjari.database.uploadDocument
import com.google.android.material.snackbar.Snackbar

class Register : AppCompatActivity() {
    private val binding by lazy {  ActivityRegisterBinding.inflate(layoutInflater)}

    private var userNameStr: String? = null
    private var emailStr: String? = null
    private var passwordStr: String? = null
    private var confirmPasswordStr: String? = null
    private var flag: Boolean = false
    private lateinit var user: User
    private val progressDialog by lazy { ProgressDialog(this@Register) }
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadDocument(uri, USER_PROFILE_FOLDER, progressDialog) {
                if (it != null) {
                    user.image = it
                    binding.circleImageView.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = User()

        binding.setProfilePic.setOnClickListener {
            launcher.launch("image/*")
        }
        val text =
            "<font color=\"black\">Already have an account? </font> <font color=\"blue\">Login</font>"
        binding.tvLoginIntent.text = Html.fromHtml(text)
        binding.tvLoginIntent.setOnClickListener {
            startActivity(Intent(this@Register, Login::class.java))
            finish()
        }

        binding.btnSubmit.setOnClickListener(View.OnClickListener {
            userNameStr = binding.userName.editText?.text.toString().trim()
            emailStr = binding.email.editText?.text.toString().trim()
            passwordStr = binding.password.editText?.text.toString().trim()
            confirmPasswordStr = binding.confirmPassword.editText?.text.toString().trim()

            if (userNameStr.equals("")) {
                showErrorSnackBar("Name cannot be empty")
                flag = true
            } else if (emailStr.equals("")) {
                showErrorSnackBar("Email cannot be empty")
                flag = true
            } else if (passwordStr.equals("")) {
                showErrorSnackBar("Password cannot be empty")
                flag = true
            } else if (confirmPasswordStr?.equals(passwordStr) == false) {
                showErrorSnackBar("Confirm Password should be same as Password")
                flag = true
            }
            if (flag) {
                flag = false
                return@OnClickListener
                // If any data entered by user is incorrect, below code will not be executed
            }
            user.name = userNameStr
            user.email = emailStr
            registerUser(user, passwordStr!!) {
                if (it == SUCCESS) {
                    startActivity(Intent(this@Register, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

    }

    fun showErrorSnackBar(message: String) {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackbar.show()
    }
}