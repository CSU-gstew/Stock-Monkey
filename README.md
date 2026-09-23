# Project 01 StockMonkey Retrospective and overview

[Video Walkthrough](https://drive.google.com/file/d/1gZB9ze6-40Ec5Y_zJX6WksJ6TPrSsMWD/view?usp=sharing) 
<!-- Ads have really ruined rick-rolling. -->
[Github Repo](https://github.com/CSU-gstew/Stock-Monkey)


## Overview
This is a stock tracker app that uses the https://marketstack.com/ API in order to get the EOD price of a stock based on its ticker and stores that information on your account. This is ran on Android Studio.

We got styling help for this document from [this guide](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax)

### Introduction

### Alexey Berezhnoy, Alyosha-ctrl

- [pull requests](https://github.com/CSU-gstew/Stock-Monkey/pulls?q=is%3Apr+state%3Aclosed+author%3AAlyosha-ctrl)
- [issues](https://github.com/CSU-gstew/Stock-Monkey/issues?q=is%3Aissue%20state%3Aclosed%20assignee%3AAlyosha-ctrl)

#### What was your role / which stories did you work on
I worked on the Home Page and the API.

+ What was the biggest challenge?
  + Working the api, and the conversion.
+ Why was it a challenge?
  + I found that the tutorial and various materials given by the professor worked mostly for entirely public api's with no key, and stumbled upon having to leave the nest. I was more used to Pythons way of doing things and got frustrated when what I thought were simple no stress solutions were flat out not possible. 
  + How was the challenge addressed?
  + A major factor was getting out of my own head and destressing. By giving up I didn't give out, and what I focused on doing was at the very least running something so that I could see what would break instead of working entirely in design space. Once I did that I could literally read in the logs what the API request looked like, and from there figure out how to put the api key in the right spot, and properly format my request. 
+ Favorite / most interesting part of this project
  + API, it was the most annoying but it was also the most magical, pulling information from the air. Plus the thrill of victory after long defeats was very juicy, and the sheer potential of yoinking structured data from others is incredible. 
+ If you could do it over, what would you change?
  + I would add a date variable to the Stock object so we could know if it was stale and repull it if it was, and if it wasn't keep it the same. 
+ What is the most valuable thing you learned?
  + Freak out less, and run code more. 


### Annabelle Baltes, annabelleb20

- [pull requests](https://github.com/CSU-gstew/Stock-Monkey/pulls?q=is%3Apr+state%3Aclosed+author%3Aannabelleb20)
- [issues](https://github.com/CSU-gstew/Stock-Monkey/issues?q=is%3Aissue%20state%3Aclosed%20assignee%3Aannabelleb20)

#### What was your role / which stories did you work on
I worked on setting up the database, providing database insight when needed, and setting up a more universal UI.

+ What was the biggest challenge?
  + Setting up the Room database
+ Why was it a challenge?
  + The Room database has changed a lot in recent times, so it's hard to find one universal way to handle anything related to Room (setting it up, testing it, etc.)
  + How was the challenge addressed?
  + I tried my best to find the right way to set up the database, but when I started getting frustrated I asked a TA for help on setting up specific things like prepopulating the database.
+ Favorite / most interesting part of this project
  + The UI, I love working on making something look nice so getting the opportunity to do that was really fun. Though I also really enjoyed seeing the database work.
+ If you could do it over, what would you change?
  + I would set up the database sooner so there would be more of a chance to work with it and I would have more time to do things outside of the database.
+ What is the most valuable thing you learned?
  + That unit tests matter A LOT more than I thought, and can be helpful to find out what's wrong with your code before pushing everything anyways. Also how to set up a Room database.


### Gideon Stewman, CSU-Gstew

- [pull requests](https://github.com/CSU-gstew/Stock-Monkey/pulls?q=is%3Apr+state%3Aclosed+author%3ACSU-gstew)
- [issues](https://github.com/CSU-gstew/Stock-Monkey/issues?q=is%3Aissue+state%3Aclosed+assignee%3ACSU-gstew)

  
### What was your role / which stories did you work on

I implemented the LoginPage Activity and connected it to the HomePage and CreateAccount page, as well as completing the required Static Analysis work and adding a logout button to the home page. I also helped fix bugs wherever they popped up and proofread my teammate's code.

+ What was the biggest challenge?
   - The biggest challenge for me was implementing Composeable UI into my Activity class
     
   + Why was it a challenge?
        - It was challenging for me because I have always used .xmls for all UI in Android Studio, so moving to a new system I wasn't familiar with was hard.
   + How was the challenge addressed?
        - I used Azreal's implementation of his HomePage composable UI as a stepping stone and built my own UI off of that, understanding it more as I went.
+ Favorite / most interesting part of this project
        - We had a week or two where everything we had was disconnected which meant we couldn't test the full app, but once we had finished connecting everything, it felt really good to be able to test all of our individual sections as one complete project.
+ If you could do it over, what would you change?
        - I would make sure we had clear stepping stones at the beginning of each week so everyone was clear on what to work on. Early on, we had some confusion about what to work on next, which led to some miscommunications regarding implementation and multiple people working on the same thing.
+ What is the most valuable thing you learned?
        - Likely Composeable UI as well as Static Analysis, both will be helpful for future projects.


### Hyun Jeong Lim

1. Hyun Jeong's pull requests are [here](https://github.com/CSU-gstew/Stock-Monkey/pulls?q=is%3Apr+author%3AJacklen-lim)
2. Hyun Jeong's Github issues are [here](https://github.com/CSU-gstew/Stock-Monkey/issues?q=is%3Aissue+assignee%3AJacklen-lim)

### What was your role / which stories did you work on

I worked on the Sign Up feature. I built the Sign Up screen, wrote the password 
validation (8–20 characters, at least one number and one special character), and 
connected account creation to the Room database so the app checks for duplicate 
usernames before saving a new user. I also added error dialogs that tell the user 
why sign up failed.

- What was the biggest challenge?
  - Connecting the Sign Up screen to the Room database
- Why was it a challenge?
  - I had to make sure the duplicate username check finished before a new user was 
    saved, and that the result showed correctly on the screen. Gideon's login code 
    also depended on sign up working, so the whole login flow couldn't be tested 
    until my part was done.
  - How was the challenge addressed?
    - I tested each step separately (validation, duplicate check, saving the user) 
      and checked the results in the Database Inspector until everything worked 
      together.
- Favorite / most interesting part of this project
  - Creating an account on my Sign Up screen and then logging in with it using my 
    teammate's login code. It made the project feel like one connected app.
- If you could do it over, what would you change?
  - I would write unit tests for my validation logic while building it instead of 
    only testing manually, and set up .gitignore properly from the start since I 
    accidentally pushed some .idea files.
- What is the most valuable thing you learned?
  - How to work in a team Git workflow: one branch per issue, clear PR descriptions, 
    and code review.


#### Conclusion

- How successful was the project?
  - Think in terms of what did you set out to do and what actually got done?
    Very successful we did all we wanted that being a stock tracker app that stored stocks on users and pulled from an API to get the stocks, and even had the extra time to add a little log out button, and everything looks very nice.
- What was the largest victory?
  - Home Page Logic(But I'm biased since I did that)
- Final assessment of the project
  - Excellent.
