package src.main.kotlin.ru.sber.qa

import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.sber.qa.Certificate
import ru.sber.qa.CertificateRequest
import kotlin.random.Random

internal class CertificateTest {

    private val certificateRequest = mockk<CertificateRequest>()
    private val processedBy: Long = 1000L
    private val data: ByteArray = Random.nextBytes(10)
    private val certificate = Certificate(certificateRequest, processedBy, data)


    @Test
    @DisplayName("certificateRequest is of a proper type")
    fun isCertificateRequestTest() {

        // result
        assertEquals(certificateRequest, certificate.certificateRequest)

    }

    @Test
    @DisplayName("processedBy is of a proper type")
    fun isProcessedByTest() {

        // result
        assertEquals(processedBy, certificate.processedBy)

    }

    @Test
    @DisplayName("data is of a proper type")
    fun isDataTest() {

        // result
        assertEquals(data, certificate.data)

    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

}