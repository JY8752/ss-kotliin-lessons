package exposed

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    //データベース接続
    Database.connect(
        "jdbc:mysql://127.0.0.1:3306/exposed_example",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "mysql"
    )

    //トランザクション
    transaction {
        //標準ログ出力の有効化
        addLogger(StdOutSqlLogger)

        //insert文の実行
        val id = Member.insert {
            //idは自動採番のため省略
            it[name] = "Kotlin"
        } get Member.id //get カラムのプロパティ で登録結果から絡むの値を取得
        println("Inserted id: $id")

        //select文の実行
        val member = Member.select { Member.id eq id } //insertしたレコードのid指定
            .single() //クエリの単一の結果セットの取得
        //結果はResultRowというクラスでMapのような形式となる
        println("id: ${member[Member.id]}")
        println("name: ${member[Member.name]}")
    }
}

//テーブルオブジェクト
object Member : Table("member") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 32)
}