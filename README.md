# ToDo App - Abgabeprojekt
HHN - AIB3 - LabAppEntw - Task 9 - Persistence and Database implementation

## Installation
Download the ![apk](https://github.com/Luke-l2e/LabAppEntwTask9/blob/master/app/release/app-release.apk) and execute it on your mobile

## Overview and description
This app has three screens:
1. The Dashboard. That's your starting point when opening the app.
2. The Active Tasks screen. It will list all open ToDo's.
3. The Finished Tasks screen. It will list all finished ToDo's.

### Dashboard
You have to buttons which lead you to either the Active Tasks screen or the Finished Tasks screen.\
<img src="https://github.com/user-attachments/assets/51b35d21-0da3-4699-9cea-439219d6d51d" alt="Dashboard" width="300"/>\

### Task List
As you can see from top to bottom:
* An arrow to return to the Dashboard
* Several expandable items. Each with a name, description, priority and date
* When pressing on an expanded item for a few seconds it will open a dialog to edit it
* at the bottom right corner there's a floating action button to create a new task

<img src="https://github.com/user-attachments/assets/0d7aee70-8453-4bdf-8932-98fdd3eadb77" alt="Task List" width="300"/>

### Editable tasks
As said you can either create new tasks or edit them. On top of them you can also delete them permanently.\
You can set the date, by clicking on it. It's shown in the system locale and zone format. So depending what you're used to it should look familiar.\
When you toggle the finish checkbox and save it it will disappear from the current list and be shown in the other one.\
<img src="https://github.com/user-attachments/assets/dbcecdf2-0e92-48f7-9b9c-d003dec24d3f" alt="Edit Task" width="300"/>

### Choosable Priority
When clicking on the priority you can see a drop down menu from which you can select your preferred priority.\
<img src="https://github.com/user-attachments/assets/919eedf8-6969-49b2-bd8b-919e6031debd" alt="Priority Drop Down Menu" width="300"/>

### Date Picker
When clicking on the date in the editable view a date picker will pop up allowing you to select a date.\
<img src="https://github.com/user-attachments/assets/a805fdd7-bca2-49c5-9a2a-c2c95cda2274" alt="Date Picker" width="300"/>

### Time Picker
After selecting a date a time picker will let you choose a specific time.\
<img src="https://github.com/user-attachments/assets/e389fbe6-15ba-4843-b6ba-0c8f53836f5b" alt="Time Picker" width="300"/>

### Internationalization
The app currently supports two languages:
* english (default)
* german

You can choose between them in the app settings if you're using android 13 or higher\
<img src="https://github.com/user-attachments/assets/59b346a8-4595-42bc-90e4-a3424b7607cf" alt="Language Settings" width="300"/>

## Features
* SQLite database integration
* CRUD operations
* Implementation of a DatabaseHelper
* Demonstration of using ContentValues and Cursors
* Example of working with ListViews

## Known issues
* No dark mode support
* Rotating your device will reset the current Screen
* Bad looking design

*There's no update planned. It's the second time within 3 months that I created a ToDo-app. But I'm planning on updating my ![Random Game Selector](https://github.com/Luke-l2e/RandomGameSelector) from scratch soon.*

# Credits
<a href="https://www.flaticon.com/free-icons/to-do-list" title="to do list icons">App Logo created by Freepik - Flaticon</a>
