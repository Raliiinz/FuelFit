package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.details.model.RoutineDay
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
class CreateRoutineDayUseCaseImplTest {

    @MockK
    lateinit var repository: RoutineDayRepository

    private lateinit var useCase: CreateRoutineDayUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CreateRoutineDayUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns created RoutineDay`() = runTest {
        val request = makeRoutineDayRequest()
        val day: RoutineDay = makeRoutineDay()

        coEvery { repository.createDay(request) } returns ResultWrapper.Success(day)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(day, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.createDay(request) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val request = makeRoutineDayRequest()
        val error = ApiError(400, "Invalid day")
        coEvery { repository.createDay(request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.createDay(request) }
    }
}
