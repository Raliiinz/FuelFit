package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.impl.testutils.SlotTestData.makeSlot
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
class GetSlotsUseCaseImplTest {

    @MockK
    lateinit var repository: DayDetailRepository

    private lateinit var useCase: GetSlotsUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetSlotsUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns Slot list`() = runTest {
        val dayId = 1
        val slots: List<Slot> = listOf(
            makeSlot(id = 1, dayId = dayId),
            makeSlot(id = 2, dayId = dayId)
        )

        coEvery { repository.getSlots(dayId) } returns ResultWrapper.Success(slots)

        val result = useCase.invoke(dayId)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(slots, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.getSlots(dayId) }
    }

    @Test
    fun `when repository returns empty list then use case returns empty list`() = runTest {
        val dayId = 2
        val emptySlots: List<Slot> = emptyList()

        coEvery { repository.getSlots(dayId) } returns ResultWrapper.Success(emptySlots)

        val result = useCase.invoke(dayId)

        assertTrue(result is ResultWrapper.Success)
        assertTrue((result as ResultWrapper.Success).data.isEmpty())
        coVerify(exactly = 1) { repository.getSlots(dayId) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val dayId = 3
        val error = ApiError(404, "Not Found")

        coEvery { repository.getSlots(dayId) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(dayId)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getSlots(dayId) }
    }

    @Test
    fun `when dayId is invalid then repository returns Error`() = runTest {
        val invalidDayId = -1
        val error = ApiError(400, "Invalid Day ID")

        coEvery { repository.getSlots(invalidDayId) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(invalidDayId)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.getSlots(invalidDayId) }
    }
}
