package com.codingblocks.groupchat.model

/**
 * Created by nimit on 4/8/17.
 */


data class Group(val groupName: String,
                 val messages:String )


data class Message(val message: String,
                   val firebaseUserID: String,
                   val timestamp: String)
