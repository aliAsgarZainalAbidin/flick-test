package id.deval.android_test.model

data class Repository(
    var fullName : String,
    var description : String,
    var star : Int,
    var language : String,
    var fork : Int,
    var user : User?
)
