import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class StudentManager(private val filePath: String) {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val studentListType = object : TypeToken<List<Student>>() {}.type
    private var students: MutableList<Student> = mutableListOf()

    init {
        loadStudents()
    }

    private fun loadStudents() {
        val file = File(filePath)
        if (file.exists()) {
            val jsonString = file.readText()
            val loadedStudents: List<Student>? = gson.fromJson(jsonString, studentListType)
            if (loadedStudents != null) {
                students = loadedStudents.toMutableList()
            }
        }
    }

    private fun saveStudents() {
        val jsonString = gson.toJson(students)
        File(filePath).writeText(jsonString)
    }

    fun addStudent(student: Student) {
        students.add(student)
        saveStudents()
    }

    fun removeStudent(studentId: String) {
        students.removeIf { it.studentId == studentId }
        saveStudents()
    }

    fun updateStudent(updatedStudent: Student) {
        val index = students.indexOfFirst { it.studentId == updatedStudent.studentId }
        if (index != -1) {
            students[index] = updatedStudent
            saveStudents()
        }
    }

    fun getAllStudents(): List<Student> {
        return students
    }
}
