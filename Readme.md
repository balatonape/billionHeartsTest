# Face detection of images from the Gallery with below functionality

Application should be able to scan a user’s photo gallery, detect faces in each
image and put a bounding box around each face in the image.
- The app should ask for permission to scan the user’s photo gallery
- It should then show a loading/placeholder view
- As photos which contain faces of people are found, they should be surfaced with
  a bounding box over each face
- There should be a way for users to “tag” each face, allowing users to name each
  face in an image

## Architecture followed
Used **MVVM** architecture, where viewmodel controls the business logic

## Libraries Used
- Jetpack Compose - for Declarative UI
- Coroutines - for better performance
- RoomDB - for storing the mention names to persist in db
- DaggerHilt -  for dependency injection
- ViewModel - for attaching to the apps lifecycle and persist the app config changes


All the views are present in views folder, view model handles all the logic to load images from the content provider and detect the faces in the image using mediapipe library.

Strings files are used to store all the constants

## To run the app
- Open in android studio
- Build the application
- Run the application with Potrait images with some of them containing the faces

## Below is the video record of the application working 

![This is an alt text.](/BillionHearts.mp4 "App working video")
