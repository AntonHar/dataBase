package com.example.database

class User {
    var id: String? = null
    var name: String? = null
    var sec_name: String? = null
    var email: String? = null

    constructor() {}
    constructor(id: String?, name: String?, sec_name: String?, email: String?) {
        this.id = id
        this.name = name
        this.sec_name = sec_name
        this.email = email
    }
}