package database

import database.Players.elo
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import kotlin.collections.forEach

class GenericRepository<T>(private val table: IntIdTable, private val toEntity: (ResultRow) -> T, private val toInsertMap: (T) -> Map<Column<*>, Any?>) : Repository<T> {
    override fun getById(id: Int): T? = transaction { table.selectAll().where {table.id eq id}.map { toEntity(it) }.singleOrNull() }

    override fun getAll(): List<T> = transaction { table.selectAll().map { toEntity(it) } }

    override fun saveItem(item: T): Int = transaction {
        val id = table.insertAndGetId {
            val map = toInsertMap(item)
            map.forEach { (column, value) ->
                @Suppress("UNCHECKED_CAST")
                when (value) {
                    null -> it[column as Column<Any?>] = null
                    else -> it[column as Column<Any>] = value
                }
            }
        }
        id.value
    }

    override fun updateItem(id: Int, newElo: Double) = transaction {
        table.update({ table.id eq id }) {
            it[elo] = newElo
        }
    }
}