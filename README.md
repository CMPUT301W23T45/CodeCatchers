# CodeCatchers

This project was developed for CMPUT 301 for the semester of Winter 2023 at the University of Alberta.

Project Problem Description

You are to design and implement a simple, attractive, and easy-to-use Android application to satisfy the following goals. Your design must be flexible enough to allow developers to extend or migrate it.

We want a mobile application that allows us to hunt for the coolest QR codes that score the most points. Players will run around scanning QR codes, barcodes, etc. trying to find barcodes and QR codes that give them the most points.

QR codes and barcodes (scannable codes) will be hashed and the hashes they produce will be analyzed and scored. A QR code that has certain properties like repeated nibbles or bytes (hex digits) will have a higher score than a QR code that does not. We have a proposed scoring system, but the implementers are free to use a different scoring system.

We want users to compete with each other for the highest scoring QR codes, the most QR codes, the highest sum of QR codes, or highest scoring QR codes in a region. 

When a player scans a QR code they will take a photo of what or where the QR code is and also record the geolocation of the QR code. 

Players can see on a map local QR codes that other players have scanned.

Scenario:
I open my QRHunter app. I see a QR code in my wallet. I indicate I want to add a new QR code and I use the phone camera to add the QR code. The QR code is scored and I’m told that my QR score is 30. The system prompts me for a photo of the object I scanned. I decline since this was an ID card. I also decline geolocation because it is in my wallet. The system adds the 30 points to my total score and records a hash of the QR code. I then see some sticker on a pole. I scan it and am told it is worth 1000 points! I record the geolocation and take a photo of the pole and save it to my account. 1000 points wow. Then I see that other users have found this pole as well. So I open the map for nearby QR codes and I see something worth 10000 is 100 meters away so I’m going to head on over there!


Needs in (Partial) User Story Form
Actors:

Player: a person who plays the game

Owner: the entity that owns the infrastructure that the game runs on.

Glossary:

QR Code: a scannable code, either a barcode, a QR code, or other code scannable by Zebra crossing libraries or google QR code scanning libraries.

User needs are expressed in the form of partial user stories:

As a <role>, I want <goal>.

These descriptions may change to correct omissions and clarify noticed issues. New requirements will be introduced for the final project part.

Player

US 01.01.01
As a player, I want to add new QR codes to my account.

US 01.02.01
As a player, I want to see what QR codes I have added to my account.

US 01.03.01
As a player, I want to remove QR codes from my account.

US 01.04.01
As a player, I want to see my highest and lowest scoring QR codes.

US 01.05.01
As a player, I want to see the sum of scores of QR codes that I have scanned.

US 01.06.01
As a player, I want to see the total number of QR codes that I have scanned.

US 01.07.01
As a player, I want to see other player’s profiles

Game QR Codes

US 02.01.01
As a player, I want to be able to scan QR codes and record a photo of the location or object, and the geolocation of the location or object.

US 02.02.01
As a player, I want to be able to comment on QR codes.

US 02.03.01
As a player, I want to be able to browse QR codes that other players have scanned.

US 02.04.01
As a player, I want to see that other players have scanned the same QR code.

US 02.05.01
As a player, I want QR codes to have a unique human readable name.

US 02.06.01
As a player, I want to see a visual representation of a QR code relatively unique to that QR code.


Player profile

US 04.01.01
As a player, I want a profile with a unique username and my contact information.

US 04.02.01 
As a player, I do not want to log into my application using a username and password as my device can identify me.


Searching

US 05.01.01
As a player, I want to search for other players by username.

US 05.02.01
As a player, I want to search for nearby QR codes by using geolocation.


Location

US 06.01.01
As a player, I want to see a map of geo-locations of nearby QR codes.


Scoring

US 07.01.01
As a player, I want to see game-wide high scores of all players.

US 07.02.01
As a player, I want an estimate of my ranking for the highest scoring unique QR code.


Privacy

US 08.01.01
As a player, I don’t want the actual code recorded. E.g., so I can scan and score my vaccine passport.

US 08.02.01
As a player, I want to be able to decline recording geolocation for privacy reasons.


Owner

US 09.01.01 
As an owner, I don’t want to store big images online.



Firebase was used for cloud storage.
There were two collections that the app worked with.

all codes has the following feilds:
a) comment : string
b) hash : string
c) humanName : string
d) imageString : string
e) location : geopoint
f) qrimage : string
g) score : integer

1) Users: a collection of the documents of all players playing the game. Document names are the usernames for each player.
Each document in the Users colletion has the following feilds.

	i) collectedQRCodes
		a) backToMap : Bool
		b) currentCode : Code being looked at
		c) highest : highest code
		d) lowest : lowest code
		e) highestUniqueScore : integer
		f) size: integer
		g) total : integer
		h) userCodes :  array of codes that the user has scanned
		
	ii) devices : string
	
	iii) email : string
	
	iv) highestUniqueCode : integer
	
	v) id : string
	
	vi) phone : string
	
	vii) rank : integer
	
	viii) totalScore : integer
	
	ix) username : string
	
2) qrCollect: a collection of all the QR codes that have been ever scanned by any player. Each document name is the hash of the QR code.
Each document in theqrCollect colletion has the following feilds.

	i) hash : string

	ii) locPic : string

	iii) location : geopoint

	iv) name : string

	v) playerwhoScanned : array of string
