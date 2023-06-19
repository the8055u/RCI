# RTSP Camera Inspector

![logo](./logo.png)

</br>

RCI is an Android application that helps you view camera video streams using the RTSP protocol. It has a convenient and user-friendly interface that allows you to quickly set up connections to cameras and view video streams.

## Features
1. Convenient and user-friendly interface that allows you to quickly set up connections to cameras.
2. Small size.
3. Fast performance even on old devices.
4. Supports Android 6 and higher.

## Functionality
The application includes the following features:
1. Viewing video cameras via RTSP protocol.
2. Automatic and manual addition of video cameras.

## Installation
To install the application, follow these steps:
1. Download the APK file from the [repository](https://github.com/the8055u/RCI/releases).
2. Allow installation from unknown sources.
3. Open the APK file.

## Usage
1. Open the application.
2. Click the radar button at the top of the screen to scan the network and automatically add available cameras.
3. Or, click the plus icon to add a new camera and enter the stream URL and camera name.
4. Now you can view the camera stream!

</br>

## Project Life Cycle

1. Preparation
- Analysis of project requirements, goals, and tasks
- Studying counterparts, identifying strengths and weaknesses
- Choosing a software implementation method
- Studying documentation
- Setting up and studying development tools

2. Design
- Approval of project requirements
- Approval of the development process

3. UX Analysis
- Analysis of user preferences regarding the interface and functionality

4. UI Design
- Development of interface design

5. Alpha Testing
- Developing basic functionality and debugging

6. Beta Testing (project currently at this stage)
- Implementation of additional functionality
- Testing the application in real conditions
- Adding tests and error logging system
- Finalizing the design

7. Pre-release
- Final verification before release, testing all functions, fixing errors

8. Release
- After successfully passing all stages of development and testing, the "RCI" application is ready for release to the market. It is necessary to ensure the smooth operation of the application and its compliance with user requirements. After releasing the application to the market, work continues on improving it, adding new functions, and fixing possible errors.

</br>

## Working with the source code

If you want to work with the source code of the RTSP Camera Inspector application, you need to download the repository from GitHub.

1. Use GitHub Desktop.
2. Or go to the [repository page](https://github.com/the8055u/RCI).
3. Click the Clone or download button and copy the link to clone the repository.
4. Install GIT, open a terminal on your computer and go to the folder where you want to save the repository.
5. Enter the command

  
 git clone 
  
6. Press Enter to start cloning the repository.

Note that to work with the source code, you may need to install [Android Studio](https://developer.android.com/studio) and all necessary dependencies.

## Project Structure

├───java
│   ╰───it
│       ╰───dsng
│           ╰───rci
│               ├───entities
│               ├───interfaces
│               ├───ui
│               │   ╰───adapters
│               ╰───utils
╰───res
    ├───drawable
    ├───layout
    ├───menu
    ├───mipmap
    ├───navigation   
    ╰───values       

</br>

## RTSP links for testing
- rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov
- rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov

</br>

## Special Thanks To

- [VideoLAN](https://www.videolan.org/) for the [libVLC](https://www.videolan.org/vlc/libvlc.html) library