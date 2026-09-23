# Project 01 StockMonkey Retrospective and overview

[Video Walkthrough](https://www.youtube.com/watch?v=o-YBDTqX_ZU) 
<!-- Ads have really ruined rick-rolling. -->
[Github Repo](https://github.com/dclinkenbeard/MyDemoApplication)


## Overview
This is a stock tracker app that uses the https://marketstack.com/ API in order to get the EOD price of a stock based on its ticker and stores that information on your account.

We got styling help for this document from [this guide](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax)

## Introduction

* How was communication managed
Initially text messages but once difficulties with different OS's popped up switch to Google Chat.
* How many stories/issues were initially considered
14
* How many stories/issues were completed

27
## Team Retrospective

### Team Member name

- [a link to your pull requests]()
- [a link to your issues]()

#### What was your role / which stories did you work on

+ What was the biggest challenge? 
+ Why was it a challenge?
  + How was the challenge addressed?
+ Favorite / most interesting part of this project
+ If you could do it over, what would you change?
+ What is the most valuable thing you learned?

### Alexey Berezhnoy, Alyosha-ctrl

- [pull requests](https://github.com/CSU-gstew/Stock-Monkey/pulls?q=is%3Apr+state%3Aclosed+author%3AAlyosha-ctrl)
- [issues](https://github.com/CSU-gstew/Stock-Monkey/issues?q=is%3Aissue%20state%3Aclosed%20assignee%3AAlyosha-ctrl)

#### What was your role / which stories did you work on
I worked on the Home Page and the API.

+ What was the biggest challenge?
  Working the api, and the conversion.
+ Why was it a challenge?
  I found that the tutorial and various materials given by the professor worked mostly for entirely public api's with no key, and stumbled upon having to leave the nest. I was more used to Pythons way of doing things and got frustrated when what I thought were simple no stress solutions were flat out not possible. 
  + How was the challenge addressed?
    A major factor was getting out of my own head and destressing. By giving up I didn't give out, and what I focused on doing was at the very least running something so that I could see what would break instead of working entirely in design space. Once I did that I could literally read in the logs what the API request looked like, and from there figure out how to put the api key in the right spot, and properly format my request. 
+ Favorite / most interesting part of this project
  API, it was the most annoying but it was also the most magical, pulling information from the air. Plus the thrill of victory after long defeats was very juicy, and the sheer potential of yoinking structured data from others is incredible. 
+ If you could do it over, what would you change?
  I would add a date variable to the Stock object so we could know if it was stale and repull it if it was, and if it wasn't keep it the same. 
+ What is the most valuable thing you learned?
  Freak out less, and run code more. 

### Carol Danvers
1. Carol's pull requests are [here](https://github.com/Jonathan-Welham/Bits-Bots/pulls/@CarolDanvers)
1. Carol's Github issues are [here](https://github.com/FedericoRubino/cst438_project2/issues/created_by/@FedericoRubino)

#### What was your role / which stories did you work on
Carol mostly worked on getting the app to run faster, better, and higher.  She did the best work possible but her contributions were overpowered and not well received by the fans.

+ What was the biggest challenge? 
  + Managing pull requests and merges
+ Why was it a challenge?
  + We were all new to git/github and not everyone followed convention
  + How was the challenge addressed?
  + I went to the TA for help and used ChatGPT and web resources to get more comfortable with git.
+ Favorite / most interesting part of this project
  + Finally getting the IDs from the API calls to store in the ROOM database
+ If you could do it over, what would you change?
  + I would get the ROOM database setup FIRST
+ What is the most valuable thing you learned?
  + Do the work early and document EVERYTHING


## Conclusion

- How successful was the project?
  - Think in terms of what did you set out to do and what actually got done?
    Very successful we did all we wanted that being a stock tracker app that stored stocks on users and pulled from an API to get the stocks, and even had the extra time to add a little log out button, and everything looks very nice.
- What was the largest victory?
  Home Page Logic(But I'm biased since I did that)
- Final assessment of the project
  Excellent.
