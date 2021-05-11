# Dogs App

## Libraries

* Retrofit (For fetching data from database)
* Shimmer (For loading effect)
* Dagger/Hilt (For Dependency Injection)
* Gson (Handle serialization)
* Coil (For loading images from network)
* Jetpack navigation (Handle navigation)

## Architecture

I used a MVVM architecture that includes Dependency Injection. The network layer has been created following the Repository/Data Source approach so
in that way It's easier to mantain, add more api calls and also a local data source to work with Room Database.

I also used a NetworkResult utility to handle the response status so in this way It's so much eaiser to handle the Loading state or just to show an error or even
if you want to set visibility of a view depnending of the NetworkResult status.

The app also includes support for Dark mode and some style that can be reuse across the app following best practices of DRY.

Finally since I'm really kind to use coroutines more and more, I thoughts this was a good opportunity to include coroutines as well.

## Considerations

* The app has been made in 2.5 hours.
* DarkMode support
* Shimmer effect for best loading UI
* Animations for navigating between screens
* Image loading effect
* Menu showing Author info

