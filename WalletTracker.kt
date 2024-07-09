//install these libraries
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

// Data class to represent a financial transaction
data class Transaction(
    val id: Int,
    val type: String, // "expense" or "income"
    val amount: Double,
    val date: LocalDate,
    val category: String,
    val note: String? = null
)

// List to store all transactions
val transactions = mutableListOf<Transaction>()

// Counter for generating unique transaction IDs
var transactionIdCounter = 1

// Formatter to parse and format dates in the "YYYY-MM-DD" format
val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

// Function to add a new transaction
fun addTransaction(type: String, amount: Double, date: String, category: String, note: String? = null) {
    try {
        // Parse the date string into a LocalDate object
        val parsedDate = LocalDate.parse(date, dateFormatter)
        
        // Create a new transaction object
        val transaction = Transaction(transactionIdCounter++, type, amount, parsedDate, category, note)
        
        // Add the transaction to the list
        transactions.add(transaction)
    } catch (e: DateTimeParseException) {
        // Handle invalid date format
        println("Invalid date format. Please use YYYY-MM-DD.")
    }
}

// Function to calculate the total amount for a given type (income or expense)
fun getTotalAmount(type: String): Double {
    return transactions.filter { it.type == type }.sumOf { it.amount }
}

// Function to display all transactions
fun displayTransactions() {
    println("ID | Type    | Amount | Date       | Category     | Note")
    println("------------------------------------------------------------")
    for (transaction in transactions) {
        println(
            "${transaction.id.toString().padEnd(3)}| " +
                    "${transaction.type.padEnd(7)}| " +
                    "${transaction.amount.toString().padEnd(7)}| " +
                    "${transaction.date.format(dateFormatter).padEnd(11)}| " +
                    "${transaction.category.padEnd(12)}| " +
                    "${transaction.note ?: ""}"
        )
    }
}

// Function to display a summary of income, expenses, and net amount
fun displaySummary() {
    val totalIncome = getTotalAmount("income")
    val totalExpenses = getTotalAmount("expense")
    val netAmount = totalIncome - totalExpenses

    println("Total Income: $$totalIncome")
    println("Total Expenses: $$totalExpenses")
    println("Net Amount: $$netAmount")
}

// Main function to interact with the user
fun main() {
    val scanner = Scanner(System.`in`)

    while (true) {
        // Display the menu
        println("\nExpense Tracker Menu")
        println("1. Add Income")
        println("2. Add Expense")
        println("3. View Transactions")
        println("4. View Summary")
        println("5. Exit")
        print("Choose an option: ")

        // Get user input
        when (scanner.nextInt()) {
            1 -> {
                // Add Income
                println("\nAdd Income")
                print("Enter amount: ")
                val amount = scanner.nextDouble()
                scanner.nextLine() // Consume newline
                print("Enter date (YYYY-MM-DD): ")
                val date = scanner.nextLine()
                print("Enter category: ")
                val category = scanner.nextLine()
                print("Enter note (optional): ")
                val note = scanner.nextLine()
                addTransaction("income", amount, date, category, note)
                println("Income added successfully.")
            }
            2 -> {
                // Add Expense
                println("\nAdd Expense")
                print("Enter amount: ")
                val amount = scanner.nextDouble()
                scanner.nextLine() // Consume newline
                print("Enter date (YYYY-MM-DD): ")
                val date = scanner.nextLine()
                print("Enter category: ")
                val category = scanner.nextLine()
                print("Enter note (optional): ")
                val note = scanner.nextLine()
                addTransaction("expense", amount, date, category, note)
                println("Expense added successfully.")
            }
            3 -> {
                // View Transactions
                println("\nTransactions")
                displayTransactions()
            }
            4 -> {
                // View Summary
                println("\nSummary")
                displaySummary()
            }
            5 -> {
                // Exit
                println("Exiting...")
                return
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}
