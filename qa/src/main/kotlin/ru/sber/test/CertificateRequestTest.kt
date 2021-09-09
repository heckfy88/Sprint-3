package src.main.kotlin.ru.sber.qa

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.sber.qa.CertificateRequest
import ru.sber.qa.CertificateType
import kotlin.random.Random

internal class CertificateRequestTest {

    private val employeeNumber: Long = 100L
    private val certificateType = mockk<CertificateType>()
    private val hrEmployeeNumber: Long = 50L
    private val certificateRequest = CertificateRequest(employeeNumber, certificateType)


    @Test
    fun employeeNumberTest() {
        assertEquals(certificateRequest.employeeNumber, employeeNumber)
    }

    @Test
    fun certificateTypeTest() {
        assertEquals(certificateRequest.certificateType, certificateType)
    }

    @Test
    fun certificateRequestTest() {
        assertNotNull(certificateRequest.process(hrEmployeeNumber))
        mockkObject(Random)
        
        every { Random.nextLong(5000L, 15000L) } returns 10000L
        every { Random.nextBytes(3) } returns dataArr

        val certificate = certificateRequest.process(hrEmployeeNumber)
        assertEquals(certificate.data, dataArr)
        assertEquals(certificate.processedBy, hrEmployeeNumber)
        assertEquals(certificate.certificateRequest, certificateRequest)
    }


    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
