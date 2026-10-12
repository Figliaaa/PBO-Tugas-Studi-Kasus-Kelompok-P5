package model.interfaces

interface Searchable {
    fun matches(query: String): Boolean
}