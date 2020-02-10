
import UIKit

let highInterest = 1.02

let lowInterest = 1.01

var balance = 1000.0

func compound( bal : Double) -> Double
{
    var bal = bal
    if(bal > 500)
    {
        bal = bal * highInterest
        print("Interest was compounded. Your balance is now \(bal)")
    }
    else
    {
        bal = bal * lowInterest
        print("Interest was compounded. Your balance is now \(bal)")
    }
    return bal
}

func payAtEndOfMonth(bal : Double) -> Double
{
    var bal = bal
    
    if(bal > 100)
    {
        bal = bal - 100
        print("You paid 100. Your Balance now is \(bal)")
    }
    else
    {
        print("You paid \(bal). Your Balance now is \(bal - bal)")
        bal = bal - bal
    }
    
    
    return bal
}

func main()
{
    var numMonths = 0;
    
    print("Your took out a loan of \(balance)")
    while(balance > 0) //each iteration represents a month
    {
        numMonths = numMonths + 1
        balance = compound(bal: balance) //compound then pay
        balance = payAtEndOfMonth(bal: balance)
    }
    
    print("It took \(numMonths) months to pay off your balance")
}

main()

