A single-screen registration form that uses most of the standard Android input widgets, shows the collected data in a Snackbar, and saves it to a text file after the user confirms.

What the app does
The screen is a scrollable form:
1. Pavadinimas: free-text name field
2. Padalinys: auto-complete field with 6 university units; shows a Toast when you pick one
3. Sudėtingumas: 4-star RatingBar in whole steps, default 1
4. Laikas / Data: TimePicker (24-hour) and DatePicker, both in spinner style
5. Pasirinkite miestą: Spinner with Akmenė, Biržai, Vilnius, Kaunas, Klaipėda
5. Registruoti: a switch
6. SAUGOTI (Save): builds a summary of the form and shows it in a Snackbar for 6 seconds
Finding the saved file: it is stored in the app's internal storage, so it is not visible in a file manager. In Android Studio open View → Tool Windows → Device Explorer and browse to data/data/com.example.mda_3_lab/files/. The Toast shows the exact path.

Tech stack
- Java 11, Android SDK (min 33, target and compile 36)
- AndroidX AppCompat 1.8.0, Activity 1.13.0, Material 1.14.0, ConstraintLayout 2.2.2
- Gradle 8.13 and Android Gradle Plugin 8.13.2
- Android Studio

How to build and run
Requirements
- Android Studio (a recent release that supports Android Gradle Plugin 8.13) with Android SDK Platform 36 installed
- JDK 17 or newer (the JDK bundled with Android Studio is fine)
- An emulator or phone running Android 13 (API 33) or newer, because minSdk is 33
Steps
- git clone https://github.com/Aumila123/android-ui-component-suite.git
- Open the folder in Android Studio and wait for the Gradle sync to finish.
- Pick a device in the device selector and press Run.
