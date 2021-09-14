package com.mobile.android.feature.contributor

import com.google.gson.Gson
import com.mobile.android.dependencies.database.ContributorDao
import com.mobile.android.dependencies.http.HttpClient
import com.mobile.android.feature.NetworkBoundResource
import com.mobile.android.feature.contributor.model.*
import com.mobile.android.handler.Resource
import com.mobile.android.handler.genericType
import com.mobile.android.handler.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetContributorImpl(
    private val httpClient: HttpClient,
    private val contributorDao: ContributorDao,
): GetContributor {
    override fun invoke(): Flow<Resource<List<ContributorDto>>> {
        return object : NetworkBoundResource<List<ContributorDto>, List<ContributorEntity>>{
            override suspend fun saveFetchResult(data: List<ContributorEntity>) = withContext(Dispatchers.IO){
                contributorDao.insertContributors(data)
            }

            override fun shouldFetch(current: List<ContributorDto>?): Boolean {
                return current == null || current.isEmpty()
            }

            override suspend fun loadFromDb(): Flow<List<ContributorDto>> = withContext(Dispatchers.IO){
                return@withContext contributorDao.loadContributors().map { entity->
                    entity.toDto()
                }
            }

            /**
             *  Hardcoded URL, no need a flexible one for now
             *
             *  Locally defined context to avoid function executed in MainThread
             */
            override suspend fun fetchFromNetwork(): Result<List<ContributorEntity>> = withContext(Dispatchers.IO){
                return@withContext httpClient.get("https://api.github.com/repos/square/retrofit/contributors")
                    .log("FETCH_CONTRIBUTOR")
                    .dispatch().fold(
                        failure = {error->
                            Result.Failure(error)
                        },
                        success = {json->
                            val type = genericType<List<ContributorResponse>>()
                            val response = Gson()
                                .fromJson<List<ContributorResponse>>(json, type)
                            Result.Success(response.map { resp->
                                resp.toEntity()
                            })
                        }
                    )
            }
        }.asFlow().catch {
            emit(Resource.error(it.message?:"Exception on Get Contributor", emptyList()))
        }
    }
}