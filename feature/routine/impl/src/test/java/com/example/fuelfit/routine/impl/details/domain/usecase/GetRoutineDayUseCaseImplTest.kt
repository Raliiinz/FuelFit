package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.details.model.RoutineDay
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
class GetRoutineDayUseCaseImplTest {

    @MockK
    lateinit var repository: RoutineDayRepository

    private lateinit var useCase: GetRoutineDayUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetRoutineDayUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns RoutineDay`() = runTest {
        val day: RoutineDay = makeRoutineDay(id = 1)
        coEvery { repository.getDay(1) } returns ResultWrapper.Success(day)

        val result = useCase.invoke(1)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(day, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.getDay(1) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ApiError(404, "Day not found")
        coEvery { repository.getDay(99) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(99)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getDay(99) }
    }

    @Test
    fun `when dayId is invalid then repository returns Error`() = runTest {
        val error = ApiError(400, "Invalid ID")
        coEvery { repository.getDay(-1) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(-1)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.getDay(-1) }
    }
}
