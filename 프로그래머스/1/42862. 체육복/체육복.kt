class Solution {
    fun solution(n: Int, lost: IntArray, reserve: IntArray): Int {
        val students = ArrayList<Student>()
        
        // 0번 학생은 미사용
        for(number in 0..n) {
            students.add(
                Student(1)
            )
        }
        
        for(number in lost) {
            students[number].cloth = 0
        }
        
        for(number in reserve) {
            students[number].cloth = if(students[number].cloth == 0) {
                1
            } else {
                2
            }
        }
        
        for(number in students.indices) {
            if(students[number].cloth != 2) {
                continue
            }
            
            val prevNumber = number - 1
            if(prevNumber in students.indices) {
                if(students[prevNumber].cloth == 0) {
                    students[prevNumber].cloth = 1
                    students[number].cloth = 1
                }
            }
            
            if(students[number].cloth != 2) {
                continue
            }
            
            val nextNumber = number + 1
            if(nextNumber in students.indices) {
                if(students[nextNumber].cloth == 0) {
                    students[number].cloth = 1
                    students[nextNumber].cloth = 1
                }
            }
        }
        
        students.removeAt(0)
        return students.count{ it.cloth > 0 }
    }
}

data class Student(
    var cloth: Int,
)