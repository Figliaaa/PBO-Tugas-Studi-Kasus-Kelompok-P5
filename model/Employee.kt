// 1. Sealed Class EmployeeStatus
sealed class EmployeeStatus {
    object Active : EmployeeStatus()
    object OnLeave : EmployeeStatus()
    object Terminated : EmployeeStatus()

    fun display(): String = when (this) {
        Active -> "Aktif"
        OnLeave -> "Cuti"
        Terminated -> "Berhenti"
    }
}

// 2. Interface Taxable & BonusEligible
interface Taxable {
    fun calculateTax(): Double = 0.05 * (this as Employee).calculateSalary()
}

interface BonusEligible {
    val bonusRate: Double
    fun calculateBonus(): Double = bonusRate * (this as Employee).calculateSalary()
}

// 3. Abstract Class Employee
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

// 4. Subclass Implementation
class FullTimeEmployee(
    id: String,
    name: String,
    baseSalary: Double,
    val allowance: Double,
    val annualBonus: Double
) : Employee(id, name, baseSalary), Taxable, BonusEligible {
    override var status: EmployeeStatus = EmployeeStatus.Active
    override val bonusRate: Double = 0.0 // Tidak digunakan langsung karena calculateBonus di-override

    override fun calculateSalary(): Double = baseSalary + allowance
    override fun calculateBonus(): Double = annualBonus
}

class ContractEmployee(
    id: String,
    name: String,
    baseSalary: Double,
    val contractDuration: Int
) : Employee(id, name, baseSalary), Taxable {
    override var status: EmployeeStatus = EmployeeStatus.Active

    override fun calculateSalary(): Double = baseSalary
}

class Intern(
    id: String,
    name: String,
    val stipend: Double
) : Employee(id, name, 0.0) {
    override var status: EmployeeStatus = EmployeeStatus.Active

    override fun calculateSalary(): Double = stipend
}

// 5. Kelas Company
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

// 6. Fungsi Utama (main)
fun main() {
    val company = Company("PT Maju Jaya")

    company.addEmployee(FullTimeEmployee("P001", "Budi", 5_000_000.0, 1_500_000.0, 6_000_000.0))
    company.addEmployee(ContractEmployee("P002", "Siti", 4_500_000.0, 12))
    company.addEmployee(Intern("P003", "Ahmad", 2_000_000.0))

    company.displayAllEmployees()

    println("Total Gaji: Rp ${company.getTotalSalary()}")
    println("Total Pajak: Rp ${company.getTotalTax()}")
    println("Total Bonus: Rp ${company.getTotalBonus()}")
}