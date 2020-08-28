# Anagram Assignment

A solution to the challenge at https://docs.google.com/document/d/1MfiR4QA9h4eYa3vSSpyqMCpxdBX3h4PEhB89Epliwmw
, written in Java.

## To run the code:
The input file should be accessible at a known path. A Gradle wrapper is provided, so from the root of the project

```./gradlew run --args 'PATH_TO_INPUT_FILE'```

eg 
```
$ ./gradlew run --args 'C:\Users\michael\Downloads\example1.txt'
   
   > Task :run
   fun,unf
   abc,bac,cba
   hello
```


## Big O Analysis

It seems like there are two variables affecting worst-case time complexity: the number of lines of input, and the
 length of the longest word. 

In respect of the *number of lines of input*, `n`
```
Iterating over the words => O(n)
    Computing value for word => O(1)
    Inserting `value:word` pair into Map (hash table) => O(n) 
Iterating over complete map and consuming results => O(n)
----------
Worst: O(n)
```

In respect of the *length of the longest word*, `m`
```
Iterating over the words => O(n)
    Computing value for word => O(m)
    Inserting value and word into Map (hash table) => O(n)
Iterating over complete map and consuming results => O(n)
Worst: O(m)
```

So I believe the time complexity is `O(nm)`


## Reasons for choosing data structures
ArrayList chosen to gather the words of same length due to fast adds and gets

Map for the pointsToWordsMap is effectively a hashtable, offering fast inserts and is quick to iterate over its
 buckets when producing the output


## Given more time...
What to do next would be steered by feedback from stakeholders and direction about which improvements may be most
 impactful. These may include:
 - Validation of the word as it's provided to ensure it only contains characters a-z, and decide what to do with
  validation failures
 - memoisation of `AnagramComputer.getPointsForChar()`, saving results for each char in a hashtable as computed, or
  precomputing the values. This would likely save a small amount of time on larger data sets at the cost of
  slightly more memory use
- If more performance were needed, at the cost of readability, some of the Streams API calls could be replaced by
 custom lower-level implementations which run be faster, if harder to understand and maintain
- Replacing Scanner with BufferedReader may yield performance improvements, but this would need testing.
- Implementing retry policy in the event that the connection to the disk was lost (as may be more likely if
 target file is on a network share, for example)
- Parallelisation of the "points" computation may offer some performance benefits

## Assumptions:

- that the input characters are all alphabetic characters a-z, with no unicode
- that duplicate words do not count as anagrams and should appear in output only once. This assumption is supported by the single
  appearance of "fun" in the demo output despite its appearance twice in example1.txt
- that the order of words _inside_ groups does not matter (the spec does not seem to mention this, although it does confirm that the order of groups themselves does not matter)
- Examples 1 and 2 seem to deal just with lower case characters, but the example Output on the google doc shows an
 uppercase "H" on "Hello". The solution presupposes that this is not significant and that any casing on inputs does not need to be
 preserved on outputs
 - that no input word is likely to be above 700 characters in length (although longer words are likely supported, I have
  not prioritised finding the limit - see unit test)
- that the "separate by newline" requirement does not discriminate between the various newline indicators in Linux/MacOS
/Windows


  