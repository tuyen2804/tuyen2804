import java.util.Scanner

fun main() {
    val studentManager = StudentManager("students.json")
    val scanner = Scanner(System.`in`)

    while (true) {
        println("Chọn một tùy chọn:")
        println("1. Thêm sinh viên")
        println("2. Sửa thông tin sinh viên")
        println("3. Xóa sinh viên")
        println("4. Xem danh sách sinh viên")
        println("5. Thoát")
        print("Lựa chọn của bạn: ")
        when (scanner.nextLine()) {
            "1" -> {
                println("Nhập thông tin sinh viên:")
                print("ID sinh viên: ")
                val studentId = scanner.nextLine()
                print("Tên sinh viên: ")
                val name = scanner.nextLine()
                print("Tuổi sinh viên: ")
                val age = scanner.nextLine().toInt()
                val subjects = mutableListOf<Subject>()
                while (true) {
                    println("Nhập thông tin môn học (hoặc nhập 'kết thúc' để hoàn tất):")
                    print("ID môn học: ")
                    val subjectId = scanner.nextLine()
                    if (subjectId == "kết thúc") break
                    print("Tên môn học: ")
                    val subjectName = scanner.nextLine()
                    print("Điểm: ")
                    val grade = scanner.nextLine()
                    subjects.add(Subject(subjectId, subjectName, grade))
                }
                val newStudent = Student(studentId, name, age, subjects)
                studentManager.addStudent(newStudent)
            }
            "2" -> {
                print("Nhập ID sinh viên cần sửa: ")
                val studentId = scanner.nextLine()
                val student = studentManager.getAllStudents().find { it.studentId == studentId }
                if (student != null) {
                    print("Tên sinh viên (${student.name}): ")
                    val name = scanner.nextLine().ifBlank { student.name }
                    print("Tuổi sinh viên (${student.age}): ")
                    val age = scanner.nextLine().ifBlank { student.age.toString() }.toInt()
                    val subjects = mutableListOf<Subject>()
                    for (subject in student.subjects) {
                        print("ID môn học (${subject.subjectId}): ")
                        val subjectId = scanner.nextLine().ifBlank { subject.subjectId }
                        print("Tên môn học (${subject.subjectName}): ")
                        val subjectName = scanner.nextLine().ifBlank { subject.subjectName }
                        print("Điểm (${subject.grade}): ")
                        val grade = scanner.nextLine().ifBlank { subject.grade }
                        subjects.add(Subject(subjectId, subjectName, grade))
                    }
                    studentManager.updateStudent(Student(studentId, name, age, subjects))
                } else {
                    println("Sinh viên không tồn tại!")
                }
            }
            "3" -> {
                print("Nhập ID sinh viên cần xóa: ")
                val studentId = scanner.nextLine()
                studentManager.removeStudent(studentId)
            }
            "4" -> {
                val students = studentManager.getAllStudents()
                println("Danh sách sinh viên:")
                students.forEach { println(it) }
            }
            "5" -> {
                println("Thoát chương trình.")
                break
            }
            else -> {
                println("Lựa chọn không hợp lệ, vui lòng chọn lại.")
            }
        }
    }
}
