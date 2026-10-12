package model.subclasses

import model.LibraryItem
import model.interfaces.Searchable
import model.status.ItemStatus

class Magazine(
    id: String,
    title: String,
    year: Int,
    val issueNumber: Int,
    val publisher: String
) : LibraryItem(id, title, year), Searchable {

    override var status: ItemStatus = ItemStatus.Available

    override fun getDetails(): String = "Majalah edisi #$issueNumber, diterbitkan oleh $publisher"

    override fun matches(query: String): Boolean {
        return title.contains(query, ignoreCase = true) || publisher.contains(query, ignoreCase = true)
    }
}