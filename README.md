# Free-context-grammar-2
Role of this automata is to see if a word would be part of a grammar.
It would do this by CYK (Cocke–Younger–Kasami) algorithm.
A 2D array matrice[n][n] is created, 
where n is the length of the input string.

Each cell holds a set of non-terminals that can derive 
the substring for that position.

For each character in the input string, it finds all non-terminals 
that produce that character (e.g., A → a) and fills in the first row.

For substrings of length 2 to n, it checks all ways 
to split the substring into two parts.

It looks for combinations B C from the CYK matrix 
that match a production A → BC in the grammar.

If such a production exists, A is added to the corresponding cell.
If the start symbol S is in the top-right cell matrice[n-1][0], 
the string is derivable from the grammar.
