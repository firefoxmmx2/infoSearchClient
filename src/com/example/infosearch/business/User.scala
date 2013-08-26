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

object UserService {
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
      val request = new HttpPost(Constants.api_url_register)
      val params = ListBuffer[NameValuePair]()
      params += new BasicNameValuePair("username", username.getText().toString())
      params += new BasicNameValuePair("password", password.getText().toString())
      params += new BasicNameValuePair("email", email.getText().toString())
      params += new BasicNameValuePair("birth", birth.getText().toString())
      params += new BasicNameValuePair("mobile", mobile.getText().toString())

      request.setEntity(new UrlEncodedFormEntity(params.asJava, "utf8"))
      val httpclient = new DefaultHttpClient
      val response = httpclient.execute(request)
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        val result = EntityUtils.toString(response.getEntity())
        val resultMap = JSON.parseFull(result) match {
          case Some(map: Map[String, Any]) => map
        }
        ret = resultMap.getOrElse("resultCode", 0) == 1
        if (ret) {
          Toast.makeText(context, "[错误]:" + resultMap.get("message"),
            Toast.LENGTH_LONG).show()
        }
      }
    }
    ret
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