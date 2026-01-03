package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.impl.testutils.SlotEntryTestData.makeSlotEntry
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetSlotEntriesUseCaseImplTest {

    @MockK
    lateinit var repository: DayDetailRepository

    private lateinit var useCase: GetSlotEntriesUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetSlotEntriesUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns list of SlotEntries`() = runTest {
        val slotId = 1
        val entries = listOf(
            makeSlotEntry(id = 1, slotId = slotId),
            makeSlotEntry(id = 2, slotId = slotId)
        )

        coEvery { repository.getSlotEntries(slotId) } returns ResultWrapper.Success(entries)

        val result = useCase.invoke(slotId)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(entries, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.getSlotEntries(slotId) }
    }

    @Test
    fun `when repository returns empty list then use case returns empty list`() = runTest {
        val slotId = 2
        coEvery { repository.getSlotEntries(slotId) } returns ResultWrapper.Success(emptyList())

        val result = useCase.invoke(slotId)

        assertTrue(result is ResultWrapper.Success)
        assertTrue((result as ResultWrapper.Success).data.isEmpty())
        coVerify(exactly = 1) { repository.getSlotEntries(slotId) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val slotId = 3
        val error = ApiError(404, "Not Found")

        coEvery { repository.getSlotEntries(slotId) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(slotId)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getSlotEntries(slotId) }
    }

    @Test
    fun `when slotId is invalid then repository returns Error`() = runTest {
        val invalidSlotId = -1
        val error = ApiError(400, "Invalid slotId")

        coEvery { repository.getSlotEntries(invalidSlotId) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(invalidSlotId)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.getSlotEntries(invalidSlotId) }
    }
}
