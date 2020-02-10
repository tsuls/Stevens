import Foundation

var weather = 60

var scheudle = 2

var likeRunning = 5


func checkWeather()
{
    if(weather > 45 && weather < 90)
    {
        print("Going for a run")
    }
    else
    {
        print("Not going for a run")
    }
}

func checkSchedule()
{
    if(scheudle <= 2)
    {
        checkWeather()
    }
    else
    {
        print("Not going for a run")
    }
}

func checkRun()
{
    if(likeRunning >= 4 )
    {
        checkSchedule()
    }
    else
    {
        print("Not going for a run")
    }
}

func main()
{
    checkRun();
}

main()