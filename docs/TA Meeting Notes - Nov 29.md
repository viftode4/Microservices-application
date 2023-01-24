# TA Meeting 29.11.2022

## Agenda

	- Going over requirements
	- Make an overview of week's plan
	- Organization of coming weeks
 
 
## Requirements

	- Went over all requirements (split mainly between HR and Employee roles)
 
### Feedback

	- System should allow for candidate and HR to send draft contracts back & forth until they reach agreement
	- Candidate should be able to change contract and HR can then accept//reject the changes
	- Create notification/email system 
		- email is preferred, but use dummy emails so there is no spamming during testing
		- notify the other party every time the contract is changed
		
		- Implementation idea: next time the recepient of the notification logs in, a pop-up is shown
	
	- Contract elements:
		- don't store all fields, unless they will actually be implemented and used
		- move it to should have
		- move "additional benefits" to MUST have (can take care of future things we will need to store)
		
	- At least one of the employee requests (and their approvals by HR) must be moved from Could Have to Should Have
		- e.g. vacation days, sick leave, maternity/paternity leave

	- Add Application Security to non-functional requirements
	- Add Microservice Architecture to non-functional requirements
	
	
## Overview for Next Week
	
	- Deadline on friday for Draft of Assignment 1 (set-up meeting for when to do it)
	- Make timeframes for coding/implementing features
		e.g. must-haves until XXX date, should haves until XXX date, etc.
	- Have online meeting on Wednesday 30/11 at 19:30
	- Add Sprints as milestones on GitLab


## Feedback on App Workflow

	- HR POV
		- log-in with credentials
		- see overview of all candidates (+ whether they have requested changes)
			- possibility to approve/deny changes to contracts or other requests
	- Candidate POV
		- log-in with credentials
		- look at status of candidacy
		- make requests
	- Employee of TUDelft POV
		- log-in with credentials
		- look at documents
		- make requests about vacation days, sick leave, etc.
		
	- We assume that the user knows how to make HTTP requests/send commands to server (log-in, ask to see contract, etc.)
	- HTTP requests are made independently
