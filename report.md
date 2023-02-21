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

- *findNodeListName * Cylomatic complexity: 15,  lizard evaluated to 14
- *findIndexOfCorrespondingNodeTextElement * Cylomatic complexity: 16,  lizard evaluated to 15
- *cleanLines* Cylomatic complexity: 6, lizard evaluated to 14.
- *toBinaryOperator* Cyclomatic complexity: 2, lizard evaluated to 12.
- *prettyPrintingTextNode* Cyclomatic complexity: 14, lizard evaluated to 14.
- *cleanTheLineOfLeftOverSpace* Cyclomatic complexity: 14, lizard evaluated to 15.
- *matching*: Cyclomatic complexity: 13, lizard evaluated to 13
- *calculate*: lizard evaluated to 13


2. Are the functions just complex, or also long?

- *findNodeListName * Cylomatic complexity: 38 lines of code
- *findIndexOfCorrespondingNodeTextElement * 47 lines of code
- *cleanLines* 36 lines of code.
- *toBinaryOperator*  28 lines of code.
- *prettyPrintingTextNode* 55 lines of code.
- *cleanTheLineOfLeftOverSpace* 27 lines of code.
- *matching* 36 lines of code
- *calculate* 13 lines of code

3. What is the purpose of the functions?

- *findNodeListName *: No code comment but method name and return string in error case: “Cannot find list name of NodeList of size” shows that it’s purpose is to find the name of a NodeList

- *findIndexOfCorrespondingNodeTextElement*: No code comments but name of method reveals it’s purpose is to find the index of a NodeText element

- *toBinaryOperator*: The toBinaryOperator method is a method in the enum that converts an assignment operator into the corresponding binary operator. The method lacks documentation, but there is some in the regarding class containing the method.

- *cleanLines*: has no comments regarding the purpose, but the method aims to remove lines only containing
  whitespace as well as lines without any characters. It will also remove whitespaces in front of asterisks
  assuming that the line contains useful comments.

- *prettyPrintingTextNode*: “Methods to handle transformation”. It adds the appropriate token to the variable nodeText, depending on what type of node is given. I.e. it checks if the node is of a certain instance, e.g. primitive data type, and sets the appropriate token. It seems to have a high CC due to taking into account many different node types.

- *cleanTheLinesOfLeftOverSpace*: “Cleans the line of left over space if there is unnecessary indentation and the element will not be replaced.” This method is called from another function which removes elements, to clean up the lines. The CC seems to be high due to having many conditionals in some of the if-statements.

- *matching*: checks if two “concrete syntax model elements” are matching, i.e. identical, checks that both elements are the same type and where applicable that they have the same contents.

- *calculate*: calculate the Difference between two CalculatedSyntaxModel elements, determining which elements were kept, which were added and which were removed

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
  cyclomatic complexity for both of these will be < 10, thus reducing the complexity. A more extensive refactoring plan was done in branch doc/#18

### prettyPrintingTextNode
- *Refactoring plan*:
  For this function the high complexity isn’t really necessary, there are multiple if/else statements and switch statements. Due to the function handling nodes of multiple types, it is possible to split it up into multiple functions. The simplest way would be to have the switch statements on a different method, which is called upon when the node is an instance of primitive data type.

### matching
- *Refactoring plan*: Most of the complexity in this method comes from the fact that it needs to validate the input objects to see if they are one of four types. To refactor i will take the validation step out of the method and make it a separate method. The rest of the code will be cleaned up to be as concise as possible.

The refactoring plans are included in a separate refactoring branch.

### Refactored methods:

- *matching*: is refactored in branch #14.

- *prettyPrintingTextNode* is in branch refactor/#12. The CC was reduced from 14 to 6. The command “git diff test/#6 refactor/#12” shows the changes that were made in the refactoring.


Estimated impact of refactoring (lower CC, but other drawbacks?).

- *cleanLines*: Reduced CC, but the total number lines of code is not reduced. Could also improve testing due the lower CC. In order to improve the refactoring and code quality further a more thorough refactoring could be implemented.

- *prettyPrintingTextNode*:  The CC was lowered from 14 to 6 (57 % decrease, P+) but the total number of lines wasn’t reduced, due to only splitting it up into two methods.

- *matching*: The CC was lowered from 13 to 5 (61% decrease, gib P+), and the number of lines were reduced. To be honest legibility is somewhat decreased from this, and the function does not really explain itself as it did before.

Carried out refactoring (optional, P+):

- *matching*: is refactored in branch #14. The CC was lowered from 13 to 5 (61% decrease, gib P+) git diff in DifferenceElementCalculator,.java is 16 additions, 40 deletions.

## Coverage

Jacoco was a really convenient and useful coverage tool. It generated an index webpage where you could navigate to any file/method and see complexity, LOC and branch coverage. Jacoco came already included in the project we chose so no setup was needed and thus we cannot really comment on the set up and documentation.

### Tools

Document your experience in using a "new"/different coverage tool.

How well was the tool documented? Was it possible/easy/difficult to
integrate it with your build environment?

### Your own coverage tool

Show a patch (or link to a branch) that shows the instrumented code to
gather coverage measurements.

The patch is probably too long to be copied here, so please add
the git command that is used to obtain the patch instead:

git diff …


What kinds of constructs does your tool support, and how accurate is
its output?

- The command “git diff master feat/#4” shows how our coverage tool was implemented and how the flags were sets in *prettyPrintingTextNode*. It also shows the text file that was generated from the tests.


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

Report of old coverage: [link]

Report of new coverage: [link]

Test cases added:

git diff ...

Number of test cases added: two per team member (P) or at least four (P+).

Hans: added 5 test cases for 100% coverage.
Claudia: Added 5 test cases for prettyPrintingTextNode. It increased the coverage from 68 % to 84 % (JaCoCo) and from 53 % to 78 % (our manual coverage tool).

## Self-assessment: Way of working

Current state according to the Essence standard: …

According to the essence checklist, we are currently residing on the “In Place”-level. We consider ourselves to have fulfilled the checklist items before “In Place”. We have discussed the project thoroughly in person and via online meetings to get all group members working in tandem and to familiarize ourselves with the chosen tools. However, it is a new group we do not yet fulfill the “Working well”-level, but we are heading towards it.




Was the self-assessment unanimous? Any doubts about certain items?

How have you improved so far?
We have learned how to search for and work on open source software as a group.
We have learned the underlying theory of cyclomatic complexity.
We have implemented a branch coverage tool.

Where is potential for improvement?

Better understanding of the open source project.
Improved testing.
Improved coverage tool.

## Overall experience

What are your main take-aways from this project? What did you learn?

Open source is powerful, but difficult.

Is there something special you want to mention here?

In the future compile a list of open source projects checked by the staff to clearly state that they are accessible, working, and easier to judge grade-wise.
