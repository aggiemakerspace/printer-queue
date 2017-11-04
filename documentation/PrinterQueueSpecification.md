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

DIAGRAM GOES HERE

## Development

### Enumerations

#### PrintType

The PrintType enumeration contains a constant value representing the purpose of a PrintJob.

Value | Comments
---------- | ------------------------------
ASSIGNMENT | Assigned to PrintJobs related to an assigned class project with a set due date
TEAM_PROJECT | Assigned to PrintJobs related to a project overseen by a campus organization of College of Engineering competitive team (ie SAE Baja, AutoDrive)
PERSONAL | Assigned to PrintJobs for the personal use or enjoyment by an NCAT student or group of students

### Data Types

#### Student

The `Student` datatype holds information related to a single Makerspace user. The purpose of the `Student` datatype is to hold the basic information required to track student work and contact the student when the print is completed.

##### Readonly Fields

Type | Name | Comments
---------- | ---------- | ------------------------------
String | firstName | The first or given name of the Makerspace user
String | lastName | The last or family name of the Makerspace user
String | studentID | The 9-digit student identification number, used as a key for users in the directory
String | emailAddress | The preferred contact email address of the user for notifications regarding their PrintJobs
String | phoneNumber | The preferred contact phone number of the user for notifications regarding their PrintJobs

#### PrintJob

#### PrinterQueue

#### StudentDirectory
