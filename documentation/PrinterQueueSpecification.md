# Printer Queue

Printer Queue is a JavaFX application developed by the Aggie Makerspace at North Carolina A&T State University to manage student projects waiting to be brought to life on the 3D printer.

PrinterQueue is licensed under the GNU General Public Use license v3.0. This program is free software, you can redistribute it and/or modify it under the terms of the aforementioned license which can be reviewed in full [here](http://www.gnu.org/licenses).

Version | Date | Prepared By | Reviewed By
---------- | ---------- | ------------------- | -------------------
v0.1 | 2017-11-6 | Chris Cannon | Jonathan Reddix

## Scope

This specification is the ultimate source of information regarding the design and implementation of PrinterQueue. This document supersedes specifications issued before the DATE listed above and any specifications numbered before the VERSION listed above.

## Class Diagram

Printer Queue shall be developed in accordance with the following class diagram. For implementation details of each class, see subsequent sections.

![Application UML Diagram](https://imgur.com/YHlCBo0.png)

## Enumerations

### PrintType

The PrintType enumeration contains a constant value representing the purpose of a PrintJob.

![PrintType Diagram](https://imgur.com/cCZHVyM.png)

Value | Comments
---------- | ------------------------------
ASSIGNMENT | Assigned to PrintJobs related to an assigned class project with a set due date
TEAM_PROJECT | Assigned to PrintJobs related to a project overseen by a campus organization of College of Engineering competitive team (ie SAE Baja, AutoDrive)
PERSONAL | Assigned to PrintJobs for the personal use or enjoyment by an NCAT student or group of students

### PrintStatus

The PrintStatus enumeration contains a constant value representing the current status of a PrintJob

![PrintStatus Diagram](https://i.imgur.com/iSNyfEV.png)

Value | Comments
---------- | ------------------------------
READY_TO_PRINT | The PrintJob is ready to be printed.
READY_TO_PICKUP | The PrintJob is printed and ready to be picked up.
COMPLETED | The PrintJob is completed and picked up.

## Data Types

### Student

The `Student` datatype holds information related to a single Makerspace user. The purpose of the `Student` datatype is to hold the basic information required to track student work and contact the student when the print is completed.

![Student Class Diagram](https://i.imgur.com/oC6xmw7.png)

#### Readonly Properties

Type | Name | Comments
---------- | ---------- | ------------------------------
String | firstName | The first or given name of the Makerspace user
String | lastName | The last or family name of the Makerspace user
String | studentID | The 9-digit student identification number, used as a key for users in the directory
String | emailAddress | The preferred contact email address of the user for notifications regarding their PrintJobs
String | phoneNumber | The preferred contact phone number of the user for notifications regarding their PrintJobs

#### Public Methods

Return Type | Name | Parameters | Comments
----------- | ---------- | ---------- | ------------------------------
String | toString | _none_ | Returns each of the properties as a series of comma-separated values (csv)

### PrintJob

The `PrintJob` datatype holds information related to a single print to be made. The purpose of the `PrintJob` datatype is to hold all information required for a superuser to locate and print a .stl file and inform the requesting student when it is complete.

![PrintJob Diagram](https://i.imgur.com/YvL1kH8.png)

#### Readonly Properties

Type | Name | Comments
---------- | ---------- | ------------------------------
String | stlPath | The absolute path to the .stl file for this print on the computer
PrintType | type | The PrintType of this print, used to determine priority with Class Assignments receiving the highest priority and personal projects receiving the lowest priority
Date | dueDate | The date by which the student requires this print to be completed. For personal projects, this is set to 5 days from the request.
String | requestComments | Any additional information that the requesting student would like to provide about this PrintJob
Student | requestingStudent | The Makerspace user who is requesting this PrintJob

#### Properties

Type | Name | Comments
---------- | ---------- | ------------------------------
String | printComments | Comments from the user running the printer regarding the outcome or result of the print.
PrintStatus  |  status | Current status of the print.

#### Constants

Type | Name | Comments
---------- | ---------- | ------------------------------
SimpleDateFormat | dateFormat | The SimpleDateFormat used to interpolate Date objects to and from the "MM/dd/yyyy" format

#### Public Methods

Return Type | Name | Parameters | Comments
----------- | ---------- | ---------- | ------------------------------
String | toString | _none_ | Returns each of the properties of the PrintJob in the format shown below

##### toString Format
> _stlPath, type, dueDate_
>
> _requestingStudent as a csv of student values_
>
> _comments_
>
> END

It is important to include END as the final line of a PrintJob toString to protect against cases where comments include an unknown number of new line characters.

### PrinterQueue

The `PrinterQueue` datatype holds the PrintJobs processed by this program in three separate ArrayLists. The `PrinterQueue` will interact directly with the user interface to add, process, and complete PrintJobs.

![PrinterQueue Diagram](https://i.imgur.com/bNwn4OV.png)

#### Properties

Type | Name | Comments
---------- | ---------- | ------------------------------
ArrayList<PrintJob> | queue | The PrintJobs that need to be printed
ArrayList<PrintJob> | waitingForPickup | The PrintJobs that have been printed but not yet picked up
ArrayList<PrintJob> | completed | The PrintJobs that have been printed and picked up
PrintJob  | lastPrintJobMoved | The PrintJob last moved between queues

#### Constants

Type | Name | Comments
---------- | ---------- | ------------------------------
String | queueConfigFileName | The name of the configuration file from which the queue is loaded when the application launches
String | waitingForPickupConfigFileName | The name of the configuration file from which the waitingForPickup list is loaded when the application launches
String | completedConfigFileName | The name of the configuration file from which the completed list is loaded when the application launches

#### Public Methods

Return Type | Name | Parameters | Comments
----------- | ---------- | ---------- | ------------------------------
void | savePrinterQueue | _none_ | Saves the current data in `queue` to the file specified in `queueConfigFileName`
void | loadPrinterQueue | _none_ | Loads the data in the file specified in `queueConfigFileName` to `queue`
void | saveWaitingForPickup | _none_ | Saves the current data in `waitingForPickup` to the file specified in `waitingForPickupConfigFileName`
void | loadWaitingForPickup | _none_ | Loads the data in the file specified in `waitingForPickupConfigFileName` to `waitingForPickup`
void | saveCompleted | _none_ | Saves the current data in `completed` to the file specified in `completedConfigFileName`
void | loadCompleted | _none_ | Loads the data in the file specified in `completedConfigFileName` to `completed`
String | toString | _none_ | Returns the toString for each PrintJob in `queue` separated by a new line character

#### ArrayList Handling

Each ArrayList should include the five standard public methods for handling ArrayLists:
Return Type | Name | Parameters | Comments
----------- | ---------- | ---------- | ------------------------------
void | addWidget | widget: Widget | Adds the given Widget to the ArrayList
void | setWidget | index: int, widget: Widget | Adds the given Widget to the ArrayList at the given index
Widget | getWidget | index: int | Returns the Widget stored at the given index
Widget | removeWidget | index: int | Removes the Widget stored at the given index and returns it
int | getNumWidgets | _none_ | Returns the number of items in the ArrayList

### StudentDirectory

The `StudentDirectory` datatype uses a HashMap to store and access all `Student` objects that have been used by the program. This allows quick access to all Students for searching and creating new PrintJobs associated with them.

![StudentDirectory Diagram](https://i.imgur.com/NeTi7pe.png)

#### ReadOnlyProperties

Type | Name | Comments
---------- | ---------- | ------------------------------
HashMap<String, Student> | directory | Holds each `Student` registered with the application, uses that `Student`'s studentID as the key

#### Constants

Type | Name | Comments
---------- | ---------- | ------------------------------
String | configFileName | The name of the configuration file from which the `directory` is loaded when the application launches

#### Public Methods

Return Type | Name | Parameters | Comments
----------- | ---------- | ---------- | ------------------------------
void | saveStudentDirectory | _none_ | Saves the current data in `directory` to the file specified in `configFileName`
void | loadStudentDirectory | _none_ | Loads the data in the file specified in `configFileName` to `directory`
boolean | containsStudent | _none_ | Returns true if the HashMap contains a `Student` with the given studentID `key`
Student | getStudent | key: String | Returns the `Student` associated with the given studentID `key`
void | putStudent | key: String, student: Student | Adds the given `Student` to the HashMap associated with the given studentID `key`
Collection<Student> | getStudents | _none_ | Returns a Collection of all `Student`s in `directory`
String | toString | _none_ | Returns the csv value of each Student in the directory separated by a new line character

## Interface

### PrinterQueueViewController

The `PrinterQueueViewController` is the main launch screen of the application. The `PrinterQueueViewController` should display the current print queue in a ListView as well as the waiting for pickup queue in another ListView. It should also include controls to add print jobs, view print jobs, and exit the application.

Name | UI Element | Description
--------------- | --------------- | ------------------------------
queueListView  | ListView |  Displays the contents of the queue waiting to be printed
waitingForPickupListView  | ListView | Displays the contents of the queue waiting to be picked up
addPrintJobButton | Button | Launches the AddPrintJobTextInputDialog
viewQueuePrintJobButton | Button | Launches PrintJobViewController for the given job waiting to print
viewWaitingForPickupPrintJobButton | Button | Launches PrintJobViewController for the given job waiting to be picked up
printJobCompletedButton  | Button | Moves the highlighted PrintJob in the queue to the waitingForPickupQueue
printJobPickedUpButton  | Button | Moves the highlighted PrintJob in the waitingForPickupQueue to the completedQueue
exitButton | Button | Saves the queue and exits the program
undoButton | Button | Reverses the last queue move operation

### PrintJobViewController

The `PrintJobViewController` is the screen that displays details regarding a single PrintJob. Immutable properties should be displayed in nodes that cannot be edited, and mutable properties should be displayed in an editable fashion and include submission buttons for new values.

Name | UI Element | Description
--------------- | --------------- | ------------------------------
stlPathLabel  | Label | self-explanatory
stlPathTextField  | TextField | self-explanatory
statusLabel  | Label | self-explanatory
statusTextField  | TextField | self-explanatory
typeLabel  | Label | self-explanatory
typeTextField  | TextField | self-explanatory
dueDateLabel  | Label | self-explanatory
dueDateTextField  | TextField | self-explanatory
requestCommentsLabel  | Label | self-explanatory
requestCommentsTextArea  | TextArea | self-explanatory
requestingStudentNameLabel  | Label | self-explanatory
requestingStudentNameTextField  | TextField | self-explanatory
requestingStudentIDLabel  | Label | self-explanatory
requestingStudentIDTextField  | TextField | self-explanatory
requestingStudentEmailLabel  | Label | self-explanatory
requestingStudentEmailTextField  | TextField | self-explanatory
requestingStudentPhoneLabel  | Label | self-explanatory
requestingStudentPhoneTextField  | TextField | self-explanatory
printCommentsLabel  | Label | self-explanatory
printCommentsTextArea  | TextArea | self-explanatory
savePrintCommentsButton | Button | Updates the information saved as printComments
transitionButton  | Button | Moves the PrintJob to the next status based on its current PrintStatus.

#### Fields
Type | Name | Comments
---------- | ---------- | ------------------------------
PrintJob | printJob | The PrintJob displayed in this view.

### AddPrintJobTextInputDialog

### AddPrintJobInputErrorAlertDialog
