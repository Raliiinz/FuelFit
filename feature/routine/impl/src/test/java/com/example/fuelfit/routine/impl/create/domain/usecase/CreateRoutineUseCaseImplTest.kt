package com.example.fuelfit.routine.impl.create.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.create.repository.CreateRoutineRepository
import com.example.fuelfit.routine.impl.testutils.RoutineTestData.makeRoutine
import com.example.fuelfit.routine.impl.testutils.RoutineTestData.makeRoutineRequest
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
class CreateRoutineUseCaseImplTest {

    @MockK
    lateinit var repository: CreateRoutineRepository

    private lateinit var useCase: CreateRoutineUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CreateRoutineUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns same Success`() = runTest {
        val request = makeRoutineRequest()
        val routine = makeRoutine()
        coEvery { repository.createRoutine(request) } returns ResultWrapper.Success(routine)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(routine, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.createRoutine(request) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val request = makeRoutineRequest()
        val error = ApiError(400, "Bad Request")
        coEvery { repository.createRoutine(request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.createRoutine(request) }
    }

    @Test
    fun `when request has empty name then repository returns Error`() = runTest {
        val request = makeRoutineRequest(name = "")
        val error = ApiError(422, "Name cannot be empty")
        coEvery { repository.createRoutine(request) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(request)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.createRoutine(request) }
    }
}
