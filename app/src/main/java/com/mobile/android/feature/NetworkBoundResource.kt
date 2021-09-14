package com.mobile.android.feature

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.mobile.android.handler.Resource
import com.mobile.android.handler.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*

/**
 *  Repository Pattern
 *  Based on NetworkBoundResource example in https://developer.android.com/jetpack/guide
 *
 *  Using Flow instead of LiveData
 *  Pros:
 *      Concise easier to follow
 *
 *  Cons:
 *      Maybe leaking somewhere
 *      Exception not caught because run on non-supervisor Job
 */
interface  NetworkBoundResource<Dto, Entity> {

    fun repoContext() = Dispatchers.IO+SupervisorJob()
    fun asFlow() = flow<Resource<Dto>> {
        emit(Resource.loading(null))
        val dbSource = loadFromDb().first()
        if(shouldFetch(dbSource)){
            fetchFromNetwork().fold(
                failure = {error->
                    onFailed()
                    emitAll(loadFromDb().map {dto->
                        Resource.error(error.message, dto)
                    })
                },
                success = {entity->
                    saveFetchResult(entity)
                    emitAll(loadFromDb().map {dto->
                        Resource.success(dto)
                    })
                }
            )
        }else{
            emitAll(loadFromDb().map {dto->
                Resource.success(dto)
            })
        }
    }

    fun onFailed(){ }

    @WorkerThread
    suspend fun saveFetchResult(data: Entity)

    @MainThread
    fun shouldFetch(current: Dto?): Boolean

    @WorkerThread
    suspend fun loadFromDb(): Flow<Dto>

    @WorkerThread
    suspend fun fetchFromNetwork(): Result<Entity>
}