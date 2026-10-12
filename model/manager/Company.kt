package model.manager

import model.Employee
import model.interfaces.BonusEligible
import model.interfaces.Taxable

class Company(val name: String) {
    private val employees = mutableListOf<Employee>()

    fun addEmployee(employee: Employee) {
        employees.add(employee)
    }

    fun getTotalSalary(): Double = employees.sumOf { it.calculateSalary() }

    fun getTotalTax(): Double = employees.filterIsInstance<Taxable>().sumOf { it.calculateTax() }

    fun getTotalBonus(): Double = employees.filterIsInstance<BonusEligible>().sumOf { it.calculateBonus() }

    fun displayAllEmployees() {
        println("=== Daftar Pegawai $name ===")
        employees.forEach {
            it.displayInfo()
            println("---")
        }
    }
}