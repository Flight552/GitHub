package com.ybr_system.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_login.*

const val urlImage = "https://cdn.pixabay.com/photo/2015/03/26/09/47/sky-690293_1280.jpg"

class MainActivity : AppCompatActivity() {
    private var formState: FormState = FormState(false, "Hello, User!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Glide.with(this).load(urlImage).into(iv_Image)

        if (savedInstanceState != null) {
            formState = savedInstanceState.getParcelable(INSTANCE_KEY) ?: error("Fields Not Found")
            bt_EnterMain.isEnabled = savedInstanceState.getBoolean(INSTANCE_KEY_ID)
            tv_mainText.text = formState.message
            cb_agreement.isChecked = formState.valid
        }

        bt_Error.setOnClickListener {
            getError()
        }

        cb_agreement.setOnClickListener {
            userInfoCheck(et_UserID.text.toString(), et_UserPassword.text.toString())
            formState = FormState(cb_agreement.isChecked, tv_mainText.text.toString())
        }

        bt_EnterMain.setOnClickListener {
            if (userInfoCheck(et_UserID.text.toString(), et_UserPassword.text.toString())) {
                progressAfterClick()
                formState = FormState(cb_agreement.isChecked, tv_mainText.text.toString())
            }
        }
    }


    private fun getError() {
        Thread.sleep(10000)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(INSTANCE_KEY, formState)
        outState.putBoolean(INSTANCE_KEY_ID, bt_EnterMain.isEnabled)
    }

    private fun userInfoCheck(userEmail: String, userPassword: String): Boolean {

        val getUserId =
            android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            //  Toast.makeText(this, R.string.text_userInfo, Toast.LENGTH_SHORT).show()
            cb_agreement.isChecked = false
            tv_mainText.text = getUserError(getString(R.string.text_userInfo))
        } else if (!getUserId) {
            //  Toast.makeText(this, R.string.toast_notValidEmail, Toast.LENGTH_SHORT).show()
            cb_agreement.isChecked = false
            tv_mainText.text = getUserError(getString(R.string.toast_notValidEmail))
        } else if (!cb_agreement.isChecked) {
            //  Toast.makeText(this, R.string.text_userAgreement, Toast.LENGTH_SHORT).show()
            cb_agreement.isChecked = false
            tv_mainText.text = getUserError(getString(R.string.text_terms_agreement))

        } else {
            cb_agreement.isChecked = true
        }
        if (!cb_agreement.isChecked) {
            cb_agreement.isChecked = false
            bt_EnterMain.isClickable = false
        } else {
            bt_EnterMain.isEnabled = true
            bt_EnterMain.isClickable = true
        }

        return cb_agreement.isChecked
    }

    private fun statusOnClick() {
        bt_EnterMain.isClickable = false
        et_UserID.isEnabled = false
        et_UserPassword.isEnabled = false
        cb_agreement.isClickable = false
        pb_Progress.visibility = View.VISIBLE
    }

    private fun getUserError(error: String): String {
        return error
    }

    private fun progressAfterClick() {
        statusOnClick()
        Handler().postDelayed({
            bt_EnterMain.isClickable = true
            et_UserID.isEnabled = true
            et_UserPassword.isEnabled = true
            cb_agreement.isClickable = true
            pb_Progress.visibility = View.INVISIBLE
            tv_mainText.text = getString(R.string.Login_Success)
            formState =
                FormState(cb_agreement.isChecked, getUserError(getString(R.string.Login_Success)))
            starNewActivity()
        }, 2000)
    }

    private fun starNewActivity() {
        val enter = EnterActivity.enterSecondActivity(this)
        startActivity(enter)
    }

    companion object {
        private const val INSTANCE_KEY = "parcelableKey"
        private const val INSTANCE_KEY_ID = "keyID"
    }

}
