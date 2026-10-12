package model.subclasses

import model.Employee
import model.interfaces.Taxable
import model.status.EmployeeStatus

class ContractEmployee(
    id: String,
    name: String,
    baseSalary: Double,
    val contractDuration: Int
) : Employee(id, name, baseSalary), Taxable {

    override var status: EmployeeStatus = EmployeeStatus.Active

    override fun calculateSalary(): Double = baseSalary
}