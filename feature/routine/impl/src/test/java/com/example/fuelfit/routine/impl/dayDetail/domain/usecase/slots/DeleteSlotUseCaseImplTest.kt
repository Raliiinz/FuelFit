package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteSlotUseCaseImplTest {

    @MockK
    lateinit var repository: DayDetailRepository

    private lateinit var useCase: DeleteSlotUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteSlotUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns Unit`() = runTest {
        val id = 1

        coEvery { repository.deleteSlot(id) } returns ResultWrapper.Success(Unit)

        val result = useCase.invoke(id)

        assertTrue(result is ResultWrapper.Success)
        coVerify(exactly = 1) { repository.deleteSlot(id) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val id = 2
        val error = ApiError(404, "Not Found")

        coEvery { repository.deleteSlot(id) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(id)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.deleteSlot(id) }
    }

    @Test
    fun `when id is invalid then repository returns Error`() = runTest {
        val invalidId = -1
        val error = ApiError(400, "Invalid ID")

        coEvery { repository.deleteSlot(invalidId) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(invalidId)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.deleteSlot(invalidId) }
    }
}
