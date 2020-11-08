package com.example.android.developers.notesmanager.common

data class Note(
    val id: Int,
    val title: String,
    val description: String,
    val dayOfWeek: Int,
    val priority: String
){
    companion object{
        fun getDayAsString(number : Int )=
            when(number){
                1->"Понедельник"
                2->"Вторник"
                3->"Среда"
                4->"Четверг"
                5->"Пятница"
                6->"Суббота"
                else ->"Воскресенье"
            }

    }
}