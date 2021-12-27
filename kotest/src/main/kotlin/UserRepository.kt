class UserRepository {
    fun findName(id: Int): String? {
        return if (id % 2 == 0) "Kotlin" else null
    }
}