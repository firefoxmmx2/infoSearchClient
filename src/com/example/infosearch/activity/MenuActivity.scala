package com.example.infosearch.activity

import android.widget.Button
import com.example.infosearch.R
import android.os.Bundle
import com.example.infosearch.common.Widgets._

class MenuActivity extends RichActivity {
  lazy val downloadBtn = get[Button](R.id.menu_downloadBtn)
  lazy val uploadBtn = get[Button](R.id.menu_uploadBtn)
  lazy val infoSearchBtn = get[Button](R.id.menu_infoSearchBtn)

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_menu)

    downloadBtn.onClick({
      println("download file")
    })

    uploadBtn.onClick({
      println("upload file")
    })

    infoSearchBtn.onClick({
      println("search file")
    })
  }
}