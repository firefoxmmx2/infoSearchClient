package com.example.infosearch.business

import com.example.infosearch.domain.User
import android.widget.EditText
import android.widget.Toast
import com.example.infosearch.activity.RegisterActivity
import android.content.Context
import java.text.SimpleDateFormat
import java.text.ParseException

object UserService {
  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def register(context: Context, username: EditText,
               password: EditText,
               passwordRepeat: EditText,
               email: EditText,
               birth: EditText,
               mobile: EditText): Unit = {
    if (regValidate(context, username, password, passwordRepeat, email, birth, mobile)) {

      add(User(username = username.getText().toString(),
        password = password.getText().toString(),
        email = email.getText().toString(),
        birth = sdf.parse(birth.getText().toString()),
        mobile = Option(mobile.getText().toString())))
    }
  }
  def add(user: User) = {

  }

  def update(user: User) = {

  }

  def delete(user: User) = {

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