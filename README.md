# GithubUserFinder

Thank you for reviewing my code. :)

<p align="center">
    <img src="https://raw.githubusercontent.com/SirLordPouya/GithubUserFinder/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" width="250">
</p>

I'd like to share some of the design decisions I made:

* I implemented simple pagination for searching users manually instead of using the Paging library.
  The reason for this choice was to have full control over the data in my ViewModel and UseCase, and
  I wasn't satisfied with how the Paging library handled failures or refreshes.

* I apologize for the UI; I didn't have much time to work on it. In a real-life app, I typically
  invest more effort into UI design. 😄

* While planning the project, I considered using multiple modules for core classes and features.
  However, I found this approach to be somewhat redundant even for a sample application. Therefore,
  I opted for a single-module architecture.

* Due to time constraints, I couldn't implement the following improvements:
    * Hiding the keyboard when an error message is displayed.
    * Enhancements to start fetching the next page earlier, before the user reaches the end of the
      list.

I thoroughly enjoyed working on this challenge, and I hope you'll enjoy reviewing it as well.
