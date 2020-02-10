//
//  ViewController.swift
//  Calculator
//  Created by Tyler on 6/9/18.
//  Copyright © 2018 Tyler. All rights reserved.
//

import UIKit

enum Operation:String
{
    case Add = "+"
    case Subtract = "-"
    case Divide = "/"
    case Multiply = "*"
    case Squared = "2"
    case Power = "^"
    case Sin = "sin"
    case Cos = "cos"
    case Tan = "tan"
    case PN = "PN"
    case Empty = "Empty"
}

extension Formatter
{
    static let scientific: NumberFormatter =
    {
        let formatter = NumberFormatter()
        formatter.numberStyle = .scientific
        formatter.positiveFormat = "0.###E+0"
        formatter.exponentSymbol = "e"
        return formatter
    }()
}
extension Numeric
{
    var scientificFormatted: String
    {
        return Formatter.scientific.string(for: self) ?? ""
    }
}

class ViewController: UIViewController
{

    @IBOutlet weak var outletLbl: UILabel!
    
    var runningNumber = ""
    var leftValue = ""
    var rightValue = ""
    var result = ""
    var currentOperation:Operation =  .Empty
    var decimalPlaced = false
    var lastPressed = ""
    
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        outletLbl.text = "0";
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    //Handles Number Buttons
    @IBAction func numberPressed(_ sender:Button)
    {
        if runningNumber.count <= 8
        {
            runningNumber += "\(sender.tag)"
            outletLbl.text = runningNumber
            lastPressed = runningNumber
        }
    }
    
    
    //Handles All Clear Button
    @IBAction func allClearPressed(_ sender: Button)
    {
        runningNumber = ""
        leftValue = ""
        rightValue = ""
        result = ""
        currentOperation = .Empty
        outletLbl.text = "0"
        decimalPlaced = false
    }
    
    //Handles Equals Button.
    @IBAction func equalsPressed(_ sender: Button)
    {
            operation(operation: currentOperation)
    }
    
    //Handles Decimal Button
    @IBAction func decimalPressed(_ sender: Button)
    {
        if !decimalPlaced
        {
            decimalPlaced = true;
            if runningNumber.count <= 8
            {
                runningNumber += "."
                outletLbl.text = runningNumber
            }
        }
    }
    
    //Handles Plus Button
    @IBAction func plusPressed(_ sender: Button)
    {
        operation(operation: .Add)
    }
    
    //Handles Minus Button
    @IBAction func minusPressed(_ sender: Button)
    {
        operation(operation: .Subtract)
    }
    
    //Handles Multply Button
    @IBAction func multPressed(_ sender: Button)
    {
        operation(operation: .Multiply)
    }
    
    //Hanldes Divide Button
    @IBAction func divPressed(_ sender: Button)
    {
        operation(operation: .Divide)
    }
    
    //Handles pos/neg Button
    @IBAction func pnPressed(_ sender: Button) //DO
    {
        currentOperation = .PN
        operation(operation: .PN)
    }
    
    //Handles Squared Button
    @IBAction func squaredPressed(_ sender: Button)
    {
        currentOperation = .Squared
        operation(operation: .Squared)
    }
    
    //Handles Power Button
    @IBAction func powerPressed(_ sender: Button)
    {
        currentOperation = .Power
        operation(operation: .Power)
    }
    
    //Handles cos Button
    @IBAction func cosPressed(_ sender: Button)
    {
        currentOperation = .Cos
        operation(operation: .Cos)
    }
    
    //Handles sin Button
    @IBAction func sinPressed(_ sender: Button)
    {
        currentOperation = .Sin
        operation(operation: .Sin)
    }
    
    //Handles tan Button
    @IBAction func tanPressed(_ sender: Button)
    {
        currentOperation = .Tan
        operation(operation: .Tan)
    }
    
    func operation (operation: Operation)
    {
        if currentOperation != .Empty
        {
            if runningNumber != "" || (leftValue != "" && (currentOperation == .Squared || currentOperation == .PN || currentOperation == .Sin || currentOperation == .Cos || currentOperation == .Tan))
            {
                rightValue = runningNumber
                runningNumber = ""
                
                if currentOperation == .Add
                {
                    result = "\(Double(leftValue)! + Double(rightValue)!)"
                }
                else if currentOperation == .Subtract
                {
                     result = "\(Double(leftValue)! - Double(rightValue)!)"
                }
                else if currentOperation == .Multiply
                {
                     result = "\(Double(leftValue)! * Double(rightValue)!)"
                }
                else if currentOperation == .Divide
                {
                    result = "\(Double(leftValue)! / Double(rightValue)!)"
                }
                else if currentOperation == .Squared
                {
                    if(leftValue != "")
                    {
                        result = "\(Double(leftValue)! * Double(leftValue)!)"
                    }
                    else
                    {
                        result = "\(Double(rightValue)! * Double(rightValue)!)"
                    }
                }
                else if currentOperation == .Power
                {
                    result = "\(pow(Double(leftValue)!, Double(rightValue)!))"
                }
                else if currentOperation == .PN
                {
                    if(leftValue != "")
                    {
                      result = "\(Double(leftValue)! * -1)"
                    }
                    else
                    {
                        result = "\(Double(rightValue)! * -1)"
                    }
                }
                else if currentOperation == .Sin
                {
                    if(leftValue != "")
                    {
                        result = "\(sin(Double(leftValue)!))"
                    }
                    else
                    {
                        result = "\(sin(Double(rightValue)!))"
                    }
                }
                else if currentOperation == .Cos
                {
                    if(leftValue != "")
                    {
                        result = "\(cos(Double(leftValue)!))"
                    }
                    else
                    {
                        result = "\(cos(Double(rightValue)!))"
                    }
                }
                else if currentOperation == .Tan
                {
                    if(leftValue != "")
                    {
                        result = "\(tan(Double(leftValue)!))"
                    }
                    else
                    {
                        result = "\(tan(Double(rightValue)!))"
                    }
                }
                leftValue = result
                if(result.count >= 9)
                {
                    result = "\(Double(result)!.scientificFormatted)"
                }
                outletLbl.text = result
                lastPressed = result
                if outletLbl.text?.range(of: ".") != nil
                {
                    decimalPlaced = true
                }
            }
             currentOperation = operation
        }
        else
        {
            leftValue = runningNumber
            runningNumber = ""
            currentOperation = operation
        }
        
    }
}

