# CS_300_Project

I have copied and pasted the contents of barker_usage_instructions.doc here for easy access.

## Required Files:

server.jar

client.jar

accounts.txt


## Required Folders:

logs

## Instructions

All of these files can be found within the “executables” folder on the github repo. The files “server.jar”, “accounts.txt”, and the folder “logs” all have to be within the same folder for the server to work properly.

Launch the server by running “server.jar”, then enter the port number that you wish to use, and click the “Start server” button.

Once the server is running, launch the “client.jar” and enter the IPv4 address and port that you wish to connect to, then click “Connect to server”.

The rest of the login/signup/messaging should be self explainatory. The only possibly confusing thing is when you click on the button “Get log file” on the messaging screen, it gets the log for the current conversation. If you want the log file for a specific user, click on their username, then click on “Get log file”.

Other notes:
* I have tested it to work correctly when connecting remotely, so that should not be an issue. 
* There is no logout button. Simply close the client window to logout.
* There is no stop server button. Simply close the server window to stop the server.
* Logs are stored as logs/username1-username2.txt, except for the global chatroom, which is logs/_ALL_USERS.txt
