# Hyun Jeong Lim - Project 01 Retrospective

## My work
- Merged PRs:
  - [#23 feat: implement sign up UI](https://github.com/CSU-gstew/Stock-Monkey/pull/23)
  - [#32 feat: implement login UI](https://github.com/CSU-gstew/Stock-Monkey/pull/32)
  - [#40 Implement Sign Up logic, Compose UI, and password validation](https://github.com/CSU-gstew/Stock-Monkey/pull/40)
- My issues:
  - [#8 Create Sign Up Page UI](https://github.com/CSU-gstew/Stock-Monkey/issues/8)
  - [#11 Create Sign Up Validation Logic](https://github.com/CSU-gstew/Stock-Monkey/issues/11)
  - [#12 Add Sign Up Error Handling](https://github.com/CSU-gstew/Stock-Monkey/issues/12)
  - [#14 Connect User Account Creation to the Database](https://github.com/CSU-gstew/Stock-Monkey/issues/14)
- What I built: I built the sign up screen, along with the sign up logic, password validation, and error handling so the user sees a clear message when their input is invalid. I also worked on connecting account creation to the Room database and built a first version of the login screen UI. I first built the sign up screen with Java and XML layouts, and later rebuilt it in Jetpack Compose (#40).

## Biggest challenge
My biggest challenge was that my first version of the sign up screen did not match the project requirements. I built it with Java and XML layouts, which was what I already knew from CST 338, but the project required Jetpack Compose. It happened because I did not confirm the approach with the team before I started, and I did not check the progress of the project often enough, so I did not notice the mismatch in time. I handled it by rebuilding the sign up screen in Compose in #40. This was hard because Compose builds the UI from functions and state instead of XML layouts and view listeners, so I had to learn a new way of thinking about the screen. The code itself worked, but I learned that the real problem was that I worked on my own for too long without checking in with the team or re-reading the requirements.

## Most valuable thing I learned
The most valuable thing I learned is that finishing my own task is not enough if it does not fit the rest of the project. Checking the requirements, the issue board, and my teammates' progress early would have saved time for both me and my teammates. Communication in a team project is a part of the work, not something extra.

## What I carry into Project 02
1. Before I start coding on any issue, I will check the project requirements and write in the issue which approach I plan to use, so my teammates can correct me early. - I will know it worked if every issue I work on in P2 has a comment from me describing my plan before my first commit.
2. I will reply to every review comment on my pull requests before they are merged, either by making the suggested change or by explaining why I am not making it. - I will know it worked if none of my P2 pull requests is merged with an unanswered review comment.
