package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.impl.testutils.SlotTestData.makeSlot
import com.example.fuelfit.routine.impl.testutils.SlotTestData.makeSlotRequest
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
class CreateSlotUseCaseImplTest {

    @MockK
    lateinit var repository: DayDetailRepository

    private lateinit var useCase: CreateSlotUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CreateSlotUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns Slot`() = runTest {
        val request: SlotRequest = makeSlotRequest(dayId = 1, order = 0)
        val slot: Slot = makeSlot(id = 1, dayId = 1, order = 0)

        coEvery { repository.createSlot(request) } returns ResultWrapper.Success(slot)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(slot, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.createSlot(request) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val request: SlotRequest = makeSlotRequest()
        val error = ApiError(400, "Bad Request")

        coEvery { repository.createSlot(request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.createSlot(request) }
    }

    @Test
    fun `when request has minimal fields then use case works`() = runTest {
        val request = makeSlotRequest(comment = "", config = null)
        val slot = makeSlot(id = 2, comment = "", config = null)

        coEvery { repository.createSlot(request) } returns ResultWrapper.Success(slot)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(slot, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.createSlot(request) }
    }
}
