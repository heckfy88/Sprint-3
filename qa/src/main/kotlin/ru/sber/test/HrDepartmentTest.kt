package src.main.kotlin.ru.sber.qa

import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.sber.qa.*
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

internal class HrDepartmentTest {
    private val certificateRequest = mockk<CertificateRequest>()
    private val timeZone = ZoneOffset.UTC


    @Test
    fun weekendDayExceptionTest() {
        HrDepartment.clock = Clock.fixed(Instant.parse("2021-09-11T01::00:00Z"), timeZone)
        assertThrows(WeekendDayException::class.java, { HrDepartment.receiveRequest(certificateRequest) })
    }

    @Test
    fun notAllowReceiveRequestExceptionTest() {
        HrDepartment.clock = Clock.fixed(Instant.parse("2021-09-14T01::00:00Z"), timeZone)
        every { certificateRequest.certificateType } returns CertificateType.NDFL
        assertThrows(NotAllowReceiveRequestException::class.java, { HrDepartment.receiveRequest(certificateRequest) })
    }

    @Test
    fun receiveRequest() {
        HrDepartment.clock = Clock.fixed(Instant.parse("2021-09-14T01:00:00Z"), timeZone)
        every { certificateRequest.certificateType } returns CertificateType.LABOUR_BOOK
        assertDoesNotThrow({ HrDepartment.receiveRequest(certificateRequest) })
    }

    @Test
    fun processNextRequest() {
        HrDepartment.clock = Clock.fixed(Instant.parse("2021-09-11T01:00:00Z"), timeZone)
        val certificate = mockk<Certificate>()
        val hrEmployeeNumber = 1000L

        every { certificateRequest.process(hrEmployeeNumber) } returns certificate
        every { certificateRequest.certificateType } returns CertificateType.LABOUR_BOOK

        HrDepartment.receiveRequest(certificateRequest)
        assertDoesNotThrow({ HrDepartment.processNextRequest(hrEmployeeNumber) })
        unmockkAll()
    }

}