package com.example.infosearch.activity

import com.example.infosearch.common.Widgets._
import android.os.Bundle
import android.widget._
import android.content.Intent
import com.example.infosearch.R

class LoginActivity extends RichActivity {
	override def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val loginBtn = get[Button](R.id.login_loginBtn)
		val registerBtn = get[Button](R.id.login_registerBtn)
		val username = get[EditText](R.id.login_username)
		val password = get[EditText](R.id.login_password)

		loginBtn.onClick({
			val intent = new Intent(LoginActivity.this, classOf[MenuActivity])
			startActivity(intent)
			finish()
		})

		registerBtn.onClick({
			val intent = new Intent(LoginActivity.this, classOf[RegisterActivity])
			startActivity(intent)

		})

	}

}