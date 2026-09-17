package database

import player.Player

interface Repository<T> {
    fun getById(id: Int): T?
    fun getAll(): List<T>
    fun saveItem(item: T): Int
    fun updateItem(id: Int, newElo: Double): Int
}
