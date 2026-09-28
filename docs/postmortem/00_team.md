# Project 01 Post Mortem - Team 04 / Stockmonkey

## Context
We set out to build a stock tracker app that would let you track the price of stocks that you wanted to track and we achieved that, at the cost of scaling down the project a bit and removing extra features like a search menu and more stock functionality. Overall we did a good job accomplishing our goal of a Stock Tracker App, albeit a simple one.

## By the numbers
- Issues opened: [29](https://github.com/CSU-gstew/Stock-Monkey/issues) | closed: [29](https://github.com/CSU-gstew/Stock-Monkey/issues?q=is%3Aissue%20state%3Aclosed)
- Pull requests opened: [21](https://github.com/CSU-gstew/Stock-Monkey/pulls) | merged: [19](https://github.com/CSU-gstew/Stock-Monkey/pulls?q=is%3Apr+state%3Aclosed+is%3Amerged)
- Planned at kickoff: [14] stories | done: [12]

## What went well
1. Annabelle's work on the UI was a later addition to the project after they had finished with the database, but it made the app feel so much more professional and really gave our app and our team's confidence a good boost.
2. We had a plan and idea from the very first meeting. I heard some teams struggled with finding a concrete idea to stick to and ended up swapping ideas a few times during the first two or so weeks, so having something concrete to work on from the first meeting helped us stay on track.

## What went wrong
1. We encountered a bug in the Home Page while Annabelle was recording our demo video that crashes the whole app if you removed an older stock from your list which likely would've been caught if we had made Unit Tests for all our code, something to work on for future projects.
2. Not being clear with the group led to the login page being designed twice by two separate teammates, which costed the team time that could've been spent on other issues. This was caused by a lack of clear communication between group members and an early mindset that everyone was designing their components individually, which led to us working completely separately at the beginning. Later weeks we were better about communicating and doing code review but this early mistake cost us some time on the project that could've been spent implementing more features.

## Advice to our next teams
1. Plan and assign enough issues that group members always have at least two weeks worth of issues to work on, our team struggled at times figuring out what to work on next for the current week after completing our PRs the previous week, having a concrete path forward for every group member is important to make sure everyone has work to do and everyone is on task.
2. Have open communication between group members and check in with each other to make sure everyone knows what they are working on, so there isn't any miscommunication between group members like two people working on the same issue. If an issue involves two group members (like someone setting up the login page working with the database person to connect to the database), be clear with both group members on which parts they are doing so it doesn't look like they both have to implement that issue by themselves.