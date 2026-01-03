package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.impl.testutils.SlotEntryTestData.makeSlotEntry
import com.example.fuelfit.routine.impl.testutils.SlotEntryTestData.makeSlotEntryRequest
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
class UpdateSlotEntryUseCaseImplTest {

    @MockK
    lateinit var repository: DayDetailRepository

    private lateinit var useCase: UpdateSlotEntryUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UpdateSlotEntryUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns updated SlotEntry`() = runTest {
        val id = 1
        val request: SlotEntryRequest = makeSlotEntryRequest(comment = "Updated")
        val updatedEntry: SlotEntry = makeSlotEntry(id = id, comment = "Updated")

        coEvery { repository.updateSlotEntry(id, request) } returns ResultWrapper.Success(updatedEntry)

        val result = useCase.invoke(id, request)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(updatedEntry, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.updateSlotEntry(id, request) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val id = 2
        val request: SlotEntryRequest = makeSlotEntryRequest()
        val error = ApiError(404, "Not Found")

        coEvery { repository.updateSlotEntry(id, request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(id, request)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.updateSlotEntry(id, request) }
    }

    @Test
    fun `when id is invalid then repository returns Error`() = runTest {
        val invalidId = -1
        val request: SlotEntryRequest = makeSlotEntryRequest()
        val error = ApiError(400, "Invalid ID")

        coEvery { repository.updateSlotEntry(invalidId, request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(invalidId, request)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.updateSlotEntry(invalidId, request) }
    }

    @Test
    fun `when request has minimal fields then use case works`() = runTest {
        val id = 3
        val request: SlotEntryRequest = makeSlotEntryRequest(
            repetitionUnit = null,
            weightUnit = null,
            comment = "",
            config = null
        )
        val updatedEntry: SlotEntry = makeSlotEntry(
            id = id,
            repetitionUnit = null,
            weightUnit = null,
            comment = "",
            config = null
        )

        coEvery { repository.updateSlotEntry(id, request) } returns ResultWrapper.Success(updatedEntry)

        val result = useCase.invoke(id, request)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(updatedEntry, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.updateSlotEntry(id, request) }
    }
}
