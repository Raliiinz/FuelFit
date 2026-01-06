package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.common.Routine
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
import java.time.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class GetRoutineUseCaseImplTest {

    @MockK
    lateinit var repository: RoutinesRepository

    private lateinit var useCase: GetRoutineUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetRoutineUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns same Routine`() = runTest {
        val routine: Routine = makeRoutine(
            id = 1,
            created = OffsetDateTime.now()
        )
        coEvery { repository.getRoutine(1) } returns ResultWrapper.Success(routine)

        val result = useCase.invoke(1)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(routine, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.getRoutine(1) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ApiError(404, "Not Found")
        coEvery { repository.getRoutine(99) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(99)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getRoutine(99) }
    }

    @Test
    fun `when id is invalid then repository returns Error`() = runTest {
        val error = ApiError(400, "Invalid ID")
        coEvery { repository.getRoutine(-1) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(-1)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getRoutine(-1) }
    }
}
