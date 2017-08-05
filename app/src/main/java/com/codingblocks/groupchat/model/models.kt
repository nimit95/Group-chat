package com.codingblocks.groupchat.model

/**
 * Created by nimit on 4/8/17.
 */


data class Group(val groupName: String,
                 val groupID:String,
                 val messages:String )


data class User(val userId:String,
                val Name: String,
                val usersGroup:ArrayList<String>)