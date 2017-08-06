package com.codingblocks.groupchat.model

/**
 * Created by nimit on 4/8/17.
 */


data class Group(val groupName: String ="",
                 val groupID:String = "",
                 val messages:ArrayList<Message> = ArrayList<Message>() )


/*data class User(val userId:String,
                val Name: String,
                val usersGroup:ArrayList<String>)*/

data class Message(val message: String ="",
                   val firebaseUserID: String="",
                   val timestamp: String="",
                   val groupID: String="")
