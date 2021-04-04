package com.ybr_system.myapplication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.login_fragment.*

const val urlImage = "https://cdn.pixabay.com/photo/2015/03/26/09/47/sky-690293_1280.jpg"

class LoginFragment : Fragment(R.layout.login_fragment) {


    private val getActivity: OnItemClickListener?
        get() = activity.let { it as? OnItemClickListener }

    private var formState: FormState = FormState(false, "Hello, User!")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Glide.with(this).load(urlImage).into(iv_Image)

        cb_agreement.setOnClickListener {
            userInfoCheck(et_UserID.text.toString(), et_UserPassword.text.toString())
            formState = FormState(cb_agreement.isChecked, tv_mainText.text.toString())
        }

        bt_EnterMain.setOnClickListener {
            if (userInfoCheck(et_UserID.text.toString(), et_UserPassword.text.toString())) {
                // progressAfterClick()
                formState = FormState(cb_agreement.isChecked, tv_mainText.text.toString())
                if(isTablet(this)) {
                    openMainFragmentTablet()
                } else {
                    openMainFragment()
                }
            }
        }

    }

    private fun userInfoCheck(userEmail: String, userPassword: String): Boolean {

        val getUserId =
            android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            cb_agreement.isChecked = false
            tv_mainText.text = getUserError(getString(R.string.text_userInfo))
        } else if (!getUserId) {
            cb_agreement.isChecked = false
            tv_mainText.text = getUserError(getString(R.string.toast_notValidEmail))
        } else if (!cb_agreement.isChecked) {
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

//    private fun statusOnClick() {
//        bt_EnterMain.isClickable = false
//        et_UserID.isEnabled = false
//        et_UserPassword.isEnabled = false
//        cb_agreement.isClickable = false
//        pb_Progress.visibility = View.VISIBLE
//    }

    private fun getUserError(error: String): String {
        return error
    }
//
//    private fun progressAfterClick() {
//        statusOnClick()
//        Handler().postDelayed({
//            bt_EnterMain.isClickable = true
//            et_UserID.isEnabled = true
//            et_UserPassword.isEnabled = true
//            cb_agreement.isClickable = true
//            pb_Progress.visibility = View.INVISIBLE
//            tv_mainText.text = getString(R.string.Login_Success)
//            formState = FormState(cb_agreement.isChecked, getUserError(getString(R.string.Login_Success)))
//        }, 2000)
//    }

    private fun openMainFragment() {
        getActivity?.onItemSelected(MainFragment())
    }

    private fun openMainFragmentTablet() {
        getActivity?.onItemSelected(MainFragmentTablet())
    }


    fun isTablet(context: LoginFragment): Boolean {
        val xlarge =
            context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK === Configuration.SCREENLAYOUT_SIZE_XLARGE
        val large =
            context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK === Configuration.SCREENLAYOUT_SIZE_LARGE
        return xlarge || large
    }
}