package model.subclasses

import model.LibraryItem
import model.interfaces.Borrowable
import model.interfaces.Searchable
import model.status.ItemStatus

class DVD(
    id: String,
    title: String,
    year: Int,
    val duration: Int,
    val genre: String
) : LibraryItem(id, title, year), Borrowable, Searchable {

    override var status: ItemStatus = ItemStatus.Available

    override fun getDetails(): String = "DVD, $duration menit, genre: $genre"

    override fun matches(query: String): Boolean {
        return title.contains(query, ignoreCase = true) || genre.contains(query, ignoreCase = true)
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