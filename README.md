# slackcraft
Connect slack with a minecraft server. Receive notifications in minecraft chat messages, and send messages to channels from there too. (Private messages too!)

# How to set up
1) Create a bot. Go into your browser of preference and surf to your team's custom integrations page. URL should be https://.slack.com/apps/manage/custom-integrations . In the top search bar search for Bots, and click the one with a gray icon. For your teams name, click configure, and then Add configuration. You will be asked for a username for your bot, don't be dull please, choose a magic inspiring octocat name :octocat: . You will get a large API Token, keep that in a safe place, you'll need it later. You can also customize the icon, name, channels, etcetera, in this page.

2) Invite the bot to all the necessary channels (they can be private channels too) with /invite @mybotsnamehere.

3) Load the plugin, simply clone in desktop, run mvn clean install on top of it, and there you go with your jar file, which should be found at ~/slackcraft-beta/target/slackcraft-1.0-SNAPSHOT.jar. Drop it in your plugins folder and start the server up.

4) When you join the server, you will be approached with a message which you can read if you need help. Now, to connect with the slack bot, do /slc <thekeyyouhavebeenkeepinginasafeplace> (and yes, there is enough space in chat box). When you receive the go-ahead message, you can now use all the commands:

You can disconnect from the slack bot using /sld
You can send a message to a channel with /slack <channel> <message>
You can send a private message with /pm <user> <message>
You will also receive notifications in chat (along with a cool sound) if:

A message contains your username (your mc name to lowercase by default) (not caps-sensitive)
You get pinged.
Your slack username (@Username) isn't exactly your minecraft name lowercased? Oh mon deu! Don't worry too much though, you can just use /slu <newusername, without the @>

# Kudos
This is possible in majority thanks to simple-slack-api, so huge thanks for that!

# License
The MIT License (MIT) Copyright (c) 2016 Javipepe

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

# Help me with a star!
Like the plugin? Please please please please, click the "Star" symbol on the github repository :))
