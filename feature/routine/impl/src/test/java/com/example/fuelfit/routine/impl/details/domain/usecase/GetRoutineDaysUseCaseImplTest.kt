package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.impl.testutils.RoutineDayTestData.makeRoutineDay
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
class GetRoutineDaysUseCaseImplTest {

    @MockK
    lateinit var repository: RoutineDayRepository

    private lateinit var useCase: GetRoutineDaysUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetRoutineDaysUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success with days then use case returns list`() = runTest {
        val days = listOf(
            makeRoutineDay(id = 1),
            makeRoutineDay(id = 2)
        )
        coEvery { repository.getDaysByRoutine(1) } returns ResultWrapper.Success(days)

        val result = useCase.invoke(1)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(days, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.getDaysByRoutine(1) }
    }

    @Test
    fun `when repository returns empty list then use case returns empty list`() = runTest {
        coEvery { repository.getDaysByRoutine(1) } returns ResultWrapper.Success(emptyList())

        val result = useCase.invoke(1)

        assertTrue(result is ResultWrapper.Success)
        assertTrue((result as ResultWrapper.Success).data.isEmpty())
        coVerify(exactly = 1) { repository.getDaysByRoutine(1) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ApiError(500, "Server Error")
        coEvery { repository.getDaysByRoutine(1) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(1)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getDaysByRoutine(1) }
    }
}
