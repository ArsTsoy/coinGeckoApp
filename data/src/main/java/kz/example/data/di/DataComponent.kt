package kz.example.data.di

import dagger.Component


@Component(modules = [
    NetworkModule::class
])
interface DataComponent {
}