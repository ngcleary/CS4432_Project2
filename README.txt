Nora Cleary | 175441581

Section I
To compile: javac *.java
To run: java Main.java
 - All .txt files are in the folder Project2Dataset and are located at the top level of the project
 - All commands must be written as provided in the project descriptions, with no additional characters (including spaces)

 Section II
 All parts of the code are working.

 Section III
 I created a Query class with 5 attributes (index object, v1, v2, notEquals values, and a hasIndex flag).
 I utilize a flag variable called hasIndex to determine if an index has already been created. With this flag I can reuse the code
 to read from the directory, either creating an index or performing a table scan. If a select statement is issued and hasIndex is
 false, I set the query objects v1, v2, and notEquals variables and run the readDirectory method. In this method a table scan
 is used to select the records, using the assigned object attributes. If a create statement is issued and hasIndex is false, the
 readDirectory method is called, but the v1, v2, notEquals attributes are not set, so the method calls add to index on each file
 as it reads from the directory. 
 The index class is where the hash-based index and array-based index logic is. Here each record is added to each index as it is 
 read from the query class.