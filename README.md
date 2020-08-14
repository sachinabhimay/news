# news
This app is for demo using The Guardian API.

It contains HomeActivity with tab layout on the top. Which can be used to swipe and switch to different categories of news.
On clicking the news item you can read the full article.

Features:
* 10 different category of news
* uses View model to store UI during configuration changes
* custom chrome tabs for fast and easy inbuild web view
* asysnc tasks to get the data on different thread.


Improvements required:
* using multiple fragments on single activity with model view to save data comming to recycler view has made the system `quite slow.`
* imporvement can be done using activity for different category of news instead of fragments.
* This is an experimental demo hence menu buttons and side navigations for different settings are not included.
* This app is best designed only for 6 inches smart phones.
