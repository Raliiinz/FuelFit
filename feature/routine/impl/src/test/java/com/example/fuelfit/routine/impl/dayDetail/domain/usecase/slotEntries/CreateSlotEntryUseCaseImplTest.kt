package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
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
class CreateSlotEntryUseCaseImplTest {

    @MockK
    lateinit var repository: DayDetailRepository

    private lateinit var useCase: CreateSlotEntryUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CreateSlotEntryUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns SlotEntry`() = runTest {
        val request = makeSlotEntryRequest()
        val entry: SlotEntry = makeSlotEntry()

        coEvery { repository.createSlotEntry(request) } returns ResultWrapper.Success(entry)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(entry, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.createSlotEntry(request) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val request = makeSlotEntryRequest()
        val error = ApiError(400, "Invalid data")

        coEvery { repository.createSlotEntry(request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.createSlotEntry(request) }
    }

    @Test
    fun `when request has minimal fields then use case works`() = runTest {
        val request = makeSlotEntryRequest(
            repetitionUnit = null,
            weightUnit = null,
            comment = "",
            config = null
        )
        val entry = makeSlotEntry(
            repetitionUnit = null,
            weightUnit = null,
            comment = "",
            config = null
        )

        coEvery { repository.createSlotEntry(request) } returns ResultWrapper.Success(entry)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Success)
        coVerify(exactly = 1) { repository.createSlotEntry(request) }
    }
}
