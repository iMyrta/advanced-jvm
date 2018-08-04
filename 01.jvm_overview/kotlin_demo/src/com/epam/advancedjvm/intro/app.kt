package com.epam.advancedjvm.intro


fun main(args: Array<String>) {
    val person = Person("""
John
Doe
""", null)
    print("Hello, world from ${person.firstName} ${person.lastName?.length}")
    Thread.sleep(200000)
}


data class Person(

        val firstName: String,
        val lastName: String?
)