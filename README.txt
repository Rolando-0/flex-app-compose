READ ME

Project Structure (within com.example.flexapp)

  database package:
  
    
    Within the database package is the entities package, which contains the definitions of the
    entities used by the database including-
  
      - The Routine entity, which contains a one-to-many relationship with the exercise entity,
      and the its own attributes.
  
      - The Exercise entity, which creates the relationship with the Routine entity via a foreign
      key, and its own attributes.
  
      - The Routine Database Access Object (Dao), which provides the list of SQL queries that can be
      used to interact with the Routine and Exercise entities.
  
      - The ProgressItem entity, which has a one-to-many relationship with ProgressDataPoint
      entity, and its own attributes.
  
      - The ProgressDataPoint Entity, which creates the relationship with the ProgressItem entity,
      via a foreign key, and its own attributes.
  
      - The Progress Database Access Object (Dao), which provides the list of SQL queries that can be 
        used to interact with the ProgressItem and ProgressDataPoint entities.
  
      These entities above were created using the Room library for Jetpack Compose.
  
      - The LibraryExercise, which defines a data class similar to the Exercise class that is
      mainly used to read from JSON to create the library of exercises.
  
  
    The database package also contains the necessary files to instantiate the database, its
    entities, and their relationships during for the running application. This includes the
    following-
  
      *RoutineDatabase.kt - defines the implementation of the database, all of the  app's
      entities, the current version of the database (it must be incremented when adding or
      or changing entities) using the database access objects.
  
      RoutineRepository.kt - provides an abstraction layer to the RoutineDao
  
      ProgressRepository.kt - provides an abstraction layer to the ProgressDao
      
      AppContainer.kt - wraps both the RoutineRepository and
      ProgressRepository into members of a single container
  
      AppViewModelProvider.kt - provides the instantation of each ViewModel (more information
      on ViewModels below) via a Factory.
  
      
  navigation package:
  
    MainNavGraph.kt - a critical file to understanding how the navigation between screens works.
    Defines all the routes in the app and their arguments.
  
    BottomNavBar.kt - a component that is given the navController, which gives it privilege of
    being able to navigate to different routes.
  
    TopBarApp.kt - a composable used by many screens as a parameter to the TopNavigationBar of a 
    Scaffold composable.
  
    It is highly recommended to look up Jetpack Compose Type-safe navigation to learn more:
    androidx-navigation-compose, version '2.8.0-beta05'
  
  
  screens package:
  
    Within the screens package is viewmodels package, which defines the ViewModel for each screen.
    Each ViewModel initializes and defines the state for each screen. For example, the state of
    the routine screen is defined by the RoutineDetailsViewModel.kt, which is a mutable list of
    exercises, as the user can add exercises to their Routine.
  
      - ViewModels may also access arguments passed to the route in the current context, via an
      instance a SavedStateHandle.
  
    Within the screens package is a components package, which only contains composable functions
    to load Youtube videos and thumbnails. It uses the 'android-youtube-player' library on github,
    a widely used library to load and play youtube videos in Android. More info here:
      https://github.com/PierfrancescoSoffritti/android-youtube-player
  
    The screens package also contains all the files that define how each screen in the app appears,
    the ViewModels they receive and how they present the state from the ViewModel to present the
    data in a user friendly way.
  
      Notably, the ProgressItemScreen uses a special composable Chart() which uses the Vico Charts
      Version 1.15 library to implement charts for progression tracker feature. More info here:
      https://github.com/patrykandpatrick/vico/tree/v1.15.0


  ui.theme package:

    Contains files that describe the primary and secondary colors, fonts and themes for the app.
  
  
    
  


  
    
