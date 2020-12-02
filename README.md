# advent-of-code-2020
solutions to puzzles at [advent of code](https://adventofcode.com) in kotlin

more solutions of advent of code here: [Bogdanp/awesome-advent-of-code#awesome-advent-of-code](https://github.com/Bogdanp/awesome-advent-of-code#awesome-advent-of-code)

### day 1
1. given an list of numbers, find two numbers that sum to 2020, and return the production of the two numbers
```
for (i in (data.indices)) {
    if (data.contains(2020 - data[i]))
        return data[i] * (2020 - data[i])
}
```
**solution 1**

similar to two sum, find the complement of each number and if it exists in the array. O(n) for looping through the array and O(n) for checking if complement exists in array for each number so total time complexity is O(n^2) and space complexity O(1)

**solution 2**

another way is to create a hash table which will make the search of complement faster to O(1) but space complexity will be O(n)

2. give an list of numbers, find three numbers that sum to 2020, and return the production of the two numbers
```
for (i in (0..data.size-2)) {
    for (j in (i+1 until data.size)) {
        if (data.contains(2020 - data[i] - data[j])) // duplicate number?
            return data[i] * data[j] * (2020 - data[i] - data[j])
    }
}
```
similar to part 1, use two pointers instead of one for two numbers in the array and check if the complement is in array. O(n) looping for the first number, each of which takes O(n) for the second number, and O(n) to check if the array contains the complement of each combination of the two numbers. Total time complexity is O(n^3) and space complexity is O(1)

q: is uniqueness guarenteed in the array? 
