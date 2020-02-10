import Foundation


class Currency
{
    var country:String
    var name:String
    var symbol:Character
    
    init(inCountry:String, inName:String, inSymbol:Character)
    {
        country = inCountry
        name = inName
        symbol = inSymbol
    }
}

class InnerCash:Money
{
    var bill1:Int
    var bill10:Int
    var bill100:Int
    var bill20:Int
    var bill5:Int
    var bill50:Int
    
    init(inB1:Int, inB10:Int, inB100:Int, inB20:Int, inB5:Int, inB50:Int)
    {
        bill1 = inB1
        bill10 = inB10
        bill100 = inB100
        bill20 = inB20
        bill5 = inB5
        bill50 = inB50
    }
}

class ATM:Money
{
    var machineName:String
    var machineNumber:String
    
    init(inMNa:String, inMNu:String)
    {
        machineName = inMNa
        machineNumber = inMNu
    }
}

class Money:Currency
{
    var value:Float
    
    init(inValue:Float)
    {
        value = inValue
    }
}


class Log
{
    var cardNumber:String
    var date:Date
    var details:String
    var sessionNumber:String
    var time:Time
    var transactionNumber:String
    
    init(inCN:String, inDate:Date, inDetails:String, inSN:String, inTime:Time, inTN:String)
    {
        cardNumber = inCN
        date = inDate
        details = inDetails
        sessionNumber = inSN
        time = inTime
        transactionNumber = inTN
    }
}

class Card
{
    var expiriationDate:Date
    var holderName:String
    var holderSurname:String
    var number:String
    var password:String
   
    init(inED:Date, inHN:String, inHS:String, inNumber:String, inPassword:String)
    {
        expiriationDate = inED
        holderName = inHN
        holderSurname = inHS
        number = inNumber
        password = inPassword
    }
    
    func bank() -> Bank
    {
    }
}

class Account:Currency
{
    var initialBalance:Float
    var number:Character
    var overDraftLimit:Int
    var owner:String
    
    init(inIB:Float, inNumber:Character, inODL:Int, inOwner:String)
    {
        initialBalance = inIB
        number = inNumber
        overDraftLimit = inODL
        owner = inOwner
    }
}

class Bank
{
    var code:String
    var name:String
    
    init(inCode:String, inName:String)
    {
        code = inCode
        name = inName
    }
}

class Transaction:Currency
{
    var amount:Float
    var currentBalance:Int
    var date:Date
    var done:Bool
    var number:String
    var time:Time
    
    init(inAmount:Float, inCB:Int, inDate:Date, inDone:Bool, inNumber:String, inTime:Time)
    {
        amount = inAmount
        currentBalance = inCB
        date = inDate
        done = inDone
        number = inNumber
        time = inTime
    }
    
    func account() -> Account
    {
    }
    
    func bank() -> Bank
    {
    }
    
    func card() -> Card
    {
    }
}

class Withdrawl:Transaction
{
    init()
    {
    }
}

class Inquiry:Transaction
{
    init()
    {
    }
}

class Deposit:Transaction
{
    init()
    {
    }
}

class Transfer:Transaction
{
    var targetAccountNumber:String
    var targetBankCode:String
    
    init(inTAN:String, inTBC:String)
    {
        targetAccountNumber = inTAN
        targetBankCode = inTBC
    }
}

class Session:Card
{
    var name:String
    var lastMessage:String
    
    init(inName:String, inLM:String)
    {
        name = inName
        lastMessage = inLM
    }
    
    func account() -> Account
    {
    }
    
    override func bank() -> Bank
    {
    }
}


class ATMLogin
{
    var username:String
    var password:String
   
    init(inUsername:String, inPassword:String)
    {
        username = inUsername
        password = inPassword
    }
}
