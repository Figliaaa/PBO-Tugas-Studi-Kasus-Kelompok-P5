package model.interfaces

import model.Employee

interface Taxable {
    fun calculateTax(): Double = 0.05 * (this as Employee).calculateSalary()
}