package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.impl.testutils.RoutineDayTestData.makeRoutineDay
import com.example.fuelfit.routine.impl.testutils.RoutineDayTestData.makeRoutineDayRequest
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
class UpdateRoutineDayUseCaseImplTest {

    @MockK
    lateinit var repository: RoutineDayRepository

    private lateinit var useCase: UpdateRoutineDayUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UpdateRoutineDayUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns updated RoutineDay`() = runTest {
        val request = makeRoutineDayRequest(order = 2)
        val updatedDay = makeRoutineDay(id = 1, order = 2)

        coEvery { repository.updateDayFromRequest(request, 1) } returns ResultWrapper.Success(updatedDay)

        val result = useCase.invoke(request, 1)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(updatedDay, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.updateDayFromRequest(request, 1) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val request = makeRoutineDayRequest()
        val error = ApiError(404, "Day not found")
        coEvery { repository.updateDayFromRequest(request, 99) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(request, 99)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.updateDayFromRequest(request, 99) }
    }

    @Test
    fun `when id is invalid then repository returns Error`() = runTest {
        val request = makeRoutineDayRequest()
        val error = ApiError(400, "Invalid ID")
        coEvery { repository.updateDayFromRequest(request, -1) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(request, -1)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.updateDayFromRequest(request, -1) }
    }
}
