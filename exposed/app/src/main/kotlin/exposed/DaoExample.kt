package exposed

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    //データベース接続
    Database.connect(
        "jdbc:mysql://127.0.0.1:3306/exposed_example",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "mysql"
    )

    transaction {
        addLogger(StdOutSqlLogger)

        //insert文の実行
        val member = MemberEntity.new {
            name = "Kotlin"
        }
        println("Inserted id: ${member.id}")

        //select文の実行
        MemberEntity.findById(member.id)?.let {
            println("id: ${it.id}")
            println("name: ${it.name}")
        }
    }
}

//テーブルオブジェクト
//IntIdTableクラスはint型のidを主キーにもつテーブルで使用
object MemberTable : IntIdTable("member") {
    val name = varchar("name", 32)
}

//Entityクラス
//IntEntityクラスはint型のidを主キーにもつテーブルで使用
class MemberEntity(id: EntityID<Int>) : IntEntity(id) {
    //フィールドとしてIntEntitiyClassに型パラメーターとして自身のEntityクラス、コンストラクタの引数にはテーブルクラスを渡す
    companion object : IntEntityClass<MemberEntity>(MemberTable)

    //id以外のカラムをフィールド定義。Tableクラスの対応するカラムのフィールドにデリゲートする
    var name by MemberTable.name
}