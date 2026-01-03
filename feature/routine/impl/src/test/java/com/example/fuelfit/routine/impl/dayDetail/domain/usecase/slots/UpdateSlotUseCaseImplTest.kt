package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.impl.testutils.SlotTestData
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
class UpdateSlotUseCaseImplTest {

    @MockK
    lateinit var repository: DayDetailRepository

    private lateinit var useCase: UpdateSlotUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UpdateSlotUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns updated Slot`() = runTest {
        val id = 1
        val request: SlotRequest = makeSlotRequest(order = 2, comment = "Updated")
        val updatedSlot: Slot = makeSlot(id = id, order = 2, comment = "Updated")

        coEvery { repository.updateSlot(id, request) } returns ResultWrapper.Success(updatedSlot)

        val result = useCase.invoke(id, request)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(updatedSlot, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.updateSlot(id, request) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val id = 2
        val request: SlotRequest = SlotTestData.makeSlotRequest()
        val error = ApiError(404, "Not Found")

        coEvery { repository.updateSlot(id, request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(id, request)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.updateSlot(id, request) }
    }

    @Test
    fun `when id is invalid then repository returns Error`() = runTest {
        val invalidId = -1
        val request: SlotRequest = SlotTestData.makeSlotRequest()
        val error = ApiError(400, "Invalid ID")

        coEvery { repository.updateSlot(invalidId, request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(invalidId, request)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.updateSlot(invalidId, request) }
    }
}
