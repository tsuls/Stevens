import Foundation

var times =
[
    "Elena" : 341,
    "Thomas": 273,
    "Hamilton": 278,
    "Suzie": 329,
    "Phil": 445,
    "Matt": 402,
    "Alex": 388,
    "Emma": 275,
    "John": 243,
    "James": 334,
    "Jane": 412,
    "Emily": 393,
    "Daniel": 299,
    "Neda": 343,
    "Aaron": 317,
    "Kate": 265
]

var fastest = Int.max
var fastestPerson = "God"



for (name, time) in times
{
    if time < fastest
    {
        fastest = time
        fastestPerson = name
    }
}
print("The fastest runner is \(fastestPerson) with a time of \(fastest) minutes")
