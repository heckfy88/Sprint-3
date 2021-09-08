package ru.sber.nio

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.readLines


class Grep {

    /**
     * Метод должен выполнить поиск подстроки subString во всех файлах каталога logs.
     * Каталог logs размещен в данном проекте (io/logs) и внутри содержит другие каталоги.
     * Результатом работы метода должен быть файл в каталоге io(на том же уровне, что и каталог logs), с названием result.txt.
     * Формат содержимого файла result.txt следующий:
     * имя файла, в котором найдена подстрока: номер строки в файле: содержимое найденной строки
     * Результирующий файл должен содержать данные о найденной подстроке во всех файлах.
     * Пример для подстроки "22/Jan/2001:14:27:46":
     * 22-01-2001-1.log : 3 : 192.168.1.1 - - [22/Jan/2001:14:27:46 +0000] "POST /files HTTP/1.1" 200 - "-"
     */
    fun find(subString: String) {

        val path = Paths.get("io/logs/")
        var fileName: String
        var foundString: String
        var foundStringNumber: Int
        val outputFile: File = File("io/result.txt")

        outputFile.bufferedWriter().use { out ->
            Files.walk(path).filter { it.toString().endsWith(".log") }.forEach { filePath ->

                fileName = filePath.toString().substringAfterLast("/")

                val lineList: List<String> = filePath.readLines()

                for (i in 0 until lineList.count()) {
                    if (lineList[i].contains(subString)) {
                        foundStringNumber = i + 1
                        foundString = lineList[i]
                        out.write("$fileName : $foundStringNumber : $foundString")
                        out.newLine()
                    }
                }
            }
        }
    }
}
