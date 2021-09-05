package ru.sber.io

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


class Archivator {

    /**
     * Метод, который архивирует файл logfile.log в архив logfile.zip.
     * Архив должен располагаться в том же каталоге, что и исходный файл.
     */
    fun zipLogfile(
        fileToZipPath: String = "io/logs/22.01.2001/22-01-2001-2.log",
        zippedFileName: String = "logfile.zip"
    ) {

        val fileToZip: File = File(fileToZipPath)
        val zippedFile: File = File(
            fileToZipPath.substringBeforeLast("/")
                    + "/" + zippedFileName
        )

        ZipOutputStream(FileOutputStream(zippedFile)).use { output ->
            FileInputStream(fileToZip).use { input ->
                BufferedInputStream(input).use { origin ->
                    output.putNextEntry(ZipEntry(fileToZipPath.substring(fileToZipPath.lastIndexOf("/"))))
                    origin.copyTo(output, DEFAULT_BUFFER_SIZE * 2)
                }

            }
        }
    }

    /**
     * Метод, который извлекает файл из архива.
     * Извлечь из архива logfile.zip файл и сохарнить его в том же каталоге с именем unzippedLogfile.log
     */
    fun unzipLogfile(
        zippedFilePath: String = "io/logs/22.01.2001/logfile.zip",
        unzippedLogName: String = "unzippedLogFile.log"
    ) {

        val zippedFile: File = File(zippedFilePath)
        val unzippedLogFile: File = File(
            zippedFilePath.substringBeforeLast("/")
                    + "/" + unzippedLogName
        )

        var buffer: ByteArray
        ZipInputStream(FileInputStream(zippedFile)).use { input ->
            BufferedOutputStream(FileOutputStream(unzippedLogFile)).use { output ->
                input.nextEntry
                do {
                    buffer = input.readNBytes(DEFAULT_BUFFER_SIZE * 2)
                    output.write(buffer)
                } while (buffer.isNotEmpty())
            }

        }

    }

}


fun main() {
    val arch: Archivator = Archivator()

    arch.zipLogfile("io/logs/23.01.2001/23-01-2001-1.log")
    arch.unzipLogfile("io/logs/23.01.2001/logfile.zip")

}
