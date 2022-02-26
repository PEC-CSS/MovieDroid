<div id="top"></div>

<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<div align="center">
  <h3 align="center">MovieDroid</h3>

  <p align="center">
    <p>Native Android Application for organized and categorized list of a user’s favorite movies and TV shows while keeping up to date with the latest releases.</p> 
    
  <a href="https://github.com/PEC-CSS/MovieDroid"><strong>Explore the docs »</strong></a>
    <br />
    <a href="https://github.com/PEC-CSS/MovieDroid">View Demo</a>
    ·
    <a href="https://github.com/PEC-CSS/MovieDroid/issues">Report Bug</a>
    ·
    <a href="https://github.com/PEC-CSS/MovieDroid/issues">Request Feature</a>
  </p>
</div>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
        <a href="#about-the-project">About The Project</a>
        <ul>
          <li><a href="#built-with">Built With</a></li>
        </ul>
      </li>
      <li>
        <a href="#getting-started">Getting Started</a>
        <ul>
          <li><a href="#prerequisites">Prerequisites</a></li>
          <li><a href="#installation">Installation</a></li>
        </ul>
      </li>
      <li><a href="#usage">Usage</a></li>
      <li><a href="#roadmap">Roadmap</a></li>
      <li><a href="#contributing">Contributing</a></li>
      <li><a href="#license">License</a></li>
      <li><a href="#contact">Contact</a></li>
      <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

<p align='middle'>
  <!-- <img src='assets/loginScreen.gif' alt='Login Screen' width='200' />
  <img src='assets/stockScreenAndWatchlist.gif' alt='Stock Screen and Watchlist' width='200' />
  <img src='assets/aboutAndProfileScreen.gif' alt='About and Profile Screen' width='200' />
  <img src='assets/searchScreen.gif' alt='Search Screen' width='200' /> -->
  MovieDroid is a completely free and open source native android application that maintains an organized and categorized list of a user's favourite movies and TV shows. The app aims at helping a user keep track of the shows they have watched while keeping to up-to-date with the latest releases.
</p>


### Built With
<a href="https://www.java.com/en/"><img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"  ></a> &nbsp; 
<a href="https://kotlinlang.org/"><img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" /></a> &nbsp; 
<a href="https://firebase.google.com/"><img src="https://img.shields.io/badge/firebase-ffca28?style=for-the-badge&logo=firebase&logoColor=black" /></a>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

* npm
  ```sh
  npm install npm@latest -g
  ```
* [Android Studio](https://developer.android.com/studio)

### Installation

_Below is an example of how you can install and set up your app._

<!-- 1. Get a free API Key at [https://example.com](https://example.com) -->
1. Clone the repo
   ```sh
   git clone https://github.com/PEC-CSS/MovieDroid.git
   ```
2. Open Android Studio. Go to File->Open... and locate and open your cloned project. Then wait for all the dependencies to get installed.
3. To setup firebase for development on your local system, comment out all the firebase dependencies in the app level build.gradle file and run gradle sync.
4. Create a project on [Firebase](https://console.firebase.google.com/u/0/) and add the package name of the project com.pec_acm.moviedroid
5. Enable Google Sign in authentication, Realtime Database and Firebase Storage in your Firebase project.
6. Add your SHA1 and SHA256 fingerprints to the app in the firebase project settings. You can get these by running the command:
  - For windows
  ```sh
keytool -list -v -keystore C:\Users\your_user_name\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android
```
  - For mac
  ```sh
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```
7. Download the google-services.json file in your firebase project and replace it with the google-services.json present in the cloned project.
8. Uncomment all the firebase dependencies and run gradle sync again.
<!-- 4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ``` -->


<!-- USAGE EXAMPLES -->
## Usage

- Open the cloned project in Android Studio.
- Select your preferred device and click on Run app.
- You can run the app using:
  - Virtual device - If you haven't created a virtual device yet, create one by going to Tools->AVD Manager (Device Manager in Bumblebee) and create virtual device, choose the device specifications according to your needs.
Select your virtual device from the list of devices and run the app
  - Physical device -  In your android device, go to settings and [enable developer options](https://developer.android.com/studio/debug/dev-options#enable).
Connect your device to your system using USB or WiFi (Only for Android 11) 

_For more examples, please refer to the [Documentation](https://example.com)_



<!-- ROADMAP -->
<!-- ## Roadmap
- [x] Add Changelog
- [x] Add back to top links
- [ ] Add Additional Templates w/ Examples
- [ ] Add "components" document to easily copy & paste sections of the readme
- [ ] Multi-language Support
    - [ ] Chinese
    - [ ] Spanish -->
<!-- CONTRIBUTORS -->
## Contributors
This project exists thanks to all the people who contribute. [<a href="#contributing">Contributing</a>].

<a href="https://github.com/PEC-CSS/MovieDroid/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=PEC-CSS/MovieDroid" />
</a>

See the [open issues](https://github.com/PEC-CSS/MovieDroid/issues) for a full list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

Be sure to read the [contribution guidelines](CONTRIBUTING.md) before contributing.


<!-- LICENSE -->
## License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.

<!-- CONTACT -->
## Contact
acmcss@pec.edu.in

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments
* [Android docs](https://developer.android.com/docs)
* [IMDB API](https://imdb-api.com/api)

<p align="right">(<a href="#top">back to top</a>)</p>




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io:/github/contributors/PEC-CSS/MovieDroid?style=for-the-badge
[contributors-url]: https://github.com/PEC-CSS/MovieDroid/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/PEC-CSS/MovieDroid?style=for-the-badge
[forks-url]: https://github.com/PEC-CSS/MovieDroid/network/members
[stars-shield]: https://img.shields.io/github/stars/PEC-CSS/MovieDroid?style=for-the-badge
[stars-url]: https://github.com/PEC-CSS/MovieDroid/stargazers
[issues-shield]: https://img.shields.io/github/issues/PEC-CSS/MovieDroid?style=for-the-badge
[issues-url]: https://github.com/PEC-CSS/MovieDroid/issues
[license-shield]: https://img.shields.io/github/license/PEC-CSS/MovieDroid?style=for-the-badge
[license-url]: https://github.com/PEC-CSS/MovieDroid/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/rahul5430/
[product-screenshot-loginScreen]: assets/loginScreen.gif
[product-screenshot-stockScreenAndWatchlist]: assets/stockScreenAndWatchlist.gif
[product-screenshot-aboutAndProfileScreen]: assets/aboutAndProfileScreen.gif
[product-screenshot-searchScreen]: assets/searchScreen.gif
