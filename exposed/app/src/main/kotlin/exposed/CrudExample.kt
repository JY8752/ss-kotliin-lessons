package exposed

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

data class MemberModel(val id: Int, val name: String) {
    constructor(entity: MemberEntity) : this(entity.id.value, entity.name)
}

fun main() {
    createSessionFactory()
    transaction {
        //全件検索
        val list = MemberEntity.all().map { MemberModel(it) }
        list.forEach {
            println(it)
        }

        //主キー検索
        val entity = MemberEntity.findById(4)
        entity?.let { println(MemberModel(it)) }

        //主キー以外での検索
        val entity2 = MemberEntity.find { MemberTable.name eq "Saburo" }.map { MemberModel(it) }
        println(entity2)

        //insert
        val entity3 = MemberEntity.new {
            name = "Shiro"
        }
        println(MemberModel(entity3))

        //update
        val entity4 = MemberEntity.findById(4)
        entity4?.let { it.name = "Yonro" }

        //delete
        val entity5 = MemberEntity.findById(5)
        entity5?.delete()
    }
}

fun createSessionFactory() {
    Database.connect(
        "jdbc:mysql://127.0.0.1:3306/exposed_example",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "mysql"
    )
}