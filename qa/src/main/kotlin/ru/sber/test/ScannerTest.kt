package src.main.kotlin.ru.sber.qa

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.sber.qa.ScanTimeoutException
import ru.sber.qa.Scanner
import kotlin.random.Random

internal class ScannerTest {

    @BeforeEach
    fun setUp() {
        mockkObject(Random)
    }

    @Test
    fun getScannerDataTest() {
        every { Random.nextLong(5000L, 15000L) } returns 5000L
        assertDoesNotThrow({ Scanner.getScanData() })
        assertEquals(Scanner.getScanData().size, 100)
    }

    @Test
    fun scannerTimeoutExceptionTest() {
        every { Random.nextLong(5000L, 15000L) } returns 10001L
        assertThrows(ScanTimeoutException::class.java, { Scanner.getScanData() })
    }


    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
