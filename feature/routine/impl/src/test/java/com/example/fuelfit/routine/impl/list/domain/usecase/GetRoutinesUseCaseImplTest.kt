package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.list.repository.RoutinesRepository
import com.example.fuelfit.routine.impl.testutils.RoutineTestData.makeRoutine
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
class GetRoutinesUseCaseImplTest {

    @MockK
    lateinit var repository: RoutinesRepository

    private lateinit var useCase: GetRoutinesUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetRoutinesUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success with routines then use case returns same list`() = runTest {
        val routines = listOf(
            makeRoutine(id = 1),
            makeRoutine(id = 2)
        )
        coEvery { repository.getRoutines() } returns ResultWrapper.Success(routines)

        val result = useCase.invoke()

        assertTrue(result is ResultWrapper.Success)
        assertEquals(routines, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.getRoutines() }
    }

    @Test
    fun `when repository returns empty list then use case returns empty list`() = runTest {
        coEvery { repository.getRoutines() } returns ResultWrapper.Success(emptyList())

        val result = useCase.invoke()

        assertTrue(result is ResultWrapper.Success)
        assertTrue((result as ResultWrapper.Success).data.isEmpty())
        coVerify(exactly = 1) { repository.getRoutines() }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ApiError(500, "Server Error")
        coEvery { repository.getRoutines() } returns ResultWrapper.Error(error)

        val result = useCase.invoke()

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getRoutines() }
    }
}
