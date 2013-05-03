Production Viability Framework Version v. 1.0


GENERAL USAGE NOTES
-----------------------------------------
- The app can be run standalone without being attached to a computer on a manual basis.
- To automate the app and run the test suite, the cellphone must be connected to a PC.
-  Start executing the script on the computer after connecting the cellphone, provide inputs according to the prompts and  monitor behavior as the test suite runs and displays results.
- After the test suite has run its course, we can go ahead and view some statistics by clicking on the view logs button in the app.
- Various graphs can be viewed based on preference.


INSTALLING THE APP
----------------------------------


We need a minimum for Android 2.3 to run this app without any glitches. Once this requirement has been met, we can proceed with installation.
1)Load the apk installer file to device memory.
2)Access the apk installer file on the device.
3)Follow the instructions on the screen to install.


Running the framework using Monkeyrunner script (Automated Testing)
-----------------------------------------------------------------------------------------------------------------
1) Enable “USB Debugging Mode” from the Developer Options in the “Settings/Developer Options” tab.
2) Connect your device to the PC using a data-cable.
3) Open the “Republic Tester” app in the device.
4) Open “Command prompt” in windows.
5) Add “C:\android-sdk-windows\platform-tools\adb.exe” to windows system paths.
6) Add “C:\Android\adt-bundle-windows-x86_64\adt-bundle-windows-x86_64\sdk\tools\lib” to windows system path.Here,assumption is made that the user has Android ADK installed in his Windows machine.
7) In Command prompt,give the following:
    adb devices
    This starts the adb daemon.
8) Give the following command:
    monkeyrunner “path to the python script”
9) Follow the prompts on the screen.
10) The test suite will get executed and will display the results on a web-page after completion.




RUNNING MANUAL TESTS
------------------------------------------
The app can be used to do manual testing when connecting to a PC for testing. We can specify the phone number and choose the type of call, or enter an SMS message and try sending it. The app will still push data onto the database and we will be able to retrieve this info in the form of graphs and even textual info when necessary.




READING RESULTS
------------------------------
Graphs are available on a web-server and hence, can be viewed either on the PC or on the mobile device having internet access.It is more convenient to view on a PC.
Graphs are classified into various types primarily based on the success rate for calls and SMSes.


CONTACT US
----------------------
Please feel free to contact us for any comments on the working of the app. You can shoot an  email across to ncsu.republicwireless@gmail.com