package by.evisun.goldenlime

import by.evisun.goldenlime.cart.CartFragment
import by.evisun.goldenlime.categories.CategoriesFragment
import by.evisun.goldenlime.categories.CategoriesInteractor
import by.evisun.goldenlime.categories.CategoryViewModel
import by.evisun.goldenlime.product.details.ProductDetailsFragment
import by.evisun.goldenlime.product.ProductInteractor
import by.evisun.goldenlime.product.list.ProductListViewModel
import by.evisun.goldenlime.product.list.ProductListFragment
import by.evisun.goldenlime.menu.MenuFragment
import by.evisun.goldenlime.product.details.ProductDetailsViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object KoinAppModules {

    fun create() = module {
        singleOf(::DefaultNavigation).bind<Navigation>()

        fragment { CartFragment() }

        fragment { CategoriesFragment(get(), { get() }) }
        viewModelOf(::CategoryViewModel)
        factoryOf(::CategoriesInteractor)

        fragment { ProductDetailsFragment(get(), { get() }) }
        viewModelOf(::ProductDetailsViewModel)

        fragment { ProductListFragment(get(), { get() }) }
        viewModelOf(::ProductListViewModel)
        factoryOf(::ProductInteractor)

        fragment { MenuFragment() }
    }
}