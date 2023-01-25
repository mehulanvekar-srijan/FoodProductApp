package com.experiment.foodproductapp.di

import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        DatabaseRepository(get())
    }
}

val viewModelModule = module {
    viewModel{
        SplashScreenViewModel(get())
    }
    viewModel{
        RewardsPageViewModel(get())
    }
    viewModel{
        SignInViewModel(get())
    }
    viewModel{
        SignUpViewModel(get())
    }
    viewModel{
        PaymentScreenViewModel(get())
    }
    viewModel{
        CheckoutPageViewModel(get())
    }
    viewModel{
        ForgotPasswordViewModel(get())
    }
    viewModel{
        UserDetailsViewModel(get())
    }
    viewModel{
        ProductCartViewModel(get())
    }
    viewModel{
        HomeScreenViewModel(get())
    }
    viewModel{
        DetailsPageViewModel(get())
    }
    viewModel{
        OrderDetailsViewModel(get())
    }
    viewModel{
        FavouriteProductsViewModel(get())
    }
    viewModel{
        MainViewModel()
    }
}