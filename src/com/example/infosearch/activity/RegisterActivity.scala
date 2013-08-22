package com.example.infosearch.activity

import android.app.Activity
import com.example.infosearch.common.Widgets._
import android.os.Bundle
import com.example.infosearch.R
import android.widget.EditText
import android.widget.Button
import android.app.DatePickerDialog
import java.util.Calendar
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import java.text.SimpleDateFormat

class RegisterActivity extends Activity with RichActivity {
	override def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register)

		val usernameEdit = get[EditText](R.id.reg_usernameEdit)
		val passwordEdit = get[EditText](R.id.reg_passwordEdit)
		val passwordRepeatEdit = get[EditText](R.id.reg_passwordRepeatEdit)
		val emailEdit = get[EditText](R.id.reg_emailEdit)
		val mobileEdit = get[EditText](R.id.reg_mobileEdit)
		val birthEdit = get[EditText](R.id.reg_birthEdit)

		val birthBtn = get[Button](R.id.reg_birthBtn)
		val registerBtn = get[Button](R.id.reg_registerBtn)

		val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
		birthBtn.onClick({
			val nowCal = Calendar.getInstance()
			val dpd = new DatePickerDialog(RegisterActivity.this, new OnDateSetListener() {
				override def onDateSet(dataPicker: DatePicker, year: Int, month: Int, day: Int): Unit = {
					val selectCal = Calendar.getInstance()
					selectCal.set(year, month, day)
					birthEdit.setText(dateFormat.format(selectCal.getTime()))
				}
			}, nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH), nowCal.get(Calendar.DAY_OF_MONTH))
		})

		registerBtn.onClick({

		})
	}
}