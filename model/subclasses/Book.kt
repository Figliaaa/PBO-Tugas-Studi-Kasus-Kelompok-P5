package model.subclasses

import model.LibraryItem
import model.interfaces.Borrowable
import model.interfaces.Searchable
import model.status.ItemStatus

class Book(
    id: String,
    title: String,
    year: Int,
    val author: String,
    val pages: Int
) : LibraryItem(id, title, year), Borrowable, Searchable {

    override var status: ItemStatus = ItemStatus.Available

    override fun getDetails(): String = "Buku oleh $author, $pages halaman"

    override fun matches(query: String): Boolean {
        return title.contains(query, ignoreCase = true) || author.contains(query, ignoreCase = true)
    }

    override fun borrow(memberId: String, date: String): Boolean {
        return if (status is ItemStatus.Available) {
            status = ItemStatus.Borrowed(memberId, date)
            true
        } else false
    }

    override fun returnItem(): Boolean {
        return if (status is ItemStatus.Borrowed) {
            status = ItemStatus.Available
            true
        } else false
    }

    override fun isAvailable(): Boolean = status is ItemStatus.Available

    override fun getBorrower(): String? = (status as? ItemStatus.Borrowed)?.by
}