package by.evisun.goldenlime

import by.evisun.goldenlime.details.ProductDetailsFragment
import by.evisun.goldenlime.list.ProductListViewModel
import by.evisun.goldenlime.list.ProductListFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object KoinAppModules {

    fun create() = module {
        singleOf(::DefaultNavigation).bind<Navigation>()

        fragment { ProductListFragment(get(), { get() }) }

        viewModelOf(::ProductListViewModel)
        fragment { ProductDetailsFragment(get()) }
    }
}