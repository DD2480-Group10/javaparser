# Report for assignment 3

## Project

Name: javaparser

URL: https://github.com/javaparser/javaparser

Javaparser is a Java 1-15 Parser and Abstract Syntax Tree for Java, including preview features to Java 13. 

## Onboarding experience

#### javaparser

1\. Did it build and run as documented?
- We needed to install maven to build the software.
- It was documented regarding how to install and build the software
- Yes, there were other components installed (maven dependencies and plugins).
- Yes, outside of some initial build issues related to the JAVA versions, and operative systems used it run as documented as there was not much information regarding building the project.
- It had Tests run: 1693, Failures: 0, Errors: 0, Skipped: 28 when running “mvn test”
- When running the java test folder for javaparser-core it passed 1897 tests, and skipped 7 tests



2\. Do you plan to continue or choose another project?

We will go forward with this project

## Complexity

1. What are your results for ten complex functions?

The complexity calculation
Is provided in a separate branch for the following methods

|Method|Calculated CC|Lizard CC|  
| ----------- | -----------   |--------| 
|findNodeListName | 15| 14|
|findIndexOfCorrespondingNodeTextElement | 16|15 |
|cleanLines |6 | 14|
|toBinaryOperator | 2| 12|
|cleanTheLineOfLeftOverSpace |14 |14 |
|prettyPrintingTextNode| 14| 14|
|matching|13|13|
|calculate|12|13|
|toToken|13|13|
|isAssignableMatchTypeParametersMatchingQName| 14| 14|

Some of us calculated the branch coverage by first drawing the control flow graph by hand and then used the formula M = E - N + 2. Alternatively we also used the formula decision points - exit points + 2 for some functions with multiple exit points. 



2. Are the functions just complex, or also long?

|Method|Lizard CC|Lines of code|
| -----------|-----------|--------|
|findNodeListName | 14| 38|
|findIndexOfCorrespondingNodeTextElement | 15|47 |
|cleanLines |14 | 36|
|toBinaryOperator | 12| 28|
|cleanTheLineOfLeftOverSpace |15 |27 |
|prettyPrintingTextNode| 14| 55|
|matching|13|36|
|calculate|13|13|
|toToken|13|33
|isAssignableMatchTypeParametersMatchingQName|14|39

As shown in the table most of the methods are not very long or medium length, so they are mostly just complex. Note that toBinaryOperator decreased from 12 to 2 when doing it by hand compared to lizard. This is probably because lizard most likely counts certain keywords. For example *if*, *else*, *for* etc that sometimes affects the complexity estimation. As such these tools are not always a good measure of the actual complexity depending on how the theory is applied it can yield different results.

3. What is the purpose of the functions?

- *findNodeListName*: No code comment but method name and return string in error case: “Cannot find list name of NodeList of size” shows that it’s purpose is to find the name of a NodeList

- *findIndexOfCorrespondingNodeTextElement*: No code comments but name of method reveals it’s purpose is to find the index of a NodeText element

- *toBinaryOperator*: The toBinaryOperator method is a method in the enum that converts an assignment operator into the corresponding binary operator. The method lacks documentation, but there is some in the regarding class containing the method.

- *cleanLines*: has no comments regarding the purpose, but the method aims to remove lines only containing
  whitespace as well as lines without any characters. It will also remove whitespaces in front of asterisks
  assuming that the line contains useful comments.

- *prettyPrintingTextNode*: “Methods to handle transformation”. It adds the appropriate token to the variable nodeText, depending on what type of node is given. I.e. it checks if the node is of a certain instance, e.g. primitive data type, and sets the appropriate token. It seems to have a high CC due to taking into account many different node types.

- *cleanTheLinesOfLeftOverSpace*: “Cleans the line of left over space if there is unnecessary indentation and the element will not be replaced.” This method is called from another function which removes elements, to clean up the lines. The CC seems to be high due to having many conditionals in some of the if-statements.

- *matching*: checks if two “concrete syntax model elements” are matching, i.e. identical, checks that both elements are the same type and where applicable that they have the same contents.

- *calculate*: calculate the Difference between two CalculatedSyntaxModel elements, determining which elements were kept, which were added and which were removed

- *toToken*: Convert a keyword enum into a corresponding int.

- *isAssignableMatchTypeParametersMatchingQName*: Check if two reference types given parameters have matching qualify names.



4. Are exceptions taken into account in the given measurements?

- The language uses exceptions, but they are not used in most of the methods, as such it does not seem to affect the Cyclomatic Complexity for most of the chosen methods. In one method where there are exceptions, removing them does not seem to affect the cyclomatic complexity measurement in lizard either.

5. Is the documentation clear w.r.t. all the possible outcomes?

- *cleanLines* Has little to no documentation, but the method name, and header is telling.

- *toBinaryOperator* Has no documentation, but the methods goal is fairly simple.

- *findNodeListName* Has no documentation (but method name reveals the purpose)
- *findIndexOfCorrespondingNodeTextElement* Has no documentation (but method name reveals the purpose)
- *binaryToOperator* Lacks extensive documentation regarding the outcomes
- *cleanLines* Lacks extensive documentation regarding the outcomes.
- *matching* Has no documentation, but is pretty understandable anyways.
- *calculate* The comment explains only what the expected input and outputs are.
- *cleanTheLinesOfLeftOverSpace* Has some sort of documentation of the possible outcomes.
- *prettyPrintingTextNode* Has almost no documentation at all.
- *toToken*: Has no no documentation at all. 
- *isAssignableMatchTypeParametersMatchingQName*: Has no ducomentation except internal method comments that explain little.


## Refactoring

Plan for refactoring complex code:

### findNodeListName
- *Refactoring plan*:
  This method has a section were after a if-statement it set:
  String name = m.getName();
  if (name.startsWith("get")) {
  name = name.substring("get".length());
  }
  return ObservableProperty.fromCamelCaseName(decapitalize(name));
This is repeated for a second if-statement later in the method. So this section could be refactored into a separate method. So instead of having to write this whole section again it could call the other method. This would reduce the CC with 2 points from 15 to 13.

### cleanLines
- *Refactoring plan*: If we want to simplify the cleanLines method to reduce the cyclomatic complexity we can do so
  by splitting up the method into two separate methods.
  It could be done by collecting the lines, and then returning them at line 119 in the JavadocParser class.
  Since the method is now split up into separate methods the
  cyclomatic complexity for both of these will be < 10, thus reducing the complexity. 
```java 
/*
* If we want to simplify the cleanLines method to reduce the cyclomatic complexity we can do so
* by splitting up the method to two separate methods.
* It could be done by collecting the lines, and then returning them at line 119, line 29 in this code snippet.
* Since the method is now split up into separate methods the
* cyclomatic complexity for both of these will be < 10, thus reducing the complexity.
*/
private static List<String> cleanLines(String content) {
       String[] lines = content.split(SYSTEM_EOL);
       if (lines.length == 0) {
           return Collections.emptyList();
       }
       List<String> cleanedLines = Arrays.stream(lines).map(l -> {
           int asteriskIndex = startsWithAsterisk(l);
           if (asteriskIndex == -1) {
               return l;
           } else {
               // if a line starts with space followed by an asterisk drop to the asterisk
               // if there is a space immediately after the asterisk drop it also
               if (l.length() > (asteriskIndex + 1)) {
                   char c = l.charAt(asteriskIndex + 1);
                   if (c == ' ' || c == '\t') {
                       return l.substring(asteriskIndex + 2);
                   }
               }
               return l.substring(asteriskIndex + 1);
           }
       }).collect(Collectors.toList());
       // lines containing only whitespace are normalized to empty lines
       /*
       * Here we can return the cleanedLines and send it to the new method.
       * The rest of the method can be refactored to the new method to reduce the complexity.
       */
       cleanedLines = cleanedLines.stream().map(l -> l.trim().isEmpty() ? "" : l).collect(Collectors.toList());
       // if the first starts with a space, remove it
       if (!cleanedLines.get(0).isEmpty() && (cleanedLines.get(0).charAt(0) == ' ' || cleanedLines.get(0).charAt(0) == '\t')) {
           cleanedLines.set(0, cleanedLines.get(0).substring(1));
       }
       // drop empty lines at the beginning and at the end
       while (cleanedLines.size() > 0 && cleanedLines.get(0).trim().isEmpty()) {
           cleanedLines = cleanedLines.subList(1, cleanedLines.size());
       }
       while (cleanedLines.size() > 0 && cleanedLines.get(cleanedLines.size() - 1).trim().isEmpty()) {
           cleanedLines = cleanedLines.subList(0, cleanedLines.size() - 1);
       }
       return cleanedLines;
   }
  ```

### prettyPrintingTextNode
- *Refactoring plan*:
  The high complexity isn’t really needed for this method. It has a lot fo if/else statements and switch statements, due to handling many different node types. One way to go about refactoring this method is to split up the method into two smaller ones. Where the whole switch statemet is moved to a separate method, that prettyPrintingTextNode calls to when needed. 

### matching
- *Refactoring plan*: Most of the complexity in this method comes from the fact that it needs to validate the input objects to see if they are one of four types. To refactor i will take the validation step out of the method and make it a separate method. The rest of the code will be cleaned up to be as concise as possible.

### toToken
- Refractoring for this method while possible, would not make the code any less complex since the high complexity is necessary for this method to function.
- *Refractoring plan*: A way to decrece the CC of this method would be to split the switch into two methods. So that the default branch of the first switch calls and then returns the results of a method that contains the other half of the switch case. 


Estimated impact of refactoring (lower CC, but other drawbacks?).

- *cleanLines*: Reduced CC, but the total number lines of code is not reduced. Could also improve testing due the lower CC. In order to improve the refactoring and code quality further a more thorough refactoring could be implemented.

- *prettyPrintingTextNode*: The CC would be reduced, by how much I am not completly sure. I think that it will get lowered by a lot, due to the switch statement being very large. But the number of lines wouldn't be decreased. In this case I am not sure what the drawback could be, maybe a perfromance hit due to need to call to a separate function instead on keeping it all on the same function. 

- *matching*: The CC will be lowered from and the number of lines will be reduced. To be honest legibility is probably going to be somewhat decreased from this, and the code might not be as self-explaining as it was before.

- *toToken*: The planned refractoring of this method would reduce the CC by 11 - 1 depending on were the switch is split. Spliting early (i.e moving alot of the case arms to a new method) would reduce the CC more then spliting late (i.e moving only a few arms to the new method).

### Refactored methods (optional, P+):

These are the methods which are refacored to receive the extra point.
|Method|Name|Branch|% decrease|Pre-CC|Post-CC|git command|
|-|-|-|-|-|-|-|
|matching|Hans Stammler|feat/#14|61%|13|5|git diff feat/#7 feat/#14
|prettyPrintingTextNode|Claudia Berlin|refactor/#12|57%|14|6|git diff test/#6 refactor/#12|
|toToken| Adam Giscombe Schmidt| refactor/#24| 69%| 13 | 4 | git diff test/#11 refacotr/#24|

## Coverage

### Tools

Document your experience in using a "new"/different coverage tool.

How well was the tool documented? Was it possible/easy/difficult to
integrate it with your build environment?

Jacoco was a really convenient and useful coverage tool. It generated an index webpage where you could navigate to any file/method and see complexity, LOC and branch coverage. Jacoco came already included in the project we chose so no setup was needed and thus we cannot really comment on the set up and documentation.

### Your own coverage tool

Show a patch (or link to a branch) that shows the instrumented code to
gather coverage measurements.

- Our tool can be found on branch: *feat/#4*. Where the class CodeCoverage.java contains the datastructure and methods of our tool. Meanwhile, LexicalPreservingPrinterTest.java and LexicalPreservingPrinter.java shows how it can be used.  

- The command “git diff master feat/#4” shows how our coverage tool was implemented and how the flags were set in *prettyPrintingTextNode*. 


What kinds of constructs does your tool support, and how accurate is its output?

The way the tool was implemented is such that it has to be called within the test files where your method is called for it to count branches reached there. Becasue of this it will not count branches reached by indirect calls to the method outside of the test file. As such the branch coverage might be less than reported in Jacoco, but it works accurately for all tests in the same file.

### Evaluation

1. How detailed is your coverage measurement?

It measures the branches taken in the functions. The specific branch points are added manually in the functions.

2. What are the limitations of your own tool?

The branch points are added manually as such they are subject to human error.
They are explicitly written for the test methods, as such they can not be called outside of these.
The tool can not account for calls to the function from outside the specific test file it is used in. Because of this our tool will sometimes report less coverage than the existing ones.

3. Are the results of your tool consistent with existing coverage tools?

It is not as consistent as intelliJ’s built in tool, or jacoco. See answer to 2.

## Coverage improvement

Show the comments that describe the requirements for the coverage.

Report of old coverage:
- Note that *Manual BC* is calculated using the developed coverage tool.
- Note that the JaCoCo report is generated after running mvn test and is to large to be added to git. 

|Method|Jacoco BC|Manual BC|branch|
|-|-|-|-|
|matching|87%|10/15|feat/#7|
|toBinaryOperator|8%|1/12|feat/#21|
|prettyPrintingTextNode|12/19 (68 %)|10/19 (53%)|feat/#4|
|toToken|38%|0/13|feat/#10|


Report of new coverage:
|Method|Jacoco BC|Manual BC|branch|
|-|-|-|-|
|matching|100%|15/15|feat/#13|
|toBinaryOperator|25%|3/12|test/#22|
|prettyPrintingTextNode|16/19 (84%)|15/19 (79%)|test/#6|
|toToken| 100% | 13/13| test/#11

Test cases added:

|Method|git command|# tests added|
|-|-|-|
|matching|git diff feat/#7 feat/#13 | 5|
|toBinaryOperator|git diff test/#22 feat/#21|2|
|prettyPrintingTextNode|git diff feat/#4 test/#6|5|
|toToken| git diff feat/#10 test/#11| 13

Number of test cases added: two per team member (P) or at least four (P+).

- *Hans*: added 5 test cases for 100% coverage (from 87%).
- *Claudia*: Added 5 test cases for prettyPrintingTextNode. It increased the coverage from 68 % to 84 % (JaCoCo) and from 53 % to 78 % (our manual coverage tool).
- *Adam*: Added 13 test cases for toToken increased coverage from 38% to 100%.

## Self-assessment: Way of working

Current state according to the Essence standard:

According to the essence checklist, we are currently residing on the *“In Place”-level*. We consider ourselves to have fulfilled the checklist items before *“In Place”*. We have discussed the project thoroughly in person and via online meetings to get all group members working in tandem and to familiarize ourselves with the chosen tools. However, it is a new group we do not yet fulfill the *“Working well”-level*, but we are heading towards it.




Was the self-assessment unanimous? Any doubts about certain items?

How have you improved so far?
- We have learned how to search for and work on open source software as a group.
- We have learned the underlying theory of cyclomatic complexity.
- We have implemented a branch coverage tool.

Where is potential for improvement?

- Better understanding of the open source project.
- Improved testing.
- Improved coverage tool.

## Overall experience

What are your main take-aways from this project? What did you learn?

Open source is powerful, but difficult.

Is there something special you want to mention here?

In the future compile a list of open source projects checked by the staff to clearly state that they are accessible, working, and easier to judge grade-wise.
