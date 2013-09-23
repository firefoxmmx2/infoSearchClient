package com.example.infosearch.business

import com.example.infosearch.domain.User
import android.widget.EditText
import android.widget.Toast
import com.example.infosearch.activity.RegisterActivity
import android.content.Context
import java.text.SimpleDateFormat
import java.text.ParseException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import com.example.infosearch.common.Constants
import org.apache.http.NameValuePair
import scala.collection.JavaConverters._
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.HttpStatus
import org.apache.http.util.EntityUtils
import scala.util.parsing.json.JSON
import scala.collection.mutable.ListBuffer
import org.apache.http.params.BasicHttpParams
import android.content.SharedPreferences
import com.example.infosearch.common.Utils
import org.apache.http.HttpResponse

object UserService {
	val loginInfo = scala.collection.mutable.Map[String, Any]()
	val sdf = new SimpleDateFormat("yyyy-MM-dd")

	def register(context: Context, username: EditText,
		password: EditText,
		passwordRepeat: EditText,
		email: EditText,
		birth: EditText,
		mobile: EditText): Unit = {
		var ret = false;
		if (regValidate(context, username, password, passwordRepeat, email, birth, mobile)) {
			import scala.collection.mutable.ListBuffer
			val params = Map("username" -> username.getText().toString(),
				"password" -> password.getText().toString(),
				"email" -> email.getText().toString(),
				"birth" -> birth.getText().toString(),
				"mobile" -> mobile.getText().toString())

			Utils.doHttpRequest(url = Constants.api_url_register, data = params, onSuccess = (response: HttpResponse, httpclient: HttpClient) => {
				val result = EntityUtils.toString(response.getEntity())
				val resultMap = JSON.parseFull(result) match {
					case Some(map: Map[String, Any]) => map
				}
				ret = resultMap.getOrElse("resultCode", 0) == 1
				if (!ret) {
					Toast.makeText(context, "[错误]:" + resultMap.get("message"),
						Toast.LENGTH_LONG).show()
				}
			})
		}
		ret
	}

	def login(context: Context, usernameEdit: EditText, passwordEdit: EditText) = {
		var ret = false
		val params = Map("username" -> usernameEdit.getText.toString,
			"password" -> passwordEdit.getText.toString)

		Utils.doHttpRequest(url = Constants.api_url_login,
			data = params,
			onSuccess = (response: HttpResponse, httpclient: HttpClient) => {
				val result = EntityUtils.toString(response.getEntity())
				val resultMap = JSON.parseFull(result) match {
					case Some(map: Map[String, Any]) => map
				}

				ret = resultMap.getOrElse("resultCode", 0) == 1
				if (ret) {
					val sharedPreference = context.getSharedPreferences("userdata", Context.MODE_PRIVATE)
					val userdata = resultMap.get("user") match {
						case Some(map: Map[String, Any]) => map
					}

					val edit = sharedPreference.edit()
					edit.putString("username", userdata.get("username").toString())
					edit.putString("password", userdata.get("password").toString())
					edit.commit();

					loginInfo += "username" -> userdata.get("username").toString()
					loginInfo += "password" -> userdata.get("password").toString()
					loginInfo += "cookie" -> httpclient.asInstanceOf[DefaultHttpClient].getCookieStore()

				} else {
					Toast.makeText(context,
						"[错误]:" + resultMap.get("message"),
						Toast.LENGTH_LONG).show()
				}
			})
		ret
	}

	def isLogin(context: Context, userid: Int) = {
		context.getApplicationContext()
	}

	def regValidate(context: Context, username: EditText,
		password: EditText,
		passwordRepeat: EditText,
		email: EditText,
		birth: EditText,
		mobile: EditText): Boolean = {
		if (username.getText().toString().length() < 5) {
			Toast.makeText(context, "用户名必须大于等于5个字符", Toast.LENGTH_LONG).show()
			return false
		}
		if (password.getText().toString().length() < 5) {
			Toast.makeText(context, "密码必须大于等于5个字符", Toast.LENGTH_LONG).show()
			return false
		}
		if (passwordRepeat.getText().toString().length() < 5) {
			Toast.makeText(context, "重复密码必须大于等于5个字符", Toast.LENGTH_LONG).show()
			return false
		}
		if (password.getText().toString() != passwordRepeat.getText().toString()) {
			Toast.makeText(context, "两次输入的密码必须相同", Toast.LENGTH_LONG).show()
			return false
		}
		if (!"\\d*".r.findAllIn(mobile.getText().toString()).hasNext) {
			Toast.makeText(context, "手机号码需要是数字", Toast.LENGTH_LONG).show()
			return false
		}
		if (email.getText().toString().length() == 0) {
			Toast.makeText(context, "邮箱必须输入", Toast.LENGTH_LONG).show()
			return false
		}
		try {

			sdf.parse(birth.getText().toString())
		} catch {
			case e: ParseException => {
				Toast.makeText(context, "不符合日期格式", Toast.LENGTH_LONG).show()
				return false
			}
		}
		true
	}
}