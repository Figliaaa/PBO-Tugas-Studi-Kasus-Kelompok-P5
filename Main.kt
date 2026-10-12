import model.manager.Company
import model.manager.LibraryManager
import model.subclasses.*

fun main() {
    println("==================================================")
    println("  DEMO STUDI KASUS INDIVIDU: MANAJEMEN PEGAWAI")
    println("==================================================")
    val company = Company("PT Maju Jaya")

    company.addEmployee(FullTimeEmployee("P001", "Budi", 5_000_000.0, 1_500_000.0, 6_000_000.0))
    company.addEmployee(ContractEmployee("P002", "Siti", 4_500_000.0, 12))
    company.addEmployee(Intern("P003", "Ahmad", 2_000_000.0))

    company.displayAllEmployees()

    println("Total Gaji : Rp ${company.getTotalSalary()}")
    println("Total Pajak: Rp ${company.getTotalTax()}")
    println("Total Bonus: Rp ${company.getTotalBonus()}")

    println("\n==================================================")
    println("  DEMO STUDI KASUS KELOMPOK: PERPUSTAKAAN")
    println("==================================================")

    // 1. Inisialisasi Data Koleksi
    val book1 = Book("P001", "Pemrograman Kotlin", 2023, "Budi Santoso", 350)
    val book2 = Book("P002", "Dasar-Dasar OOP", 2022, "Siti Rahayu", 280)
    val magazine1 = Magazine("M001", "Tech Today", 2024, 42, "Tech Media")
    val dvd1 = DVD("D001", "Film Dokumenter", 2021, 120, "Dokumenter")

    LibraryManager.addItem(book1)
    LibraryManager.addItem(book2)
    LibraryManager.addItem(magazine1)
    LibraryManager.addItem(dvd1)

    println("\n--- Daftar Semua Item ---")
    LibraryManager.displayAllItems()

    println("\n--- Meminjam Buku P001 oleh M001 ---")
    if (LibraryManager.borrowItem("P001", "M001", "2025-01-15")) {
        println("✅ Buku '${book1.title}' dipinjam oleh M001 pada 2025-01-15")
    }

    println("\n--- Mencari 'Kotlin' ---")
    val searchResults = LibraryManager.findByTitle("Kotlin")
    searchResults.forEach { it.display() }

    println("\n--- Mengembalikan Buku P001 ---")
    if (LibraryManager.returnItem("P001")) {
        println("✅ Buku '${book1.title}' dikembalikan")
    }

    println("\n--- Daftar Item Tersedia ---")
    LibraryManager.getAvailableItems().forEach { it.display() }

    println("\n=== SIMULASI EXCEPTION HANDLING ===")
    println("[Uji 1] Meminjam ID yang tidak ada:")
    LibraryManager.borrowItem("P999", "M002", "2025-01-16")

    println("\n[Uji 2] Meminjam Majalah (non-borrowable):")
    LibraryManager.borrowItem("M001", "M002", "2025-01-16")

    println("\n[Uji 3] Meminjam Buku yang sedang dipinjam:")
    LibraryManager.borrowItem("P002", "M002", "2025-01-16")
    LibraryManager.borrowItem("P002", "M003", "2025-01-17")

    println("\n[Uji 4] Mengembalikan item yang belum dipinjam:")
    LibraryManager.returnItem("D001")
}