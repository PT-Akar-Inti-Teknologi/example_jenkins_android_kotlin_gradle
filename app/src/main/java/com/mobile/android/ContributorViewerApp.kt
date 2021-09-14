package com.mobile.android

import android.app.Application
import com.mobile.android.dependencies.database.ContributorDao
import com.mobile.android.dependencies.database.GithubDatabase
import com.mobile.android.dependencies.database.RepositoryDao
import com.mobile.android.dependencies.http.FuelAdapter
import com.mobile.android.dependencies.http.HttpClient
import com.mobile.android.feature.contributor.GetContributor
import com.mobile.android.feature.contributor.GetContributorImpl
import com.mobile.android.feature.searchrepository.GetRepos
import com.mobile.android.feature.searchrepository.GetReposImpl
import com.mobile.android.ui.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ContributorViewerApp: Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         *  Dependency Injection use Koin instead of Dagger family
         *
         */
        startKoin {
            modules(mainModule)
            androidContext(this@ContributorViewerApp)
        }
    }

    private val mainModule = module {
        viewModel<HomeViewModel> {
            HomeViewModel(
                getContributor = get<GetContributor>(),
                getRepos = get<GetRepos>()
            )
        }

        factory<HttpClient> {
            FuelAdapter()
        }

        single<GithubDatabase?> {
            GithubDatabase.getInstance(
                context = androidApplication()
            )
        }

        single<ContributorDao> {
            get<GithubDatabase>().contributorDao()
        }

        single<RepositoryDao> {
            get<GithubDatabase>().repositoryDao()
        }

        single<GetContributor> {
            GetContributorImpl(
                httpClient = get<HttpClient>(),
                contributorDao = get<ContributorDao>()
            )
        }

        single<GetRepos> {
            GetReposImpl(
                httpClient = get<HttpClient>(),
                repositoryDao = get<RepositoryDao>()
            )
        }
    }
}