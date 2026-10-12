package model.status

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