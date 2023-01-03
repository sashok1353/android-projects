package com.example.wwitestapp.data.game2048.game

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    val nonEmptyCells = this.filterNotNull()
    val mergedCells = mutableListOf<T>()
    var i = 0
    while (i < nonEmptyCells.size) {
        if (i == nonEmptyCells.size - 1) {
            mergedCells.add(nonEmptyCells[i])
            i++
        } else if (nonEmptyCells[i] == nonEmptyCells[i+1]) {
            mergedCells.add(merge(nonEmptyCells[i]))
            i += 2
        } else {
            mergedCells.add(nonEmptyCells[i])
            i++
        }
    }
    return mergedCells
}


