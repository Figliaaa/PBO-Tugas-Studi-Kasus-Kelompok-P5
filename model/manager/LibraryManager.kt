package model.manager

import exception.ItemAlreadyBorrowedException
import exception.ItemNotFoundException
import exception.ItemNotBorrowedException
import model.LibraryItem
import model.interfaces.Borrowable
import model.interfaces.Searchable
import model.status.ItemStatus

object LibraryManager {
    private val itemRepo = Repository<LibraryItem>()

    fun addItem(item: LibraryItem) {
        itemRepo.add(item)
    }

    fun findByTitle(query: String): List<LibraryItem> {
        return itemRepo.findAll { item ->
            if (item is Searchable) {
                item.matches(query)
            } else {
                item.title.contains(query, ignoreCase = true)
            }
        }
    }

    fun borrowItem(itemId: String, memberId: String, date: String): Boolean {
        val result = runCatching {
            val item = itemRepo.find { it.id == itemId }
                ?: throw ItemNotFoundException("Item dengan ID '$itemId' tidak ditemukan.")

            if (item !is Borrowable) {
                throw IllegalArgumentException("Item '${item.title}' bukan tipe item yang bisa dipinjam.")
            }

            if (!item.isAvailable()) {
                throw ItemAlreadyBorrowedException("Item '${item.title}' sedang dipinjam oleh ${item.getBorrower()}.")
            }

            item.borrow(memberId, date)
        }

        return result.getOrElse { exception ->
            println("❌ ${exception.message}")
            false
        }
    }

    fun returnItem(itemId: String): Boolean {
        val result = runCatching {
            val item = itemRepo.find { it.id == itemId }
                ?: throw ItemNotFoundException("Item dengan ID '$itemId' tidak ditemukan.")

            if (item !is Borrowable) {
                throw IllegalArgumentException("Item '${item.title}' bukan item yang bisa dipinjam.")
            }

            if (item.isAvailable()) {
                throw ItemNotBorrowedException("Item '${item.title}' belum/tidak sedang dipinjam.")
            }

            item.returnItem()
        }

        return result.getOrElse { exception ->
            println("❌ ${exception.message}")
            false
        }
    }

    fun getAvailableItems(): List<LibraryItem> {
        return itemRepo.findAll { it.status is ItemStatus.Available }
    }

    fun getBorrowedItems(): List<LibraryItem> {
        return itemRepo.findAll { it.status is ItemStatus.Borrowed }
    }

    fun displayAllItems() {
        itemRepo.getAll().forEach { it.display() }
    }
}