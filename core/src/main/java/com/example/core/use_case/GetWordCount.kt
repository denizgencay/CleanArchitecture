package com.example.core.use_case

import com.example.core.data.Note

class GetWordCount {
    operator fun invoke(note: Note): Int{
        var wordCount = 0
        wordCount += getCount(note.title)
        wordCount += getCount(note.content)
        println(wordCount)
        return wordCount
    }

    private fun getCount(str: String) =
        str.split(" ","\n")
            .filter {
                it.contains(Regex(".*[a-zA-Z]"))
            }
            .count()
}