package com.mobile.android.feature.searchrepository

import com.mobile.android.dependencies.database.RepositoryDao
import com.mobile.android.dependencies.http.HttpClient
import com.mobile.android.feature.NetworkBoundResource
import com.mobile.android.feature.searchrepository.model.*
import com.mobile.android.handler.Deserializer
import com.mobile.android.handler.Resource
import com.mobile.android.handler.orZero
import com.mobile.android.handler.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetReposImpl(
    private val httpClient: HttpClient,
    private val repositoryDao: RepositoryDao
): GetRepos {
    override fun invoke(query: String): Flow<Resource<List<RepoDto>>> {
        return object : NetworkBoundResource<List<RepoDto>, RepoEntity>{
            override suspend fun saveFetchResult(data: RepoEntity) = withContext(Dispatchers.IO){
                val ids = data.items.map { repository ->
                    repository.id.orZero()
                }
                val repoSearch = RepoSearchEntity(
                    query = query,
                    repoIds = ids,
                    totalCount = data.totalCount
                )

                /**
                 *  Async from the worker of Database
                 */
                repositoryDao.insertRepos(data.items)
                repositoryDao.insertSearchResult(repoSearch)
            }

            override fun shouldFetch(current: List<RepoDto>?): Boolean {
                return (current == null || current.isEmpty()) && query.isNotBlank()
            }

            override suspend fun loadFromDb(): Flow<List<RepoDto>> = withContext(Dispatchers.IO){
                return@withContext repositoryDao.loadSearchResult(query = query)?.let { repoSearchEntity ->
                    repositoryDao.loadById(repoSearchEntity.repoIds).map { repo->
                        repo.toDtoList()
                    }
                } ?: flowOf(emptyList())
            }

            override suspend fun fetchFromNetwork(): Result<RepoEntity> = withContext(Dispatchers.IO){
                val deserializer = Deserializer<RepoResponse>(RepoResponse::class.java)
                return@withContext httpClient.get("https://api.github.com/search/repositories?q=$query")
                    .log("FETCH_REPOS")
                    .dispatch().fold(
                        failure = {error->
                            Result.Failure(error)
                        },
                        success = {json->
                            val response = deserializer.deserialize(json)
                            Result.Success(response.toEntity())
                        }
                    )
            }
        }.asFlow().catch {
            emit(Resource.error(it.message?:"Exception on Get Repo", emptyList()))
        }
    }
}