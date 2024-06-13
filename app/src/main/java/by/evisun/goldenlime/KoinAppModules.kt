package by.evisun.goldenlime

import by.evisun.goldenlime.cart.CartFragment
import by.evisun.goldenlime.categories.CategoriesFragment
import by.evisun.goldenlime.details.ProductDetailsFragment
import by.evisun.goldenlime.list.ProductListViewModel
import by.evisun.goldenlime.list.ProductListFragment
import by.evisun.goldenlime.menu.MenuFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object KoinAppModules {

    fun create() = module {
        singleOf(::DefaultNavigation).bind<Navigation>()

        fragment { CartFragment() }

        fragment { CategoriesFragment(get()) }

        fragment { ProductDetailsFragment(get()) }

        fragment { ProductListFragment(get(), { get() }) }
        viewModelOf(::ProductListViewModel)

        fragment { MenuFragment() }
    }
}