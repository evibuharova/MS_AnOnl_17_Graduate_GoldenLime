package by.evisun.goldenlime

import android.content.Context
import android.content.SharedPreferences
import by.evisun.goldenlime.auth.Authenticator
import by.evisun.goldenlime.auth.DefaultAuthenticator
import by.evisun.goldenlime.auth.LoginFragment
import by.evisun.goldenlime.auth.LoginUserInteractor
import by.evisun.goldenlime.auth.LoginViewModel
import by.evisun.goldenlime.auth.SignInFragment
import by.evisun.goldenlime.auth.SignInViewModel
import by.evisun.goldenlime.auth.UserModelPreferences
import by.evisun.goldenlime.cart.CartFragment
import by.evisun.goldenlime.cart.CartViewModel
import by.evisun.goldenlime.categories.CategoriesFragment
import by.evisun.goldenlime.categories.CategoriesInteractor
import by.evisun.goldenlime.categories.CategoryViewModel
import by.evisun.goldenlime.favourites.FavouritesFragment
import by.evisun.goldenlime.favourites.FavouritesViewModel
import by.evisun.goldenlime.product.details.ProductDetailsFragment
import by.evisun.goldenlime.product.ProductInteractor
import by.evisun.goldenlime.product.list.ProductListViewModel
import by.evisun.goldenlime.product.list.ProductListFragment
import by.evisun.goldenlime.menu.MenuFragment
import by.evisun.goldenlime.menu.MenuViewModel
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

        fragment { CartFragment(get(), { get() }) }
        viewModelOf(::CartViewModel)

        fragment { CategoriesFragment(get(), { get() }) }
        viewModelOf(::CategoryViewModel)
        factoryOf(::CategoriesInteractor)

        fragment { ProductDetailsFragment(get(), { get() }) }
        viewModelOf(::ProductDetailsViewModel)

        fragment { ProductListFragment(get(), { get() }) }
        viewModelOf(::ProductListViewModel)
        factoryOf(::ProductInteractor)

        fragment { MenuFragment(get(), { get() }) }
        viewModelOf(::MenuViewModel)

        fragment { FavouritesFragment(get(), { get() }) }
        viewModelOf(::FavouritesViewModel)

        fragment { SignInFragment(get(), { get() }) }
        viewModelOf(::SignInViewModel)

        fragment { LoginFragment(get(), { get() }) }
        viewModelOf(::LoginViewModel)
        factoryOf(::LoginUserInteractor)
        singleOf(::DefaultAuthenticator).bind<Authenticator>()
        singleOf(::UserModelPreferences)
        single {
            get<Context>().getSharedPreferences("goldenLimeAppPrefs", Context.MODE_PRIVATE)
        }
    }
}