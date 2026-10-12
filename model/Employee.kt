package model

import model.status.EmployeeStatus

abstract class Employee(
    val id: String,
    val name: String,
    val baseSalary: Double
) {
    abstract var status: EmployeeStatus
    abstract fun calculateSalary(): Double

    open fun displayInfo() {
        println("ID: $id, Nama: $name")
        println("Gaji Pokok: Rp $baseSalary")
        println("Gaji Total: Rp ${calculateSalary()}")
        println("Status: ${status.display()}")
    }
}