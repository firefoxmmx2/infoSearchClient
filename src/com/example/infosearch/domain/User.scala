package com.example.infosearch.domain

import java.util.Date

case class User(id: Long = 0l,
                username: String,
                password: String,
                email: String,
                birth: Date,
                mobile: Option[String]) {

}