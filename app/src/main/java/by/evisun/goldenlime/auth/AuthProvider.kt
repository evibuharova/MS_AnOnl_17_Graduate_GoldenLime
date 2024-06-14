package by.evisun.goldenlime.auth

enum class AuthProvider(val id: String) {
    Google("google.com"),
    Apple("apple.com"),
    Facebook("facebook.com"),
    Twitter("twitter.com")
}

val AuthProvider.title: String
    get() = this.name